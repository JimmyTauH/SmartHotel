<template>
  <div class="main-content">
    <div style="display: flex">
      <div class="left" style="flex: 1">

        <div class="card" style="padding: 20px 30px; margin-bottom: 10px">
          <!--标题-->
          <div style="font-weight: bold;font-size: 26px; margin-bottom: 20px">
            {{ blog.title }}
          </div>
          <!--标签-->
          <div style="color: #999999; font-size: 12px; margin-bottom: 5px">
            <span style="margin-right: 20px"><i class="el-icon-user"
                                                style="margin-right: 3px"></i>{{ blog.userName }}</span>
            <span style="margin-right: 20px"><i class="el-icon-date"
                                                style="margin-right: 3px"></i>{{ blog.date }}</span>
            <span style="margin-right: 20px"><i class="el-icon-eye"
                                                style="margin-right: 3px"></i>{{ blog.readCount }}</span>
          </div>
          <div style="margin-bottom: 20px">
            <el-tag v-for="item in tagsArr" :key="item.id" type="primary" style="margin-right: 3px" size="mini">
              {{ item }}
            </el-tag>
          </div>
          <!--关联酒店-->
          <div class="custom-card" style="margin-bottom: 10px" v-if="blog.contestId != null" @click="goDetail(contest.id)">
            <div style="display: flex; grid-gap: 20px">
              <img :src="contest.cover" style="width: 50%; border-radius: 5px; height: auto" alt="">
              <div style="flex: 1">
                <div style="font-weight: bold;font-size: 24px;margin-bottom: 20px;margin-top: 10px">{{ contest.name }}</div>
                <div style="color: #666666; font-size: 13px; line-height: 22px;text-align: justify;margin-bottom: 20px;margin-right: 20px">
                  {{ contest.descr }}
                </div>
                <div style="color: #666666;margin-bottom: 10px">
                  <i class="el-icon-date" style="margin-right: 5px"></i>
                  <span style="margin-right: 5px">时间</span>
                  <span>{{ contest.start }} - {{ contest.end }}</span>
                </div>
                <div style="margin-bottom: 10px; color: #666666">
                  <i class="el-icon-location-outline" style="margin-right: 5px"></i>
                  <span style="margin-right: 5px">地址</span>
                  <span v-if="contest.form === '线上'">
                      <a style="color: #409EFF" :href="contest.address" target="_blank">{{ contest.address }}</a>
                      <el-tag size="mini" style="margin-left: 10px">{{ contest.form }}</el-tag>
                    </span>
                  <span v-if="contest.form === '线下'">
                      {{ contest.address }}
                      <el-tag size="mini" style="margin-left: 10px">{{ contest.form }}</el-tag>
                    </span>
                </div>
              </div>
            </div>
          </div>
          <!--内容-->
          <div class="w-e-text" style="width: 100%">
            <div v-html="blog.content"></div>
          </div>
        </div>



        <!--点赞、收藏-->
        <div class="card" style="text-align: center; font-size: 20px; color: #666; margin-bottom: 10px">
          <span style="margin-right: 20px; cursor: pointer" @click="setLikes" :class="{'active' : blog.userLike}"><i
              class="el-icon-like"></i> {{ blog.likesCount }}</span>
          <span style=" cursor: pointer" @click="setCollect" :class="{'active' : blog.userCollect}"><i
              class="el-icon-star-off"></i>{{ blog.collectCount }}</span>
        </div>
        <!--评论-->
        <Comment :fid="blogId" :title="blog.title" :module="module" :replyId="blog.user.id"></Comment>
      </div>

      <div class="right" style="width: 260px;margin-left: 10px">
        <div class="card" style="margin-bottom: 10px">
          <div style="display: flex;grid-gap: 10px;align-items: center;margin-bottom: 10px">
            <img :src="blog.user?.avatar" alt="" @click="homePage(blog.user.id)" style="width: 50px; height: 50px; border-radius: 50%">
            <div style="flex: 1">
              <div style="font-weight: bold;margin-bottom: 5px">{{ blog.user?.name }}</div>
              <div class="line2" style="color: #666666;font-size: 12px">{{ blog.user?.info }}</div>
            </div>
          </div>
          <div style="display: flex">
            <div style="flex: 1; text-align: center">
              <div style="margin-bottom: 5px">文章</div>
              <div style="color: #888">{{ blog.userBlogCount }}</div>
            </div>
            <div style="flex: 1; text-align: center">
              <div style="margin-bottom: 5px">点赞</div>
              <div style="color: #888">{{ blog.userLikeCount }}</div>
            </div>
            <div style="flex: 1; text-align: center">
              <div style="margin-bottom: 5px">收藏</div>
              <div style="color: #888">{{ blog.userCollectCount }}</div>
            </div>
          </div>
        </div>

        <div class="card" style="margin-bottom: 10px">
          <div
              style="font-weight: bold; font-size: 20px; padding-bottom: 10px; border-bottom: 1px solid #ddd; margin-bottom: 10px">
            相关推荐
          </div>

          <div>
            <div style="margin-bottom: 15px" v-for="item in recommendList" :key="item.id">
              <a :href="'/front/blogDetail?blogId=' + item.id" target="_blank">
                <div class="line2 reCommendTitle">{{ item.title }}</div>
              </a>
              <div style="color: #888">
                <span>阅读</span> <span>{{ item.readCount }}</span>
                <span style="margin-left: 10px">点赞</span> <span>{{ item.likesCount }}</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>

</template>
<script>
import Comment from "@/components/Comment";
export default {
  name: "BlogDetail",
  components: {Comment},
  data() {
    return {
      blogId: this.$route.query.blogId,
      blog: {},
      contest: {},
      tagsArr: [],
      recommendList: [],
      module: '帖子'
    }
  },
  created() {
    this.load()
    //更新阅读量
    this.$request.put('/blog/updateCount/' + this.blogId)
  },
  methods: {
    goDetail(id) {
      window.open('/front/ActivityDetail?activityId=' + id)
    },
    homePage(userId){
      this.$router.push('/front/HomePage?userId=' + userId)
    },
    load() {
      this.$request.get("/blog/select/" + this.blogId).then(res => {
        this.blog = res.data || {}
        this.tagsArr = JSON.parse(this.blog.tags || '[]')
        if (this.blog.contestId) {
          this.loadContest(this.blog.contestId);
        }
      })

      this.$request.get("/blog/selectRecommend/" + this.blogId).then(res => {
        this.recommendList = res.data || []
      })
    },
    loadContest(contestId) {
      this.$request.get(`/activity/selectById/${contestId}`).then(res => {
        this.contest = res.data;
      });
    },
    setLikes() {
      this.$request.post("/likes/set", {fid: this.blogId, module: '帖子'}).then(res => {
        if (res.code === '200') {
          this.$message.success("操作成功")
        }
        this.load() // 刷新页面
      })
    },
    setCollect() {
      this.$request.post("/collect/set/collect", {fid: this.blogId, module: '帖子'}).then(res => {
        if (res.code === '200') {
          this.$message.success("操作成功")
        }
        this.load()// 刷新页面
      })
    }
  }
}

</script>

<style>
/* blockquote 样式 */
blockquote {
  display: block;
  border-left: 8px solid #d0e5f2;
  padding: 20px 10px;
  margin: 10px 0;
  line-height: 1.4;
  font-size: 100%;
  background-color: #d9d9d9;
}

/* code 样式 */
code {
  display: inline-block;
  background-color: #909399;
  border-radius: 3px;
  padding: 3px 5px;
  margin: 3px 3px;
}

pre code {
  display: block;
}

p {
  line-height: 15px
}

.line2 {
  word-break: break-all;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 2; /* 超出几行省略 */
  overflow: hidden;
}

.active {
  color: orange !important;
}

.reCommendTitle {
  margin-bottom: 5px
}

.reCommendTitle:hover {
  color: #409EFF;
}

.custom-card {
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2); /* Adds a subtle shadow */
  transition: 0.3s; /* Smooth transition for hover effects */
  border-radius: 10px; /* Rounded corners for a modern look */
  cursor: pointer;
}

.custom-card:hover {
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.4); /* Enhanced shadow on hover */
}

</style>