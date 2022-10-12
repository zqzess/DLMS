import Vue from 'vue'
import Vuex from 'vuex'

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

Vue.use(Vuex)


export default new Vuex.Store({
    state: {
        // Authtoken: "" // 存储的数据
        token: localStorage.getItem('token') ? localStorage.getItem('token') : '',
        userid: localStorage.getItem('userid') ? localStorage.getItem('userid') : '',
        type: localStorage.getItem('type') ? localStorage.getItem('type') : '',
    },  // 存放数据
    mutations: {
        // 定义的方法
        setToken(state, token) {
            state.token = token.token;
            state.userid = token.userid;
            state.type = token.type;
            localStorage.setItem("token", token.token);     //存储token
            localStorage.setItem("userid", token.userid);
            localStorage.setItem("type", token.type);
        },
        clearToken(state) {
            state.token = '';
            state.userid = '';
            state.type = '';
            localStorage.removeItem("token");
            localStorage.removeItem("userid");
            localStorage.removeItem("type");
            sessionStorage.removeItem("user");
            sessionStorage.removeItem("fstate");
            sessionStorage.removeItem("collegestate")
            sessionStorage.removeItem("departmentstate")
            sessionStorage.removeItem("majorstate")
            sessionStorage.removeItem("classstate")
            sessionStorage.removeItem("bracestate")
            sessionStorage.removeItem("funcstate")
            sessionStorage.removeItem("userListstate")
            sessionStorage.removeItem("manageUserstate")
            sessionStorage.removeItem("tableListstate")
            sessionStorage.removeItem("manageTablestate")
            sessionStorage.removeItem("myTablestate")
            sessionStorage.removeItem("tableViewstate")
            sessionStorage.removeItem("editTableContentstate")
            sessionStorage.removeItem("editTablestate")
            sessionStorage.removeItem("createTableState")
        },
        // clearTag(tag) {
        //     let name = [{name: "fstate"}, {name: "collegestate"}, {name: "departmentstate"}, {name: "majorstate"}, {name: "classstate"}, {name: "bracestate"}, {name: "funcstate"}, {name: "userListstate"}, {name: "manageUserstate"}]
        //     console.log("tag:"+tag.tag)
        //     for (let i = 0; i < name.length; i++) {
        //         if(tag==0&&name[i].name=='fstate')
        //         {
        //             console.log(name[i].name)
        //         }else if(tag==1&&name[i].name=='collegestate')
        //         {
        //             console.log(name[i].name)
        //         }else if(tag==2&&name[i].name=='departmentstate')
        //         {
        //             console.log(name[i].name)
        //         }else if(tag==3&&name[i].name=='majorstate')
        //         {
        //             console.log(name[i].name)
        //         }else if(tag==4&&name[i].name=='classstate')
        //         {
        //             console.log(name[i].name)
        //         }else if(tag==5&&name[i].name=='bracestate')
        //         {
        //             console.log(name[i].name)
        //         }else if(tag==6&&name[i].name=='funcstate')
        //         {
        //             console.log(name[i].name)
        //         }else if(tag==7&&name[i].name=='userListstate')
        //         {
        //             console.log(name[i].name)
        //         }else if(tag==8&&name[i].name=='manageUserstate')
        //         {
        //             console.log(name[i].name)
        //         }
        //         else {
        //             sessionStorage.removeItem(name[i].name)
        //         }
        //     }
        //     // if (type == 0) {
        //     //     sessionStorage.removeItem("collegestate")
        //     //     sessionStorage.removeItem("departmentstate")
        //     //     sessionStorage.removeItem("majorstate")
        //     //     sessionStorage.removeItem("classstate")
        //     //     sessionStorage.removeItem("bracestate")
        //     //     sessionStorage.removeItem("funcstate")
        //     //     sessionStorage.removeItem("userListstate")
        //     //     sessionStorage.removeItem("manageUserstate")
        //     // } else if (type == 1) {
        //     //     sessionStorage.removeItem("fstate")
        //     //     sessionStorage.removeItem("departmentstate")
        //     //     sessionStorage.removeItem("majorstate")
        //     //     sessionStorage.removeItem("classstate")
        //     //     sessionStorage.removeItem("bracestate")
        //     //     sessionStorage.removeItem("funcstate")
        //     //     sessionStorage.removeItem("userListstate")
        //     //     sessionStorage.removeItem("manageUserstate")
        //     // } else if (type == 2) {
        //     //     sessionStorage.removeItem("fstate")
        //     //     sessionStorage.removeItem("collegestate")
        //     //     sessionStorage.removeItem("majorstate")
        //     //     sessionStorage.removeItem("classstate")
        //     //     sessionStorage.removeItem("bracestate")
        //     //     sessionStorage.removeItem("funcstate")
        //     //     sessionStorage.removeItem("userListstate")
        //     //     sessionStorage.removeItem("manageUserstate")
        //     // } else if (type == 3) {
        //     //     sessionStorage.removeItem("fstate")
        //     //     sessionStorage.removeItem("collegestate")
        //     //     sessionStorage.removeItem("departmentstate")
        //     //     sessionStorage.removeItem("classstate")
        //     //     sessionStorage.removeItem("bracestate")
        //     //     sessionStorage.removeItem("funcstate")
        //     //     sessionStorage.removeItem("userListstate")
        //     //     sessionStorage.removeItem("manageUserstate")
        //     // } else if (type == 4) {
        //     //     sessionStorage.removeItem("fstate")
        //     //     sessionStorage.removeItem("collegestate")
        //     //     sessionStorage.removeItem("departmentstate")
        //     //     sessionStorage.removeItem("majorstate")
        //     //     sessionStorage.removeItem("bracestate")
        //     //     sessionStorage.removeItem("funcstate")
        //     //     sessionStorage.removeItem("userListstate")
        //     //     sessionStorage.removeItem("manageUserstate")
        //     // } else if (type == 5) {
        //     //     sessionStorage.removeItem("fstate")
        //     //     sessionStorage.removeItem("collegestate")
        //     //     sessionStorage.removeItem("departmentstate")
        //     //     sessionStorage.removeItem("majorstate")
        //     //     sessionStorage.removeItem("classstate")
        //     //     sessionStorage.removeItem("funcstate")
        //     //     sessionStorage.removeItem("userListstate")
        //     //     sessionStorage.removeItem("manageUserstate")
        //     // } else if (type == 6) {
        //     //     sessionStorage.removeItem("fstate")
        //     //     sessionStorage.removeItem("collegestate")
        //     //     sessionStorage.removeItem("departmentstate")
        //     //     sessionStorage.removeItem("majorstate")
        //     //     sessionStorage.removeItem("classstate")
        //     //     sessionStorage.removeItem("bracestate")
        //     //     sessionStorage.removeItem("userListstate")
        //     //     sessionStorage.removeItem("manageUserstate")
        //     // } else if (type == 7) {
        //     //     sessionStorage.removeItem("fstate")
        //     //     sessionStorage.removeItem("collegestate")
        //     //     sessionStorage.removeItem("departmentstate")
        //     //     sessionStorage.removeItem("majorstate")
        //     //     sessionStorage.removeItem("classstate")
        //     //     sessionStorage.removeItem("bracestate")
        //     //     sessionStorage.removeItem("funcstate")
        //     //     sessionStorage.removeItem("manageUserstate")
        //     // } else if (type == 8) {
        //     //     sessionStorage.removeItem("fstate")
        //     //     sessionStorage.removeItem("collegestate")
        //     //     sessionStorage.removeItem("departmentstate")
        //     //     sessionStorage.removeItem("majorstate")
        //     //     sessionStorage.removeItem("classstate")
        //     //     sessionStorage.removeItem("bracestate")
        //     //     sessionStorage.removeItem("funcstate")
        //     //     sessionStorage.removeItem("userListstate")
        //     // }
        // }
    },  // 对数据进行修改
})

