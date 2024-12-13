<template>
  <div>
    <div class="search">
      <el-input placeholder="请输入房型名称查询" style="width: 200px" v-model="name"></el-input>
      <el-input placeholder="请输入酒店名称查询" style="width: 200px; margin-left: 5px" v-model="searchHotelName" clearable>
      </el-input>
      <el-button type="info" plain style="margin-left: 10px" @click="load(1)">查询</el-button>
      <el-button type="warning" plain style="margin-left: 10px" @click="reset">重置</el-button>
    </div>

    <div class="operation">
      <el-button type="primary" plain @click="handleAdd">新增</el-button>
      <el-button type="danger" plain @click="delBatch">批量删除</el-button>
    </div>

    <div class="table">

      <el-table :data="filteredTableData" stripe @selection-change="handleSelectionChange" >
        <el-table-column type="selection" width="60" align="center"></el-table-column>
        <el-table-column prop="cover" label="封面" >
          <template v-slot="scope">
            <div style="display: flex; align-items: center" >
              <el-image style="width:120px; height: 70px; border-radius: 5px" v-if="scope.row.cover"
                :src="scope.row.cover" :preview-src-list="[scope.row.cover]"></el-image>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="hotelID" label="所属酒店" :formatter="getHotelName" width="240"></el-table-column>
        <el-table-column prop="name" label="房型名称" show-overflow-tooltip width="240"></el-table-column>



        <el-table-column prop="descr" label="房间简介" show-overflow-tooltip width="240"></el-table-column>
        <!-- <el-table-column prop="applystart" label="房间入住时间"></el-table-column>
        <el-table-column prop="applyend" label="房间退房时间"></el-table-column> -->
        <!-- <el-table-column prop="form" label="订房渠道"></el-table-column> -->
        <el-table-column prop="host" label="今日房价"></el-table-column>
        <el-table-column prop="number" label="剩余房量"></el-table-column>

        <el-table-column prop="readCount" label="浏览量"></el-table-column>
        <el-table-column label="酒店描述" width="150px">
          <template v-slot="scope">
            <div>
              <el-button @click="handleActivityContent(scope.row.content)">查看酒店描述</el-button>
            </div>
          </template>
        </el-table-column>

        <el-table-column label="操作" width="180" align="center">
          <template v-slot="scope">
            <el-button plain type="primary" @click="handleEdit(scope.row)" size="mini">编辑</el-button>
            <el-button plain type="danger" size="mini" @click=del(scope.row.id)>删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <div class="pagination">
        <el-pagination background @current-change="handleCurrentChange" :current-page="pageNum"
          :page-sizes="[5, 10, 20]" :page-size="pageSize" layout="total, prev, pager, next" :total="total">
        </el-pagination>
      </div>
    </div>

    <el-dialog title="房型信息" :visible.sync="fromVisible" width="70%" :close-on-click-modal="false" destroy-on-close>
      <el-form label-width="100px" style="padding-right: 50px" :model="form" :rules="rules" ref="formRef">
        <el-form-item label="相关酒店" prop="hotelID">
          <el-select v-model="form.hotelID" filterable remote :remote-method="filterHotels" style="width: 100%">
            <el-option v-for="item in filteredHotelList" :key="item.id" :value="item.id"
              :label="item.title"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item prop="name" label="房型名称">
          <el-input v-model="form.name" placeholder="房型名称"></el-input>
        </el-form-item>
        <el-form-item prop="descr" label="房型简介">
          <el-input v-model="form.descr" placeholder="房型简介" type="textarea"></el-input>
        </el-form-item>
        <el-form-item prop="cover" label="封面">
          <el-upload :action="$baseUrl + '/files/upload'" :headers="{ token: user.token }" list-type="picture"
            :on-success="handleCoverSuccess">
            <el-button type="primary">上传封面</el-button>
          </el-upload>
        </el-form-item>


        <!-- <el-form-item prop="applystart" label="房间入住时间">
          <el-date-picker
              v-model="form.applystart"
              type="date"
              placeholder="请选择房间入住时间"
              value-format="yyyy-MM-dd"
              format="yyyy-MM-dd"
          />
        </el-form-item>
        <el-form-item prop="applyend" label="房间退房时间">
          <el-date-picker
              v-model="form.applyend"
              type="date"
              placeholder="请选择房间退房时间"
              value-format="yyyy-MM-dd"
              format="yyyy-MM-dd"
          />
        </el-form-item> -->

        <el-form-item prop="host" label="今日房价">
          <el-input v-model="form.host" placeholder="今日房价"></el-input>
        </el-form-item>
        <el-form-item prop="number" label="总房量">
          <el-input v-model="form.number" placeholder="总房量"></el-input>
        </el-form-item>

        <el-form-item prop="content" label="房型相关信息">
          <div id="editor"></div>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="fromVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="房型相关信息" :visible.sync="fromVisible1" width="70%" :close-on-click-modal="false" destroy-on-close>
      <div class="w-e-text">
        <div v-html="content"></div>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="fromVisible1 = false">关 闭</el-button>
      </div>
    </el-dialog>


  </div>
</template>

<script>
import E from "wangeditor"
import hljs from 'highlight.js'

export default {
  name: "Activity",
  data() {
    return {
      tableData: [],  // 所有的数据
      searchHotelName: "",
      pageNum: 1,   // 当前的页码
      pageSize: 10,  // 每页显示的个数,
      hotelList: [],
      filteredHotelList: [],  // 过滤后的酒店列表
      total: 0,
      name: null,
      host: null,
      number: 0,
      fromVisible: false,
      form: {},
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      rules: {
        name: [
          { required: true, message: '请输入房型名称', trigger: 'blur' },
        ],
        descr: [
          { required: true, message: '请输入房型简介', trigger: 'blur' },
        ],
        // applystart: [
        //   {required: false, message: '请输入房间入住时间', trigger: 'blur'},
        // ],
        // applyend: [
        //   {required: false, message: '请输入房间退房时间', trigger: 'blur'},
        // ],
        host: [
          { required: true, message: '请输入今日房价', trigger: 'blur' },
        ],
        number: [
          { required: true, message: '请输入剩余房量', trigger: 'blur' },
        ],
        hotelID: [
          { required: true, message: '请选择相关酒店', trigger: 'blur' }
        ],
      },
      ids: [],
      editor: null,
      content: '',
      fromVisible1: false
    }
  },
  created() {
    this.load(1)
  },
  mounted() {
    this.$request.get('/blog/selectAll').then(response => {
      this.hotelList = response.data || [];
      this.filteredHotelList = this.hotelList;  // 初始化过滤列表为完整列表
    });
  },
  computed: {
    filteredTableData() {
      // 使用计算属性根据输入的酒店名称来过滤表格数据
      if (!this.searchHotelName) {
        return this.tableData; // 如果没有输入查询条件，返回全部数据
      }

      return this.tableData.filter(row => {
        const hotel = this.filteredHotelList.find(item => item.id === row.hotelID);
        return hotel && hotel.title.includes(this.searchHotelName);
      });
    }
  },
  methods: {
    filterHotels(query) {
      if (query) {
        this.$request.get(`/blog/selectBytitle/` + query).then(response => {
          this.filteredHotelList = response.data;
        }).catch(error => {
          console.error('Error fetching blogs:', error);
          this.$message.error('加载酒店数据失败');
        });
      } else {
        this.$request.get('/blog/selectAll').then(response => {
          this.filteredHotelsList = response.data || [];
        });
      }
    },
    handleCoverSuccess(res) {
      this.form.cover = res.data
    },
    handleAdd() {   // 新增数据
      this.form = {}  // 新增数据的时候清空数据
      this.setRichText('')   // 初始化富文本编辑器
      this.fromVisible = true   // 打开弹窗
    },
    handleEdit(row) {   // 编辑数据
      this.form = JSON.parse(JSON.stringify(row))  // 给form对象赋值  注意要深拷贝数据
      this.setRichText(this.form.content) // 初始化富文本编辑器
      this.fromVisible = true   // 打开弹窗
    },
    save() {   // 保存按钮触发的逻辑  它会触发新增或者更新
      this.$refs.formRef.validate((valid) => {
        if (valid) {
          this.form.content = this.editor.txt.html()
          this.$request({
            url: this.form.id ? '/activity/update' : '/activity/add',
            method: this.form.id ? 'PUT' : 'POST',
            data: this.form
          }).then(res => {
            if (res.code === '200') {  // 表示成功保存
              this.$message.success('保存成功')
              this.load(1)
              this.fromVisible = false
            } else {
              this.$message.error(res.msg)  // 弹出错误的信息
            }
          })
        }
      })
    },
    del(id) {   // 单个删除
      this.$confirm('您确定删除吗？', '确认删除', { type: "warning" }).then(response => {
        this.$request.delete('/activity/delete/' + id).then(res => {
          if (res.code === '200') {   // 表示操作成功
            this.$message.success('操作成功')
            this.load(1)
          } else {
            this.$message.error(res.msg)  // 弹出错误的信息
          }
        })
      }).catch(() => {
      })
    },
    getHotelName(row) {
      const hotel = this.filteredHotelList.find(item => item.id === row.hotelID);
      return hotel ? hotel.title : ''; // 找到匹配的酒店名称，否则返回空字符串
    },
    handleSelectionChange(rows) {   // 当前选中的所有的行数据
      this.ids = rows.map(v => v.id)   //  [1,2]
    },
    delBatch() {   // 批量删除
      if (!this.ids.length) {
        this.$message.warning('请选择数据')
        return
      }
      this.$confirm('您确定批量删除这些数据吗？', '确认删除', { type: "warning" }).then(response => {
        this.$request.delete('/activity/delete/batch', { data: this.ids }).then(res => {
          if (res.code === '200') {   // 表示操作成功
            this.$message.success('操作成功')
            this.load(1)
          } else {
            this.$message.error(res.msg)  // 弹出错误的信息
          }
        })
      }).catch(() => {
      })
    },
    load(pageNum) {  // 分页查询
      if (pageNum) this.pageNum = pageNum
      this.$request.get('/activity/selectPage', {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          name: this.name,
          host: this.host,
        }
      }).then(res => {
        this.tableData = res.data?.list
        this.total = res.data?.total
      })
    },
    reset() {
      this.name = null
      this.host = null
      this.number = 0
      this.load(1)
    },
    handleCurrentChange(pageNum) {
      this.load(pageNum)
    },
    setRichText(content) {
      this.$nextTick(() => {
        this.editor = new E(`#editor`)
        this.editor.highlight = hljs
        this.editor.config.uploadImgServer = this.$baseUrl + '/files/editor/upload'
        this.editor.config.uploadFileName = 'file'
        this.editor.config.uploadImgHeaders = {
          token: this.user.token
        }
        this.editor.config.uploadImgParams = {
          type: 'img',
        }
        this.editor.create()  // 创建
        this.editor.txt.html(content)
      })
    },
    handleActivityContent(content) {
      this.content = content
      this.fromVisible1 = true
    }
  }
}
</script>

<style scoped>
.w-e-text img {
  width: 100% !important;
}
</style>
