package vip.xiaonuo.biz.modular.income.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import vip.xiaonuo.biz.modular.income.dto.IncomeParam;
import vip.xiaonuo.biz.modular.income.dto.IncomeParam.SubprojectIncome;
import vip.xiaonuo.biz.modular.income.entity.Income;
import vip.xiaonuo.biz.modular.income.mapper.IncomeMapper;
import vip.xiaonuo.biz.modular.income.service.IncomeService;
import vip.xiaonuo.biz.modular.income.vo.IncomeVO;
import vip.xiaonuo.biz.modular.income.vo.IncomeVO.Annual;
import vip.xiaonuo.biz.modular.income.vo.IncomeVO.SubprojectIncomeVO;
import vip.xiaonuo.biz.modular.income.vo.IncomeVO.SubprojectIncomeVO.AnnualAdd;
import vip.xiaonuo.biz.modular.income.vo.IncomeVO.SubprojectIncomeVO.IncludeTaxRate6;
import vip.xiaonuo.biz.modular.income.vo.IncomeVO.SubprojectIncomeVO.UnincludeTotal;
import vip.xiaonuo.biz.modular.strategy.dto.IncomeReq;
import vip.xiaonuo.biz.modular.strategy.income.IncomeAlgorithm;
import vip.xiaonuo.biz.modular.strategy.income.IncomeAlgorithmFactory;
import vip.xiaonuo.biz.modular.subjectinfo.mapper.SubprojectMapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static vip.xiaonuo.biz.modular.income.dto.IncomeParam.SubprojectIncome.AnnualInfo;

/**
 * @author admin
 * @description 针对表【income】的数据库操作Service实现
 * @createDate 2024-07-22 15:19:10
 */
@Service
public class IncomeServiceImpl extends ServiceImpl<IncomeMapper, Income>
        implements IncomeService {

    @Resource
    private IncomeMapper incomeMapper;

    @Resource
    private SubprojectMapper subprojectMapper;

    @Resource
    private IncomeAlgorithmFactory factory;

    @Override
    public List<Income> writeSubProjetIncomeInfo(IncomeParam incomeParam) throws Exception {
        // 获取子项目收益列表
        List<SubprojectIncome> subprojectIncomeList = incomeParam.getSubprojectIncome();

        //创建返回值列表
        List<Income> IncomeList = new ArrayList<>();

        // 遍历列表，将每个字项目添加至数据库
        for (SubprojectIncome subprojectIncome : subprojectIncomeList) {
            String subprojectId = subprojectIncome.getSubprojectId();
            List<AnnualInfo> annualAdd = subprojectIncome.getAnnualAdd();
            List<AnnualInfo> annualDiscard = subprojectIncome.getAnnualDiscard();

            String annualAdd_jsonStr = JSONUtil.toJsonStr(annualAdd);
            String annualDiscard_jsonStr = JSONUtil.toJsonStr(annualDiscard);

            Income income = new Income();
            income.setSubprojectId(Long.valueOf(subprojectId));
            income.setAnnualDiscard(annualDiscard_jsonStr);
            income.setAnnualAdd(annualAdd_jsonStr);


            // 剩下字段需调用策略类计算，存入数据库
            IncomeReq rep = caculateSubProjetOtherIncomeInfo(subprojectIncome);
            List<AnnualInfo> yearEndUse = rep.getYearEndUse();
            List<Annual> unincludeTotal = rep.getUnincludeTotal();
            List<Annual> includeTaxRate6 = rep.getIncludeTaxRate6();

            String yearEndUse_jsonStr = JSONUtil.toJsonStr(yearEndUse);
            String unincludeTotal_jsonStr = JSONUtil.toJsonStr(unincludeTotal);
            String includeTaxRate6_jsonStr = JSONUtil.toJsonStr(includeTaxRate6);

            income.setYearendUse(yearEndUse_jsonStr);
            income.setUnincludeTotal(unincludeTotal_jsonStr);
            income.setIncludeTaxrate6(includeTaxRate6_jsonStr);

            // 存入数据库
//            incomeMapper.insert(income);

            IncomeList.add(income);


        }

        return IncomeList;
    }

    @Override
    public IncomeVO returnSubProjetIncomeInfo(List<Income> incomeIDList) {

        IncomeVO incomeVO = new IncomeVO();

        ArrayList<SubprojectIncomeVO> IncomeVOList = new ArrayList<>();

        // 每年合计情况 （含税）
        List<UnincludeTotal> unincludeTotalList = new ArrayList<>();
        // 每年合计情况 （不含税）
        List<IncludeTaxRate6> includeTaxRate6List = new ArrayList<>();


        for (Income income : incomeIDList) {
            SubprojectIncomeVO subprojectIncomeVO = new SubprojectIncomeVO();
            Long incomeId = income.getSubprojectId();
            // 获取每年新增道路数量
            String annualAddstr = income.getAnnualAdd();
            List<AnnualAdd> annualAdd = JSONUtil.toList(annualAddstr, AnnualAdd.class);
            // 获取每年废弃道路数量
            String annualDiscardstr = income.getAnnualDiscard();
            List<AnnualAdd> annualDiscard = JSONUtil.toList(annualDiscardstr, AnnualAdd.class);
            // 获取年底使用
            String yearendUsestr = income.getYearendUse();
            List<AnnualAdd> yearendUse = JSONUtil.toList(yearendUsestr, AnnualAdd.class);
            // 获取不含税合计
            String unincludeTotalstr = income.getUnincludeTotal();
            List<Annual> unincludeTotal = JSONUtil.toList(unincludeTotalstr, Annual.class);
            // 获取含税合计
            String includeTaxrate6str = income.getIncludeTaxrate6();
            List<Annual> includeTaxrate6 = JSONUtil.toList(includeTaxrate6str, Annual.class);

            subprojectIncomeVO.setSubprojectId(String.valueOf(incomeId));
            subprojectIncomeVO.setAnnualAdd(annualAdd);
            subprojectIncomeVO.setAnnualDiscard(annualDiscard);
            subprojectIncomeVO.setYearEndUse(yearendUse);
            subprojectIncomeVO.getUnincludeTotal().setAnnual(unincludeTotal);
            subprojectIncomeVO.getIncludeTaxRate6().setAnnual(includeTaxrate6);
            IncomeReq incomeReq = new IncomeReq();
            incomeReq.setUnincludeTotal(unincludeTotal);
            incomeReq.setIncludeTaxRate6(includeTaxrate6);

            IncomeAlgorithm Algorithm = factory.getAlgorithm("Sum");
            IncomeReq res = Algorithm.incomeResult(incomeReq);
            subprojectIncomeVO.getUnincludeTotal().setSum(res.getUnincludeTotalSum());
            subprojectIncomeVO.getIncludeTaxRate6().setSum(res.getIncludeTaxRate6Sum());

            unincludeTotalList.add(subprojectIncomeVO.getUnincludeTotal());
            includeTaxRate6List.add(subprojectIncomeVO.getIncludeTaxRate6());

            IncomeVOList.add(subprojectIncomeVO);
        }
        // 用于存储每年累计的金额
        Map<Integer, BigDecimal> UnyearAmountMap = new HashMap<>();

        Map<Integer, BigDecimal> InyearAmountMap = new HashMap<>();

        for (UnincludeTotal unincludeTotal : unincludeTotalList) {
            for (Annual annual : unincludeTotal.getAnnual()) {
                Integer year = annual.getYear();
                BigDecimal amount = annual.getAmount();
                UnyearAmountMap.put(year, UnyearAmountMap.getOrDefault(year, BigDecimal.ZERO).add(amount));
            }
        }

        BigDecimal unincludeTotalSum = UnyearAmountMap.values()
                .stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // 将 yearAmountMap 转换为 List<Annual>
        List<Annual> unresultList = new ArrayList<>();
        for (Map.Entry<Integer, BigDecimal> entry : UnyearAmountMap.entrySet()) {
            int year = entry.getKey();
            BigDecimal amount = entry.getValue();
            unresultList.add(new Annual(year, amount));
        }


        for (IncludeTaxRate6 includeTaxRate6 : includeTaxRate6List) {
            for (Annual annual : includeTaxRate6.getAnnual()) {
                Integer year = annual.getYear();
                BigDecimal amount = annual.getAmount();
                InyearAmountMap.put(year, InyearAmountMap.getOrDefault(year, BigDecimal.ZERO).add(amount));
            }
        }

        BigDecimal includeTotalSum = InyearAmountMap.values()
                .stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        List<Annual> inresultList = new ArrayList<>();
        for (Map.Entry<Integer, BigDecimal> entry : InyearAmountMap.entrySet()) {
            int year = entry.getKey();
            BigDecimal amount = entry.getValue();
            inresultList.add(new Annual(year, amount));
        }

        incomeVO.setSubprojectIncomeVO(IncomeVOList);
        incomeVO.getUnincludeSum().setAnnual(unresultList);
        incomeVO.getUnincludeSum().setSum(unincludeTotalSum);
        incomeVO.getIncludeSum().setAnnual(inresultList);
        incomeVO.getIncludeSum().setSum(includeTotalSum);

        return incomeVO;
    }

    @Override
    public IncomeReq caculateSubProjetOtherIncomeInfo(IncomeParam.SubprojectIncome subprojectIncome) throws Exception {
        String subprojectId = subprojectIncome.getSubprojectId();
        // 根据子产品id获取子产品单条售价
        String singlePrice = subprojectMapper.selectSinglePriceById(subprojectId);
        List<Annual> subProjectSinglePrices = JSONUtil.toList(singlePrice, Annual.class);
        if (subProjectSinglePrices == null) {
            throw new Exception("添加子产品信息时，未添加产品售价信息");
        }
        List<AnnualInfo> annualAdd = subprojectIncome.getAnnualAdd();
        List<AnnualInfo> annualDiscard = subprojectIncome.getAnnualDiscard();

        IncomeReq incomeReq = new IncomeReq();
        incomeReq.setSubprojectId(subprojectId);
        incomeReq.setAnnualAdd(annualAdd);
        incomeReq.setSubprojectSingleprice(subProjectSinglePrices);
        incomeReq.setAnnualDiscard(annualDiscard);

        // 计算年底投入使用的道路条数
        IncomeAlgorithm Algorithm1 = factory.getAlgorithm("EndRoadUseNumber");
        IncomeReq number = Algorithm1.incomeResult(incomeReq);
        incomeReq.setYearEndUse(number.getYearEndUse());

        // 计算收入（不含税）
        IncomeAlgorithm Algorithm2 = factory.getAlgorithm("UnRateTotal");
        IncomeReq UnRateAmount = Algorithm2.incomeResult(incomeReq);
        incomeReq.setUnincludeTotal(UnRateAmount.getUnincludeTotal());

        // 计算收入（含税）
        IncomeAlgorithm Algorithm3 = factory.getAlgorithm("RateTotal");
        IncomeReq RateAmount = Algorithm3.incomeResult(incomeReq);
        incomeReq.setIncludeTaxRate6(RateAmount.getIncludeTaxRate6());

        return incomeReq;
    }

}




