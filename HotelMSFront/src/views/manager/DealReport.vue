<template>
  <div>
    <!-- 搜索区 -->
    <div class="search" style="margin-bottom: 20px;">
      <el-input placeholder="请输入设备名称查询" style="width: 200px" v-model="deviceName"></el-input>
      <el-input placeholder="请输入后勤人员 ID 查询" style="width: 200px; margin-left: 20px" v-model="logisticsId"></el-input>
      <el-button type="info" plain style="margin-left: 20px" @click="load(1)">查询</el-button>
      <el-button type="warning" plain style="margin-left: 20px" @click="reset">重置</el-button>
    </div>

    <!-- 操作区 -->
    <div class="operation" style="margin-bottom: 20px;">
      <el-button type="danger" plain @click="delBatch">批量删除</el-button>
    </div>

    <!-- 表格展示 -->
    <div class="table">
      <el-table :data="tableData" stripe @selection-change="handleSelectionChange" style="width: 100%;">
        <el-table-column type="selection" width="60" align="center"></el-table-column>
        <el-table-column prop="id" label="序号" width="100" align="center" sortable></el-table-column>
        <el-table-column prop="deviceName" label="设备名称" width="180" align="center"></el-table-column>
        <el-table-column label="故障描述" width="160" align="center">
          <template v-slot="scope">
            <div>
              <el-button @click="handleFaultContent(scope.row.description)">查看内容</el-button>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="image" label="上传图片" width="160" align="center">
          <template v-slot="scope">
            <div style="display: flex; justify-content: center; align-items: center;">
              <el-image v-if="scope.row.image" style="width: 50px; height: 50px; border-radius: 5px;" :src="scope.row.image" :preview-src-list="[scope.row.image]"></el-image>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="logisticsId" label="后勤人员ID" width="150" align="center"></el-table-column>
        <el-table-column prop="reportTime" label="报告时间" width="160" align="center"></el-table-column>
        <el-table-column label="操作" width="200" align="center">
          <template v-slot="scope">
            <el-button plain type="danger" size="mini" @click="del(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页 -->
      <div class="pagination" style="margin-top: 20px;">
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

      <el-dialog title="故障描述" :visible.sync="fromVisible1" width="60%" :close-on-click-modal="false" destroy-on-close>
      <div class="w-e-text">
        <div v-html="description"></div>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="fromVisible1 = false">关 闭</el-button>
      </div>
    </el-dialog>
    </div>
  </div>

</template>

<script>
import E from "wangeditor"
import hljs from 'highlight.js'

export default {
  name: "DealReport",
  data() {
    return {
      tableData: [],
      pageNum: 1,
      pageSize: 10,
      total: 0,
      deviceName: null,
      logisticsId: null,
      form: {},
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      rules: {},
      ids: [],
      description: '',
      fromVisible1: false,
    };
  },
  created() {
    this.load(1);
  },
  methods: {
    del(id) {
      this.$confirm('确定删除该故障报备吗？', '确认删除', {type: "warning"}).then(response => {
        this.$request.delete('/report-fault/delete/' + id).then(res => {
          if (res.code === '200') {
            this.$message.success('删除成功');
            this.load(1);
          } else {
            this.$message.error(res.msg);
          }
        });
      }).catch(() => {
      });
    },
    delBatch() {
      if (!this.ids.length) {
        this.$message.warning('请选择数据');
        return;
      }
      this.$confirm('您确定批量删除这些数据吗？', '确认删除', {type: "warning"}).then(response => {
        this.$request.delete('/report-fault/delete/batch', {data: this.ids}).then(res => {
          if (res.code === '200') {
            this.$message.success('操作成功');
            this.load(1);
          } else {
            this.$message.error(res.msg);
          }
        });
      }).catch(() => {
      });
    },
    load(pageNum) {
      if (pageNum) this.pageNum = pageNum;
      this.$request.get('/report-fault/selectPage', {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          deviceName: this.deviceName,
          logisticsId: this.logisticsId
        }
      }).then(res => {
        this.tableData = res.data?.list
        this.total = res.data?.total
      })
    },

    handleSelectionChange(rows) {   // 当前选中的所有的行数据
      this.ids = rows.map(v => v.id)   //  [1,2]
    },

    reset() {
      this.deviceName = null;
      this.logisticsId = null;
      this.load(1);
    },

    handleCurrentChange(pageNum) {
      this.load(pageNum)
    },

    handleFaultContent(description) {
      this.description = description
      this.fromVisible1 = true
    },
  }
};
</script>

<style scoped>
/* 调整表格列间距 */
.el-table th, .el-table td {
  padding: 10px;
  text-align: center;
}

/* 表单项之间的间距 */
.el-form-item {
  margin-bottom: 20px;
}

/* 弹窗底部按钮的间距 */
.dialog-footer {
  padding: 10px 20px;
}

/* 调整分页按钮的间距 */
.pagination {
  margin-top: 20px;
}

/* 弹窗内容区间距 */
.el-form {
  padding-right: 30px;
}
</style>
