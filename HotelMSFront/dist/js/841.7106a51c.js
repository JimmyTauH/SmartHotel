"use strict";(self["webpackChunkvue"]=self["webpackChunkvue"]||[]).push([[841],{86841:function(t,e,s){s.r(e),s.d(e,{default:function(){return d}});var i=function(){var t=this,e=t._self._c;return e("div",{staticClass:"main-content",staticStyle:{width:"50%"}},[e("div",{staticClass:"card",staticStyle:{"font-weight":"bold","font-size":"28px",display:"flex"}},[e("div",{staticStyle:{"margin-right":"80px"}},[t._v("现有积分："+t._s(t.newUser.integral))]),e("div",[t._v("签到天数："+t._s(t.count))]),e("div",{staticStyle:{flex:"1","text-align":"right"}},[e("el-button",{attrs:{size:"medium",type:"primary"},on:{click:function(e){return t.handleCheckIn()}}},[t._v("签到")]),e("el-button",{attrs:{size:"medium",type:"success"},on:{click:function(e){t.drawer=!0}}},[t._v("充值")])],1)]),e("div",{staticClass:"card",staticStyle:{"margin-top":"10px"}},[e("div",{staticStyle:{color:"#999999"}},[t._v("最近30条记录")]),e("el-table",{staticStyle:{width:"100%"},attrs:{data:t.tableData}},[e("el-table-column",{attrs:{prop:"type",label:"方式",width:"200"}}),e("el-table-column",{attrs:{prop:"time",label:"获取日期",width:"200"}}),e("el-table-column",{attrs:{prop:"integral",label:"积分变动",width:"200"}})],1)],1),e("el-drawer",{attrs:{title:"充值",visible:t.drawer,direction:t.direction,"before-close":t.handleClose},on:{"update:visible":function(e){t.drawer=e}}},[e("el-card",{staticStyle:{padding:"20px"},attrs:{shadow:"never"}},[e("div",{staticClass:"card"},[e("el-row",{attrs:{gutter:10}},t._l(t.items,(function(s){return e("el-col",{key:s,attrs:{span:6}},[e("div",{staticClass:"charge-item",class:{active:t.current===s},on:{click:function(e){return t.clickItem(s)}}},[t._v(" ￥"+t._s(s)+" ")])])})),1),e("div",{staticStyle:{"margin-top":"20px"}},[e("el-button",{staticStyle:{"background-color":"#f94b4b"},attrs:{type:"primary",size:"medium"},on:{click:function(e){return t.handlePay()}}},[t._v("支 付")])],1)],1)])],1)],1)},a=[],r={name:"SingIn",data(){return{user:JSON.parse(localStorage.getItem("xm-user")||"{}"),newUser:{},integral:10,count:0,value:new Date,tableData:[],drawer:!1,direction:"rtl",current:6,items:[6,12,30,68,98,128,328,648]}},mounted(){this.load()},methods:{clickItem(t){this.current=t},handleClose(t){this.$confirm("确认关闭？").then((e=>{t()})).catch((t=>{}))},load(){this.$request.get("user/select/"+this.user.id).then((t=>{this.newUser=t.data||{}})),this.$request.get("/records/selectAll",{params:{userId:this.user.id}}).then((t=>{this.tableData=t.data||[]})),this.$request.get("/signIn/selectCount/"+this.user.id).then((t=>{this.count=t.data||0}))},handleCheckIn(){this.$request.post("/signIn/add",{userId:this.user.id,integral:this.integral}).then((t=>{"200"===t.code?(this.$message.success("签到成功"),this.$request.put("/user/updateIntegral2",{id:this.user.id,integral:this.integral}).then((t=>{"200"===t.code?(this.$message.success("积分更新成功"),this.load()):this.$message.error("积分更新失败")}))):this.$message.error(t.msg)})),this.load()},handlePay(){this.$request.put("/user/updateIntegral",{id:this.user.id,integral:this.current}).then((t=>{"200"===t.code?(this.$message.success("充值成功"),this.load()):this.$message.error("充值失败")}))}}},l=r,n=s(81656),c=(0,n.A)(l,i,a,!1,null,"b4aa3f0e",null),d=c.exports}}]);
//# sourceMappingURL=841.7106a51c.js.map