<template>
  <div>
    <div class="card"
         style="padding: 10px; display: flex; justify-content: space-between; margin-bottom: 10px"
         v-for="item in myWelfare" :key="item.id">
      <!-- 头像与用户名组合-->
      <div style="position: relative; margin-right: 15px; text-align: center" @click="homePage(item.dbUserId)">
        <el-avatar :size="50" :src="item.avatar"></el-avatar>
        <div style="font-size: 16px; color: #999999">{{ item.username }}</div>
      </div>
      <!--标题-->
      <div style="margin-right: 15px; flex: 1; align-items: center; max-width: 600px; overflow: hidden">
        <div class="line1" style="font-size: 20px; font-weight: bold; margin-bottom: 22px;margin-top: 10px">
          {{ item.title }}
        </div>
        <div style="font-size: 12px; color: #999999">{{ item.time }}</div>
      </div>
      <!-- 查看详情按钮 -->
      <div style="margin-left: auto; display: flex; align-items: center;">
        <el-button round type="warning" @click="detail(item.content,item.title)" size="medium">查看详情</el-button>
      </div>
    </div>
    <div v-if="total === 0" style="padding: 20px;text-align: center; font-size: 16px; color: #666">暂无数据</div>

    <!--分页-->
    <div class="pagination" style="margin-top: 10px" v-if="total">
      <el-pagination
          background
          @current-change="handleCurrentChange"
          :current-page="pageNum"
          :page-sizes="[5, 10, 20]"
          :page-size="pageSize"
          layout="total, prev, pager, next"
          :total="total">
      </el-pagination>
    </div>
    <el-dialog title="查看详情" :visible.sync="fromVisible1" width="60%" :close-on-click-modal="false" destroy-on-close>
      <div style="font-weight: bold; font-size: 28px">
        {{ title }}
      </div>
      <div class="w-e-text">
        <div v-html="content"></div>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="fromVisible1 = false">关 闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
export default {
  name: "MyWelfare",
  data() {
    return {
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      myWelfare: [],
      pageNum: 1,
      pageSize: 10,
      total: 0,
      content: '',
      title: '',
      fromVisible1: false,
    }
  },
  mounted() {
    this.load()
  },
  methods: {
    //请求我的福利数据
    load() {
      this.$request.get("/myWelfare/selectByUserId/", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          userId: this.user.id
        }
      }).then(res => {
        this.myWelfare = res.data?.list
        this.total = res.data?.total
      })
    },
    detail(content, title) {
      this.content = content
      this.title = title
      this.fromVisible1 = true
    },
    handleCurrentChange(pageNum) {
      this.load(pageNum)
    },
    homePage(userId){
      this.$router.push('/front/HomePage?userId=' + userId)
    },
  }
}
</script>

<style scoped>
.line1 {
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}
</style>