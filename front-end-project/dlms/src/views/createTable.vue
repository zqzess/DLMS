<template>
  <keep-alive>
    <div class="content">
      <el-container style="height: 98%; border: 1px solid #eee;padding: 1%">
        <el-header>
          <el-page-header @back="goBack" content="创建表" style="margin: 20px;"/>
        </el-header>
        <el-main style="width: 90%;margin: 0 auto">
          <div style="width: 90%;margin-bottom: 5px">
            <el-row type="flex" justify="end">
              <el-button type="primary" icon="el-icon-upload" @click="updateForm" v-if="isSave">发布</el-button>
              <el-button type="primary" icon="el-icon-upload" @click="submitForm" v-else>提交</el-button>
              <!--              <el-button type="primary" icon="el-icon-upload" @click="saveForm">保存</el-button>-->
              <el-button @click="addDomain">新增表头</el-button>
            </el-row>
          </div>
          <el-form :model="tableData" ref="tableData" label-width="100px" class="tableData" size="middle">
            <el-form-item label="名称" :rules="{required: true, message: '请输入该表名称', trigger: 'blur' }"
                          :prop="tableData.name">
              <el-input v-model="tableData.name" placeholder="请为表命名" style="width: 300px;margin-right: 20px"/>
            </el-form-item>
            <el-form-item label="身份" :rules="{required: true, message: '请选择表发布对象', trigger: ['blur', 'change']}">
              <el-select v-model="tableData.type" placeholder="请选择发布对象" @change="typeChange(tableData.type)">
                <el-option
                    v-for="(item,index) in userType"
                    :key="index"
                    :label="item.label"
                    :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="学院">
              <el-col :span="6">
                <el-select v-model="tableData.college" placeholder="请选择发布学院" @change="collegeChange(tableData.college)">
                  <el-option
                      v-for="(item,index) in college"
                      :key="index"
                      :value="item">
                  </el-option>
                </el-select>
              </el-col>
              <el-col class="line" :span="1">专业</el-col>
              <el-col :span="6">
                <el-select v-model="tableData.subject" placeholder="请选择发布专业" :disabled="isDisabledMajor"
                           @change="subjectChange(tableData.subject)">
                  <el-option
                      v-for="(item,index) in subject"
                      :key="index"
                      :value="item">
                  </el-option>
                </el-select>
              </el-col>
              <el-col class="line" :span="1" v-if="isStudent">班级</el-col>
              <el-col class="line" :span="1" v-else>部门</el-col>
              <el-col :span="6">
                <el-select v-model="tableData.classname" placeholder="请选择发布班级" v-if="isStudent"
                           :disabled="isDisabledClass">
                  <el-option
                      v-for="(item,index) in classname"
                      :key="index"
                      :value="item">
                  </el-option>
                </el-select>
                <el-select v-model="tableData.department" placeholder="请选择发布部门" v-else>
                  <el-option
                      v-for="(item,index) in department"
                      :key="index"
                      :value="item">
                  </el-option>
                </el-select>
              </el-col>
            </el-form-item>
            <el-form-item
                :rules="{required: true, message: '请填写表头', trigger: 'blur'}"
                v-for="(item, index) in tableData.structure.structure"
                :label="'表头' + (index+1)"
                :key="index">
              <el-col :span="6" style="margin-right: 10px">
                <el-input v-model="tableData.structure.structure[index].label" placeholder="请填写表头"></el-input>
              </el-col>
              <el-col :span="6" style="margin-right: 10px">
                <el-select v-model="tableData.structure.structure[index].type" placeholder="请选择填写类型">
                  <el-option
                      v-for="(item,index) in headerType"
                      :key="index"
                      :label="item.label"
                      :value="item.value">
                  </el-option>
                </el-select>
              </el-col>
              <el-col :span="6">
                <el-button @click.prevent="removeDomain(item)" type="danger" plain>删除</el-button>
              </el-col>
            </el-form-item>
            <!--            <el-form-item>-->
            <!--              <el-button type="primary" @click="submitForm()">提交</el-button>-->
            <!--              <el-button @click="addDomain">新增表头</el-button>-->
            <!--            </el-form-item>-->
          </el-form>
        </el-main>
      </el-container>
    </div>
  </keep-alive>
</template>

<script>
export default {
  name: "createTable",
  data() {
    return {
      tableData: {
        name: '',
        type: '',
        college: '',
        subject: '',
        classname: '',
        department: '',
        // structure: [
        //   {
        //     label: '',
        //     value: 'object1'
        //   }
        // ]
        structure: {
          structure:[
            {
              label: '',
              value: 'object1',
              type: 0
            }
          ]
        }
      },
      userType: [{
        value: 0,
        label: '学生'
      },
        {
          value: 1,
          label: '教师'
        }],
      college: [],
      department: [],
      subject: [],
      classname: [],
      objectCount: 1,
      isStudent: true,
      isDisabledClass: true,
      isDisabledMajor: true,
      isSave: false,
      saveBtn: true,
      dynamicValidateForm: {
        domains: [{
          value: ''
        }],
        email: ''
      },
      headerType:[{
        label:'默认字符串',
        value:0
      },
        {
          label:'学院',
          value:1
        },
        {
          label:'专业',
          value:2
        },
        {
          label:'班级',
          value:3
        },
        {
          label:'部门',
          value:4
        },
        {
          label:'日期',
          value:5
        },
        {
          label:'数字',
          value:6
        }]
    }
  },
  created() {
    let vue = this;
    let createTableState = sessionStorage.getItem('createTableState');
    if (createTableState == null || createTableState == 'undefined') {
      // this.$router.go(-1);//返回上一层
      this.$router.push('/error')
    }
    this.$axios.get('/baseinfo/getBaseInfo', {
      params: {
        method: '6'
      }
    }).then(res => {
      if (res.data.code == 200) {
        let colleges = res.data.colleges;
        let department = res.data.departments;

        for (let i = 0; i < colleges.length; i++) {
          vue.college.push(colleges[i])
        }
        for (let i = 0; i < department.length; i++) {
          vue.department.push(department[i])
        }

        // let result = res.data.content;
        // for (let i = 0; i < result.length; i++) {
        //   // console.log(result[i])
        //   vue.college.push(result[i]);
        // }
      }
    })
  },
  methods: {
    goBack() {
      this.$router.back();
    },
    removeDomain(item) {
      let index = this.tableData.structure.structure.indexOf(item)
      if (index !== -1) {
        this.objectCount = this.objectCount - 1;
        this.tableData.structure.structure.splice(index, 1)
      }
    },
    addDomain() {
      let vue = this;
      vue.objectCount = vue.objectCount + 1
      this.tableData.structure.structure.push({
        label: '',
        value: 'object' + vue.objectCount,
        type:0
      });
    },
    typeChange(type) {
      // console.log(type)
      if (type == 1) {
        this.isStudent = false;
        this.tableData.classname = '';
      } else if (type == 0) {
        this.isStudent = true;
        this.tableData.department = '';
      }
    },
    collegeChange(college) {
      // console.log(college)
      let vue = this;
      this.isDisabledMajor = false;
      this.tableData.subject='';
      this.tableData.classname='';
      this.$axios.post('/baseinfo/getBaseInfoWithName', {
        method: 0,
        name: college
      }).then(res => {
        if (res.data.code == 200) {
          let result = res.data.content;
          vue.subject.length=0;
          vue.subject.push()
          if(result.length!=0)
          {
            for (let i = 0; i < result.length; i++) {
              vue.subject.push(result[i].subject)
            }
          }
        }
      })
    },
    subjectChange(subject) {
      let vue = this;
      this.isDisabledClass = false;
      this.tableData.classname='';
      this.$axios.post('/baseinfo/getBaseInfoWithName', {
        method: 1,
        name: subject
      }).then(res => {
        if (res.data.code == 200) {
          let result = res.data.content;
          vue.classname.length=0;
          vue.classname.push()
          if(result.length!=0)
          {
            for (let i = 0; i < result.length; i++) {
              vue.classname.push(result[i].classname)
            }
          }
        }
      })
    },
    submitForm() {
      // console.log(this.tableData.structure)
      let vue = this;
      if (this.tableData.name === '') {
        this.$message.error("请为表命名");
        return false;
      }
      if (this.tableData.type === '') {
        this.$message.error("请选择发布对象");
        return false;
      }
      let tmp = this.tableData.structure.structure;
      if (tmp.length == 0) {
        this.$message.error("请添加表头");
        return false;
      }
      for (let i = 0; i < tmp.length; i++) {
        if (tmp[i].label === '') {
          this.$message.error("请输入表头");
          return false;
        }
      }
      let userid = localStorage.getItem("userid");
      let data = {
        method: 0,
        userid: userid,
        name: vue.tableData.name,
        type: vue.tableData.type,
        college: vue.tableData.college,
        subject: vue.tableData.subject,
        classname: vue.tableData.classname,
        department: vue.tableData.department,
        state: 1,
        structure: vue.tableData.structure
      }
      vue.$axios.post('/table/setTableInfo', data).then(res => {
        if (res.data.code == 200) {
          this.$notify.success(res.data.msg);
          setTimeout(() => {
            this.$router.back();
          }, 1500);
        } else {
          this.$notify.error(res.data.msg);
        }
      }).catch(res => {
        console.log(res)
        this.$notify.error("操作失败，请稍后重试");
      })

    },
    updateForm() {
    },
    saveForm() {

    }
  }
}
</script>

<style scoped>
.content {
  width: 100%;
  height: 100%;
}
</style>
