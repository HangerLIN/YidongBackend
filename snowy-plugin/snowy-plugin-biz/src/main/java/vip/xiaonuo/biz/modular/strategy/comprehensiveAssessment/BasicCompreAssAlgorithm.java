package vip.xiaonuo.biz.modular.strategy.comprehensiveAssessment;

import org.springframework.stereotype.Component;
import vip.xiaonuo.biz.modular.income.vo.IncomeVO;
import vip.xiaonuo.biz.modular.spend.vo.SpendVO;
import vip.xiaonuo.biz.modular.strategy.dto.*;
import vip.xiaonuo.biz.modular.strategy.utils.Pair;
import vip.xiaonuo.biz.modular.strategy.vo.CompreAssVO;
import vip.xiaonuo.biz.modular.strategy.vo.InvestVO;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component(value = "BasicCompreAssAlgorithm")
public class BasicCompreAssAlgorithm implements CompreAssAlgorithm{
    public CompreAssVO compreAssResult(BasicInfoForCompreAssReq basicVO, InvestVO investVO, IncomeVO incomeVO,
                                       SpendVO spendVO, StreamForCompreAssReq streamVO){

        CompreAssVO compreAssResp = new CompreAssVO();
        List<Pair<Integer, BigDecimal>> annualInvest = new ArrayList<>();   //每年详细投入
        List<Pair<Integer, BigDecimal>> annualProduce = new ArrayList<>();  //每年详细产出
        List<Pair<Integer, BigDecimal>> annualProfit = new ArrayList<>();   //每年详细利润
        List<Pair<Integer, BigDecimal>> annualProfitNet = new ArrayList<>(); //每年详细净利润
        List<BigDecimal> spendEveryYear = new ArrayList<>();

        List<InvestVO.ProjectUnincludeTotal> projectUnincludeTotalPerYear = investVO.getProjectUnincludeTotal(); //每年各个子产品不含税投资金额总和

        IncomeVO.UnincludeSum unincludeSum = incomeVO.getUnincludeSum();
        List<IncomeVO.Annual> incomeUnincludeSumAnnual = unincludeSum.getAnnual();

        Map<String, Map<Integer, BigDecimal>> sumsByTypeAndYear = spendVO.getAnnualSumsByTypeAndYear();
        Map<Integer, BigDecimal> spendSafeguard = sumsByTypeAndYear.get("SpendSafeguard");  //路面后期维护费用
        Map<Integer, BigDecimal> spendUpkeep = sumsByTypeAndYear.get("SpendUpkeep");     //路面保养费用
        Map<Integer, BigDecimal> spendArtificial = sumsByTypeAndYear.get("SpendArtifical"); //人工服务费用
        Map<Integer, BigDecimal> spendOther = sumsByTypeAndYear.get("SpendOther");      //其他费用
        Map<Integer, BigDecimal> spendNoise = sumsByTypeAndYear.get("SpendNoise");      //噪音污染补偿
        Map<Integer, BigDecimal> spendPublicize = sumsByTypeAndYear.get("SpendPublicized");  //宣传推广费用


        BigDecimal projectUnincludeTotal = BigDecimal.ZERO; //2. 投入明细的不含税投资额合计
        BigDecimal spendSafeguardTotal = BigDecimal.ZERO;  //4. 开支明细的路面后期维护费用合计
        BigDecimal spendUpkeepTotal = BigDecimal.ZERO;     //4. 开支明细的路面保养费用合计
        BigDecimal spendArtificialTotal = BigDecimal.ZERO; //4. 开支明细的人工服务费用合计
        BigDecimal spendOtherTotal = BigDecimal.ZERO;      //4. 开支明细的其他费用合计
        BigDecimal spendNoiseTotal = BigDecimal.ZERO;      //4. 开支明细的噪音污染补偿合计
        BigDecimal spendPublicizeTotal = BigDecimal.ZERO;  //4. 开支明细的宣传推广费用合计
        BigDecimal annualInvestTotal = BigDecimal.ZERO;
        BigDecimal annualProduceTotal = BigDecimal.ZERO;
        BigDecimal annualProfitTotal = BigDecimal.ZERO;
        BigDecimal annualProfitNetTotal = BigDecimal.ZERO;

        int construct_cycle = projectUnincludeTotalPerYear.size(); //1.基础信息页的建设周期
        int construct_start = basicVO.getConstructStart(); //1.基础信息页的项目开始年份
        int evaluate_cycle = spendSafeguard.size(); //1.基础信息页的评估周期
        int single_ae_cycle = basicVO.getSingleAeCycle(); //1.基础信息页的单批资产评估周期
        BigDecimal approvedTaxRate = streamVO.getApprovedTaxRate(); //6. 流水测试的审定税率
        BigDecimal tenK = new BigDecimal(10000);
        BigDecimal single_cycle = new BigDecimal(single_ae_cycle);

        for(int i = 0; i < construct_cycle; i++){
            BigDecimal amount = projectUnincludeTotalPerYear.get(i).getAmount();
            projectUnincludeTotal = projectUnincludeTotal.add(amount);
        }
        System.out.println(projectUnincludeTotal);

        for(int i = 0; i < evaluate_cycle; i++){
            int year = construct_start + i;
            spendSafeguardTotal.add(spendSafeguard.get(i));
            spendUpkeepTotal.add(spendUpkeep.get(i));
            spendArtificialTotal.add(spendArtificial.get(i));
            spendOtherTotal.add(spendOther.get(i));
            spendNoiseTotal.add(spendNoise.get(i));
            spendPublicizeTotal.add(spendPublicize.get(i));

            spendEveryYear.add(spendSafeguard.get(i).add(spendUpkeep.get(i))
                          .add(spendArtificial.get(i)).add(spendOther.get(i))
                          .add(spendNoise.get(i)).add(spendPublicize.get(i)));
        }

        BigDecimal tmp = BigDecimal.ZERO;
        for(int i = 0; i < evaluate_cycle; i++){
            if(i < construct_cycle) tmp = tmp.add(projectUnincludeTotalPerYear.get(i).getAmount());
            else if (i >= single_ae_cycle && i < single_ae_cycle + construct_cycle) {
                tmp = tmp.subtract(projectUnincludeTotalPerYear.get(i - single_ae_cycle).getAmount());
            }
            BigDecimal annualInvestEveryYear = tmp.divide(single_cycle,2, BigDecimal.ROUND_HALF_UP).add(spendEveryYear.get(i)).divide(tenK,2, BigDecimal.ROUND_HALF_UP);
            BigDecimal annualProduceEveryYear = incomeUnincludeSumAnnual.get(i).getAmount().divide(tenK,2, BigDecimal.ROUND_HALF_UP);
            BigDecimal annualProfitEveryYear = annualProduceEveryYear.subtract(annualInvestEveryYear);
            BigDecimal annualProfitNetEveryYear = BigDecimal.ONE.subtract(approvedTaxRate).multiply(annualProfitEveryYear);

            annualInvestTotal = annualInvestTotal.add(annualInvestEveryYear);
            annualProduceTotal = annualProduceTotal.add(annualProduceEveryYear);
            annualProfitTotal = annualProfitTotal.add(annualProfitEveryYear);
            annualProfitNetTotal = annualProfitNetTotal.add(annualProfitNetEveryYear);

            annualInvest.add(new Pair<Integer,BigDecimal>(annualInvestEveryYear.setScale(2, RoundingMode.HALF_UP)));
            annualProduce.add(new Pair<Integer,BigDecimal>(annualProduceEveryYear.setScale(2, RoundingMode.HALF_UP)));
            annualProfit.add(new Pair<Integer,BigDecimal>(annualProfitEveryYear.setScale(2, RoundingMode.HALF_UP)));
            annualProfitNet.add(new Pair<Integer,BigDecimal>(annualProfitNetEveryYear.setScale(2, RoundingMode.HALF_UP)));
        }
        BigDecimal approvalCost = projectUnincludeTotal.divide(tenK,2, BigDecimal.ROUND_HALF_UP);
        compreAssResp.setAnnualInvest(annualInvest);
        compreAssResp.setAnnualProduce(annualProduce);
        compreAssResp.setAnnualProfit(annualProfit);
        compreAssResp.setAnnualProfitNet(annualProfitNet);

        compreAssResp.setApprovalCost(approvalCost.setScale(2, RoundingMode.HALF_UP));
        compreAssResp.setOtherCost(annualInvestTotal.subtract(approvalCost).setScale(2, RoundingMode.HALF_UP));
        compreAssResp.setDirectIncome(annualProduceTotal.setScale(2, RoundingMode.HALF_UP));
        compreAssResp.setAnnualAvgProfitRate(annualProfitNetTotal.divide(annualProduceTotal,2, BigDecimal.ROUND_HALF_UP));
        compreAssResp.setRoi(annualProfitNetTotal.divide(approvalCost, 2, BigDecimal.ROUND_HALF_UP));
        compreAssResp.setPaybackPeriod(BigDecimal.ONE.setScale(2, RoundingMode.HALF_UP));
        compreAssResp.setCumulativeNPV(streamVO.getAnnualNetCashFlowPv().setScale(2, RoundingMode.HALF_UP));


        return compreAssResp;
    }
}
