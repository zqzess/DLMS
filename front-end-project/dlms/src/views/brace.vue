<template>
  <keep-alive>
    <div>
      <template style="width: 100%">
        <el-button style="float: right" type="primary" icon="el-icon-document-add" size="small" @click="addCollege" plain>添加</el-button>
      </template>
      <el-table
          max-height="550"
          :data="tableData"
          style="width: 100%;"
          v-loading="loading">
        <el-table-column
            label="名称"
            style="margin: 0 auto"
            min-width="300">
          <template slot-scope="scope">
            <span v-if="scope.row.isSet">
              <el-input style="margin-left: 10px" v-model="editname"/>
            </span>
            <span v-else>{{ scope.row.name }}</span>
          </template>
        </el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button v-if="scope.row.isSet" size="mini" type="success" @click="handleComplete(scope.$index, scope.row)">
              <i class="el-icon-check" style="margin-right: 5px"/>保存
            </el-button>
            <el-button v-if="scope.row.isSet" size="mini" type="warning" @click="handleCancel(scope.$index, scope.row)">
              <i class="el-icon-close" style="margin-right: 5px"/>取消
            </el-button>
            <el-button v-else size="mini" @click="handleEdit(scope.$index, scope.row)" type="primary">
              <i class="el-icon-edit" style="margin-right: 5px"/>编辑
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </keep-alive>
</template>

<script>
export default {
  name: "brace",
  data() {
    return {
      tableData: [],
      editname: '',
      loading: true,
      isAdd: false,
      colleges:''
    }
  },
  created() {
    let vue=this;
    let bracestate = sessionStorage.getItem('bracestate');
    if (bracestate == null || bracestate == 'undefined') {
      // this.$router.go(-1);//返回上一层
      this.$router.push('/error')
    }
    this.$axios.get('/baseinfo/getBaseInfo',{
      params: {
        method: '4',
      }
    }).then(res=>{
      if(res.data.code==200)
      {
        let result=res.data.content;
        for(let i=0;i<result.length;i++)
        {
          let data={
            name:result[i],
            isSet:false
          }
          vue.tableData.push(data)
        }
        vue.loading=false;
      }
    }).catch(function (err) {
      console.log(err);
    });
  },
  methods: {
    handleEdit(index, row) {
      console.log(index, row);
      console.log(this.tableData[index].name)
      for (let i of this.tableData) {
        if (i.isSet) {
          this.$message.warning("请先保存当前编辑项");
          return false;
        }
      }
      this.editname = this.tableData[index].name
      this.tableData[index].isSet = true
    },
    handleComplete(index, row) {
      console.log(index, row);
      let vue=this;
      if(this.tableData[index].isSet == true)
      {
        let data={
          method: '1',
          oldName:this.tableData[index].name,
          newName:this.editname
        }
        this.$axios.post('/baseinfo/setBrace',data).then(res=>{
          if(res.data.code==200)
          {
            vue.$notify.success(res.data.msg);
            this.tableData[index].name = this.editname
            this.tableData[index].isSet = false
          }else {
            vue.$notify.error(res.data.msg);
            this.editname=this.tableData[index].name;
          }
        }).catch(res=>{
          console.log(res)
        })
      }
    },handleCancel(index, row)
    {
      console.log(index, row);
      if(this.tableData[index].isSet == true)
      {
        this.tableData[index].isSet = false
      }
    },
    addCollege()
    {
      let vue=this;
      this.$prompt('请输入职称名称', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
      }).then(({ value }) => {
        let data={
          method: '0',
          name: value
        }
        vue.$axios.post('/baseinfo/setBrace',data).then(res=>{
          if(res.data.code==200)
          {
            vue.$notify.success(res.data.msg);
            vue.tableData.push({name: value,isSet: false});
          }else {
            vue.$notify.error(res.data.msg);
          }
        }).catch(res=>{
          console.log(res)
        })
      }).catch(() => {
      });
    },
  }
}
</script>

<style scoped>

</style>