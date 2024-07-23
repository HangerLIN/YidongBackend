package vip.xiaonuo.biz.modular.strategy.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@Data
public class InvestResp {

    private Long projectId;
    private List<SubProjectInvest> subProjectInvest;

    private List<ProjectUnincludeTotal> projectUnincludeTotal;
    private List<ProjectUnincludeTaxRate13> projectUnincludeTaxRate13;
    private List<ProjectIncludeTaxRate6> projectIncludeTaxRate6;
    private List<ProjectIncludeTaxRate9> projectIncludeTaxRate9;
    private List<ProjectIncludeTaxRate13> projectIncludeTaxRate13;
    private List<ProjectIncludeTotal> projectIncludeTotal;

    @NoArgsConstructor
    @Data
    public static class ProjectUnincludeTotal {
        private Integer year;
        private BigDecimal amount;
    }

    @NoArgsConstructor
    @Data
    public static class ProjectUnincludeTaxRate13 {
        private Integer year;
        private BigDecimal amount;
    }

    @NoArgsConstructor
    @Data
    public static class ProjectIncludeTaxRate6 {
        private Integer year;
        private BigDecimal amount;
    }

    @NoArgsConstructor
    @Data
    public static class ProjectIncludeTaxRate9{
        private Integer year;
        private BigDecimal amount;
    }

    @NoArgsConstructor
    @Data
    public static class ProjectIncludeTaxRate13 {
        private Integer year;
        private BigDecimal amount;
    }

    @NoArgsConstructor
    @Data
    public static class ProjectIncludeTotal {
        private Integer year;
        private BigDecimal amount;
    }

    @NoArgsConstructor
    @Data
    public static class SubProjectInvest {
        private List<SubProjectInvestAmount> subProjectInvestAmount;

        @NoArgsConstructor
        @Data
        public static class SubProjectInvestAmount {
            private Integer year;
            private BigDecimal amount;
        }
    }
}
