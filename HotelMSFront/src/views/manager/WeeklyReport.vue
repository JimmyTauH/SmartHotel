<template>
  <div>
    <el-row :gutter="20" style="padding: 20px; text-align: center;">
      <!-- 周选择器和查询按钮 -->
      <el-col :span="8" style="display: inline-block;">
        <el-date-picker
          v-model="selectedWeek"
          type="week"
          format="yyyy 第 WW 周"
          placeholder="选择一周"
          :default-value="defaultWeek"
          @change="handleWeekChange"
          style="width: 100%; font-size: 18px;"
        />
      </el-col>
      <el-col :span="4" style="display: inline-block;">
        <el-button type="primary" @click="fetchData" style="font-size: 18px; padding: 12px 20px;">
          查询
        </el-button>
      </el-col>
    </el-row>

    <!-- 显示总预定量和总预定金额 -->
    <el-row style="text-align: center; font-size: 18px; margin-top: 20px;">
      <el-col :span="8">本周总预定量: <strong>{{ summary.totalReservations }}</strong> 条</el-col>
      <el-col :span="8">本周总预定金额: <strong>{{ summary.totalAmount }}</strong> 元</el-col>
      <el-col :span="8">本周总入住量: <strong>{{ checkinoutSummary.totalCheckins }}</strong> 人次</el-col>
    </el-row>

    <!-- 折线图：每天的预定量和金额 -->
    <div style="width: 80%; margin: 0 auto; margin-top: 30px;">
      <h3 style="text-align: center">每天的预定量和金额</h3>
      <div ref="lineChart" style="height: 400px;"></div>
    </div>

    <!-- 折线图：每天的入住和退房人次 -->
    <div style="width: 80%; margin: 0 auto; margin-top: 30px;">
      <h3 style="text-align: center">每天的入住和退房人次</h3>
      <div ref="checkinoutChart" style="height: 400px;"></div>
    </div>

    <!-- 饼图：按房型的一周总预定量和总预定金额 -->
    <div style="width: 80%; margin: 0 auto; margin-top: 30px;">
      <h3 style="text-align: center">按房型统计的一周预定量和金额</h3>
      <div ref="pieChart" style="height: 400px;"></div>
    </div>
  </div>
</template>

<script>
import * as echarts from 'echarts';
import { Message } from 'element-ui';

export default {
  name: 'WeeklyReport',
  data() {
    return {
      selectedWeek: null,
      defaultWeek: this.getCurrentWeekStartDate(),
      summary: {
        totalReservations: 0,
        totalAmount: 0,
      },
      checkinoutSummary: {
        totalCheckins: 0,
      },
      dailyData: [],
      roomTypeData: [],
      checkinoutData: [],
    };
  },
  created() {
    this.selectedWeek = this.defaultWeek;
    this.fetchData();
  },
  methods: {
    getCurrentWeekStartDate() {
      const today = new Date();
      const dayOfWeek = today.getDay();
      const startOfWeek = new Date(today);
      startOfWeek.setDate(today.getDate() - (dayOfWeek === 0 ? 6 : dayOfWeek - 1));
      startOfWeek.setHours(0, 0, 0, 0);
      return startOfWeek;
    },
    handleWeekChange(date) {
      const dayOfWeek = date.getDay();
      const startOfWeek = new Date(date);
      startOfWeek.setDate(date.getDate() - (dayOfWeek === 0 ? 6 : dayOfWeek - 1));
      startOfWeek.setHours(0, 0, 0, 0);
      this.selectedWeek = startOfWeek;
    },
    async fetchData() {
      const startOfWeek = new Date(this.selectedWeek);
      const endOfWeek = new Date(startOfWeek);
      endOfWeek.setDate(startOfWeek.getDate() + 6);

      const startDate = this.formatDate(startOfWeek);
      const endDate = this.formatDate(endOfWeek);

      try {
        this.$message.info(`开始获取数据：从 ${startDate} 到 ${endDate}`);

        // 获取预定数据
        const reservationResponse = await this.$request.get('/week_report/activity_sign_week', {
            params: { startDate, endDate },
        });
        const reservationData = reservationResponse || {};
        this.summary = reservationData.weeklySummary || { totalReservations: 0, totalAmount: 0 };
        this.dailyData = reservationData.dailySummaries || [];
        this.roomTypeData = reservationData.roomTypeSummaries || [];

        // 获取入住退房数据
        const checkinoutResponse = await this.$request.get('/week_report/checkinout_week', {
            params: { startDate, endDate },
        });
        const checkinoutData = checkinoutResponse || {};
        this.checkinoutSummary = checkinoutData.weeklySummary || { totalCheckins: 0 };
        this.checkinoutData = checkinoutData.dailySummaries || [];

        this.$message.success("数据加载成功！");
        this.renderLineChart();
        this.renderPieChart();
        this.renderCheckinoutChart();
      } catch (error) {
        this.$message.error(`数据加载失败：${error.message || "未知错误"}`);
        console.error("请求失败:", error);

        this.summary = { totalReservations: 0, totalAmount: 0 };
        this.dailyData = [];
        this.roomTypeData = [];
        this.checkinoutSummary = { totalCheckins: 0 };
        this.checkinoutData = [];
      }
    },
    formatDate(date) {
      const year = date.getFullYear();
      const month = String(date.getMonth() + 1).padStart(2, '0');
      const day = String(date.getDate()).padStart(2, '0');
      return `${year}-${month}-${day}`;
    },
    renderLineChart() {
      const lineChart = echarts.init(this.$refs.lineChart);

      const dates = this.dailyData.map(item => item.date);
      const dailyReservations = this.dailyData.map(item => item.totalReservations);
      const dailyAmounts = this.dailyData.map(item => item.totalAmount);

      const option = {
        title: { text: '每天的预定量和金额', left: 'center' },
        tooltip: { trigger: 'axis' },
        legend: { data: ['预定量', '金额'], bottom: 10 },
        xAxis: { type: 'category', data: dates },
        yAxis: [
          { type: 'value', name: '预定量' },
          { type: 'value', name: '金额' },
        ],
        series: [
          { name: '预定量', type: 'line', data: dailyReservations },
          { name: '金额', type: 'line', yAxisIndex: 1, data: dailyAmounts },
        ]
      };

      lineChart.setOption(option);
    },
    renderPieChart() {
      const pieChart = echarts.init(this.$refs.pieChart);

      const roomTypes = [...new Set(this.roomTypeData.map(item => item.roomType))];
      const roomTypeSummary = roomTypes.map(type => ({
        name: type,
        value: this.roomTypeData
          .filter(item => item.roomType === type)
          .reduce((sum, item) => sum + item.totalReservations, 0),
      }));

      const option = {
        title: { text: '按房型的一周总预定量', left: 'center' },
        tooltip: { trigger: 'item' },
        legend: { orient: 'vertical', left: 'left' },
        series: [
          {
            name: '房型预定量',
            type: 'pie',
            radius: '50%',
            data: roomTypeSummary,
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]
      };

      pieChart.setOption(option);
    },
    renderCheckinoutChart() {
      const checkinoutChart = echarts.init(this.$refs.checkinoutChart);

      const dates = this.checkinoutData.map(item => item.date);
      const dailyCheckins = this.checkinoutData.map(item => item.checkins);
      const dailyCheckouts = this.checkinoutData.map(item => item.checkouts);

      const option = {
        title: { text: '每天的入住和退房人次', left: 'center' },
        tooltip: { trigger: 'axis' },
        legend: { data: ['入住人次', '退房人次'], bottom: 10 },
        xAxis: { type: 'category', data: dates },
        yAxis: { type: 'value', name: '人次' },
        series: [
          { name: '入住人次', type: 'line', data: dailyCheckins },
          { name: '退房人次', type: 'line', data: dailyCheckouts },
        ]
      };

      checkinoutChart.setOption(option);
    }
  }
};
</script>

<style scoped>
.el-row {
  display: flex;
  justify-content: center;
  align-items: center;
}

.el-col {
  text-align: center;
}
</style>
