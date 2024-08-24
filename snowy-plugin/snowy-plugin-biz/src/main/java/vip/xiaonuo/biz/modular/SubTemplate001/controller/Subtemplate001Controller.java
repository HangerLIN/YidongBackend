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
package vip.xiaonuo.biz.modular.SubTemplate001.controller;

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
import vip.xiaonuo.biz.modular.SubTemplate001.entity.Subtemplate001;
import vip.xiaonuo.biz.modular.SubTemplate001.param.Subtemplate001AddParam;
import vip.xiaonuo.biz.modular.SubTemplate001.param.Subtemplate001EditParam;
import vip.xiaonuo.biz.modular.SubTemplate001.param.Subtemplate001IdParam;
import vip.xiaonuo.biz.modular.SubTemplate001.param.Subtemplate001PageParam;
import vip.xiaonuo.biz.modular.SubTemplate001.service.Subtemplate001Service;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

/**
 * SubTemplate001控制器
 *
 * @author gaobaoqu
 * @date  2024/08/24 10:27
 */
@Tag(name = "SubTemplate001控制器")
@RestController
@Validated
public class Subtemplate001Controller {

    @Resource
    private Subtemplate001Service subtemplate001Service;

    /**
     * 获取SubTemplate001分页
     *
     * @author gaobaoqu
     * @date  2024/08/24 10:27
     */
    @Operation(summary = "获取SubTemplate001分页")
    @SaCheckPermission("/biz/SubTemplate001/page")
    @GetMapping("/biz/SubTemplate001/page")
    public CommonResult<Page<Subtemplate001>> page(Subtemplate001PageParam subtemplate001PageParam) {
        return CommonResult.data(subtemplate001Service.page(subtemplate001PageParam));
    }

    /**
     * 添加SubTemplate001
     *
     * @author gaobaoqu
     * @date  2024/08/24 10:27
     */
    @Operation(summary = "添加SubTemplate001")
    @CommonLog("添加SubTemplate001")
    @SaCheckPermission("/biz/SubTemplate001/add")
    @PostMapping("/biz/SubTemplate001/add")
    public CommonResult<String> add(@RequestBody @Valid Subtemplate001AddParam subtemplate001AddParam) {
        subtemplate001Service.add(subtemplate001AddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑SubTemplate001
     *
     * @author gaobaoqu
     * @date  2024/08/24 10:27
     */
    @Operation(summary = "编辑SubTemplate001")
    @CommonLog("编辑SubTemplate001")
    @SaCheckPermission("/biz/SubTemplate001/edit")
    @PostMapping("/biz/SubTemplate001/edit")
    public CommonResult<String> edit(@RequestBody @Valid Subtemplate001EditParam subtemplate001EditParam) {
        subtemplate001Service.edit(subtemplate001EditParam);
        return CommonResult.ok();
    }

    /**
     * 删除SubTemplate001
     *
     * @author gaobaoqu
     * @date  2024/08/24 10:27
     */
    @Operation(summary = "删除SubTemplate001")
    @CommonLog("删除SubTemplate001")
    @SaCheckPermission("/biz/SubTemplate001/delete")
    @PostMapping("/biz/SubTemplate001/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   List<Subtemplate001IdParam> subtemplate001IdParamList) {
        subtemplate001Service.delete(subtemplate001IdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取SubTemplate001详情
     *
     * @author gaobaoqu
     * @date  2024/08/24 10:27
     */
    @Operation(summary = "获取SubTemplate001详情")
    @SaCheckPermission("/biz/SubTemplate001/detail")
    @GetMapping("/biz/SubTemplate001/detail")
    public CommonResult<Subtemplate001> detail(@Valid Subtemplate001IdParam subtemplate001IdParam) {
        return CommonResult.data(subtemplate001Service.detail(subtemplate001IdParam));
    }
}
