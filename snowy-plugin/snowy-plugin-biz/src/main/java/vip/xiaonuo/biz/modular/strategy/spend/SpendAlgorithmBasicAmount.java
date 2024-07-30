package vip.xiaonuo.biz.modular.strategy.spend;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import vip.xiaonuo.biz.modular.income.vo.IncomeVO;
import vip.xiaonuo.biz.modular.spend.entity.Benchmark;
import vip.xiaonuo.biz.modular.strategy.dto.SpendReq;

import java.math.BigDecimal;

/**
 * @author lth
 * @version 1.0
 * @description TODO
 * @date 2024/7/28 下午4:41
 */
public class SpendAlgorithmBasicAmount implements SpendAlgorithm{
    @Override
    public Integer spendResult(Integer productCode, SpendReq spendReq) {
        LambdaQueryWrapper<Benchmark> benchmarkLambdaQueryWrapper = new LambdaQueryWrapper<>();
        benchmarkLambdaQueryWrapper.eq(Benchmark::getProductCode, productCode);
//      Benchmark benchmark = benchmarkService.getOne(benchmarkLambdaQueryWrapper); //TODO
        Benchmark benchmark = new Benchmark(); //TODO:后续需要调用service
        return null;
    }
}
