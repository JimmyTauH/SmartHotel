<template>
  <div class="main-content">
    <div class="card" style="margin-bottom: 10px">
      <div style="display: flex; grid-gap: 20px">
        <img :src="activity.cover" style="width: 40%; border-radius: 5px; height: 300px" alt="">
        <div style="flex: 1">
          <div style="font-weight: bold;font-size: 24px;margin-bottom: 10px">{{ activity.name }}</div>
          <div class="el-icon-set-up"
            style="color: #666666; font-size: 18px; margin-top: 5px;line-height: 22px;text-align: justify;margin-bottom: 10px">
            {{ activity.descr }}
          </div>
          <div style="display: flex; justify-content: space-between; align-items: center;margin-top: 10px">
            <div style="display: flex; align-items: center;">
              <span style="font-weight: bold; font-size: 25px; color: #333;">¥ 房价:</span>
              <span style="font-weight: bold; font-size: 22px; color: #FFD700; margin-left: 25px;">{{
                activity.host
              }}</span>
            </div>
            <div style="display: flex; align-items: center;margin-right: 50px;">
              <span style="font-weight: bold; font-size: 20px; color: #999;">剩余房量:</span>
              <span style="font-weight: bold; font-size: 20px; margin-left: 25px;">{{ activity.number }}</span>
            </div>

          </div>
          <div style="margin-bottom: 20px;margin-top: 20px;" class="table">

            <el-table :data="filteredTableData" stripe @selection-change="handleSelectionChange">

              <el-table-column prop="applystart" label="预订房间入住时间"></el-table-column>
              <el-table-column prop="applyend" label="预订房间退房时间"></el-table-column>
              <el-table-column prop="form" label="订房渠道"></el-table-column>

              <el-table-column label="操作" width="180" align="center">
                <template v-slot="scope">
                  <el-button plain type="primary" @click="handleEdit(scope.row)" size="mini">查看订房信息</el-button>
                </template>
              </el-table-column>

            </el-table>
          </div>
          <div style="display: flex">

            <el-button type="success" v-if="activity.isSign" :key="sighText" @click="cancel"
              @mouseenter.native="sighText = '取消预订'" @mouseleave.native="sighText = '已预订'">
              {{ sighText }}
            </el-button>
            <el-button type="primary" v-else key="预订该房" @click="sign">预订该房</el-button>
          </div>
          <div
            style="font-weight: bold; font-size: 24px;margin-bottom: 20px;padding-left: 10px;border-left: 5px solid #409EFF">
            房间号:
          </div>

        </div>
      </div>
    </div>
    <!--酒店详情-->
    <div class="card" style="margin-bottom: 10px">
      <div
        style="font-weight: bold; font-size: 24px;margin-bottom: 20px;padding-left: 10px;border-left: 5px solid #409EFF">
        该房型详情
      </div>
      <div class="w-e-text">
        <div v-html="activity.content"></div>
      </div>

    </div>



    <!-- <div class="custom-card" style="margin-bottom: 10px" v-if="activity.hotelId != null" @click="goDetail(hotel.id)">
      <div style="display: flex; grid-gap: 20px">
        <img :src="hotel.cover" style="width: 50%; border-radius: 5px; height: auto" alt="">
        <div style="flex: 1">
          <div style="font-weight: bold;font-size: 24px;margin-bottom: 20px;margin-top: 10px">{{ hotel.name }}</div>
          <div
            style="color: #666666; font-size: 13px; line-height: 22px;text-align: justify;margin-bottom: 20px;margin-right: 20px">
            {{ hotel.descr }}
          </div>
        </div>
      </div>
      
    </div> -->
    <!--评论-->
    <div>
      <Comment :fid="activityId" module="酒店" />
    </div>
    <div class="card" style="text-align: center; font-size: 20px; color: #666; margin-bottom: 10px">
      <span style="margin-right: 20px; cursor: pointer" @click="handleLike" :class="{ 'active': activity.isLike }"><i
          class="el-icon-like"></i> {{ activity.likesCount }}</span>

      <span style=" cursor: pointer" @click="handleCollect" :class="{ 'active': activity.collectCount }"><i
          class="el-icon-star-off"></i>{{ activity.collectCount }}</span>

      <span style="margin-left: 20px;"><i class="el-icon-view"></i> {{ activity.readCount }}</span>
    </div>
    <div>
      <el-dialog title="订房信息" :visible.sync="fromVisible" width="40%" :close-on-click-modal="false" destroy-on-close>
        <el-form label-width="100px" style="padding-right: 50px" :model="form" :rules="rules" ref="formRef">

          <div style="font-weight: bold;font-size: 15px;margin-bottom: 10px"> 房型名：{{ activity.name }}</div>
          <el-form-item prop="applystart" label="房间入住时间">
            <el-date-picker v-model="form.applystart" type="date" placeholder="请选择房间入住时间" value-format="yyyy-MM-dd"
              format="yyyy-MM-dd" />
          </el-form-item>
          <el-form-item prop="applyend" label="房间退房时间">
            <el-date-picker v-model="form.applyend" type="date" placeholder="请选择房间退房时间" value-format="yyyy-MM-dd"
              format="yyyy-MM-dd" />
          </el-form-item>

          <el-form-item label="订房渠道" prop="form">
            <el-select style="width: 100%" v-model="form.form">
              <el-option value="美团"></el-option>
              <el-option value="携程"></el-option>
              <el-option value="抖音"></el-option>
              <el-option value="去哪儿旅行"></el-option>
              <el-option value="其他"></el-option>
            </el-select>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="fromVisible = false">取 消</el-button>
          <el-button type="primary" @click="save">确 定</el-button>
        </div>
      </el-dialog>

    </div>
  </div>
</template>
<script>
import Comment from "@/components/Comment.vue";
import E from "wangeditor"
import hljs from 'highlight.js'

export default {
  name: "ActivityDetail",
  data() {
    return {
      activity: {},
      form: {},
      tableData: [],  // 所有的数据
      pageSize: 10,  // 每页显示的个数,
      pageNum: 1,   // 当前的页码
      ids: [],
      total: 0,
      name: null,
      host: null,
      activityId: this.$route.query.activityId,
      hotel: {},
      content: '',
      editor: null,
      sighText: '已预订',
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      fromVisible: false,
      rules: {
        applystart: [
          { required: false, message: '请输入房间入住时间', trigger: 'blur' },
        ],
        applyend: [
          { required: false, message: '请输入房间退房时间', trigger: 'blur' },
        ],
      },
    }
  },
  components: {
    Comment
  },

  created() {
    this.load()
    this.load2(1)
    //更新阅读量
    this.$request.put('/activity/updateCount/' + this.activityId)
  },
  computed: {
    filteredTableData() {
      return this.tableData.filter(item => item.id === this.activity.id);
    }
  },
  methods: {
    handleEdit(row) {   // 编辑数据
      this.form = JSON.parse(JSON.stringify(row))  // 给form对象赋值  注意要深拷贝数据
      this.fromVisible = true   // 打开弹窗
    },
    save() {   // 保存按钮触发的逻辑  它会触发新增或者更新
      this.form.startdate = this.form.applystart;
      this.form.enddate = this.form.applyend;
      this.$refs.formRef.validate((valid) => {
        if (valid) {
          this.$request({
            url: this.form.id ? '/activity/update' : '/activity/add',
            method: this.form.id ? 'PUT' : 'POST',
            data: this.form
          }).then(res => {
            if (res.code === '200') {  // 表示成功保存
              this.$message.success('保存成功')
              this.load2(1)
              this.fromVisible = false
            } else {
              this.$message.error(res.msg)  // 弹出错误的信息
            }
          })
        }
      })
    },
    cancel() {   // 取消报名
      this.$confirm('您确定取消报名吗？', '确认取消', { type: "warning" }).then(response => {
        this.$request.delete('/activitySign/delete/user/' + this.activityId + '/' + this.user.id).then(res => {
          if (res.code === '200') {   // 表示操作成功
            this.$message.success('操作成功')
            this.load()
          } else {
            this.$message.error(res.msg)  // 弹出错误的信息
          }
        })
      }).catch(() => {
      })
    },
    handleLike() {
      this.$request.post("/likes/set", { fid: this.activityId, module: "酒店" }).then(res => {
        if (res.code === '200') {
          this.$message.success("操作成功")
          this.load()
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    handleCollect() {
      this.$request.post("/collect/set/collect", { fid: this.activityId, module: "酒店" }).then(res => {
        if (res.code === '200') {
          this.$message.success("操作成功")
          this.load()
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    sign() {
      this.$request.post("/activitySign/add", {
        activityId: this.activityId,
        start_date: this.form.applystart,
        end_date: this.form.applyend
      }).then(res => {
        if (res.code === '200') {
          this.$message.success("报名成功")
          this.load()
        }
        else {
          this.$message.error(res.msg)
        }
      })
    },
    goDetail(id) {
      window.open('/front/blogDetail?blogId=' + id)
    },

    load() {
      this.$request.get('/activity/selectById/' + this.activityId).then(res => {
        this.activity = res.data || {}
        this.startCountdown()
        if (this.activity.hotelId) {
          this.loadContest(this.activity.hotelId);
        }
      })
    },

    load2(pageNum) {  // 分页查询
      if (pageNum) this.pageNum = pageNum
      this.$request.get('/activity/selectPage', {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          name: this.name,
          host: this.host
        }
      }).then(res => {
        this.tableData = res.data?.list
        this.total = res.data?.total
      })
    },
    loadContest(hotelId) {
      this.$request.get(`/blog/select/${hotelId}`).then(res => {
        this.hotel = res.data;
      });
    },
    startCountdown() {
      this.updateCountdown()
      setInterval(this.updateCountdown, 1000);
    },
    updateCountdown() {
      if (this.activity == null) {
        return;
      }
      const now = new Date();
      const applyStartTime = new Date(this.activity.applystart);
      if (applyStartTime > now) {
        this.$set(this.activity, 'isApplyStart', false);
      } else {
        this.$set(this.activity, 'isApplyStart', true);
      }

      const applyEndTime = new Date(this.activity.applyend);
      if (applyEndTime >= now) {
        const applyRemainingTime = this.calculateRemainingTime(now, applyEndTime);
        this.$set(this.activity, 'applyRemainingDays', applyRemainingTime.days);
        this.$set(this.activity, 'applyRemainingHours', applyRemainingTime.hours);
        this.$set(this.activity, 'isApplyEnd', false);
      } else {
        this.$set(this.activity, 'applyRemainingDays', 0);
        this.$set(this.activity, 'applyRemainingHours', 0);
        this.$set(this.activity, 'isApplyEnd', true);
      }

      const startTime = new Date(this.activity.start);
      if (startTime > now) {
        const remainingTime = this.calculateRemainingTime(now, startTime);
        this.$set(this.activity, 'remainingDays', remainingTime.days);
        this.$set(this.activity, 'remainingHours', remainingTime.hours);
        this.$set(this.activity, 'isStartActivity', false);
      } else {
        this.$set(this.activity, 'remainingDays', 0);
        this.$set(this.activity, 'remainingHours', 0);
        this.$set(this.activity, 'isStartActivity', true);
      }

      const endTime = new Date(this.activity.end);
      if (endTime >= now) {
        this.$set(this.activity, 'isEndActivity', false);
      } else {
        this.$set(this.activity, 'isEndActivity', true);
      }
    },
    calculateRemainingTime(startTime, endTime) {
      const diff = endTime - startTime;
      const days = Math.floor(diff / (1000 * 60 * 60 * 24));
      const hours = Math.floor((diff % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
      return { days, hours };
    },
    handleSelectionChange(rows) {   // 当前选中的所有的行数据
      this.ids = rows.map(v => v.id)   //  [1,2]
    },

  }
}
</script>


<style scoped></style>