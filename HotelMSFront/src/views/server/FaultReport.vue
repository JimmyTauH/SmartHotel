<template>
  <div>
    <h2>报备故障设备</h2>
    <el-form :model="form" ref="formRef" label-width="100px">
      <el-form-item label="设备名称">
        <el-input v-model="form.deviceName" required></el-input>
      </el-form-item>
      <el-form-item label="故障描述">
        <div id="editor"></div>
      </el-form-item>
      <el-form-item label="后勤人员 ID">
        <el-input v-model="form.logisticsId" required></el-input>
      </el-form-item>
      <el-form-item label="报告时间">
        <el-input v-model="form.reportTime" readonly></el-input>
      </el-form-item>
      <el-form-item label="上传图片" prop="image">
        <el-upload
          ref="upload"          
          :action="$baseUrl + '/files/upload'"
          :headers="{ token: user.token }"
          list-type="picture"
          :before-upload="beforeUpload" 
          :limit="1" 
          :on-success="handleCoverSuccess"
          >
          <el-button size="small" type="primary">点击上传</el-button>
          <div slot="tip" class="el-upload__tip">只能上传 JPG/PNG 文件，且不超过 2MB</div>
        </el-upload>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitReport">提交报备</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import E from "wangeditor"
import hljs from 'highlight.js'

export default {
  name: "FaultReport",
  data() {
    return {
      form: {
      reportTime: this.getCurrentDate(),
      deviceName: '',
      logisticsId: '',
      image: '', // 用来存储上传后的图片地址 
      },
      description: '', 
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'), // 假设从localStorage获取用户token
    };
  },
  created() {
    this.handleAdd()
  },
  
  methods: {
  // 获取当前时间，格式为 yyyy-MM-dd-HH:mm:ss
  getCurrentDate() {
    const date = new Date();
    const options = {
      year: 'numeric',
      month: '2-digit',
      day: '2-digit',
      hour: '2-digit',
      minute: '2-digit',
      second: '2-digit',
      hour12: false,
    };
    const formatter = new Intl.DateTimeFormat('zh-CN', options);
    const parts = formatter.formatToParts(date);
    
    const year = parts.find(part => part.type === 'year').value;
    const month = parts.find(part => part.type === 'month').value.padStart(2, '0');
    const day = parts.find(part => part.type === 'day').value.padStart(2, '0');
    const hour = parts.find(part => part.type === 'hour').value.padStart(2, '0');
    const minute = parts.find(part => part.type === 'minute').value.padStart(2, '0');
    const second = parts.find(part => part.type === 'second').value.padStart(2, '0');
    
    return `${year}-${month}-${day}-${hour}:${minute}:${second}`;
  },

  // 上传前的校验，限制上传文件类型及大小
  beforeUpload(file) {
    const isJPG = file.type === 'image/jpeg' || file.type === 'image/png';  // 限制格式为 jpg 或 png
    const isLt2M = file.size / 1024 / 1024 < 2;  // 限制大小不超过 2MB

    // 校验格式
    if (!isJPG) {
      this.$message.error('上传图片只能是 JPG/PNG 格式!');
    }

    // 校验文件大小
    if (!isLt2M) {
      this.$message.error('上传图片大小不能超过 2MB!');
    }

    // 如果格式正确且大小符合要求，返回 true，允许上传
    return isJPG && isLt2M;
  },

  handleAdd() {   // 新增数据
      // this.form = {}  // 新增数据的时候清空数据
      this.setRichText()  // 初始化富文本编辑器
    },

  // 上传成功后的回调
  handleCoverSuccess(res) {
    this.form.image = res.data
  },

  // 表单提交
  submitReport() {
  // 表单验证
  this.$refs.formRef.validate((valid) => {
    if (valid) {
      
      this.form.description = this.editor.txt.html()

      // 发送请求到后端
      this.$request.post('/report-fault/create',this.form)
        .then(res => {
          if (res.code === '200') {  // 表示成功保存
            this.$message.success('保存成功');
            this.resetForm();
            this.$nextTick(() => {
              if (this.$refs.upload) {
                this.$refs.upload.clearFiles(); // 清空上传的图片文件
              }
            });
          } else {
            // this.$message.error('报备失败，请重试！');
            this.$message.error(res.msg);  // 弹出错误的信息
          }
        })
    }
  });
},


  // 重置表单
  resetForm() {
    this.form.deviceName = '';
    this.form.logisticsId = '';
    this.form.reportTime = this.getCurrentDate();
    this.form.image = null; // 清空上传的图片地址
    this.editor.txt.clear(); // 清空富文本内容
  },

  setRichText() {
      this.$nextTick(() => {
        this.editor = new E(`#editor`)  // 实例化\初始化
        this.editor.highlight = hljs    // 配置高亮
        this.editor.config.uploadImgServer = this.$baseUrl + '/files/editor/upload' // 上传图片的接口
        this.editor.config.uploadFileName = 'file'  // 上传图片的参数名
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

};
</script>

<style scoped>
/* 可添加样式 */
</style>
