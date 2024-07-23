package vip.xiaonuo.biz.modular.income.service;

import vip.xiaonuo.biz.modular.income.dto.IncomeParam;
import vip.xiaonuo.biz.modular.income.entity.Income;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.income.vo.IncomeVO;

import java.util.List;

/**
* @author admin
* @description 针对表【income】的数据库操作Service
* @createDate 2024-07-22 15:19:10
*/
public interface IncomeService extends IService<Income> {

    boolean writeSubProjetIncomeInfo(IncomeParam incomeParam);

    IncomeVO returnSubProjetIncomeInfo(List<String> incomeID);

}
