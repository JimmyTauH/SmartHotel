package com.example.service;

import cn.hutool.core.date.DateUtil;

import com.example.common.enums.LikesModuleEnum;
import com.example.common.enums.RoleEnum;
import com.example.entity.*;
import com.example.mapper.ActivityMapper;

import com.example.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 功能：竞赛服务
 * 日期：2024/1/31 20:51
 */
@Service
public class ActivityService {

    @Resource
    private ActivityMapper activityMapper;

    @Resource
    private ActivitySignService activitySignService;

    @Resource
    private LikesService likesService;
    @Resource
    private CollectService collectService;

    public void add(Activity activity) {
        activityMapper.add(activity);
    }

    public void deleteById(Integer id) {
        activityMapper.deleteById(id);
    }

    public Integer updateRoom(Activity activity) {
        // 检查是否还有房间可以预订
        if (activity.getNumber() <= 0) {
            return 0; // 或者返回一个特定的值，表示没有可用房间
        }
        int startRoomNumber = 201;
        // 计算当前要分配的房间号
        int assignedRoomNumber = startRoomNumber + 1;
        // 预订一个房间，将房间数量减一
        activity.setNumber(activity.getNumber() - 1);
        // 更新数据库中的房间数量
        activityMapper.updateById(activity);
        // 返回分配的房间号给前端
        return assignedRoomNumber;
    }

    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            activityMapper.deleteById(id);
        }
    }

    public void updateById(Activity activity) {
        activityMapper.updateById(activity);
    }

    public Activity selectById(Integer id) {
        Activity activity = activityMapper.selectById(id);
        this.setAct(activity, TokenUtils.getCurrentUser());
        //获取点赞和收藏数据
        int likeCount = likesService.selectByFidAndModule(id, LikesModuleEnum.ACTIVITY.getValue());
        int collectCount = collectService.selectByFidAndModule(id, LikesModuleEnum.ACTIVITY.getValue());
        activity.setLikeCount(likeCount);
        activity.setCollectCount(collectCount);
        //设置用户是否点过赞、是否收过藏
        Likes likes = likesService.selectUserLikes(id, LikesModuleEnum.ACTIVITY.getValue());
        Collect collect = collectService.selectUserCollect(id, LikesModuleEnum.ACTIVITY.getValue());
        activity.setIsLike(likes != null);
        activity.setIsCollect(collect != null);
        return activity;
    }

    public List<Activity> selectAll(Activity activity) {
        return activityMapper.selectAll(activity);
    }

    public List<Activity> selectByName(String name) {
        return activityMapper.selectByName("%" + name + "%");
    }



    public PageInfo<Activity> selectPage(Activity activity, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Activity> list = activityMapper.selectAll(activity);
        PageInfo<Activity> activityPageInfo = PageInfo.of(list);
        List<Activity> activityList = activityPageInfo.getList();
        Account currentUser = TokenUtils.getCurrentUser();
        for (Activity act : activityList) {
            this.setAct(act, currentUser);
        }
        return PageInfo.of(list);
    }

    //设置竞赛信息
    private void setAct(Activity act, Account currentUser) {
        //竞赛是否结束
            /*
                该函数将act对象的end属性转换为日期对象，并与当前日期进行比较，
                如果end属性的日期早于当前日期，则将act对象的isEndActivity属性设置为true，否则设置为false。
             */
//        act.setIsEndActivity(DateUtil.parseDate(act.getEnd()).isBefore(new Date()));
        //查询用户是否报名竞赛
        ActivitySign activitySign = activitySignService.selectByActivityIdAndUserId(act.getId(), currentUser.getId());
        act.setIsSign(activitySign != null);
    }

    public List<Activity> selectBlogTop() {
        return activityMapper.selectBlogTop();
    }

    public void updateCount(Integer activityId) {
        activityMapper.updateCount(activityId);
    }



    public Integer selectHotelIdByActivityID(Integer actId){
        return activityMapper.selectHotelIdByActivityID(actId);
    }

    //查询用户报名的竞赛
    public PageInfo<Activity> selectUser(Activity activity, Integer pageNum, Integer pageSize) {
        Account currentUser = TokenUtils.getCurrentUser();
        PageHelper.startPage(pageNum, pageSize);
        if (RoleEnum.USER.name().equals(currentUser.getRole())) {
            activity.setUserId(currentUser.getId());
        }
        List<Activity> list = activityMapper.selectUser(activity);
        PageInfo<Activity> activityPageInfo = PageInfo.of(list);
        List<Activity> activityList = activityPageInfo.getList();
        for (Activity act : activityList) {
            this.setAct(act, currentUser);
        }
        return activityPageInfo;
    }

    public PageInfo<Activity> selectLike(Activity activity, Integer pageNum, Integer pageSize) {
        Account currentUser = TokenUtils.getCurrentUser();
        PageHelper.startPage(pageNum, pageSize);
        if (RoleEnum.USER.name().equals(currentUser.getRole())) {
            activity.setUserId(currentUser.getId());
        }
        List<Activity> list = activityMapper.selectLike(activity);
        PageInfo<Activity> activityPageInfo = PageInfo.of(list);
        List<Activity> activityList = activityPageInfo.getList();
        for (Activity act : activityList) {
            this.setAct(act, currentUser);
        }
        return activityPageInfo;
    }

    public PageInfo<Activity> selectCollect(Activity activity, Integer pageNum, Integer pageSize) {
        Account currentUser = TokenUtils.getCurrentUser();
        PageHelper.startPage(pageNum, pageSize);
        if (RoleEnum.USER.name().equals(currentUser.getRole())) {
            activity.setUserId(currentUser.getId());
        }
        List<Activity> list = activityMapper.selectCollect(activity);
        PageInfo<Activity> activityPageInfo = PageInfo.of(list);
        List<Activity> activityList = activityPageInfo.getList();
        for (Activity act : activityList) {
            this.setAct(act, currentUser);
        }
        return activityPageInfo;
    }

    public PageInfo<Activity> selectComment(Activity activity, Integer pageNum, Integer pageSize) {
        Account currentUser = TokenUtils.getCurrentUser();
        PageHelper.startPage(pageNum, pageSize);
        if (RoleEnum.USER.name().equals(currentUser.getRole())) {
            activity.setUserId(currentUser.getId());
        }
        List<Activity> list = activityMapper.selectComment(activity);
        PageInfo<Activity> activityPageInfo = PageInfo.of(list);
        List<Activity> activityList = activityPageInfo.getList();
        for (Activity act : activityList) {
            this.setAct(act, currentUser);
        }
        return activityPageInfo;
    }
}