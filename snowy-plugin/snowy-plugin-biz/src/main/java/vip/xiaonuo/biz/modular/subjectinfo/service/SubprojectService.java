package vip.xiaonuo.biz.modular.subjectinfo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.strategy.dto.InvestReq;
import vip.xiaonuo.biz.modular.strategy.vo.CompreAssResp;
import vip.xiaonuo.biz.modular.strategy.vo.InvestResp;
import vip.xiaonuo.biz.modular.subjectinfo.dto.SubProjcetParam;
import vip.xiaonuo.biz.modular.subjectinfo.entity.Subproject;

import java.util.List;


/**
* @author admin
* @description 针对表【subproject】的数据库操作Service
* @createDate 2024-07-20 16:32:25
*/
public interface SubprojectService extends IService<Subproject> {

    boolean addSubProject(SubProjcetParam.SubprojectInfo sp);

    List<SubProjcetParam.SubprojectInfo> selectbyProuctId(Long projectId);

    boolean deleteSubProject(Long SubProject_id);

    InvestResp calculateInvestAmout(InvestReq req);

    CompreAssResp calculateCompreAssessment();
}
