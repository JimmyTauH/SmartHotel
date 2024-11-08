import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

// 解决导航栏或者底部导航tabBar中的vue-router在3.0版本以上频繁点击菜单报错的问题。
const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push (location) {
  return originalPush.call(this, location).catch(err => err)
}

const routes = [
  {
    path: '/',
    name: 'Manager',
    component: () => import('../views/Manager.vue'),
    redirect: '/front/home',  // 重定向到主页
    children: [
      { path: '403', name: 'NoAuth', meta: { name: '无权限' }, component: () => import('../views/manager/403') },
      { path: 'home', name: 'Home', meta: { name: '系统首页' }, component: () => import('../views/manager/Home') },
      { path: 'admin', name: 'Admin', meta: { name: '管理员信息' }, component: () => import('../views/manager/Admin') },
      { path: 'adminPerson', name: 'AdminPerson', meta: { name: '个人信息' }, component: () => import('../views/manager/AdminPerson') },
      { path: 'password', name: 'Password', meta: { name: '修改密码' }, component: () => import('../views/manager/Password') },
      { path: 'notice', name: 'Notice', meta: { name: '酒店公告' }, component: () => import('../views/manager/Notice') },
      { path: 'user', name: 'User', meta: { name: '用户信息' }, component: () => import('../views/manager/User') },
      { path: 'recep', name: 'Recep', meta: { name: '前台信息' }, component: () => import('../views/manager/Recep') },
      { path: 'server', name: 'Server', meta: { name: '后勤信息' }, component: () => import('../views/manager/Server') },
      { path: 'category', name: 'Category', meta: { name: '分类信息' }, component: () => import('../views/manager/Category') },
      { path: 'blog', name: 'Blog', meta: { name: '酒店信息' }, component: () => import('../views/manager/Blog') },
      { path: 'activity', name: 'Activity', meta: { name: '房型信息' }, component: () => import('../views/manager/Activity') },
      { path: 'comment', name: 'Comment', meta: { name: '评论管理' }, component: () => import('../views/manager/Comment') },
      { path: 'activitySign', name: 'ActivitySign', meta: { name: '酒店入住' }, component: () => import('../views/manager/ActivitySign') },
      { path: 'certification', name: 'Certification', meta: { name: '会员认证' }, component: () => import('../views/manager/Certification.vue') },
      { path: 'circulars', name: 'Circulars', meta: { name: '系统通知' }, component: () => import('../views/manager/Circulars.vue') },
      { path: 'dailyreport', name: 'DailyReport', meta: { name: '日报表' }, component: () => import('../views/manager/DailyReport.vue') },
      { path: 'weeklyreport', name: 'WeeklyReport', meta: { name: '周报表' }, component: () => import('../views/manager/WeeklyReport.vue') },
      { path: 'monthlyreport', name: 'MonthlyReport', meta: { name: '月报表' }, component: () => import('../views/manager/MonthlyReport.vue') },
    ]
  },
  {
    path: '/front',
    name: 'Front',
    component: () => import('../views/Front.vue'),
    children: [
      { path: 'home', name: 'FrontHome', meta: { name: '系统首页' }, component: () => import('../views/front/Home.vue') },
      { path: 'person', name: 'Person', meta: { name: '个人信息' }, component: () => import('../views/front/Person') },
      { path: 'activity', name: 'Activity', meta: { name: '房型信息' }, component: () => import('../views/front/Activity') },
      { path: 'activityDetail', name: 'ActivityDetail', meta: { name: '酒店详情' }, component: () => import('../views/front/ActivityDetail') },
      { path: 'blogDetail', name: 'BlogDetail', meta: { name: '帖子详情' }, component: () => import('../views/front/BlogDetail') },
      { path: 'search', name: 'Search', meta: { name: '搜索' }, component: () => import('../views/front/Search') },
      { path: 'newBlog', name: 'NewBlog', meta: { name: '发布帖子' }, component: () => import('../views/front/NewBlog') },
      // { path: 'signIn', name: 'SignIn', meta: { name: '签到' }, component: () => import('../views/front/SignIn.vue') },
      { path: 'certification', name: 'Certification', meta: { name: '会员认证' }, component: () => import('../views/front/Certification.vue') },
      { path: 'circulars', name: 'Circulars', meta: { name: '系统通知' }, component: () => import('../views/front/Circulars.vue') },
      { path: 'homePage', name: 'HomePage', meta: { name: '我的主页' }, component: () => import('../views/front/HomePage.vue') },
      { path: 'editHomePage', name: 'EditHomePage', meta: { name: '编辑主页' }, component: () => import('../views/front/EditHomePage.vue') },
      { path: 'exitMemberWelfare', name: 'ExitMemberWelfare', meta: { name: '编辑会员福利' }, component: () => import('../views/front/ExitMemberWelfare.vue') },
      { path: 'serviceBooking', name: 'ServiceBooking', meta: { name: '服务预约申请' }, component: () => import('../views/front/ServiceBooking.vue') },
    ]
  },
  { path: '/login', name: 'Login', meta: { name: '登录' }, component: () => import('../views/Login.vue') },
  { path: '/register', name: 'Register', meta: { name: '注册' }, component: () => import('../views/Register.vue') },

    // 前台路径
  { 
    path: '/recep', name: 'Recep', meta: { name: '前台'}, component: () => import('../views/Receptionist.vue'),
    children: [
      { path: 'home', name: 'Home', meta: { name: '前台首页' }, component: () => import('../views/recep/Home.vue') },
      { path: 'recepPerson', name: 'RecepPerson', meta: { name: '个人信息' }, component: () => import('../views/recep/RecepPerson.vue') },
      { path: 'ruzhu', name: 'ruzhu', meta: { name: '入住管理' }, component: () => import('../views/recep/Ruzhu.vue') },
      { path: 'tuifang', name: 'tuifang', meta: { name: '退房管理' }, component: () => import('../views/recep/Tuifang.vue') },
      { path: 'houqin', name: 'houqin', meta: { name: '后勤管理' }, component: () => import('../views/recep/Houqin.vue') },
      { path: 'kefu', name: 'kefu', meta: { name: '客服服务' }, component: () => import('../views/recep/Kefu.vue') },
      ]
    },
    // 后勤路径
    { 
      path: '/server', name: 'Server', meta: { name: '后勤'}, component: () => import('../views/Server.vue'),
      children: [
        { path: 'home', name: 'Home', meta: { name: '后勤首页' }, component: () => import('../views/server/Home.vue') },
        { path: 'serverPerson', name: 'ServerPerson', meta: { name: '个人信息' }, component: () => import('../views/server/ServerPerson.vue') },
        { path: 'work', name: 'work', meta: { name: '顾客预约服务' }, component: () => import('../views/server/ServiceChecking.vue') },
        { path: 'report', name: 'report', meta: { name: '报告设施状态' }, component: () => import('../views/server/Report.vue') },
        { path: 'logging', name: 'logging', meta: { name: '打扫日志' }, component: () => import('../views/server/Logging.vue') },
        { path: 'serverPerson', name: 'serverPerson', meta: { name: '主页' }, component: () => import('../views/server/ServerPerson.vue') },
  
      ]
    },
  { path: '*', name: 'NotFound', meta: { name: '无法访问' }, component: () => import('../views/404.vue') },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

// 注：不需要前台的项目，可以注释掉该路由守卫
// 路由守卫
// router.beforeEach((to ,from, next) => {
//   let user = JSON.parse(localStorage.getItem("xm-user") || '{}');
//   if (to.path === '/') {
//     if (user.role) {
//       if (user.role === 'USER') {
//         next('/front/home')
//       } else {
//         next('/home')
//       }
//     } else {
//       next('/login')
//     }
//   } else {
//     next()
//   }
// })

export default router
