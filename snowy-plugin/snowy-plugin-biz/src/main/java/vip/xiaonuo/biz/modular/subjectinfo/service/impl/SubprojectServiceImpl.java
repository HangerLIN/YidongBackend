package vip.xiaonuo.biz.modular.subjectinfo.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import vip.xiaonuo.biz.core.config.GlobalVoMap;
import vip.xiaonuo.biz.modular.income.vo.IncomeVO;
import vip.xiaonuo.biz.modular.spend.vo.SpendVO;
import vip.xiaonuo.biz.modular.strategy.comprehensiveAssessment.CompreAssAlgorithm;
import vip.xiaonuo.biz.modular.strategy.comprehensiveAssessment.CompreAssAlgorithmFactory;
import vip.xiaonuo.biz.modular.strategy.dto.*;
import vip.xiaonuo.biz.modular.strategy.invest.InvestAlgorithm;
import vip.xiaonuo.biz.modular.strategy.invest.InvestAlgorithmFactory;
import vip.xiaonuo.biz.modular.strategy.utils.Pair;
import vip.xiaonuo.biz.modular.strategy.vo.CompreAssVO;
import vip.xiaonuo.biz.modular.subjectinfo.dto.SubProjcetParam;
import vip.xiaonuo.biz.modular.subjectinfo.vo.SubProjcetParamVO.SubprojectInfo;
import vip.xiaonuo.biz.modular.subjectinfo.entity.Subproject;
import vip.xiaonuo.biz.modular.subjectinfo.mapper.SubprojectMapper;
import vip.xiaonuo.biz.modular.subjectinfo.service.SubprojectService;
import vip.xiaonuo.biz.modular.strategy.vo.InvestVO;
import vip.xiaonuo.biz.modular.subjectinfo.vo.SubProjcetParamVO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author admin
 * @description 针对表【subproject】的数据库操作Service实现
 * @createDate 2024-07-20 16:32:25
 */
@Service
public class SubprojectServiceImpl extends ServiceImpl<SubprojectMapper, Subproject>
        implements SubprojectService {

    @Resource
    private SubprojectMapper subprojectMapper;

    @Resource
    private InvestAlgorithmFactory investFactory;

    @Resource
    private CompreAssAlgorithmFactory compreAssFactory;

    @Resource
    private GlobalVoMap globalVoMap;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean addSubProject(SubProjcetParam.SubprojectInfo sp) {
        Long projectId = sp.getProjectId();
        List<SubProjcetParam.SubprojectInfo.BasicInformation> basicInformation = sp.getBasicInformation();
        List<SubProjcetParam.SubprojectInfo.SubProjectSingleCost> Singlecost = sp.getSubprojectSinglecost();
        List<SubProjcetParam.SubprojectInfo.SubProjectSinglePrice> Singleprice = sp.getSubprojectSingleprice();
        List<SubProjcetParam.SubprojectInfo.SubProjectSchedule> Schedule = sp.getSubprojectSchedule();

        String basicInformation_jsonStr = JSONUtil.toJsonStr(basicInformation);
        String Singlecost_jsonStr = JSONUtil.toJsonStr(Singlecost);
        String Singleprice_jsonStr = JSONUtil.toJsonStr(Singleprice);
        String Schedule_jsonStr = JSONUtil.toJsonStr((Schedule));

        Subproject subproject = new Subproject();
        subproject.setProjectId(projectId);
        subproject.setBasicInformation(basicInformation_jsonStr);
        subproject.setSubprojectSinglecost(Singlecost_jsonStr);
        subproject.setSubprojectSingleprice(Singleprice_jsonStr);
        subproject.setSubprojectSchedule(Schedule_jsonStr);

        subprojectMapper.insert(subproject);

        System.out.println(basicInformation_jsonStr);
        System.out.println(Singlecost_jsonStr);
        System.out.println(Singleprice_jsonStr);

        return false;
    }


    @Override
    public List<SubProjcetParamVO.SubprojectInfo> selectbyProuctId(Long projectId) {

        // 1. 创建查询条件构造器
        QueryWrapper<Subproject> wrapper = new QueryWrapper<>();
        // 2. 设置条件
        wrapper.eq("Project_id", projectId).eq("isDelete", 0);

        List<Subproject> subprojectList = subprojectMapper.selectList(wrapper);

        List<SubProjcetParamVO.SubprojectInfo> list = new ArrayList<>();

        for (Subproject subproject : subprojectList) {
            SubProjcetParamVO.SubprojectInfo subprojectInfo = new SubProjcetParamVO.SubprojectInfo();

            // 从数据库获取信息，将其转为vo数组形式
            List<SubprojectInfo.BasicInformation> basicInformation = JSONUtil.toList(subproject.getBasicInformation(), SubprojectInfo.BasicInformation.class);
            List<SubprojectInfo.SubProjectSchedule> schedule = JSONUtil.toList(subproject.getSubprojectSchedule(), SubprojectInfo.SubProjectSchedule.class);
            List<SubprojectInfo.SubProjectSingleCost> singlecost = JSONUtil.toList(subproject.getSubprojectSinglecost(), SubprojectInfo.SubProjectSingleCost.class);
            List<SubprojectInfo.SubProjectSinglePrice> singleprice = JSONUtil.toList(subproject.getSubprojectSingleprice(), SubprojectInfo.SubProjectSinglePrice.class);

            subprojectInfo.setSubprojectId(subproject.getSubprojectId());
            subprojectInfo.setProjectId(projectId);
            subprojectInfo.setBasicInformation(basicInformation);
            subprojectInfo.setSubprojectSchedule(schedule);
            subprojectInfo.setSubprojectSinglecost(singlecost);
            subprojectInfo.setSubprojectSingleprice(singleprice);

            //添加到vo基础信息的数组中
            list.add(subprojectInfo);
        }

        return list;
    }

    @Override
    public boolean deleteSubProject(Long SubProject_id) {

        Subproject subproject = new Subproject();
        subproject.setSubprojectId(SubProject_id);
        subproject.setIsdelete(1);

        int i = subprojectMapper.updateById(subproject);

        return i == 1;
    }

    @Override
    public InvestVO calculateInvestAmout(InvestReq req) {
        String type = req.getType();
        InvestAlgorithm investAlgorithm = investFactory.getAlgorithm(type);
        InvestVO investVO = investAlgorithm.investResult(req);


        stringRedisTemplate.opsForValue().set("projectID:invest", JSONUtil.toJsonStr(investVO));

        //因为大家返回前端的VO类还没写好，我就暂时在这个位置写点假数据。BasicInfoForCompreAssReq
        BasicInfoForCompreAssReq basicVO = new BasicInfoForCompreAssReq();
        SpendForCompreAssReq spendVO = new SpendForCompreAssReq();
        StreamForCompreAssReq streamVO = new StreamForCompreAssReq();

        basicVO.setSingleAeCycle(5);
        basicVO.setConstructStart(2024);

        streamVO.setApprovedTaxRate(new BigDecimal(0.25));
        streamVO.setAnnualNetCashFlowPv(new BigDecimal(2229.45));

        globalVoMap.put("basic", basicVO);
        globalVoMap.put("stream", streamVO);

        return investVO;
    }

    @Override
    public CompreAssVO calculateCompreAssessment() {
        BasicInfoForCompreAssReq basicResp = (BasicInfoForCompreAssReq) globalVoMap.get("basic");
//        InvestVO investResp = (InvestVO) globalVoMap.get("invest");
//        IncomeForCompreAssReq incomeResp = (IncomeForCompreAssReq) globalVoMap.get("income");

        String investJson = stringRedisTemplate.opsForValue().get("projectID:invest");
        InvestVO investVO = JSONUtil.toBean(investJson, InvestVO.class);

        String incomeJson = stringRedisTemplate.opsForValue().get("projectID:income");
        IncomeVO incomeVO = JSONUtil.toBean(incomeJson, IncomeVO.class);

        String spendJson = stringRedisTemplate.opsForValue().get("projectID:spend");
        SpendVO spendVO = JSONUtil.toBean(spendJson, SpendVO.class);

        StreamForCompreAssReq streamResp = (StreamForCompreAssReq) globalVoMap.get("stream");

        CompreAssAlgorithm compreAssAlgorithm = compreAssFactory.getAlgorithm("basic");
        return compreAssAlgorithm.compreAssResult(basicResp, investVO, incomeVO, spendVO, streamResp);
    }
}




