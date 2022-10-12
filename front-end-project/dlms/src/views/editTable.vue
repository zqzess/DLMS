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
            fixed
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
            <el-button @click="handleEdit(scope.$index,scope.row)" type="primary" size="small" icon="el-icon-edit">填写
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </keep-alive>
</template>

<script>
export default {
  name: "editTable",
  data() {
    return {
      tableData: [],
      loading: true
    }
  },
  created() {
    let vue = this;
    let editTablestate = sessionStorage.getItem('editTablestate');
    if (editTablestate == null || editTablestate == 'undefined') {
      // this.$router.go(-1);//返回上一层
      this.$router.push('/error')
    }
    let type = localStorage.getItem("type");
    let userid = localStorage.getItem("userid");
    if (type === '0') {
      //学生
      vue.getTableList(0, userid, 0, 1)
    } else if (type === '1') {
      //教师
      vue.getTableList(0, userid, 1, 1)
    }
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
          for (let i = 0; i < result.length; i++) {
            let state = {}
            if (result[i].state == 0) {
              state.value = 0
              state.label = '待发布'
            } else if (result[i].state == 1) {
              state.value = 1
              state.label = '已发布'
            } else if (result[i].state == 2) {
              state.value = 2
              state.label = '已截止'
            }
            if (res.data.function != null || '') {
              //如果用户已经填写过该表，去重筛选出为填写的表
              let tableContent = res.data.function;
              let flag = false;
              for (let j = 0; j < tableContent.length; j++) {
                console.log("j:" + tableContent[j].tableId + "   i:" + result[i].id)
                if (tableContent[j].tableId === result[i].id) {
                  console.log("同,打断")
                  flag = true;
                }
              }
              if(flag==false)
              {
                let data = {
                  id: result[i].id,
                  name: result[i].name,
                  creator: result[i].creator,
                  createTime: result[i].createTime,
                  state: {
                    value: state.value,
                    label: state.label
                  }
                }
                vue.tableData.push(data)
              }

            } else {
              let data = {
                id: result[i].id,
                name: result[i].name,
                creator: result[i].creator,
                createTime: result[i].createTime,
                state: {
                  value: state.value,
                  label: state.label
                }
              }
              vue.tableData.push(data)
            }
          }
          vue.loading = false;
        }
      })
    },
    handleEdit(index, row) {
      console.log(index, row)
      let vue = this;
      // console.log(vue.tableData[index].id)
      this.$router.push({name: "editTableContent"})
      sessionStorage.setItem('editTableContentstate', JSON.stringify({tag: 0, id: vue.tableData[index].id}))
      // let type = localStorage.getItem("type");
      // if (type === '0') {
      //   //学生
      //   this.$router.push({name: "tableView"})
      // } else if (type === '1') {
      //   //教师
      //   this.$router.push({name: "tableView"})
      // }
    }
  }
}
</script>

<style scoped>

</style>