<template>
  <div>
    <div class="search">
      <el-input placeholder="请输入评论内容查询" style="width: 200px" v-model="content"></el-input>
      <el-input placeholder="请输入评论人查询" style="width: 200px;margin-left: 5px" v-model="userName"></el-input>
      <el-input placeholder="请输入评论人ID查询" style="width: 200px;margin-left: 5px" v-model="userId"></el-input>
      <el-button type="info" plain style="margin-left: 10px" @click="load(1)">查询</el-button>
      <el-button type="warning" plain style="margin-left: 10px" @click="reset">重置</el-button>
    </div>

    <div class="operation">
      <el-button type="danger" plain @click="delBatch">批量删除</el-button>
    </div>

    <div class="table">
      <el-table :data="tableData" stripe @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center"></el-table-column>
        <el-table-column prop="id" label="序号" width="80" align="center" sortable></el-table-column>
        <el-table-column prop="content" label="评论内容" show-overflow-tooltip></el-table-column>
        <el-table-column prop="userId" label="评论人ID"></el-table-column>
        <el-table-column prop="userName" label="评论人" show-overflow-tooltip></el-table-column>
        <el-table-column prop="pid" label="父级评论ID"></el-table-column>
        <el-table-column prop="rootId" label="根级评论ID"></el-table-column>
        <el-table-column prop="fid" label="关联评论ID"></el-table-column>
        <el-table-column prop="time" label="评论时间" width="140px"></el-table-column>
        <el-table-column prop="module" label="评论模块"></el-table-column>

        <el-table-column label="操作" width="180" align="center">
          <template v-slot="scope">
            <el-button plain type="primary" @click="handleEdit(scope.row)" size="mini">编辑</el-button>
            <el-button plain type="danger" size="mini" @click="del(scope.row.id); updateCircularsUserId(scope.row.userId)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <div class="pagination">
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


    <el-dialog title="评论信息" :visible.sync="fromVisible" width="40%" :close-on-click-modal="false" destroy-on-close>
      <el-form label-width="100px" style="padding-right: 50px" :model="form" :rules="rules" ref="formRef">
        <el-form-item prop="content" label="内容">
          <el-input type="textarea" :rows="5" v-model="form.content" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item prop="userId" label="评论人ID">
          <el-input v-model="form.userId" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item prop="pid" label="父级评论ID">
          <el-input v-model="form.pid" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item prop="rootId" label="根级评论ID">
          <el-input v-model="form.rootId" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item prop="fid" label="关联评论ID">
          <el-input v-model="form.fid" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item prop="module" label="评论模块">
          <el-input v-model="form.module" autocomplete="off"></el-input>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="fromVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>

    <div v-if="visible">
      <Circulars :userId="circularsUserid" :visible="isVisible" @close="visible = false"></Circulars>
    </div>


  </div>
</template>

<script>
import Circulars from "@/components/Circulars.vue";

export default {
  name: "Comment",
  components: {Circulars},
  data() {
    return {
      tableData: [],  // 所有的数据
      pageNum: 1,   // 当前的页码
      pageSize: 10,  // 每页显示的个数
      total: 0,
      content: null,
      userName: null,
      userId: null,
      fromVisible: false,
      form: {},
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      rules: {},
      ids: [],
      visible: false,
      isVisible: true,
      circularsUserid: null
    }
  },
  created() {
    this.load(1)
  },
  methods: {
    updateCircularsUserId(userId) {
      this.circularsUserid = userId;
    },
    handleEdit(row) {   // 编辑数据
      this.form = JSON.parse(JSON.stringify(row))  // 给form对象赋值  注意要深拷贝数据
      this.fromVisible = true   // 打开弹窗
    },
    save() {   // 保存按钮触发的逻辑  它会触发新增或者更新
      this.$refs.formRef.validate((valid) => {
        console.log(this.form)
        if (valid) {
          this.$request({
            url: '/comment/update',
            method: 'PUT',
            data: this.form
          }).then(res => {
            if (res.code === '200') {  // 表示成功保存
              this.$message.success('保存成功')
              this.load(1)
              this.fromVisible = false
            } else {
              this.$message.error(res.msg)  // 弹出错误的信息
            }
          })
        }
      })
    },
    del(id) {   // 单个删除
      this.$confirm('您确定删除吗？', '确认删除', {type: "warning"}).then(response => {
        this.$request.delete('/comment/delete/' + id).then(res => {
          if (res.code === '200') {   // 表示操作成功
            this.$message.success('操作成功')
            this.load(1)
            //发送通知给用户
            this.visible = true
          } else {
            this.$message.error(res.msg)  // 弹出错误的信息
          }
        })
        this.visible = false
      }).catch(() => {
      })
    },
    handleSelectionChange(rows) {   // 当前选中的所有的行数据
      this.ids = rows.map(v => v.id)   //  [1,2]
    },
    delBatch() {   // 批量删除
      if (!this.ids.length) {
        this.$message.warning('请选择数据')
        return
      }
      this.$confirm('您确定批量删除这些数据吗？', '确认删除', {type: "warning"}).then(response => {
        this.$request.delete('/comment/delete/batch', {data: this.ids}).then(res => {
          if (res.code === '200') {   // 表示操作成功
            this.$message.success('操作成功')
            this.load(1)
          } else {
            this.$message.error(res.msg)  // 弹出错误的信息
          }
        })
      }).catch(() => {
      })
    },
    load(pageNum) {  // 分页查询
      if (pageNum) this.pageNum = pageNum
      this.$request.get('/comment/selectPage', {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          content: this.content,
          userName: this.userName,
          userId: this.userId,
        }
      }).then(res => {
        this.tableData = res.data?.list
        this.total = res.data?.total
      })
    },
    reset() {
      this.content = null
      this.userName = null
      this.userId = null
      this.load(1)
    },
    handleCurrentChange(pageNum) {
      this.load(pageNum)
    },
  }
}
</script>

<style scoped>

</style>
