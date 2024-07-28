package vip.xiaonuo.biz.modular.strategy.comprehensiveAssessment;

import org.springframework.stereotype.Component;
import vip.xiaonuo.biz.modular.strategy.dto.*;
import vip.xiaonuo.biz.modular.strategy.utils.Pair;
import vip.xiaonuo.biz.modular.strategy.vo.CompreAssResp;
import vip.xiaonuo.biz.modular.strategy.vo.InvestResp;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Component(value = "BasicCompreAssAlgorithm")
public class BasicCompreAssAlgorithm implements CompreAssAlgorithm{
    public CompreAssResp compreAssResult(BasicInfoForCompreAssReq basicReq, InvestResp investResp, IncomeForCompreAssReq incomeReq,
                                  SpendForCompreAssReq spendReq, StreamForCompreAssReq streamReq){

        CompreAssResp compreAssResp = new CompreAssResp();
        List<Pair<Integer, BigDecimal>> annualInvest = new ArrayList<>();   //每年详细投入
        List<Pair<Integer, BigDecimal>> annualProduce = new ArrayList<>();  //每年详细产出
        List<Pair<Integer, BigDecimal>> annualProfit = new ArrayList<>();   //每年详细利润
        List<Pair<Integer, BigDecimal>> annualProfitNet = new ArrayList<>(); //每年详细净利润
        List<BigDecimal> spendEveryYear = new ArrayList<>();

        List<InvestResp.ProjectUnincludeTotal> projectUnincludeTotalPerYear = investResp.getProjectUnincludeTotal(); //每年各个子产品不含税投资金额总和
        List<Pair<Integer, BigDecimal>> spendSafeguard = spendReq.getSpendSafeguard();  //路面后期维护费用
        List<Pair<Integer, BigDecimal>> spendUpkeep = spendReq.getSpendUpkeep();     //路面保养费用
        List<Pair<Integer, BigDecimal>> spendArtificial = spendReq.getSpendArtificial(); //人工服务费用
        List<Pair<Integer, BigDecimal>> spendOther = spendReq.getSpendOther();      //其他费用
        List<Pair<Integer, BigDecimal>> spendNoise = spendReq.getSpendNoise();      //噪音污染补偿
        List<Pair<Integer, BigDecimal>> spendPublicize = spendReq.getSpendPublicize();  //宣传推广费用

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
        int evaluate_cycle = spendSafeguard.size(); //1.基础信息页的评估周期
        int single_ae_cycle = basicReq.getSingleAeCycle(); //1.基础信息页的单批资产评估周期
        BigDecimal approvedTaxRate = streamReq.getApprovedTaxRate(); //6. 流水测试的审定税率
        BigDecimal tenK = new BigDecimal(10000);
        BigDecimal single_cycle = new BigDecimal(single_ae_cycle);

        for(int i = 0; i < construct_cycle; i++){
            BigDecimal amount = projectUnincludeTotalPerYear.get(i).getAmount();
            projectUnincludeTotal = projectUnincludeTotal.add(amount);
        }
        System.out.println(projectUnincludeTotal);

        for(int i = 0; i < evaluate_cycle; i++){
            spendSafeguardTotal.add(spendSafeguard.get(i).getSecond());
            spendUpkeepTotal.add(spendUpkeep.get(i).getSecond());
            spendArtificialTotal.add(spendArtificial.get(i).getSecond());
            spendOtherTotal.add(spendOther.get(i).getSecond());
            spendNoiseTotal.add(spendNoise.get(i).getSecond());
            spendPublicizeTotal.add(spendPublicize.get(i).getSecond());

            spendEveryYear.add(spendSafeguard.get(i).getSecond().add(spendUpkeep.get(i).getSecond())
                          .add(spendArtificial.get(i).getSecond()).add(spendOther.get(i).getSecond())
                          .add(spendNoise.get(i).getSecond()).add(spendPublicize.get(i).getSecond()));
        }

        BigDecimal tmp = BigDecimal.ZERO;
        for(int i = 0; i < evaluate_cycle; i++){
            if(i < construct_cycle) tmp = tmp.add(projectUnincludeTotalPerYear.get(i).getAmount());
            else if (i >= single_ae_cycle && i < single_ae_cycle + construct_cycle) {
                tmp = tmp.subtract(projectUnincludeTotalPerYear.get(i - single_ae_cycle).getAmount());
            }
            BigDecimal annualInvestEveryYear = tmp.divide(single_cycle,2, BigDecimal.ROUND_HALF_UP).add(spendEveryYear.get(i)).divide(tenK,2, BigDecimal.ROUND_HALF_UP);
            BigDecimal annualProduceEveryYear = incomeReq.getUnincludeTotal().get(i).getSecond().divide(tenK,2, BigDecimal.ROUND_HALF_UP);
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
        compreAssResp.setCumulativeNPV(streamReq.getAnnualNetCashFlowPv().setScale(2, RoundingMode.HALF_UP));


        return compreAssResp;
    }
}
