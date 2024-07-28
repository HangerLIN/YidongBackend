package vip.xiaonuo.biz.modular.income.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import vip.xiaonuo.biz.modular.income.dto.IncomeParam;

import vip.xiaonuo.biz.modular.income.dto.IncomeSaveParam;
import vip.xiaonuo.biz.modular.income.entity.Income;
import vip.xiaonuo.biz.modular.income.service.IncomeService;
import vip.xiaonuo.biz.modular.income.vo.IncomeVO;

import vip.xiaonuo.common.pojo.CommonResult;

import java.util.List;

@Tag(name = "收入控制器")
@RestController
public class IncomeController {
    @Resource
    private IncomeService incomeService;


    @Operation(summary = "保存并返回子项目使用情况")
    @PostMapping("/biz/income/saveAndReturn")
    public CommonResult<IncomeVO> writeUsingInfo(@RequestBody IncomeParam incomeParam) throws Exception {


        // 1. 写入数据库
        List<Income> incomeList = incomeService.writeSubProjetIncomeInfo(incomeParam);

        // 2. 响应返回
        IncomeVO incomeVO = incomeService.returnSubProjetIncomeInfo(incomeList);

        return CommonResult.data(incomeVO);
    }

//    @Operation(summary = "保存返回子项目使用情况")
//    @PostMapping("/biz/income/save")
//    public CommonResult<IncomeVO> saveUsingInfo(@RequestBody IncomeSaveParam incomeSaveParam) {
//        List<String> incomeIdList = incomeSaveParam.getIncomeIdList();
//
//        // String JsonMessage = ResourceUtil.readUtf8Str("incomeVO.json");
//        // IncomeVO Meta = JSONUtil.toBean(JsonMessage, IncomeVO.class);
//        // return CommonResult.data(Meta);
//
////        IncomeVO incomeVO = incomeService.caculateSubProjetIncomeInfo(incomeIdList);
//        return CommonResult.data(incomeVO);
//
//    }
}
