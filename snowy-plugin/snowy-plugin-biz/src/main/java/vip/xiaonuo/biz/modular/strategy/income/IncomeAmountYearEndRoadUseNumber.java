package vip.xiaonuo.biz.modular.strategy.income;

import org.springframework.stereotype.Component;
import vip.xiaonuo.biz.modular.income.dto.IncomeParam.SubprojectIncome.AnnualInfo;
import vip.xiaonuo.biz.modular.strategy.dto.IncomeReq;

import java.util.ArrayList;
import java.util.List;

@Component(value = "IncomeAmountYearEndRoadUseNumber")
public class IncomeAmountYearEndRoadUseNumber implements IncomeAlgorithm{
    @Override
    public IncomeReq incomeResult(IncomeReq data) {
        List<AnnualInfo> annualAdd = data.getAnnualAdd();
        List<AnnualInfo> annualDiscard = data.getAnnualDiscard();

        // 将结果转换回 List 并按年份排序
        List<AnnualInfo> resultList = new ArrayList<>();
        for (AnnualInfo addInfo: annualAdd){
            resultList.add(new AnnualInfo(addInfo));
        }

        // 初始化第一个元素
        Integer numberAdd = annualAdd.get(0).getNumber();
        Integer numberDiscard = annualDiscard.get(0).getNumber();
        resultList.get(0).setNumber(numberAdd - numberDiscard);
        System.out.println(resultList);

        // 设置其他年份数据
        int size = annualAdd.size();
        for (int i = 1; i < size; i++){
            numberAdd = annualAdd.get(i).getNumber();
            numberDiscard = annualDiscard.get(i).getNumber();
            resultList.get(i).setNumber(resultList.get(i-1).getNumber() + numberAdd - numberDiscard);
        }
        IncomeReq incomeReq = new IncomeReq();
        incomeReq.setYearEndUse(resultList);

        return incomeReq;
    }
}
