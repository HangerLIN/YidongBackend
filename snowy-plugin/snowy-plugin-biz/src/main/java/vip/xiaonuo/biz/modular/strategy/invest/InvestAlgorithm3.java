package vip.xiaonuo.biz.modular.strategy.invest;

import org.springframework.stereotype.Component;
import vip.xiaonuo.biz.modular.strategy.dto.InvestReq;
import vip.xiaonuo.biz.modular.strategy.vo.InvestResp;

import java.util.HashMap;
import java.util.Map;

/**
 * 策略:计算方式1
 */
@Component(value = "InvestAlgorithm3")
public class InvestAlgorithm3 implements InvestAlgorithm{

    @Override
    public InvestResp investResult(InvestReq data) {
        System.out.println("策略：计算方式3");
        // TODO
        // 执行业务操作

        return new InvestResp();
    }
}