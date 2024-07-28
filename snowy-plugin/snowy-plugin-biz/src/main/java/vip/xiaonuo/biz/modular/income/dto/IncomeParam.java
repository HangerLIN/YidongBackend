package vip.xiaonuo.biz.modular.income.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class IncomeParam {

    private List<SubprojectIncome> subprojectIncome;

    @NoArgsConstructor
    @Data
    public static class SubprojectIncome {
        private String subprojectId;
        private List<AnnualInfo> annualAdd;
        private List<AnnualInfo> annualDiscard;

        @NoArgsConstructor
        @Data
        public static class AnnualInfo {
            private Integer year;
            private Integer number;

            // Copy constructor
            public AnnualInfo(AnnualInfo other) {
                this.year = other.year;
                this.number = other.number;
            }
        }


    }
}
