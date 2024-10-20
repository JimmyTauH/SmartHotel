<template>
  <div class="card">
    <h2 style="margin-bottom: 20px">评论 {{ commentCount }}</h2>
    <div>
      <el-input type="textarea" placeholder="请输入评论内容" v-model="commentContent"></el-input>
      <div style="text-align: right;margin-top: 5px">
        <el-button type="primary" @click="addComment">评 论</el-button>
      </div>
    </div>

    <div>
      <div style="display: flex;grid-gap: 20px; margin-bottom: 10px" v-for="item in commentList" :key="item.id">
        <img :src="item.avatar" alt="" style="width: 50px; height: 50px; border-radius: 50%">
        <div style="flex: 1">
          <!--一级评论-->
          <div style="margin-bottom: 10px">
            <div style="color: #666666;margin-bottom: 15px;">{{ item.userName }}</div>
            <div style="color: #555555;margin-bottom: 10px;font-size: 16px">{{ item.content }}</div>
            <div style="margin-bottom: 10px;">
              <span style="font-size: 12px;color: #999999;margin-right: 10px">{{ item.time }}</span>
              <span :class="{'comment-activity' : item.showReplyInput}" style="color: #999999;cursor: pointer"
                    @click="handleShowReplyInput(item)">
                    <i class="el-icon-s-comment"/>
                    评论
                  </span>
              <span style="color: #999999;cursor: pointer;margin-left: 20px" @click="del(item.id)"
                    v-if="item.userId === user.id">
                <i class="el-icon-delete"></i>删除</span>
            </div>
            <!--评论回复框-->
            <div v-if="item.showReplyInput">
              <el-input type="textarea" placeholder="请输入回复内容" v-model="item.replyContent"></el-input>
              <div style="text-align: right;margin-top: 5px">
                <el-button type="primary" @click="addReplyComment(item)">回 复</el-button>
              </div>
            </div>
          </div>
          <!--回复-->
          <div style="display: flex;grid-gap: 20px; margin-bottom: 10px" v-for="sub in item.childrenCommentList"
               :key="sub.id">
            <img :src="sub.avatar" alt="" style="width: 50px; height: 50px; border-radius: 50%">
            <div style="flex: 1">
              <div style="color: #666666;margin-bottom: 15px;">{{ sub.userName }}
                <span v-if="sub.userName !== sub.replyName">回复 {{ sub.replyName }}</span>
              </div>
              <div style="color: #555555;margin-bottom: 10px;font-size: 16px">{{ sub.content }}</div>
              <div style="margin-bottom: 10px">
                <span style="font-size: 12px;color: #999999;margin-right: 10px">{{ sub.time }}</span>
                <span style="color: #999999;cursor: pointer" :class="{'comment-activity' : sub.showReplyInput}"
                      @click="handleShowReplyInput(sub)">
                        <i class="el-icon-s-comment"/>
                        回复
                      </span>
                <span style="color: #999999;cursor: pointer;margin-left: 20px" @click="del(sub.id)"
                      v-if="sub.userId === user.id">
                <i class="el-icon-delete"></i>删除</span>
              </div>
              <!--评论回复框-->
              <div v-if="sub.showReplyInput">
                <el-input type="textarea" placeholder="请输入回复内容" v-model="sub.replyContent"></el-input>
                <div style="text-align: right;margin-top: 5px">
                  <el-button type="primary" @click="addReplyComment(sub)">回 复</el-button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  name: "CommentComponent",
  props: {
    fid: null,
    title: null,
    module: null,
    replyId: null
  },
  data() {
    return {
      commentCount: 0,
      commentContent: '',
      commentList: [],
      childrenCommentList: [],
      user: JSON.parse(localStorage.getItem('xm-user') || {})
    }
  },
  created() {
    this.loadComment()
  },
  methods: {
    del(id) {   // 单个删除
      this.$confirm('您确定删除吗？', '确认删除', {type: "warning"}).then(response => {
        this.$request.delete('/comment/delete/' + id).then(res => {
          if (res.code === '200') {   // 表示操作成功
            this.$message.success('操作成功')
            this.loadComment()
          } else {
            this.$message.error(res.msg)  // 弹出错误的信息
          }
        })
      }).catch(() => {
      })
    },
    handleShowReplyInput(item) {
      this.$set(item, 'showReplyInput', !item.showReplyInput)
    },
    addReplyComment(item) {
      if (item.replyContent === undefined || item.replyContent === '' || item.replyContent === null) {
        this.$message.error("请输入回复内容")
        return
      }
      this.$request.post('/comment/add', {
        pid: item.id,
        fid: this.fid,
        module: this.module,
        replyId: this.replyId,
        rootId: item.rootId,
        content: item.replyContent
      }).then(res => {
        if (res.code === '200') {
          this.$message.success("操作成功")
        }
        if (this.replyId != null) {
          this.$request({ // 发送提醒查阅消息
            url: '/msg/add',
            method: 'POST',
            data: {
              userId: this.replyId,
              title: "新的回复",
              content: this.genReplyCommentMsg(this.fid, this.title, item.replyContent)
            }
          }).then(res => {
            if (res.code === '200') {  // 表示成功保存
              // this.$message.success('提醒查阅信息发送成功')
              this.fromVisible = false
            } else {
              this.$message.error(res.msg)  // 弹出错误的信息
            }
          })
        }
        item.replyContent = ''
        this.loadComment() // 刷新页面
      })
    },
    loadComment() {
      this.$request.get('comment/selectForUser', {
        params: {
          fid: this.fid,
          module: this.module
        }
      }).then(res => {
        this.commentList = res.data || []
      })
      this.$request.get('comment/selectCommentCount', {
        params: {
          fid: this.fid,
          module: this.module
        }
      }).then(res => {
        this.commentCount = res.data || 0
      })
    },
    addComment() {
      if (this.commentContent === undefined || this.commentContent === '' || this.commentContent === null) {
        this.$message.error("请输入评论内容")
        return
      }
      this.$request.post("/comment/add", {
        fid: this.fid,
        replyId: this.replyId,
        content: this.commentContent,
        module: this.module
      }).then(res => {
        if (res.code === '200') {
          this.$message.success("操作成功")
        }
        if (this.replyId != null) {
          this.$request({ // 发送提醒查阅消息
            url: '/msg/add',
            method: 'POST',
            data: {
              userId: this.replyId,
              title: "新的回复",
              content: this.genCommentMsg(this.fid, this.title, this.commentContent)
            }
          }).then(res => {
            if (res.code === '200') {  // 表示成功保存
              // this.$message.success('提醒查阅信息发送成功')
              this.fromVisible = false
            } else {
              this.$message.error(res.msg)  // 弹出错误的信息
            }
          })
        }
        this.commentContent = ''
        this.loadComment() // 刷新页面
      })
    },
    genReplyCommentMsg(fid, title, content) {
      const href = '/front/blogDetail?blogId=' + fid
      return `您在帖子<a href="${href}" target="_blank" style="color: blue;">《${title}》</a>下的评论有了新的回复：${content}`
    },
    genCommentMsg(fid, title, content) {
      const href = '/front/blogDetail?blogId=' + fid
      return `您的帖子<a href="${href}" target="_blank" style="color: blue;">《${title}》</a>有了新的回复：${content}`
    }
  }
}
</script>


<style scoped>
.comment-activity {
  color: #409EFF;
}
</style>