package vip.xiaonuo.biz.modular.strategy.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import vip.xiaonuo.biz.modular.strategy.utils.Pair;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@Data
public class SpendForCompreAssReq {
    private List<Pair<Integer, BigDecimal>> spendSafeguard;

    private List<Pair<Integer, BigDecimal>> spendUpkeep;

    private List<Pair<Integer, BigDecimal>> spendArtificial;

    private List<Pair<Integer, BigDecimal>> spendOther;

    private List<Pair<Integer, BigDecimal>> spendNoise;

    private List<Pair<Integer, BigDecimal>> spendPublicize;



}
