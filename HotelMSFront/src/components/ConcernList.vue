<template>
  <div>
    <div class="card"
         style="padding: 10px; display: flex; justify-content: space-between; margin-bottom: 10px"
         v-for="item in creatorList" :key="item.id">
      <!-- 头像与用户名组合-->
      <div style="display: flex; align-items: center; margin-right: 15px;" @click="homePage(item.creatorId)">
        <el-avatar :size="70" :src="item.avatar"></el-avatar>
        <span style="font-weight: bold; font-size: 30px; margin-left: 10px;">{{ item.userName }}</span>
      </div>
      <!-- 关注/取消关注按钮 -->
      <div style="margin-left: auto; display: flex; align-items: center;">
        <el-button round type="warning" @click="cancelConcern(item.creatorId)" size="medium">取消关注</el-button>
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
  </div>
</template>
<script>
export default {
  name: "ConcernList",
  data() {
    return {
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      creatorList: [],
      pageNum: 1,
      pageSize: 10,
      total: 0,
    }
  },
  mounted() {
    this.load()
  },
  methods: {
    //请求创作者数据
    load() {
      this.$request.get("/concern/selectConcernList/", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          userId: this.user.id
        }
      }).then(res => {
        this.creatorList = res.data?.list
        this.total = res.data?.total
      })
    },
    cancelConcern(creatorId) {
      this.$request.delete("/concern/delete/" + this.user.id + "/" + creatorId).then(res => {
        if (res.code === '200') {
          this.$message.success("取消关注成功")
          this.load()
        } else {
          this.$message.error(res.msg)
        }
      })
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

</style>