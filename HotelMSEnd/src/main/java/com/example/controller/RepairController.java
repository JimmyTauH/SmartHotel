package com.example.controller;

import com.example.common.Result;
import com.example.entity.Repair;
import com.example.service.ActivityService;
import com.example.service.ActivitySignService;
import com.example.service.RepairService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 故障报告前端接口
 **/
@RestController
@RequestMapping("/repair")
public class RepairController {

    @Resource
    private RepairService repairService;
    @Resource
    private ActivitySignService activitySignService;
    @Resource
    private ActivityService activityService;
    /**
     * 新增
     */
    @PostMapping("/add")
    public Result add(@RequestBody Repair repair) {
        Integer userid = repair.getUser();
        Integer act_id = activitySignService.selectActIdByUserId(userid);
        Integer hotel_id = activityService.selectHotelIdByActivityID(act_id);
        repair.setHotel(hotel_id);
        repairService.add(repair);
        return Result.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        repairService.deleteById(id);
        return Result.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        repairService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public Result updateById(@RequestBody Repair repair) {
        repairService.updateById(repair);
        return Result.success();
    }

    @PutMapping("/updateState/{id}")
    public Result updateState(@PathVariable Integer id) {
        repairService.updateState(id);
        return Result.success();
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        Repair repair = repairService.selectById(id);
        return Result.success(repair);
    }

    /**
     * 查询用户
     */
    @GetMapping("/selectByUser/{userid}")
    public Result selectByUser(@PathVariable Integer userid) {
        List<Repair> list = repairService.selectByUser(userid);
        return Result.success(list);
    }


    /**
     * 根据酒店
     */
    @GetMapping("/selectByHotel/{hotel_id}")
    public Result selectByHotel(@PathVariable Integer hotel_id) {
        List<Repair> list = repairService.selectByHotel(hotel_id);
        return Result.success(list);
    }

    /**
     * 查询所有
     */
    @GetMapping("/selectAll")
    public Result selectAll(Repair repair) {
        List<Repair> list = repairService.selectAll(repair);
        return Result.success(list);
    }

    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
    public Result selectPage(Repair repair,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Repair> page = repairService.selectPage(repair, pageNum, pageSize);
        return Result.success(page);
    }

}