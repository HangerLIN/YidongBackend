package vip.xiaonuo.biz.modular.spend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

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
        private List<Annualspend> spendUpkeep;
        private List<Annualspend> spendArtifical;
        private List<Annualspend> spendOther;
        private List<Annualspend> spendNoise;

        @NoArgsConstructor
        @Data
        public static class Annualspend {
            private Integer year;
            private BigDecimal amount;
        }
    }
}