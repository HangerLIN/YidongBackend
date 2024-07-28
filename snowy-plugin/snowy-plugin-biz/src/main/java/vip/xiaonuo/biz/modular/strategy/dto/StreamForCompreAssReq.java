package vip.xiaonuo.biz.modular.strategy.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@Data
public class StreamForCompreAssReq {
    private BigDecimal approvedTaxRate;
    private BigDecimal annualNetCashFlowPv;
}
