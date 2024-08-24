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
package vip.xiaonuo.biz.modular.template1.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.biz.modular.template1.entity.template1;
import vip.xiaonuo.biz.modular.template1.param.template1AddParam;
import vip.xiaonuo.biz.modular.template1.param.template1EditParam;
import vip.xiaonuo.biz.modular.template1.param.template1IdParam;
import vip.xiaonuo.biz.modular.template1.param.template1PageParam;
import vip.xiaonuo.biz.modular.template1.service.template1Service;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

/**
 * template1控制器
 *
 * @author gaobaoqu
 * @date  2024/08/24 08:36
 */
@Tag(name = "template1控制器")
@RestController
@Validated
public class template1Controller {

    @Resource
    private template1Service template1Service;

    /**
     * 获取template1分页
     *
     * @author gaobaoqu
     * @date  2024/08/24 08:36
     */
    @Operation(summary = "获取template1分页")
    @SaCheckPermission("/biz/template1/page")
    @GetMapping("/biz/template1/page")
    public CommonResult<Page<template1>> page(template1PageParam template1PageParam) {
        return CommonResult.data(template1Service.page(template1PageParam));
    }

    /**
     * 添加template1
     *
     * @author gaobaoqu
     * @date  2024/08/24 08:36
     */
    @Operation(summary = "添加template1")
    @CommonLog("添加template1")
    @SaCheckPermission("/biz/template1/add")
    @PostMapping("/biz/template1/add")
    public CommonResult<String> add(@RequestBody @Valid template1AddParam template1AddParam) {
        template1Service.add(template1AddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑template1
     *
     * @author gaobaoqu
     * @date  2024/08/24 08:36
     */
    @Operation(summary = "编辑template1")
    @CommonLog("编辑template1")
    @SaCheckPermission("/biz/template1/edit")
    @PostMapping("/biz/template1/edit")
    public CommonResult<String> edit(@RequestBody @Valid template1EditParam template1EditParam) {
        template1Service.edit(template1EditParam);
        return CommonResult.ok();
    }

    /**
     * 删除template1
     *
     * @author gaobaoqu
     * @date  2024/08/24 08:36
     */
    @Operation(summary = "删除template1")
    @CommonLog("删除template1")
    @SaCheckPermission("/biz/template1/delete")
    @PostMapping("/biz/template1/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   List<template1IdParam> template1IdParamList) {
        template1Service.delete(template1IdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取template1详情
     *
     * @author gaobaoqu
     * @date  2024/08/24 08:36
     */
    @Operation(summary = "获取template1详情")
    @SaCheckPermission("/biz/template1/detail")
    @GetMapping("/biz/template1/detail")
    public CommonResult<template1> detail(@Valid template1IdParam template1IdParam) {
        return CommonResult.data(template1Service.detail(template1IdParam));
    }
}
