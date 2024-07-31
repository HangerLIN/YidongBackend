package vip.xiaonuo.biz.modular.spend.convert;

import vip.xiaonuo.biz.modular.spend.dto.SpendParam;
import vip.xiaonuo.biz.modular.spend.vo.SpendVO;
import vip.xiaonuo.biz.modular.spend.entity.Annualspend;

import java.util.ArrayList;
import java.util.List;

public class SpendConvert {

    public static SpendVO.SubprojectSpendVO toSubprojectSpendVO(SpendParam.SubprojectSpendInfo subprojectSpendInfo) {
        SpendVO.SubprojectSpendVO subprojectSpendVO = new SpendVO.SubprojectSpendVO();
        subprojectSpendVO.setSubprojectId(subprojectSpendInfo.getSubprojectId());

        subprojectSpendVO.setSpendSafeguard(new SpendVO.SubprojectSpendVO.SpendSafeguard());
        subprojectSpendVO.getSpendSafeguard().setAnnual(new ArrayList<>());
        for (Annualspend annualSpend : subprojectSpendInfo.getSpendSafeguard()) {
            subprojectSpendVO.getSpendSafeguard().getAnnual().add(annualSpend);
        }

        subprojectSpendVO.setSpendUpkeep(new SpendVO.SubprojectSpendVO.SpendUpkeep());
        subprojectSpendVO.getSpendUpkeep().setAnnual(new ArrayList<>());
        for (Annualspend annualSpend : subprojectSpendInfo.getSpendUpkeep()) {
            subprojectSpendVO.getSpendUpkeep().getAnnual().add(annualSpend);
        }

        subprojectSpendVO.setSpendArtifical(new SpendVO.SubprojectSpendVO.SpendArtifical());
        subprojectSpendVO.getSpendArtifical().setAnnual(new ArrayList<>());
        for (Annualspend annualSpend : subprojectSpendInfo.getSpendArtifical()) {
            subprojectSpendVO.getSpendArtifical().getAnnual().add(annualSpend);
        }

        subprojectSpendVO.setSpendOther(new SpendVO.SubprojectSpendVO.SpendOther());
        subprojectSpendVO.getSpendOther().setAnnual(new ArrayList<>());
        for (Annualspend annualSpend : subprojectSpendInfo.getSpendOther()) {
            subprojectSpendVO.getSpendOther().getAnnual().add(annualSpend);
        }

        subprojectSpendVO.setSpendNoise(new SpendVO.SubprojectSpendVO.SpendNoise());
        subprojectSpendVO.getSpendNoise().setAnnual(new ArrayList<>());
        for (Annualspend annualSpend : subprojectSpendInfo.getSpendNoise()) {
            subprojectSpendVO.getSpendNoise().getAnnual().add(annualSpend);
        }

        subprojectSpendVO.setSpendPublicize(new SpendVO.SubprojectSpendVO.SpendPublicize());
        subprojectSpendVO.getSpendPublicize().setAnnual(new ArrayList<>());
        for (Annualspend annualSpend : subprojectSpendInfo.getSpendPublish()) {
            subprojectSpendVO.getSpendPublicize().getAnnual().add(annualSpend);
        }

        return subprojectSpendVO;
    }

    public static List<SpendVO.SubprojectSpendVO> toSubprojectSpendVOList(List<SpendParam.SubprojectSpendInfo> subprojectSpendInfoList) {
        List<SpendVO.SubprojectSpendVO> subprojectSpendVOList = new ArrayList<>();
        for (SpendParam.SubprojectSpendInfo subprojectSpendInfo : subprojectSpendInfoList) {
            subprojectSpendVOList.add(toSubprojectSpendVO(subprojectSpendInfo));
        }
        return subprojectSpendVOList;
    }
}
