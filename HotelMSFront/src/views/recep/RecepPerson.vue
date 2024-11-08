<template>
  <div style="display: flex; justify-content: center; align-items: center; height: 100vh; background-color: #f9f5f0;">
    <el-card style="width: 50%; box-shadow: 0 4px 10px rgba(0, 0, 0, 0.15); border-radius: 12px; overflow: hidden;">
      <div style="background-color: #d4a373; color: white; text-align: center; padding: 12px 0; font-size: 20px; font-weight: bold;">
        酒店前台 - 用户信息
      </div>
      <el-form :model="user" label-width="100px" style="padding: 20px;">
        <div style="margin: 15px; text-align: center">
          <el-upload
            class="avatar-uploader"
            :action="$baseUrl + '/files/upload'"
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
          >
            <img v-if="user.avatar" :src="user.avatar" class="avatar" />
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </div>
        <el-form-item label="用户名" prop="username">
          <el-input v-model="user.username" placeholder="用户名" disabled style="border-radius: 8px;"></el-input>
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-input v-model="user.name" placeholder="姓名" style="border-radius: 8px;"></el-input>
        </el-form-item>
        <el-form-item label="电话" prop="phone">
          <el-input v-model="user.phone" placeholder="电话" style="border-radius: 8px;"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="user.email" placeholder="邮箱" style="border-radius: 8px;"></el-input>
        </el-form-item>
        <div style="text-align: center; margin-top: 20px">
          <el-button type="primary" @click="update" style="width: 100px; border-radius: 8px; background-color: #d4a373; color: white;">保存</el-button>
        </div>
      </el-form>
    </el-card>
  </div>
</template>

<script>
export default {
  name: "RecepPerson",
  data() {
    return {
      user: JSON.parse(localStorage.getItem('xm-user') || '{}')
    }
  },
  methods: {
    update() {
      this.$request.put('/recep/update', this.user).then(res => {
        if (res.code === '200') {
          this.$message.success('保存成功')
          localStorage.setItem('xm-user', JSON.stringify(this.user))
          this.$emit('update:user')
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    handleAvatarSuccess(response) {
      this.$set(this.user, 'avatar', response.data)
    },
  }
}
</script>

<style scoped>
.el-card {
  border-radius: 12px;
}
.el-form-item__label {
  font-weight: bold;
  color: #6d4c41;
}
.avatar-uploader .el-upload {
  border-radius: 50%;
  border: 2px solid #e0e0e0;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  width: 120px;
  height: 120px;
  display: inline-block;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}
.avatar-uploader .el-upload:hover {
  border-color: #d4a373;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #b0a8a2;
  line-height: 120px;
  text-align: center;
}
.avatar {
  width: 120px;
  height: 120px;
  display: block;
  border-radius: 50%;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}
.el-button--primary {
  background-color: #d4a373;
  border-color: #d4a373;
}
</style>
