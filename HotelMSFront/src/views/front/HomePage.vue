<template>
  <div>
    <div style="height: 330px;background-color: #2CBE56;">
      <el-image :src="pageMsg.cover" fit="cover" style="width: 100%;height: 100%"></el-image>
    </div>
    <div class="main-content" style="margin-left: 250px;">
      <div class="card" style="padding: 10px; display: flex; justify-content: space-between;">
        <!-- 头像部分 -->
        <div style="margin-right: 15px;">
          <img v-if="user.avatar" :src="user.avatar" class="avatar"/>
        </div>
        <!-- 用户名和信息部分 -->
        <div style="display: flex; flex-direction: column; padding-top: 20px">
          <span style="font-weight: bold;font-size: 36px">{{ user.name }}</span>
          <div style="font-size: 26px;">
            {{ user.info }}
          </div>
        </div>
        <div style="margin-left: auto;display: flex; align-items: center;">
          <div style="margin-left: 10px" v-if="isShowConcern">
            <el-button round type="success" v-if="isConcern" @click="concern" size="medium">关注</el-button>
            <el-button round type="warning" v-else @click="cancelConcern" size="medium">取消关注</el-button>
          </div>
          <div style="margin-left: 10px">
            <el-statistic
                group-separator=","
                :value="concernCount"
                title="关注人数"
            >
            </el-statistic>
          </div>
        </div>
      </div>
      <div class="card" style="margin-top: 10px">
        <el-tabs v-model="activeName">
<!--          <el-tab-pane label="主页" name="主页">-->
<!--            <div style="display: flex">-->
              <!--会员-->
<!--              <div style="width: 30%">-->
<!--                <span style="font-size: 36px;font-weight: bold">{{ memberCount }}</span>-->
<!--                <div style="color: #999999">会员人数</div>-->
<!--                <el-divider></el-divider>-->
<!--                <div style="font-size: 20px; padding: 16px">-->
<!--                  <div v-html="pageMsg.synopsis"></div>-->
<!--                </div>-->
<!--              </div>-->
<!--              <div>-->
<!--                <div style="margin-bottom: 5px">-->
<!--                  <el-button type="text" @click="handleAdd()" v-if="!isShowConcern">新增会员档位</el-button>-->
<!--                </div>-->
<!--                <div v-for="item in memberList" :key="item.id">-->
<!--                  <el-card shadow="always" style="width: 750px;border: 2px solid #8AC007;margin-bottom: 20px">-->
<!--                    <div v-if="!isShowConcern">-->
<!--                      <el-button type="text" @click="handleEdit(item.id)" v-if="!isShowConcern">编辑</el-button>-->
<!--                      <el-button type="text" @click="deleteMember(item.id)" v-if="!isShowConcern">删除</el-button>-->
<!--                    </div>-->
<!--                    <div style="font-weight: bold;font-size: 30px">{{ item.title }}</div>-->
<!--                    <div style="font-size: 60px;font-weight: bold;margin-bottom: 10px">{{ item.integral }}积分/月</div>-->
<!--                    <div style="margin-bottom: 10px;font-size: 16px">-->
<!--                      <div v-html="item.info"></div>-->
<!--                    </div>-->
<!--                    <div style="margin-bottom: 10px">-->
<!--                    <span style="font-size: 26px;font-weight: bold">-->
<!--                      会员福利：-->
<!--                    <br>-->
<!--                    </span>-->
<!--                      <div>-->
<!--                        <el-button type="text" @click="editWelfare(item.id)" v-if="!isShowConcern">设置福利</el-button>-->
<!--                      </div>-->
<!--                      <div style="color: #999999">-->
<!--                        {{ item.welfareTitle }}-->
<!--                      </div>-->
<!--                    </div>-->
<!--                    <div>-->
<!--                      <el-button round type="success" size="medium" @click="opened(item.id)" v-if="item.isOpened">-->
<!--                        立即开通-->
<!--                      </el-button>-->
<!--                      <el-button round type="primary" size="medium" v-if="!item.isOpened">-->
<!--                        已开通-->
<!--                      </el-button>-->
<!--                    </div>-->
<!--                  </el-card>-->
<!--                </div>-->
<!--              </div>-->
<!--            </div>-->
<!--          </el-tab-pane>-->

          <el-tab-pane label="帖子" name="帖子">
            <BlogList type="certification"/>
          </el-tab-pane>
        </el-tabs>
      </div>
    </div>
    <el-dialog
        title="新增会员档位"
        :visible.sync="dialogVisible"
        width="60%"
        :before-close="handleClose">
      <div style="font-weight: bold;font-size: 24px;margin-bottom: 20px; text-align: center">编辑</div>
      <el-form :model="form" label-width="100px" style="padding-right: 50px" :rules="rules" ref="formRef">
        <el-form-item label="标题" prop="title">
          <el-input v-model="form.title" placeholder="标题"></el-input>
        </el-form-item>
        <el-form-item label="福利标题" prop="welfareTitle">
          <el-input v-model="form.welfareTitle" placeholder="福利标题"></el-input>
        </el-form-item>
        <el-form-item label="简介" prop="info">
          <div id="editor"></div>
        </el-form-item>
        <el-form-item label="需求积分" prop="integral">
          <el-input v-model="form.integral" placeholder="需求积分" type="number"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer" style="text-align: center">
        <el-button type="primary" @click="save" style="width: 200px">保 存</el-button>
      </div>
    </el-dialog>

  </div>
</template>
<script>
import E from "wangeditor"
import hljs from 'highlight.js'
import Vue from "vue";
import BlogList from "@/components/BlogList.vue";

export default {
  name: "HomePage",
  components: {
    BlogList,
  },
  data() {
    return {
      userId: this.$route.query.userId,
      dbUser: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      user: {},
      activeName: '帖子',
      concernCount: 0,
      isShowConcern: true,
      isConcern: false,
      pageMsg: '',
      dialogVisible: false,
      memberList: [],
      form: {},
      editor: null,
      rules: {
        title: [
          {required: true, message: '请输入标题', trigger: 'blur'},
        ],
        welfareTitle: [
          {required: true, message: '请输入福利标题', trigger: 'blur'},
        ],
        integral: [
          {required: true, message: '请输入需求积分', trigger: 'blur'},
        ],
      },
      welfareTitle: '',
      memberCount: 0,
    }
  },
  mounted() {
    this.load()
    this.selectConcernCount()
    this.selectIsConcern()

  },
  methods: {
    isOpened(memberId) {
      return this.$request.get("/myWelfare/selectByMidAndDbUid/" + memberId + "/" + this.userId + "/" + this.dbUser.id).then(res => {
        if (res.code == '200') {
          return res.data === null ? 1 : 0;
        } else {
          this.$message.error(res.msg)
          return null;
        }
      })
    },
    opened(memberId) {
      this.$confirm('是否确定开通此会员?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$request.post("/myWelfare/add", {
          memberId: memberId,
          dbUserId: this.userId
        }).then(res => {
          if (res.code == '200') {
            this.$message.success('开通成功')
            this.load()
          } else {
            this.$message.error(res.msg)
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消'
        });
      });
    },
    save() {   // 保存按钮触发的逻辑  它会触发新增或者更新
      this.$refs.formRef.validate((valid) => {
        if (valid) {
          this.form.info = this.editor.txt.html()
          this.form.userId = this.userId
          this.$request({
            url: this.form.id ? '/member/update' : '/member/add',
            method: this.form.id ? 'PUT' : 'POST',
            data: this.form
          }).then(res => {
            if (res.code === '200') {  // 表示成功保存
              this.$message.success('保存成功')
              this.dialogVisible = false
              //刷新
              this.load()
            } else {
              this.$message.error(res.msg)  // 弹出错误的信息
            }
          })
        }
      })
    },
    handleAdd() {   // 新增数据
      this.form = {}  // 新增数据的时候清空数据
      this.setRichText()  // 初始化富文本编辑器
      this.fromVisible = true   // 打开弹窗
      this.dialogVisible = true
    },
    handleEdit(id) {   // 编辑数据
      this.$request.get('/member/selectByMemberId/' + id).then(res => {
        this.form = res.data || {}
        this.fromVisible = true   // 打开弹窗
        this.dialogVisible = true
        this.setRichText()  // 初始化富文本编辑器
        //异步操作，等富文本页面渲染完再赋值
        setTimeout(() => {
          this.editor.txt.html(this.form.info)
        }, 0)
      })
    },
    deleteMember(id) {
      this.$confirm('您确定删除吗？', '确认删除', {type: "warning"}).then(response => {
        this.$request.delete('/member/delete/' + id).then(res => {
          if (res.code === '200') {   // 表示操作成功
            this.$message.success('删除成功')
            this.load()
          } else {
            this.$message.error(res.msg)  // 弹出错误的信息
          }
        })
      }).catch(() => {
      })
    },
    setRichText() {
      this.$nextTick(() => {
        this.editor = new E(`#editor`)  // 实例化\初始化
        this.editor.highlight = hljs    // 配置高亮
        this.editor.config.uploadImgServer = this.$baseUrl + '/files/editor/upload' // 上传图片的接口
        this.editor.config.uploadFileName = 'file'  // 上传图片的参数名
        this.editor.config.zIndex = 0
        this.editor.config.uploadImgHeaders = {
          token: this.user.token  // token
        }
        this.editor.config.uploadImgParams = {
          type: 'img',
        }
        this.editor.create()  // 创建
      })
    },
    load() {
      //获取主页用户信息
      this.$request.get("/user/select/" + this.userId).then(res => {
        this.user = res.data || {}
        if (this.userId - this.dbUser.id === 0) {
          this.isShowConcern = false
        }
      })
      //获取主页其他信息
      this.$request.get("homePage/select/" + this.userId).then(res => {
        this.pageMsg = res.data || ''
      })
      //获取会员档位信息
      this.$request.get("/member/selectById/" + this.userId).then(res => {
        this.memberList = res.data || [];
        if (this.memberList.length !== 0) {
          const promises = this.memberList.map(member => this.isOpened(member.id));
          Promise.all(promises).then(results => {
            results.forEach((res, index) => {
              Vue.set(this.memberList[index], 'isOpened', res === 1 ? true : false);
            });
          });
        }
      });
      //获取会员人数
      this.$request.get("/myWelfare/selectMemberCount/" + this.userId).then(res => {
        this.memberCount = res.data || 0
      });
    },
    handleClose(done) {
      this.$confirm('确认关闭？')
          .then(_ => {
            done();
          })
          .catch(_ => {
          });
    },
    // 查询关注人数
    selectConcernCount() {
      this.$request.get("concern/selectCount/" + this.userId).then(res => {
        this.concernCount = res.data || 0
      })
    },
    // 查询是否关注
    selectIsConcern() {
      this.$request.get("concern/selectIsConcern/" + this.dbUser.id + "/" + this.userId).then(res => {
        if (res.data !== 0) {
          this.isConcern = false
        } else {
          this.isConcern = true
        }
      })
    },
    // 取消关注
    cancelConcern() {
      this.$request.delete("/concern/delete/" + this.dbUser.id + "/" + this.userId).then(res => {
            if (res.code === '200') {
              this.$message.success("取消关注成功")
              this.selectConcernCount()
              this.selectIsConcern()
            } else {
              this.$message.error(res.msg)
            }
          }
      )
    },
    // 关注
    concern() {
      this.$request.post("/concern/add", {
        userId: this.dbUser.id,
        creatorId: this.userId
      }).then(res => {
        if (res.code === '200') {
          this.$message.success("关注成功")
          this.selectConcernCount()
          this.selectIsConcern()
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    editWelfare(memberId) {
      this.$router.push('/front/ExitMemberWelfare?userId=' + this.userId + '&memberId=' + memberId)
    }
  }
}
</script>


<style scoped>
.main-content {
  width: 70% !important;
}

.avatar {
  width: 120px;
  height: 120px;
  display: block;
  border-radius: 50%;
}

/* blockquote 样式 */
blockquote {
  display: block;
  border-left: 8px solid #d0e5f2;
  padding: 20px 10px;
  margin: 10px 0;
  line-height: 1.4;
  font-size: 100%;
  background-color: #f1f1f1;
}

/* code 样式 */
code {
  display: inline-block;
  background-color: #f1f1f1;
  border-radius: 3px;
  padding: 3px 5px;
  margin: 0 3px;
}

pre code {
  display: block;
}

p {
  line-height: 30px
}

.el-statistic .head {
  margin-bottom: 4px;
  font-size: 20px;
  font-weight: bold;
}

</style>