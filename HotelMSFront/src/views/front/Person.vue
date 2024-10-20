<template>
  <div class="main-content" style="width: 50%">

    <el-tabs v-model="activeName" @tab-click="clickTab">
      <el-tab-pane label="个人信息" name="个人信息">
        <PersonPage @update:user="updateUser"/>
      </el-tab-pane>
      <el-tab-pane label="我的帖子" name="我的帖子">
        <div style="margin-top: 10px">
          <BlogList type="user" :showOpt="true"/>
        </div>
      </el-tab-pane>
      <el-tab-pane label="已报名酒店" name="已报名酒店">
        <ActivityList type="user" :span="12"/>
      </el-tab-pane>
      <el-tab-pane label="我的点赞" name="我的点赞">
        <div class="card" style="display: flex;padding: 5px">
          <div class="category-btn" :class="{'active': likesCurrent === '帖子'}" @click="likesCurrent = '帖子'">帖子
          </div>
          <div class="category-btn" :class="{'active': likesCurrent === '酒店'}" @click="likesCurrent = '酒店'">酒店
          </div>
        </div>
        <div style="margin-top: 10px">
          <BlogList v-if="likesCurrent === '帖子'" type="like"></BlogList>
          <ActivityList v-if="likesCurrent === '酒店'" type="like" :span="12"/>
        </div>
      </el-tab-pane>
      <el-tab-pane label="我的收藏" name="我的收藏">
        <div class="card" style="display: flex;padding: 5px">
          <div class="category-btn" :class="{'active': collectCurrent === '帖子'}" @click="collectCurrent='帖子'">帖子
          </div>
          <div class="category-btn" :class="{'active': collectCurrent === '酒店'}" @click="collectCurrent='酒店'">酒店
          </div>
        </div>
        <div style="margin-top: 10px">
          <BlogList v-if="collectCurrent === '帖子'" type="collect"></BlogList>
          <ActivityList v-if="collectCurrent === '酒店'" type="collect" :span="12"/>
        </div>
      </el-tab-pane>
      <el-tab-pane label="我的评论" name="我的评论">
        <div class="card" style="display: flex;padding: 5px">
          <div class="category-btn" :class="{'active': commentCurrent === '帖子'}" @click="commentCurrent='帖子'">帖子
          </div>
          <div class="category-btn" :class="{'active': commentCurrent === '酒店'}" @click="commentCurrent='酒店'">酒店
          </div>
        </div>
        <div style="margin-top: 10px">
          <BlogList v-if="commentCurrent === '帖子'" type="comment"></BlogList>
          <ActivityList v-if="commentCurrent === '酒店'" type="comment" :span="12"/>
        </div>
      </el-tab-pane>
      <el-tab-pane label="我的关注" name="我的关注">
        <div class="card" style="margin-top: 10px">
          <ConcernList/>
        </div>
      </el-tab-pane>
<!--      <el-tab-pane label="我的福利" name="我的福利">-->
<!--        <div class="card" style="margin-top: 10px">-->
<!--          <MyWelfare/>-->
<!--        </div>-->
<!--      </el-tab-pane>-->
    </el-tabs>

    <Footer/>
  </div>
</template>

<script>
import Footer from "@/components/Footer.vue";
import PersonPage from "@/components/PersonPage.vue";
import BlogList from "@/components/BlogList.vue";
import ActivityList from "@/components/ActivityList.vue";
import ConcernList from "@/components/ConcernList.vue";
import MyWelfare from "@/components/MyWelfare.vue";
export default {
  data() {
    return {
      activeName: '个人信息',
      likesCurrent: '帖子',
      collectCurrent: '帖子',
      commentCurrent: '帖子',
      user: JSON.parse(localStorage.getItem('xm-user') || '{}')
    }
  },
  components: {
    Footer,
    PersonPage,
    BlogList,
    ActivityList,
    ConcernList,
    MyWelfare
  },
  created() {

  },
  methods: {
    updateUser() {
      // 触发父级的数据更新
      this.$emit('update:user')
    },
    clickTab() {

    },
  }
}
</script>

<style>
.category-btn {
  width: fit-content;
  padding: 5px 10px;
  border-radius: 5px;
  cursor: pointer;
}

.active {
  background-color: #409EFF;
  color: white;
}
</style>