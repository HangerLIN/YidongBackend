package vip.xiaonuo.biz.modular.strategy.comprehensiveAssessment;

import vip.xiaonuo.biz.modular.strategy.dto.*;
import vip.xiaonuo.biz.modular.strategy.vo.CompreAssResp;
import vip.xiaonuo.biz.modular.strategy.vo.InvestResp;

public interface CompreAssAlgorithm {

    CompreAssResp compreAssResult(BasicInfoForCompreAssReq basicReq, InvestResp investResp, IncomeForCompreAssReq incomeReq,
                                  SpendForCompreAssReq spendReq, StreamForCompreAssReq streamReq);
}
