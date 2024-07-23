package vip.xiaonuo.biz.modular.strategy.invest;

import org.springframework.stereotype.Component;
import vip.xiaonuo.biz.modular.strategy.dto.InvestReq;
import vip.xiaonuo.biz.modular.strategy.vo.InvestResp;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
/**
 * 策略:计算方式1
 */
@Component(value = "InvestAmountOfSubproject")
public class InvestAmountOfSubproject implements InvestAlgorithm{

    @Override
    public InvestResp investResult(InvestReq data) {
        System.out.println("计算每个子项目的投资金额（不含税）");
        // TODO
        // 执行业务操作
        InvestResp resp = new InvestResp();  //创建一个包含所有投资明细表所需的计算结果的vo类
        List<InvestResp.SubProjectInvest> subProjectInvests = new ArrayList<>();  //每个子项目的投资金额
        List<InvestResp.ProjectUnincludeTotal> projectUnincludeTotals = new ArrayList<>(); //每年的不含税投资额合计
        List<InvestReq.SubProjectScheduleAndCost> subProjectScheduleAndCosts = data.getSubProjectScheduleAndCosts(); //每个子项目在建设年份内的每年的建造数量和单价
        int n = subProjectScheduleAndCosts.get(0).getSchedule().size();
        List<BigDecimal> unincludeTotal = new ArrayList<>(n);
        for (int i = 0; i < n; i++) { unincludeTotal.add(BigDecimal.ZERO); };
         //建设年份，总共要建设多少年
        for(int i = 0; i < subProjectScheduleAndCosts.size(); i++){

            List<InvestReq.SubProjectScheduleAndCost.Schedule> schedule = subProjectScheduleAndCosts.get(i).getSchedule(); //获取某个子项目在建设年份内每年的建造数量
            List<InvestReq.SubProjectScheduleAndCost.Cost> cost = subProjectScheduleAndCosts.get(i).getCost(); //获取某个子项目在建设年份内每年的单价
            List<InvestResp.SubProjectInvest.SubProjectInvestAmount> subProjectInvestAmounts = new ArrayList<>(); //某个子项目在建设年份内的每年的投资金额

            for(int j = 0; j < n; j++){
                InvestResp.SubProjectInvest.SubProjectInvestAmount subProjectInvestAmount = new InvestResp.SubProjectInvest.SubProjectInvestAmount();
                BigDecimal result = schedule.get(j).getAmount().multiply(cost.get(j).getAmount());  //每年的投资金额 =  建造数量 * 单价
                unincludeTotal.set(j, unincludeTotal.get(j).add(result));  //每年投资金额的总和

                subProjectInvestAmount.setAmount(result);
                subProjectInvestAmounts.add(subProjectInvestAmount);
            }
            InvestResp.SubProjectInvest subProjectInvest = new InvestResp.SubProjectInvest();
            subProjectInvest.setSubProjectInvestAmount(subProjectInvestAmounts);  //计算得到某个子项目在建设年份内的每年的投资金额

            subProjectInvests.add(subProjectInvest);
        }

        for(int i = 0; i < n; i++){
            InvestResp.ProjectUnincludeTotal projectUnincludeTotal = new InvestResp.ProjectUnincludeTotal();
            projectUnincludeTotal.setAmount(unincludeTotal.get(i));
            projectUnincludeTotals.add(projectUnincludeTotal);
        }

        resp.setSubProjectInvest(subProjectInvests); //每个子项目的投资金额
        resp.setProjectUnincludeTotal(projectUnincludeTotals);  //每年各个子项目投资金额的总和

        System.out.println(resp);

        return resp;
    }
}
