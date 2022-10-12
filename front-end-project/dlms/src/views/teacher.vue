<template>
  <keep-alive>
    <div class="content">
      <el-container style="height: 98%; border: 1px solid #eee;padding: 1%">

        <div style="margin-top: 1%">
          <label style="width: 100%;font-weight: bold;font-size: 26px;padding: 10%">
            管理
          </label>
          <el-aside width="200px" style="background-color: rgb(238, 241, 246); height: 96%;margin-top: 5%">
            <!--            <el-menu :default-active="$route.name" :router="true">-->
            <el-menu :default-active="$route.name">
              <el-submenu index="1">
                <template slot="title"><i class="el-icon-edit-outline"></i>信息管理</template>
                <el-menu-item-group>
                  <template slot="title">个人</template>
                  <el-menu-item index="teacheruserInfo" @click="gotoUserInfoPage" route="/teacher/userInfo">
                    个人信息
                  </el-menu-item>
                </el-menu-item-group>
                <el-menu-item-group title="表格">
                  <el-menu-item index="teacherTableList" @click="goToTableList">表格管理</el-menu-item>
                  <el-menu-item index="createTable" @click="goToCreateTable" v-show="isEnableCreate">创建表格</el-menu-item>
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
  </keep-alive>
</template>

<script>
import {mapMutations} from 'vuex';
export default {
  name: "teacher",
  data() {
    return {
      showindextwo: true,
      showindexthree: true,
      isEnableCreate: false,
      username:'',
      userinfo:{}
    }
  },
  created() {
    let v=this;
    let type = localStorage.getItem("type")
    if (type == 4||type ==2||type ==3) {
      this.$router.push('/admin')
    }else if(type==0)
    {
      this.$router.push('/student')
    }
    let userid = {userid: localStorage.getItem("userid")}
    this.$axios.post('/user/getUser', userid).then(res => {
      // console.log(res)
      if (res.data.code === 200) {
        // console.log("认证通过:  "+res)
        let result = res.data.content
        // console.log(result)
        // console.log("姓名:"+result.name)
        v.username = result.name + "老师,欢迎你!"
        v.userinfo = result;
      } else if (res.data.code == 401) {
        console.log("错误")
        this.$message(res.data.msg);
        setTimeout(() => {
          this.$router.push({path: "/login"}); // 强制切换当前路由 path
          // loading.close();
        }, 3000)
      }
    }).catch(function (error) {
      if (error.response) {
        // 请求已发出，但服务器响应的状态码不在 2xx 范围内
        // console.log("错误123124124")
        console.log(error.response.data);
        console.log(error.response.status);
        console.log(error.response.headers);
        v.$router.push({name: 'error', params: {err: "未知错误"}});
      } else {
        console.log('Error', error.message);
        v.$router.push({name: 'error', params: {err: "503 服务器错误"}});
      }
      console.log(error.config);
    });
    this.$axios.get('/user/getController', {
      params: {
        method: '1',
        typeid: '122'
      }//params参数必写 , 如果没有参数传{}也可以
    }).then(function (res) {
          // console.log(res);
          if (res.data.code === 200) {
            let result = res.data.content;
            if (result !== '') {
              if(result.state==0)
              {
                v.isEnableCreate=true;
              }else
              {
                v.$notify.error("您没有权限创建管理表格，请联系管理员修改权限!")
              }
            }
          }
        }
    ).catch(function (err) {
      console.log(err);
      v.$notify.error("网络异常，获取权限失败")
    });
  },
  methods: {
    ...mapMutations(['clearToken']),
    async gotoUserInfoPage(){
      let v=this;
      await this.$axios.get('/user/getController', {
        params: {
          method: '1',
          typeid: '110'
        }//params参数必写 , 如果没有参数传{}也可以
      }).then(function (res) {
            // console.log(res);
            if (res.data.code === 200) {
              let result = res.data.content;
              if (result !== '') {
                if(result.state==0)
                {
                  sessionStorage.setItem('fstate', JSON.stringify(result.state))
                  sessionStorage.setItem('user', JSON.stringify(v.userinfo))
                  v.$router.push({name: "teacheruserInfo"})
                }else
                {
                  v.$msgbox("您没有权限，请联系管理员!")
                }
                  // v.$router.push({name: "userInfo"})
                  // sessionStorage.setItem('user', JSON.stringify(v.userinfo))
                  // sessionStorage.setItem('fstate', JSON.stringify(state3))

                  // v.$router.push({name:'userInfo',params:{user:v.userinfo,fstate3:state3}})
              }
            }
          }
      ).catch(function (err) {
        console.log(err);
      });
    },
    logout(){
      this.clearToken();
      setTimeout(() => {
        this.$router.push('/login');
        // loading.close();
      }, 500)
    },
    goToTableList(){
      sessionStorage.setItem('tableListstate', 0)
      this.$router.push({name: "teacherTableList"})
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