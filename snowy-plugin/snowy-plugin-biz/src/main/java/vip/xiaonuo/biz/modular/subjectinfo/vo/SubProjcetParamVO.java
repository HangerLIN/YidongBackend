package vip.xiaonuo.biz.modular.subjectinfo.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@Data
public class SubProjcetParamVO {


    private List<SubprojectInfo> subproject;

    @NoArgsConstructor
    @Data
    public static class SubprojectInfo {
        private Long subprojectId;
        private Long projectId;
        private List<BasicInformation> basicInformation;
        private List<SubProjectSchedule> subprojectSchedule;
        private List<SubProjectSingleCost> subprojectSinglecost;
        private List<SubProjectSinglePrice> subprojectSingleprice;

        @NoArgsConstructor
        @Data
        public static class BasicInformation {
            private String key;
            private String value;
        }

        @NoArgsConstructor
        @Data
        public static class SubProjectSchedule {
            private Integer year;
            private BigDecimal amount;
        }

        @NoArgsConstructor
        @Data
        public static class SubProjectSingleCost {
            private Integer year;
            private BigDecimal amount;
        }

        @NoArgsConstructor
        @Data
        public static class SubProjectSinglePrice {
            private Integer year;
            private BigDecimal amount;
        }
    }
}
