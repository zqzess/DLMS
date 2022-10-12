<template>
  <keep-alive>
<!--      <el-tabs type="border-card" v-model="$route.name" @tab-click="gotoManageTable">-->
    <el-tabs type="border-card" v-model="activeName" @tab-click="gotoManageTable">
        <el-tab-pane label="填写表格" name="editTable" :disabled="editTableDisabled" v-if="isStudent">
          <router-view></router-view>
        </el-tab-pane>
        <el-tab-pane label="填写表格" name="teacherEditTable" :disabled="editTableDisabled" v-else>
          <router-view></router-view>
        </el-tab-pane>
        <el-tab-pane label="管理表格" name="manageTable" :disabled="manageTableDisabled">
          <router-view></router-view>
        </el-tab-pane>
        <el-tab-pane label="我的表格" name="myTable" :disabled="myTableDisbabled" v-if="isAdmin">
          <router-view></router-view>
        </el-tab-pane>
        <el-tab-pane label="我的表格" name="teacherMyTable" :disabled="myTableDisbabled" v-else>
          <router-view></router-view>
        </el-tab-pane>
      </el-tabs>
  </keep-alive>
</template>

<script>
export default {
  name: "tableList",
  data() {
    return {
      editTableDisabled:true,
      manageTableDisabled:true,
      myTableDisbabled:true,
      isStudent:true,
      isAdmin:true,
      activeName:''
    }
  },
  created() {
    let vue=this;
    vue.activeName = vue.$router.history.current.name
    let tableListstate = sessionStorage.getItem('tableListstate');
    if (tableListstate == null || tableListstate == 'undefined') {
      // this.$router.go(-1);//返回上一层
      this.$router.push('/error')
    }
    let type = localStorage.getItem("type")
    if (type === '4') {
      vue.manageTableDisabled=false;
      vue.myTableDisbabled=false;
    } else if (type === '2' || type === '3') {
      vue.manageTableDisabled=false;
      vue.myTableDisbabled=false;
    }else if(type==='0'){
      vue.editTableDisabled=false
      vue.myTableDisbabled=true;
    }else if (type==='1')
    {
      vue.editTableDisabled=false
      // vue.myTableDisbabled=false
      vue.isStudent=false
      vue.isAdmin=false
      vue.$axios.get('/user/getController', {
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
                  vue.myTableDisbabled=false
                }else
                {
                  vue.myTableDisbabled=true
                }
              }
            }
          }
      ).catch(function (err) {
        console.log(err);
        vue.$notify.error("网络异常，获取权限失败")
      });
    }
  },
  methods: {
    gotoManageTable(tab, event){
      // sessionStorage.setItem('manageUserstate', JSON.stringify({tag:0,method:1}))
      console.log(tab.name, event);
      let vue = this;
      if(tab.name==='manageTable')
      {
        sessionStorage.setItem('manageTablestate', 0)
        this.$router.push({name: "manageTable"})
      }else if(tab.name==='myTable')
      {
        vue.activeName = 'myTable'
        sessionStorage.setItem('myTablestate', 0)
        this.$router.push({name: "myTable"})
      }else if(tab.name==='editTable')
      {
        vue.activeName = 'editTable'
        sessionStorage.setItem('editTablestate', 0)
        this.$router.push({name: "editTable"})
      }else if(tab.name==='teacherEditTable')
      {
        vue.activeName = 'teacherEditTable'
        sessionStorage.setItem('editTablestate', 0)
        this.$router.push({name: "teacherEditTable"})
      }else if(tab.name==='teacherMyTable')
      {
        vue.activeName = 'teacherMyTable'
        sessionStorage.setItem('myTablestate', 0)
        this.$router.push({name: "teacherMyTable"})
      }
    },
  }
}
</script>

<style scoped>

</style>
