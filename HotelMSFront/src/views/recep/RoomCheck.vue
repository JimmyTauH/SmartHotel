<template>
  <div class="main-content">
    <div v-for="room in tableData2" :key="room.id" class="room-card">
      <h3>房间号：{{ room.roomnumber || "未分配" }}</h3>
      <p><strong>房间类型：</strong>{{ room.activityName }}</p>
      <p><strong>预订人：</strong>{{ room.userName }}</p>
      <p><strong>预订平台：</strong>{{ room.form }}</p>
      <p><strong>入住日期：</strong>{{ room.applystart }} - {{ room.applyend }}</p>
      <p><strong>预订时间：</strong>{{ new Date(room.time).toLocaleString("zh-CN") }}</p>
    </div>
    <pagination :total="total" :page-size="pageSize" @change="load" />
  </div>
</template>

<script>
import Comment from "@/components/Comment.vue";

export default {
  name: "RoomCheck",
  data() {
    return {
      tableData2: [], // 预约数据
      pageSize: 10, // 每页显示的个数
      pageNum: 1, // 当前的页码
      total: 0 // 数据总条数
    };
  },

  created() {
    this.load(1); // 加载第一页数据
    this.$request.put("/activity/updateCount/" + this.activityId);
  },

  methods: {
    load(pageNum) {
      if (pageNum) this.pageNum = pageNum;
      this.$request
        .get("/activitySign/selectPage", {
          params: {
            pageNum: this.pageNum,
            pageSize: this.pageSize
          }
        })
        .then(res => {
          this.tableData2 = res.data?.list || this.tableData2; // 如果没有数据则使用默认数据
          this.total = res.data?.total || this.tableData2.length;
        });
    }
  }
};
</script>

<style scoped>
.main-content {
  display: flex;
  flex-wrap: wrap;
  gap: 15px;
  justify-content: center;
  padding: 20px;
}

.room-card {
  width: 320px;
  padding: 20px;
  border: 1px solid #c8c8c8;
  border-radius: 10px;
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.15);
  background-color: #f9f9f9;
  transition: transform 0.3s;
}

.room-card:hover {
  transform: scale(1.05);
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);
}

.room-card h3 {
  margin: 0 0 10px;
  color: #007bff;
  font-size: 18px;
}

.room-card p {
  margin: 5px 0;
  font-size: 15px;
  color: #333;
}

.pagination {
  margin-top: 20px;
  display: flex;
  justify-content: center;
}
</style>
