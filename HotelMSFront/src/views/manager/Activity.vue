<template>
  <div>
    <div class="search">
      <el-input placeholder="请输入酒店标题查询" style="width: 200px" v-model="name"></el-input>
      <el-input placeholder="请输入主办方查询" style="width: 200px; margin-left: 5px" v-model="host"></el-input>
      <el-button type="info" plain style="margin-left: 10px" @click="load(1)">查询</el-button>
      <el-button type="warning" plain style="margin-left: 10px" @click="reset">重置</el-button>
    </div>

    <div class="operation">
      <el-button type="primary" plain @click="handleAdd">新增</el-button>
      <el-button type="danger" plain @click="delBatch">批量删除</el-button>
    </div>

    <div class="table">
      <el-table :data="tableData" stripe @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center"></el-table-column>
        <el-table-column prop="id" label="序号" width="80" align="center" sortable></el-table-column>
        <el-table-column prop="name" label="酒店名称" show-overflow-tooltip></el-table-column>
        <el-table-column prop="descr" label="酒店简介" show-overflow-tooltip></el-table-column>
        <el-table-column prop="cover" label="封面">
          <template v-slot="scope">
            <div style="display: flex; align-items: center">
              <el-image style="width: 50px; height: 30px; border-radius: 5px" v-if="scope.row.cover"
                        :src="scope.row.cover" :preview-src-list="[scope.row.cover]"></el-image>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="applystart" label="酒店入住时间"></el-table-column>
        <el-table-column prop="applyend" label="酒店退房时间"></el-table-column>
        <!-- <el-table-column prop="start" label="比赛开始时间"></el-table-column>
        <el-table-column prop="end" label="比赛结束时间"></el-table-column> -->
        <el-table-column prop="form" label="订房渠道"></el-table-column>
        <el-table-column prop="address" label="酒店地址" show-overflow-tooltip></el-table-column>
        <!-- <el-table-column prop="host" label="主办方"></el-table-column> -->
        <el-table-column prop="readCount" label="浏览量"></el-table-column>
        <el-table-column label="酒店描述" width="150px">
          <template v-slot="scope">
            <div>
              <el-button @click="handleActivityContent(scope.row.content)">查看酒店描述</el-button>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="180" align="center">
          <template v-slot="scope">
            <el-button plain type="primary" @click="handleEdit(scope.row)" size="mini">编辑</el-button>
            <el-button plain type="danger" size="mini" @click=del(scope.row.id)>删除</el-button>
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


    <el-dialog title="酒店信息" :visible.sync="fromVisible" width="70%" :close-on-click-modal="false" destroy-on-close>
      <el-form label-width="100px" style="padding-right: 50px" :model="form" :rules="rules" ref="formRef">
        <el-form-item prop="name" label="酒店标题">
          <el-input v-model="form.name" placeholder="酒店标题"></el-input>
        </el-form-item>
        <el-form-item prop="descr" label="酒店简介">
          <el-input v-model="form.descr" placeholder="酒店简介" type="textarea"></el-input>
        </el-form-item>
        <el-form-item prop="cover" label="封面">
          <el-upload
              :action="$baseUrl + '/files/upload'"
              :headers="{token:user.token}"
              list-type="picture"
              :on-success="handleCoverSuccess">
            <el-button type="primary">上传封面</el-button>
          </el-upload>
        </el-form-item>
        <el-form-item prop="applystart" label="酒店入住时间">
          <el-date-picker
              v-model="form.applystart"
              type="date"
              placeholder="请选择报名开始时间"
              value-format="yyyy-MM-dd"
              format="yyyy-MM-dd"
          />
        </el-form-item>
        <el-form-item prop="applyend" label="酒店退房时间">
          <el-date-picker
              v-model="form.applyend"
              type="date"
              placeholder="请选择酒店退房时间"
              value-format="yyyy-MM-dd"
              format="yyyy-MM-dd"
          />
        </el-form-item>
        <!-- <el-form-item prop="start" label="酒店开始时间">
          <el-date-picker
              v-model="form.start"
              type="date"
              placeholder="请选择酒店开始时间"
              value-format="yyyy-MM-dd"
              format="yyyy-MM-dd"
          />
        </el-form-item>
        <el-form-item prop="end" label="酒店结束时间">
          <el-date-picker
              v-model="form.end"
              type="date"
              placeholder="请选择酒店结束时间"
              value-format="yyyy-MM-dd"
              format="yyyy-MM-dd"
          /> -->
        <!-- </el-form-item> -->
        <el-form-item label="订房渠道" prop="form">
          <el-select style="width: 100%" v-model="form.form">
            <el-option value="美团"></el-option>
            <el-option value="携程"></el-option>
            <el-option value="抖音"></el-option>
            <el-option value="去哪儿旅行"></el-option>
            <el-option value="其他"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item prop="address" label="酒店地址">
          <el-input v-model="form.address" placeholder="酒店地址"></el-input>
        </el-form-item>
        <!-- <el-form-item prop="host" label="主办方">
          <el-input v-model="form.host" placeholder="主办方"></el-input>
        </el-form-item> -->
        <el-form-item prop="content" label="顾客留言备注">
          <div id="editor"></div>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="fromVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="顾客留言备注" :visible.sync="fromVisible1" width="70%" :close-on-click-modal="false" destroy-on-close>
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
import E from "wangeditor"
import hljs from 'highlight.js'

export default {
  name: "Activity",
  data() {
    return {
      tableData: [],  // 所有的数据
      pageNum: 1,   // 当前的页码
      pageSize: 10,  // 每页显示的个数
      total: 0,
      name: null,
      host:null,
      fromVisible: false,
      form: {},
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      rules: {
        name: [
          {required: true, message: '请输入酒店名称', trigger: 'blur'},
        ],
        descr: [
          {required: true, message: '请输入酒店简介', trigger: 'blur'},
        ],
        applystart: [
          {required: true, message: '请输入报名开始时间', trigger: 'blur'},
        ],
        applyend: [
          {required: true, message: '请输入酒店退房时间', trigger: 'blur'},
        ],
        start: [
          {required: true, message: '请输入酒店开始时间', trigger: 'blur'},
        ],
        end: [
          {required: true, message: '请输入酒店结束时间', trigger: 'blur'},
        ],
        address: [
          {required: true, message: '请输入酒店地址', trigger: 'blur'},
        ],
        host: [
          {required: true, message: '请输入酒店主办方', trigger: 'blur'},
        ],
      },
      ids: [],
      editor: null,
      content: '',
      fromVisible1: false
    }
  },
  created() {
    this.load(1)
  },
  methods: {
    handleCoverSuccess(res) {
      this.form.cover = res.data
    },
    handleAdd() {   // 新增数据
      this.form = {}  // 新增数据的时候清空数据
      this.setRichText('')   // 初始化富文本编辑器
      this.fromVisible = true   // 打开弹窗
    },
    handleEdit(row) {   // 编辑数据
      this.form = JSON.parse(JSON.stringify(row))  // 给form对象赋值  注意要深拷贝数据
      this.setRichText(this.form.content) // 初始化富文本编辑器
      this.fromVisible = true   // 打开弹窗
    },
    save() {   // 保存按钮触发的逻辑  它会触发新增或者更新
      this.$refs.formRef.validate((valid) => {
        if (valid) {
          this.form.content = this.editor.txt.html()
          this.$request({
            url: this.form.id ? '/activity/update' : '/activity/add',
            method: this.form.id ? 'PUT' : 'POST',
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
        this.$request.delete('/activity/delete/' + id).then(res => {
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
    handleSelectionChange(rows) {   // 当前选中的所有的行数据
      this.ids = rows.map(v => v.id)   //  [1,2]
    },
    delBatch() {   // 批量删除
      if (!this.ids.length) {
        this.$message.warning('请选择数据')
        return
      }
      this.$confirm('您确定批量删除这些数据吗？', '确认删除', {type: "warning"}).then(response => {
        this.$request.delete('/activity/delete/batch', {data: this.ids}).then(res => {
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
      this.$request.get('/activity/selectPage', {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          name: this.name,
          host: this.host
        }
      }).then(res => {
        this.tableData = res.data?.list
        this.total = res.data?.total
      })
    },
    reset() {
      this.name = null
      this.host = null
      this.load(1)
    },
    handleCurrentChange(pageNum) {
      this.load(pageNum)
    },
    setRichText(content) {
      this.$nextTick(() => {
        this.editor = new E(`#editor`)
        this.editor.highlight = hljs
        this.editor.config.uploadImgServer = this.$baseUrl + '/files/editor/upload'
        this.editor.config.uploadFileName = 'file'
        this.editor.config.uploadImgHeaders = {
          token: this.user.token
        }
        this.editor.config.uploadImgParams = {
          type: 'img',
        }
        this.editor.create()  // 创建
        this.editor.txt.html(content)
      })
    },
    handleActivityContent(content){
      this.content = content
      this.fromVisible1 = true
    }
  }
}
</script>

<style scoped>
.w-e-text img {
  width: 100% !important;
}
</style>
