package vip.xiaonuo.biz.modular.New.subtemplate.service;

import vip.xiaonuo.biz.modular.New.subtemplate.dto.SubtemplateInputParam;
import vip.xiaonuo.biz.modular.New.subtemplate.entity.Subtemplate;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.New.subtemplate.vo.SubtemplateOutput;

import java.util.List;

/**
* @author admin
* @description 针对表【SubTemplate】的数据库操作Service
* @createDate 2024-08-21 19:49:44
*/
public interface SubtemplateService extends IService<Subtemplate> {
    SubtemplateOutput calculate(List<Subtemplate> subtemplateList, SubtemplateInputParam subtemplateInputParam);
}
