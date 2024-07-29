package vip.xiaonuo.biz.modular.spend.service;

import vip.xiaonuo.biz.modular.spend.dto.SpendParam;
import vip.xiaonuo.biz.modular.spend.entity.Spend;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.spend.vo.SpendVO;

/**
* @author M
* @description 针对表【spend】的数据库操作Service
* @createDate 2024-07-26 11:09:30
*/
public interface SpendService extends IService<Spend> {

    boolean saveSubjectSpendInfo(SpendParam spendParam) throws Exception;

    boolean saveSpendInfo(SpendVO spendVO) throws Exception;

}
