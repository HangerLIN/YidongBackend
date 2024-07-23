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
@Component(value = "InvestAmountPerTaxRate")
public class InvestAmountPerTaxRate implements InvestAlgorithm{

    @Override
    public InvestResp investResult(InvestReq data) {
        System.out.println("计算项目的各个税率的投资额，如13%税率投资额（不含税），6%，9%，13%税率投资额（含税）等");
        // TODO
        // 执行业务操作

        InvestResp resp = new InvestResp();  //创建一个包含所有投资明细表所需的计算结果的vo类
        List<InvestResp.ProjectUnincludeTaxRate13> projectUnincludeTaxRate13 = new ArrayList<>();
        List<InvestResp.ProjectIncludeTaxRate6> projectIncludeTaxRate6 = new ArrayList<>();
        List<InvestResp.ProjectIncludeTaxRate9> projectIncludeTaxRate9 = new ArrayList<>();
        List<InvestResp.ProjectIncludeTaxRate13> projectIncludeTaxRate13 = new ArrayList<>();
        List<InvestResp.ProjectIncludeTotal> projectIncludeTotal = new ArrayList<>();

        List<InvestResp.ProjectUnincludeTotal> projectUnincludeTotal = data.getProjectUnincludeTotal();
        List<InvestReq.ProjectUnincludeTaxRate6> projectUnincludeTaxRate6 = data.getProjectUnincludeTaxRate6();
        List<InvestReq.ProjectUnincludeTaxRate9> projectUnincludeTaxRate9 = data.getProjectUnincludeTaxRate9();
        int n = projectUnincludeTotal.size();

        for(int i = 0; i < n; i++){
            BigDecimal unincludeTotal = projectUnincludeTotal.get(i).getAmount();
            BigDecimal unincludetaxRate6 = projectUnincludeTaxRate6.get(i).getAmount();
            BigDecimal unincludetaxRate9 = projectUnincludeTaxRate9.get(i).getAmount();
            BigDecimal unincludetaxRate13 = unincludeTotal.subtract(unincludetaxRate6).subtract(unincludetaxRate9);

            BigDecimal taxRate6 = new BigDecimal("1.06");
            BigDecimal taxRate9 = new BigDecimal("1.09");
            BigDecimal taxRate13 = new BigDecimal("1.13");
            BigDecimal includetaxRate6 = unincludetaxRate6.multiply(taxRate6);
            BigDecimal includetaxRate9 = unincludetaxRate9.multiply(taxRate9);
            BigDecimal includetaxRate13 = unincludetaxRate13.multiply(taxRate13);
            BigDecimal includeTotal = includetaxRate6.add(includetaxRate9).add(includetaxRate13);

            InvestResp.ProjectUnincludeTaxRate13 projectUnincludeTaxRate13_single = new InvestResp.ProjectUnincludeTaxRate13();
            projectUnincludeTaxRate13_single.setAmount(unincludetaxRate13);
            projectUnincludeTaxRate13.add(projectUnincludeTaxRate13_single);

            InvestResp.ProjectIncludeTaxRate6 projectIncludeTaxRate6_single = new InvestResp.ProjectIncludeTaxRate6();
            projectIncludeTaxRate6_single.setAmount(includetaxRate6);
            projectIncludeTaxRate6.add(projectIncludeTaxRate6_single);

            InvestResp.ProjectIncludeTaxRate9 projectIncludeTaxRate9_single = new InvestResp.ProjectIncludeTaxRate9();
            projectIncludeTaxRate9_single.setAmount(includetaxRate9);
            projectIncludeTaxRate9.add(projectIncludeTaxRate9_single);

            InvestResp.ProjectIncludeTaxRate13 projectIncludeTaxRate13_single = new InvestResp.ProjectIncludeTaxRate13();
            projectIncludeTaxRate13_single.setAmount(includetaxRate13);
            projectIncludeTaxRate13.add(projectIncludeTaxRate13_single);

            InvestResp.ProjectIncludeTotal projectIncludeTotal_single = new InvestResp.ProjectIncludeTotal();
            projectIncludeTotal_single.setAmount(includeTotal);
            projectIncludeTotal.add(projectIncludeTotal_single);
        }

        resp.setProjectUnincludeTaxRate13(projectUnincludeTaxRate13);
        resp.setProjectIncludeTaxRate6(projectIncludeTaxRate6);
        resp.setProjectIncludeTaxRate9(projectIncludeTaxRate9);
        resp.setProjectIncludeTaxRate13(projectIncludeTaxRate13);
        resp.setProjectIncludeTotal(projectIncludeTotal);


        System.out.println(resp);

        return resp;
    }
}
