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
package vip.xiaonuo.biz.modular.template1.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.template1.entity.template1;
import vip.xiaonuo.biz.modular.template1.param.template1AddParam;
import vip.xiaonuo.biz.modular.template1.param.template1EditParam;
import vip.xiaonuo.biz.modular.template1.param.template1IdParam;
import vip.xiaonuo.biz.modular.template1.param.template1PageParam;

import java.util.List;

/**
 * template1Service接口
 *
 * @author gaobaoqu
 * @date  2024/08/24 08:36
 **/
public interface template1Service extends IService<template1> {

    /**
     * 获取template1分页
     *
     * @author gaobaoqu
     * @date  2024/08/24 08:36
     */
    Page<template1> page(template1PageParam template1PageParam);

    /**
     * 添加template1
     *
     * @author gaobaoqu
     * @date  2024/08/24 08:36
     */
    void add(template1AddParam template1AddParam);

    /**
     * 编辑template1
     *
     * @author gaobaoqu
     * @date  2024/08/24 08:36
     */
    void edit(template1EditParam template1EditParam);

    /**
     * 删除template1
     *
     * @author gaobaoqu
     * @date  2024/08/24 08:36
     */
    void delete(List<template1IdParam> template1IdParamList);

    /**
     * 获取template1详情
     *
     * @author gaobaoqu
     * @date  2024/08/24 08:36
     */
    template1 detail(template1IdParam template1IdParam);

    /**
     * 获取template1详情
     *
     * @author gaobaoqu
     * @date  2024/08/24 08:36
     **/
    template1 queryEntity(String id);
}
