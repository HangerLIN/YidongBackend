package vip.xiaonuo.biz.modular.income.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@Data
public class IncomeVO  implements Serializable {

    private List<SubprojectIncomeVO> subprojectIncomeVO;
    private UnincludeSum unincludeSum;
    private IncludeSum includeSum;

    @NoArgsConstructor
    @Data
    public static class UnincludeSum {
        private List<Annual> annual;
        private BigDecimal sum;

    }

    @NoArgsConstructor
    @Data
    public static class IncludeSum {
        private List<Annual> annual;
        private BigDecimal sum;

    }

    @NoArgsConstructor
    @Data
    public static class Annual {
        private Integer year;
        private BigDecimal amount;
    }

    @NoArgsConstructor
    @Data
    public static class SubprojectIncomeVO {
        private String subprojectId;
        private List<AnnualAdd> annualAdd;
        private List<AnnualAdd> annualDiscard;
        private List<AnnualAdd> yearEndUse;
        private UnincludeTotal unincludeTotal;
        private IncludeTaxRate6 includeTaxRate6;

        @NoArgsConstructor
        @Data
        public static class UnincludeTotal {
            private List<Annual> annual;
            private BigDecimal sum;

        }

        @NoArgsConstructor
        @Data
        public static class IncludeTaxRate6 {
            private List<Annual> annual;
            private BigDecimal sum;

        }

        @NoArgsConstructor
        @Data
        public static class AnnualAdd {
            private Integer year;
            private Integer number;
        }
    }
}
