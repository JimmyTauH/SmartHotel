<template>
  <div class="main-content">
    <div class="card" style="margin-bottom: 10px">
      <div style="display: flex; grid-gap: 20px">
        <img :src="activity.cover" style="width: 30%; border-radius: 5px; height: 200px" alt="">
        <div style="flex: 1">
          <div style="font-weight: bold;font-size: 24px;margin-bottom: 20px">{{ activity.name }}</div>
          <div style="color: #666666; font-size: 13px; line-height: 22px;text-align: justify;margin-bottom: 20px">
            {{ activity.descr }}
          </div>
          <div style="color: #666666;margin-bottom: 10px">
            <i class="el-icon-date" style="margin-right: 5px"></i>
            <span style="margin-right: 5px">时间</span>
            <span>{{ activity.start }} - {{ activity.end }}</span>
          </div>
          <div style="margin-bottom: 10px; color: #666666">
            <i class="el-icon-location-outline" style="margin-right: 5px"></i>
            <span style="margin-right: 5px">地址</span>
            <span v-if="activity.form === '线上'">
              <a style="color: #409EFF" :href="activity.address" target="_blank">{{ activity.address }}</a>
              <el-tag size="mini" style="margin-left: 10px">{{ activity.form }}</el-tag>
            </span>
            <span v-if="activity.form === '线下'">
              {{ activity.address }}
              <el-tag size="mini" style="margin-left: 10px">{{ activity.form }}</el-tag>
            </span>
          </div>
          <div style="display: flex">
            <el-button type="primary" disabled v-if="activity.isApplyEnd" key="报名已结束">报名已结束</el-button>
            <el-button type="primary" disabled v-else-if="!activity.isApplyStart" key="报名未开始">报名未开始</el-button>
            <el-button type="success" v-else-if="activity.isSign" :key="sighText" @click="cancel"
                       @mouseenter.native="sighText='取消报名'" @mouseleave.native="sighText='已报名'">
              {{ sighText }}
            </el-button>
            <el-button type="primary" v-else key="报 名" @click="sign">报 名</el-button>
            <div style="flex: 1;text-align: right">
              <el-button type="danger" @click="handleLike" v-if="activity.isLike">已点赞 {{
                  activity.likeCount
                }}
              </el-button>
              <el-button type="danger" @click="handleLike" v-else>点赞 {{ activity.likeCount }}</el-button>
              <el-button type="warning" @click="handleCollect" v-if="activity.isCollect">已收藏 {{
                  activity.collectCount
                }}
              </el-button>
              <el-button type="warning" @click="handleCollect" v-else>收藏 {{ activity.collectCount }}</el-button>
              <el-button type="primary">阅读量 {{ activity.readCount }}</el-button>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!--酒店详情-->
    <div class="card" style="margin-bottom: 10px">
      <div
          style="font-weight: bold; font-size: 24px;margin-bottom: 20px;padding-left: 10px;border-left: 5px solid #409EFF">
        酒店详情
      </div>
      <div class="w-e-text">
        <div v-html="activity.content"></div>
      </div>
    </div>
    <!--评论-->
    <div>
      <Comment :fid="activityId" module="酒店"/>
    </div>
  </div>
</template>
<script>
import Comment from "@/components/Comment.vue";

export default {
  name: "ActivityDetail",
  data() {
    return {
      activity: {},
      activityId: this.$route.query.activityId,
      sighText: '已报名',
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
    }
  },
  components: {
    Comment
  },
  created() {
    this.load()
    //更新阅读量
    this.$request.put('/activity/updateCount/' + this.activityId)
  },

  methods: {
    cancel() {   // 取消报名
      this.$confirm('您确定取消报名吗？', '确认取消', {type: "warning"}).then(response => {
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
      this.$request.post("/likes/set", {fid: this.activityId, module: "酒店"}).then(res => {
        if (res.code === '200') {
          this.$message.success("操作成功")
          this.load()
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    handleCollect() {
      this.$request.post("/collect/set/collect", {fid: this.activityId, module: "酒店"}).then(res => {
        if (res.code === '200') {
          this.$message.success("操作成功")
          this.load()
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    sign() {
      this.$request.post("/activitySign/add", {activityId: this.activityId}).then(res => {
        if (res.code === '200') {
          this.$message.success("报名成功")
          this.load()
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    load() {
      this.$request.get('/activity/selectById/' + this.activityId).then(res => {
        this.activity = res.data || {}
        this.startCountdown()
      })
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
    }
  }
}
</script>


<style scoped>

</style>