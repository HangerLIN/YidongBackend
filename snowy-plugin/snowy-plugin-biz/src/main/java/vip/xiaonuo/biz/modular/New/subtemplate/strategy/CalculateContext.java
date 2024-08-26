package vip.xiaonuo.biz.modular.New.subtemplate.strategy;

import java.math.BigDecimal;
import java.util.List;

import static com.alibaba.druid.sql.ast.SQLPartitionValue.Operator.List;
import static com.fasterxml.jackson.databind.type.LogicalType.Map;

public class CalculateContext {
    private Strategy strategy;

    public CalculateContext(Strategy strategy) {
        this.strategy = strategy;
    }

    public List<List<BigDecimal>> executeStrategy(List<List<BigDecimal>> a, List<List<BigDecimal>> b,
                                java.util.Map<Long, List<List<BigDecimal>>> subtemplateIdToStartYearData, Long subtemplateId) {
        return strategy.calculateMethod(a, b, subtemplateIdToStartYearData, subtemplateId);
    }
}
