package vip.xiaonuo.biz.modular.subjectinfo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import vip.xiaonuo.biz.modular.subjectinfo.entity.Subproject;

import java.math.BigDecimal;


/**
* @author admin
* @description 针对表【subproject】的数据库操作Mapper
* @createDate 2024-07-20 16:32:24
* @Entity vip.xiaonuo.biz.model.entity.Subproject
*/
@Mapper
public interface SubprojectMapper extends BaseMapper<Subproject> {

    @Select("select SubProject_singlePrice from yidong.subproject where SubProject_id = #{subprojectId}")
    String selectSinglePriceById(String subprojectId);

    @Select("select Project_id from yidong.subproject where SubProject_id = #{subprojectId}")
    String selectProjectIdById(String subprojectId);

    @Select("select TaxRate from yidong.subproject where SubProject_id = #{subprojectId}")
    BigDecimal selectTaxRateById(String subprojectId);

}




