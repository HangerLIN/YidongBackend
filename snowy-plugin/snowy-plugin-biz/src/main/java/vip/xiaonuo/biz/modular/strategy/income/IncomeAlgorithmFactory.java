package vip.xiaonuo.biz.modular.strategy.income;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import vip.xiaonuo.biz.core.config.IncomeAlgorithmTypeConfig;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 操作策略的上下文环境类 工具类
 * 将策略整合起来 方便管理
 */
@Component
public class IncomeAlgorithmFactory implements ApplicationContextAware {
    private static final Map<String, IncomeAlgorithm> incomeAlgorithmPool  = new ConcurrentHashMap<String, IncomeAlgorithm>();

    @Autowired
    private IncomeAlgorithmTypeConfig incomeAlgorithmTypeConfig;

    /**
     * 从配置文件中读取策略信息存储到map中，不同的模板的投入明细可能有不同的计算方式
     * {
     * incomeAmount=incomeAmountOfSubproject
     * TaxRate=incomeAmountPerTaxRate
     * Algorithm3=incomeAlgorithm3
     * }
     *
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        incomeAlgorithmTypeConfig.getTypes().forEach((k, y) -> {
            incomeAlgorithmPool.put(k, (IncomeAlgorithm) applicationContext.getBean(y));
        });
    }

    /**
     * 对外提供获取具体策略
     *
     * @param algorithmType 投入明细表的计算方式，需要跟配置文件中匹配
     * @return 具体策略
     */
    public IncomeAlgorithm getAlgorithm(String algorithmType) {
        IncomeAlgorithm incomeAlgorithm = incomeAlgorithmPool.get(algorithmType);
        return incomeAlgorithm;
    }
}
