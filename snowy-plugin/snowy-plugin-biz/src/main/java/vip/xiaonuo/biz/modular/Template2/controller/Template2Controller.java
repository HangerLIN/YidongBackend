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
package vip.xiaonuo.biz.modular.Template2.controller;

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
import vip.xiaonuo.biz.modular.Template2.entity.Template2;
import vip.xiaonuo.biz.modular.Template2.param.Template2AddParam;
import vip.xiaonuo.biz.modular.Template2.param.Template2EditParam;
import vip.xiaonuo.biz.modular.Template2.param.Template2IdParam;
import vip.xiaonuo.biz.modular.Template2.param.Template2PageParam;
import vip.xiaonuo.biz.modular.Template2.service.Template2Service;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

/**
 * Template2控制器
 *
 * @author gaobaoqu
 * @date  2024/08/24 09:07
 */
@Tag(name = "Template2控制器")
@RestController
@Validated
public class Template2Controller {

    @Resource
    private Template2Service template2Service;

    /**
     * 获取Template2分页
     *
     * @author gaobaoqu
     * @date  2024/08/24 09:07
     */
    @Operation(summary = "获取Template2分页")
    @SaCheckPermission("/biz/Template2/page")
    @GetMapping("/biz/Template2/page")
    public CommonResult<Page<Template2>> page(Template2PageParam template2PageParam) {
        return CommonResult.data(template2Service.page(template2PageParam));
    }

    /**
     * 添加Template2
     *
     * @author gaobaoqu
     * @date  2024/08/24 09:07
     */
    @Operation(summary = "添加Template2")
    @CommonLog("添加Template2")
    @SaCheckPermission("/biz/Template2/add")
    @PostMapping("/biz/Template2/add")
    public CommonResult<String> add(@RequestBody @Valid Template2AddParam template2AddParam) {
        template2Service.add(template2AddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑Template2
     *
     * @author gaobaoqu
     * @date  2024/08/24 09:07
     */
    @Operation(summary = "编辑Template2")
    @CommonLog("编辑Template2")
    @SaCheckPermission("/biz/Template2/edit")
    @PostMapping("/biz/Template2/edit")
    public CommonResult<String> edit(@RequestBody @Valid Template2EditParam template2EditParam) {
        template2Service.edit(template2EditParam);
        return CommonResult.ok();
    }

    /**
     * 删除Template2
     *
     * @author gaobaoqu
     * @date  2024/08/24 09:07
     */
    @Operation(summary = "删除Template2")
    @CommonLog("删除Template2")
    @SaCheckPermission("/biz/Template2/delete")
    @PostMapping("/biz/Template2/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   List<Template2IdParam> template2IdParamList) {
        template2Service.delete(template2IdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取Template2详情
     *
     * @author gaobaoqu
     * @date  2024/08/24 09:07
     */
    @Operation(summary = "获取Template2详情")
    @SaCheckPermission("/biz/Template2/detail")
    @GetMapping("/biz/Template2/detail")
    public CommonResult<Template2> detail(@Valid Template2IdParam template2IdParam) {
        return CommonResult.data(template2Service.detail(template2IdParam));
    }
}
