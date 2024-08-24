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
package vip.xiaonuo.biz.modular.template1.service.impl;

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
import vip.xiaonuo.biz.modular.template1.entity.template1;
import vip.xiaonuo.biz.modular.template1.mapper.template1Mapper;
import vip.xiaonuo.biz.modular.template1.param.template1AddParam;
import vip.xiaonuo.biz.modular.template1.param.template1EditParam;
import vip.xiaonuo.biz.modular.template1.param.template1IdParam;
import vip.xiaonuo.biz.modular.template1.param.template1PageParam;
import vip.xiaonuo.biz.modular.template1.service.template1Service;

import java.util.List;

/**
 * template1Service接口实现类
 *
 * @author gaobaoqu
 * @date  2024/08/24 08:36
 **/
@Service
public class template1ServiceImpl extends ServiceImpl<template1Mapper, template1> implements template1Service {

    @Override
    public Page<template1> page(template1PageParam template1PageParam) {
        QueryWrapper<template1> queryWrapper = new QueryWrapper<template1>().checkSqlInjection();
        if(ObjectUtil.isAllNotEmpty(template1PageParam.getSortField(), template1PageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(template1PageParam.getSortOrder());
            queryWrapper.orderBy(true, template1PageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(template1PageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(template1::getTransactionId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(template1AddParam template1AddParam) {
        template1 template1 = BeanUtil.toBean(template1AddParam, template1.class);
        this.save(template1);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(template1EditParam template1EditParam) {
        String transactionIdStr = String.valueOf(template1EditParam.getTransactionId());
        template1 template1 = this.queryEntity(transactionIdStr);
        BeanUtil.copyProperties(template1EditParam, template1);
        this.updateById(template1);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<template1IdParam> template1IdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(template1IdParamList, template1IdParam::getTransactionId));
    }

    @Override
    public template1 detail(template1IdParam template1IdParam) {
        String transactionIdStr = String.valueOf(template1IdParam.getTransactionId());
        return this.queryEntity(transactionIdStr);
    }

    @Override
    public template1 queryEntity(String id) {
        template1 template1 = this.getById(id);
        if(ObjectUtil.isEmpty(template1)) {
            throw new CommonException("template1不存在，id值为：{}", id);
        }
        return template1;
    }
}
