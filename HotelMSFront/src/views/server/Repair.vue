<template>
  <div class="main-content">
    <div class="card-header">
      设备报修
    </div>
    <div class="card-body">
      <div class="section-title">
        待处理故障
      </div>
      <el-table :data="filteredReports_noncompleted" style="width: 100%" class="report-table">
        <el-table-column prop="title" label="故障项目"></el-table-column>
        <el-table-column prop="room" label="房间号"></el-table-column>
        <el-table-column prop="time" label="报告时间"></el-table-column>
        <el-table-column prop="content" label="故障描述"></el-table-column>
          <!-- 操作列 -->
          <el-table-column label="操作" width="180" align="center">
          <template v-slot="scope">
            <el-button plain type="danger" size="mini" @click=updateReport(scope.row.id)>更新为已完成</el-button>
          </template>
        </el-table-column>

      </el-table>

      <div class="section-title">
        已处理故障
      </div>
      <el-table :data="filteredReports_completed" style="width: 100%" class="report-table">
        <el-table-column prop="title" label="故障项目"></el-table-column>
        <el-table-column prop="room" label="房间号"></el-table-column>
        <el-table-column prop="time" label="报告时间"></el-table-column>
        <el-table-column prop="content" label="故障描述"></el-table-column>
          <!-- 操作列 -->
          <el-table-column label="操作" width="180" align="center">
          <template v-slot="scope">
            <el-button plain type="danger" size="mini" @click=updateReport0(scope.row.id)>更新为未完成</el-button>
          </template>
        </el-table-column>
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
      reports: [],
    }
  },
  mounted() {
    this.loadReports();
  },
  computed: {
    filteredReports_noncompleted() {
      return this.reports.filter(item => item.state == 0);
    },
    filteredReports_completed() {
      return this.reports.filter(item => item.state == 1);
    },
  },
  methods: {
    loadReports() {
      if (!this.user.id) {
        console.error("用户ID无效!无法加载服务!");
        return;
      }
      console.log(this.user.birth);
      this.$request.get(`/repair/selectByHotel/`+this.user.birth)
        .then(res => {
          this.services = res.data || [];
        })
        .catch(err => {
          console.error("加载服务出错:", err);
        });
    },
    updateReports(id) {
      console.log(id);
      // 更新 state 为 1 表示服务完成
      this.$request.put(`/repair/updateState/`+id)
      .then(res => {
        if (res.code === '200') {
          this.$message.success('服务状态更新为已完成');
          this.loadReports(); // 刷新服务列表
        } else {
          this.$message.error(res.msg);
        }
      })
      .catch(err => {
        console.error("更新服务状态出错:", err);
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
