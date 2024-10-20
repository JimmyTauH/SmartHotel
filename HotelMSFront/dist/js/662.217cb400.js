"use strict";(self["webpackChunkvue"]=self["webpackChunkvue"]||[]).push([[662],{54662:function(t,e,s){s.r(e),s.d(e,{default:function(){return m}});var r=function(){var t=this,e=t._self._c;return e("div",{staticStyle:{width:"50%",margin:"5px auto"}},[e("div",{staticClass:"card"},[e("div",{staticStyle:{"font-weight":"bold","font-size":"24px","margin-bottom":"20px","text-align":"center"}},[t._v("发布/编辑帖子")]),e("el-form",{ref:"formRef",staticStyle:{"padding-right":"50px"},attrs:{model:t.form,"label-width":"100px",rules:t.rules}},[e("el-form-item",{attrs:{label:"标题",prop:"title"}},[e("el-input",{attrs:{placeholder:"标题"},model:{value:t.form.title,callback:function(e){t.$set(t.form,"title",e)},expression:"form.title"}})],1),e("el-form-item",{attrs:{label:"简介",prop:"descr"}},[e("el-input",{attrs:{type:"textarea",placeholder:"简介"},model:{value:t.form.descr,callback:function(e){t.$set(t.form,"descr",e)},expression:"form.descr"}})],1),e("el-form-item",{attrs:{label:"封面",prop:"cover"}},[e("el-upload",{attrs:{action:t.$baseUrl+"/files/upload",headers:{token:t.user.token},"list-type":"picture","on-success":t.handleCoverSuccess}},[e("el-button",{attrs:{type:"primary"}},[t._v("上传封面")])],1)],1),e("el-form-item",{attrs:{label:"分类",prop:"categoryId"}},[e("el-select",{staticStyle:{width:"100%"},model:{value:t.form.categoryId,callback:function(e){t.$set(t.form,"categoryId",e)},expression:"form.categoryId"}},t._l(t.categoryList,(function(t){return e("el-option",{key:t.id,attrs:{value:t.id,label:t.name}})})),1)],1),e("el-form-item",{attrs:{label:"相关酒店",prop:"contestId"}},[e("el-select",{staticStyle:{width:"100%"},attrs:{filterable:"",remote:"","remote-method":t.filterContests},model:{value:t.form.contestId,callback:function(e){t.$set(t.form,"contestId",e)},expression:"form.contestId"}},t._l(t.filteredContestList,(function(t){return e("el-option",{key:t.id,attrs:{value:t.id,label:t.name}})})),1)],1),e("el-form-item",{attrs:{label:"标签",prop:"tags"}},[e("el-select",{staticStyle:{width:"100%"},attrs:{multiple:"",filterable:"","allow-create":"","default-first-option":""},model:{value:t.tagsArr,callback:function(e){t.tagsArr=e},expression:"tagsArr"}},[e("el-option",{attrs:{value:"酒店发布"}}),e("el-option",{attrs:{value:"团队招募"}}),e("el-option",{attrs:{value:"信息分享"}}),e("el-option",{attrs:{value:"技术分享"}}),e("el-option",{attrs:{value:"杂谈"}})],1)],1),e("el-form-item",{attrs:{label:"内容",prop:"content"}},[e("div",{attrs:{id:"editor"}})])],1),e("div",{staticClass:"dialog-footer",staticStyle:{"text-align":"center"},attrs:{slot:"footer"},slot:"footer"},[e("el-button",{staticStyle:{width:"200px"},attrs:{type:"primary"},on:{click:t.save}},[t._v("保 存")])],1)],1)])},i=[],o=(s(28743),s(5507)),l=s.n(o),a=s(81109),n={name:"NewBlog",data(){return{form:{},tagsArr:[],categoryList:[],contestList:[],filteredContestList:[],editor:null,rules:{title:[{required:!0,message:"请输入标题",trigger:"blur"}],descr:[{required:!0,message:"请输入简介",trigger:"blur"}],categoryId:[{required:!0,message:"请选择分类",trigger:"blur"}],contestId:[{required:!0,message:"请选择相关酒店",trigger:"blur"}]},fromVisible:!1,fromVisible1:!1,user:JSON.parse(localStorage.getItem("xm-user")||"{}"),blogId:this.$route.query.blogId}},mounted(){this.$request.get("/category/selectAll").then((t=>{this.categoryList=t.data||[]})),this.$request.get("/activity/selectAll").then((t=>{this.contestList=t.data||[],this.filteredContestList=this.contestList})),this.$request.get("/blog/select/"+this.blogId).then((t=>{this.form=t.data||{},null!=this.form.id&&(this.tagsArr=JSON.parse(this.form.tags||"[]"),setTimeout((()=>{this.editor.txt.html(this.form.content)}),0))})),this.setRichText()},methods:{filterContests(t){t?this.$request.get("/activity/selectByName/"+t).then((t=>{this.filteredContestList=t.data})).catch((t=>{console.error("Error fetching activities:",t),this.$message.error("加载酒店数据失败")})):this.$request.get("/activity/selectAll").then((t=>{this.filteredContestList=t.data||[]}))},handleAdd(){this.form={},this.tagsArr=[],this.setRichText(),this.fromVisible=!0},handleEdit(t){this.form=JSON.parse(JSON.stringify(t)),this.tagsArr=JSON.parse(t.tags||"[]"),this.fromVisible=!0,this.setRichText(),setTimeout((()=>{this.editor.txt.html(this.form.content)}),0)},save(){this.$refs.formRef.validate((t=>{t&&(this.form.tags=JSON.stringify(this.tagsArr),this.form.content=this.editor.txt.html(),this.$request({url:this.form.id?"/blog/update":"/blog/add",method:this.form.id?"PUT":"POST",data:this.form}).then((t=>{"200"===t.code?(this.$message.success("保存成功"),this.$router.push("/front/Home")):this.$message.error(t.msg)})))}))},handleCoverSuccess(t){this.form.cover=t.data},setRichText(){this.$nextTick((()=>{this.editor=new(l())("#editor"),this.editor.highlight=a.A,this.editor.config.uploadImgServer=this.$baseUrl+"/files/editor/upload",this.editor.config.uploadFileName="file",this.editor.config.zIndex=0,this.editor.config.uploadImgHeaders={token:this.user.token},this.editor.config.uploadImgParams={type:"img"},this.editor.create()}))}}},c=n,d=s(81656),h=(0,d.A)(c,r,i,!1,null,"09c56c5c",null),m=h.exports}}]);
//# sourceMappingURL=662.217cb400.js.map