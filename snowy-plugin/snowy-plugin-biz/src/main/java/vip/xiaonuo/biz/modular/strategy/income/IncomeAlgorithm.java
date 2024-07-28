package vip.xiaonuo.biz.modular.strategy.income;

import vip.xiaonuo.biz.modular.strategy.dto.IncomeReq;

public interface IncomeAlgorithm {
    /**
     * 获取数据
     * @param data 投入明细表中手工录入的数据
     * 		0: 每年新增投入道路条数
     * 	    1: 每年废弃道路条数
     * 		2: 单条售价（不含税）（MP009）
     * @return SpendResp 返回的计算结果
     * 		0: 年底投入使用的道路条数
     * 	    1: 收入（不含税）(MP010)
     * 	    2: 收入（含税）(MP011)
     */

    IncomeReq incomeResult(IncomeReq data);


}
