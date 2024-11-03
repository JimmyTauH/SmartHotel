<template>
  <div class="main-content" style="width: 50%">
    <div class="card" style="font-weight: bold; font-size: 28px; display: flex">
      酒店服务申请
    </div>
    <div class="card" style="margin-top: 10px">
      <div style="font-size: 28px; display: flex">
        已申请服务
      </div>
      <el-table :data="services" style="width: 100%">
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
      rules: {
        title: [{ required: true, message: '请选择服务类型', trigger: 'change' }],
        time: [{ required: true, message: '请选择预约时间', trigger: 'change' }],
      }
    }
  },
  mounted() {
    this.loadServices();
  },
  computed: {
    filteredServices() {
      return this.services.map(service => ({
        title: service.title,
        time: new Date(service.time).toLocaleString(),
        content: service.content
      }));
    },
    isFormDisabled() {
      return this.services.length >= 5; // 限制最多申请5个服务
    },
  },
  methods: {
    loadServices() {
      if (!this.user.id) {
        console.error("用户ID无效!无法加载服务!");
        return;
      }
      console.log(this.user.id);
      this.$request.get(`/serviceBook/selectByHotel/` + this.user.birth)
        .then(res => {
          this.services = res.data || [];
        })
        .catch(err => {
          console.error("加载服务出错:", err);
        });
    },
    submitRequest() {
      this.$refs.formRef.validate().then(() => {
        // 确保 user.id 存在并传递给后端
        this.form.userId = this.user.id; // 确保用户ID传递给后端
        this.$request.post('/serviceBook/add/', this.form)
          .then(res => {
            if (res.code === '200') {
              this.$message.success('申请成功');
              this.loadServices();
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
    },
    deleteService(serviceId) {
      this.$confirm('您确定删除该服务申请吗？', '确认删除', { type: 'warning' })
        .then(() => {
          this.$request.delete(`/serviceBook/delete/${serviceId}`)
            .then(res => {
              if (res.code === '200') {
                this.$message.success('删除成功');
                this.loadServices();
              } else {
                this.$message.error(res.msg);
              }
            })
            .catch(err => {
              console.error("删除服务出错:", err);
            });
        }).catch(err => {
          console.log('取消删除:', err);
        });
    }
  }
}
</script>

<style scoped>
.my-button-primary {
  background-color: #006eff !important;
  height: 70rpx;
  line-height: 70rpx;
  font-size: 28rpx;
}
</style>
