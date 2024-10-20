<template>
  <div class="main-content" style="width: 50%">
    <div class="card" style="font-weight: bold;font-size: 28px;display: flex">
      <div style="margin-right: 80px">现有积分：{{ newUser.integral }}</div>
      <div>签到天数：{{ count }}</div>
      <div style="flex: 1;text-align: right"><!-- 添加签到按钮 -->
        <el-button size="medium" @click="handleCheckIn()" type="primary">签到</el-button>
        <el-button size="medium" @click="drawer = true" type="success">充值</el-button>
      </div>
    </div>
    <div class="card" style="margin-top: 10px">
      <div style="color: #999999">最近30条记录</div>
      <el-table
          :data="tableData"
          style="width: 100%">
        <el-table-column
            prop="type"
            label="方式"
            width="200">
        </el-table-column>
        <el-table-column
            prop="time"
            label="获取日期"
            width="200">
        </el-table-column>
        <el-table-column
            prop="integral"
            label="积分变动"
            width="200">
        </el-table-column>
      </el-table>
    </div>
    <el-drawer
        title="充值"
        :visible.sync="drawer"
        :direction="direction"
        :before-close="handleClose">
      <el-card shadow="never" style="padding: 20px;">
        <div class="card">
          <el-row :gutter="10">
            <el-col :span="6" v-for="item in items" :key="item">
              <div class="charge-item" :class="{ 'active': current === item }" @click="clickItem(item)">
                ￥{{ item }}
              </div>
            </el-col>
          </el-row>

          <div style="margin-top: 20px;">
            <el-button type="primary" size="medium" @click="handlePay()" style="background-color: #f94b4b;">支 付</el-button>
          </div>
        </div>
      </el-card>
    </el-drawer>
  </div>
</template>
<script>
export default {
  name: "SingIn",
  data() {
    return {
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      newUser: {},
      integral: 10,
      count: 0,
      value: new Date(),
      tableData: [],
      drawer: false,
      direction: 'rtl',
      current: 6,
      items: [6, 12, 30, 68, 98, 128, 328, 648]
    };
  },
  mounted() {
    this.load()
  },
  methods: {
    clickItem(item) {
      this.current = item
    },
    handleClose(done) {
      this.$confirm('确认关闭？')
          .then(_ => {
            done();
          })
          .catch(_ => {
          });
    },
    load() {
      // 获取用户信息
      this.$request.get('user/select/' + this.user.id).then(res => {
        this.newUser = res.data || {}
      })
      // 获取签到记录(30条)
      this.$request.get('/records/selectAll', {
        params: {
          userId: this.user.id
        }
      }).then(res => {
        this.tableData = res.data || []
      })
      // 获取签到记录总数
      this.$request.get('/signIn/selectCount/' + this.user.id).then(res => {
        this.count = res.data || 0
      })
    },
    handleCheckIn() {
      this.$request.post('/signIn/add', {
        userId: this.user.id,
        integral: this.integral
      }).then(res => {
        if (res.code === '200') {
          this.$message.success('签到成功')
          this.$request.put('/user/updateIntegral2', {
            id: this.user.id,
            integral: this.integral
          }).then(res => {
            if (res.code === '200') {
              this.$message.success('积分更新成功')
              this.load()
            } else {
              this.$message.error('积分更新失败')
            }
          })
        } else {
          this.$message.error(res.msg)
        }
      })
      this.load()
    },
    handlePay() {
      this.$request.put('/user/updateIntegral', {
        id: this.user.id,
        integral: this.current
      }).then(res => {
        if (res.code === '200') {
          this.$message.success('充值成功')
          this.load()
        } else {
          this.$message.error('充值失败')
        }
      })

    }
  },
}

</script>

<style scoped>
.charge-item {
  border: 1px solid #ccc;
  padding: 20px 0;
  margin-bottom: 10px;
  text-align: center;
  font-size: 20px;
  color: #888;
  border-radius: 5px;
  cursor: pointer;
}

.active {
  background-color: #f94b4b;
  color: white;
  border-color: #f94b4b;
}
</style>