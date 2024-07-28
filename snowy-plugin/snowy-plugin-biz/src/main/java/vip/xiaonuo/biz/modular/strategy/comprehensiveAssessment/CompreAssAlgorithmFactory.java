package vip.xiaonuo.biz.modular.strategy.comprehensiveAssessment;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import vip.xiaonuo.biz.core.config.CompreAssAlgorithmTypeConfig;
import vip.xiaonuo.biz.core.config.InvestAlgorithmTypeConfig;
import vip.xiaonuo.biz.modular.strategy.invest.InvestAlgorithm;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 操作策略的上下文环境类 工具类
 * 将策略整合起来 方便管理
 */
@Component
public class CompreAssAlgorithmFactory implements ApplicationContextAware {
    private static Map<String, CompreAssAlgorithm> compreAssAlgorithmPool  = new ConcurrentHashMap<String, CompreAssAlgorithm>();

    @Autowired
    private CompreAssAlgorithmTypeConfig compreAssAlgorithmTypeConfig;

    /**
     * 从配置文件中读取策略信息存储到map中，不同的模板的投入明细可能有不同的计算方式
     * {
     * InvestAmount=InvestAmountOfSubproject
     * TaxRate=InvestAmountPerTaxRate
     * Algorithm3=InvestAlgorithm3
     * }
     *
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        compreAssAlgorithmTypeConfig.getTypes().forEach((k, y) -> {
            compreAssAlgorithmPool.put(k, (CompreAssAlgorithm) applicationContext.getBean(y));
        });
    }

    /**
     * 对外提供获取具体策略
     *
     * @param algorithmType 投入明细表的计算方式，需要跟配置文件中匹配
     * @return 具体策略
     */
    public CompreAssAlgorithm getAlgorithm(String algorithmType) {
        CompreAssAlgorithm compreAssAlgorithm = compreAssAlgorithmPool.get(algorithmType);
        return compreAssAlgorithm;
    }
}
