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
<!--            <el-menu :default-active="activeIndex">-->
<!--            <el-menu :default-active="$route.path">-->
              <el-submenu index="1">
                <template slot="title"><i class="el-icon-edit-outline"></i>{{ index1labels.label1 }}</template>
                <el-menu-item-group :disabled="isShowInfo">
                  <template slot="title">{{ index1labels.label2.label2_1 }}</template>
                  <el-menu-item index="userInfo" @click="gotoUserInfoPage" route="/student/userInfo">
<!--                  <el-menu-item index="1-1" @click="gotoUserInfoPage" route="/student/userInfo">-->
<!--                  <el-menu-item index="/student/userInfo" @click="gotoUserInfoPage" route="/student/userInfo">-->
                    {{ index1labels.label2.label2_2 }}
                  </el-menu-item>
                </el-menu-item-group>
                <el-menu-item-group :title="index1labels.label3.label3_1">
                  <el-menu-item index="studentTableList" @click="goToTableList">{{ index1labels.label3.label3_2 }}</el-menu-item>
<!--                  <el-menu-item index="1-2" @click="goToTableList">{{ index1labels.label3.label3_2 }}</el-menu-item>-->
<!--                  <el-menu-item :index="activeIndex2" @click="goToTableList">{{ index1labels.label3.label3_2 }}</el-menu-item>-->
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
<!--                <el-dropdown-item>查看</el-dropdown-item>-->
<!--                <el-dropdown-item>新增</el-dropdown-item>-->
<!--                <el-dropdown-item>删除</el-dropdown-item>-->
                <el-dropdown-item @click.native="logout">登出</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
            <span>{{ username }}</span>
          </el-header>

          <el-main>
            <keep-alive>
              <router-view></router-view>
            </keep-alive>
<!--            <router-view></router-view>-->
            <!--          <el-table :data="tableData">-->
            <!--            <el-table-column prop="date" :label="labels.label1" width="140">-->
            <!--            </el-table-column>-->
            <!--            <el-table-column prop="name" :label="labels.label2" width="120">-->
            <!--            </el-table-column>-->
            <!--            <el-table-column prop="address" :label="labels.label3">-->
            <!--            </el-table-column>-->
            <!--          </el-table>-->
          </el-main>

        </el-container>

      </el-container>

    </div>
  </keep-alive>
</template>

<script>
import {mapMutations} from 'vuex';

export default {
  name: "student",
  data() {
    // const item = {
    //   date: '2016-05-02',
    //   name: 'test',
    //   address: '123'
    // };
    return {
      // tableData: Array(20).fill(item),
      username: '',
      showindextwo: true,
      showindexthree: true,
      labels: {label1: '日期', label2: '姓名', label3: '地址'},
      index1labels: {
        label1: '信息管理',
        label2:
            {
              label2_1: '个人',
              label2_2: '个人信息'
            },
        label3: {
          label3_1: '表格',
          label3_2: '填写表格'
        }
      },
      isShowInfo: false,
      // userinfo: {
      //   id: '',
      //   name: '',
      //   gender:'',
      //   college:'',
      //   department:'',
      //   subject:'',
      //   classname:'',
      //   brace:'',
      //   state:'',
      //   type:'',
      //   phone:'',
      //   address:'',
      //   password:''
      // },
      userinfo: {},
      // activeIndex: '',
      // activeIndex2: '/student/tableList'
    }
  },
  activated () {
    // console.log("刷新")
    // location.reload();
  },
  created() {
    const v = this;
    let type = localStorage.getItem("type")
    if (type == 4||type ==2||type ==3) {
      this.$router.push('/admin')
    }else if(type==2)
    {
      this.$router.push('/teacher')
    }
    let userid = {userid: localStorage.getItem("userid")}
    this.$axios.post('/user/getUser', userid).then(res => {
      // console.log(res)
      if (res.data.code === 200) {
        // console.log("认证通过:  "+res)
        let result = res.data.content
        // console.log(result)
        // console.log("姓名:"+result.name)
        this.username = result.name + "同学,欢迎你!"
        this.userinfo = result;
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
    async gotoUserInfoPage() {
      let v=this;
      // v.activeIndex = '1-1'
      let state1 = 1;
      let state2 = 1;
      let state3 = 1;
      await this.$axios.get('/user/getController', {
        params: {
          method: '0',
          typeid: '0'
        }//params参数必写 , 如果没有参数传{}也可以
      }).then(function (res) {
            // console.log(res);
            if (res.data.code === 200) {
              let result = res.data.content;
              if (result !== '') {
                for (let i = 0; i < result.length; i++) {
                  if (result[i].id == 1010) {
                    state1 = result[i].state;
                  } else if (result.id == 1023) {
                    state2 = result[i].state;
                  } else if (result.id == 1025) {
                    state3 = result[i].state;
                  }
                }

                if (v.userinfo.state == 2) {
                  //未入学
                  sessionStorage.setItem('fstate', JSON.stringify(state3))
                  if(state3==0)
                  {
                    // v.this.isShowInfo=true;
                    v.$router.push({name: "userInfo"})
                    sessionStorage.setItem('user', JSON.stringify(v.userinfo))
                  }else
                  {
                    v.$msgbox("您没有权限，请联系管理员!")
                  }

                  // v.$router.push({name: "userInfo"})
                  // sessionStorage.setItem('user', JSON.stringify(v.userinfo))
                  // sessionStorage.setItem('fstate', JSON.stringify(state3))

                  // v.$router.push({name:'userInfo',params:{user:v.userinfo,fstate3:state3}})
                } else if (v.userinfo.state == 4) {
                  //已毕业
                  sessionStorage.setItem('fstate', JSON.stringify(state2))
                  if(state2==0)
                  {
                    // v.this.isShowInfo=true;
                    v.$router.push({name: "userInfo"})
                    sessionStorage.setItem('user', JSON.stringify(v.userinfo))
                  }else
                  {
                    v.$msgbox("您没有权限，请联系管理员!")
                  }

                  // v.$router.push({name: "userInfo"})
                  // sessionStorage.setItem('user', JSON.stringify(v.userinfo))
                  // sessionStorage.setItem('fstate', JSON.stringify(state2))

                  // v.$router.push({name:'userInfo',params:{user:v.userinfo,fstate2:state2}})
                } else if (v.userinfo.state == 3) {
                  //已入学
                  sessionStorage.setItem('fstate', JSON.stringify(state1))
                  if(state1==0)
                  {
                    // v.this.isShowInfo=true;
                    v.$router.push({name: "userInfo"})
                    sessionStorage.setItem('user', JSON.stringify(v.userinfo))
                  }else
                  {
                    v.$msgbox("您没有权限，请联系管理员!")
                  }

                  // v.$router.push({name: "userInfo"})
                  // sessionStorage.setItem('user', JSON.stringify(v.userinfo))
                  // sessionStorage.setItem('fstate', JSON.stringify(state1))

                  // v.$router.push({name:'userInfo',params:{user:v.userinfo,fstate1:state1}})

                }
              }
            }
          }
      ).catch(function (err) {
          console.log(err);
        });
        // if (this.userinfo.state == 2) {
        //   //未入学
        //   if (state3 == 0) {
        //     await this.$router.push({name: "userInfo"})
        //     sessionStorage.setItem('user', JSON.stringify(this.userinfo))
        //   } else if (state3 == 1) {
        //     this.$message("你未入学，无法操作，请联系管理员!")
        //   }
        // } else if (this.userinfo.state == 4) {
        //   //已毕业
        //   if (state2 == 0) {
        //     await this.$router.push({name: "userInfo"})
        //     sessionStorage.setItem('user', JSON.stringify(this.userinfo))
        //   } else if (state2 == 1) {
        //     this.$message("你已毕业，无法操作，请联系管理员!")
        //   }
        // } else if (this.userinfo.state == 3) {
        //   //已入学
        //   if (state1 == 0) {
        //     await this.$router.push({name: "userInfo"})
        //     sessionStorage.setItem('user', JSON.stringify(this.userinfo))
        //
        //   } else if (state2 == 1) {
        //     this.$message("你没有权限，请联系管理员!")
        //   }

        // this.$router.push({name:'个人信息',params:{user:this.userinfo}})

        // this.$router.push('/student/userInfo')
      // }
    },
    goToTableList(){
      // this.activeIndex = '1-2'
      // this.activeIndex2 = '/student/tableList/editTable'
      sessionStorage.setItem('tableListstate', 0)
      this.$router.push({name: "studentTableList"})
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
