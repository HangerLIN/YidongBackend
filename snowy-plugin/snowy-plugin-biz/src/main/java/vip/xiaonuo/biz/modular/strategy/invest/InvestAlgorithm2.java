package vip.xiaonuo.biz.modular.strategy.invest;

import org.springframework.stereotype.Component;
import vip.xiaonuo.biz.modular.strategy.dto.InvestReq;
import vip.xiaonuo.biz.modular.strategy.vo.InvestResp;

import java.util.HashMap;
import java.util.Map;

/**
 * 策略:计算方式1
 */
@Component(value = "InvestAlgorithm2")
public class InvestAlgorithm2 implements InvestAlgorithm{

    @Override
    public InvestResp investResult(InvestReq data) {
        System.out.println("策略：计算方式2");
        // TODO
        // 执行业务操作

        return new InvestResp();
    }
}
