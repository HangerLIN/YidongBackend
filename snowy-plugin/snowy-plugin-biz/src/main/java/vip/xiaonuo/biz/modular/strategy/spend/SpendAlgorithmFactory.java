//package vip.xiaonuo.biz.modular.strategy.spend;
//
//import org.springframework.beans.BeansException;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.ApplicationContextAware;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import vip.xiaonuo.biz.core.config.SpendAlgorithmTypeConfig;
//
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
///**
// * 操作策略的上下文环境类 工具类
// * 将策略整合起来 方便管理
// */
//@Component
//public class SpendAlgorithmFactory implements ApplicationContextAware {
//    private static final Map<String, SpendAlgorithm> spendAlgorithmPool  = new ConcurrentHashMap<>();
//
//    @Autowired
//    private SpendAlgorithmTypeConfig spendAlgorithmTypeConfig;
//
//    /**
//     * 从配置文件中读取策略信息存储到map中，不同的模板的支出明细可能有不同的计算方式
//     * {
//     * spendAmount=spendAmountOfSubproject
//     * TaxRate=spendAmountPerTaxRate
//     * Algorithm3=spendAlgorithm3
//     * }
//     *
//     * @param applicationContext
//     * @throws BeansException
//     */
//    @Override
//    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//        Map<String, String> types = spendAlgorithmTypeConfig.getTypes();
//        if (types != null) {
//            types.forEach((k, y) -> {
//                spendAlgorithmPool.put(k, (SpendAlgorithm) applicationContext.getBean(y));
//            });
//        } else {
//            throw new IllegalStateException("Types configuration is missing in SpendAlgorithmTypeConfig");
//        }
//    }
//
//
//    /**
//     * 对外提供获取具体策略
//     *
//     * @param algorithmType 支出明细表的计算方式，需要跟配置文件中匹配
//     * @return 具体策略
//     */
//    public SpendAlgorithm getAlgorithm(String algorithmType) {
//        SpendAlgorithm spendAlgorithm = spendAlgorithmPool.get(algorithmType);
//        return spendAlgorithm;
//    }
//}
