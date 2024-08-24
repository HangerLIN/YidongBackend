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
package vip.xiaonuo.biz.modular.SubTemplate001.service.impl;

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
import vip.xiaonuo.biz.modular.SubTemplate001.entity.Subtemplate001;
import vip.xiaonuo.biz.modular.SubTemplate001.mapper.Subtemplate001Mapper;
import vip.xiaonuo.biz.modular.SubTemplate001.param.Subtemplate001AddParam;
import vip.xiaonuo.biz.modular.SubTemplate001.param.Subtemplate001EditParam;
import vip.xiaonuo.biz.modular.SubTemplate001.param.Subtemplate001IdParam;
import vip.xiaonuo.biz.modular.SubTemplate001.param.Subtemplate001PageParam;
import vip.xiaonuo.biz.modular.SubTemplate001.service.Subtemplate001Service;

import java.util.List;

/**
 * SubTemplate001Service接口实现类
 *
 * @author gaobaoqu
 * @date  2024/08/24 10:27
 **/
@Service
public class Subtemplate001ServiceImpl extends ServiceImpl<Subtemplate001Mapper, Subtemplate001> implements Subtemplate001Service {

    @Override
    public Page<Subtemplate001> page(Subtemplate001PageParam subtemplate001PageParam) {
        QueryWrapper<Subtemplate001> queryWrapper = new QueryWrapper<Subtemplate001>().checkSqlInjection();
        if(ObjectUtil.isNotEmpty(subtemplate001PageParam.getTemplateId())) {
            queryWrapper.lambda().like(Subtemplate001::getTemplateId, subtemplate001PageParam.getTemplateId());
        }
        if(ObjectUtil.isNotEmpty(subtemplate001PageParam.getSubtemplateName())) {
            queryWrapper.lambda().like(Subtemplate001::getSubtemplateName, subtemplate001PageParam.getSubtemplateName());
        }
        if(ObjectUtil.isAllNotEmpty(subtemplate001PageParam.getSortField(), subtemplate001PageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(subtemplate001PageParam.getSortOrder());
            queryWrapper.orderBy(true, subtemplate001PageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(subtemplate001PageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(Subtemplate001::getSubtemplateId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(Subtemplate001AddParam subtemplate001AddParam) {
        Subtemplate001 subtemplate001 = BeanUtil.toBean(subtemplate001AddParam, Subtemplate001.class);
        this.save(subtemplate001);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(Subtemplate001EditParam subtemplate001EditParam) {
        String transactionIdStr = String.valueOf(subtemplate001EditParam.getSubtemplateId());
        Subtemplate001 subtemplate001 = this.queryEntity(transactionIdStr);
        BeanUtil.copyProperties(subtemplate001EditParam, subtemplate001);
        this.updateById(subtemplate001);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<Subtemplate001IdParam> subtemplate001IdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(subtemplate001IdParamList, Subtemplate001IdParam::getSubtemplateId));
    }

    @Override
    public Subtemplate001 detail(Subtemplate001IdParam subtemplate001IdParam) {
        String transactionIdStr = String.valueOf(subtemplate001IdParam.getSubtemplateId());
        return this.queryEntity(transactionIdStr);
    }

    @Override
    public Subtemplate001 queryEntity(String id) {
        Subtemplate001 subtemplate001 = this.getById(id);
        if(ObjectUtil.isEmpty(subtemplate001)) {
            throw new CommonException("SubTemplate001不存在，id值为：{}", id);
        }
        return subtemplate001;
    }
}
