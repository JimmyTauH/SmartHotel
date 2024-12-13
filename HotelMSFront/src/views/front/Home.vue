<template>
  <div class="main-content">
    
    <div class="con">
      <div style="position: absolute; font-size: 40px; font-weight: bold;color:white; font-style: italic; margin-top: 120px; margin-left: 22%">旅行订酒店,就上HotelMS√</div>
    <div>
      <img src="@/assets/imgs/carousel.jpg" style="width: 140%;margin-left: -20%; height: 300px; border-bottom-left-radius: 40%; border-bottom-right-radius: 40%" alt="">
    </div>
    <div style="width: 80%; margin: 30px auto; text-align: center">
      <div style="text-align: center; font-size: 25px; font-weight: bold">酒店服务升级</div>
      <div style="display: flex; margin-top: 30px">
        <div style="flex: 1">
          <img src="@/assets/imgs/icon-11.png" style="width: 50px; height: 50px" alt="">
          <div style="font-size: 14px; margin-top: 10px; color: #455873FF">知名酒店，客房充足，选择多多</div>
        </div>
        <div style="flex: 1;">
          <img src="@/assets/imgs/icon-22.png" style="width: 50px; height: 50px" alt="">
          <div style="font-size: 14px; margin-top: 10px; color: #455873FF">专业服务，房间干净，放心入住</div>
        </div>
        <div style="flex: 1">
          <img src="@/assets/imgs/icon-33.png" style="width: 50px; height: 50px" alt="">
          <div style="font-size: 14px; margin-top: 10px; color: #455873FF">安全便捷，服务保障，用心生活</div>
        </div>
      </div>
    </div>
    <div style="width: 60%; margin: 50px auto; text-align: left">
      <div style="text-align: center; font-size: 25px; font-weight: bold">平台优质酒店</div>
    </div>


    <div class="container">
      <!--左边分类部分-->
      
      <div class="left-column card" style="margin-left: -150px;">
        <div class="category-item" :class="{ 'category-item-active': item.name === current }"
             v-for="item in categoryList" :key="item.id" @click="selectCategory(item.name)">{{ item.name }}
        </div>
      </div>
      <!--中间正文部分-->
 
      <div class="main-column" style="width: 100%;">
        <!--博客列表-->
        <div class="card blog-list">
          <!-- <el-button type="primary" @click="addBlog" v-if="user.isCreator" class="button-new-post">发表新酒店</el-button> -->
          <!-- <el-button type="warning" @click="goToApply" v-if="!user.isCreator" class="button-apply">点此认证后使用发帖功能</el-button> -->
          <BlogList :categoryName="current"></BlogList>
          <Footer></Footer>
        </div>
      </div>
      <!--右边酒店、热门文章展示部分-->
      <div class="right-column" style="margin-top: 0px;">
        <!--欢迎语-->
        <!--热门帖子-->
        <div class="card hot-articles">
          <div class="hot-articles-header">
            <div class="hot-articles-title">热门房型推荐  </div>
            <div class="refresh-button" @click="refreshTop"><i class="el-icon-refresh"></i>换一换</div>
          </div>
          <div>
            <div v-for="item in showList" :key="item.id" class="hot-article-item line1">
              <a :href="'/front/blogDetail?blogId=' + item.id" target="_blank">
                <span class="hot-article-index">
                  <span v-if="item.index === 1" class="first">{{ item.index }}</span>
                  <span v-else-if="item.index === 2" class="second">{{ item.index }}</span>
                  <span v-else-if="item.index === 3" class="third">{{ item.index }}</span>
                  <span v-else>{{ item.index }}</span>
                </span>
                <span class="hot-article-title">{{ item.title }}</span>
              </a>
            </div>
          </div>
        </div>
      </div>


    </div>
  </div>
  </div>
</template>

<script>
import Footer from "@/components/Footer.vue";
import BlogList from "@/components/BlogList.vue";

export default {
  name: "Home",
  components: {
    Footer,
    BlogList
  },
  data() {
    return {
      current: '全部酒店',  //当前选择的分类名称，默认为全部
      categoryList: [],
      topList: [],  //排行榜数据
      showList: [],   //展示的topList
      lastNum: 0,
      tableData: [],
      user: JSON.parse(localStorage.getItem('xm-user') || '{}')
    }
  },
  mounted() {
    this.load()
    this.refreshTop()
    this.loadActivity()
  },
  methods: {
    loadActivity() {
      this.$request.get("/activity/selectActivityTop").then(res => {
        this.tableData = res.data || []
        this.tableData = this.tableData.slice(0, 5)  // 展示前5个酒店
        this.startCountdown()
      })
    },
    refreshTop() {
      this.$request.get("/blog/selectBlogTop").then(res => {
        //如果已经到了最后一页，就从第一页开始展示
        if (this.lastNum >= this.topList.length) {
          this.lastNum = 0
        }
        this.topList = res.data || []
        //给topList的每个元素添加序号
        for (let i = this.lastNum; i < this.lastNum + 5 && i < this.topList.length; i++) {
          this.topList[i].index = i - this.lastNum + 1
        }
        //展示前5个
        this.showList = this.topList.slice(this.lastNum, this.lastNum + 5)
        //展示下一页5个
        this.lastNum += 5
      })
    },
    selectCategory(categoryName) {
      this.current = categoryName
    },
    load() {
      //请求分类数据
      this.$request.get('/category/selectAll').then(res => {
        this.categoryList = res.data || []
        this.categoryList.unshift({name: '全部酒店'})
      })
    },
    goToApply() {
      this.$router.push('/front/Certification')
    },
    addBlog() {
      window.open('/front/NewBlog')
    },
    startCountdown() {
      this.updateCountdown()
      setInterval(this.updateCountdown, 1000);
    },
    updateCountdown() {
      if (this.tableData == null || this.tableData.length === 0) {
        return;
      }
      const now = new Date();
      this.tableData.forEach(item => {
        const applyStartTime = new Date(item.applystart);
        if (applyStartTime > now) {
          this.$set(item, 'isApplyStart', false);
        } else {
          this.$set(item, 'isApplyStart', true);
        }

        const applyEndTime = new Date(item.applyend);
        if (applyEndTime >= now) {
          const applyRemainingTime = this.calculateRemainingTime(now, applyEndTime);
          this.$set(item, 'applyRemainingDays', applyRemainingTime.days);
          this.$set(item, 'applyRemainingHours', applyRemainingTime.hours);
          this.$set(item, 'isApplyEnd', false);
        } else {
          this.$set(item, 'applyRemainingDays', 0);
          this.$set(item, 'applyRemainingHours', 0);
          this.$set(item, 'isApplyEnd', true);
        }

        const startTime = new Date(item.start);
        if (startTime > now) {
          const remainingTime = this.calculateRemainingTime(now, startTime);
          this.$set(item, 'remainingDays', remainingTime.days);
          this.$set(item, 'remainingHours', remainingTime.hours);
          this.$set(item, 'isStartActivity', false);
        } else {
          this.$set(item, 'remainingDays', 0);
          this.$set(item, 'remainingHours', 0);
          this.$set(item, 'isStartActivity', true);
        }

        const endTime = new Date(item.end);
        if (endTime >= now) {
          this.$set(item, 'isEndActivity', false);
        } else {
          this.$set(item, 'isEndActivity', true);
        }
      });
    },
    calculateRemainingTime(startTime, endTime) {
      const diff = endTime - startTime;
      const days = Math.floor(diff / (1000 * 60 * 60 * 24));
      const hours = Math.floor((diff % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
      return {days, hours};
    }
  }
}
</script>

<style>
.container {
  display: flex;
  align-items: flex-start;
  grid-gap: 10px;
}

.left-column {
  width: 150px;
  align-self: flex-start; /* Align to the top */
}

.main-column {
  flex: 1;
}

.right-column {
  width: 260px;
  align-self: flex-start; /* Align to the top */
}

.card {
  margin-bottom: 10px;
}

.system_title {
  font-size: 20px;
  font-weight: bold;
}

.category-item {
  text-align: center;
  width: 100px;
  height: 50px;
  padding: 10px;
  font-size: 16px;
  cursor: pointer;
}

.category-item-active {
  background-color: #1890ff;
  color: #fff;
  border-radius: 5px;
}

.line1 {
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

a {
  color: #333333;
}

.competion-carousel {
  position: relative;
  width: 100%;
  overflow: hidden;
  margin-bottom: 10px;
  flex: 2;
  margin-left: -160px;
}

.announcements {
  margin-bottom: 10px;
  flex: 1;
  margin-right: -270px;
}

.announcement-item {
  margin-bottom: 5px;
}

.separator {
  border-top: 1px solid #e2e0e0;
  margin-top: 5px;
  margin-bottom: 3px;
}

.announcement-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.announcement-name {
  font-size: 16px;
  width: 300px;
}

.countdown-footer {
  display: flex;
  justify-content: flex-end;
  align-items: center;
  padding-bottom: 5px;
}

.end-text {
  color: gray;
  font-weight: bold;
}

.blog-list {
  flex: 1;
}

.button-new-post,
.button-apply {
  margin-bottom: 10px;
}

.welcome-card {
  margin-top: 0; /* Remove the margin-top and align with main content */
}

.welcome-header {
  font-size: 20px;
  font-weight: bold;
  margin-bottom: 10px;
}

.welcome-message {
  color: #666;
}

.hot-articles {
  margin-bottom: 10px;
}

.hot-articles-header {
  display: flex;
  padding-bottom: 10px;
  border-bottom: 1px solid #ddd;
}

.hot-articles-title {
  font-size: 20px;
  flex: 1;
}

.refresh-button {
  font-size: 12px;
  color: #666;
  cursor: pointer;
}

.hot-article-item {
  margin: 10px 0;
}

.hot-article-index {
  display: inline-block;
  margin-right: 10px;
  text-align: right;
  width: 20px;
}

.hot-article-title {
  color: #666;
}

.first {
  color: orangered;
}

.second {
  color: goldenrod;
}

.third {
  color: #409EFF;
}
</style>