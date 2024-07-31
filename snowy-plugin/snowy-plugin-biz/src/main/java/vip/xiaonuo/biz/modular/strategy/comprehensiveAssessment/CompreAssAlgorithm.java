package vip.xiaonuo.biz.modular.strategy.comprehensiveAssessment;

import vip.xiaonuo.biz.modular.income.vo.IncomeVO;
import vip.xiaonuo.biz.modular.spend.vo.SpendVO;
import vip.xiaonuo.biz.modular.strategy.dto.*;
import vip.xiaonuo.biz.modular.strategy.vo.CompreAssVO;
import vip.xiaonuo.biz.modular.strategy.vo.InvestVO;

public interface CompreAssAlgorithm {

    CompreAssVO compreAssResult(BasicInfoForCompreAssReq basicVO, InvestVO investVO, IncomeVO incomeVO,
                                SpendVO spendVO, StreamForCompreAssReq streamVO);
}
