package vip.xiaonuo.biz.modular.income.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import vip.xiaonuo.biz.modular.income.dto.IncomeParam;
import vip.xiaonuo.biz.modular.income.entity.Income;
import vip.xiaonuo.biz.modular.income.service.IncomeService;
import vip.xiaonuo.biz.modular.income.mapper.IncomeMapper;
import org.springframework.stereotype.Service;
import vip.xiaonuo.biz.modular.income.vo.IncomeVO;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public boolean writeSubProjetIncomeInfo(IncomeParam incomeParam) {
        List<IncomeParam.SubprojectIncome> subprojectIncomeList = incomeParam.getSubprojectIncome();

        for (IncomeParam.SubprojectIncome subprojectIncome : subprojectIncomeList) {
            String subprojectId = subprojectIncome.getSubprojectId();
            List<IncomeParam.SubprojectIncome.AnnualInfo> annualAdd = subprojectIncome.getAnnualAdd();
            List<IncomeParam.SubprojectIncome.AnnualInfo> annualDiscard = subprojectIncome.getAnnualDiscard();

            String annualAdd_jsonStr = JSONUtil.toJsonStr(annualAdd);
            String annualDiscard_jsonStr = JSONUtil.toJsonStr(annualDiscard);

            Income income = new Income();
            income.setSubprojectId(Long.valueOf(subprojectId));
            income.setAnnualDiscard(annualDiscard_jsonStr);
            income.setAnnualAdd(annualAdd_jsonStr);

            // 剩下字段需调用策略类计算，存入数据库，这里只是随便存的数据
            income.setYearendUse(annualAdd_jsonStr);
            income.setUnincludeTotal(annualAdd_jsonStr);
            income.setIncludeTaxrate6(annualAdd_jsonStr);

            incomeMapper.insert(income);

        }

        return true;
    }

    @Override
    public IncomeVO returnSubProjetIncomeInfo(List<String> incomeIDList) {

        IncomeVO incomeVO = new IncomeVO();

        ArrayList<IncomeVO.SubprojectIncomeVO> List = new ArrayList<>();

        for (String incomID : incomeIDList) {
            IncomeVO.SubprojectIncomeVO subprojectIncomeVO = new IncomeVO.SubprojectIncomeVO();
            Income income = incomeMapper.selectById(incomID);

            // 反序列化
            List<IncomeVO.SubprojectIncomeVO.AnnualAdd> annualAdds = JSONUtil.toList(income.getAnnualAdd(), IncomeVO.SubprojectIncomeVO.AnnualAdd.class);

            // SubprojectIncomeVO 赋值
            subprojectIncomeVO.setAnnualAdd(annualAdds);
            // 省略其他属性

            // SubprojectIncomeVO 加入列表
            List.add(subprojectIncomeVO);

        }
        incomeVO.setSubprojectIncomeVO(List);
        //  incomeVO.setIncludeSum();
        //  incomeVO.setUnincludeSum();

        return incomeVO;
    }


}




