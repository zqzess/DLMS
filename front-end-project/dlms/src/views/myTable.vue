<template>
  <keep-alive>
    <div>
      <el-table
          max-height="450"
          :data="tableData"
          v-loading="loading"
          border
          style="width: 100%">
        <el-table-column
            prop="id"
            label="编号">
        </el-table-column>
        <el-table-column
            prop="name"
            label="名称">
        </el-table-column>
        <el-table-column
            prop="creator"
            label="创建人">
        </el-table-column>
        <el-table-column
            prop="createTime"
            label="创建日期">
        </el-table-column>
        <el-table-column
            prop="state.label"
            label="填写状态">
        </el-table-column>
        <el-table-column
            label="操作">
          <template slot-scope="scope">
            <el-button @click="handleEdit(scope.$index,scope.row)" type="primary" size="mini" icon="el-icon-view"
                       circle/>
            <el-button @click="handleEnd(scope.$index,scope.row)" type="warning" size="mini" icon="el-icon-s-promotion"
                       :disabled="scope.row.isDisabled" circle v-if="scope.row.isPublish"/>
            <el-button @click="handleEnd(scope.$index,scope.row)" type="warning" size="mini"
                       icon="el-icon-switch-button" :disabled="scope.row.isDisabled2" circle v-else/>
            <el-button @click="handleDelete(scope.$index,scope.row)" type="danger" size="mini" icon="el-icon-delete"
                       circle/>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </keep-alive>
</template>

<script>
export default {
  name: "myTable",
  data() {
    return {
      tableData: [],
      loading: false,
      // isDisabled:true,
      // isDisabled2:true,
      // isPublish:false

    }
  },
  created() {
    let vue = this;
    let myTablestate = sessionStorage.getItem('myTablestate');
    if (myTablestate == null || myTablestate == 'undefined') {
      // this.$router.go(-1);//返回上一层
      this.$router.push('/error')
    }
    // let type = localStorage.getItem("type");
    let userid = localStorage.getItem("userid");
    // if (type === '4') {
    //   vue.getTableList(4, userid, 0, 0)
    // } else if (type === '2') {
    //   vue.getTableList(4, userid, 0, 0)
    // } else if (type === '3') {
    //   vue.getTableList(4, userid, 0, 0)
    // }

    vue.getTableList(4, userid, 0, 0)
  },
  methods: {
    getTableList(method, userid, type, state) {
      let vue = this;
      vue.$axios.get('/table/getTableList', {
        params: {
          method: method,
          userid: userid,
          type: type,
          state: state
        }
      }).then(res => {
        // console.log(res);
        let result = res.data.content;
        if (res.data.code == 200) {
          let btnstate = true;
          for (let i = 0; i < result.length; i++) {
            let state = {}
            if (result[i].state == 0) {
              state.value = 0
              state.label = '待发布'
              btnstate = true;
            } else if (result[i].state == 1) {
              state.value = 1
              state.label = '已发布'
              btnstate = false;
            } else if (result[i].state == 2) {
              state.value = 2
              state.label = '已截止'
              btnstate = true;
            }
            let data = {
              id: result[i].id,
              name: result[i].name,
              creator: result[i].creator,
              createTime: result[i].createTime,
              state: {
                value: state.value,
                label: state.label
              },
              isDisabled: true,
              isDisabled2: btnstate,
              isPublish: false
            }
            vue.tableData.push(data)
          }
          vue.loading = false;
        }
      })
    },
    handleEdit(index, row) {
      console.log(index, row)
      let vue = this;
      // console.log(vue.tableData[index].id)
      sessionStorage.setItem('tableViewstate', JSON.stringify({tag: 0, id: vue.tableData[index].id}))
      // sessionStorage.setItem('tableViewstate', 0)
      this.$router.push({name: "tableView"})
    },
    handleEnd(index, row) {
      console.log(index, row)
      let vue = this;
      vue.$axios.get('/table/updateTableInfo', {
        params: {
          method: 0,
          tableid: vue.tableData[index].id,
          state: 2
        }
      }).then(res=>{
        if(res.data.code==200)
      {
        vue.tableData[index].isDisabled2=true;
        vue.$notify.success(res.data.msg);
      }else
        {
          vue.$notify.error(res.data.msg);
        }
      }).catch(res=>{
        console.log(res)
        vue.$notify.error("操作失败，请稍后重试!");
      })
    },
    handleDelete(index, row) {
      console.log(index, row)
      let vue = this;
      this.$confirm('此操作将永久删除该表及与该表有关的所有数据', '警告, 是否继续?', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // let userid = localStorage.getItem("userid");
        vue.$axios.get('/table/deleteTable', {
          params: {
            method: 0,
            tableid: vue.tableData[index].id
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
    }
  }
}
</script>

<style scoped>

</style>
