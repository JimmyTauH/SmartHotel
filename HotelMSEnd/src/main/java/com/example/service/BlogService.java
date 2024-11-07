package com.example.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.example.common.enums.LikesModuleEnum;
import com.example.common.enums.RoleEnum;
import com.example.entity.*;
import com.example.mapper.BlogMapper;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 功能：帖子信息业务处理
 * 日期：2024/1/25 22:14
 */
@Service
public class BlogService {
    @Resource
    private BlogMapper blogMapper;
    @Resource
    private UserService userService;

    @Resource
    private LikesService likesService;

    @Resource
    private CollectService collectService;

    @Resource
    private CommentService commentService;

    public void add(Blog blog) {
        blog.setDate(DateUtil.today());
        Account currentUser = TokenUtils.getCurrentUser();
        if (currentUser.getRole().equals(RoleEnum.USER.name())) {
            blog.setUserId(currentUser.getId());
        }
        blogMapper.insert(blog);
    }

    public void update(Blog blog) {
        blogMapper.updateById(blog);
    }

    public void deleteById(Integer id) {
        blogMapper.deleteById(id);
    }

    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            deleteById(id);
        }
    }

    public Blog selectById(Integer blogId) {
        Blog blog = blogMapper.selectById(blogId);
        User user = userService.selectById(blog.getUserId());
        blog.setUser(user); //设置用户信息
        //查询当前帖子的点赞数据
        int likesCount = likesService.selectByFidAndModule(blogId, LikesModuleEnum.BlOG.getValue());
        blog.setLikesCount(likesCount);
        //查询当前帖子的收藏数据
        int collectCount = collectService.selectByFidAndModule(blogId, LikesModuleEnum.BlOG.getValue());
        blog.setCollectCount(collectCount);
        //查询用户是否点过赞
        Likes likes = likesService.selectUserLikes(blogId, LikesModuleEnum.BlOG.getValue());
        blog.setUserLike(likes != null);//设置用户是否点过赞
        //查询用户是否收藏
        Collect collect = collectService.selectUserCollect(blogId, LikesModuleEnum.BlOG.getValue());
        blog.setUserCollect(collect != null);
        //查询用户发布的帖子数量
        List<Blog> blogs = blogMapper.selectUserBlogCount(blog.getUserId());
        blog.setUserBlogCount(blogs.size());
        //查询用户被点赞和被收藏的数量
        int userLikeCount = 0;
        int userCollectCount = 0;
        for (Blog blog1 : blogs) {
            Integer fid = blog1.getId();
            userLikeCount += likesService.selectByFidAndModule(fid, LikesModuleEnum.BlOG.getValue());
            userCollectCount += collectService.selectByFidAndModule(fid, LikesModuleEnum.BlOG.getValue());
        }
        blog.setUserLikeCount(userLikeCount);
        blog.setUserCollectCount(userCollectCount);
        return blog;
    }

    public List<Blog> selectAll(Blog blog) {
        return blogMapper.selectAll(blog);
    }

    public List<Blog> selectBytitle(String title) {
        return blogMapper.selectBytitle("%" + title + "%");
    }

    public PageInfo<Blog> selectPage(Blog blog, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Blog> pageList = blogMapper.selectAll(blog);
        //查询当前帖子的点赞数据
        for (Blog b : pageList) {
            //设置当前帖子的点赞数据
            b.setLikesCount(likesService.selectByFidAndModule(b.getId(), LikesModuleEnum.BlOG.getValue()));
        }
        return PageInfo.of(pageList);
    }

    public List<Blog> selectBlogTop() {
        return blogMapper.selectBlogTop();
    }

    /**
     * 函数接受一个Integer类型的参数blogId，表示要获取推荐帖子的相关帖子ID。
     * 首先通过调用类中的selectById(blogId)方法，根据传入的blogId查询并获取对应帖子对象，并将其赋值给Blog类型变量blog。
     * 从获取到的blog对象中调用getTags()方法获取此帖子的标签信息，并存储在字符串变量tags中。
     * 初始化一个HashSet类型的blogSet，用于存放满足条件的推荐帖子对象。
     * 使用ObjectUtil.isNotEmpty(tags)判断帖子是否有标签信息，如果有，则执行以下操作：
     * a. 调用selectAll(null)方法获取数据库中所有帖子对象组成的列表。
     * b. 使用JSON工具库（这里是JSONUtil）解析字符串形式的标签信息为JSONArray对象，即tagsArr。
     * c. 遍历这个JSONArray对象，对于其中每一个标签（以tag表示），执行以下筛选逻辑：
     * i. 应用Java 8流(Stream)对所有帖子列表进行过滤(filter)操作，筛选出那些其标签集合中含有当前遍历到的标签（将tag转换为字符串）的帖子对象。
     * ii. 将过滤后的帖子对象集合转换为一个新的HashSet，并使用collect(Collectors.toSet())方法收集这些对象。
     * iii. 将上述收集到的包含特定标签的帖子HashSet添加到总的推荐帖子集合blogSet中。
     * 在完成上述循环后，blogSet中包含了所有与指定帖子具有相同标签的其他帖子。
     * 最终，函数返回填充了推荐帖子对象的blogSet集合。
     *
     * @param blogId
     * @return
     */
    public Set<Blog> selectRecommend(Integer blogId) {
        // 根据帖子ID查询帖子
        Blog blog = this.selectById(blogId);
        // 获取帖子的标签
        String tags = blog.getTags();
        // 创建一个HashSet集合用于存储筛选后的帖子列表
        Set<Blog> blogSet = new HashSet<>();
        // 如果帖子的标签不为空
        if (ObjectUtil.isNotEmpty(tags)) {
            // 查询所有的帖子列表
            List<Blog> blogs = this.selectAll(null);
            // 将帖子的标签转换为JSONArray数组
            JSONArray tagsArr = JSONUtil.parseArray(tags);
            // 遍历标签数组
            for (Object tag : tagsArr) {
                /*
                    筛选出包含当前帖子标签的其他的帖子列表
                 1  blogs.stream()：对原始集合blogs进行流式处理，将其转换为一个Stream流。
                    Stream是Java 8引入的一个强大的数据处理工具，可以方便地对集合中的元素执行各种批量操作。
                 2  .filter(blog1 -> blog1.getTags().contains(tag.toString()))：在得到的Stream上应用了一个filter()方法，
                    这个lambda表达式作为过滤器的条件。对于stream中的每一个帖子对象blog1，
                    它会检查其内部的标签集合getTags()是否包含指定的标签tag.toString()。
                    如果包含，则该帖子对象会被保留下来；如果不包含，则会被过滤掉。
                    && !blogId.equals(blog1.getId() 排除当前的帖子
                 3  .collect(Collectors.toSet())：这是对过滤后的Stream进行终端操作（即产生最终结果）的一种方式。
                    collect()方法用于将流中的元素收集到一个新的集合中，这里使用了Collectors.toSet()，
                    表示我们希望将所有满足条件的帖子对象收集到一个新的Set集合中。Set集合的特点是不包含重复元素，确保每个帖子对象在新集合中都是唯一的。
                 4  blogSet.addAll(...)：最后，将通过上述过程生成的新Set集合添加到原有的blogSet集合中，
                    即将所有带有指定标签的帖子对象添加到blogSet集合里
                 */
                blogSet.addAll(blogs.stream().filter(blog1 ->
                        blog1.getTags().contains(tag.toString()) && !blogId.equals(blog1.getId())).collect(Collectors.toSet()));

                /*
                    //两段代码是一样的，第一段代码是Java 8的流式编程，第二段代码是传统编程
                    Set<Blog> collect = blogs.stream().filter(new Predicate<Blog>() {
                        @Override
                        public boolean test(Blog blog) {
                            return blog.getTags().contains(tag.toString()) && !blogId.equals(blog1.getId();
                        }
                    }).collect(Collectors.toSet());
                    blogSet.addAll(collect);
                */
            }
        }
        // 返回筛选后的帖子列表
        // 返回前5个
        blogSet = blogSet.stream().limit(5).collect(Collectors.toSet());
        // 设置每个帖子的点赞数
        blogSet.forEach(b ->
                b.setLikesCount(likesService.selectByFidAndModule(b.getId(), LikesModuleEnum.BlOG.getValue())));
        return blogSet;
    }

    public void updateCount(Integer blogId) {
        blogMapper.updateCount(blogId);
    }

    // 用户帖子列表
    public PageInfo<Blog> selectUser(Blog blog, Integer pageNum, Integer pageSize) {
        Account currentUser = TokenUtils.getCurrentUser();
        // 如果当前用户是用户，则设置userId
        if (RoleEnum.USER.name().equals(currentUser.getRole())) {
            blog.setUserId(currentUser.getId());
        }
        return this.selectPage(blog, pageNum, pageSize);
    }

    // 用户点赞列表
    public PageInfo<Blog> selectLike(Blog blog, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Account currentUser = TokenUtils.getCurrentUser();
        // 如果当前用户是用户，则设置userId
        if (RoleEnum.USER.name().equals(currentUser.getRole())) {
            blog.setUserId(currentUser.getId());
        }
        List<Blog> blogList = blogMapper.selectLike(blog);
        PageInfo<Blog> blogPageInfo = PageInfo.of(blogList);
        List<Blog> pageList = blogPageInfo.getList();
        //查询当前帖子的点赞数据
        for (Blog b : pageList) {
            //设置当前帖子的点赞数据
            if (b != null && b.getId() != null) {
                b.setLikesCount(likesService.selectByFidAndModule(b.getId(), LikesModuleEnum.BlOG.getValue()));
            }
            //如果帖子已被删除，则删除
            if (b != null && b.getId() != null) {
                if (blogMapper.selectById(b.getId()) == null) {
                    pageList.remove(b);
                    //删除点赞
                    likesService.deleteByFidAndModule(b.getId(), LikesModuleEnum.BlOG.getValue());
                }
            }
        }
        return blogPageInfo;
    }

    public PageInfo<Blog> selectCollect(Blog blog, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Account currentUser = TokenUtils.getCurrentUser();
        // 如果当前用户是用户，则设置userId
        if (RoleEnum.USER.name().equals(currentUser.getRole())) {
            blog.setUserId(currentUser.getId());
        }
        List<Blog> blogList = blogMapper.selectCollect(blog);
        PageInfo<Blog> blogPageInfo = PageInfo.of(blogList);
        List<Blog> pageList = blogPageInfo.getList();
        //查询当前帖子的点赞数据
        for (Blog b : pageList) {
            //设置当前帖子的点赞数据
            if (b != null && b.getId() != null) {
                b.setLikesCount(likesService.selectByFidAndModule(b.getId(), LikesModuleEnum.BlOG.getValue()));
            }
            //如果帖子已被删除，则删除
            if (b != null && b.getId() != null) {
                if (blogMapper.selectById(b.getId()) == null) {
                    pageList.remove(b);
                    //删除收藏
                    collectService.deleteByFidAndModule(b.getId(), LikesModuleEnum.BlOG.getValue());
                }
            }
        }
        return blogPageInfo;
    }

    public PageInfo<Blog> selectComment(Blog blog, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Account currentUser = TokenUtils.getCurrentUser();
        // 如果当前用户是用户，则设置userId
        if (RoleEnum.USER.name().equals(currentUser.getRole())) {
            blog.setUserId(currentUser.getId());
        }
        List<Blog> blogList = blogMapper.selectComment(blog);
        PageInfo<Blog> blogPageInfo = PageInfo.of(blogList);
        List<Blog> pageList = blogPageInfo.getList();
        //查询当前帖子的点赞数据
        for (Blog b : pageList) {
            //设置当前帖子的点赞数据
            if (b != null && b.getId() != null) {
                b.setLikesCount(likesService.selectByFidAndModule(b.getId(), LikesModuleEnum.BlOG.getValue()));
            }
            //如果帖子已被删除，则删除
            if (b != null && b.getId() != null) {
                if (blogMapper.selectById(b.getId()) == null) {
                    pageList.remove(b);
                    //删除评论
                    commentService.deleteByFid(b.getId(), LikesModuleEnum.BlOG.getValue());
                }
            }
        }
        return blogPageInfo;
    }

}