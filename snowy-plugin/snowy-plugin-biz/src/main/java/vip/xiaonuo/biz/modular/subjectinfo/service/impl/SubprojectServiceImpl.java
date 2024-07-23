package vip.xiaonuo.biz.modular.subjectinfo.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import vip.xiaonuo.biz.modular.strategy.dto.InvestReq;
import vip.xiaonuo.biz.modular.strategy.invest.InvestAlgorithm;
import vip.xiaonuo.biz.modular.strategy.invest.InvestAlgorithmFactory;
import vip.xiaonuo.biz.modular.subjectinfo.dto.SubProjcetParam;
import vip.xiaonuo.biz.modular.subjectinfo.dto.SubProjcetParam.SubprojectInfo;
import vip.xiaonuo.biz.modular.subjectinfo.entity.Subproject;
import vip.xiaonuo.biz.modular.subjectinfo.mapper.SubprojectMapper;
import vip.xiaonuo.biz.modular.subjectinfo.service.SubprojectService;
import vip.xiaonuo.biz.modular.strategy.vo.InvestResp;

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
    private InvestAlgorithmFactory factory;

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

        InvestAlgorithm investAlgorithm = factory.getAlgorithm(req.getType());
        return investAlgorithm.investResult(req);
    }
}




