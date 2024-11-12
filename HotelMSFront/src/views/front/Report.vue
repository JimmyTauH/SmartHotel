<template>
  <div class="main-content">
    <div class="card-header">
      设备报修
    </div>
    <div class="card-body">
      <div class="section-title">
        设备报修
      </div>
      <el-form :model="form" ref="formRef" label-width="150px" label-align="right" :rules="rules" class="report-form">
        <el-form-item label="故障项目" prop="title" required>
          <el-select v-model="form.title" placeholder="选择故障项目">
            <el-option
              v-for="option in reportOptions"
              :key="option.value"
              :label="option.label"
              :value="option.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="故障描述" prop="content" required>
          <el-input type="textarea" v-model="form.content" placeholder="请输入故障描述"></el-input>
        </el-form-item>
        
        <div class="submit-button-container">
          <el-button 
            class="my-button-primary"
            type="primary" 
            @click="submitRequest">
            提交
          </el-button>
        </div>
      </el-form>

      <div class="section-title">
        待处理故障
      </div>
      <el-table :data="filteredReports_noncompleted" style="width: 100%" class="service-table">
        <el-table-column prop="title" label="故障项目"></el-table-column>
        <el-table-column prop="content" label="描述"></el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script>
export default {
  name: "Report",
  data() {
    return {
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      form: {
        title: '',
        content: ''
      },
      reports: [],
      reportOptions: [
        { label: '水', value: "水" },
        { label: '电', value: "电" },
        { label: '木', value: "木" },
        { label: '网络', value: "网络" },
        { label: '空调', value: "空调" },
        { label: '热水', value: "热水" },
        { label: '照明', value: "照明" },
      ],
      rules: {
        title: [{ required: true, message: '请选择故障项目', trigger: 'change' }],
        content: [{ required: true, message: '请输入故障描述', trigger: 'change' }],
      }
    }
  },
  mounted() {
    this.loadReports();
  },
  computed: {
    filteredReports_completed() {
      return this.reports.filter(item => item.state == 1);
    },
    filteredReports_noncompleted() {
      return this.reports.filter(item => item.state == 0);
    },
    // isFormDisabled() {
    //   return this.services.length >= 20; // 限制最多申请5个服务
    // },
  },
  methods: {
    loadReports() {
      if (!this.user.id) {
        console.error("用户ID无效!无法加载报修列表!");
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
    deleteReport(id) {   // 单个删除
      console.log(id);
      this.$confirm('您确定删除吗？', '确认删除', {type: "warning"}).then(response => {
        this.$request.delete('/serviceBook/delete/' + id).then(res => {
          if (res.code === '200') {   // 表示操作成功
            this.$message.success('操作成功')
            //this.load(1)
            this.loadReports()
          } else {
            this.$message.error(res.msg)  // 弹出错误的信息
          }
        })
      }).catch(() => {
      })
    },
    submitRequest() {
      this.$refs.formRef.validate().then(() => {
        this.form.state = 0;
        this.form.user = this.user.id;
        this.$request.post('/serviceBook/add/', this.form)
          .then(res => {
            if (res.code === '200') {
              this.$message.success('申请成功');
              this.loadReports();
              this.form = { title: '', time: '', content: '' };
            } else {
              this.$message.error(res.msg);
            }
          })
          .catch(err => {
            console.error("申请服务出错:", err);
          });
      }).catch(err => {
        console.log('验证失败:', err);
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

