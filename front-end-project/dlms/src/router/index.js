import Vue from 'vue'
// import VueRouter from 'vue-router'
import Router from 'vue-router';

//导入自定义组件
import login from '@/views/login'
import HelloWorld from "@/components/HelloWorld";
// import regist from "@/views/regist";
import admin from "@/views/admin";
// import admin from "@/views/admin"
import student from "@/views/student"
import userInfo from "@/views/userInfo"
import page404 from "@/views/page404"
import error from "@/views/error"
import teacher from "@/views/teacher"
import college from  "@/views/college"
import department from  "@/views/department"
import major from  "@/views/major"
import classname from "@/views/classname"
import brace from  "@/views/brace"
import func from "@/views/func"
import userList from "@/views/userList"
import manageUser from "@/views/manageUser"
import tableList from "@/views/tableList"
import manageTable from "@/views/manageTable"
import myTable from "@/views/myTable"
import tableView from "@/views/tableView"
import editTableContent from "@/views/editTableContent"
import editTable from "@/views/editTable"
import createTable from "@/views/createTable"

/**
 *
 * @Author zqzess
 * @Date 2021/10/27
 * @File index.js
 * @Version 1.0
 * @Github https://github.com/zqzess
 * @Msg 亦余心之所善兮,虽九死其尤未毁
 *
 **/



//安装路由，显示引用
// Vue.use(VueRouter);
Vue.use(Router);
// 解决ElementUI导航栏中的vue-router在3.0版本以上重复点菜单报错问题
const originalPush = Router.prototype.push

Router.prototype.push = function push(location) {
    return originalPush.call(this, location).catch(err => err)
}


//配置导出路由
// export default new VueRouter({
//     routes:[
//         {
//             //路由路径
//             path:'/login',
//             //自定义路由名称
//             name:'登录',
//             //路由跳转的组件
//             component:login,
//             meta: {title: "订阅", keepAlive: true}
//         }
//     ]
// });
const router = new Router({
    mode: 'history',
    base: process.env.BASE_URL,
    routes: [
        {
            //路由路径
            path: '/login',
            //自定义路由名称
            name: '登录',
            //路由跳转的组件
            component: login,
            meta: {title: "登录", keepAlive: true}
        },
        {
            path: '/Hello',
            name: '欢迎',
            component: HelloWorld,
            meta: {title: "欢迎", keepAlive: false}
        },
        // {
        //     path: '/regist',
        //     name: '注册',
        //     component: regist,
        //     meta: {title: "注册", keepAlive: true}
        // },
        {
            path: '/admin',
            name: '管理',
            component: admin,
            meta: {title: "管理", keepAlive: true},
            children: [
                {
                    path: 'college',//以“/”开头的嵌套路径会被当作根路径，所以子路由上不用加“/”;在生成路由时，主路由上的path会被自动添加到子路由之前，所以子路由上的path不用在重新声明主路由上的path了。
                    name: 'college',
                    component: college,
                    meta: {title: "学院", keepAlive: true}
                },
                {
                    path: 'department',
                    name: 'department',
                    component: department,
                    meta: {title: "部门", keepAlive: true}
                },
                {
                    path: 'major',
                    name: 'major',
                    component: major,
                    meta: {title: "专业", keepAlive: true}
                },
                {
                    path: 'classname',
                    name: 'classname',
                    component: classname,
                    meta: {title: "班级", keepAlive: true}
                },
                {
                    path: 'brace',
                    name: 'brace',
                    component: brace,
                    meta: {title: "职称", keepAlive: true}
                },
                {
                    path: 'func',
                    name: 'func',
                    component: func,
                    meta: {title: "权限", keepAlive: true}
                },
                {
                    path: 'userList',
                    name: 'userList',
                    component: userList,
                    meta: {title: "用户列表", keepAlive: true},
                },
                {
                    path: 'tableList',
                    name: 'tableList',
                    component: tableList,
                    meta: {title: "表格", keepAlive: true},
                    children: [
                        {
                            path: '/admin/tableList/manageTable',
                            name: 'manageTable',
                            component: manageTable,
                            meta: {title: "管理表格", keepAlive: true}
                        },
                        {
                            path: '/admin/tableList/myTable',
                            name: 'myTable',
                            component: myTable,
                            meta: {title: "我的表格", keepAlive: true}
                        }
                    ]
                }
            ]
        },
        {
            path: '/student',
            name: '学生',
            component: student,
            meta: {title: "学生", keepAlive: true},
            children: [
                {
                    path: '/student/userInfo',
                    name: 'userInfo',
                    component: userInfo,
                    meta: {title: "个人信息", keepAlive: true}
                },
                {
                    path: '/student/tableList',
                    name: 'studentTableList',
                    component: tableList,
                    meta: {title: "表格", keepAlive: true},
                    children: [
                        {
                            path:'/student/tableList/editTable',
                            name:'editTable',
                            component:editTable,
                            meta: {title: "表格填写", keepAlive: true},
                        }
                    ]
                }
            ]
        },
        {
            path: '/manageUser',
            name: 'manageUser',
            component: manageUser,
            meta: {title: "用户信息", keepAlive: true}
        },
        {
            path:'/editTableContent',
            name: 'editTableContent',
            component: editTableContent,
            meta: {title: "填写表格", keepAlive: true},
        },
        {
            path: '/tableView',
            name: 'tableView',
            component: tableView,
            meta: {title: "查看表信息", keepAlive: true}
        },
        {
            path: '/createTable',
            name: 'createTable',
            component: createTable,
            meta: {title: "创建表ss", keepAlive: true}
        },
        {
            path: '/404',
            name: 'page404',
            component: page404
        },
        {
            path:'/teacher',
            name: 'teacher',
            component: teacher,
            meta: {title: "教师", keepAlive: true},
            children: [
                {
                    path: '/teacher/userInfo',
                    name: 'teacheruserInfo',
                    component: userInfo,
                    meta: {title: "个人信息", keepAlive: true}
                },
                {
                    path: '/teacher/tableList',
                    name: 'teacherTableList',
                    component: tableList,
                    meta: {title: "表格", keepAlive: true},
                    children: [
                        {
                            path:'/teacher/tableList/editTable',
                            name:'teacherEditTable',
                            component:editTable,
                            meta: {title: "表格填写", keepAlive: true},
                        },
                        {
                            path: '/teacher/tableList/myTable',
                            name: 'teacherMyTable',
                            component: myTable,
                            meta: {title: "我的表格", keepAlive: true}
                        }
                    ]
                }
            ]
        },
        {
            path: '/error',
            name: 'error',
            component: error,
        },
        {
            path: '/', // 页面不存在的情况下会跳到404页面
            redirect: '/login',
        },
        {
            path: '*', // 页面不存在的情况下会跳到404页面
            redirect: '/404',
            name: 'notFound',
            hidden: true
        }
    ]
});

// 导航守卫
// 使用 router.beforeEach 注册一个全局前置守卫，判断用户是否登陆
router.beforeEach((to, from, next) => {
    // let token = window.localStorage.getItem('token')
    // let type = window.localStorage.getItem('type')
    if (to.path === '/' || to.path === '/login' || to.path === '/error') {
        // console.log("允许直接访问")
        next();
    } else {
        let token = window.localStorage.getItem('token')
        // console.log("需要token")
        if (token === null || token === '') {
            // console.log("无token，跳转登录")
            next('/login');
        } else {
            // console.log("有token")
            next();
        }
    }
});

export default router;
