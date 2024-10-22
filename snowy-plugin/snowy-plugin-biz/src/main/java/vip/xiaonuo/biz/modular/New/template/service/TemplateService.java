package vip.xiaonuo.biz.modular.New.template.service;

import vip.xiaonuo.biz.modular.New.template.entity.Template;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.HashMap;
import java.util.List;

/**
* @author admin
* @description 针对表【Template】的数据库操作Service
* @createDate 2024-08-21 19:47:31
*/
public interface TemplateService extends IService<Template> {

    List<HashMap<Long,Integer>> getTemplateIdByTransactionId(Long Transaction_id);



}
