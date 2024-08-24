package vip.xiaonuo.biz.modular.New.template.mapper;

import org.apache.ibatis.annotations.Select;
import vip.xiaonuo.biz.modular.New.template.entity.Template;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.HashMap;
import java.util.List;

/**
* @author admin
* @description 针对表【Template】的数据库操作Mapper
* @createDate 2024-08-21 19:47:31
* @Entity vip.xiaonuo.biz.modular.New.template.entity.Template
*/
public interface TemplateMapper extends BaseMapper<Template> {

    @Select("SELECT template_id,Template_Serial FROM Template WHERE transaction_id = #{transactionId}")
    List<HashMap<Long,Integer>> selectTemplateIdByTransactionId(Long transactionId);

}




