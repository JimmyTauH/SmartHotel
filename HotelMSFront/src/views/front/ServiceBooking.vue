<template>
  <div class="main-content">
    <div class="card-header">
      酒店服务申请
    </div>
    <div class="card-body">
      <div class="section-title">
        申请服务
      </div>
      <el-form :model="form" ref="formRef" label-width="150px" label-align="right" :rules="rules" class="service-form">
        <el-form-item label="预约服务类型" prop="title">
          <el-select v-model="form.title" placeholder="选择服务类型">
            <el-option
              v-for="option in serviceOptions"
              :key="option.value"
              :label="option.label"
              :value="option.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <!-- TODO: get到房间号 -->
        <el-form-item label="房间号" prop="room">
          <el-input type="textarea" v-model="form.room" rows=1 placeholder="请输入房间号"></el-input>
        </el-form-item>
        <el-form-item label="预约服务时间" prop="time">
          <el-date-picker v-model="form.time" type="datetime" placeholder="选择预约时间"></el-date-picker>
        </el-form-item>
        <el-form-item label="备注" prop="content">
          <el-input type="textarea" v-model="form.content" placeholder="请输入备注"></el-input>
        </el-form-item>
        <div class="submit-button-container">
          <el-button 
            :disabled="isFormDisabled" 
            class="my-button-primary"
            type="primary" 
            @click="submitRequest">
            提交申请
          </el-button>
        </div>
      </el-form>

      <el-dialog :visible.sync="showDialog" title="确认申请">
        <p>服务类型：{{ formPreview.title }}</p>
        <p>预约时间：{{ formPreview.time }}</p>
        <p>房间号：{{ formPreview.room }}</p>
        <p>备注：{{ formPreview.content }}</p>
        <span slot="footer" class="dialog-footer">
          <el-button @click="showDialog = false">取消</el-button>
          <el-button type="primary" @click="confirmSubmit">确认</el-button>
        </span>
      </el-dialog>

      <div class="section-title">
        待完成服务
      </div>
      <el-table :data="filteredServices_noncompleted" style="width: 100%" class="service-table">
        <el-table-column prop="title" label="服务类型"></el-table-column>
        <el-table-column prop="time" label="预约时间"></el-table-column>
        <el-table-column prop="content" label="备注"></el-table-column>
          <!-- 操作列 -->
          <el-table-column label="操作" width="180" align="center">
          <template v-slot="scope">
            <el-button plain type="danger" size="mini" @click=deleteService(scope.row.id)>取消预约</el-button>
          </template>
        </el-table-column>

      </el-table>

      <div class="section-title">
        已完成服务
      </div>
      <el-table :data="filteredServices_completed" style="width: 100%" class="service-table">
        <el-table-column prop="title" label="服务类型"></el-table-column>
        <el-table-column prop="time" label="预约时间"></el-table-column>
        <el-table-column prop="content" label="备注"></el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script>
export default {
  name: "ServiceBook",
  data() {
    return {
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      form: {
        title: '',
        time: '',
        content: ''
      },
      services: [],
      serviceOptions: [
        { label: '房间清洁', value: "房间清洁" },
        { label: '早餐服务', value: "早餐服务" },
        { label: '接送服务', value: "接送服务" },
      ],
      roomOptions: [
            { number: '101' },
            { number: '102' },
            { number: '103' },
            { number: '104' },
      ],
      rules: {
        title: [{ required: true, message: '请选择服务类型', trigger: 'change' }],
        room: [{ required: true, message: '请选择房间号', trigger: 'change' }],
        time: [{ required: true, message: '请选择预约时间', trigger: 'change' }],
      },
      showDialog: false, // 对话框的显示状态
      formPreview: {} // 用于预览的表单内容
    }
  },
  mounted() {
    this.loadServices();
  },
  computed: {
    filteredServices_completed() {
      return this.services.filter(item => item.state == 1);
    },
    filteredServices_noncompleted() {
      return this.services.filter(item => item.state == 0);
    },
    isFormDisabled() {
      return this.services.length >= 20; // 限制最多申请5个服务
    },
  },
  methods: {
    loadServices() {
      if (!this.user.id) {
        console.error("用户ID无效!无法加载服务!");
        return;
      }
      this.$request.get(`/serviceBook/selectByUser/` + this.user.id)
        .then(res => {
          this.services = res.data || [];
        })
        .catch(err => {
          console.error("加载服务出错:", err);
        });
    },
    deleteService(id) {   // 单个删除
      console.log(id);
      this.$confirm('您确定删除吗？', '确认删除', {type: "warning"}).then(response => {
        this.$request.delete('/serviceBook/delete/' + id).then(res => {
          if (res.code === '200') {   // 表示操作成功
            this.$message.success('操作成功')
            //this.load(1)
            this.loadServices()
          } else {
            this.$message.error(res.msg)  // 弹出错误的信息
          }
        })
      }).catch(() => {
      })
    },
    submitRequest() {
      this.$refs.formRef.validate().then(() => {
        this.formPreview = { ...this.form }; // 复制表单内容用于预览
        this.showDialog = true; // 显示对话框
      }).catch(err => {
        console.log('验证失败:', err);
      });
    },
    confirmSubmit() {
      this.form.state = 0;
      this.form.userId = this.user.id;
      this.$request.post('/serviceBook/add/', this.form)
        .then(res => {
          if (res.code === '200') {
            this.$message.success('申请成功');
            this.loadServices();
            this.form = { title: '', time: '', roomNumber: '', content: '' }; // 重置表单
          } else {
            this.$message.error(res.msg);
          }
        })
        .catch(err => {
          console.error("申请服务出错:", err);
        })
        .finally(() => {
          this.showDialog = false; // 关闭对话框
        });
    }
  }
}
</script>

<style scoped>
.main-content {
  width: 80%;
  margin: 20px auto;
  padding: 20px;
  background-color: #f5f8fa;
  border-radius: 10px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.card-header {
  font-size: 32px;
  font-weight: bold;
  text-align: center;
  color: white;
  background-color: #006eff;
  padding: 15px;
  border-radius: 8px;
}

.card-body {
  padding: 20px;
  background-color: white;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.section-title {
  font-size: 24px;
  font-weight: 600;
  color: #333;
  margin-top: 20px;
  margin-bottom: 10px;
}

.service-form {
  background-color: #fafafa;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
}

.service-table {
  margin-top: 15px;
  border-radius: 8px;
  overflow: hidden;
}

.el-table th {
  background-color: #e9f5ff;
  color: #333;
  font-weight: bold;
  font-size: 16px;
}

.el-table td {
  font-size: 14px;
}

.el-table .el-table__body tr:hover {
  background-color: #f5faff;
}

.submit-button-container {
  margin-top: 20px;
  text-align: right;
}

.my-button-primary {
  background-color: #006eff !important;
  border: none;
  font-size: 16px;
  font-weight: bold;
  padding: 10px 20px;
  color: white;
  border-radius: 5px;
  transition: background-color 0.3s ease;
}

.my-button-primary:hover {
  background-color: #0056cc !important;
}

.delete-button {
  color: #f56c6c; /* 删除按钮的红色 */
}

.delete-button:hover {
  color: #ff4d4f;
}
</style>

