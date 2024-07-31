package vip.xiaonuo.biz.modular.spend.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vip.xiaonuo.biz.modular.spend.entity.Annualspend;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
@Data
public class SpendVO {

    private List<SubprojectSpendVO> subprojectSpendVOList;

    private Map<String, Map<Integer, BigDecimal>> annualSumsByTypeAndYear;

    private BigDecimal totalSumWithoutR = BigDecimal.ZERO;

    private BigDecimal totalSumWithR = BigDecimal.ZERO;

    @NoArgsConstructor
    @Data
    public static class SubprojectSpendVO {
        private String subprojectId;
        private SpendSafeguard spendSafeguard;
        private SpendUpkeep spendUpkeep;
        private SpendArtifical spendArtifical;
        private SpendOther spendOther;
        private SpendNoise spendNoise;
        private SpendPublicize spendPublicize;
        private SpendSafeguardR9 spendSafeguardR9;
        private SpendUpkeepR6 spendUpkeepR6;
        private SpendArtificalR9 spendArtificalR9;
        private SpendOtherR9 spendOtherR9;
        private SpendNoiseR6 spendNoiseR6;
        private SpendPublicizeR6 spendPublicizeR6;
        
        @NoArgsConstructor
        @Data
        public static class SpendSafeguard {
            private List<Annualspend> annual;
            private BigDecimal sum;
        }

        @NoArgsConstructor
        @Data
        public static class SpendUpkeep {
            private List<Annualspend> annual;
            private BigDecimal sum;
            
        }

        @NoArgsConstructor
        @Data
        public static class SpendArtifical {
            private List<Annualspend> annual;
            private BigDecimal sum;
            
        }

        @NoArgsConstructor
        @Data
        public static class SpendOther {
            private List<Annualspend> annual;
            private BigDecimal sum;
            
        }

        @NoArgsConstructor
        @Data
        public static class SpendNoise {
            private List<Annualspend> annual;
            private BigDecimal sum;
            
        }

        @NoArgsConstructor
        @Data
        public static class SpendPublicize {
            private List<Annualspend> annual;
            private BigDecimal sum;
            
        }

        @NoArgsConstructor
        @Data
        public static class SpendSafeguardR9 {
            private List<Annualspend> annual;
            private BigDecimal sum;
            
        }

        @NoArgsConstructor
        @Data
        public static class SpendUpkeepR6 {
            private List<Annualspend> annual;
            private BigDecimal sum;

        }

        @NoArgsConstructor
        @Data
        public static class SpendArtificalR9 {
            private List<Annualspend> annual;
            private BigDecimal sum;
            
        }

        @NoArgsConstructor
        @Data
        public static class SpendOtherR9 {
            private List<Annualspend> annual;
            private BigDecimal sum;
            
        }

        @NoArgsConstructor
        @Data
        public static class SpendNoiseR6 {
            private List<Annualspend> annual;
            private BigDecimal sum;
            
        }

        @NoArgsConstructor
        @Data
        public static class SpendPublicizeR6 {
            private List<Annualspend> annual;
            private BigDecimal sum;
            
        }
    }
}
