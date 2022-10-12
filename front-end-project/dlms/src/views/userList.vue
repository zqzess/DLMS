<template>
  <keep-alive>
    <div>
      <div>
        <!--        <el-input-->
        <!--            style="width: 300px;margin-right: 10px"-->
        <!--            placeholder="请输入姓名或身份类型"-->
        <!--            v-model="search">-->
        <!--          <i slot="prefix" class="el-input__icon el-icon-search"></i>-->
        <!--        </el-input>-->
        <el-autocomplete
            class="inline-input"
            style="width: 300px;margin-right: 10px"
            v-model="search"
            :fetch-suggestions="querySearch"
            placeholder="请输入内容"
            @select="handleSelect"
            @input="inpuEvent"
            @keyup.enter.native="searchEvent"
            clearable
        ></el-autocomplete>
        <el-button type="primary" icon="el-icon-search" @click="searchEvent">搜索</el-button>
        <el-button type="primary" icon="el-icon-document-add" style="float: right" @click="gotoAddUser">添加用户</el-button>
        <!--        <el-button type="primary" icon="el-icon-document-add" style="float: right">批量添加用户</el-button>-->

      </div>
      <el-table
          max-height="550"
          :data="tableData"
          :v-loading="loading"
          border
          style="width: 100%">
        <el-table-column
            prop="id"
            label="学号或工号">
        </el-table-column>
        <el-table-column
            prop="name"
            label="姓名">
        </el-table-column>
        <el-table-column
            prop="gender"
            label="性别">
        </el-table-column>
        <el-table-column
            prop="type"
            label="身份">
        </el-table-column>
        <el-table-column
            prop="state"
            label="状态">
        </el-table-column>
        <el-table-column
            label="操作">
          <template slot-scope="scope">
            <el-button @click="handleEdit(scope.$index,scope.row)" type="primary" size="small" icon="el-icon-view">查看
            </el-button>
            <el-button @click="handleDelete(scope.$index,scope.row)" type="danger" size="small" icon="el-icon-delete">
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </keep-alive>
</template>

<script>

export default {
  name: "userList",
  data() {
    return {
      // tableData: [{
      //   id: '2016-05-02',
      //   name: '王小虎',
      //   gender: '上海',
      //   type: '普陀区',
      //   state: '上海市普陀区金沙江路 1518 弄',
      // }],
      tableData: [],
      search: '',
      loading: true,
      restaurants: [],
    }
  },
  mounted() {
    this.restaurants = this.loadAll();
  },
  created() {
    // let vue=this;
    let userListstate = sessionStorage.getItem('userListstate');
    if (userListstate == null || userListstate == 'undefined') {
      // this.$router.go(-1);//返回上一层
      this.$router.push('/error')
    }
    // let userid = {userid: localStorage.getItem("userid")};
    let type = localStorage.getItem("type")
    if (type == 4) {
      this.getUserList(0, {type: 5})
    } else if (type == 2 || type == 3) {
      this.getUserList(0, {type: 2});
    }
  },
  methods: {
    loadAll() {
      return [
        {
          "value": "学生", "type": 0
        }, {
          "value": "教师", "type": 1
        },
      ]
    },
    querySearch(queryString, cb) {
      let restaurants = this.restaurants;
      let results = queryString ? restaurants.filter(this.createFilter(queryString)) : restaurants;
      // 调用 callback 返回建议列表的数据
      cb(results);
    },
    createFilter(queryString) {
      return (restaurant) => {
        return (restaurant.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0);
      };
    },
    getUserList(method, type) {
      let vue = this;
      let url;
      if (method == 0) {
        url = '/user/getUserList'
      } else if (method == 1) {
        url = '/user/searchUser'
      }
      vue.$axios.get(url, {
        params: type
      }).then(res => {
        // vue.loading = false;
        vue.tableData.length = 0;
        let result = res.data.content
        if (res.data.code == 200) {
          console.log(res.data)
          if (result.length == 0) {
            vue.tableData.length = 0;
            vue.tableData.push();
          }
          for (let i = 0; i < result.length; i++) {
            let tmp;
            if (result[i].type === 0) {
              tmp = "学生"
            } else if (result[i].type === 1) {
              tmp = "教师"
            } else if (result[i].type === 2) {
              tmp = "部门管理员"
            } else if (result[i].type === 3) {
              tmp = "学院管理员"
            } else if (result[i].type === 4) {
              tmp = "超级管理员"
            }
            let tmp2;
            if (result[i].state === 0) {
              tmp2 = "在职"
            } else if (result[i].state === 1) {
              tmp2 = "离职"
            } else if (result[i].state === 2) {
              tmp2 = "未入学"
            } else if (result[i].state === 3) {
              tmp2 = "在读"
            } else if (result[i].state === 4) {
              tmp2 = "毕业"
            }
            let data = {
              id: result[i].id,
              name: result[i].name,
              gender: result[i].gender,
              type: tmp,
              state: tmp2
            }
            vue.tableData.push(data);
          }
          vue.loading = false;
        }
      })
    },
    handleEdit(index, row) {
      console.log(index, row);
      sessionStorage.setItem('manageUserstate', JSON.stringify({tag: 0, method: 0, userid: this.tableData[index].id}))
      this.$router.push({name: "manageUser"})
    },
    handleDelete(index, row) {
      console.log(index, row);
      let vue = this;
      vue.$confirm('此操作将永久删除该用户及与该用户有关的一切', '警告, 是否继续?', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        vue.$axios.get('/user/deleteUser', {
          params: {
            method: 0,
            userid: vue.tableData[index].id
          }
        }).then(res => {
          if (res.data.code == 200) {
            vue.$notify.success(res.data.msg);
            vue.tableData.splice(index, 1)
          } else {
            vue.$notify.error(res.data.msg);
          }
        }).catch(res => {
          console.log(res)
          vue.$notify.error("操作失败，请稍后重试");
        })
      });
    },
    handleSelect(item) {
      console.log(item.type);
      this.getUserList(1, {method: '0', id: item.type})
    },
    searchEvent() {
      // console.log("搜索值:"+this.search)
      if (Number(this.search)) {
        // console.log("成功")
        this.getUserList(1, {method: '1', id: this.search})
      } else {
        // console.log("失败")
        this.getUserList(1,{method:'2',id:0,nameString:this.search})
      }
    },
    inpuEvent() {
      // console.log(this.search)
      if (this.search === '') {
        // console.log("成功le11")
        let type = localStorage.getItem("type")
        if (type == 4) {
          this.getUserList(0,{type: 5})
        } else if (type == 2 || type == 3) {
          this.getUserList(0, {type: 2});
        }
      }
    },
    gotoAddUser() {
      sessionStorage.setItem('manageUserstate', JSON.stringify({tag: 0, method: 1}))
      this.$router.push({name: "manageUser"})
    }
  }
}
</script>

<style scoped>

</style>
