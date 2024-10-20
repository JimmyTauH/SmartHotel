<template>
  <div>
    <div class="front-notice"><i class="el-icon-bell" style="margin-right: 2px"></i>公告：{{ top }}</div>
    <!--头部-->
    <div class="front-header" style="margin-bottom: 20px;">
      <div class="front-header-left" style="margin-left: 0px;">
        <img src="@/assets/imgs/logo.png" alt="">
        <div class="title">HotelMS酒店预订
        </div>
      </div>
      <div class="front-header-center">
        <div class="front-header-nav">
          <el-menu :default-active="$route.path" mode="horizontal" router >
						<el-menu-item index="/front/home" style="margin-left: 240px">首页</el-menu-item>
						<el-menu-item index="/front/activity">热门房型</el-menu-item>
						<el-menu-item index="/front/person">个人中心</el-menu-item>
<!--						<el-menu-item index="/front/signIn">签到</el-menu-item>-->
						<el-menu-item index="/front/Certification">会员认证</el-menu-item>
						<el-menu-item index="/front/Circulars">
              系统通知
              <div class="notification-bell">
                <i class="fa fa-bell"></i>
                <span v-if="unreadCount > 0" class="notification-dot">
                  {{unreadCount}}
                </span>
              </div>
            </el-menu-item>
          </el-menu>
        </div>
      </div>
      <div>
        <el-input style="width: 260px; margin-right: 10px;margin-top: 5px;" placeholder="请输入关键词搜索" v-model="title" clearable></el-input>
        <el-button type="success" @click="goSearch" style="margin-right: 30px">搜 索</el-button>
      </div>
      <div class="front-header-right">
        <div v-if="!user.username">
          <el-button @click="$router.push('/login')">登录</el-button>
          <el-button @click="$router.push('/register')">注册</el-button>
        </div>
        <div v-else>
          <el-dropdown>
            <div class="front-header-dropdown" >
              <img :src="user.avatar" alt="">
              <div style="margin-left: 10px; color:#fff">
                <span>{{ user.name }}</span><i class="el-icon-arrow-down" style="margin-left: 5px"></i>
              </div>
            </div>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item>
                <div style="text-decoration: none" @click="goHomePage">我的主页</div>
              </el-dropdown-item>
              <el-dropdown-item>
                <div style="text-decoration: none" @click="editHomePage">编辑主页</div>
              </el-dropdown-item>
              <el-dropdown-item>
                <div style="text-decoration: none" @click="logout">退出登录</div>
              </el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </div>
      </div>
    </div>
    <!--主体-->
    <div class="main-body">
      <router-view ref="child" @update:user="updateUser" />
    </div>
  </div>

</template>

<script>

import search from "@/views/front/Search.vue";
// import editHomePage from "@/views/front/EditHomePage.vue";

export default {
  name: "FrontLayout",
  computed: {
    // editHomePage() {
    //   return editHomePage
    // },
    search() {
      return search
    }
  },

  data () {
    return {
      top: '',
      notice: [],
      user: JSON.parse(localStorage.getItem("xm-user") || '{}'),
      title:this.$route.query.title,
      unreadCount: 0
    }
  },

  mounted() {
    this.loadNotice()
    this.fetchUnreadCount()
    setInterval(this.fetchUnreadCount, 1000)
  },
  methods: {
    goSearch(){
      window.open("/front/search?title="+this.title)
    },
    loadNotice() {
      this.$request.get('/notice/selectAll').then(res => {
        this.notice = res.data
        let i = 0
        if (this.notice && this.notice.length) {
          this.top = this.notice[0].content
          setInterval(() => {
            this.top = this.notice[i].content
            i++
            if (i === this.notice.length) {
              i = 0
            }
          }, 2500)
        }
      })
    },
    editHomePage(){
      window.open('/front/EditHomePage')
    },
    goHomePage(){
      this.$router.push('/front/HomePage?userId=' + this.user.id)
    },
    updateUser() {
      this.user = JSON.parse(localStorage.getItem('xm-user') || '{}')   // 重新获取下用户的最新信息
    },
    // 退出登录
    logout() {
      localStorage.removeItem("xm-user");
      this.$router.push("/login");
    },
    fetchUnreadCount() {
      this.$request.get('/msg/getUnreadCount/' + this.user.id).then(res => {
        this.unreadCount = res
      })
    },
  }
}
</script>

<style scoped>
 body{
    background-color: #e4e6eb;
}


.front-notice {
    padding: 5px 20px;
    color: #666;
    font-size: 12px;
    background-color: #fff;
}
.front-header {
    border-radius: 5px;
    display: flex;
    justify-content: center;
    height: 70px;
    line-height: 60px;
    border-bottom: 1px solid #eee;
    background: #0066bc;
}
.front-header-left {
    margin-left:160px;
    width: 300px;
    display: flex;
    align-items: center;
    padding-left: 30px;
}
.front-header-left img{
    width: 50px;
    height: 50px;
    border-radius: 10%
}

.front-header-dropdown img {
    width: 40px;
    height: 40px;
    border-radius: 50%;
    display: flex; 
    align-items: center;
}
.front-header-left .title {
    flex: 1;
    color: #fff;
    cursor: pointer;
    margin-left: 10px;
    font-size: 20px;
    font-weight: bold;
}
.front-header-center {
    flex: 1;
}
.front-header-right {
    margin-top: 5px;
    width: 200px;
    margin-right: 250px;
    padding-right: 20px;
    text-align: right;
}
.front-header-dropdown {
    width: 250px;
    display: flex;
    align-items: center;
}

.notification-bell {
 position: relative;
 font-size: 24px; /* Size of the bell icon */
 cursor: pointer;
}

.notification-dot {
 position: absolute;
 top: -2px;
 right: -20px;
 width: 18px; /* Adjust size as needed to fit numbers */
 height: 18px; /* Adjust size as needed to fit numbers */
 background-color: red;
 color: white;
 font-size: 12px; /* Adjust font size based on the size of the dot */
 display: flex;
 align-items: center;
 justify-content: center;
 border-radius: 50%;
 border: 2px solid white; /* White border to make the dot stand out */
 opacity: 0.85; /* Reduced opacity */
 box-shadow: 0 1px 3px rgba(0,0,0,0.3); /* Optional: adds a shadow for better visibility */
}

.el-dropdown-menu {
  margin-right: 130px;
    width: 100px !important;
    text-align: center !important;

}

/*页面具体样式自定义*/
.main-content {
    width: 70%;
    margin: 5px auto;
}
/* ElementUI 样式覆盖 */
.el-menu.el-menu--horizontal {
    border: none !important;
    height: 69px;
}
.el-menu{
    background-color: #0066bc;
}
.el-menu-item.is-active {
    color: #fff !important;
    background-color: #1890ff !important;
    height: 69px;
}
.el-menu-item {
    color: #fff !important;
    border: none !important;
    height: 69px !important;
    display: flex; 
    align-items: center;
}
.el-menu-item:not(.is-active):hover {
    background-color: rgb(121, 187, 249) !important;
    height: 69px;
}
</style>