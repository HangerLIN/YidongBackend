package vip.xiaonuo.biz.modular.New.subtemplate.mapper;

import org.apache.ibatis.annotations.Select;
import vip.xiaonuo.biz.modular.New.subtemplate.entity.Subtemplate;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.HashMap;
import java.util.List;

/**
* @author admin
* @description 针对表【SubTemplate】的数据库操作Mapper
* @createDate 2024-08-21 19:49:44
* @Entity vip.xiaonuo.biz.modular.New.subtemplate.entity.Subtemplate
*/
public interface SubtemplateMapper extends BaseMapper<Subtemplate> {

    @Select("SELECT Subtemplate_id, Subtemplate_Serial FROM SubTemplate WHERE Template_id = #{templateId}")
    List<HashMap<Long,Integer>> selectSubtemplateIdAndSerialByTemplateId(Long templateId);

}




