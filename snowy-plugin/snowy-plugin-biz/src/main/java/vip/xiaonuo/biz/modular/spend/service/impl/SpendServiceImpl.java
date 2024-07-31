package vip.xiaonuo.biz.modular.spend.service.impl;

import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import vip.xiaonuo.biz.modular.income.vo.IncomeVO;
import vip.xiaonuo.biz.modular.spend.dto.SpendParam;
import vip.xiaonuo.biz.modular.spend.dto.SpendParam.SubprojectSpendInfo;
import vip.xiaonuo.biz.modular.spend.entity.Annualspend;
import vip.xiaonuo.biz.modular.spend.entity.Benchmark;
import vip.xiaonuo.biz.modular.spend.entity.Spend;
import vip.xiaonuo.biz.modular.spend.service.SpendService;
import vip.xiaonuo.biz.modular.spend.mapper.SpendMapper;
import org.springframework.stereotype.Service;
import vip.xiaonuo.biz.modular.spend.vo.SpendVO;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author M
 * @description 针对表【spend】的数据库操作Service实现
 * @createDate 2024-07-26 11:09:30
 */
@Service
public class SpendServiceImpl extends ServiceImpl<SpendMapper, Spend>
        implements SpendService {
    private final static Benchmark benchmark = new Benchmark(); //初始化过程中需要调用service获取数据

    static {
        benchmark.setDepreciationRate1(new BigDecimal("0.1"));
        benchmark.setDepreciationRate2(new BigDecimal("0.2"));
        benchmark.setDepreciationRate3(new BigDecimal("0.3"));
        benchmark.setDepreciationRate4(new BigDecimal("0.4"));
        benchmark.setMaintenanceOther(new BigDecimal("0.5"));
    }

    @Override
    public SpendVO saveSubjectSpendInfo(SpendParam spendParam) throws Exception {
        //先创建一个返回的结果
        //这个SpendVO
        SpendVO spendVO = new SpendVO();

        List<SubprojectSpendInfo> subprojectSpendInfoList = spendParam.getSubprojectSpendInfoList();
        for (SubprojectSpendInfo subprojectSpendInfo : subprojectSpendInfoList) {
            // 1. 检验子项目信息是否需要策略计算
            List<Annualspend> spendUpkeep = subprojectSpendInfo.getSpendUpkeep();// 保养
            List<Annualspend> spendSafeguard = subprojectSpendInfo.getSpendSafeguard();// 维护
            List<Annualspend> spendOther = subprojectSpendInfo.getSpendOther();// 其他
            List<Annualspend> spendArtifical = subprojectSpendInfo.getSpendArtifical();// 人工
            List<Annualspend> spendNoise = subprojectSpendInfo.getSpendNoise();// 噪音
            List<Annualspend> spendPublish = subprojectSpendInfo.getSpendPublish();// 宣传推广

            // 2. 判断数据是否为空
            if(Objects.isNull(spendUpkeep) || Objects.isNull(spendSafeguard) || Objects.isNull(spendOther) || Objects.isNull(spendArtifical)){
                //TODO 判断是否是手动输入的 每一次都是对相同的一个id对应的Project的List来进行判定 判定是否是一个需要手动输入的 直接跳过计算的步骤
                continue;
            }

            //3.开始调用策略计算
            LambdaQueryWrapper<Benchmark> benchmarkLambdaQueryWrapper = new LambdaQueryWrapper<>();
            Integer productCode = 120198;
            //TODO 计算获得月份差（开始），第一年需要做减法获得月份的数目；后续的年份需要都是直接乘以12
            benchmarkLambdaQueryWrapper.eq(Benchmark::getProductCode, productCode);
            //        Benchmark benchmark = benchmarkService.getOne(benchmarkLambdaQueryWrapper); //TODO:后续需要调用service获取数据

            BigDecimal depreciationRate1 = benchmark.getDepreciationRate1();
            BigDecimal depreciationRate2 = benchmark.getDepreciationRate2();
            BigDecimal depreciationRate3 = benchmark.getDepreciationRate3();
            BigDecimal depreciationRate4 = benchmark.getDepreciationRate4();

            IncomeVO incomeVO = new IncomeVO(); //TODO 后续需要调用service获取数据,这里只是模拟数据
            String JsonMessage = ResourceUtil.readUtf8Str("incomeVO.json");
            incomeVO = JSONUtil.toBean(JsonMessage, IncomeVO.class);

            // 获得收入数据，使用HashMap完成数据的解耦
            Map<String, Map<String, List<IncomeVO.SubprojectIncomeVO.AnnualAdd>>> subprojectData = organizeIncomeData(incomeVO);

            // 3.1 计算后续每一个年度的数据
            //3.1.1 第一个位置的计算公式 直接相乘 不需要做减法 更新第一个位置的数据
            updateSpendAnnualAddDataFirstYear(incomeVO, spendVO, subprojectSpendInfoList);
            //3.1.2 第二个位置的计算公式 需要做减法
            updateSpendDataOtherYear(incomeVO, spendVO, benchmark, subprojectSpendInfoList);

            //3.1.3 计算含税金额
            // 这部分不需要再从SpendParam中获取数据，因为已经在spendVO里面在上面注入了数据了
            applyTaxRates(spendVO);

            //4. 计算每一种支出类别每一年总和的年度数据 （纵向求和）
            calculateAnnualSumsByTypeAndYear(spendVO);
            //5. 计算每一种产品代码所有年度的总和（横向求和），
            calculateTotalSumsForAllYears(spendVO);

            // 6. 保存数据 遍历的方式批量存储 按照一个List同时存入
            saveSpendInfo(spendVO);
        }
        return spendVO;
    }

    /**
     * 保存支出信息,将数据序列化存储
     * @param spendVO
     * @return
     */
    @Override
    public boolean saveSpendInfo(SpendVO spendVO) {
        // 遍历 SpendVO 中的 subprojectSpendInfoList，并将每个项目的数据序列化存储
        // 值得一提的是，传入的一个SpendVO对象中包含了多个子项目的支出信息，需要使用循环遍历的方式将每个子项目的支出信息存储到数据库中
        for (SpendVO.SubprojectSpendVO subproject : spendVO.getSubprojectSpendVOList()) {
            Spend spend = new Spend();
            spend.setSubprojectId(Long.parseLong(subproject.getSubprojectId())); // 设置子项目ID

            // 序列化并存储每个具体的支出信息
            spend.setSpendSafeguard(JSONUtil.toJsonStr(subproject.getSpendSafeguard()));
            spend.setSpendUpkeep(JSONUtil.toJsonStr(subproject.getSpendUpkeep()));
            spend.setSpendArtificial(JSONUtil.toJsonStr(subproject.getSpendArtifical()));
            spend.setSpendOther(JSONUtil.toJsonStr(subproject.getSpendOther()));
            spend.setSpendNoise(JSONUtil.toJsonStr(subproject.getSpendNoise()));
            spend.setSpendPublicize(JSONUtil.toJsonStr(subproject.getSpendPublicize()));

            // 持久化 Spend 对象
            save(spend);
        }
        return true;
    }

    /**
     * 整理收入数据,将数据按照subprojectId存储
     *
     * @param incomeVO 收入数据
     * @return 整理后的数据
     */
    public Map<String, Map<String, List<IncomeVO.SubprojectIncomeVO.AnnualAdd>>> organizeIncomeData(IncomeVO incomeVO) {
        Map<String, Map<String, List<IncomeVO.SubprojectIncomeVO.AnnualAdd>>> subprojectData = new HashMap<>();
        List<IncomeVO.SubprojectIncomeVO> subprojects = incomeVO.getSubprojectIncomeVO();

        if (subprojects != null) {
            for (IncomeVO.SubprojectIncomeVO subproject : subprojects) {
                Map<String, List<IncomeVO.SubprojectIncomeVO.AnnualAdd>> detailsMap = new HashMap<>();

                // 获取annualAdd列表并存储
                detailsMap.put("annualAdd", subproject.getAnnualAdd());
                // 获取annualDiscard列表并存储
                detailsMap.put("annualDiscard", subproject.getAnnualDiscard());
                // 获取yearEndUse列表并存储
                detailsMap.put("yearEndUse", subproject.getYearEndUse());

                // 将详情Map按照subprojectId存储
                subprojectData.put(subproject.getSubprojectId(), detailsMap);
            }
        }
        return subprojectData;
    }

    /**
     *
     * @param incomeVO
     * @param spendVO
     * @param subprojectSpendInfoList
     */
    public void updateSpendAnnualAddDataFirstYear(IncomeVO incomeVO, SpendVO spendVO, List<SubprojectSpendInfo> subprojectSpendInfoList) {
        // 1. 获取 IncomeVO 中的 annualAdd 的第一个元素
        if (incomeVO.getSubprojectIncomeVO() != null && !incomeVO.getSubprojectIncomeVO().isEmpty()) {
            IncomeVO.SubprojectIncomeVO firstSubprojectIncome = incomeVO.getSubprojectIncomeVO().get(0);
            List<IncomeVO.SubprojectIncomeVO.AnnualAdd> annualAdds = firstSubprojectIncome.getAnnualAdd();

            if (annualAdds != null && !annualAdds.isEmpty()) {
                IncomeVO.SubprojectIncomeVO.AnnualAdd firstAnnualAdd = annualAdds.get(0);
                Integer number = firstAnnualAdd.getNumber();

                // 2. 执行计算（这里简化为直接将number转为BigDecimal，实际可能涉及复杂计算）
                LambdaQueryWrapper<Benchmark> benchmarkLambdaQueryWrapper = new LambdaQueryWrapper<>();
                Integer productCode = 120198;
                benchmarkLambdaQueryWrapper.eq(Benchmark::getProductCode, productCode);
                //        Benchmark benchmark = benchmarkService.getOne(benchmarkLambdaQueryWrapper); //TODO
//                Benchmark benchmark = new Benchmark(); //TODO:后续需要调用service获取数据
                BigDecimal depreciationRate2 = benchmark.getDepreciationRate2();
                BigDecimal depreciationRate3 = benchmark.getDepreciationRate3(); // For spendArtifical
                BigDecimal depreciationRate4 = benchmark.getDepreciationRate4(); // For spendSafeguard
                BigDecimal other = benchmark.getMaintenanceOther(); // For spendOther

                Integer month = 8;

                // 将 Integer 转换为 BigDecimal
                BigDecimal numberAsBigDecimal = new BigDecimal(number);
                BigDecimal monthAsBigDecimal = new BigDecimal(month);
//                // 执行乘法运算
//                // 计算结果
//                BigDecimal resultUpkeep = depreciationRate2.multiply(numberAsBigDecimal).multiply(monthAsBigDecimal).divide(new BigDecimal(2));
//                BigDecimal resultSafeguard = depreciationRate4.multiply(numberAsBigDecimal).multiply(monthAsBigDecimal).divide(new BigDecimal(2));
//                BigDecimal resultArtifical = depreciationRate3.multiply(numberAsBigDecimal).multiply(monthAsBigDecimal).divide(new BigDecimal(2));
//                BigDecimal resultOther = other.multiply(numberAsBigDecimal).multiply(monthAsBigDecimal).divide(new BigDecimal(2));

                // 对折旧类的
                for (SpendParam.SubprojectSpendInfo subprojectSpendInfo: subprojectSpendInfoList) {
                    updateFirstAnnualSpend(subprojectSpendInfo.getSpendUpkeep(), depreciationRate2.multiply(numberAsBigDecimal).multiply(monthAsBigDecimal).divide(new BigDecimal(2)));

                    // 计算并更新 SpendSafeguard
                    updateFirstAnnualSpend(subprojectSpendInfo.getSpendSafeguard(), depreciationRate4.multiply(numberAsBigDecimal).multiply(monthAsBigDecimal).divide(new BigDecimal(2)));

                    // 计算并更新 SpendArtifical
                    updateFirstAnnualSpend(subprojectSpendInfo.getSpendArtifical(), depreciationRate3.multiply(numberAsBigDecimal).multiply(monthAsBigDecimal).divide(new BigDecimal(2)));

                    // 计算并更新 SpendOther
                    updateFirstAnnualSpend(subprojectSpendInfo.getSpendOther(), other.multiply(numberAsBigDecimal).multiply(monthAsBigDecimal).divide(new BigDecimal(2)));
                }
                // 使用 MapStruct 进行映射 因为这里是一个List 而且是第一次映射 全部映射
                List<SpendVO.SubprojectSpendVO> subprojectSpendVOList = subprojectSpendInfoList.stream()
                                                                                                .map(vip.xiaonuo.biz.modular.spend.convert.SpendConvert::toSubprojectSpendVO)
                                                                                                .collect(Collectors.toList());

                spendVO.setSubprojectSpendVOList(subprojectSpendVOList);
            }
        }
    }

    /**
     * 更新第一年的支出数据
     * @param annualspendList
     * @param rate
     */
    private void updateFirstAnnualSpend(List<Annualspend> annualspendList, BigDecimal rate) {
        BigDecimal amount = annualspendList.get(0).getAmount(); //获得当前id产品类型的第一年的开销
        BigDecimal res = amount.multiply(rate);
        annualspendList.get(0).setAmount(res);
    }


//    private void updateFirstAnnualSpend(List<SpendParam.SubprojectSpendInfo> subprojectSpendInfoList, BigDecimal result) {
//        for (int i = 0; i < subprojectSpendInfoList.size(); i++) {
//            if (subprojectSpendInfoList != null && !subprojectSpendInfoList.isEmpty()) {
//                SubprojectSpendInfo subprojectSpendInfo = subprojectSpendInfoList.get(i);
//
//                        // 使用 MapStruct 进行映射
//                        List<SpendVO.SubprojectSpendVO> subprojectSpendVOList = subprojectSpendInfoList.stream()
//                        .map(spendConvert.INSTANCE::toSubprojectSpendVO)
//                        .collect(Collectors.toList());
//                firstAnnualSpend.setAmount(result);
//            }
//        }
//    }

    /**
     * 更新支出数据
     * @param incomeVO
     * @param spendVO
     */
    @Deprecated
    public void updateSpendDataOtherYear(IncomeVO incomeVO, SpendVO spendVO, Benchmark benchmark) {
        // 从 Benchmark 对象获取不同的折旧率和其他参数
        BigDecimal depreciationRate2 = benchmark.getDepreciationRate2(); // For spendUpkeep
        BigDecimal depreciationRate4 = benchmark.getDepreciationRate4(); // For spendSafeguard
        BigDecimal depreciationRate3 = benchmark.getDepreciationRate3(); // For spendArtifical
        BigDecimal other = benchmark.getMaintenanceOther(); // For spendOther

        // 预先构建用于存储年度数据的映射
        Map<Integer, Integer> yearEndUseMap = new HashMap<>();
        Map<Integer, Integer> annualAddMap = new HashMap<>();
        Map<Integer, Integer> annualDiscardMap = new HashMap<>();

        // 从 IncomeVO 提取数据
        for (IncomeVO.SubprojectIncomeVO subprojectIncome : incomeVO.getSubprojectIncomeVO()) {
            for (IncomeVO.SubprojectIncomeVO.AnnualAdd add : subprojectIncome.getAnnualAdd()) {
                annualAddMap.put(add.getYear(), add.getNumber());
            }
            for (IncomeVO.SubprojectIncomeVO.AnnualAdd discard : subprojectIncome.getAnnualDiscard()) {
                annualDiscardMap.put(discard.getYear(), discard.getNumber());
            }
            for (IncomeVO.SubprojectIncomeVO.AnnualAdd use : subprojectIncome.getYearEndUse()) {
                yearEndUseMap.put(use.getYear(), use.getNumber());
            }
        }

        // 更新每个支出类别的数据
        for (SpendVO.SubprojectSpendVO subprojectSpend : spendVO.getSubprojectSpendVOList()) {
            // 假设年度列表是按年份排序的
            int startCalculationYear = subprojectSpend.getSpendUpkeep().getAnnual().isEmpty() ? 0 :
                    subprojectSpend.getSpendUpkeep().getAnnual().get(0).getYear() + 1; // 第二年开始计算

            updateAnnualSpendList(subprojectSpend.getSpendUpkeep().getAnnual(), depreciationRate2, yearEndUseMap, annualAddMap, annualDiscardMap, startCalculationYear);
            updateAnnualSpendList(subprojectSpend.getSpendSafeguard().getAnnual(), depreciationRate4, yearEndUseMap, annualAddMap, annualDiscardMap, startCalculationYear);
            updateAnnualSpendList(subprojectSpend.getSpendArtifical().getAnnual(), depreciationRate3, yearEndUseMap, annualAddMap, annualDiscardMap, startCalculationYear);
            updateAnnualSpendList(subprojectSpend.getSpendOther().getAnnual(), other, yearEndUseMap, annualAddMap, annualDiscardMap, startCalculationYear);
        }
    }

    public void updateSpendDataOtherYear(IncomeVO incomeVO, SpendVO spendVO, Benchmark benchmark, List<SubprojectSpendInfo> subprojectSpendInfoList) {
        BigDecimal depreciationRate2 = benchmark.getDepreciationRate2(); // For spendUpkeep
        BigDecimal depreciationRate4 = benchmark.getDepreciationRate4(); // For spendSafeguard
        BigDecimal depreciationRate3 = benchmark.getDepreciationRate3(); // For spendArtifical
        BigDecimal other = benchmark.getMaintenanceOther(); // For spendOther

        Map<Integer, Integer> yearEndUseMap = new HashMap<>();
        Map<Integer, Integer> annualAddMap = new HashMap<>();
        Map<Integer, Integer> annualDiscardMap = new HashMap<>();

        for (IncomeVO.SubprojectIncomeVO subprojectIncome : incomeVO.getSubprojectIncomeVO()) {
            for (IncomeVO.SubprojectIncomeVO.AnnualAdd add : subprojectIncome.getAnnualAdd()) {
                annualAddMap.put(add.getYear(), add.getNumber());
            }
            for (IncomeVO.SubprojectIncomeVO.AnnualAdd discard : subprojectIncome.getAnnualDiscard()) {
                annualDiscardMap.put(discard.getYear(), discard.getNumber());
            }
            for (IncomeVO.SubprojectIncomeVO.AnnualAdd use : subprojectIncome.getYearEndUse()) {
                yearEndUseMap.put(use.getYear(), use.getNumber());
            }
        }

        for (SubprojectSpendInfo subprojectSpendInfo : subprojectSpendInfoList) {
            //获取当前第一年的年份
            int startYear = subprojectSpendInfo.getSpendUpkeep().get(0).getYear(); //随便取一个subprojectSpendInfo的年份 反正都是统一的
            updateAnnualSpendList(subprojectSpendInfo.getSpendUpkeep(), depreciationRate2, yearEndUseMap, annualAddMap, annualDiscardMap, startYear); // 从第二年开始计算，所以 startYear 为 1
            updateAnnualSpendList(subprojectSpendInfo.getSpendSafeguard(), depreciationRate4, yearEndUseMap, annualAddMap, annualDiscardMap, startYear);
            updateAnnualSpendList(subprojectSpendInfo.getSpendArtifical(), depreciationRate3, yearEndUseMap, annualAddMap, annualDiscardMap, startYear);
            updateAnnualSpendList(subprojectSpendInfo.getSpendOther(), other, yearEndUseMap, annualAddMap, annualDiscardMap, startYear);
        }

        List<SpendVO.SubprojectSpendVO> subprojectSpendVOList = subprojectSpendInfoList.stream()
                .map(vip.xiaonuo.biz.modular.spend.convert.SpendConvert::toSubprojectSpendVO)
                .collect(Collectors.toList());

        spendVO.setSubprojectSpendVOList(subprojectSpendVOList);
    }

    private void updateAnnualSpendList(List<Annualspend> spends, BigDecimal rate, Map<Integer, Integer> yearEndUseMap, Map<Integer, Integer> annualAddMap, Map<Integer, Integer> annualDiscardMap, int startYear) {
        for (Annualspend spend : spends) {
            if (spend.getYear() > startYear) {
                BigDecimal baseValue = calculateBaseValue(spend.getYear(), yearEndUseMap, annualAddMap, annualDiscardMap);
                BigDecimal result = rate.multiply(baseValue);
                spend.setAmount(result);
            }
        }
    }

//    private void updateAnnualSpendList(List<Annualspend> spends, BigDecimal rate, Map<Integer, Integer> yearEndUseMap, Map<Integer, Integer> annualAddMap, Map<Integer, Integer> annualDiscardMap, int startCalculationYear) {
//        for (Annualspend spend : spends) {
//            if (spend.getYear() >= startCalculationYear) {
//                BigDecimal baseValue = calculateBaseValue(spend.getYear(), yearEndUseMap, annualAddMap, annualDiscardMap);
//                BigDecimal result = rate.multiply(baseValue);
//                spend.setAmount(result);
//            }
//        }
//    }

    @Deprecated
    private void updateAnnualSpendList(List<Annualspend> spends, BigDecimal rate, Map<Integer, Integer> yearEndUseMap, Map<Integer, Integer> annualAddMap, Map<Integer, Integer> annualDiscardMap) {
        for (Annualspend spend : spends) {
            BigDecimal baseValue = calculateBaseValue(spend.getYear(), yearEndUseMap, annualAddMap, annualDiscardMap);
            BigDecimal result = rate.multiply(baseValue);
            spend.setAmount(result);
        }
    }


//    private BigDecimal calculateBaseValue(int year, Map<Integer, Integer> yearEndUseMap, Map<Integer, Integer> annualAddMap, Map<Integer, Integer> annualDiscardMap) {
//        Integer previousYearEndUse = yearEndUseMap.getOrDefault(year - 1, 0);
//        Integer currentYearAdd = annualAddMap.getOrDefault(year, 0);
//        Integer currentYearDiscard = annualDiscardMap.getOrDefault(year, 0);
//        return new BigDecimal(previousYearEndUse * 12 + (currentYearAdd - currentYearDiscard) * 6);
//    }

    private BigDecimal calculateBaseValue(int year, Map<Integer, Integer> yearEndUseMap, Map<Integer, Integer> annualAddMap, Map<Integer, Integer> annualDiscardMap) {
        Integer previousYearEndUse = yearEndUseMap.getOrDefault(year - 1, 0);
        Integer currentYearAdd = annualAddMap.getOrDefault(year, 0);
        Integer currentYearDiscard = annualDiscardMap.getOrDefault(year, 0);
        return new BigDecimal(previousYearEndUse * 12 + (currentYearAdd - currentYearDiscard) * 6);
    }


//    /**
//     * 计算每一种支出类别每一年总和的年度数据 （纵向求和）
//     * @param spendVO
//     */
//    public void calculateAnnualSumsByTypeAndYear(SpendVO spendVO) {
//        Map<String, Map<Integer, BigDecimal>> sumsByTypeAndYear = new HashMap<>();
//
//        for (SpendVO.SubprojectSpendVO subproject : spendVO.getSubprojectSpendVOList()) {
//            accumulateAnnualSums(sumsByTypeAndYear, "SpendSafeguard", subproject.getSpendSafeguard().getAnnual());
//            accumulateAnnualSums(sumsByTypeAndYear, "SpendUpkeep", subproject.getSpendUpkeep().getAnnual());
//            accumulateAnnualSums(sumsByTypeAndYear, "SpendArtifical", subproject.getSpendArtifical().getAnnual());
//            accumulateAnnualSums(sumsByTypeAndYear, "SpendOther", subproject.getSpendOther().getAnnual());
//            accumulateAnnualSums(sumsByTypeAndYear, "SpendNoise", subproject.getSpendNoise().getAnnual());
//            accumulateAnnualSums(sumsByTypeAndYear, "SpendPublicize", subproject.getSpendPublicize().getAnnual());
//            accumulateAnnualSums(sumsByTypeAndYear, "SpendSafeguardR9", subproject.getSpendSafeguardR9().getAnnual());
//            accumulateAnnualSums(sumsByTypeAndYear, "SpendUpkeepR6", subproject.getSpendUpkeepR6().getAnnual());
//            accumulateAnnualSums(sumsByTypeAndYear, "SpendArtificalR9", subproject.getSpendArtificalR9().getAnnual());
//            accumulateAnnualSums(sumsByTypeAndYear, "SpendOtherR9", subproject.getSpendOtherR9().getAnnual());
//            accumulateAnnualSums(sumsByTypeAndYear, "SpendNoiseR6", subproject.getSpendNoiseR6().getAnnual());
//            accumulateAnnualSums(sumsByTypeAndYear, "SpendPublicizeR6", subproject.getSpendPublicizeR6().getAnnual());
//        }
//        spendVO.setAnnualSumsByTypeAndYear(sumsByTypeAndYear);
//    }
//
//    private void accumulateAnnualSums(Map<String, Map<Integer, BigDecimal>> sumsByTypeAndYear, String type, List<Annualspend> annuals) {
//        if (annuals != null) {
//            sumsByTypeAndYear.putIfAbsent(type, new HashMap<>());
//            Map<Integer, BigDecimal> sumsByYear = sumsByTypeAndYear.get(type);
//            for (Annualspend annual : annuals) {
//                sumsByYear.merge(annual.getYear(), annual.getAmount(), BigDecimal::add);
//            }
//        } else {
//            System.out.println("Warning: " + type + " annuals list is null, skipping.");
//        }
//    }

    /**
     * 计算每一种支出类别每一年总和的年度数据 （纵向求和）
     * @param spendVO
     */
    public void calculateAnnualSumsByTypeAndYear(SpendVO spendVO) {
        Map<String, Map<Integer, BigDecimal>> sumsByTypeAndYear = new HashMap<>();
        BigDecimal totalSumWithoutR = BigDecimal.ZERO;
        BigDecimal totalSumWithR = BigDecimal.ZERO;

        for (SpendVO.SubprojectSpendVO subproject : spendVO.getSubprojectSpendVOList()) {
            totalSumWithoutR = totalSumWithoutR.add(accumulateAnnualSums(sumsByTypeAndYear, "SpendSafeguard", subproject.getSpendSafeguard().getAnnual()));
            totalSumWithoutR = totalSumWithoutR.add(accumulateAnnualSums(sumsByTypeAndYear, "SpendUpkeep", subproject.getSpendUpkeep().getAnnual()));
            totalSumWithoutR = totalSumWithoutR.add(accumulateAnnualSums(sumsByTypeAndYear, "SpendArtifical", subproject.getSpendArtifical().getAnnual()));
            totalSumWithoutR = totalSumWithoutR.add(accumulateAnnualSums(sumsByTypeAndYear, "SpendOther", subproject.getSpendOther().getAnnual()));
            totalSumWithoutR = totalSumWithoutR.add(accumulateAnnualSums(sumsByTypeAndYear, "SpendNoise", subproject.getSpendNoise().getAnnual()));
            totalSumWithoutR = totalSumWithoutR.add(accumulateAnnualSums(sumsByTypeAndYear, "SpendPublicize", subproject.getSpendPublicize().getAnnual()));

            totalSumWithR = totalSumWithR.add(accumulateAnnualSums(sumsByTypeAndYear, "SpendSafeguardR9", subproject.getSpendSafeguardR9().getAnnual()));
            totalSumWithR = totalSumWithR.add(accumulateAnnualSums(sumsByTypeAndYear, "SpendUpkeepR6", subproject.getSpendUpkeepR6().getAnnual()));
            totalSumWithR = totalSumWithR.add(accumulateAnnualSums(sumsByTypeAndYear, "SpendArtificalR9", subproject.getSpendArtificalR9().getAnnual()));
            totalSumWithR = totalSumWithR.add(accumulateAnnualSums(sumsByTypeAndYear, "SpendOtherR9", subproject.getSpendOtherR9().getAnnual()));
            totalSumWithR = totalSumWithR.add(accumulateAnnualSums(sumsByTypeAndYear, "SpendNoiseR6", subproject.getSpendNoiseR6().getAnnual()));
            totalSumWithR = totalSumWithR.add(accumulateAnnualSums(sumsByTypeAndYear, "SpendPublicizeR6", subproject.getSpendPublicizeR6().getAnnual()));
        }

        spendVO.setAnnualSumsByTypeAndYear(sumsByTypeAndYear);
        spendVO.setTotalSumWithoutR(totalSumWithoutR);
        spendVO.setTotalSumWithR(totalSumWithR);
    }

    private BigDecimal accumulateAnnualSums(Map<String, Map<Integer, BigDecimal>> sumsByTypeAndYear, String type, List<Annualspend> annuals) {
        BigDecimal sum = BigDecimal.ZERO;

        if (annuals != null) {
            sumsByTypeAndYear.putIfAbsent(type, new HashMap<>());
            Map<Integer, BigDecimal> sumsByYear = sumsByTypeAndYear.get(type);
            for (Annualspend annual : annuals) {
                sumsByYear.merge(annual.getYear(), annual.getAmount(), BigDecimal::add);
                sum = sum.add(annual.getAmount());
            }
        } else {
            System.out.println("Warning: " + type + " annuals list is null, skipping.");
        }

        return sum;
    }



    /**
     * 计算每一种产品代码所有年度的总和（横向求和），使用反射
     * @param spendVO
     */
    public void calculateTotalSumsForAllYears(SpendVO spendVO) {
        for (SpendVO.SubprojectSpendVO subproject : spendVO.getSubprojectSpendVOList()) {
            sumAllYearsReflectively(subproject.getSpendSafeguard());
            sumAllYearsReflectively(subproject.getSpendUpkeep());
            sumAllYearsReflectively(subproject.getSpendArtifical());
            sumAllYearsReflectively(subproject.getSpendOther());
            sumAllYearsReflectively(subproject.getSpendNoise());
            sumAllYearsReflectively(subproject.getSpendPublicize());
            sumAllYearsReflectively(subproject.getSpendSafeguardR9());
            sumAllYearsReflectively(subproject.getSpendUpkeepR6());
            sumAllYearsReflectively(subproject.getSpendArtificalR9());
            sumAllYearsReflectively(subproject.getSpendOtherR9());
            sumAllYearsReflectively(subproject.getSpendNoiseR6());
            sumAllYearsReflectively(subproject.getSpendPublicizeR6());
        }
    }

    private void sumAllYearsReflectively(Object spendCategory) {
        try {
            Method getAnnualMethod = spendCategory.getClass().getMethod("getAnnual");
            List<Annualspend> annuals = (List<Annualspend>) getAnnualMethod.invoke(spendCategory);
            BigDecimal totalSum = annuals.stream()
                    .map(Annualspend::getAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            Method setSumMethod = spendCategory.getClass().getMethod("setSum", BigDecimal.class);
            setSumMethod.invoke(spendCategory, totalSum);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 直接在 VO 对象中计算含税金额。
     * @param spendVO 包含所有子项目支出数据的 SpendVO 对象。
     */
    public void applyTaxRates(SpendVO spendVO) {
        for (SpendVO.SubprojectSpendVO subproject : spendVO.getSubprojectSpendVOList()) {
            // 处理保障支出的含税计算
            if (subproject.getSpendSafeguard() != null) {
                if (subproject.getSpendSafeguardR9() == null) {
                    subproject.setSpendSafeguardR9(new SpendVO.SubprojectSpendVO.SpendSafeguardR9());
                }
                subproject.getSpendSafeguardR9().setAnnual(
                        applyTaxRate(subproject.getSpendSafeguard().getAnnual(), new BigDecimal("1.09"))
                );
            }

            // 处理维护支出的含税计算
            if (subproject.getSpendUpkeep() != null) {
                if (subproject.getSpendUpkeepR6() == null) {
                    subproject.setSpendUpkeepR6(new SpendVO.SubprojectSpendVO.SpendUpkeepR6());
                }
                subproject.getSpendUpkeepR6().setAnnual(
                        applyTaxRate(subproject.getSpendUpkeep().getAnnual(), new BigDecimal("1.06"))
                );
            }

            // 处理人工成本的含税计算
            if (subproject.getSpendArtifical() != null) {
                if (subproject.getSpendArtificalR9() == null) {
                    subproject.setSpendArtificalR9(new SpendVO.SubprojectSpendVO.SpendArtificalR9());
                }
                subproject.getSpendArtificalR9().setAnnual(
                        applyTaxRate(subproject.getSpendArtifical().getAnnual(), new BigDecimal("1.09"))
                );
            }

            // 处理其他支出的含税计算
            if (subproject.getSpendOther() != null) {
                if (subproject.getSpendOtherR9() == null) {
                    subproject.setSpendOtherR9(new SpendVO.SubprojectSpendVO.SpendOtherR9());
                }
                subproject.getSpendOtherR9().setAnnual(
                        applyTaxRate(subproject.getSpendOther().getAnnual(), new BigDecimal("1.09"))
                );
            }

            // 处理噪音支出的含税计算
            if (subproject.getSpendNoise() != null) {
                if (subproject.getSpendNoiseR6() == null) {
                    subproject.setSpendNoiseR6(new SpendVO.SubprojectSpendVO.SpendNoiseR6());
                }
                subproject.getSpendNoiseR6().setAnnual(
                        applyTaxRate(subproject.getSpendNoise().getAnnual(), new BigDecimal("1.06"))
                );
            }

            // 处理宣传支出的含税计算
            if (subproject.getSpendPublicize() != null) {
                if (subproject.getSpendPublicizeR6() == null) {
                    subproject.setSpendPublicizeR6(new SpendVO.SubprojectSpendVO.SpendPublicizeR6());
                }
                subproject.getSpendPublicizeR6().setAnnual(
                        applyTaxRate(subproject.getSpendPublicize().getAnnual(), new BigDecimal("1.06"))
                );
            }
        }
    }

    /**
     * 辅助方法，用于对一组 Annualspend 应用税率。
     * @param originalList 原始的 Annualspend 列表。
     * @param taxRate 要应用的税率。
     * @return 一个包含已税金额的 Annualspend 列表。
     */
    private List<Annualspend> applyTaxRate(List<Annualspend> originalList, BigDecimal taxRate) {
        List<Annualspend> taxedList = new ArrayList<>();
        if (originalList != null) {
            for (Annualspend original : originalList) {
                BigDecimal taxedAmount = original.getAmount().multiply(taxRate);
                taxedList.add(new Annualspend(original.getYear(), taxedAmount));
            }
        }
        return taxedList;
    }

}




