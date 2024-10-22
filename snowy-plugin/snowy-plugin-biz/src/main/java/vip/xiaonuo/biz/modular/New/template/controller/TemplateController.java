package vip.xiaonuo.biz.modular.New.template.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import vip.xiaonuo.biz.modular.New.template.entity.Template;
import vip.xiaonuo.biz.modular.New.template.service.TemplateService;
import vip.xiaonuo.biz.modular.New.transaction.entity.Transaction;
import vip.xiaonuo.common.pojo.CommonResult;


@RestController
@Tag(name = "模版控制器")
public class TemplateController {
    @Resource
    private TemplateService templateService;

    // 添加模版
    @PostMapping("/biz/new/template/add")
    @Operation(summary = "增加模版信息")
    public CommonResult<String> addTemplate(@RequestBody Template template) {
        templateService.save(template);
        return CommonResult.ok("添加成功");
    }


    // 删除模版
    @GetMapping("/biz/new/template/delete/{id}")
    @Operation(summary = "删除模版信息")
    public CommonResult<String> deleteTemplate(@PathVariable("id") Long id) {
        templateService.removeById(id);
        return CommonResult.ok("删除成功");
    }

    // 根据ID获取模版
    @GetMapping("/biz/new/template/get/{id}")
    @Operation(summary = "获取模版信息")
    public CommonResult<Template> getTemplateById(@PathVariable("id") Long id) {
        return CommonResult.data(templateService.getById(id));
    }

    // 列表查询模版
    @GetMapping("/biz/new/template/page")
    @Operation(summary = "获取模版分页信息")
    public CommonResult<Page<Template>> listTemplates(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        return CommonResult.data(templateService.page(new Page<>(page, size)));
    }


}

