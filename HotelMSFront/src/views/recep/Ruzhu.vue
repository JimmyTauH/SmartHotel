<template>
  <div>
    <div
      style="height: 100vh; overflow: hidden; display: flex; align-items: center; justify-content: center; flex-direction: column"
    >
      <div style="font-size: 20px; margin-bottom: 20px">入住办理</div>
      
      <!-- 滚动容器，设置为100vh和800px宽度 -->
      <div style="height: 100vh; overflow-y: auto; width: 800px; padding: 20px; border: 1px solid #e0e0e0; border-radius: 8px;">
        <!-- 房间选择，支持手动输入 -->
        <el-form :model="form" ref="form">
          <el-form-item label="选择或输入房间">
            <div style="display: flex; align-items: center;">
              <el-select 
                v-model="form.room" 
                placeholder="请选择或输入房间号"
                filterable 
                allow-create 
                default-first-option
                style="flex: 1; margin-right: 10px;"
              >
                <el-option v-for="room in availableRooms" :key="room.id" :label="room.name" :value="room.name" />
              </el-select>
              <el-button type="primary" icon="el-icon-refresh" @click="fetchAvailableRooms">
                查询
              </el-button>
            </div>
          </el-form-item>
          
          <!-- 动态添加客人信息 -->
          <div v-for="(guest, index) in form.guests" :key="index" style="margin-bottom: 10px;">
            <el-form-item label="客人信息">
              <el-input placeholder="姓名" v-model="guest.name" style="width: 20%; margin-right: 10px;"></el-input>
              <el-select v-model="guest.gender" placeholder="性别" style="width: 10%; margin-right: 10px;">
                <el-option label="男" value="男"></el-option>
                <el-option label="女" value="女"></el-option>
              </el-select>
              <el-input placeholder="身份证号" v-model="guest.idCard" style="width: 30%; margin-right: 10px;"></el-input>
              <el-input placeholder="联系电话" v-model="guest.phone" style="width: 30%;"></el-input>
              <el-button type="danger" icon="el-icon-minus" @click="removeGuest(index)">移除</el-button>
            </el-form-item>
          </div>

          <!-- 添加客人按钮 -->
          <el-form-item>
            <el-button type="primary" @click="addGuest">添加客人</el-button>
          </el-form-item>

          <!-- 办理入住按钮 -->
          <el-form-item>
            <el-button type="success" @click="handleCheckIn">办理入住</el-button>
            <router-link to="/front/home" style="margin-left: 10px;">返回首页</router-link>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Ruzhu",
  data() {
    return {
      availableRooms: [],  // 存储从 API 获取的可用房间列表
      form: {
        room: "",  // 选择或输入的房间号
        guests: [  // 客人信息
          {
            name: "",
            gender: "",
            idCard: "",
            phone: "",
          },
        ],
      },
    };
  },
  created() {
    this.fetchAvailableRooms();  // 初始化时获取可用房间
  },
  methods: {
    // 获取可用房间列表
    async fetchAvailableRooms() {
      try {
        const response = await this.$request.get('/recep/available-room');
        if (response.code === "200") {
          this.availableRooms = (response.data || []).map(room => ({
            id: room.id,
            name: room.name || `${room.id}`,
            status: room.status || '未知状态',
          }));
          this.$message.success("可用房间已更新！");
        } else {
          this.$message.error("获取可用房间失败：" + (response.msg || "未知错误"));
        }
      } catch (error) {
        this.$message.error("获取可用房间失败，请重试");
        console.error(error);
      }
    },
    // 添加新客人
    addGuest() {
      this.form.guests.push({
        name: "",
        gender: "",
        idCard: "",
        phone: "",
      });
    },
    // 移除客人
    removeGuest(index) {
      this.form.guests.splice(index, 1);
    },
    // 办理入住
    async handleCheckIn() {
      if (!this.form.room) {
        this.$message.error("请选择或输入房间号");
        return;
      }
      if (this.form.guests.length === 0 || this.form.guests.some(g => !g.name || !g.gender || !g.idCard || !g.phone)) {
        this.$message.error("请填写完整的客人信息");
        return;
      }

      try {
        await this.$request.post("/recep/checkin", this.form);  // 提交表单至 /recep/checkin API
        this.$message.success("办理入住成功！");
        this.form = { room: "", guests: [{ name: "", gender: "", idCard: "", phone: "" }] };  // 重置表单
      } catch (error) {
        this.$message.error("办理入住失败，请重试");
        console.error(error);
      }
    },
  },
};
</script>

<style scoped>
</style>