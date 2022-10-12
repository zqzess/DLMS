<template>
  <div class="content">
    <el-container style="height: 98%; border: 1px solid #eee;padding: 1%">
      <div style="margin-top: 1%">
        <label style="width: 100%;font-weight: bold;font-size: 26px;padding: 10%">
          管理
        </label>
<!--        <el-radio-group v-model="isCollapse" style="margin-bottom: 20px;">-->
<!--          <el-radio-button :label="false">展开</el-radio-button>-->
<!--          <el-radio-button :label="true">收起</el-radio-button>-->
<!--        </el-radio-group>-->
        <el-aside width="200px" style="background-color: rgb(238, 241, 246); height: 96%;margin-top: 5%" >
<!--          <el-menu :default-active="$route.name" @open="handleOpen" @close="handleClose" :collapse="isCollapse">-->
          <el-menu :default-active="$route.name" :collapse="isCollapse">
            <el-submenu index="1">
              <template slot="title"><i class="el-icon-edit-outline"/><span slot="title">信息管理</span></template>
              <el-menu-item-group title="综合" v-show="adminshow3">
                <!--                <template slot="title">综合</template>-->
                <el-menu-item index="college" @click="gotoCollege">学院</el-menu-item>
                <el-menu-item index="department" @click="gotodepartmentstate">部门</el-menu-item>
              </el-menu-item-group>
              <el-menu-item-group title="科目" v-show="adminshow1.class">
                <el-menu-item index="major" v-show="adminshow2.major" @click="gotoMajor">专业</el-menu-item>
                <el-menu-item index="classname" v-show="adminshow1.class" @click="gotoClass">班级</el-menu-item>
              </el-menu-item-group>
              <el-menu-item-group title="表格">
                <el-menu-item index="tableList" @click="gotoTableList">表格统计</el-menu-item>
                <el-menu-item index="createTable" @click="goToCreateTable">创建表格</el-menu-item>
              </el-menu-item-group>
            </el-submenu>
            <el-submenu index="2">
              <template slot="title"><i class="el-icon-edit-outline"/><span slot="title">人员管理</span></template>
              <el-menu-item-group>
                <template slot="title">职位权限</template>
                <el-menu-item index="brace" v-show="adminshow3" @click="gotoBrace">职称</el-menu-item>
                <el-menu-item index="func" @click="gotoFunc">权限</el-menu-item>
              </el-menu-item-group>
              <el-menu-item-group title="人员信息" v-show="adminshow1.user">
                <el-menu-item index="userList" @click="gotoUserList">人员信息修改</el-menu-item>
              </el-menu-item-group>
            </el-submenu>
          </el-menu>
        </el-aside>
      </div>


      <el-container>

        <el-header style="text-align: right; font-size: 12px; width: 100%">
          <el-dropdown>
            <i class="el-icon-setting" style="margin-right: 15px"></i>
            <el-dropdown-menu slot="dropdown">
<!--              <el-dropdown-item>查看</el-dropdown-item>-->
<!--              <el-dropdown-item>新增</el-dropdown-item>-->
<!--              <el-dropdown-item>删除</el-dropdown-item>-->
              <el-dropdown-item @click.native="logout">登出</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
          <span>{{ username }}</span>
        </el-header>

        <el-main>
          <keep-alive>
            <router-view></router-view>
          </keep-alive>
        </el-main>

      </el-container>

    </el-container>

  </div>
</template>

<script>
import {mapMutations} from 'vuex';

export default {
  name: "admin",
  data() {
    return {
      username: '',
      adminshow1: {
        user: false,
        class: false
      },
      adminshow2: {
        func: false,
        major: false
      },
      adminshow3: false,
      adminfunc1: {
        user: '',
        class: ''
      },
      adminfunc2: {
        func: '',
        major: ''
      },
    isCollapse: false
    }
  },
  created() {
    let v = this;
    let type = localStorage.getItem("type")
    if (type == 0) {
      this.$router.push('/student')
    } else if (type == 1) {
      this.$router.push('/teacher')
    }
    let userid = localStorage.getItem("userid")
    this.$axios.get('/user/getUserWithAccess', {
      params: {
        userid: userid,
        typeid: '5'
      }
    }).then(res => {
      // console.log(res)
      if (res.data.code === 200) {
        // console.log("认证通过:  "+res)
        let result = res.data.content
        let functions = res.data.function
        // console.log("用户信息:" + result)
        // console.log("姓名:"+result.name)
        // console.log("管理员权限:" + functions)
        this.username = result.name + ",欢迎你!"
        for (let i = 0; i < functions.length; i++) {
          if (functions[i].id === 210) {//部门管理员管理人员
            v.adminfunc1.user = functions[i].state
          } else if (functions[i].id === 211) {//部门管理员管理班级
            v.adminfunc1.class = functions[i].state
          } else if (functions[i].id === 310) {//学院管理员权限管理
            v.adminfunc2.func = functions[i].state
          } else if (functions[i].id === 311) {//学院管理员管理专业
            v.adminfunc2.major = functions[i].state
          }
        }
        if (result.type === 2) {//部门
          if (v.adminfunc1.user === 0) {
            v.adminshow1.user = true;
          }
          if (v.adminfunc1.class === 0) {
            v.adminshow1.class = true;
          }
          v.adminshow3 = false;
        } else if (result.type === 3) {//学院
          // this.adminshow2=false;
          if (v.adminfunc2.major === 0) {
            v.adminshow2.major = true;
          }
          v.adminshow2.func = true;
          v.adminshow1.user = true;
          v.adminshow1.class = true;
          v.adminshow3 = false;
        } else if (result.type === 4) {
          v.adminshow1.user = true;
          v.adminshow1.class = true;
          v.adminshow2.major = true;
          v.adminshow2.func = true;
          v.adminshow3 = true;
        }
      } else if (res.data.code === 401) {
        // console.log("错误")
        v.$message(res.data.msg);
        setTimeout(() => {
          v.$router.push({path: "/login"}); // 强制切换当前路由 path
          // loading.close();
        }, 3000)
      }
    })
  },
  methods: {
    ...mapMutations(['clearToken']),
    logout() {
      // console.log("退出登录")
      this.clearToken();
      setTimeout(() => {
        this.$router.push('/login');
        // loading.close();
      }, 500)
    },
    gotoCollege(){
      sessionStorage.setItem('collegestate', 0)
      this.$router.push({name: "college"})
    },
    gotodepartmentstate(){
      sessionStorage.setItem('departmentstate', 0)
      this.$router.push({name: "department"})
    },
    gotoMajor(){
      sessionStorage.setItem('majorstate', 0)
      this.$router.push({name: "major"})
    },
    gotoClass(){
      sessionStorage.setItem('classstate', 0)
      this.$router.push({name: "classname"})
    },
    gotoBrace(){
      sessionStorage.setItem('bracestate', 0)
      this.$router.push({name: "brace"})
    },
    gotoFunc()
    {
      sessionStorage.setItem('funcstate', 0)
      this.$router.push({name: "func"})
    },
    // handleOpen(key, keyPath) {
    //   console.log(key, keyPath);
    // },
    // handleClose(key, keyPath) {
    //   console.log(key, keyPath);
    // },
    gotoUserList(){
      sessionStorage.setItem('userListstate', 0)
      this.$router.push({name: "userList"})
    },
    gotoTableList(){
      sessionStorage.setItem('tableListstate', 0)
      this.$router.push({name: "tableList"})
    },
    goToCreateTable(){
      sessionStorage.setItem('createTableState', 0)
      this.$router.push({name: "createTable"})
    }
  }
}
</script>

<style scoped>
.content {
  width: 100%;
  height: 100%;
}

.el-header {
  background-color: #B3C0D1;
  color: #333;
  line-height: 60px;
}

.el-aside {
  color: #333;
}
</style>