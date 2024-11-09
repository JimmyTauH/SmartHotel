<template>
  <div>
    <el-row :gutter="20" style="padding: 20px; text-align: center;">
      <!-- 月选择器和查询按钮 -->
      <el-col :span="8" style="display: inline-block;">
        <el-date-picker
          v-model="selectedMonth"
          type="month"
          format="yyyy-MM"
          placeholder="选择一个月"
          :default-value="defaultMonth"
          @change="handleMonthChange"
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
      <el-col :span="8">本月总预定量: <strong>{{ summary.totalReservations }}</strong> 条</el-col>
      <el-col :span="8">本月总预定金额: <strong>{{ summary.totalAmount }}</strong> 元</el-col>
      <el-col :span="8">本月总入住量: <strong>{{ checkinoutSummary.totalCheckins }}</strong> 人次</el-col>
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

    <!-- 饼图：按房型的本月总预定量和总预定金额 -->
    <div style="width: 80%; margin: 0 auto; margin-top: 30px;">
      <h3 style="text-align: center">按房型统计的本月预定量和金额</h3>
      <div ref="pieChart" style="height: 400px;"></div>
    </div>
  </div>
</template>

<script>
import * as echarts from 'echarts';
import { Message } from 'element-ui';

export default {
  name: 'MonthlyReport',
  data() {
    return {
      selectedMonth: null,
      defaultMonth: this.getCurrentMonthStartDate(),
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
    this.selectedMonth = this.defaultMonth;
    this.fetchData();
  },
  methods: {
    getCurrentMonthStartDate() {
      const today = new Date();
      const startOfMonth = new Date(today.getFullYear(), today.getMonth(), 1);
      return startOfMonth;
    },
    handleMonthChange(date) {
      const startOfMonth = new Date(date.getFullYear(), date.getMonth(), 1);
      this.selectedMonth = startOfMonth;
    },
    async fetchData() {
      const startOfMonth = new Date(this.selectedMonth);
      const endOfMonth = new Date(startOfMonth.getFullYear(), startOfMonth.getMonth() + 1, 0);

      const startDate = this.formatDate(startOfMonth);
      const endDate = this.formatDate(endOfMonth);

      try {
        this.$message.info(`开始获取数据：从 ${startDate} 到 ${endDate}`);

        // 获取预定数据
        const reservationResponse = await this.$request.get('/month_report/activity_sign_month', {
            params: { startDate, endDate },
        });
        const reservationData = reservationResponse || {};
        this.summary = reservationData.monthlySummary || { totalReservations: 0, totalAmount: 0 };
        this.dailyData = reservationData.dailySummaries || [];
        this.roomTypeData = reservationData.roomTypeSummaries || [];

        // 获取入住退房数据
        const checkinoutResponse = await this.$request.get('/month_report/checkinout_month', {
            params: { startDate, endDate },
        });
        const checkinoutData = checkinoutResponse || {};
        this.checkinoutSummary = checkinoutData.monthlySummary || { totalCheckins: 0 };
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
        title: { text: '按房型的本月总预定量', left: 'center' },
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