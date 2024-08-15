package vip.xiaonuo.biz.modular.strategy.invest;

import vip.xiaonuo.biz.modular.strategy.dto.InvestReq;
import vip.xiaonuo.biz.modular.strategy.vo.InvestVO;

/**
 * 投入明细表计算接口
 * 抽象策略类
 */
public interface InvestAlgorithm {
    /**
     * 获取数据
     * @param data 投入明细表中手工录入的数据
     * 		0: 建造条数
     * 	    1: 单条造价
     * 		2: 投资金额（不含税）中的6%和9%税率投资额
     * 	          说明：子项目信息填报页面点击下一步，跳转到收入明细页面时为空，收入明细页面填写完6%和9%税率投资额（不含税）后有值
     * 	    3：不含税投资额合计（在收入明细页面进行计算时需要，数据来自于前端）
     * @return map值 返回的计算结果
     * 		0: 不含税投资额合计（跳转到收入明细页面时显示需要）
     * 	    1: 13%税率投资额（不含税）
     * 	    2: 6%，9%，13%税率投资额（含税）
     * 	    3: 含税投资额合计
     */
    InvestVO investResult(InvestReq data);
}
