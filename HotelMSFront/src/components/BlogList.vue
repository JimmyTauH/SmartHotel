<template>
  <div style="" class="card">
    <el-row :gutter="20">
  <el-col :span="6" v-for="item in tableData" :key="item.id" v-if="total > 0">
    <!-- 卡片整体容器，使用固定高度 -->
    <div style="display: flex; flex-direction: column; justify-content: space-between; height: 350px;">
      <!-- 右边封面部分 -->
      <img :src="item.cover" style="width: 100%; height: 175px; border-radius: 10px" alt="" @click="navToDetail(item.id)">
      
      <!-- 标题部分 -->
      <div style="font-size: 16px; font-weight: bold; margin-top: 10px; color: #455873FF">
        <a :href="'/front/blogDetail?blogId=' + item.id" target="_blank">
          {{ item.title }}
        </a>
      </div>
      
      <!-- 简介部分，设置最小高度保持一致 -->
      <div style="color: #666; margin-top: 10px; font-size: 13px; white-space: nowrap; overflow: hidden; text-overflow: ellipsis;">
        {{ item.descr }}
      </div>
      
      <!-- 创作者、点赞量、观看量部分 -->
      <div style="margin-top: 5px; font-size: 14px; color: #455873FF">
        <span style="margin-right: 20px">
          <i class="el-icon-user" style="margin-right: 5px"></i>{{ item.userName }}
        </span>
        <span style="margin-right: 20px">
          <i class="el-icon-eye" style="margin-right: 5px"></i>{{ item.readCount }}
        </span>
        <span>
          <i class="el-icon-like" style="margin-right: 5px"></i>{{ item.likesCount }}
        </span>
      </div>
      
      <!-- 删除和编辑操作 -->
        <!-- <div v-if="showOpt" style="margin-top: 10px;">
          <span style="color: red; cursor: pointer; margin-right: 10px" @click="del(item.id)">
            <i class="el-icon-delete"></i>删除
          </span>
          <span style="color: #2a60c9; cursor: pointer" @click="editBlog(item.id)">
            <i class="el-icon-edit"></i>编辑
          </span>
        </div>
        -->
      <!-- 标签部分 -->
      <div style="margin-top: 5px;margin-bottom: 10px;">
        <el-tag v-for="tag in JSON.parse(item.tags || [])" :key="tag.id" type="primary" style="margin-right: 2px">
          {{ tag }}
        </el-tag>
      </div>
    </div>
  </el-col>
</el-row>


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
  name: "BlogList",
  props: {
    categoryName: null,
    type: null,
    showOpt: false
  },
  watch: {
    categoryName() {
      this.loadBlog(1);
    }
  },
  data() {
    return {
      pageNum: 1,
      pageSize: 10,
      tableData: [],
      total: 0,
      title: this.$route.query.title,
      userId: this.$route.query.userId
    }
  },
  mounted() {
    this.loadBlog(1)
  },
  methods: {
    editBlog(blogId) {
      window.open('/front/newBlog?blogId=' + blogId)
    },
    del(id) {   // 单个删除
      this.$confirm('您确定删除吗？', '确认删除', {type: "warning"}).then(response => {
        this.$request.delete('/blog/delete/' + id).then(res => {
          if (res.code === '200') {   // 表示操作成功
            this.$message.success('操作成功')
            this.loadBlog(1)
          } else {
            this.$message.error(res.msg)  // 弹出错误的信息
          }
        })
      }).catch(() => {
      })
    },
    //请求帖子数据
    loadBlog(pageNum) {
      let url
      if (pageNum) this.pageNum = pageNum
      switch (this.type) {
        case 'user':
          url = '/blog/selectUser';
          break;
        case 'like':
          url = '/blog/selectLike';
          break;
        case 'collect':
          url = '/blog/selectCollect';
          break;
        case 'comment':
          url = '/blog/selectComment';
          break;
        case 'certification':
          url = '/blog/selectCertification';
          break;
        default:
          url = '/blog/selectPage';
      }
      this.$request.get(url, {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          categoryName: this.categoryName === '全部房型' ? null : this.categoryName,
          title: this.title,
          userId: this.userId
        }
      }).then(res => {
        this.tableData = res.data?.list
        this.total = res.data?.total
      })
    },
    handleCurrentChange(pageNum) {
      this.loadBlog(pageNum)
    },
  }
}
</script>


<style scoped>
.blog-box {
  display: flex;
  grid-gap: 15px;
  padding: 10px 0;
  border-bottom: 1px solid #ddd;
}

.blog-box:first-child {
  padding-top: 0;
}

.blog-box-title {
  font-size: 16px;
  font-weight: bold;
  margin-bottom: 10px;
  cursor: pointer;
}

.blog-box-title:hover {
  color: #409EFF;
}
</style>