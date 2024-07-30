package vip.xiaonuo.biz.modular.spend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import vip.xiaonuo.biz.modular.spend.entity.Annualspend;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@Data
public class SpendParam {

    private List<SubprojectSpendInfo> subprojectSpendInfoList;

    @NoArgsConstructor
    @Data
    public static class SubprojectSpendInfo {
        private String subprojectId;
        private List<Annualspend> spendSafeguard;
        private List<Annualspend> spendUpkeep; //后期维护
        private List<Annualspend> spendArtifical;
        private List<Annualspend> spendOther;
        private List<Annualspend> spendNoise;
        private List<Annualspend> spendPublish; //宣传推广
    }
}
