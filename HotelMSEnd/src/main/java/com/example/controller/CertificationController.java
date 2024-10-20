package com.example.controller;

import com.example.common.Result;
import com.example.entity.Certification;
import com.example.service.CertificationService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 功能：认证创作者信息
 * 日期：2023/11/30 21:33
 */
@RestController
@RequestMapping("/certification")
public class CertificationController {

    @Resource
    private CertificationService certificationService;

    /**
     * 添加
     *
     * @param certification
     */
    @PostMapping("/add")
    public Result add(@RequestBody Certification certification) {
        certificationService.add(certification);
        return Result.success();
    }

    /**
     * 修改
     * @param certification
     */
    @PutMapping("/update")
    public Result update(@RequestBody Certification certification) {
        certificationService.update(certification);
        return Result.success();
    }

    /**
     * 根据id删除
     *
     * @param id
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        certificationService.deleteById(id);
        return Result.success();
    }

    /**
     * 根据userid删除
     *
     * @param userId
     */
    @DeleteMapping("/deleteByUserId/{userId}")
    public Result deleteByUserId(@PathVariable Integer userId) {
        certificationService.deleteByUserId(userId);
        return Result.success();
    }

    /**
     * 批量删除
     *
     * @param ids
     */
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        certificationService.deleteBatch(ids);
        return Result.success();
    }

    @GetMapping("/selectById/{id}")
    public Certification selectById(@PathVariable Integer id) {
        return certificationService.selectById(id);
    }


    @GetMapping("/selectAll")
    public Result selectAll(Certification certification) {

        List<Certification> certifications = certificationService.selectAll(certification);
        return Result.success(certifications);
    }

    @GetMapping("/selectPage")
    public Result selectPage(Certification certification,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Certification> certificationPageInfo = certificationService.selectPage(certification, pageNum, pageSize);
        return Result.success(certificationPageInfo);
    }
    /**
     * 查询当前用户的认证信息
     */
    @GetMapping("/selectUserCertification")
    public Result selectUserCertification(){
        Certification certification = certificationService.selectUserCertification();
        return Result.success(certification);
    }
}