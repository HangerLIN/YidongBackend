package vip.xiaonuo.biz.modular.strategy.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import vip.xiaonuo.biz.modular.strategy.utils.Pair;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@Data
public class CompreAssResp {
    private BigDecimal approvalCost; //立项支出
    private BigDecimal otherCost; //其他支出
    private BigDecimal directIncome; //直接收入
    private BigDecimal indirectIncome; //间接收入
    private BigDecimal annualAvgProfitRate; //年度平均利润率
    private BigDecimal roi; // 投资回报率
    private BigDecimal paybackPeriod; //投资回收期
    private BigDecimal cumulativeNPV; //累计净现值

    private List<Pair<Integer, BigDecimal>> annualInvest;  //每年详细投入
    private List<Pair<Integer, BigDecimal>> annualProduce;  //每年详细产出
    private List<Pair<Integer, BigDecimal>> annualProfit; //每年详细利润
    private List<Pair<Integer, BigDecimal>> annualProfitNet; //每年详细净利润


}
