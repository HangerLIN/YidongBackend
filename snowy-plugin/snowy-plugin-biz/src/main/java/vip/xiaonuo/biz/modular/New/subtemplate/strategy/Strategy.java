package vip.xiaonuo.biz.modular.New.subtemplate.strategy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface Strategy {
    List<List<BigDecimal>> calculateMethod(List<List<BigDecimal>> a, List<List<BigDecimal>> b,
                                              Map<Long, List<List<BigDecimal>>> subtemplateIdToStartYearData, Long subtemplateId);
}
