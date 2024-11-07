<template>
  <div class="main-content">
    <div class="card-header">
      酒店服务申请
    </div>
    <div class="card-body">
      <div class="section-title">
        已申请服务
      </div>
      <el-table :data="filteredServices" style="width: 100%" class="service-table">
        <el-table-column prop="title" label="服务类型" width="200"></el-table-column>
        <el-table-column prop="time" label="预约时间" width="250"></el-table-column>
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
      services: [],
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
    }
  },
  methods: {
    loadServices() {
      if (!this.user.id) {
        console.error("用户ID无效!无法加载服务!");
        return;
      }

      this.$request.get(`/serviceBook/selectAll`)
        .then(res => {
          this.services = res.data || [];
        })
        .catch(err => {
          console.error("加载服务出错:", err);
        });
    },
  }
}
</script>

<style scoped>
.main-content {
  width: 80%;
  margin: 20px auto;
  padding: 20px;
  border-radius: 8px;
  background-color: #f9f9f9;
}

.card-header {
  font-weight: bold;
  font-size: 32px;
  text-align: center;
  color: #333;
  background-color: #006eff;
  padding: 15px;
  border-radius: 8px;
  color: white;
  margin-bottom: 20px;
}

.card-body {
  background-color: white;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  padding: 20px;
}

.section-title {
  font-size: 28px;
  color: #333;
  font-weight: 600;
  margin-bottom: 15px;
}

.service-table {
  border: 1px solid #eee;
  border-radius: 8px;
}

.el-table th {
  background-color: #f0f4f7;
  font-size: 16px;
  color: #555;
  padding: 12px;
}

.el-table td {
  font-size: 14px;
  padding: 10px;
}

.el-table tr:hover {
  background-color: #f5f5f5;
}

.el-table .el-table__body-wrapper {
  border-radius: 8px;
  overflow: hidden;
}

.el-table .el-table__row {
  border-bottom: 1px solid #eee;
}

.el-table .el-table__row:last-child {
  border-bottom: none;
}

.el-table .el-table__cell {
  color: #666;
}

.el-table .el-table__header-wrapper th {
  text-align: center;
}

</style>
