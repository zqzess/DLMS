<template>
  <keep-alive>
    <div>
      <template style="width: 100%">
        <el-button style="float: right" type="primary" icon="el-icon-document-add" size="small" @click="showdialog"
                   plain>添加
        </el-button>
        <el-dialog title="创建新的专业" :visible.sync="dialogFormVisible">
          <el-form :model="newMajor">
            <el-form-item label="专业名称" :label-width="formLabelWidth">
              <el-input v-model="newMajor.major" autocomplete="off" placeholder="请输入专业名称"></el-input>
            </el-form-item>
            <el-form-item label="所属学院" :label-width="formLabelWidth">
              <el-select v-model="newMajor.college" placeholder="请选择所属学院">
                <el-option v-for="item in colleges" :key="item" :label="item" :value="item"/>
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
            label="专业"
            style="margin: 0 auto"
            min-width="150">
          <template slot-scope="scope">
            <span v-if="scope.row.isSet">
              <el-input style="margin-left: 10px" v-model="editname.subject"/>
            </span>
            <span v-else>{{ scope.row.subject }}</span>
          </template>
        </el-table-column>
        <el-table-column
            label="学院"
            style="margin: 0 auto"
            min-width="150">
          <template slot-scope="scope">
            <!--            <span v-if="scope.row.isSet">-->
            <!--              <el-input style="margin-left: 10px" v-model="editname.college"/>-->
            <!--            </span>-->
            <!--            <span v-else>{{ scope.row.college }}</span>-->
            <span>{{ scope.row.college }}</span>
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
  name: "major",
  data() {
    return {
      tableData: [],
      editname: {
        subject: '',
        college: '',
        isSet: false
      },
      loading: true,
      isAdd: false,
      dialogFormVisible: false,
      formLabelWidth: '10%',
      newMajor: {
        method: '0',
        major: '',
        college: ''
      },
      colleges: []
    }
  },
  created() {
    let vue = this;
    let collegestate = sessionStorage.getItem('majorstate');
    if (collegestate == null || collegestate == 'undefined') {
      // this.$router.go(-1);//返回上一层
      this.$router.push('/error')
    }
    this.$axios.get('/baseinfo/getBaseInfo', {
      params: {
        method: '2',
      }
    }).then(res => {
      if (res.data.code == 200) {
        let result = res.data.content;
        for (let i = 0; i < result.length; i++) {
          let data = {
            subject: result[i].subject,
            college: result[i].college,
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
      console.log(this.tableData[index].subject)
      for (let i of this.tableData) {
        if (i.isSet) {
          this.$message.warning("请先保存当前编辑项");
          return false;
        }
      }
      this.editname.subject = this.tableData[index].subject
      this.editname.college = this.tableData[index].college
      this.tableData[index].isSet = true
    },
    handleComplete(index, row) {
      console.log(index, row);
      let vue = this;
      if (this.tableData[index].isSet == true) {
        // this.tableData[index].name = this.editname
        // this.tableData[index].isSet = false
        let data = {
          method: '1',
          oldName: this.tableData[index].subject,
          newName: this.editname.subject
        }
        this.$axios.post('/baseinfo/setMajor', data).then(res => {
          if (res.data.code == 200) {
            vue.$notify.success(res.data.msg);
            this.tableData[index].subject = this.editname.subject;
            this.tableData[index].isSet = false;
          } else {
            // vue.$message.error(res.data.msg);
            vue.$notify.error(res.data.msg);
            this.editname.subject = this.tableData[index].subject;
          }
        }).catch(res => {
          console.log(res)
        })
      }
    }, handleCancel(index, row) {
      console.log(index, row);
      if (this.tableData[index].isSet == true) {
        this.tableData[index].isSet = false
      }
    },
    addCollege() {
      let vue = this;
      console.log("tttt:" + this.newMajor.major);
      console.log("tttt:" + this.newMajor.college);
      if(this.newMajor.major===''||this.newMajor.college==='')
      {
        this.$notify.error("请输入内容");
        return false;
      }
      vue.$axios.post('/baseinfo/setMajor', this.newMajor).then(res => {
        if (res.data.code == 200) {
          vue.$notify.success(res.data.msg);
          vue.tableData.push({subject: vue.newMajor.major, college: vue.newMajor.college, isSet: false});
          this.newMajor.major='';
          this.newMajor.college='';
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
          method: '0',
        }
      }).then(res => {
        if (res.data.code == 200) {
          let result = res.data.content;
          for (let i = 0; i < result.length; i++) {
            vue.colleges.push(result[i])
          }
        }
      }).catch(function (err) {
        console.log(err);
        vue.$notify.error("加载学院列表失败");
      });
      this.dialogFormVisible = true;
    }
  }
}
</script>

<style scoped>

</style>