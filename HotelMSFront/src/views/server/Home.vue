<template>
  <div class="main-page">
    <el-container>
      <el-header>
        <h1 class="logo">酒店后勤管理系统</h1>
      </el-header>

      <el-main>
        <h2>欢迎来到酒店后勤管理系统</h2>
        <p>请选择需要管理的模块：</p>
        
        <el-row>
          <el-col :span="8">
            <el-card class="module-card">
              <div slot="header" class="clearfix">
                <span>房间管理</span>
              </div>
              <div>
                <p>管理房间信息与状态。</p>
                <el-button type="primary" @click="navigateTo('/rooms')">进入</el-button>
              </div>
            </el-card>
          </el-col>

          <el-col :span="8">
            <el-card class="module-card">
              <div slot="header" class="clearfix">
                <span>员工管理</span>
              </div>
              <div>
                <p>管理员工信息与考勤。</p>
                <el-button type="primary" @click="navigateTo('/employees')">进入</el-button>
              </div>
            </el-card>
          </el-col>

          <el-col :span="8">
            <el-card class="module-card">
              <div slot="header" class="clearfix">
                <span>服务申请管理</span>
              </div>
              <div>
                <p>查看和管理服务申请。</p>
                <el-button type="primary" @click="navigateTo('/service-requests')">进入</el-button>
              </div>
            </el-card>
          </el-col>
        </el-row>
      </el-main>

      <el-footer>
        <div>© 2024 酒店后勤管理系统</div>
      </el-footer>
    </el-container>
  </div>
</template>

<script>
export default {
  name: 'MainPage',
  methods: {
    navigateTo(path) {
      this.$router.push(path);
    },
  },
}
</script>

<style scoped>
.main-page {
  padding: 20px;
}
.logo {
  font-size: 24px;
  color: #409EFF;
  text-align: center;
}
.module-card {
  margin: 20px;
  text-align: center;
}
</style>
