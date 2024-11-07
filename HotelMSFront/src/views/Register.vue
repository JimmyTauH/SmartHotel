<template>
  <div>
    <main>
      <div class="box">
        <div class="inner-box">
          <div class="container">
            <div
              style="width: 350px; padding: 20px 30px; background-color: white; border-radius: 5px;margin-left: 50px; ">
              <div class="logo1" style="margin-top: -20px;">
                <img src="@/assets/imgs/pic4.png" alt="HotelMS">
                <h4 style="margin-top: 5px;">HotelMS智能酒店平台</h4>
              </div>

              <div class="heading" style="margin-top: 50px;margin-bottom: 20px;">
                <h2 style="width: 350px; font-size: 28px;margin-bottom: 40px;">Register right now? </h2>
              </div>
              <el-form :model="form" :rules="rules" ref="formRef">
                <el-form-item prop="username">
                  <el-input size="medium" prefix-icon="el-icon-user" placeholder="请输入账号"
                    v-model="form.username"></el-input>
                </el-form-item>
                <el-form-item prop="password">
                  <el-input size="medium" prefix-icon="el-icon-lock" placeholder="请输入密码" show-password
                    v-model="form.password"></el-input>
                </el-form-item>
                <el-form-item prop="confirmPass">
                  <el-input size="medium" prefix-icon="el-icon-lock" placeholder="请确认密码" show-password
                    v-model="form.confirmPass"></el-input>
                </el-form-item>

                <el-form-item prop="role">
                  <el-radio-group v-model="form.role">
                    <el-radio label="ADMIN">管理员</el-radio>
                    <el-radio label="USER">用户</el-radio>
                    <el-radio label="RECEP">酒店前台</el-radio>
                    <el-radio label="SERVER">后勤人员</el-radio>
                  </el-radio-group>
                </el-form-item>

                <el-form-item prop="code">
                  <div style="display: flex">
                    <el-input style="flex: 1" size="medium" v-model="code"></el-input>
                    <Identify :identifyCode="identifyCode" @click.native="refreshCode" />
                  </div>
                </el-form-item>

                <el-form-item>
                  <el-button class="sign-btn" style="width: 100%;" @click="register">注 册</el-button>
                </el-form-item>
                <div style="display: flex; align-items: center">
                  <div style="flex: 1"></div>
                  <div style="flex: 1; text-align: right">
                    已有账号？请 <a href="/login">登录</a>
                  </div>
                </div>
              </el-form>

            </div>
            <div class="pics">
              <div class="images-wrapper">
                <img src="@/assets/imgs/pic3.png" style="width: 100%;height: 100%;">
              </div>

              <div class="text-slider">
                <div class="text-wrap">
                  <div class="text-group" style="margin-top: -50px;">
                    <h2>HotelMS智能酒店平台</h2>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </main>
  </div>
</template>

<script>
import Identify from "@/components/Identify";

export default {
  name: "Register",
  components: {
    Identify
  },
  data() {
    // 验证码校验
    const validatePassword = (rule, confirmPass, callback) => {
      if (confirmPass === '') {
        callback(new Error('请确认密码'))
      } else if (confirmPass !== this.form.password) {
        callback(new Error('两次输入的密码不一致'))
      } else {
        callback()
      }
    }
    return {
      form: {
        username: '',
        password: '',
        confirmPass: '',
        role:'',
      },
      code: '', // 用户输入的验证码
      identifyCode: '', // 生成的验证码
      identifyCodes: '123456789ABCDEFGHGKMNPQRSTUVWXY', // 验证码规则
      rules: {
        username: [
          { required: true, message: '请输入账号', trigger: 'blur' },
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
        ],
        confirmPass: [
          { validator: validatePassword, trigger: 'blur' }
        ],
      }
    }
  }, 
  mounted() {
    this.refreshCode()
  },
  methods: {
    // 切换验证码
    refreshCode() {
      this.identifyCode = ''
      this.makeCode(this.identifyCodes, 4)
    },
    // 生成随机验证码
    makeCode(o, l) {
      for (let i = 0; i < l; i++) {
        this.identifyCode += this.identifyCodes[Math.floor(Math.random() * this.identifyCodes.length)]
      }
    },
    register() {
      if (!this.code) {
        this.$message.warning('请输入验证码')
        this.refreshCode()
        return
      }
      if (this.code.toLowerCase() !== this.identifyCode.toLowerCase()) {
        this.$message.warning('验证码错误')
        this.refreshCode()
        return
      }
      this.$refs['formRef'].validate((valid) => {
        if (valid) {
          // 验证通过
          this.$request.post('/register', this.form).then(res => {
            if (res.code === '200') {
              this.$router.push('/')  // 跳转登录页面
              this.$message.success('注册成功')
            } else {
              this.refreshCode()
              this.$message.error(res.msg)
            }
          })
        }
      })
    },
  }
}
</script>


<style scoped>
.container {
  height: 60vh;
  overflow: hidden;
  background-size: 100%;
  display: flex;
  align-items: center;
  color: #666;
}

a {
  color: #2a60c9;
}

main {
  width: 100%;
  min-height: 100vh;
  overflow: hidden;
  background-color: #8bd2de;
  padding: 2rem;
  display: flex;
  align-items: center;
  justify-content: center;
}

.box {
  position: relative;
  width: 100%;
  max-width: 1030px;
  height: 620px;
  background-color: #fff;
  border-radius: 3.3rem;
  box-shadow: 0 60px 40px -30px rgba(0, 0, 0, 0.27);
}

.inner-box {
  position: absolute;
  width: calc(100% - 3.8rem);
  height: calc(100% - 3.8rem);
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
}

.pics {
  position: absolute;
  margin-top: 1%;
  height: 95%;
  width: 55%;
  top: 0;
  left: 45%;
  background-color: #ece8e5;
  border-radius: 2rem;
  display: grid;
  grid-template-rows: auto 1fr;
  transition: 0.8s ease-in-out;
}

.images-wrapper {
  display: grid;
  grid-template-columns: 1fr;
  grid-template-rows: 1fr;
}

.text-group {
  display: flex;
  flex-direction: column;
  text-align: center;
  color: #0c0c0c;
}

.logo1 {
  display: flex;
  align-items: center;
}

.logo1 img {
  width: 40px;
  margin-right: 0.3rem;
}

.logo1 h4 {
  font-size: 1.1rem;
  margin-top: 14px;
  letter-spacing: -0.5px;
  color: #151111;
}

.heading h2 {
  width: 100%;
  margin-top: -40px;
  font-size: 2.1rem;
  font-weight: 600;
  color: #151111;
}

.sign-btn {
  display: inline-block;
  width: 100%;
  height: 43px;
  background-color: #f6a09a;
  color: #fff;
  border: none;
  cursor: pointer;
  border-radius: 0.8rem;
  font-size: 0.95rem;
  margin-bottom: 2rem;
  transition: 0.3s;
}

.sign-btn:hover {
  background-color: #b5acf0;
}
</style>