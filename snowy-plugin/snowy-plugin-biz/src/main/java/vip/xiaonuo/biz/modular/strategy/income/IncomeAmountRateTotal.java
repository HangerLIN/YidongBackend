package vip.xiaonuo.biz.modular.strategy.income;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import vip.xiaonuo.biz.modular.income.vo.IncomeVO.Annual;
import vip.xiaonuo.biz.modular.strategy.dto.IncomeReq;
import vip.xiaonuo.biz.modular.subjectinfo.mapper.SubprojectMapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component(value = "IncomeAmountRateTotal")
public class IncomeAmountRateTotal implements IncomeAlgorithm{

    @Resource
    private SubprojectMapper subprojectMapper;

    @Override
    public IncomeReq incomeResult(IncomeReq data) {
        List<Annual> unincludeTotalList = data.getUnincludeTotal();

        List<Annual> resultList = new ArrayList<>();

        BigDecimal rate = subprojectMapper.selectTaxRateById(data.getSubprojectId());


        for (Annual unincludeTotal:unincludeTotalList) {
            Annual annual = new Annual();
            BigDecimal decimal = unincludeTotal.getAmount().multiply(rate);
            Integer year = unincludeTotal.getYear();
            annual.setAmount(decimal);
            annual.setYear(year);
            resultList.add(annual);
        }

        IncomeReq incomeReq = new IncomeReq();
        incomeReq.setIncludeTaxRate6(resultList);

        return incomeReq;
    }
}
