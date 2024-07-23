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
        InvestResp resp = new InvestResp();
        List<InvestResp.SubProjectInvest> subProjectInvests = new ArrayList<>();
        List<InvestReq.SubProjectScheduleAndCost> subProjectScheduleAndCosts = data.getSubProjectScheduleAndCosts();
        for(int i = 0; i < subProjectScheduleAndCosts.size(); i++){

            List<InvestReq.SubProjectScheduleAndCost.Schedule> schedule = subProjectScheduleAndCosts.get(i).getSchedule();
            List<InvestReq.SubProjectScheduleAndCost.Cost> cost = subProjectScheduleAndCosts.get(i).getCost();
            int n = schedule.size();
            List<InvestResp.SubProjectInvest.SubProjectInvestAmount> subProjectInvestAmounts = new ArrayList<>();

            for(int j = 0; j < n; j++){
                InvestResp.SubProjectInvest.SubProjectInvestAmount subProjectInvestAmount = new InvestResp.SubProjectInvest.SubProjectInvestAmount();
                BigDecimal result = schedule.get(j).getAmount().multiply(cost.get(j).getAmount());
                subProjectInvestAmount.setAmount(result);
                subProjectInvestAmounts.add(subProjectInvestAmount);
            }
            InvestResp.SubProjectInvest subProjectInvest = new InvestResp.SubProjectInvest();
            subProjectInvest.setSubProjectInvestAmount(subProjectInvestAmounts);

            subProjectInvests.add(subProjectInvest);
        }
        resp.setSubProjectInvest(subProjectInvests);

        System.out.println(resp);

        return resp;
    }
}
