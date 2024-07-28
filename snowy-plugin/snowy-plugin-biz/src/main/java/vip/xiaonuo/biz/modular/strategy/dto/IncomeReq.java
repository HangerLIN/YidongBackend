package vip.xiaonuo.biz.modular.strategy.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import vip.xiaonuo.biz.modular.income.dto.IncomeParam;
import vip.xiaonuo.biz.modular.income.vo.IncomeVO;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@Data
public class IncomeReq {
    private String subprojectId;
    private List<IncomeParam.SubprojectIncome.AnnualInfo> annualAdd;
    private List<IncomeParam.SubprojectIncome.AnnualInfo> annualDiscard;
    private List<IncomeVO.Annual> subprojectSingleprice;
    private List<IncomeParam.SubprojectIncome.AnnualInfo> yearEndUse;
    private List<IncomeVO.Annual> unincludeTotal;
    private List<IncomeVO.Annual> includeTaxRate6;
    private BigDecimal unincludeTotalSum;
    private BigDecimal includeTaxRate6Sum;

}
