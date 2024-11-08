<template>
  <div style="display: flex; justify-content: center; align-items: center; height: 100vh; background-color: #f5f5f5;">
    <el-card style="width: 50%; box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1); border-radius: 10px; overflow: hidden;">
      <div style="background-color: #409eff; color: white; text-align: center; padding: 10px 0; font-size: 18px; font-weight: bold;">
        用户信息编辑
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
          <el-input v-model="user.username" placeholder="用户名" disabled style="border-radius: 5px;"></el-input>
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-input v-model="user.name" placeholder="姓名" style="border-radius: 5px;"></el-input>
        </el-form-item>
        <el-form-item label="电话" prop="phone">
          <el-input v-model="user.phone" placeholder="电话" style="border-radius: 5px;"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="user.email" placeholder="邮箱" style="border-radius: 5px;"></el-input>
        </el-form-item>
        <div style="text-align: center; margin-top: 20px">
          <el-button type="primary" @click="update" style="width: 100px; border-radius: 5px; background-color: #409eff; color: white;">保存</el-button>
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
      this.$request.put('/server/update', this.user).then(res => {
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
  border-radius: 10px;
}
.el-form-item__label {
  font-weight: bold;
  color: #333;
}
.avatar-uploader .el-upload {
  border-radius: 50%;
  border: 1px solid #e0e0e0;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  width: 120px;
  height: 120px;
  display: inline-block;
}
.avatar-uploader .el-upload:hover {
  border-color: #409eff;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  line-height: 120px;
  text-align: center;
}
.avatar {
  width: 120px;
  height: 120px;
  display: block;
  border-radius: 50%;
}
.el-button--primary {
  background-color: #409eff;
  border-color: #409eff;
}
</style>
