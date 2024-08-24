package vip.xiaonuo.biz.modular.New.template.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import vip.xiaonuo.biz.modular.New.template.entity.Template;
import vip.xiaonuo.biz.modular.New.template.service.TemplateService;
import vip.xiaonuo.biz.modular.New.template.mapper.TemplateMapper;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
* @author admin
* @description 针对表【Template】的数据库操作Service实现
* @createDate 2024-08-21 19:47:31
*/
@Service
public class TemplateServiceImpl extends ServiceImpl<TemplateMapper, Template>
    implements TemplateService{

    @Resource
    private TemplateMapper templateMapper;

    @Override
    public List<HashMap<Long,Integer>> getTemplateIdByTransactionId(Long Transaction_id) {
        return templateMapper.selectTemplateIdByTransactionId(Transaction_id);
    }
}




