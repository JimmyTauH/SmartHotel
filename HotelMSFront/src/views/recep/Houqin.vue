<template>
  <div>
    <el-form :model="form" ref="formRef" label-width="200px">
      <el-form-item label="房间号" prop="rooms">
        <el-select v-model="form.rooms" multiple placeholder="请选择房间号">
          <el-option
              v-for="room in roomOptions"
              :key="room.value"
              :label="room.label"
              :value="room.value"
          ></el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="预约时间" prop="time">
        <el-date-picker
            v-model="form.time"
            type="datetime"
            placeholder="选择预约时间"
        ></el-date-picker>
      </el-form-item>

      <el-button type="primary" @click="batchCreateOrders">
        批量创建订单
      </el-button>
    </el-form>
  </div>
</template>

<script>
export default {
  name: "Houqin",
  data() {
    return {
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      form: {
        rooms: [], // 多选房间号
        time: '',  // 预约时间
      },
      roomOptions: [
        // TODO: 动态加载房间列表
        { label: '101', value: '101' },
        { label: '102', value: '102' },
        { label: '103', value: '103' },
      ],
    }
  },
  created() {

  },
  methods: {
    batchCreateOrders() {
      if (!this.form.rooms.length || !this.form.time) {
        this.$message.error("请填写完整信息");
        return;
      }

      // 构造批量订单数据
      const orders = this.form.rooms.map((room) => ({
        title: "房间清洁",
        room,
        time: this.form.time,
        content: "定时打扫",
        user: this.user.id,
        state: 0,
      }));

      // 发送请求到后端
      this.$request.post("/serviceBook/batchAdd", orders)
          .then(res => {
            if (res.code === '200') {
              this.$message.success('申请成功');
              // this.loadServices();
              // this.form = { title: '', time: '', roomNumber: '', content: '' }; // 重置表单
            } else {
              this.$message.error(res.msg);
            }
          })
          .catch(err => {
            console.error("申请服务出错:", err);
          })
          .finally(() => {
            // this.showDialog = false; // 关闭对话框
          });
    },

    resetForm() {
      this.form.rooms = [];
      this.form.time = '';
    },
  }

}
</script>
