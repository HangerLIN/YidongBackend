package vip.xiaonuo.biz.modular.spend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Annualspend {
    private Integer year;
    private BigDecimal amount;
}