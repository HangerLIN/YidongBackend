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
package vip.xiaonuo.biz.modular.basicinfo.controller;

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
import vip.xiaonuo.biz.modular.basicinfo.entity.ProjectBasicInfo;
import vip.xiaonuo.biz.modular.basicinfo.param.ProjectBasicInfoAddParam;
import vip.xiaonuo.biz.modular.basicinfo.param.ProjectBasicInfoEditParam;
import vip.xiaonuo.biz.modular.basicinfo.param.ProjectBasicInfoIdParam;
import vip.xiaonuo.biz.modular.basicinfo.param.ProjectBasicInfoPageParam;
import vip.xiaonuo.biz.modular.basicinfo.service.ProjectBasicInfoService;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 项目基础信息控制器
 *
 * @author lth
 * @date  2024/07/18 19:32
 */
@Tag(name = "项目基础信息控制器")
@RestController
@Validated
public class ProjectBasicInfoController {

    @Resource
    private ProjectBasicInfoService projectBasicInfoService;

    /**
     * 获取项目基础信息分页
     *
     * @author lth
     * @date  2024/07/18 19:32
     */
    @Operation(summary = "获取项目基础信息分页")
    @SaCheckPermission("/biz/basicinfo/page")
    @GetMapping("/biz/basicinfo/page")
    public CommonResult<Page<ProjectBasicInfo>> page(ProjectBasicInfoPageParam projectBasicInfoPageParam) {
        return CommonResult.data(projectBasicInfoService.page(projectBasicInfoPageParam));
    }

    /**
     * 添加项目基础信息
     *
     * @author lth
     * @date  2024/07/18 19:32
     */
    @Operation(summary = "添加项目基础信息")
    @CommonLog("添加项目基础信息")
    @SaCheckPermission("/biz/basicinfo/add")
    @PostMapping("/biz/basicinfo/add")
    public CommonResult<String> add(@RequestBody @Valid ProjectBasicInfoAddParam projectBasicInfoAddParam) {
        projectBasicInfoService.add(projectBasicInfoAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑项目基础信息
     *
     * @author lth
     * @date  2024/07/18 19:32
     */
    @Operation(summary = "编辑项目基础信息")
    @CommonLog("编辑项目基础信息")
    @SaCheckPermission("/biz/basicinfo/edit")
    @PostMapping("/biz/basicinfo/edit")
    public CommonResult<String> edit(@RequestBody @Valid ProjectBasicInfoEditParam projectBasicInfoEditParam) {
        projectBasicInfoService.edit(projectBasicInfoEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除项目基础信息
     *
     * @author lth
     * @date  2024/07/18 19:32
     */
    @Operation(summary = "删除项目基础信息")
    @CommonLog("删除项目基础信息")
    @SaCheckPermission("/biz/basicinfo/delete")
    @PostMapping("/biz/basicinfo/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   List<ProjectBasicInfoIdParam> projectBasicInfoIdParamList) {
        projectBasicInfoService.delete(projectBasicInfoIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取项目基础信息详情
     *
     * @author lth
     * @date  2024/07/18 19:32
     */
    @Operation(summary = "获取项目基础信息详情")
    @SaCheckPermission("/biz/basicinfo/detail")
    @GetMapping("/biz/basicinfo/detail")
    public CommonResult<ProjectBasicInfo> detail(@Valid ProjectBasicInfoIdParam projectBasicInfoIdParam) {
        return CommonResult.data(projectBasicInfoService.detail(projectBasicInfoIdParam));
    }


    @PostMapping("/next-step")
    public CommonResult<Map<String, String>> nextStep(@RequestBody Map<String, String> request) {
        String nextStep = projectBasicInfoService.nextStep(request.get("currentStep"));
        Map<String, String> response = new HashMap<>();
        response.put("nextStep", nextStep);
        response.put("message", "跳转到下一步页面");
        return CommonResult.data(response);
    }

}
