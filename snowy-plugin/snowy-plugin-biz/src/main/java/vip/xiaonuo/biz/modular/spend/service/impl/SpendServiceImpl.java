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

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author M
 * @description 针对表【spend】的数据库操作Service实现
 * @createDate 2024-07-26 11:09:30
 */
@Service
public class SpendServiceImpl extends ServiceImpl<SpendMapper, Spend>
        implements SpendService {
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

            // 3.1 计算保养
            //3.1.1 第一个位置的计算公式 直接相乘 不需要做减法
            BigDecimal firstYearKeepUp;


            //3.1.2 第二个位置的计算公式 需要做减法

            // 3.2 计算维护
            // 3.3 计算其他
            // 3.4 计算人工

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

    public void updateSpendAnnualAddData(IncomeVO incomeVO, SpendVO spendVO) {
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
                Integer month = 8;

                // 将 Integer 转换为 BigDecimal
                BigDecimal numberAsBigDecimal = new BigDecimal(number);
                BigDecimal monthAsBigDecimal = new BigDecimal(month);

                // 执行乘法运算
                BigDecimal calculatedResult = depreciationRate2.multiply(numberAsBigDecimal).multiply(monthAsBigDecimal);

                // 3. 将结果更新到 SpendVO 中的 SpendUpkeep 的 annual 的第一个元素
                if (spendVO.getSubprojectSpendVOList() != null && !spendVO.getSubprojectSpendVOList().isEmpty()) {
                    SpendVO.SubprojectSpendVO firstSubprojectSpend = spendVO.getSubprojectSpendVOList().get(0);
                    SpendVO.SubprojectSpendVO.SpendUpkeep spendUpkeep = firstSubprojectSpend.getSpendUpkeep();

                    if (spendUpkeep.getAnnual() != null && !spendUpkeep.getAnnual().isEmpty()) {
                        Annualspend firstAnnualSpend = spendUpkeep.getAnnual().get(0);
                        firstAnnualSpend.setAmount(calculatedResult);
                    }
                }
            }
        }
    }


    @Override
    public SpendVO caculateSpendInfo(SpendParam spendParam) {
        return null;
    }
}




