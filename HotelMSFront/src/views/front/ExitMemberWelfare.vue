<template>
  <div style="width: 50%; margin: 5px auto">
    <div class="card">
      <div style="font-weight: bold;font-size: 24px;margin-bottom: 20px; text-align: center">编辑会员福利</div>
      <el-form :model="form" label-width="100px" style="padding-right: 50px" :rules="rules" ref="formRef">
        <el-form-item label="标题" prop="title">
          <el-input v-model="form.title" placeholder="标题"></el-input>
        </el-form-item>
        <el-form-item label="内容" prop="content">
          <div id="editor"></div>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer" style="text-align: center">
        <el-button type="primary" @click="save" style="width: 200px">保 存</el-button>
      </div>
    </div>

  </div>
</template>
<script>
import E from "wangeditor"
import hljs from 'highlight.js'

export default {
  name: "ExitMemberWelfare",
  data() {
    return {
      form: {},
      editor: null,
      rules: {},
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      dbUserId: this.$route.query.userId,
      dbMemberId: this.$route.query.memberId
    }
  },
  mounted() {
    this.$request.get('/memberWelfare/selectByMidAndUid/' + this.dbMemberId + '/' + this.dbUserId).then(res => {
      this.form = res.data || {}
      //异步操作，等富文本页面渲染完再赋值
      setTimeout(() => {
        this.editor.txt.html(this.form.content)
      }, 0)
    })
    this.setRichText()
  },
  methods: {
    handleAdd() {   // 新增数据
      this.form = {}  // 新增数据的时候清空数据
      this.setRichText()  // 初始化富文本编辑器
    },
    handleEdit(row) {   // 编辑数据
      this.form = JSON.parse(JSON.stringify(row))  // 给form对象赋值  注意要深拷贝数据
      this.fromVisible = true   // 打开弹窗
      this.setRichText()  // 初始化富文本编辑器
      setTimeout(() => {
        this.editor.txt.html(this.form.content) // 将编辑器内容赋值给form对象
      }, 0)
    },
    save() {   // 保存按钮触发的逻辑  它会触发新增或者更新
      this.$refs.formRef.validate((valid) => {
        if (valid) {
          this.form.content = this.editor.txt.html()
          this.form.userId = this.dbUserId
          this.form.memberId = this.dbMemberId
          this.$request({
            url: this.form.id ? '/memberWelfare/update' : '/memberWelfare/add',
            method: this.form.id ? 'PUT' : 'POST',
            data: this.form
          }).then(res => {
            if (res.code === '200') {  // 表示成功保存
              this.$message.success('保存成功')
              //返回
              this.$router.push('/front/Person')
            } else {
              this.$message.error(res.msg)  // 弹出错误的信息
            }
          })
        }
      })
    },
    setRichText() {
      this.$nextTick(() => {
        this.editor = new E(`#editor`)  // 实例化\初始化
        this.editor.highlight = hljs    // 配置高亮
        this.editor.config.uploadImgServer = this.$baseUrl + '/files/editor/upload' // 上传图片的接口
        this.editor.config.uploadFileName = 'file'  // 上传图片的参数名
        this.editor.config.zIndex = 0
        this.editor.config.uploadImgHeaders = {
          token: this.user.token  // token
        }
        this.editor.config.uploadImgParams = {
          type: 'img',
        }
        this.editor.create()  // 创建
      })
    },
  }

}
</script>

<style scoped>

</style>