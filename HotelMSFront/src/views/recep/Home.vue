<template>

  <div>
    <div class="card" style="padding: 15px;margin-bottom: 10px;">
      您好，{{ user?.name }}！欢迎使用本系统
    </div>
    <el-row>

      <el-col :span="9">
        <!-- 头像及登录信息 -->
        <el-card class="box-card" style="height: 320px;">

          <div class="user">
            <img :src="user.avatar || 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png'" />
            <div class="userinfo">
              <p class="name" style="margin-left: -20px;margin-top: -6px;">HotelM前台</p>
              <p class="access">recep</p>
            </div>
          </div>

          <div class="login-info" style="margin-top: -10px;">
            <p>上次登录的时间:<span>2023-5-20</span></p>
            <p>上次登录的地点:<span>湖北武汉</span></p>
          </div>

        </el-card>
      </el-col>

      <el-col :span="15">
        <el-card class="box-card system_bulletin"
          style="margin-bottom: 5px;height:320px;margin-left: 15px;margin-bottom: 20px;">
          <div slot="header" class="system_title">
            <span>公告</span>
          </div>
          <div>
            <el-timeline reverse slot="reference">
              <el-timeline-item v-for="item in notices" :key="item.id" :timestamp="item.time">
                <el-popover placement="right" width="200" trigger="hover" :content="item.content">
                  <span slot="reference">{{ item.content }}</span>
                </el-popover>
              </el-timeline-item>
            </el-timeline>
          </div>
        </el-card>
      </el-col>

      <el-col :span="9">
        <!-- 日程信息 -->
        <el-card class="box-card2" style="margin-top: -10px;height: 365px;">

          <el-table :data="tableData" stripe style="margin-top: -5px;width: 100%" class="Data">
            <el-table-column prop="date" label="日期" width="160">
            </el-table-column>
            <el-table-column prop="name" label="姓名" width="160">
            </el-table-column>
            <el-table-column prop="address" label="地址"></el-table-column>
          </el-table>

        </el-card>

      </el-col>

      <el-col :span="15">

        <el-card class="box-card" style="margin-bottom: 15px;margin-left: 15px;margin-top: -10px;">
          <div ref="echarts3" style="height: 324px"></div>
        </el-card>

      </el-col>

      <el-col :span="11" style="margin-bottom: 20px;">

        <el-card class="box-card">
          <div ref="echarts2" style="height: 350px;position: relative;margin-top: 20px;margin-bottom: -20px;"></div>
        </el-card>
      </el-col>

      <el-col :span="7">
        <el-card class="box-card" style="margin-left: 20px;">
          <div ref="echarts1" style=" height: 350px"></div>
        </el-card>
      </el-col>

      <el-col :span="6">
        <el-card class="box-card" style="margin-left: 20px;">
          <div ref="echarts4" style=" height: 350px"></div>
        </el-card>
      </el-col>

    </el-row>
  </div>
</template>

<script>

import * as echarts from 'echarts';

export default {
  name: 'Perpage',
  data() {
    return {
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      notices: [],
      reportUrl: 'http://127.0.0.1:5501/datachange.html',
      searchTableHeight: 0,
      searchTableWidth: 0,
      tableData: [
        {
          date: "2023-05-02",
          name: "HotelMS管理员",
          address: "http://192.168.35.100:31726/",
        },
        {
          date: "2023-05-03",
          name: "HotelMS管理员",
          address: "http://192.168.35.100:31726/",
        },
        {
          date: "2023-05-04",
          name: "HotelMS管理员",
          address: "http://192.168.35.100:31726/",
        },
        {
          date: "2023-05-04",
          name: "HotelMS管理员",
          address: "http://192.168.35.100:31726/",
        },
        {
          date: "2023-05-04",
          name: "HotelMS管理员",
          address: "http://192.168.35.100:31726/",
        },
        {
          date: "2023-05-04",
          name: "HotelMS管理员",
          address: "http://192.168.35.100:31726/",
        },
        {
          date: "2023-05-04",
          name: "HotelMS管理员",
          address: "http://192.168.35.100:31726/",
        },
      ],
    };
  }, created() {
    this.$request.get('/notice/selectAll').then(res => {
      this.notices = res.data || []
    })
  }, mounted() {
    window.onresize = () => {
      this.widthHeight(); // 自适应高宽度
    };
    this.$nextTick(function () {
      this.widthHeight();
    });


    var mycharts1 = echarts.init(this.$refs.echarts1);
    var option1 = {
      title: {
        text: '数据指差异',
        subtext: '数据来源：前5个小时'
      },
      grid: {
        left: '3%',
        right: '7%',
        bottom: '7%',
        containLabel: true
      },
      tooltip: {
        // trigger: 'axis',
        showDelay: 0,
        formatter: function (params) {
          if (params.value.length > 1) {
            return (
              params.seriesName +
              ' :<br/>' +
              params.value[0] +
              ' ' +
              params.value[1] +
              ' '
            );
          } else {
            return (
              params.seriesName +
              ' :<br/>' +
              params.name +
              ' : ' +
              params.value +
              ' '
            );
          }
        },
        axisPointer: {
          show: true,
          type: 'cross',
          lineStyle: {
            type: 'dashed',
            width: 1
          }
        }
      },
      toolbox: {
        feature: {
          dataZoom: {},
          brush: {
            type: ['rect', 'polygon', 'clear']
          }
        }
      },
      brush: {},
      legend: {
        data: ['Data1', 'Data2'],
        left: 'center',
        bottom: 10
      },
      xAxis: [
        {
          type: 'value',
          scale: true,
          axisLabel: {
            formatter: '{value} cm'
          },
          splitLine: {
            show: false
          }
        }
      ],
      yAxis: [
        {
          type: 'value',
          scale: true,
          axisLabel: {
            formatter: '{value} '
          },
          splitLine: {
            show: false
          }
        }
      ],
      series: [
        {
          name: 'Female',
          type: 'scatter',
          emphasis: {
            focus: 'series'
          },
          // prettier-ignore
          data: [[161.2, 51.6], [167.5, 59.0], [159.5, 49.2], [157.0, 63.0], [155.8, 53.6],
          [170.0, 59.0], [159.1, 47.6], [166.0, 69.8], [176.2, 66.8], [160.2, 75.2],
          [172.5, 55.2], [170.9, 54.2], [172.9, 62.5], [153.4, 42.0], [160.0, 50.0],
          [147.2, 49.8], [168.2, 49.2], [175.0, 73.2], [157.0, 47.8], [167.6, 68.8],
          [159.5, 50.6], [175.0, 82.5], [166.8, 57.2], [176.5, 87.8], [170.2, 72.8],
          [174.0, 54.5], [173.0, 59.8], [179.9, 67.3], [170.5, 67.8], [160.0, 47.0],
          [154.4, 46.2], [162.0, 55.0], [176.5, 83.0], [160.0, 54.4], [152.0, 45.8],
          [162.1, 53.6], [170.0, 73.2], [160.2, 52.1], [161.3, 67.9], [166.4, 56.6],
          ],
          markArea: {
            silent: true,
            itemStyle: {
              color: 'transparent',
              borderWidth: 1,
              borderType: 'dashed'
            },
            data: [
              [
                {
                  name: 'Female Data Range',
                  xAxis: 'min',
                  yAxis: 'min'
                },
                {
                  xAxis: 'max',
                  yAxis: 'max'
                }
              ]
            ]
          },
          markPoint: {
            data: [
              { type: 'max', name: 'Max' },
              { type: 'min', name: 'Min' }
            ]
          },
          markLine: {
            lineStyle: {
              type: 'solid'
            },
            data: [{ type: 'average', name: 'AVG' }, { xAxis: 160 }]
          }
        },
        {
          name: 'Male',
          type: 'scatter',
          emphasis: {
            focus: 'series'
          },
          // prettier-ignore
          data: [[174.0, 65.6], [175.3, 71.8], [193.5, 80.7], [186.5, 72.6], [187.2, 78.8],
          [181.5, 74.8], [184.0, 86.4], [184.5, 78.4], [175.0, 62.0], [184.0, 81.6],
          [180.0, 76.6], [177.8, 83.6], [192.0, 90.0], [176.0, 74.6], [174.0, 71.0],
          [184.0, 79.6], [192.7, 93.8], [171.5, 70.0], [173.0, 72.4], [176.0, 85.9],
          [176.0, 78.8], [180.5, 77.8], [172.7, 66.2], [176.0, 86.4], [173.5, 81.8],
          [178.0, 89.6], [180.3, 82.8], [180.3, 76.4], [164.5, 63.2], [173.0, 60.9],
          [183.5, 74.8], [175.5, 70.0], [188.0, 72.4], [189.2, 84.1], [172.8, 69.1],
          [170.0, 59.5], [182.0, 67.2], [170.0, 61.3], [177.8, 68.6], [184.2, 80.1],
          ],
          markArea: {
            silent: true,
            itemStyle: {
              color: 'transparent',
              borderWidth: 1,
              borderType: 'dashed'
            },
            data: [
              [
                {
                  name: 'Male Data Range',
                  xAxis: 'min',
                  yAxis: 'min'
                },
                {
                  xAxis: 'max',
                  yAxis: 'max'
                }
              ]
            ]
          },
          markPoint: {
            data: [
              { type: 'max', name: 'Max' },
              { type: 'min', name: 'Min' }
            ]
          },
          markLine: {
            lineStyle: {
              type: 'solid'
            },
            data: [{ type: 'average', name: 'Average' }, { xAxis: 170 }]
          }
        }
      ]
    };
    mycharts1.setOption(option1);
    // 动态数据监测图片；

    var mycharts2 = echarts.init(this.$refs.echarts2);

    const categories = (function () {
      let now = new Date();
      let res = [];
      let len = 15;
      while (len--) {
        res.unshift(now.toLocaleTimeString().replace(/^\D*/, ''));
        now = new Date(+now - 2000);
      }
      return res;
    })();

    const categories2 = (function () {
      let res = [];
      let len = 15;
      while (len--) {
        res.push(15 - len - 1);
      }
      return res;
    })();

    const data = (function () {
      let res = [];
      let len = 15;
      while (len--) {
        res.push(Math.round(Math.random() * 1000));
      }
      return res;
    })();

    const data_1 = (function () {
      let res = [];
      let len = 15;
      while (len--) {
        res.push(Math.round(Math.random() * 987));
      }
      return res;
    })();

    const data2 = (function () {
      let res = [];
      let len = 0;
      while (len < 15) {
        res.push(+(Math.random() * 10 + 5).toFixed(1));
        len++;
      }
      return res;
    })();

    const data3 = (function () {
      let res = [];
      let len = 0;
      while (len < 15) {
        res.push(+(Math.random() * 9 + 7).toFixed(1));
        len++;
      }
      return res;
    })();
    var option2 = {
      title: {
        text: '动态监测数据'
      },
      tooltip: {
        trigger: 'axis',
        axisPointer: {
          type: 'cross',
          label: {
            backgroundColor: '#283b56'
          }
        }
      },
      legend: {},
      toolbox: {
        show: true,
        feature: {
          dataView: { readOnly: false },
          restore: {},
          saveAsImage: {}
        }
      },
      dataZoom: {
        show: false,
        start: 0,
        end: 100
      },
      xAxis: [
        {
          type: 'category',
          boundaryGap: true,
          data: categories
        },
        {
          type: 'category',
          boundaryGap: true,
          data: categories2
        }
      ],
      yAxis: [
        {
          type: 'value',
          scale: true,
          name: 'Price',
          max: 20,
          min: 0,
          boundaryGap: [0.2, 0.2]
        },
        {
          type: 'value',
          scale: true,
          name: 'Order',
          max: 1200,
          min: 0,
          boundaryGap: [0.2, 0.2]
        }
      ],
      series: [
        {
          name: 'Dynamic Bar',
          type: 'bar',
          showBackground: true,
          xAxisIndex: 1,
          yAxisIndex: 1,
          data: data
        },
        {
          name: 'Dynamic Bar2',
          type: 'bar',
          showBackground: true,
          xAxisIndex: 1,
          yAxisIndex: 1,
          data: data_1
        },
        {
          name: '指标1',
          type: 'line',
          data: data2,
          lineStyle: {
            width: 3,
          },
        },
        {
          color: 'rgb(128,205,193)',
          lineStyle: {
            color: 'rgb(128,205,193)',
            width: 3,
          },
          name: '指标2 ',
          type: 'line',
          data: data3
        }
      ]
    };
    app.count = 11;
    setInterval(function () {
      let axisData = new Date().toLocaleTimeString().replace(/^\D*/, '');
      data.shift();
      data.push(Math.round(Math.random() * 1000));
      data_1.shift();
      data_1.push(Math.round(Math.random() * 987));
      data2.shift();
      data2.push(+(Math.random() * 10 + 5).toFixed(1));
      data3.shift();
      data3.push(+(Math.random() * 9 + 7).toFixed(1));
      categories.shift();
      categories.push(axisData);
      categories2.shift();
      categories2.push(app.count++);
      mycharts2.setOption({
        xAxis: [
          {
            data: categories
          },
          {
            data: categories2
          }
        ],
        series: [
          {
            data: data
          },
          {
            data: data_1
          },
          {
            data: data2
          }, {
            data: data3
          }
        ]
      });
    }, 2100);
    mycharts2.setOption(option2);

    var mycharts3 = echarts.init(this.$refs.echarts3);
    var option3 = {
      title: {
    text: 'Distribution of Clients',
    subtext: 'Fake Data'
  },
  grid:{
		y:50,
		y2:25,
		borderWidth:1
	},
  tooltip: {
    trigger: 'axis',
    axisPointer: {
      type: 'cross'
    }
  },
  toolbox: {
    show: true,
    feature: {
      saveAsImage: {}
    }
  },
  xAxis: {
    type: 'category',
    boundaryGap: false,
    // prettier-ignore
    data: ['00:00', '01:15', '02:30', '03:45', '05:00', '06:15', '07:30', '08:45', '10:00', '11:15', '12:30', '13:45', '15:00', '16:15', '17:30', '18:45', '20:00', '21:15', '22:30', '23:45']
  },
  yAxis: {
    type: 'value',
    axisLabel: {
      formatter: '{value} W'
    },
    axisPointer: {
      snap: true
    }
  },
  visualMap: {
    show: false,
    dimension: 0,
    pieces: [
      {
        lte: 6,
        color: 'green'
      },
      {
        gt: 6,
        lte: 8,
        color: 'red'
      },
      {
        gt: 8,
        lte: 14,
        color: 'green'
      },
      {
        gt: 14,
        lte: 17,
        color: 'red'
      },
      {
        gt: 17,
        color: 'green'
      }
    ]
  },
  series: [
    {
      name: 'Electricity',
      type: 'line',
      smooth: true,
      // prettier-ignore
      data: [300, 280, 250, 260, 270, 300, 550, 500, 400, 390, 380, 390, 400, 500, 600, 750, 800, 700, 600, 400],
      markArea: {
        itemStyle: {
          color: 'rgba(255, 173, 177, 0.4)'
        },
        data: [
          [
            {
              name: 'Morning Peak',
              xAxis: '07:30'
            },
            {
              xAxis: '10:00'
            }
          ],
          [
            {
              name: 'Evening Peak',
              xAxis: '17:30'
            },
            {
              xAxis: '21:15'
            }
          ]
        ]
      }
    }
  ],}
    mycharts3.setOption(option3);

    var mycharts4 = echarts.init(this.$refs.echarts4);
    var option4 = {
      tooltip: {
    trigger: 'item'
  },
  legend: {
    top: '2%',
    left: 'center'
  },
  series: [
    {
      name: 'Room Type Distribution',
      type: 'pie',
      center: ["50%", "60%"],
      radius: ['20%', '65%'],
      avoidLabelOverlap: false,
      padAngle: 5,
      itemStyle: {
        borderRadius: 10
      },
      label: {
        show: false,
        position: 'center'
      },
      emphasis: {
        label: {
          show: true,
          fontSize: 10,
          fontWeight: 'bold'
        }
      },
      labelLine: {
        show: false
      },
      data: [
        { value: 1200, name: '大床房' },
        { value: 800, name: '双人床' },
        { value: 600, name: '套房别墅' },
        { value: 400, name: 'Luxury Room' }
      ]
    }
  ]
    }
    mycharts4.setOption(option4);

  }, methods: {
    widthHeight() {
      this.searchTableHeight = window.innerHeight - 20;
      this.searchTableWidth = window.innerWidth - 320;
    },
  }, updateUser() {
    this.user = JSON.parse(localStorage.getItem('xm-user') || '{}')   // 重新获取下用户的最新信息
  },
  goToPerson() {
    if (this.user.role === 'RECEP') {
      this.$router.push('/recep/recepPerson')
    }
  },
  logout() {
    localStorage.removeItem('xm-user')
    this.$router.push('/front/home')
  }
}
</script>

<style lang="less" scoped>
.Data {
  height: 370px;
}

.user {
  padding-bottom: 20px;
  margin-bottom: 20px;
  border-bottom: 1px solid #999;
  display: flex;
  align-items: center;

  img {
    margin-left: 30px;
    width: 200px;
    height: 200px;
    border-radius: 50%;
  }

  .userinfo {
    margin-left: 40px;

    .name {
      font-size: 25px;
      font-weight: 500;
      margin-top: 40px;
    }

    .access {
      color: #999;
      margin-top: 0px;
    }
  }
}

.login-info {
  p {
    font-size: 14px;
    color: #999999;
    line-height: 28px;

    span {
      color: #666666;
      margin-left: 20px;
    }
  }
}

.system_bulletin {
  .system_title {
    margin-bottom: -5px;
    font-size: 22px;
    color: #1f1f1f;
  }
}
</style>

</script>
