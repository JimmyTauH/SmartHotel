package com.example.service;

import cn.hutool.core.date.DateUtil;
import com.example.common.enums.RoleEnum;
import com.example.entity.Account;
import com.example.entity.Comment;
import com.example.mapper.CommentMapper;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 业务处理
 **/
@Service
public class CommentService {

    @Resource
    private CommentMapper commentMapper;

    /**
     * 新增
     */
    public void add(Comment comment) {
        if (StringUtil.isEmpty(comment.getContent())) {
            return;
        }
        comment.setTime(DateUtil.now());
        Account currentUser = TokenUtils.getCurrentUser();
        //判断是否是用户，只有用户才用设置userId
        if (RoleEnum.USER.name().equals(currentUser.getRole())) {
            comment.setUserId(currentUser.getId());
        }
        commentMapper.insert(comment);
        //判断插入的rootId是否为空，是的话就是根节点
        if (comment.getRootId() == null) {
            //插入数据后拿到返回id，再来设置rootId
            comment.setRootId(comment.getId());
            commentMapper.updateById(comment);
        }
    }

    /**
     * 删除
     */
    public void deleteById(Integer id) {
        commentMapper.deleteById(id);
    }

    /**
     * 批量删除
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            commentMapper.deleteById(id);
        }
    }

    /**
     * 修改
     */
    public void updateById(Comment comment) {
        commentMapper.updateById(comment);
    }

    /**
     * 根据ID查询
     */
    public Comment selectById(Integer id) {
        return commentMapper.selectById(id);
    }

    /**
     * 查询所有
     */
    public List<Comment> selectAll(Comment comment) {
        return commentMapper.selectAll(comment);
    }

    /**
     * 分页查询
     */
    public PageInfo<Comment> selectPage(Comment comment, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Comment> list = commentMapper.selectAll(comment);
        return PageInfo.of(list);
    }

    public List<Comment> selectForUser(Comment comment) {
        //查询全部评论的父评论
        List<Comment> commentList = commentMapper.selectForUser(comment);
        //遍历父评论
        for (Comment comment1 : commentList) {
            //查询回复列表
            Comment param = new Comment();
            param.setRootId(comment1.getId());
            List<Comment> children = commentMapper.selectAll(param);
            //过滤自己
            children = children.stream().filter(new Predicate<Comment>() {
                @Override
                public boolean test(Comment c) {
                    return !c.getId().equals(comment1.getId());
                }
            }).collect(Collectors.toList());
            comment1.setChildrenCommentList(children);
        }
        return commentList;
    }

    public Integer selectCommentCount(@Param("fid") Integer fid, @Param("module") String module) {
        return commentMapper.selectCommentCount(fid, module);
    }

    public void deleteByFid(Integer fid, String module) {
        commentMapper.deleteByFidAndModule(fid, module);
    }
}