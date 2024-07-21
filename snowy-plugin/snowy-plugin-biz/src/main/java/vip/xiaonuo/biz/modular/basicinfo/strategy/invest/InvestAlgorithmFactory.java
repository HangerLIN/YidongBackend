package vip.xiaonuo.biz.modular.basicinfo.strategy.invest;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import vip.xiaonuo.biz.modular.basicinfo.config.InvestAlgorithmTypeConfig;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 操作策略的上下文环境类 工具类
 * 将策略整合起来 方便管理
 */
@Component
public class InvestAlgorithmFactory implements ApplicationContextAware {
    private static Map<String, InvestAlgorithm> investAlgorithmPool  = new ConcurrentHashMap<String, InvestAlgorithm>();

    @Autowired
    private InvestAlgorithmTypeConfig investAlgorithmTypeConfig;

    /**
     * 从配置文件中读取策略信息存储到map中，不同的模板的投入明细可能有不同的计算方式，先简单命名为InvestAlgorithm1,InvestAlgorithm2,InvestAlgorithm3
     * {
     * Algorithm1:InvestAlgorithm1,
     * Algorithm2:InvestAlgorithm2,
     * Algorithm3:InvestAlgorithm3
     * }
     *
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        investAlgorithmTypeConfig.getTypes().forEach((k, y) -> {
            investAlgorithmPool.put(k, (InvestAlgorithm) applicationContext.getBean(y));
        });
    }

    /**
     * 对外提供获取具体策略
     *
     * @param algorithmType 投入明细表的计算方式，需要跟配置文件中匹配
     * @return 具体策略
     */
    public InvestAlgorithm getAlgorithm(String algorithmType) {
        InvestAlgorithm investAlgorithm = investAlgorithmPool.get(algorithmType);
        return investAlgorithm;
    }
}
