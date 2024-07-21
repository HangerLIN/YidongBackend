package vip.xiaonuo.biz.modular.subjectinfo.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import vip.xiaonuo.biz.modular.subjectinfo.dto.SubProjcetParam;
import vip.xiaonuo.biz.modular.subjectinfo.service.SubprojectService;
import vip.xiaonuo.common.pojo.CommonResult;

import java.util.List;


@Tag(name = "子项目信息控制器")
@RestController
public class SubProjectInfoController {

    @Resource
    private SubprojectService subprojectService;

    @Operation(summary = "添加子项目信息")
    //  @SaCheckPermission("/biz/subjectinfoinfo/add")
    @PostMapping("/biz/subjectinfoinfo/add")
    public CommonResult<String> addSubProject(@RequestBody SubProjcetParam subProjcetParam) {

        try {
            // 获取子项目列表
            List<SubProjcetParam.SubprojectInfo> subproject = subProjcetParam.getSubproject();

            //遍历所有子项目
            for (SubProjcetParam.SubprojectInfo sp : subproject) {
                //添加数据库
                subprojectService.addSubProject(sp);
            }
            return CommonResult.ok();

        } catch (Exception e) {
            return CommonResult.error("插入子项目失败！");
        }

    }

    @Operation(summary = "查询子项目信息")
    @GetMapping("/biz/subjectinfoinfo/select")
    public CommonResult<SubProjcetParam> selectbyProuctId(Long ProuctId) throws Exception {
        if (ProuctId <= 0) {
            throw new Exception("项目不存在！");
        }

        List<SubProjcetParam.SubprojectInfo> list = subprojectService.selectbyProuctId(ProuctId);
        if (list == null) {
            throw new Exception("项目不存在！");
        }
        SubProjcetParam subProjcetParam = new SubProjcetParam();
        subProjcetParam.setSubproject(list);

        return CommonResult.data(subProjcetParam);

    }

    @Operation(summary = "逻辑删除子项目")
    @GetMapping("/biz/subjectinfoinfo/delete")
    public CommonResult<SubProjcetParam> deletebySubProuctId(Long SubProuctId) throws Exception {
        if (SubProuctId <= 0) {
            throw new Exception("子项目不存在！");
        }

        boolean s = subprojectService.deleteSubProject(SubProuctId);
        if(s){
            return CommonResult.ok("子项目删除成功！");
        }
        else {
            return CommonResult.error("子项目删除失败！");
        }
    }
}
