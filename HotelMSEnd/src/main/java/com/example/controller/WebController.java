package com.example.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.example.common.Result;
import com.example.common.enums.ResultCodeEnum;
import com.example.common.enums.RoleEnum;
import com.example.entity.Account;
import com.example.service.AdminService;
import com.example.service.UserService;
import com.example.service.ServerService;
import com.example.service.RecepService;
import com.example.utils.ValideCodeUtils;
import org.springframework.web.bind.annotation.*;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;
import org.apache.commons.mail.HtmlEmail;
/**
 * 基础前端接口
 */
@RestController
public class WebController {

    @Resource
    private AdminService adminService;

    @Resource
    private UserService userService;

    @Resource
    private ServerService serverService;

    @Resource
    private RecepService recepService;
    @GetMapping("/")
    public Result hello() {
        return Result.success("访问成功");
    }

    /**
     * 登录
     */
    @PostMapping("/login")
    public Result login(@RequestBody Account account) {
        if (ObjectUtil.isEmpty(account.getUsername()) || ObjectUtil.isEmpty(account.getPassword())
                || ObjectUtil.isEmpty(account.getRole())) {
            return Result.error(ResultCodeEnum.PARAM_LOST_ERROR);
        }
        if (RoleEnum.ADMIN.name().equals(account.getRole())) {
            account = adminService.login(account);
        }else if (RoleEnum.USER.name().equals(account.getRole())) {
            account = userService.login(account);
        }else if (RoleEnum.SERVER.name().equals(account.getRole())) {
            account = serverService.login(account);
        }else if (RoleEnum.RECEP.name().equals(account.getRole())) {
            account = recepService.login(account);
        }else {
            return Result.error(ResultCodeEnum.PARAM_ERROR);
        }
        return Result.success(account);
    }

    /**
     * 注册
     */
    @PostMapping("/register")
    public Result register(@RequestBody Account account) {
        if (StrUtil.isBlank(account.getUsername()) || StrUtil.isBlank(account.getPassword())
                || ObjectUtil.isEmpty(account.getRole())) {
            return Result.error(ResultCodeEnum.PARAM_LOST_ERROR);
        }
        if (RoleEnum.USER.name().equals(account.getRole())) {
            userService.register(account);
        }else if (RoleEnum.SERVER.name().equals(account.getRole())) {
            serverService.register(account);
        }else if (RoleEnum.RECEP.name().equals(account.getRole())) {
            recepService.register(account);
        }else{
            return Result.error(ResultCodeEnum.PARAM_ERROR);
        }
        return Result.success();
    }

    @PutMapping("/updatePassword")
    public Result updatePassword(@RequestBody Account account) {
        if (StrUtil.isBlank(account.getUsername()) || StrUtil.isBlank(account.getPassword())
                || ObjectUtil.isEmpty(account.getNewPassword())) {
            return Result.error(ResultCodeEnum.PARAM_LOST_ERROR);
        }
        if (RoleEnum.ADMIN.name().equals(account.getRole())) {
            adminService.updatePassword(account);
        }else if (RoleEnum.USER.name().equals(account.getRole())) {
            userService.updatePassword(account);
        }else if (RoleEnum.RECEP.name().equals(account.getRole())) {
            recepService.updatePassword(account);
        }else if (RoleEnum.SERVER.name().equals(account.getRole())) {
            serverService.updatePassword(account);
        }
        return Result.success();
    }

}
