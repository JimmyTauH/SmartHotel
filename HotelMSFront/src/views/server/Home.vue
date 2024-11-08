<template>
  <div>
    <div class="welcome-card">
      <h2>欢迎使用后勤管理平台，{{ user?.name }}！</h2>
      <p>您的角色：{{ user?.role }}</p>
    </div>

    <el-row :gutter="20"> <!-- 使用 :gutter="20" -->
      <el-col :span="8">
        <!-- 系统公告卡片 -->
        <el-card class="box-card" style="height: 380px;">
          <div class="system-title">
            <span>系统公告</span>
          </div>
          <el-timeline>
            <el-timeline-item v-for="item in notices" :key="item.id" :timestamp="item.time">
              <el-popover placement="right" width="200" trigger="hover" :content="item.title">
                <span slot="reference">{{ item.title }}</span>
              </el-popover>
            </el-timeline-item>
          </el-timeline>
        </el-card>
      </el-col>

      <el-col :span="16">
        <!-- 任务进度 -->
        <el-card class="box-card" style="height: 380px;">
          <div class="system-title">
            <span>任务进度</span>
          </div>
          <el-progress :percentage="taskProgress" status="success" style="margin-top: 20px;">
            <span>{{ taskProgress }}%</span>
          </el-progress>
          <el-button type="primary" @click="viewTaskDetails" style="margin-top: 10px;">查看任务详情</el-button>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20">
      <el-col :span="12">
        <!-- 今日工作数据 -->
        <el-card class="box-card" style="height: 350px;">
          <div class="system-title">
            <span>今日工作数据</span>
          </div>
          <div ref="echarts1" style="height: 250px;"></div>
        </el-card>
      </el-col>

      <el-col :span="12">
        <!-- 员工任务分配饼图 -->
        <el-card class="box-card" style="height: 350px;">
          <div class="system-title">
            <span>员工任务分配</span>
          </div>
          <div ref="echarts2" style="height: 250px;"></div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20">
      <el-col :span="24">
        <!-- 最近设备使用情况 -->
        <el-card class="box-card" style="height: 380px;">
          <div class="system-title">
            <span>最近设备使用情况</span>
          </div>
          <div ref="echarts3" style="height: 250px;"></div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>


<script>
import * as echarts from 'echarts';

export default {
  name: 'LogisticsHome',
  data() {
    return {
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      notices: [],
      taskProgress: 75,  // 模拟任务进度
    };
  },
  created() {
      // 模拟从服务器获取公告
      this.$request.get('/notice/selectAll').then(res => {
    this.notices = res.data || [];
    console.log("Notices:", this.notices);  // 查看获取的数据
    }).catch(error => {
    console.error("获取公告数据失败:", error);
  });
  },
  mounted() {
    this.initCharts();
    window.onresize = this.resizeCharts;

  },
  methods: {

    initCharts() {

      // 今日工作数据（ECharts）
      let chart1 = echarts.init(this.$refs.echarts1);
      let option1 = {
        title: { text: '今日工作数据' },
        tooltip: { trigger: 'axis' },
        xAxis: { type: 'category', data: ['9:00', '12:00', '15:00', '18:00', '21:00'] },
        yAxis: { type: 'value' },
        series: [{ type: 'line', data: [120, 132, 101, 134, 90] }]
      };
      chart1.setOption(option1);

      // 员工任务分配（饼图）
      let chart2 = echarts.init(this.$refs.echarts2);
      let option2 = {
        title: { text: '员工任务分配', left: 'center' },
        series: [{
          type: 'pie',
          radius: ['50%', '70%'],
          data: [
            { value: 40, name: '张三' },
            { value: 30, name: '李四' },
            { value: 20, name: '王五' },
            { value: 10, name: '赵六' },
          ]
        }]
      };
      chart2.setOption(option2);

      // 最近设备使用情况（ECharts）
      let chart3 = echarts.init(this.$refs.echarts3);
      let option3 = {
        title: { text: '最近设备使用情况' },
        tooltip: { trigger: 'axis' },
        xAxis: { type: 'category', data: ['设备A', '设备B', '设备C', '设备D', '设备E'] },
        yAxis: { type: 'value' },
        series: [{ type: 'bar', data: [200, 150, 300, 500, 400] }]
      };
      chart3.setOption(option3);
    },
    resizeCharts() {
      this.$nextTick(() => {
        this.initCharts();
      });
    },
    viewTaskDetails() {
      this.$router.push('/server/work');
    },
  },
};
</script>

<style scoped lang="less">
.welcome-card {
  padding: 20px;
  background-color: #f4f6f9;
  border-radius: 10px;
  margin-bottom: 20px;
  text-align: center;
}

.system-title {
  font-size: 18px;
  font-weight: bold;
  margin-bottom: 15px;
}

.box-card {
  border-radius: 10px;
}

.el-row {
  margin-bottom: 20px;
}

.el-col {
  padding: 10px;
}

.el-card {
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  padding: 15px;
}

.el-timeline-item {
  font-size: 14px;
  color: #333;
}

.el-progress {
  margin-top: 20px;
}
</style>