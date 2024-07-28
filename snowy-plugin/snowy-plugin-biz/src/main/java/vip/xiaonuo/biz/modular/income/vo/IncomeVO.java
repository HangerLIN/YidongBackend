package vip.xiaonuo.biz.modular.income.vo;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
public class IncomeVO  implements Serializable {

    private List<SubprojectIncomeVO> subprojectIncomeVO;
    private UnincludeSum unincludeSum;
    private IncludeSum includeSum;

    public UnincludeSum getUnincludeSum() {
        if (unincludeSum == null) {
            unincludeSum = new UnincludeSum(); // 确保进行初始化
        }
        return unincludeSum;
    }

    public IncludeSum getIncludeSum() {
        if (includeSum == null) {
            includeSum = new IncludeSum(); // 确保进行初始化
        }
        return includeSum;
    }

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

        // Copy constructor
        public Annual(Annual other) {
            this.year = other.year;
            this.amount = other.amount;
        }

        public Annual(int year, BigDecimal amount) {
            this.year = year;
            this.amount = amount;
        }
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

        public List<AnnualAdd> getAnnualAdd() {
            if (annualAdd == null) {
                annualAdd = new ArrayList<>(); // 确保进行初始化
            }
            return annualAdd;
        }

        public UnincludeTotal getUnincludeTotal() {
            if (unincludeTotal == null) {
                unincludeTotal = new UnincludeTotal(); // 确保进行初始化
            }
            return unincludeTotal;
        }

        public IncludeTaxRate6 getIncludeTaxRate6() {
            if (includeTaxRate6 == null) {
                includeTaxRate6 = new IncludeTaxRate6(); // 确保进行初始化
            }
            return includeTaxRate6;
        }

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
