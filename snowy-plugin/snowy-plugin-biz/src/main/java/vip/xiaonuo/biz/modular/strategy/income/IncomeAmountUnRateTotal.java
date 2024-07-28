package vip.xiaonuo.biz.modular.strategy.income;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import vip.xiaonuo.biz.modular.strategy.dto.IncomeReq;
import vip.xiaonuo.biz.modular.subjectinfo.mapper.SubprojectMapper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import static vip.xiaonuo.biz.modular.income.dto.IncomeParam.SubprojectIncome.AnnualInfo;
import static vip.xiaonuo.biz.modular.income.vo.IncomeVO.Annual;

@Component(value = "IncomeAmountUnRateTotal")
public class IncomeAmountUnRateTotal implements IncomeAlgorithm{

//    @Resource
//    private SubprojectMapper subprojectMapper;

    @Override
    public IncomeReq incomeResult(IncomeReq data) {
        List<Annual> SinglepriceList = data.getSubprojectSingleprice();
        List<AnnualInfo> yearEndUseList = data.getYearEndUse();
        List<AnnualInfo> annualAdd = data.getAnnualAdd();
        List<AnnualInfo> annualDiscard = data.getAnnualDiscard();

        // 获取主项目评估开始日期
//        String projectId = subprojectMapper.selectProjectIdById(data.getSubprojectId());

        int yuefen = 8;

        List<Annual> resultList = new ArrayList<>();
        for (Annual Singleprice:SinglepriceList) {
            resultList.add(new Annual(Singleprice));
        }

        // 初始化第一个元素
        BigDecimal amount = SinglepriceList.get(0).getAmount();
        Integer number = yearEndUseList.get(0).getNumber();
        BigDecimal numberDecimal = new BigDecimal(number);
        BigDecimal multiply = numberDecimal.multiply(amount).multiply(BigDecimal.valueOf(yuefen));
        BigDecimal res = multiply.divide(BigDecimal.valueOf(3),2, RoundingMode.HALF_UP);
        resultList.get(0).setAmount(res);

        // 设置其他年份的收入情况
        int size = resultList.size();
        for (int i = 1; i < size; i++){
            // BigDecimal s_amount = SinglepriceList.get(i - 1).getAmount();
            Integer s_number = yearEndUseList.get(i - 1).getNumber();
            amount = SinglepriceList.get(i).getAmount();
            BigDecimal s_numberDecimal = new BigDecimal(s_number);
            BigDecimal multiply1 = s_numberDecimal.multiply(amount).multiply(BigDecimal.valueOf(12));

            int chaji = annualAdd.get(i).getNumber() - annualDiscard.get(i).getNumber();
            BigDecimal chajiDecimal = new BigDecimal(chaji);
            BigDecimal multiply2 = chajiDecimal.multiply(amount).multiply(BigDecimal.valueOf(12)).divide(BigDecimal.valueOf(3),2, RoundingMode.HALF_UP);
            res = multiply1.add(multiply2);
            resultList.get(i).setAmount(res);

        }
        System.out.println(resultList);

        IncomeReq incomeReq = new IncomeReq();
        incomeReq.setUnincludeTotal(resultList);

        return incomeReq;
    }
}
