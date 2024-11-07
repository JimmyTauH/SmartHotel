package com.example.controller;

import com.example.common.Result;
import com.example.entity.Recep;
import com.example.service.RecepService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 管理员前端操作接口
 **/
@RestController
@RequestMapping("/recep")
public class RecepController {

    @Resource
    private RecepService recepService;

    /**
     * 新增
     */
    @PostMapping("/add")
    public Result add(@RequestBody Recep recep) {
        recepService.add(recep);
        return Result.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        recepService.deleteById(id);
        return Result.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        recepService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public Result updateById(@RequestBody Recep recep) {
        recepService.updateById(recep);
        return Result.success();
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        Recep recep = recepService.selectById(id);
        return Result.success(recep);
    }

    /**
     * 查询所有
     */
    @GetMapping("/selectAll")
    public Result selectAll(Recep recep ) {
        List<Recep> list = recepService.selectAll(recep);
        return Result.success(list);
    }

    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
    public Result selectPage(Recep recep,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Recep> page = recepService.selectPage(recep, pageNum, pageSize);
        return Result.success(page);
    }

}