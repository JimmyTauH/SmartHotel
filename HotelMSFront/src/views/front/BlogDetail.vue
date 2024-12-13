<template>
  <div class="main-content">
    <div style="display: flex">
      <div class="left" style="flex: 1">

        <div class="card" style="padding: 20px 30px; margin-bottom: 10px; ">
          <!--标题-->
          <div style="font-weight: bold;font-size: 26px; margin-bottom: 20px">
            {{ blog.title }}
          </div>
          <!--标签-->
          <div style="color: #999999; font-size: 14px; margin-bottom: 5px">
            <span style="margin-right: 20px"><i class="el-icon-user" style="margin-right: 3px"></i>{{ blog.userName
              }}</span>
            <span style="margin-right: 20px"><i class="el-icon-date" style="margin-right: 3px"></i>{{ blog.date
              }}</span>
            <span style="margin-right: 20px"><i class="el-icon-eye" style="margin-right: 3px"></i>{{ blog.readCount
              }}</span>
          </div>


          <div style="display: flex; grid-gap: 20px">
            <img :src="blog.cover" style="width: 40%; border-radius: 5px; height:260px" alt="">
            <div style="flex: 1">
              <div style="margin-top:5px; margin-bottom: 10px; color: #666666">
                <i class="el-icon-location-outline" style="margin-right: 5px"></i>
                <span style="margin-right: 5px">地址:</span>
                <div v-html="blog.address"></div>
                <el-button type="primary" size="small" @click="openDialogWithAddress"
                  style="margin-top: 12px;">查看具体信息</el-button>

                <el-dialog title="地图定位" :visible.sync="dialogVisible" @close="close" width="50%">
                  <el-form ref="form" :model="form" label-width="100px">
                    <el-form-item label="归属地址：" prop="building">
                      <span style="margin-right: 5px">地址:</span>
                      <div v-html="blog.address"></div>
                      <el-autocomplete style="width: 100%" v-model="form.building" :fetch-suggestions="querySearchAsync"
                        :trigger-on-focus="false" placeholder="可搜索其他地址" clearable @select="handleSelect">
                        <template slot-scope="{ item }">
                          <i class="el-icon-search fl mgr10" />
                          <div style="overflow: hidden">
                            <div class="title">{{ item.title }}</div>
                            <span class="address ellipsis">{{ item.address }}</span>
                          </div>
                        </template>
                      </el-autocomplete>
                    </el-form-item>
                    <el-form-item label="地图定位：">
                      <div id="map-container" style="width: 100%; height: 400px"></div>
                    </el-form-item>
                  </el-form>
                </el-dialog>

                <div style="margin-top: 20px">
                  <i class="el-icon-postcard" style="margin-right: 5px"></i>
                  <span style="margin-right: 5px">提供房型:</span>
                  <el-tag v-for="item in tagsArr" :key="item.id" type="primary" style="margin-right: 5px" size="small">
                    {{ item }}
                  </el-tag>
                </div>

                <div style="margin-top: 20px">
                  <i class="el-icon-postcard" style="margin-right: 5px"></i>
                  <span style="margin-right: 5px">提供服务:</span>
                  <el-tag v-for="item in servicesArr" :key="item.id" type="primary" style="margin-right: 5px" size="small">
                    {{ item }}
                  </el-tag>
                </div>

                <div style="margin-top: 20px">
                  <i class="el-icon-edit-outline" style="margin-right: 5px"></i>
                  <span style="font-size: 14px;color: #666666">酒店简介:</span>
                  <div style="letter-spacing: 3px;font-size: 13px;margin-top: 10px" v-html="blog.content"></div>
                </div>
              </div>
            </div>
          </div>




          <div>
            <el-row :gutter="10" v-if="total" style="margin-top: 20px;">
              <div style="font-weight: bold;font-size: 20px; margin-bottom: 20px">具体房型如下：
              </div>
              <el-col :span="24" v-for="item in tableData" :key="item.id" v-if="item.hotelID === blog.id">

                <div class="card"
                  style="margin-bottom: 15px; cursor: pointer; padding: 15px; border-radius: 10px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); transition: transform 0.3s;"
                  @click="goDetail(item.id)">
                  <div style="display: flex; align-items: center;">
                    <img :src="item.cover" style="width: 200px; height: 120px; border-radius: 8px; margin-right: 15px;"
                      alt="Room Image">
                    <div style="flex-grow: 1;margin-left: 5px;">
                      <div style="font-weight: bold; font-size: 18px; color: #333;margin-top: 15px;">{{ item.name }}
                      </div>
                      <div class="el-icon-set-up" style="font-size: 14px; color: #777; margin-top: 15px;"> 简介: {{
                        item.descr }}
                      </div>
                      <div
                        style="display: flex; justify-content: space-between; align-items: center; margin-top: 10px;">
                        <div style="display: flex; align-items: center;">
                          <span style="font-weight: bold; font-size: 16px; color: #333;">¥ 房价:</span>
                          <span style="font-weight: bold; font-size: 16px; color: #FFD700; margin-left: 25px;">{{
                            item.host
                          }}</span>
                        </div>
                        <div style="font-size: 16px; color: #999;">剩余房量: {{ item.number }}</div>
                      </div>
                      <el-button style="margin-top: 10px;" @click.stop="goDetail(item.id)">查看详情</el-button>
                    </div>
                  </div>
                </div>
              </el-col>

            </el-row>
            <div class="card" v-if="total === 0" style="text-align: center; font-size: 16px; color: #666">暂无数据</div>
          </div>
          <!--关联酒店-->
          <!-- <div class="custom-card" style="margin-bottom: 10px" v-if="blog.contestId != null" @click="goDetail(contest.id)">
            
            <div style="display: flex; grid-gap: 20px">
              <img :src="blog.cover" style="width: 50%; border-radius: 5px; height: auto" alt="">
              <div style="flex: 1">
                <div style="font-weight: bold;font-size: 24px;margin-bottom: 20px;margin-top: 10px">{{ contest.name }}</div>
                <div style="color: #666666; font-size: 13px; line-height: 22px;text-align: justify;margin-bottom: 20px;margin-right: 20px">
                  {{ contest.descr }}
                </div>
                <div style="margin-bottom: 10px; color: #666666">
                  <i class="el-icon-location-outline" style="margin-right: 5px"></i>
                  <span style="margin-right: 5px">地址</span>
                  <span v-if="contest.form === '线上'">
                      <a style="color: #409EFF" :href="contest.address" target="_blank">{{ contest.address }}</a>
                      <el-tag size="mini" style="margin-left: 10px">{{ contest.form }}</el-tag>
                    </span>
                  <span v-if="contest.form === '线下'">
                      {{ contest.address }}
                      <el-tag size="mini" style="margin-left: 10px">{{ contest.form }}</el-tag>
                    </span>
                </div>
              </div>
            </div>
          </div> -->
          <!--内容-->


        </div>



        <!--点赞、收藏-->
        <div class="card" style="text-align: center; font-size: 20px; color: #666; margin-bottom: 10px">
          <span style="margin-right: 20px; cursor: pointer" @click="setLikes" :class="{ 'active': blog.userLike }"><i
              class="el-icon-like"></i> {{ blog.likesCount }}</span>
          <span style=" cursor: pointer" @click="setCollect" :class="{ 'active': blog.userCollect }"><i
              class="el-icon-star-off"></i>{{ blog.collectCount }}</span>
        </div>
        <!--评论-->
        <Comment :fid="blogId" :title="blog.title" :module="module" :replyId="blog.user.id"></Comment>
      </div>


      <div class="right" style="width: 260px;margin-left: 10px">
        <div class="card" style="margin-bottom: 10px">
          <div style="display: flex;grid-gap: 10px;align-items: center;margin-bottom: 10px">
            <img :src="blog.user?.avatar" alt="" @click="homePage(blog.user.id)"
              style="width: 50px; height: 50px; border-radius: 50%">
            <div style="flex: 1">
              <div style="font-weight: bold;margin-bottom: 5px">{{ blog.user?.name }}</div>
              <div class="line2" style="color: #666666;font-size: 12px">{{ blog.user?.info }}</div>
            </div>
          </div>
          <div style="display: flex">
            <div style="flex: 1; text-align: center">
              <div style="margin-bottom: 5px">酒店</div>
              <div style="color: #888">{{ blog.userBlogCount }}</div>
            </div>
            <div style="flex: 1; text-align: center">
              <div style="margin-bottom: 5px">点赞</div>
              <div style="color: #888">{{ blog.userLikeCount }}</div>
            </div>
            <div style="flex: 1; text-align: center">
              <div style="margin-bottom: 5px">收藏</div>
              <div style="color: #888">{{ blog.userCollectCount }}</div>
            </div>
          </div>
        </div>

        <div class="card" style="margin-bottom: 10px">
          <div
            style="font-weight: bold; font-size: 20px; padding-bottom: 10px; border-bottom: 1px solid #ddd; margin-bottom: 10px">
            相关推荐
          </div>

          <div>
            <div style="margin-bottom: 15px" v-for="item in recommendList" :key="item.id">
              <a :href="'/front/blogDetail?blogId=' + item.id" target="_blank">
                <div class="line2 reCommendTitle">{{ item.title }}</div>
              </a>
              <div style="color: #888">
                <span>阅读</span> <span>{{ item.readCount }}</span>
                <span style="margin-left: 10px">点赞</span> <span>{{ item.likesCount }}</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>

</template>

<script>
import Comment from "@/components/Comment";
import loadBMap from '@/utils/loadBMap.js'
export default {
  name: "BlogDetail",
  name: 'Baidu',
  components: { Comment },
  data() {
    return {
      tableData: [],  // 所有的数据
      pageNum: 1,   // 当前的页码
      pageSize: 10,  // 每页显示的个数
      total: 0,
      name: null,
      blogId: this.$route.query.blogId,
      blog: {},
      contest: {},
      tagsArr: [],
      servicesArr: [],
      recommendList: [],
      module: '帖子',
      map: null,
      // Marker实例
      mk: null,
      // 定时器
      initMapeq: null,
      // 表单开关
      dialogVisible: false,
      // 表单
      form: {
        building: ''
      },
    }
  },
  props: {
    type: null,
    span: null
  },
  created() {
    this.load()
    this.load22(1)
    //更新阅读量
    this.$request.put('/blog/updateCount/' + this.blogId)
  },
  mounted() {
    // 页面加载完之后，加载百度地图
    // 加载引入BMap
    loadBMap('HzftzY4qStxQqowpOchL7waG9ls01y3Z')
  },
  methods: {
    load22(pageNum) {  // 分页查询
      let url
      switch (this.type) {
        case 'user':
          url = '/activity/selectUser';
          break;
        case 'like':
          url = '/activity/selectLike';
          break;
        case 'collect':
          url = '/activity/selectCollect';
          break;
        case 'comment':
          url = '/activity/selectComment';
          break;
        default:
          url = '/activity/selectPage';
      }
      if (pageNum) this.pageNum = pageNum
      this.$request.get(url, {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          name: this.name,
          host: this.host
        }
      }).then(res => {
        this.tableData = res.data?.list
        this.total = res.data?.total
        this.startCountdown()
      })
    },
    reset() {
      this.name = null
      this.host = null
      this.load(1)
    },
    handleCurrentChange(pageNum) {
      this.load(pageNum)
    },
    startCountdown() {
      this.updateCountdown()
      setInterval(this.updateCountdown, 1000);
    },
    updateCountdown() {
      if (this.tableData == null || this.tableData.length === 0) {
        return;
      }
      const now = new Date();
      this.tableData.forEach(item => {
        const applyStartTime = new Date(item.applystart);
        if (applyStartTime > now) {
          this.$set(item, 'isApplyStart', false);
        } else {
          this.$set(item, 'isApplyStart', true);
        }

        const applyEndTime = new Date(item.applyend);
        if (applyEndTime >= now) {
          const applyRemainingTime = this.calculateRemainingTime(now, applyEndTime);
          this.$set(item, 'applyRemainingDays', applyRemainingTime.days);
          this.$set(item, 'applyRemainingHours', applyRemainingTime.hours);
          this.$set(item, 'isApplyEnd', false);
        } else {
          this.$set(item, 'applyRemainingDays', 0);
          this.$set(item, 'applyRemainingHours', 0);
          this.$set(item, 'isApplyEnd', true);
        }

        const startTime = new Date(item.start);
        if (startTime > now) {
          const remainingTime = this.calculateRemainingTime(now, startTime);
          this.$set(item, 'remainingDays', remainingTime.days);
          this.$set(item, 'remainingHours', remainingTime.hours);
          this.$set(item, 'isStartActivity', false);
        } else {
          this.$set(item, 'remainingDays', 0);
          this.$set(item, 'remainingHours', 0);
          this.$set(item, 'isStartActivity', true);
        }

        const endTime = new Date(item.end);
        if (endTime >= now) {
          this.$set(item, 'isEndActivity', false);
        } else {
          this.$set(item, 'isEndActivity', true);
        }
      });
    },
    calculateRemainingTime(startTime, endTime) {
      const diff = endTime - startTime;
      const days = Math.floor(diff / (1000 * 60 * 60 * 24));
      const hours = Math.floor((diff % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
      return { days, hours };
    },
    openDialogWithAddress() {
      this.dialogVisible = true;
      this.getbaidu()
    },
    close() {
      this.form = {}
    },
    // 加载地图方法
    // getbaidu() {
    //   this.showDialog = true
    //   this.initMapeq = setInterval(() => {
    //     if (this.initMap()) {
    //       clearInterval(this.initMapeq)
    //     }
    //   })
    // },
    getbaidu() {
      const that = this;
      const geocoder = new BMap.Geocoder();

      // 使用地理编码将地址转换为坐标
      geocoder.getPoint(this.blog.address, function (point) {
        if (point) {
          // 将转换得到的坐标赋值给表单
          that.form.longitude = point.lng;
          that.form.latitude = point.lat;

          // 初始化地图并将中心设置为该坐标
          that.initMap(point);
        } else {
          console.error("地址解析失败，请检查地址是否正确");
        }
      });
    },

    // 百度地图封装方法
    // 初始化地图
    initMap(point) {
      // this.form.building = this.blog.address;       // 将 blog.address 赋值给 form.building
      try {
        // 如果未传入point，使用默认坐标
        if (!point) {
          point = new BMap.Point(116.404, 39.915); // 默认坐标（北京）
        }

        // 创建地图实例
        this.map = new BMap.Map('map-container');
        this.map.centerAndZoom(point, 14); // 设置地图中心和缩放级别
        this.map.enableScrollWheelZoom();

        // 添加图像标注
        this.mk = new BMap.Marker(point, { enableDragging: true });
        this.map.addOverlay(this.mk);
      } catch (err) {
        console.error("地图加载失败", err);
        return false;
      }

      // 添加右上角的平移缩放控件
      this.map.addControl(
        new BMap.NavigationControl({
          anchor: BMAP_ANCHOR_TOP_RIGHT,
          type: BMAP_NAVIGATION_CONTROL_SMALL,
        })
      );

      // 点击地图时重新获取地址
      this.map.addEventListener("click", (e) => {
        this.getAddrByPoint(e.point);
      });

      return true;
    },

    // 逆地址解析：通过坐标点获取详细地址
    getAddrByPoint(point) {
      const geocoder = new BMap.Geocoder();
      geocoder.getLocation(point, (res) => {
        this.mk.setPosition(point);
        this.map.panTo(point);
        this.form.building = res.address;
        this.form.longitude = res.point.lng;
        this.form.latitude = res.point.lat;
      });
    },
    // 8-1、地址搜索
    querySearchAsync(str, cb) {
      // 根据状态码
      var options = {
        onSearchComplete: function (res) {
          var s = []
          if (local.getStatus() == BMAP_STATUS_SUCCESS) {
            for (var i = 0; i < res.getCurrentNumPois(); i++) {
              s.push(res.getPoi(i))
            }
            cb(s)
          } else {
            cb(s)
          }
        }
      }
      var local = new BMap.LocalSearch(this.map, options)
      local.search(str)
    },
    // 8-2、选择地址
    handleSelect(item) {
      this.form.building = item.address + item.title
      console.log(item);
      this.form.longitude = item.point.lng
      this.form.latitude = item.point.lat
      console.log('lng', item.point.lng)
      console.log('lat', item.point.lat)
      this.map.clearOverlays()
      this.mk = new BMap.Marker(item.point)
      this.map.addOverlay(this.mk)
      this.map.panTo(item.point)
    },




    goDetail(id) {
      window.open('/front/ActivityDetail?activityId=' + id)
    },
    homePage(userId) {
      this.$router.push('/front/HomePage?userId=' + userId)
    },
    load() {
      this.$request.get("/blog/select/" + this.blogId).then(res => {
        this.blog = res.data || {}
        this.tagsArr = JSON.parse(this.blog.tags || '[]')
        this.servicesArr = JSON.parse(this.blog.servicesProvided || '[]')
        if (this.blog.contestId) {
          this.loadContest(this.blog.contestId);
        }
      })

      this.$request.get("/blog/selectRecommend/" + this.blogId).then(res => {
        this.recommendList = res.data || []
      })
    },
    loadContest(contestId) {
      this.$request.get(`/activity/selectById/${contestId}`).then(res => {
        this.contest = res.data;
      });
    },
    setLikes() {
      this.$request.post("/likes/set", { fid: this.blogId, module: '帖子' }).then(res => {
        if (res.code === '200') {
          this.$message.success("操作成功")
        }
        this.load() // 刷新页面
      })
    },
    setCollect() {
      this.$request.post("/collect/set/collect", { fid: this.blogId, module: '帖子' }).then(res => {
        if (res.code === '200') {
          this.$message.success("操作成功")
        }
        this.load()// 刷新页面
      })
    }
  }
}

</script>


<style>
/* blockquote 样式 */
blockquote {
  display: block;
  border-left: 8px solid #d0e5f2;
  padding: 20px 10px;
  margin: 10px 0;
  line-height: 1.4;
  font-size: 100%;
  background-color: #d9d9d9;
}

/* code 样式 */
code {
  display: inline-block;
  background-color: #909399;
  border-radius: 3px;
  padding: 3px 5px;
  margin: 3px 3px;
}

pre code {
  display: block;
}

p {
  line-height: 15px
}

.line2 {
  word-break: break-all;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 2;
  /* 超出几行省略 */
  overflow: hidden;
}

.active {
  color: orange !important;
}

.reCommendTitle {
  margin-bottom: 5px
}

.reCommendTitle:hover {
  color: #409EFF;
}

.custom-card {
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
  /* Adds a subtle shadow */
  transition: 0.3s;
  /* Smooth transition for hover effects */
  border-radius: 10px;
  /* Rounded corners for a modern look */
  cursor: pointer;
}

.custom-card:hover {
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.4);
  /* Enhanced shadow on hover */
}

.countdown {
  display: flex;
  gap: 10px;
}

.countdown-block1 {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 120px;
  height: 25px;
  border: 2px solid #7acaddcc;
  border-radius: 5px;
  background-color: #f5f5f5;
}

.countdown-value1 {
  font-size: 15px;
  font-weight: bold;
}
</style>