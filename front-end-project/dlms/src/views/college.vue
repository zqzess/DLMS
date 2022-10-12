<template>
  <keep-alive>
    <div>
      <template style="width: 100%">
<!--        <el-button style="float: right" type="success" icon="el-icon-s-promotion" size="small" v-if="isAdd" @click="submit" plain>提交</el-button>-->
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
            <!--            <i class="el-icon-time"></i>-->
            <!--            <span style="margin-left: 10px">{{ scope.row.name }}</span>-->
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
<!--      <el-col>-->
<!--        <div :span="24" class="el-table-add-row" style="width: 100%;" @click="addMasterUser()">-->
<!--          <span>+ 添加</span></div>-->
<!--      </el-col>-->
    </div>
  </keep-alive>
</template>

<script>
export default {
  name: "college",
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
    let collegestate = sessionStorage.getItem('collegestate');
    if (collegestate == null || collegestate == 'undefined') {
      // this.$router.go(-1);//返回上一层
      this.$router.push('/error')
    }
    this.$axios.get('/baseinfo/getBaseInfo',{
      params: {
        method: '0',
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
        // this.tableData[index].name = this.editname
        // this.tableData[index].isSet = false
        let data={
          method: '1',
          oldName:this.tableData[index].name,
          newName:this.editname
        }
        this.$axios.post('/baseinfo/setCollege',data).then(res=>{
          if(res.data.code==200)
          {
            // vue.$message.success(res.data.msg);
            vue.$notify.success(res.data.msg);
            this.tableData[index].name = this.editname
            this.tableData[index].isSet = false
          }else {
            // vue.$message.error(res.data.msg);
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
      // if(this.isAdd)
      // {
      //   this.isAdd=false;
      // }else
      // {
      //   this.isAdd=true;
      //   let college={name:"",isSet: true}
      //   this.tableData.push(college)
      // }
      this.$prompt('请输入学院名称', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        // inputPattern: /[\w!#$%&'*+/=?^_`{|}~-]+(?:\.[\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\w](?:[\w-]*[\w])?\.)+[\w](?:[\w-]*[\w])?/,
        // inputErrorMessage: '邮箱格式不正确'
      }).then(({ value }) => {
        let data={
          method: '0',
          name: value
        }
        vue.$axios.post('/baseinfo/setCollege',data).then(res=>{
          if(res.data.code==200)
          {
            // vue.$message.success(res.data.msg);
            vue.$notify.success(res.data.msg);
            vue.tableData.push({name: value,isSet: false});
          }else {
            // vue.$message.error(res.data.msg);
            vue.$notify.error(res.data.msg);
          }
        }).catch(res=>{
          console.log(res)
        })
      }).catch(() => {
      });
    },
    // submit()
    // {
    //   if(this.isAdd)
    //   {
    //     this.isAdd=false;
    //   }else
    //   {
    //     this.isAdd=true;
    //   }
    // }
  }
}
</script>

<style scoped>
.el-table-add-row{
  border: 1px dashed darkgrey;
  border-radius: 4px;
  height: 30px;
  text-align: center;
  justify-items: center;
}
</style>