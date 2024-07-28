package vip.xiaonuo.biz.modular.strategy.spend;

import vip.xiaonuo.biz.modular.strategy.dto.SpendReq;

import java.math.BigDecimal;

public interface SpendAlgorithm {
    Integer spendResult(Integer productCode, SpendReq spendReq);
}
