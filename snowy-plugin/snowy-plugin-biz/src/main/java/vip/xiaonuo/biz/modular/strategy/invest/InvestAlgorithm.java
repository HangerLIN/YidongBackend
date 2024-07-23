package vip.xiaonuo.biz.modular.strategy.invest;

import vip.xiaonuo.biz.modular.strategy.dto.InvestReq;
import vip.xiaonuo.biz.modular.strategy.vo.InvestResp;

import java.util.Map;

/**
 * 投入明细表计算接口
 * 抽象策略类
 */
public interface InvestAlgorithm {
    /**
     * 获取数据
     * @param data 投入明细表中手工录入的数据
     *      可能有以下内容（不固定）：
     * 		0: 建造条数
     * 	    1: 单条造价
     * 		2: 投资金额（不含税）中的6%和9%税率投资额——刚开始可能为空
     * @return map值 返回的计算结果
     *      可能有以下内容（不固定）：
     * 		0: 每个子项目的投资金额（不含税）
     * 	    1: 13%税率投资额（不含税）
     * 	    2: 6%，9%，13%税率投资额（含税）
     * 	    3: 含税投资额合计
     */
    InvestResp investResult(InvestReq data);
}
