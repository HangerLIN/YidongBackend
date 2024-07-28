package vip.xiaonuo.biz.modular.strategy.dto;

import lombok.Data;
import vip.xiaonuo.biz.modular.strategy.utils.Pair;

import java.math.BigDecimal;
import java.util.List;

@Data
public class IncomeForCompreAssReq {
    private List<Pair<Integer, BigDecimal>> unincludeTotal;
}
