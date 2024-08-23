package vip.xiaonuo.biz.modular.New.subtemplate.controller;

import cn.hutool.core.bean.BeanUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import vip.xiaonuo.biz.modular.New.message.service.MessageService;
import vip.xiaonuo.biz.modular.New.subtemplate.dto.SubtemplateInputParam;
import vip.xiaonuo.biz.modular.New.subtemplate.dto.SubtemplateParam;
import vip.xiaonuo.biz.modular.New.subtemplate.entity.Subtemplate;
import vip.xiaonuo.biz.modular.New.subtemplate.service.SubtemplateService;
import vip.xiaonuo.biz.modular.New.subtemplate.vo.SubtemplateOutput;
import vip.xiaonuo.biz.modular.New.template.entity.Template;
import vip.xiaonuo.common.pojo.CommonResult;

import java.util.ArrayList;
import java.util.List;


@RestController
@Tag(name = "子模版控制器")
public class SubTemplateController {
    @Resource
    private SubtemplateService subtemplateService;

    @Resource
    private MessageService messageService;

    // 添加子模版
    @PostMapping("/biz/new/subtemplate/add")
    @Operation(summary = "增加子模版信息")
    public CommonResult<String> addSubTemplate(@RequestBody SubtemplateParam subtemplateParam) {
        List<SubtemplateParam.Submoule> submouleList = subtemplateParam.getSubmouleList();
        for(SubtemplateParam.Submoule submoule : submouleList){
//            System.out.println("------------------------------------------------");
//            System.out.println(submoule);
            Subtemplate subtemplate = BeanUtil.copyProperties(submoule, Subtemplate.class);
//            System.out.println(subtemplate);
//            System.out.println("------------------------------------------------");
            subtemplateService.save(subtemplate);
        }
        return CommonResult.ok("添加成功");
    }

    // 根据ID获取子模版
    @GetMapping("/biz/new/subtemplate/get/{id}")
    @Operation(summary = "获取子模版信息")
    public CommonResult<Subtemplate> getSubtemplateById(@PathVariable("id") Long id) {
        return CommonResult.data(subtemplateService.getById(id));
    }

    // 添加子模版
    @PostMapping("/biz/new/subtemplate/calculate")
    @Operation(summary = "计算子模版结果")
    public CommonResult<SubtemplateOutput> calculateSubTemplate(@RequestBody SubtemplateInputParam subtemplateInputParam){
        List<Subtemplate> subtemplateList = new ArrayList<>();

        //获取前端传来的某张sheet的所有子模版的数据
        List<SubtemplateInputParam.SubmouleDV> submouleDVList = subtemplateInputParam.getSubmouleDVList();

        //根据子模版ID获取子模版
        for(SubtemplateInputParam.SubmouleDV submouleDV : submouleDVList){
            subtemplateList.add(subtemplateService.getById(submouleDV.getSubtemplateId()));
        }
        SubtemplateOutput subtemplateOutput = subtemplateService.calculate(subtemplateList, subtemplateInputParam);




        return CommonResult.data(subtemplateOutput);
    }
}

