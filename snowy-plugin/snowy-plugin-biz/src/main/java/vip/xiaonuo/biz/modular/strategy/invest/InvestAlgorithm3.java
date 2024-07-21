package vip.xiaonuo.biz.modular.strategy.invest;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 策略:计算方式1
 */
@Component
public class InvestAlgorithm3 implements InvestAlgorithm{

    @Override
    public Map<String, Object> investResult(Map<String, Object> data) {
        System.out.println("策略：计算方式3");
        return new HashMap<String, Object>(){{}};
    }
}