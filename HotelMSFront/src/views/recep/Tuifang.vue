<template>
  <div>
    <div
      style="height: 100vh; overflow: hidden; display: flex; align-items: center; justify-content: center; flex-direction: column"
    >
      <div style="font-size: 20px; margin-bottom: 20px">退房办理</div>
      
      <!-- 滚动容器 -->
      <div style="height: 100vh; overflow-y: auto; width: 800px; padding: 20px; border: 1px solid #e0e0e0; border-radius: 8px;">
        <el-form :model="form" ref="form">
          <!-- 房间选择，支持手动输入 -->
          <el-form-item label="选择或输入房间">
            <div style="display: flex; align-items: center;">
              <el-select 
                v-model="form.room" 
                placeholder="请选择或输入房间号"
                filterable 
                allow-create 
                default-first-option
                @change="fetchRoomGuests"
                style="flex: 1; margin-right: 10px;"
              >
                <el-option 
                  v-for="room in occupiedRooms" 
                  :key="room.id" 
                  :label="room.name || `房间 ${room.id}`" 
                  :value="room.id" 
                />
              </el-select>
              <el-button type="primary" icon="el-icon-refresh" @click="fetchOccupiedRooms">
                刷新房间列表
              </el-button>
            </div>
          </el-form-item>

          <!-- 显示客人信息 -->
          <div v-if="guests.length > 0" style="margin-bottom: 10px;">
            <div style="display: flex; justify-content: space-between; align-items: center;">
              <span>客人信息</span>
              <el-button type="primary" icon="el-icon-refresh" @click="refreshGuests">
                刷新客人信息
              </el-button>
            </div>
          </div>
          <div v-if="guests.length > 0">
            <div v-for="(guest, index) in guests" :key="guest.id" style="margin-bottom: 10px;">
              <el-form-item :label="'客人 ' + (index + 1)">
                <el-input placeholder="姓名" v-model="guest.name" disabled style="width: 20%; margin-right: 10px;"></el-input>
                <el-input placeholder="性别" v-model="guest.gender" disabled style="width: 10%; margin-right: 10px;"></el-input>
                <el-input placeholder="身份证号" v-model="guest.idCard" disabled style="width: 30%; margin-right: 10px;"></el-input>
                <el-input placeholder="联系电话" v-model="guest.phone" disabled style="width: 30%;"></el-input>
                <el-button type="danger" icon="el-icon-minus" @click="removeGuest(guest.id)">移除</el-button>
              </el-form-item>
            </div>
          </div>

          <!-- 全部退房按钮 -->
          <el-form-item v-if="guests.length > 0">
            <el-button type="danger" @click="clearRoom">全部退房</el-button>
          </el-form-item>
        </el-form>

        <!-- 如果没有客人显示信息 -->
        <div v-if="guests.length === 0" style="text-align: center; margin-top: 20px; color: #888;">暂无入住客人信息</div>

        <!-- 返回首页按钮 -->
        <router-link to="/front/home" style="display: block; margin-top: 20px; text-align: center;">返回首页</router-link>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Checkout",
  data() {
    return {
      occupiedRooms: [], // 存储从 API 获取的已入住房间列表
      form: {
        room: "", // 选择或输入的房间号
      },
      guests: [], // 房间入住客人列表
    };
  },
  created() {
    this.fetchOccupiedRooms(); // 初始化时获取已入住房间
  },
  methods: {
    // 获取已入住房间列表
    async fetchOccupiedRooms() {
      try {
        const response = await this.$request.get('/recep/occupied-room');
        if (response.code === "200") {
          // 格式化返回的房间列表
          this.occupiedRooms = (response.data || []).map(room => ({
            id: room.id,
            name: room.name || `房间 ${room.id}`,
            status: room.status || '未知状态',
          }));
          this.$message.success("已入住房间列表已更新！");
        } else {
          this.$message.error("获取已入住房间失败：" + (response.msg || "未知错误"));
        }
      } catch (error) {
        this.$message.error("获取已入住房间失败，请重试");
        console.error(error);
      }
    },
    // 查询房间入住的客人信息
    async fetchRoomGuests() {
      if (!this.form.room) {
        this.$message.error("请选择或输入房间号");
        return;
      }
      try {
        const response = await this.$request.get(`/recep/room-guests?room=${this.form.room}`);
        if (response.code === "200") {
          // 格式化数据，确保字段名匹配
          this.guests = (response.data || []).map(guest => ({
            id: guest.id,
            name: guest.guestName,
            gender: guest.gender,
            idCard: guest.guestId,
            phone: guest.guestPhone,
          }));
          if (this.guests.length === 0) {
            this.$message.info("该房间暂无入住客人");
          }
        } else {
          this.$message.error("查询客人信息失败：" + (response.msg || "未知错误"));
        }
      } catch (error) {
        this.$message.error("查询客人信息失败，请重试");
        console.error(error);
      }
    },
    // 主动刷新客人信息
    async refreshGuests() {
      this.fetchRoomGuests(); // 调用查询客人信息的方法
    },
    // 为单个客人退房
    async removeGuest(guestId) {
      try {
        await this.$request.post(`/recep/checkout`, { room: this.form.room, guestId });
        this.$message.success("退房成功！");
        this.guests = this.guests.filter(guest => guest.id !== guestId);
      } catch (error) {
        this.$message.error("退房失败，请重试");
        console.error(error);
      }
    },
    // 全部退房
    async clearRoom() {
      try {
        await this.$request.post(`/recep/checkout`, { room: this.form.room });
        this.$message.success("房间已全部退房！");
        this.guests = []; // 清空客人信息
      } catch (error) {
        this.$message.error("全部退房失败，请重试");
        console.error(error);
      }
    },
  },
};
</script>

<style scoped>
</style>