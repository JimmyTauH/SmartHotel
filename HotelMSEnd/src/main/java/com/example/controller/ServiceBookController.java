package com.example.controller;

import com.example.common.Result;
import com.example.entity.ServiceBook;
import com.example.service.ServiceBookService;
//import com.example.mapper.ActivitySignMapper;
import com.example.service.ActivitySignService;
//import com.example.mapper.ActivityMapper;
import com.example.service.ActivityService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;


import javax.annotation.Resource;
import java.util.List;

/**
 * 服务申请前端接口
 **/
@RestController
@RequestMapping("/serviceBook")
public class ServiceBookController {

    @Resource
    private ServiceBookService serviceBookService;
    @Resource
    private ActivitySignService activitySignService;
    @Resource
    private ActivityService activityService;
    /**
     * 新增
     */
    @PostMapping("/add")
    public Result add(@RequestBody ServiceBook notice) {
        Integer userid = notice.getUser();
        Integer act_id = activitySignService.selectActIdByUserId(userid);
        Integer hotel_id = activityService.selectHotelIdByActivityID(act_id);
        notice.setHotel(hotel_id);
        serviceBookService.add(notice);
        return Result.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        serviceBookService.deleteById(id);
        return Result.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        serviceBookService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public Result updateById(@RequestBody ServiceBook notice) {
        serviceBookService.updateById(notice);
        return Result.success();
    }

    @PutMapping("/updateState/{id}")
    public Result updateState(@PathVariable Integer id) {
        serviceBookService.updateState(id);
        return Result.success();
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        ServiceBook notice = serviceBookService.selectById(id);
        return Result.success(notice);
    }

    /**
     * 查询用户
     */
    @GetMapping("/selectByUser/{userid}")
    public Result selectByUser(@PathVariable Integer userid) {
        List<ServiceBook> list = serviceBookService.selectByUser(userid);
        return Result.success(list);
    }


    /**
     * 根据酒店
     */
    @GetMapping("/selectByHotel/{hotel_id}")
    public Result selectByHotel(@PathVariable Integer hotel_id) {
        List<ServiceBook> list = serviceBookService.selectByHotel(hotel_id);
        return Result.success(list);
    }

    /**
     * 查询所有
     */
    @GetMapping("/selectAll")
    public Result selectAll(ServiceBook notice) {
        List<ServiceBook> list = serviceBookService.selectAll(notice);
        return Result.success(list);
    }

    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
    public Result selectPage(ServiceBook notice,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<ServiceBook> page = serviceBookService.selectPage(notice, pageNum, pageSize);
        return Result.success(page);
    }

}