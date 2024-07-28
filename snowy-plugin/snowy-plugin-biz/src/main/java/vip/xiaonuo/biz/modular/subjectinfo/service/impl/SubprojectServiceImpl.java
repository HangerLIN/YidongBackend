package vip.xiaonuo.biz.modular.subjectinfo.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import vip.xiaonuo.biz.core.config.GlobalVoMap;
import vip.xiaonuo.biz.modular.strategy.comprehensiveAssessment.CompreAssAlgorithm;
import vip.xiaonuo.biz.modular.strategy.comprehensiveAssessment.CompreAssAlgorithmFactory;
import vip.xiaonuo.biz.modular.strategy.dto.*;
import vip.xiaonuo.biz.modular.strategy.invest.InvestAlgorithm;
import vip.xiaonuo.biz.modular.strategy.invest.InvestAlgorithmFactory;
import vip.xiaonuo.biz.modular.strategy.utils.Pair;
import vip.xiaonuo.biz.modular.strategy.vo.CompreAssResp;
import vip.xiaonuo.biz.modular.subjectinfo.dto.SubProjcetParam;
import vip.xiaonuo.biz.modular.subjectinfo.dto.SubProjcetParam.SubprojectInfo;
import vip.xiaonuo.biz.modular.subjectinfo.entity.Subproject;
import vip.xiaonuo.biz.modular.subjectinfo.mapper.SubprojectMapper;
import vip.xiaonuo.biz.modular.subjectinfo.service.SubprojectService;
import vip.xiaonuo.biz.modular.strategy.vo.InvestResp;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public List<SubProjcetParam.SubprojectInfo> selectbyProuctId(Long projectId) {

        // 1. 创建查询条件构造器
        QueryWrapper<Subproject> wrapper = new QueryWrapper<>();
        // 2. 设置条件
        wrapper.eq("Project_id", projectId).eq("isDelete", 0);

        List<Subproject> subprojectList = subprojectMapper.selectList(wrapper);

        List<SubProjcetParam.SubprojectInfo> list = new ArrayList<>();

        for (Subproject subproject : subprojectList) {
            SubProjcetParam.SubprojectInfo subprojectInfo = new SubProjcetParam.SubprojectInfo();

            // 从数据库获取信息，将其转为vo数组形式
            List<SubprojectInfo.BasicInformation> basicInformation = JSONUtil.toList(subproject.getBasicInformation(), SubprojectInfo.BasicInformation.class);
            List<SubprojectInfo.SubProjectSchedule> schedule = JSONUtil.toList(subproject.getSubprojectSchedule(), SubprojectInfo.SubProjectSchedule.class);
            List<SubprojectInfo.SubProjectSingleCost> singlecost = JSONUtil.toList(subproject.getSubprojectSinglecost(), SubprojectInfo.SubProjectSingleCost.class);
            List<SubprojectInfo.SubProjectSinglePrice> singleprice = JSONUtil.toList(subproject.getSubprojectSingleprice(), SubprojectInfo.SubProjectSinglePrice.class);

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
    public InvestResp calculateInvestAmout(InvestReq req) {
        String type = req.getType();
        InvestAlgorithm investAlgorithm = investFactory.getAlgorithm(type);
        InvestResp resp = investAlgorithm.investResult(req);

        if(type.equals("InvestAmount")) globalVoMap.put("invest", resp);

        //因为大家返回前端的VO类还没写好，我就暂时在这个位置写点假数据。BasicInfoForCompreAssReq
        BasicInfoForCompreAssReq basicVO = new BasicInfoForCompreAssReq();
        IncomeForCompreAssReq incomeVO = new IncomeForCompreAssReq();
        SpendForCompreAssReq spendVO = new SpendForCompreAssReq();
        StreamForCompreAssReq streamVO = new StreamForCompreAssReq();

        basicVO.setSingleAeCycle(5);

        List<Pair<Integer, BigDecimal>> unincludeTotal = new ArrayList<>();
        unincludeTotal.add(new Pair<>(2024,new BigDecimal(2141866.67)));
        unincludeTotal.add(new Pair<>(2025,new BigDecimal(9802920.00)));
        unincludeTotal.add(new Pair<>(2026,new BigDecimal(11711360.00)));
        unincludeTotal.add(new Pair<>(2027,new BigDecimal(12862360.00)));
        unincludeTotal.add(new Pair<>(2028,new BigDecimal(12433360.00)));
        unincludeTotal.add(new Pair<>(2029,new BigDecimal(12050560.00)));
        unincludeTotal.add(new Pair<>(2030,new BigDecimal(11667760.00)));
        unincludeTotal.add(new Pair<>(2031,new BigDecimal(11192560.00)));
        incomeVO.setUnincludeTotal(unincludeTotal);

        List<Pair<Integer, BigDecimal>> spendSafeguard = new ArrayList<>();  //路面后期维护费用
        List<Pair<Integer, BigDecimal>> spendUpkeep = new ArrayList<>();     //路面保养费用
        List<Pair<Integer, BigDecimal>> spendArtificial = new ArrayList<>(); //人工服务费用
        List<Pair<Integer, BigDecimal>> spendOther = new ArrayList<>();      //其他费用
        List<Pair<Integer, BigDecimal>> spendNoise = new ArrayList<>();      //噪音污染补偿
        List<Pair<Integer, BigDecimal>> spendPublicize = new ArrayList<>();  //宣传推广费用

        spendSafeguard.add(new Pair<>(2024,new BigDecimal(703052.96)));
        spendSafeguard.add(new Pair<>(2025,new BigDecimal(2051295.28)));
        spendSafeguard.add(new Pair<>(2026,new BigDecimal(3232634.28)));
        spendSafeguard.add(new Pair<>(2027,new BigDecimal(3189056.48)));
        spendSafeguard.add(new Pair<>(2028,new BigDecimal(2983495.68)));
        spendSafeguard.add(new Pair<>(2029,new BigDecimal(3054558.28)));
        spendSafeguard.add(new Pair<>(2030,new BigDecimal(2913997.48)));
        spendSafeguard.add(new Pair<>(2031,new BigDecimal(543359.17)));

        spendUpkeep.add(new Pair<>(2024,new BigDecimal(357285.20)));
        spendUpkeep.add(new Pair<>(2025,new BigDecimal(1297869.60)));
        spendUpkeep.add(new Pair<>(2026,new BigDecimal(1400445.40)));
        spendUpkeep.add(new Pair<>(2027,new BigDecimal(1308935.00)));
        spendUpkeep.add(new Pair<>(2028,new BigDecimal(1260390.60)));
        spendUpkeep.add(new Pair<>(2029,new BigDecimal(1186219.20)));
        spendUpkeep.add(new Pair<>(2030,new BigDecimal(1137674.80)));
        spendUpkeep.add(new Pair<>(2031,new BigDecimal(343532.60)));

        spendArtificial.add(new Pair<>(2024,new BigDecimal(106692.08)));
        spendArtificial.add(new Pair<>(2025,new BigDecimal(394583.44)));
        spendArtificial.add(new Pair<>(2026,new BigDecimal(445363.44)));
        spendArtificial.add(new Pair<>(2027,new BigDecimal(428610.04)));
        spendArtificial.add(new Pair<>(2028,new BigDecimal(412557.64)));
        spendArtificial.add(new Pair<>(2029,new BigDecimal(400328.44)));
        spendArtificial.add(new Pair<>(2030,new BigDecimal(384276.04)));
        spendArtificial.add(new Pair<>(2031,new BigDecimal(167055.91)));

        spendOther.add(new Pair<>(2024,new BigDecimal(96753.84)));
        spendOther.add(new Pair<>(2025,new BigDecimal(352663.12)));
        spendOther.add(new Pair<>(2026,new BigDecimal(395423.92)));
        spendOther.add(new Pair<>(2027,new BigDecimal(406391.32)));
        spendOther.add(new Pair<>(2028,new BigDecimal(393607.72)));
        spendOther.add(new Pair<>(2029,new BigDecimal(383437.72)));
        spendOther.add(new Pair<>(2030,new BigDecimal(370654.12)));
        spendOther.add(new Pair<>(2031,new BigDecimal(164467.63)));

        spendNoise.add(new Pair<>(2024,new BigDecimal(960.00)));
        spendNoise.add(new Pair<>(2025,new BigDecimal(905.00)));
        spendNoise.add(new Pair<>(2026,new BigDecimal(855.00)));
        spendNoise.add(new Pair<>(2027,new BigDecimal(825.00)));
        spendNoise.add(new Pair<>(2028,new BigDecimal(815.00)));
        spendNoise.add(new Pair<>(2029,new BigDecimal(805.00)));
        spendNoise.add(new Pair<>(2030,new BigDecimal(805.00)));
        spendNoise.add(new Pair<>(2031,new BigDecimal(805.00)));

        spendPublicize.add(new Pair<>(2024,new BigDecimal(61257.39)));
        spendPublicize.add(new Pair<>(2025,new BigDecimal(280363.51)));
        spendPublicize.add(new Pair<>(2026,new BigDecimal(334944.90)));
        spendPublicize.add(new Pair<>(2027,new BigDecimal(367863.50)));
        spendPublicize.add(new Pair<>(2028,new BigDecimal(355594.10)));
        spendPublicize.add(new Pair<>(2029,new BigDecimal(344646.02)));
        spendPublicize.add(new Pair<>(2030,new BigDecimal(333697.94)));
        spendPublicize.add(new Pair<>(2031,new BigDecimal(320107.22)));

        spendVO.setSpendSafeguard(spendSafeguard);
        spendVO.setSpendUpkeep(spendUpkeep);
        spendVO.setSpendArtificial(spendArtificial);
        spendVO.setSpendOther(spendOther);
        spendVO.setSpendNoise(spendNoise);
        spendVO.setSpendPublicize(spendPublicize);

        streamVO.setApprovedTaxRate(new BigDecimal(0.25));
        streamVO.setAnnualNetCashFlowPv(new BigDecimal(2229.45));

        globalVoMap.put("basic", basicVO);
        globalVoMap.put("income", incomeVO);
        globalVoMap.put("spend", spendVO);
        globalVoMap.put("stream", streamVO);

        return resp;
    }

    @Override
    public CompreAssResp calculateCompreAssessment() {
        BasicInfoForCompreAssReq basicResp = (BasicInfoForCompreAssReq) globalVoMap.get("basic");
        InvestResp investResp = (InvestResp) globalVoMap.get("invest");
        IncomeForCompreAssReq incomeResp = (IncomeForCompreAssReq) globalVoMap.get("income");
        SpendForCompreAssReq spendResp = (SpendForCompreAssReq) globalVoMap.get("spend");
        StreamForCompreAssReq streamResp = (StreamForCompreAssReq) globalVoMap.get("stream");

        CompreAssAlgorithm compreAssAlgorithm = compreAssFactory.getAlgorithm("basic");
        return compreAssAlgorithm.compreAssResult(basicResp, investResp, incomeResp, spendResp, streamResp);
    }
}




