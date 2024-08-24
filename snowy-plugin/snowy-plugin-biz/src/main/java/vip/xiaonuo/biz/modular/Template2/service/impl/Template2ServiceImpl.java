/*
 * Copyright [2022] [https://www.xiaonuo.vip]
 *
 * Snowy采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改Snowy源码头部的版权声明。
 * 3.本项目代码可免费商业使用，商业使用请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://www.xiaonuo.vip
 * 5.不可二次分发开源参与同类竞品，如有想法可联系团队xiaonuobase@qq.com商议合作。
 * 6.若您的项目无法满足以上几点，需要更多功能代码，获取Snowy商业授权许可，请在官网购买授权，地址为 https://www.xiaonuo.vip
 */
package vip.xiaonuo.biz.modular.Template2.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.Template2.entity.Template2;
import vip.xiaonuo.biz.modular.Template2.mapper.Template2Mapper;
import vip.xiaonuo.biz.modular.Template2.param.Template2AddParam;
import vip.xiaonuo.biz.modular.Template2.param.Template2EditParam;
import vip.xiaonuo.biz.modular.Template2.param.Template2IdParam;
import vip.xiaonuo.biz.modular.Template2.param.Template2PageParam;
import vip.xiaonuo.biz.modular.Template2.service.Template2Service;

import java.util.List;

/**
 * Template2Service接口实现类
 *
 * @author gaobaoqu
 * @date  2024/08/24 09:07
 **/
@Service
public class Template2ServiceImpl extends ServiceImpl<Template2Mapper, Template2> implements Template2Service {

    @Override
    public Page<Template2> page(Template2PageParam template2PageParam) {
        QueryWrapper<Template2> queryWrapper = new QueryWrapper<Template2>().checkSqlInjection();
        if(ObjectUtil.isNotEmpty(template2PageParam.getTransactionId())) {
            queryWrapper.lambda().like(Template2::getTransactionId, template2PageParam.getTransactionId());
        }
        if(ObjectUtil.isNotEmpty(template2PageParam.getTemplateName())) {
            queryWrapper.lambda().like(Template2::getTemplateName, template2PageParam.getTemplateName());
        }
        if(ObjectUtil.isAllNotEmpty(template2PageParam.getSortField(), template2PageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(template2PageParam.getSortOrder());
            queryWrapper.orderBy(true, template2PageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(template2PageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(Template2::getTemplateId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(Template2AddParam template2AddParam) {
        Template2 template2 = BeanUtil.toBean(template2AddParam, Template2.class);
        this.save(template2);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(Template2EditParam template2EditParam) {
        String transactionIdStr = String.valueOf(template2EditParam.getTransactionId());
        Template2 template2 = this.queryEntity(transactionIdStr);
        BeanUtil.copyProperties(template2EditParam, template2);
        this.updateById(template2);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<Template2IdParam> template2IdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(template2IdParamList, Template2IdParam::getTemplateId));
    }

    @Override
    public Template2 detail(Template2IdParam template2IdParam) {
        String transactionIdStr = String.valueOf(template2IdParam.getTemplateId());
        return this.queryEntity(transactionIdStr);
    }

    @Override
    public Template2 queryEntity(String id) {
        Template2 template2 = this.getById(id);
        if(ObjectUtil.isEmpty(template2)) {
            throw new CommonException("Template2不存在，id值为：{}", id);
        }
        return template2;
    }
}
