package vip.xiaonuo.biz.modular.New.subtemplate.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import vip.xiaonuo.biz.modular.New.subtemplate.dto.SubtemplateParam;
import vip.xiaonuo.biz.modular.New.subtemplate.dto.SubtemplateParam.Submoule;
import vip.xiaonuo.biz.modular.New.subtemplate.entity.Subtemplate;
import vip.xiaonuo.biz.modular.New.subtemplate.service.SubtemplateService;
import vip.xiaonuo.biz.modular.New.subtemplate.mapper.SubtemplateMapper;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * @author admin
 * @description 针对表【SubTemplate】的数据库操作Service实现
 * @createDate 2024-08-21 19:49:44
 */
@Service
public class SubtemplateServiceImpl extends ServiceImpl<SubtemplateMapper, Subtemplate>
        implements SubtemplateService {

    @Resource
    private SubtemplateMapper subtemplateMapper;


    @Override
    public boolean addSubtemplateInfo(SubtemplateParam subtemplateParam) {
        List<Submoule> submouleList = subtemplateParam.getSubmouleList();
        try {
            for (Submoule submoule : submouleList) {
                Integer templateId = submoule.getTemplateId(); // 模版ID
                Integer subtemplateSerial = submoule.getSubtemplateSerial(); // 子模版序号
                String subtemplateName = submoule.getSubtemplateName(); // 子模版名称
                Integer templateType = submoule.getTemplateType();// 子模版类型（0:涉及计算，1：不涉及计算，3:可涉及计算也可不涉及）
                List<String> basicInformation = submoule.getBasicInformation(); // 基础信息
                String startYearEq = submoule.getStartYearEq(); // 开始年份公式
                String endyearEq = submoule.getEndyearEq(); // 其他年份公式

                Subtemplate subtemplate = new Subtemplate();
                subtemplate.setTemplateId(Long.valueOf(templateId));
                subtemplate.setSubtemplateSerial(subtemplateSerial);
                subtemplate.setSubtemplateName(subtemplateName);
                subtemplate.setTemplateType(templateType);
                subtemplate.setBasicInformation(JSONUtil.toJsonStr(basicInformation));
                subtemplate.setStartyearEq(startYearEq);
                subtemplate.setEndyearEq(endyearEq);

                subtemplateMapper.insert(subtemplate);
            }

        } catch (Exception e) {
            return false;
        }

        return true;
    }

    @Override
    public List<Subtemplate> getSubtemplatesByTemplateId(Long templateId) {
        // return this.lambdaQuery().eq(Subtemplate::getTemplateId, templateId).list();
        QueryWrapper<Subtemplate> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("template_id", templateId);
        queryWrapper.orderByAsc("subtemplate_serial");
        return subtemplateMapper.selectList(queryWrapper);
    }

    @Override
    public List<HashMap<Long,Integer>> getSubtemplateIdByTemplateId(Long templateId) {
        return subtemplateMapper.selectSubtemplateIdAndSerialByTemplateId(templateId);
    }
}




