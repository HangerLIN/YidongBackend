package vip.xiaonuo.biz.modular.New.subtemplate.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import vip.xiaonuo.biz.modular.New.subtemplate.entity.Subtemplate;
import vip.xiaonuo.biz.modular.New.template.service.TemplateService;
import vip.xiaonuo.common.pojo.CommonResult;

import java.util.HashMap;
import java.util.List;

@RestController
@Tag(name = "用户控制器")
public class UserController {

    @Resource
    private TemplateService templateService;

    // 根据业务Id查询所有模版Id及序号
    @GetMapping("/biz/new/subtemplate/selectTemplate/{id}")
    @Operation(summary = "查询模版信息Id")
    public CommonResult<List<HashMap<Long,Integer>>> selectSubTemplate(@PathVariable("id") Long Transaction_id) {

        return CommonResult.data(templateService.getTemplateIdByTransactionId(Transaction_id));
    }






}
