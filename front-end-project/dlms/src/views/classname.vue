<template>
  <keep-alive>
    <div>
      <template style="width: 100%">
        <el-button style="float: right" type="primary" icon="el-icon-document-add" size="small" @click="showdialog"
                   plain>添加
        </el-button>
        <el-dialog title="创建新的班级" :visible.sync="dialogFormVisible">
          <el-form :model="newClass">
            <el-form-item label="班级名称" :label-width="formLabelWidth">
              <el-input v-model="newClass.classname" autocomplete="off" placeholder="请输入班级名称"></el-input>
            </el-form-item>
            <el-form-item label="所属专业" :label-width="formLabelWidth">
              <el-select v-model="newClass.subject" placeholder="请选择所属专业">
                <el-option v-for="item in majors" :key="item" :label="item.subject+'\t\t'+item.college" :value="item.subject"/>
              </el-select>
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button @click="dialogFormVisible = false">取 消</el-button>
            <el-button type="primary" @click="addCollege">确 定</el-button>
          </div>
        </el-dialog>
      </template>
      <el-table
          max-height="550"
          :data="tableData"
          style="width: 100%;"
          v-loading="loading">
        <el-table-column
            label="班级"
            style="margin: 0 auto"
            min-width="150">
          <template slot-scope="scope">
            <span v-if="scope.row.isSet">
              <el-input style="margin-left: 10px" v-model="editname.classname"/>
            </span>
            <span v-else>{{ scope.row.classname }}</span>
          </template>
        </el-table-column>
        <el-table-column
            label="专业"
            style="margin: 0 auto"
            min-width="150">
          <template slot-scope="scope">
            <!--            <span v-if="scope.row.isSet">-->
            <!--              <el-input style="margin-left: 10px" v-model="editname.college"/>-->
            <!--            </span>-->
            <!--            <span v-else>{{ scope.row.college }}</span>-->
            <span>{{ scope.row.subject }}</span>
          </template>
        </el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button v-if="scope.row.isSet" size="mini" type="success"
                       @click="handleComplete(scope.$index, scope.row)">
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
  name: "classname",
  data() {
    return {
      tableData: [],
      editname: {
        classname: '',
        subject: '',
        isSet: false
      },
      loading: true,
      isAdd: false,
      dialogFormVisible: false,
      formLabelWidth: '10%',
      newClass: {
        method: '0',
        classname: '',
        subject: ''
      },
      majors: []
    }

  },
  created() {
    let vue=this;
    let classstate = sessionStorage.getItem('classstate');
    if (classstate == null || classstate === 'undefined') {
      // this.$router.go(-1);//返回上一层
      this.$router.push('/error')
    }
    this.$axios.get('/baseinfo/getBaseInfo', {
      params: {
        method: '3',
      }
    }).then(res => {
      if (res.data.code === 200) {
        let result = res.data.content;
        for (let i = 0; i < result.length; i++) {
          let data = {
            classname: result[i].classname,
            subject: result[i].subject,
            isSet: false
          }
          vue.tableData.push(data)
        }
        vue.loading = false;
      }
    }).catch(function (err) {
      console.log(err);
    });
  },
  methods: {
    handleEdit(index, row) {
      console.log(index, row);
      console.log(this.tableData[index].classname)
      for (let i of this.tableData) {
        if (i.isSet) {
          this.$message.warning("请先保存当前编辑项");
          return false;
        }
      }
      this.editname.classname = this.tableData[index].classname
      this.editname.subject = this.tableData[index].subject
      this.tableData[index].isSet = true
    },
    handleComplete(index, row) {
      console.log(index, row);
      let vue = this;
      if (this.tableData[index].isSet === true) {
        // this.tableData[index].name = this.editname
        // this.tableData[index].isSet = false
        let data = {
          method: '1',
          oldName: this.tableData[index].classname,
          newName: this.editname.classname
        }
        this.$axios.post('/baseinfo/setClass', data).then(res => {
          if (res.data.code === 200) {
            vue.$notify.success(res.data.msg);
            this.tableData[index].classname = this.editname.classname;
            this.tableData[index].isSet = false;
          } else {
            // vue.$message.error(res.data.msg);
            vue.$notify.error(res.data.msg);
            this.editname.classname = this.tableData[index].classname;
          }
        }).catch(res => {
          console.log(res)
        })
      }
    }, handleCancel(index, row) {
      console.log(index, row);
      if (this.tableData[index].isSet === true) {
        this.tableData[index].isSet = false
      }
    },
    addCollege() {
      let vue = this;
      // console.log("tttt:" + this.newClass.classname);
      // console.log("tttt:" + this.newClass.major);
      if(this.newClass.classname===''||this.newClass.subject==='')
      {
        this.$notify.error("请输入内容");
        return false;
      }
      vue.$axios.post('/baseinfo/setClass', this.newClass).then(res => {
        if (res.data.code === 200) {
          vue.$notify.success(res.data.msg);
          vue.tableData.push({classname: vue.newClass.classname, subject: vue.newClass.subject, isSet: false});
          this.newClass.classname='';
          this.newClass.subject='';
        } else {
          vue.$notify.error(res.data.msg);
        }
        vue.dialogFormVisible = false
      }).catch(res => {
        console.log(res)
      });
    },
    showdialog() {
      let vue = this;
      this.$axios.get('/baseinfo/getBaseInfo', {
        params: {
          method: '2',
        }
      }).then(res => {
        if (res.data.code === 200) {
          let result = res.data.content;
          for (let i = 0; i < result.length; i++) {
            vue.majors.push({"subject":result[i].subject,"college":result[i].college})
          }
        }
      }).catch(function (err) {
        console.log(err);
        vue.$notify.error("加载专业列表失败");
      });
      this.dialogFormVisible = true;
    }
  }
}
</script>

<style scoped>

</style>