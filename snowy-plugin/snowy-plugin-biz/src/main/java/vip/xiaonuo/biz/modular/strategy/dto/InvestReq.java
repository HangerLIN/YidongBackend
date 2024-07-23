package vip.xiaonuo.biz.modular.strategy.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeansException;
import vip.xiaonuo.biz.modular.strategy.vo.InvestResp;
import vip.xiaonuo.biz.modular.subjectinfo.dto.SubProjcetParam;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@Data
public class InvestReq {

    /**
      type = InvestAmount 计算每个子项目每年的投资金额（不含税）和不含税投资额合计
      type = TaxRate  计算13%税率投资额（不含税），6%，9%，13%税率投资额（含税），含税投资额合计
     */
    private String type;
    private List<InvestResp.ProjectUnincludeTotal> projectUnincludeTotal;
    private List<ProjectUnincludeTaxRate6> projectUnincludeTaxRate6;
    private List<ProjectUnincludeTaxRate9> projectUnincludeTaxRate9;
    private List<SubProjectScheduleAndCost> subProjectScheduleAndCosts;

    @NoArgsConstructor
    @Data
    public static class ProjectUnincludeTaxRate6 {
        private Integer year;
        private BigDecimal amount;
    }

    @NoArgsConstructor
    @Data
    public static class ProjectUnincludeTaxRate9 {
        private Integer year;
        private BigDecimal amount;
    }

    @NoArgsConstructor
    @Data
    public static class SubProjectScheduleAndCost {
        private Long projectId;
        private List<Schedule> schedule;
        private List<Cost> cost;

        @NoArgsConstructor
        @Data
        public static class Schedule {
            private Integer year;
            private BigDecimal amount;
        }

        @NoArgsConstructor
        @Data
        public static class Cost {
            private Integer year;
            private BigDecimal amount;
        }

    }


}
