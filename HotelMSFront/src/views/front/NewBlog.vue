<template>
  <div style="width: 50%; margin: 5px auto">
    <div class="card">
      <div style="font-weight: bold;font-size: 24px;margin-bottom: 20px; text-align: center">发布/编辑酒店</div>
      <el-form :model="form" label-width="100px" style="padding-right: 50px" :rules="rules" ref="formRef">
        <el-form-item label="标题" prop="title">
          <el-input v-model="form.title" placeholder="标题"></el-input>
        </el-form-item>
        <el-form-item label="简介" prop="descr">
          <el-input type="textarea" v-model="form.descr" placeholder="简介"></el-input>
        </el-form-item>
        <el-form-item label="封面" prop="cover">
          <el-upload
              :action="$baseUrl + '/files/upload'"
              :headers="{ token: user.token }"
              list-type="picture"
              :on-success="handleCoverSuccess"
          >
            <el-button type="primary">上传封面</el-button>
          </el-upload>
        </el-form-item>
        <el-form-item label="分类" prop="categoryId">
          <el-select v-model="form.categoryId" style="width: 100%">
            <el-option v-for="item in categoryList" :key="item.id" :value="item.id" :label="item.name"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="相关酒店" prop="contestId">
          <el-select
              v-model="form.contestId"
              filterable
              remote
              :remote-method="filterContests"
              style="width: 100%">
            <el-option v-for="item in filteredContestList" :key="item.id" :value="item.id" :label="item.name"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="房型具备" prop="tags">
          <el-select v-model="tagsArr" multiple filterable allow-create default-first-option style="width: 100%">
            <el-option value="大床房"></el-option>
            <el-option value="双床房"></el-option>
            <el-option value="3-4人房"></el-option>
            <el-option value="电竞游戏房"></el-option>
            <el-option value="浪漫情侣房"></el-option>
            <el-option value="温馨亲子房"></el-option>
          </el-select>
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
  name: "NewBlog",
  data() {
    return {
      form: {},
      tagsArr: [],
      categoryList: [],
      contestList: [],
      filteredContestList: [],  // 过滤后的酒店列表
      editor: null,
      rules: {
        title: [
          {required: true, message: '请输入标题', trigger: 'blur'},
        ],
        descr: [
          {required: true, message: '请输入简介', trigger: 'blur'},
        ],
        categoryId: [
          {required: true, message: '请选择分类', trigger: 'blur'},
        ],
        contestId: [
          {required: true, message: '请选择相关酒店', trigger: 'blur'}
        ],
      },
      fromVisible: false,
      fromVisible1: false,
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      blogId: this.$route.query.blogId
    }
  },
  mounted() {
    this.$request.get('/category/selectAll').then(res => {
      this.categoryList = res.data || []
    })
    this.$request.get('/activity/selectAll').then(response => {
      this.contestList = response.data || [];
      this.filteredContestList = this.contestList;  // 初始化过滤列表为完整列表
    });
    this.$request.get('/blog/select/' + this.blogId).then(res => {
      this.form = res.data || {}
      //设置标签和内容
      if (this.form.id != null) {
        //设置标签
        this.tagsArr = JSON.parse(this.form.tags || '[]')
        //设置内容
        //异步操作，等富文本页面渲染完再赋值
        setTimeout(() => {
          this.editor.txt.html(this.form.content)
        }, 0)
      }
    })
    this.setRichText()
  },
  methods: {
    filterContests(query) {
      if (query) {
        this.$request.get(`/activity/selectByName/`+query).then(response => {
          this.filteredContestList = response.data;
        }).catch(error => {
          console.error('Error fetching activities:', error);
          this.$message.error('加载酒店数据失败');
        });
      } else {
        this.$request.get('/activity/selectAll').then(response => {
          this.filteredContestList = response.data || [];
        });
      }
    },
    handleAdd() {   // 新增数据
      this.form = {}  // 新增数据的时候清空数据
      this.tagsArr = [] // 清空标签数组
      this.setRichText()  // 初始化富文本编辑器
      this.fromVisible = true   // 打开弹窗
    },
    handleEdit(row) {   // 编辑数据
      this.form = JSON.parse(JSON.stringify(row))  // 给form对象赋值  注意要深拷贝数据
      this.tagsArr = JSON.parse(row.tags || '[]') // 将tags对象转成数组
      this.fromVisible = true   // 打开弹窗
      this.setRichText()  // 初始化富文本编辑器
      setTimeout(() => {
        this.editor.txt.html(this.form.content) // 将编辑器内容赋值给form对象
      }, 0)
    },
    save() {   // 保存按钮触发的逻辑  它会触发新增或者更新
      this.$refs.formRef.validate((valid) => {
        if (valid) {
          this.form.tags = JSON.stringify(this.tagsArr) // 将tags对象转成字符串
          this.form.content = this.editor.txt.html()
          this.$request({
            url: this.form.id ? '/blog/update' : '/blog/add',
            method: this.form.id ? 'PUT' : 'POST',
            data: this.form
          }).then(res => {
            if (res.code === '200') {  // 表示成功保存
              this.$message.success('保存成功')
              //返回
              this.$router.push('/front/Home')
            } else {
              this.$message.error(res.msg)  // 弹出错误的信息
            }
          })
        }
      })
    },
    handleCoverSuccess(res) {
      this.form.cover = res.data
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