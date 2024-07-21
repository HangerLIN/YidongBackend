package vip.xiaonuo.biz.modular.subjectinfo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.subjectinfo.dto.SubProjcetParam;
import vip.xiaonuo.biz.modular.subjectinfo.entity.Subproject;


/**
* @author admin
* @description 针对表【subproject】的数据库操作Service
* @createDate 2024-07-20 16:32:25
*/
public interface SubprojectService extends IService<Subproject> {

    boolean addSubProject(SubProjcetParam.SubprojectInfo sp);

}
