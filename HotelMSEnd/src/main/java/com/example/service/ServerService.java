package com.example.service;

import cn.hutool.core.util.ObjectUtil;
import com.example.common.Constants;
import com.example.common.enums.ResultCodeEnum;
import com.example.common.enums.RoleEnum;
import com.example.entity.Account;
import com.example.entity.Certification;
import com.example.entity.Server;
import com.example.exception.CustomException;
import com.example.mapper.ServerMapper;
import com.example.utils.TokenUtils;
import com.example.utils.ValideCodeUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * 功能：用户服务
 * 日期：2024/1/16 16:33
 */
@Service
public class ServerService {
    @Resource
    private ServerMapper serverMapper;

    @Resource
    private CertificationService certificationService;

    //添加用户
    public void add(Server user) {
        Server dbuser = this.selectByUsername(user.getUsername());
        //判断用户名是否存在
        if (ObjectUtil.isNotNull(dbuser)) {
            throw new CustomException(ResultCodeEnum.USER_EXIST_ERROR);
        }
        //默认密码
        if (ObjectUtil.isEmpty(user.getPassword())) {
            user.setPassword(Constants.USER_DEFAULT_PASSWORD);  //默认密码 123
        }
        //默认用户名
        if (ObjectUtil.isEmpty(user.getName())) {
            user.setName(user.getUsername());
        }
        user.setIntegral(BigDecimal.ZERO);
        //设置用户角色
        user.setRole(RoleEnum.SERVER.name());
        serverMapper.insert(user);
    }

    //根据id查询用户
    public Server selectById(Integer id) {
        return serverMapper.selectById(id);
    }

    //批量查询用户
    public List<Server> selectBatch(Server user) {
        return serverMapper.selectAll(user);
    }

    //根据用户名查询用户
    public Server selectByUsername(String username) {
        return serverMapper.selectByUsername(username);
    }

    public void deleteById(Integer id) {
        if (id == null) {
            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        }
        serverMapper.deleteById(id);
    }

    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            this.deleteById(id);
        }
    }

    public void update(Server user) {
        serverMapper.update(user);
    }

    public PageInfo<Server> selectPage(Server user, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Server> users = serverMapper.selectAll(user);
        return PageInfo.of(users);
    }

    //登录
    public Account login(Account account) {
        Account dbUser = serverMapper.selectByUsername(account.getUsername());
        if (ObjectUtil.isNull(dbUser)) {
            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        }
        if (!account.getPassword().equals(dbUser.getPassword())) {
            throw new CustomException(ResultCodeEnum.USER_ACCOUNT_ERROR);
        }
        //生成token
        String tokenData = dbUser.getId() + "-" + RoleEnum.USER.name();
        String token = TokenUtils.createToken(tokenData, dbUser.getPassword());
        dbUser.setToken(token);
        // 判断是否认证通过,如果认证通过，则设置用户为创作者
        Certification selectedByUserId = certificationService.selectByUserId(dbUser.getId());
        if (ObjectUtil.isNotNull(selectedByUserId) && "审核通过".equals(selectedByUserId.getStatus())) {
            dbUser.setIsCreator(true);
        }
        return dbUser;
    }

    //注册用户
    public void register(Account account) {
        Server user = new Server();
        BeanUtils.copyProperties(account, user);
        this.add(user);
    }

    public String sendEmail(String email) {
        //这里先随机生成四位验证码，然后发送过去
        String code = String.valueOf(ValideCodeUtils.generateValidateCode(4));
        try {
            HtmlEmail mail = new HtmlEmail();
            /*发送邮件的服务器 126邮箱为smtp.126.com,163邮箱为163.smtp.com，QQ为smtp.qq.com*/
            mail.setHostName("smtp.qq.com");
            /*不设置发送的消息有可能是乱码*/
            mail.setCharset("UTF-8");
            /*IMAP/SMTP服务的密码 username为你开启发送验证码功能的邮箱号 password为你在qq邮箱获取到的一串字符串*/
            mail.setAuthentication("3195523611@qq.com", "nvevhkcuqlyrdehd");
            /*发送邮件的邮箱和发件人*/
            mail.setFrom("3195523611@qq.com", "EasyRoot智能运维工具");
            /*使用安全链接*/
            mail.setSSLOnConnect(true);
            /*接收的邮箱*/
            mail.addTo(email);
            /*设置邮件的主题*/
            mail.setSubject("登录验证码");
            /*设置邮件的内容*/
            mail.setMsg("尊敬的用户:你好! 登录验证码为:" + code + "(有效期为一分钟)");
            mail.send();//发送
            //在此时前端获得这个验证码
            return code;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "0000";
    }
    //修改密码
    public void updatePassword(Account account) {
        Server dbuser = serverMapper.selectByUsername(account.getUsername());
        if (ObjectUtil.isNull(dbuser)) {
            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        }
        if (!account.getPassword().equals(dbuser.getPassword())) {
            throw new CustomException(ResultCodeEnum.PARAM_PASSWORD_ERROR);
        }
        dbuser.setPassword(account.getNewPassword());
        serverMapper.update(dbuser);
    }

    @Transactional
    //充值
    public void updateIntegral(Server user) {
        Account currentUser = TokenUtils.getCurrentUser();
        Server dbUser = this.selectById(currentUser.getId());
        if (dbUser.getIntegral().intValue()==0){
            dbUser.setIntegral(new BigDecimal(0));
        }
        dbUser.setIntegral(dbUser.getIntegral().add(user.getIntegral()));
        serverMapper.updateIntegral(dbUser);
        RecordsService.addRecord(user.getIntegral(), "充值");
    }

    @Transactional
    //签到
    public void updateIntegral2(Server user) {
        Account currentUser = TokenUtils.getCurrentUser();
        Server dbUser = this.selectById(currentUser.getId());
        if (dbUser.getIntegral().intValue()==0){
            dbUser.setIntegral(new BigDecimal(0));
        }
        dbUser.setIntegral(dbUser.getIntegral().add(user.getIntegral()));
        serverMapper.updateIntegral(dbUser);
    }

    public void updateIntegralByWelfare(Integer id, BigDecimal integral) {
        serverMapper.updateIntegralByWelfare(id, integral);
    }
}