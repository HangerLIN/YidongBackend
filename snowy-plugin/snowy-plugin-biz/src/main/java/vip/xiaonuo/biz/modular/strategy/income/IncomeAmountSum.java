package vip.xiaonuo.biz.modular.strategy.income;

import org.springframework.stereotype.Component;
import vip.xiaonuo.biz.modular.income.vo.IncomeVO;
import vip.xiaonuo.biz.modular.income.vo.IncomeVO.Annual;
import vip.xiaonuo.biz.modular.strategy.dto.IncomeReq;

import java.math.BigDecimal;
import java.util.List;

@Component(value = "IncomeAmountSum")
public class IncomeAmountSum implements IncomeAlgorithm{


    @Override
    public IncomeReq incomeResult(IncomeReq data) {
        BigDecimal unincludeTotalsum = new BigDecimal(0);
        BigDecimal includeTaxRate6sum = new BigDecimal(0);

        List<Annual> unincludeTotallist = data.getUnincludeTotal();
        List<Annual> includeTaxRate6list = data.getIncludeTaxRate6();
        for (Annual unincludeTotal: unincludeTotallist){
            BigDecimal amount1 = unincludeTotal.getAmount();
            unincludeTotalsum = unincludeTotalsum.add(amount1);
        }

        for (Annual includeTaxRate6: includeTaxRate6list){
            BigDecimal amount2 = includeTaxRate6.getAmount();
            includeTaxRate6sum = includeTaxRate6sum.add(amount2);
        }
        IncomeReq incomeReq = new IncomeReq();
        incomeReq.setUnincludeTotalSum(unincludeTotalsum);
        incomeReq.setIncludeTaxRate6Sum(includeTaxRate6sum);
        return incomeReq;
    }
}
