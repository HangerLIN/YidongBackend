package vip.xiaonuo.biz.modular.spend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import vip.xiaonuo.biz.modular.spend.dto.SpendParam;
import vip.xiaonuo.biz.modular.spend.dto.SpendParam.SubprojectSpendInfo;
import vip.xiaonuo.biz.modular.spend.dto.SpendParam.SubprojectSpendInfo.Annualspend;
import vip.xiaonuo.biz.modular.spend.entity.Spend;
import vip.xiaonuo.biz.modular.spend.service.SpendService;
import vip.xiaonuo.biz.modular.spend.mapper.SpendMapper;
import org.springframework.stereotype.Service;
import vip.xiaonuo.biz.modular.spend.vo.SpendVO;

import java.util.List;

/**
 * @author M
 * @description 针对表【spend】的数据库操作Service实现
 * @createDate 2024-07-26 11:09:30
 */
@Service
public class SpendServiceImpl extends ServiceImpl<SpendMapper, Spend>
        implements SpendService {

    @Override
    public boolean saveSubjectSpendInfo(SpendParam spendParam) throws Exception {
        List<SubprojectSpendInfo> subprojectSpendInfoList = spendParam.getSubprojectSpendInfoList();
        for (SubprojectSpendInfo subprojectSpendInfo : subprojectSpendInfoList) {
            // 1. 检验子项目信息是否需要策略计算
            List<Annualspend> spendUpkeep = subprojectSpendInfo.getSpendUpkeep();// 保养
            List<Annualspend> spendSafeguard = subprojectSpendInfo.getSpendSafeguard();// 维护
            List<Annualspend> spendOther = subprojectSpendInfo.getSpendOther();// 其他
            List<Annualspend> spendArtifical = subprojectSpendInfo.getSpendArtifical();// 人工

            if (spendUpkeep == null || spendSafeguard == null || spendOther == null || spendArtifical == null) {
                if (spendUpkeep == null && spendSafeguard == null && spendOther == null && spendArtifical == null) {
                    // 调用策略类计算

                } else {
                    throw new Exception("输入数据异常！");
                }
            }

            // 数据为手动输入形式,直接存入数据库
            Spend spend = new Spend();
            String subprojectId = subprojectSpendInfo.getSubprojectId();
            spend.setSpendId(Long.valueOf(subprojectId));
            // 下面步骤省略了

        }
        return true;
    }

    @Override
    public SpendVO caculateSpendInfo(SpendParam spendParam) {
        return null;
    }
}




