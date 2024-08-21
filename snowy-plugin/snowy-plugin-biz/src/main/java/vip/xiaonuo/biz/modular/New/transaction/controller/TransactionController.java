package vip.xiaonuo.biz.modular.New.transaction.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vip.xiaonuo.biz.modular.New.transaction.entity.Transaction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import vip.xiaonuo.biz.modular.New.transaction.service.TransactionService;
import vip.xiaonuo.common.pojo.CommonResult;


@RestController
@Tag(name = "业务控制器")
public class TransactionController {


    @Resource
    private TransactionService transactionService;

    @PostMapping("/biz/new/transaction/add")
    @Operation(summary = "增加业务信息")
    public CommonResult<String> addTransaction(@RequestBody Transaction transaction) {

        transactionService.save(transaction);

        return CommonResult.ok("添加成功");
    }

    @GetMapping("/biz/new/transaction/delete/{id}")
    @Operation(summary = "删除业务信息")
    public CommonResult<String> deleteTransaction(@PathVariable("id") Long id) {
        transactionService.removeById(id);

        return CommonResult.ok("删除成功");
    }

    @GetMapping("/biz/new/transaction/get/{id}")
    @Operation(summary = "获取业务信息")
    public CommonResult<Transaction> getTransactionById(@PathVariable("id") Long id) {
        return CommonResult.data(transactionService.getById(id));
    }

    @GetMapping
    @Operation(summary = "获取业务分页信息")
    public CommonResult<Page<Transaction>> listTransactions(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        return CommonResult.data(transactionService.page(new Page<>(page, size)));
    }
}

