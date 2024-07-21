package vip.xiaonuo.biz.modular.subjectinfo.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import vip.xiaonuo.biz.modular.subjectinfo.dto.SubProjcetParam;
import vip.xiaonuo.biz.modular.subjectinfo.entity.Subproject;
import vip.xiaonuo.biz.modular.subjectinfo.mapper.SubprojectMapper;
import vip.xiaonuo.biz.modular.subjectinfo.service.SubprojectService;

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
}




