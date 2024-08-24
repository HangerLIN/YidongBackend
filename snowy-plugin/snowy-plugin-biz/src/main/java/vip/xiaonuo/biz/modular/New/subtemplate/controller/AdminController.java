package vip.xiaonuo.biz.modular.New.subtemplate.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import vip.xiaonuo.biz.modular.New.subtemplate.dto.SubtemplateParam;
import vip.xiaonuo.biz.modular.New.subtemplate.entity.Subtemplate;
import vip.xiaonuo.biz.modular.New.subtemplate.service.SubtemplateService;
import vip.xiaonuo.biz.modular.New.template.entity.Template;
import vip.xiaonuo.common.pojo.CommonResult;

import java.util.List;

@RestController
@Tag(name = "管理员控制器")
public class AdminController {

    @Resource
    private SubtemplateService subtemplateService;

    // 添加模版
//    @PostMapping("/biz/new/subtemplate/add")
//    @Operation(summary = "增加子模版信息")
//    public CommonResult<String> addSubTemplate(@RequestBody SubtemplateParam subtemplateParam) {
//        subtemplateService.addSubtemplateInfo(subtemplateParam);
//        return CommonResult.ok("添加成功");
//    }


    // 查询模版
//    @GetMapping("/biz/new/subtemplate/select/{id}")
//    @Operation(summary = "查询子模版信息")
//    public CommonResult<List<Subtemplate>> selectSubTemplate(@PathVariable("id") Long TemplateId) {
//
//        return CommonResult.data(subtemplateService.getSubtemplatesByTemplateId(TemplateId));
//    }

}
