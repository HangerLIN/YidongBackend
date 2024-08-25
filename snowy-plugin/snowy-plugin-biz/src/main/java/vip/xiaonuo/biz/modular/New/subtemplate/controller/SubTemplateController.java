package vip.xiaonuo.biz.modular.New.subtemplate.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import vip.xiaonuo.biz.modular.New.message.service.MessageService;
import vip.xiaonuo.biz.modular.New.subtemplate.dto.SubmoduleFragementParam;
import vip.xiaonuo.biz.modular.New.subtemplate.dto.SubtemplateInputParam;
import vip.xiaonuo.biz.modular.New.subtemplate.dto.SubtemplateParam;
import vip.xiaonuo.biz.modular.New.subtemplate.entity.Subtemplate;
import vip.xiaonuo.biz.modular.New.subtemplate.service.SubtemplateService;
import vip.xiaonuo.biz.modular.New.subtemplate.vo.SubtemplateOutput;
import vip.xiaonuo.common.pojo.CommonResult;

import java.util.ArrayList;
import java.util.List;


@RestController
@Tag(name = "子模版控制器")
public class SubTemplateController {

    @Resource
    private StringRedisTemplate stringRedisTemplate;


    @Resource
    private SubtemplateService subtemplateService;

    @Resource
    private MessageService messageService;

    // 添加子模版
    @PostMapping("/biz/new/subtemplate/add")
    @Operation(summary = "增加子模版信息")
    public CommonResult<String> addSubTemplate(@RequestBody SubtemplateParam subtemplateParam) {
        List<SubtemplateParam.Submoule> submouleList = subtemplateParam.getSubmouleList();
        for (SubtemplateParam.Submoule submoule : submouleList) {
//            System.out.println("------------------------------------------------");
//            System.out.println(submoule);

            Subtemplate subtemplate = BeanUtil.copyProperties(submoule, Subtemplate.class);
            String startYearEq = submoule.getStartYearEq();
            subtemplate.setStartyearEq(startYearEq);

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

    // 根据模版ID分段获取传来的数据
    @PostMapping("/biz/new/subtemplate/addFragment")
    @Operation(summary = "添加子模版片段信息")
    public CommonResult<String> addSubtemplateFragmentById(@RequestBody SubmoduleFragementParam submouleDVFragement) {
        Long projectId = submouleDVFragement.getProjectId();
        Long templateId = submouleDVFragement.getTemplateId();
        Long subtemplateId = submouleDVFragement.getSubtemplateId();
        List<SubtemplateInputParam.SubmouleDV.Data> data = submouleDVFragement.getData();

        SubtemplateInputParam.SubmouleDV submouleDV = new SubtemplateInputParam.SubmouleDV();
        submouleDV.setProjectId(projectId);
        submouleDV.setSubtemplateId(subtemplateId);
        submouleDV.setData(data);

        String Id = projectId.toString() + ":" + templateId.toString();
        String str = JSONUtil.toJsonStr(submouleDV);

        stringRedisTemplate.opsForList().rightPush(Id, str);

        return CommonResult.ok("添加成功");
    }


    @PostMapping("/biz/new/subtemplate/calculate")
    @Operation(summary = "计算子模版结果")
    public CommonResult<SubtemplateOutput> calculateSubTemplate(@RequestBody SubtemplateInputParam subtemplateInputParam) {
        List<Subtemplate> subtemplateList = new ArrayList<>();

        //获取前端传来的某张sheet的所有子模版的数据
        List<SubtemplateInputParam.SubmouleDV> submouleDVList = subtemplateInputParam.getSubmouleDVList();

        //根据子模版ID获取子模版
        for (SubtemplateInputParam.SubmouleDV submouleDV : submouleDVList) {
            subtemplateList.add(subtemplateService.getById(submouleDV.getSubtemplateId()));
        }
        SubtemplateOutput subtemplateOutput = subtemplateService.calculate(subtemplateList, subtemplateInputParam);
        return CommonResult.data(subtemplateOutput);
    }

    // 添加子模版
    @GetMapping("/biz/new/subtemplate/calculate2/{proId}/{tempId}")
    @Operation(summary = "计算子模版结果2")
    public CommonResult<SubtemplateOutput> calculateSubTemplate2(@PathVariable("proId") Long Projectid, @PathVariable("tempId") Long Templateid) {


        String Id = Projectid.toString() + ":" + Templateid.toString();

        Long size = stringRedisTemplate.opsForList().size(Id);

        if (size != null && size.intValue() > 0) {
            // 使用range来获取列表的全部或部分元素
            // 0表示列表的第一个元素，size表示结束位置（不包括）
            List<String> List = stringRedisTemplate.opsForList().range(Id, 0, size);

            List<SubtemplateInputParam.SubmouleDV> submouleDVList = new ArrayList<>();

            assert List != null;
            for (String subtemplate : List) {
                SubtemplateInputParam.SubmouleDV submouleDV = JSONUtil.toBean(subtemplate, SubtemplateInputParam.SubmouleDV.class);
                submouleDVList.add(submouleDV);
            }

            SubtemplateInputParam subtemplateInputParam = new SubtemplateInputParam();
            subtemplateInputParam.setSubmouleDVList(submouleDVList);


            List<Subtemplate> subtemplateList = new ArrayList<>();

            //根据子模版ID获取子模版
            for (SubtemplateInputParam.SubmouleDV submouleDV : submouleDVList) {
                subtemplateList.add(subtemplateService.getById(submouleDV.getSubtemplateId()));
            }
            SubtemplateOutput subtemplateOutput = subtemplateService.calculate(subtemplateList, subtemplateInputParam);
            return CommonResult.data(subtemplateOutput);
        }

        return null;
    }
}

