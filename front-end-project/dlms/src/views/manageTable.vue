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
            <el-button @click="handleEdit(scope.$index,scope.row)" type="primary" size="small" icon="el-icon-view">查看
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </keep-alive>

</template>

<script>
export default {
  name: "manageTable",
  data() {
    return {
      // tableData: [{
      //   id: '2016-05-02',
      //   name: '王小虎',
      //   creator: '上海',
      //   createTime: '普陀区',
      //   state: '上海市普陀区金沙江路 1518 弄',
      // }]
      tableData: [],
      loading: true
    }
  },
  created() {
    let vue = this;
    let manageTablestate = sessionStorage.getItem('manageTablestate');
    if (manageTablestate == null || manageTablestate == 'undefined') {
      // this.$router.go(-1);//返回上一层
      this.$router.push('/error')
    }
    let type = localStorage.getItem("type");
    let userid = localStorage.getItem("userid");
    if (type === '4') {
      vue.getTableList(1, 0, 0, 0)
    } else if (type === '2') {
      vue.getTableList(2, userid, 0, 0)
    } else if (type === '3') {
      vue.getTableList(3, userid, 0, 0)
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
        let result=res.data.content;
        if(res.data.code==200)
        {
          for(let i=0;i<result.length;i++)
          {
            let state={}
            if(result[i].state==0)
            {
              state.value=0
              state.label='待发布'
            }else if(result[i].state==1)
            {
              state.value=1
              state.label='已发布'
            }else if(result[i].state==2)
            {
              state.value=2
              state.label='已截止'
            }
            let data={
              id:result[i].id,
              name:result[i].name,
              creator:result[i].creator,
              createTime:result[i].createTime,
              state:{
                value:state.value,
                label:state.label
              }
            }
            vue.tableData.push(data)
          }
          vue.loading=false;
        }
      })
    },
    handleEdit(index,row){
      console.log(index,row)
      let vue=this;
      // console.log(vue.tableData[index].id)
      sessionStorage.setItem('tableViewstate', JSON.stringify({tag:0,id:vue.tableData[index].id}))
      // sessionStorage.setItem('tableViewstate', 0)
      this.$router.push({name: "tableView"})
    }
  }
}
</script>

<style scoped>

</style>