package vip.xiaonuo.biz.modular.income.service;

import vip.xiaonuo.biz.modular.income.dto.IncomeParam;
import vip.xiaonuo.biz.modular.income.entity.Income;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.income.vo.IncomeVO;
import vip.xiaonuo.biz.modular.strategy.dto.IncomeReq;

import java.util.List;

/**
* @author admin
* @description 针对表【income】的数据库操作Service
* @createDate 2024-07-22 15:19:10
*/
public interface IncomeService extends IService<Income> {

    List<Income> writeSubProjetIncomeInfo(IncomeParam incomeParam) throws Exception;

    IncomeVO returnSubProjetIncomeInfo(List<Income> incomeIDList);

    IncomeReq caculateSubProjetOtherIncomeInfo(IncomeParam.SubprojectIncome subprojectIncome) throws Exception;

}
