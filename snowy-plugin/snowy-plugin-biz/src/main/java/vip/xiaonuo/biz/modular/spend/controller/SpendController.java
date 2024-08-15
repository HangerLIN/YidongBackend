package vip.xiaonuo.biz.modular.spend.controller;


import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.json.JSONUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import vip.xiaonuo.biz.modular.spend.dto.SpendParam;
import vip.xiaonuo.biz.modular.spend.entity.Spend;
import vip.xiaonuo.biz.modular.spend.service.SpendService;
import vip.xiaonuo.biz.modular.spend.vo.SpendVO;
import vip.xiaonuo.common.pojo.CommonResult;

import java.net.URL;
import java.util.List;

@Tag(name = "开支控制器")
@RestController
public class SpendController {

    @Resource
    private SpendService service;

    @Operation(summary = "保存并返回子项目开支情况")
    @PostMapping("/biz/spend/save")
    public CommonResult<SpendVO> saveSpendInfo(@RequestBody SpendParam spendParam) throws Exception {
        //1.保存子项目花费信息到数据库
        SpendVO spendVO = service.saveSubjectSpendInfo(spendParam);
        return CommonResult.data(spendVO);
    }

    @Operation(summary = "查询费用信息")
    @GetMapping("/spend/details")
    public CommonResult<List<Spend>> getSpendDetails(@RequestParam Long subprojectId) throws Exception {
        if (subprojectId == null || subprojectId <= 0) {
            throw new Exception("子项目ID不合法！");
        }

        List<Spend> spends = service.getSpendDetailsBySubprojectId(subprojectId);
        if (spends == null || spends.isEmpty()) {
            throw new Exception("未找到相关费用信息");
        }

        return CommonResult.data(spends);
    }

}
