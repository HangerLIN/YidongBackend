package vip.xiaonuo.biz.modular.spend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import vip.xiaonuo.biz.modular.income.vo.IncomeVO;
import vip.xiaonuo.biz.modular.spend.dto.SpendParam;
import vip.xiaonuo.biz.modular.spend.dto.SpendParam.SubprojectSpendInfo;
import vip.xiaonuo.biz.modular.spend.dto.SpendParam.SubprojectSpendInfo.Annualspend;
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

/**
 * @author M
 * @description 针对表【spend】的数据库操作Service实现
 * @createDate 2024-07-26 11:09:30
 */
@Service
public class SpendServiceImpl extends ServiceImpl<SpendMapper, Spend>
        implements SpendService {
    private final static Benchmark benchmark = new Benchmark(); //初始化过程中需要调用service获取数据


    @Override
    public boolean saveSubjectSpendInfo(SpendParam spendParam) throws Exception {
        //先创建一个返回的结果
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
                throw new Exception("数据不能为空");
            }

            //3.开始调用策略计算
            LambdaQueryWrapper<Benchmark> benchmarkLambdaQueryWrapper = new LambdaQueryWrapper<>();
            Integer productCode = 120198;
            benchmarkLambdaQueryWrapper.eq(Benchmark::getProductCode, productCode);
            //        Benchmark benchmark = benchmarkService.getOne(benchmarkLambdaQueryWrapper); //TODO
            Benchmark benchmark = new Benchmark(); //TODO:后续需要调用service获取数据

            BigDecimal depreciationRate1 = benchmark.getDepreciationRate1();
            BigDecimal depreciationRate2 = benchmark.getDepreciationRate2();
            BigDecimal depreciationRate3 = benchmark.getDepreciationRate3();
            BigDecimal depreciationRate4 = benchmark.getDepreciationRate4();

            IncomeVO incomeVO = new IncomeVO(); //TODO 后续需要调用service获取数据
            // 获得收入数据，使用HashMap完成数据的解耦
            Map<String, Map<String, List<IncomeVO.SubprojectIncomeVO.AnnualAdd>>> subprojectData = organizeIncomeData(incomeVO);

            // 3.1 计算后续每一个年度的数据
            //3.1.1 第一个位置的计算公式 直接相乘 不需要做减法 更新第一个位置的数据
            updateSpendAnnualAddDataFirstYear(incomeVO, spendVO);
            //3.1.2 第二个位置的计算公式 需要做减法
            updateSpendDataOtherYear(incomeVO, spendVO, benchmark);

            //3.1.3 计算含税金额
            applyTaxRatesUsingReflection(spendVO);

            //4. 计算每一种支出类别每一年总和的年度数据 （纵向求和）
            calculateAnnualSumsByTypeAndYear(spendVO);
            //5. 计算每一种产品代码所有年度的总和（横向求和），
            calculateTotalSumsForAllYears(spendVO);

            // 数据为手动输入形式,直接存入数据库
            Spend spend = new Spend();
            String subprojectId = subprojectSpendInfo.getSubprojectId();
            spend.setSpendId(Long.valueOf(subprojectId));
            // 下面步骤省略了

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

    public void updateSpendAnnualAddDataFirstYear(IncomeVO incomeVO, SpendVO spendVO) {
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
                Benchmark benchmark = new Benchmark(); //TODO:后续需要调用service获取数据
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
                for (SpendVO.SubprojectSpendVO subprojectSpend : spendVO.getSubprojectSpendVOList()) {
                    updateFirstAnnualSpend(subprojectSpend.getSpendUpkeep().getAnnual(), depreciationRate2.multiply(numberAsBigDecimal).multiply(monthAsBigDecimal).divide(new BigDecimal(2)));

                    // 计算并更新 SpendSafeguard
                    updateFirstAnnualSpend(subprojectSpend.getSpendSafeguard().getAnnual(), depreciationRate4.multiply(numberAsBigDecimal).multiply(monthAsBigDecimal).divide(new BigDecimal(2)));

                    // 计算并更新 SpendArtifical
                    updateFirstAnnualSpend(subprojectSpend.getSpendArtifical().getAnnual(), depreciationRate3.multiply(numberAsBigDecimal).multiply(monthAsBigDecimal).divide(new BigDecimal(2)));

                    // 计算并更新 SpendOther
                    updateFirstAnnualSpend(subprojectSpend.getSpendOther().getAnnual(), other.multiply(numberAsBigDecimal).multiply(monthAsBigDecimal).divide(new BigDecimal(2)));
                }


            }
        }
    }

    /**
     * 更新第一个年度支出
     * @param annualSpendList
     * @param result
     */
    private void updateFirstAnnualSpend(List<SpendVO.SubprojectSpendVO.Annualspend> annualSpendList, BigDecimal result) {
        if (annualSpendList != null && !annualSpendList.isEmpty()) {
            SpendVO.SubprojectSpendVO.Annualspend firstAnnualSpend = annualSpendList.get(0);
            firstAnnualSpend.setAmount(result);
        }
    }

    /**
     * 更新支出数据
     * @param incomeVO
     * @param spendVO
     */
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

    private void updateAnnualSpendList(List<SpendVO.SubprojectSpendVO.Annualspend> spends, BigDecimal rate, Map<Integer, Integer> yearEndUseMap, Map<Integer, Integer> annualAddMap, Map<Integer, Integer> annualDiscardMap, int startCalculationYear) {
        for (SpendVO.SubprojectSpendVO.Annualspend spend : spends) {
            if (spend.getYear() >= startCalculationYear) {
                BigDecimal baseValue = calculateBaseValue(spend.getYear(), yearEndUseMap, annualAddMap, annualDiscardMap);
                BigDecimal result = rate.multiply(baseValue);
                spend.setAmount(result);
            }
        }
    }

    private BigDecimal calculateBaseValue(int year, Map<Integer, Integer> yearEndUseMap, Map<Integer, Integer> annualAddMap, Map<Integer, Integer> annualDiscardMap) {
        Integer previousYearEndUse = yearEndUseMap.getOrDefault(year - 1, 0);
        Integer currentYearAdd = annualAddMap.getOrDefault(year, 0);
        Integer currentYearDiscard = annualDiscardMap.getOrDefault(year, 0);
        return new BigDecimal(previousYearEndUse * 12 + (currentYearAdd - currentYearDiscard) * 6);
    }

/**
     * 计算每一种支出类别每一年总和的年度数据 （纵向求和）
     * @param spendVO
     */
    public void calculateAnnualSumsByTypeAndYear(SpendVO spendVO) {
        Map<String, Map<Integer, BigDecimal>> sumsByTypeAndYear = new HashMap<>();

        for (SpendVO.SubprojectSpendVO subproject : spendVO.getSubprojectSpendVOList()) {
            accumulateAnnualSums(sumsByTypeAndYear, "SpendSafeguard", subproject.getSpendSafeguard().getAnnual());
            accumulateAnnualSums(sumsByTypeAndYear, "SpendUpkeep", subproject.getSpendUpkeep().getAnnual());
            accumulateAnnualSums(sumsByTypeAndYear, "SpendArtifical", subproject.getSpendArtifical().getAnnual());
            accumulateAnnualSums(sumsByTypeAndYear, "SpendOther", subproject.getSpendOther().getAnnual());
            accumulateAnnualSums(sumsByTypeAndYear, "SpendNoise", subproject.getSpendNoise().getAnnual());
            accumulateAnnualSums(sumsByTypeAndYear, "SpendPublicize", subproject.getSpendPublicize().getAnnual());
        }
        spendVO.setAnnualSumsByTypeAndYear(sumsByTypeAndYear);
    }

    private void accumulateAnnualSums(Map<String, Map<Integer, BigDecimal>> sumsByTypeAndYear, String type, List<SpendVO.SubprojectSpendVO.Annualspend> annuals) {
        if (annuals != null) {
            sumsByTypeAndYear.putIfAbsent(type, new HashMap<>());
            Map<Integer, BigDecimal> sumsByYear = sumsByTypeAndYear.get(type);
            for (SpendVO.SubprojectSpendVO.Annualspend annual : annuals) {
                sumsByYear.merge(annual.getYear(), annual.getAmount(), BigDecimal::add);
            }
        }
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
            List<SpendVO.SubprojectSpendVO.Annualspend> annuals = (List<SpendVO.SubprojectSpendVO.Annualspend>) getAnnualMethod.invoke(spendCategory);
            BigDecimal totalSum = annuals.stream()
                    .map(SpendVO.SubprojectSpendVO.Annualspend::getAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            Method setSumMethod = spendCategory.getClass().getMethod("setSum", BigDecimal.class);
            setSumMethod.invoke(spendCategory, totalSum);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 使用反射计算含税金额,并更新到原对象中
     * @param spendVO
     */
    public void applyTaxRatesUsingReflection(SpendVO spendVO) {
        for (SpendVO.SubprojectSpendVO subproject : spendVO.getSubprojectSpendVOList()) {
            try {
                // 遍历所有支出类型字段
                Field[] fields = subproject.getClass().getDeclaredFields();
                for (Field field : fields) {
                    field.setAccessible(true);
                    // 只处理非含税字段且对应含税字段存在的情况
                    if (!field.getName().endsWith("R6") && !field.getName().endsWith("R9")) {
                        String taxFieldName = field.getName() + (field.getName().contains("Safeguard") ? "R9" : "R6");
                        Field taxField = null;
                        try {
                            taxField = subproject.getClass().getDeclaredField(taxFieldName);
                        } catch (NoSuchFieldException e) {
                            continue;  // 如果对应的含税字段不存在，跳过
                        }
                        taxField.setAccessible(true);

                        Object fieldValue = field.get(subproject);
                        if (fieldValue != null) {
                            BigDecimal taxRate = new BigDecimal(field.getName().contains("Safeguard") ? "1.09" : "1.06");
                            mapWithTaxUsingReflection((SpendVO.SubprojectSpendVO.SpendSafeguard)fieldValue, taxField, subproject, taxRate);
                        }
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private void mapWithTaxUsingReflection(SpendVO.SubprojectSpendVO.SpendSafeguard source, Field targetField, SpendVO.SubprojectSpendVO subproject, BigDecimal taxRate) throws IllegalAccessException {
        List<SpendVO.SubprojectSpendVO.Annualspend> sourceAnnuals = source.getAnnual();
        List<SpendVO.SubprojectSpendVO.Annualspend> targetAnnuals = new ArrayList<>();
        for (SpendVO.SubprojectSpendVO.Annualspend annual : sourceAnnuals) {
            BigDecimal taxedAmount = annual.getAmount().multiply(taxRate);
            targetAnnuals.add(new SpendVO.SubprojectSpendVO.Annualspend(annual.getYear(), taxedAmount));
        }
        SpendVO.SubprojectSpendVO.SpendSafeguard target = (SpendVO.SubprojectSpendVO.SpendSafeguard)targetField.get(subproject);
        if (target == null) {
            target = new SpendVO.SubprojectSpendVO.SpendSafeguard();
            targetField.set(subproject, target);
        }
        target.setAnnual(targetAnnuals);
    }
}




