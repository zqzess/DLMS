<template>
  <keep-alive>
    <div class="content"></div>
    <el-container style="height: 98%; border: 1px solid #eee;padding: 1%">
      <el-header>
        <el-page-header @back="goBack" content="用户信息" style="margin: 20px;"/>
      </el-header>
      <div style="width: 90%;margin-bottom: 5px">
        <el-row type="flex" justify="end" v-if="isAdd">
          <el-button type="primary" icon="el-icon-edit" @click="editInfo" :disabled="editBtn" v-if="!editBtn">编辑</el-button>
          <el-button type="primary" icon="el-icon-close" @click="canceleditInfo" v-else>取消</el-button>
          <el-button type="primary" icon="el-icon-upload" @click="uploadInfo" :disabled="uploadBtn">保存</el-button>
        </el-row>
        <el-row type="flex" justify="end" v-else>
          <el-button type="primary" icon="el-icon-upload" @click="submitInfo">提交</el-button>
        </el-row>
      </div>
      <el-main>
        <div style="width: 90%;margin: 0 auto">
          <el-form :model="userinfo" status-icon ref="userForm" label-width="100px" class="userForm">
            <el-form-item label="学号或工号" prop="id">
              <el-input v-model="userinfo.id" :disabled="true" label="学号或工号"></el-input>
            </el-form-item>
            <el-form-item label="姓名" prop="name">
              <el-input v-model="userinfo.name" label="姓名" :disabled="adminEditDisable"></el-input>
            </el-form-item>
            <el-form-item label="性别" prop="gender">
<!--              <el-input v-model="userinfo.gender" :disabled="addDisable" label="性别"></el-input>-->
              <el-select v-model="userinfo.gender" :placeholder="userinfo.gender" :disabled="addDisable">
                <el-option
                    v-for="item in gender"
                    :key="item"
                    :value="item">
                  <span style="float: left">{{ item }}</span>
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="身份证号" prop="peopleid">
              <el-input v-model="userinfo.peopleid" label="身份证号" :disabled="adminEditDisable"></el-input>
            </el-form-item>
            <el-form-item label="身份类型" prop="type">
              <!--              <el-input v-model="labels.type" :disabled="adminEditDisable2" label="身份类型"></el-input>-->
              <el-select v-model="usertype.label" :placeholder="labels.type" :disabled="adminEditDisable2" @change="typechange(usertype.label)">
                <el-option
                    v-for="item in usertype"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  <span style="float: left">{{ item.label }}</span>
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="学院" prop="college">
              <!--              <el-input v-model="userinfo.college" label="学院" :disabled="adminEditDisable"></el-input>-->
              <el-select v-model="userinfo.college" :placeholder="userinfo.college" :disabled="adminEditDisable" @change="collegeChange(userinfo.college)">
                <el-option
                    v-for="item in college"
                    :key="item"
                    :value="item">
                  <span style="float: left">{{ item }}</span>
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="专业" prop="subject">
              <!--              <el-input v-model="userinfo.subject" :disabled="adminEditDisable" label="专业"></el-input>-->
              <el-select v-model="userinfo.subject" :placeholder="userinfo.subject" :disabled="adminEditDisableSubject" @change="subjectChange(userinfo.subject)">
                <el-option
                    v-for="item in subject"
                    :key="item"
                    :value="item">
                  <span style="float: left">{{ item }}</span>
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="部门" prop="department" v-if="isTeacher">
              <!--              <el-input v-model="userinfo.department" :disabled="adminEditDisable" label="部门"></el-input>-->
              <el-select v-model="userinfo.department" :placeholder="userinfo.department" :disabled="adminEditDisable">
                <el-option
                    v-for="item in department"
                    :key="item"
                    :value="item">
                  <span style="float: left">{{ item }}</span>
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="班级" prop="classname" v-else>
              <!--              <el-input v-model="userinfo.classname" :disabled="adminEditDisable" label="班级"></el-input>-->
              <el-select v-model="userinfo.classname" :placeholder="userinfo.classname" :disabled="adminEditDisableClass">
                <el-option
                    v-for="item in classname"
                    :key="item"
                    :value="item">
                  <span style="float: left">{{ item }}</span>
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="职称" prop="brace" v-show="showBrace" >
              <!--              <el-input v-model="userinfo.brace" :disabled="adminEditDisable" label="职称"></el-input>-->
              <el-select v-model="userinfo.brace" :placeholder="userinfo.brace" :disabled="adminEditDisable">
                <el-option
                    v-for="item in brace"
                    :key="item"
                    :value="item">
                  <span style="float: left">{{ item }}</span>
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="状态" prop="state">
              <!--        <el-input v-model="labels.state" :disabled="adminEditDisable" label="状态"></el-input>-->
              <el-select v-model="stateoptions.label" :placeholder="labels.state" :disabled="adminEditDisable"
                         @change="statechange(stateoptions.label)">
                <el-option
                    v-for="(item,index) in stateoptions"
                    :key="index"
                    :label="item.label"
                    :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="手机号" prop="phone">
              <el-input v-model="userinfo.phone" :disabled="adminEditDisable" label="手机号" maxlength="11"
                        show-word-limit onkeyup="value=value.replace(/[^\d]/g,0)"></el-input>
            </el-form-item>
            <el-form-item label="地址" prop="address">
              <el-input v-model="userinfo.address" :disabled="adminEditDisable" label="地址"></el-input>
            </el-form-item>
            <el-form-item label="密码" prop="password">
              <el-input v-model="userinfo.password" :disabled="adminEditDisable" label="密码"></el-input>
            </el-form-item>
          </el-form>
        </div>
      </el-main>
    </el-container>
  </keep-alive>
</template>

<script>
import {mapMutations} from "vuex";

export default {
  name: "manageUser",
  data() {
    return {
      userinfo: {
        method: '1',
        id: '',
        name: '',
        gender: '',
        peopleid: '',
        college: '',
        department: '',
        subject: '',
        classname: '',
        brace: '',
        state: '',
        type: '',
        phone: '',
        address: '',
        password: ''
      },
      labels: {
        state: '',
        type: ''
      },
      // stateoptions: [{
      //   value: 3,
      //   label: '已入学'
      // }, {
      //   value: 4,
      //   label: '已毕业'
      // }, {
      //   value: 2,
      //   label: '未入学'
      // }],
      stateoptions: [{
        value: 3,
        label: '已入学'
      }, {
        value: 4,
        label: '已毕业'
      }, {
        value: 2,
        label: '未入学'
      }],
      gender:["男","女"],
      college: [],
      department: [],
      subject: [],
      classname: [],
      brace: [],
      usertype: [{
        value: 0,
        label: '学生'
      },
        {
          value: 1,
          label: '教师'
        },
        {
          value: 2,
          label: '部门管理员'
        },
        {
          value: 3,
          label: '学院管理员'
        },
        {
          value: 4,
          label: '超级管理员'
        }],
      isTeacher: false,
      showBrace: false,
      adminEditDisable: true,
      adminEditDisable2: true,
      adminEditDisableClass:true,
      adminEditDisableSubject:true,
      addDisable: true,
      editBtn: true,
      uploadBtn: true,
      pageUserType: 0,
      isAdd: false,
      loginUserid: '',
      isUserTypeChanged: false
    }
  },
  created() {
    let vue = this;
    let data = JSON.parse(sessionStorage.getItem('manageUserstate'));
    vue.loginUserid = localStorage.getItem("userid")
    if (data == null || data === 'undefined') {
      // this.$router.go(-1);//返回上一层
      this.$router.push('/error')
    }
    // let manageUserstate = data.tag;
    // let userid = {userid: data.userid};
    this.pageUserType = data.method;
    if (this.pageUserType === 1) {
      //插入新用户
      this.adminEditDisable = false;
      // this.adminEditDisableClass=false;
      // this.adminEditDisableSubject=false;
      this.adminEditDisable2 = false;
      this.addDisable = false
      this.userinfo.method = '0';
    } else {
      //编辑用户
      let userid = {userid: data.userid};
      this.isAdd = true
      // this.adminEditDisable=false;
      // this.adminEditDisable2=false;
      this.editBtn = false;
      // console.log("查询id"+userid.userid)
      vue.$axios.post('/user/getUser', userid).then(res => {
        // console.log(res.data.content)
        if (res.data.code == 200) {
          let result = res.data.content
          vue.userinfo.id = result.id;
          vue.userinfo.name = result.name;
          vue.userinfo.gender = result.gender;
          vue.userinfo.peopleid = result.peopleid;
          vue.userinfo.college = result.college;
          vue.userinfo.department = result.department;
          vue.userinfo.subject = result.subject;
          vue.userinfo.classname = result.classname;
          vue.userinfo.brace = result.brace;
          vue.userinfo.state = result.state;
          vue.userinfo.password = result.password;
          vue.userinfo.phone = result.phone;
          vue.userinfo.address = result.address;
          vue.userinfo.type = result.type;
          if (result.type == 0) {
            vue.labels.type = "学生";
            vue.isTeacher=false;
            vue.showBrace=false;
            vue.stateoptions.push({
              value: 3,
              label: '已入学'
            }, {
              value: 4,
              label: '已毕业'
            }, {
              value: 2,
              label: '未入学'
            });
          } else if (result.type == 1) {
            vue.labels.type = "教师";
            vue.isTeacher=true;
            vue.showBrace=true;
            vue.stateoptions.push({
              value: 1,
              label: '离职'
            }, {
              value: 0,
              label: '在职'
            });
          } else if (result.type == 2) {
            vue.labels.type = "部门管理员";
            vue.isTeacher=true;
            vue.showBrace=true;
            vue.stateoptions.push({
              value: 1,
              label: '离职'
            }, {
              value: 0,
              label: '在职'
            });
          } else if (result.type == 3) {
            vue.labels.type = "学院管理员";
            vue.isTeacher=true;
            vue.showBrace=true;
            vue.stateoptions.push({
              value: 1,
              label: '离职'
            }, {
              value: 0,
              label: '在职'
            });
          } else if (result.type == 4) {
            vue.labels.type = "超级管理员";
            vue.isTeacher=true;
            vue.showBrace=true;
            vue.stateoptions.push({
              value: 1,
              label: '离职'
            }, {
              value: 0,
              label: '在职'
            });
          }
          if (result.state == 0) {
            vue.labels.state = "在职";
          } else if (result.state == 1) {
            vue.labels.state = "离职";
          } else if (result.state == 2) {
            vue.labels.state = "未入学";
          } else if (result.state == 3) {
            vue.labels.state = "已入学";
          } else if (result.state == 4) {
            vue.labels.state = "毕业";
          }
        }
      }).catch(function (error) {
        if (error.response) {
          vue.$router.push({name: 'error', params: {err: "未知错误"}});
        } else {
          console.log('Error', error.message);
          vue.$router.push({name: 'error', params: {err: "503 服务器错误"}});
        }
      });
      // vue.$axios.get('/baseinfo/getBaseInfo', {
      //   params: {
      //     method: '6'
      //   }
      // }).then(res => {
      //   let colleges = res.data.colleges;
      //   let department = res.data.departments;
      //   let subject = res.data.subjetcs;
      //   let classname = res.data.classnames;
      //   let brace = res.data.braces;
      //   // console.log("college:"+colleges)
      //   if (res.data.code === 200) {
      //     for (let i = 0; i < colleges.length; i++) {
      //       vue.college.push(colleges[i])
      //     }
      //     for (let i = 0; i < department.length; i++) {
      //       vue.department.push(department[i])
      //     }
      //     for (let i = 0; i < subject.length; i++) {
      //       vue.subject.push(subject[i].subject)
      //     }
      //     for (let i = 0; i < classname.length; i++) {
      //       vue.classname.push(classname[i].classname)
      //     }
      //     for (let i = 0; i < brace.length; i++) {
      //       vue.brace.push(brace[i])
      //     }
      //   }
      // })
    }

    vue.$axios.get('/baseinfo/getBaseInfo', {
      params: {
        method: '6'
      }
    }).then(res => {
      let colleges = res.data.colleges;
      let department = res.data.departments;
      let subject = res.data.subjetcs;
      let classname = res.data.classnames;
      let brace = res.data.braces;
      // console.log("college:"+colleges)
      if (res.data.code === 200) {
        for (let i = 0; i < colleges.length; i++) {
          vue.college.push(colleges[i])
        }
        for (let i = 0; i < department.length; i++) {
          vue.department.push(department[i])
        }
        for (let i = 0; i < subject.length; i++) {
          vue.subject.push(subject[i].subject)
        }
        for (let i = 0; i < classname.length; i++) {
          vue.classname.push(classname[i].classname)
        }
        for (let i = 0; i < brace.length; i++) {
          vue.brace.push(brace[i])
        }
      }
    })

  },
  methods: {
    ...mapMutations(['clearToken']),
    goBack() {
      this.$router.back();
    },
    editInfo() {
      console.log("身份类型" + this.userinfo.type)
      let type = localStorage.getItem("type");
      if (type == 4) {
        console.log("点击了:" + this.userinfo.type)
        this.adminEditDisable2 = false;
        this.adminEditDisable = false;
        this.adminEditDisableClass=false;
        this.adminEditDisableSubject=false;
      } else {
        this.adminEditDisable = false;
        this.adminEditDisableClass=false;
        this.adminEditDisableSubject=false;
      }
      this.editBtn = true;
      this.uploadBtn = false;
    },
    uploadInfo() {
      let vue = this;
      if(vue.userinfo.name==='')
      {
        vue.$message.error("请填写姓名");
        return false
      }
      if(vue.userinfo.gender==='')
      {
        vue.$message.error("请选择用户性别");
        return false
      }
      if(vue.userinfo.type==='')
      {
        vue.$message.error("请选择身份类型");
        return false
      }
      if(vue.userinfo.state==='')
      {
        vue.$message.error("请选择用户状态");
        return false
      }
      if(vue.userinfo.type==0)
      {
        // console.log("学院:"+vue.userinfo.college)
        if(vue.userinfo.college==='')
        {
          vue.$message.error("请选择学院");
          return false
        }
      }
      // if (this.userinfo.type === 4) {
      //   this.adminEditDisable2 = true;
      //   this.adminEditDisable = true;
      //   this.adminEditDisableClass=true;
      //   this.adminEditDisableSubject=true;
      // } else {
      //   this.adminEditDisable = true;
      //   this.adminEditDisableClass=true;
      //   this.adminEditDisableSubject=true;
      // }

      this.$axios.post('/user/setUser', vue.userinfo).then(res => {
        if (res.data.code === 200) {
          vue.$notify.success(res.data.msg)
          this.adminEditDisable2 = true;
          this.adminEditDisable = true;
          this.adminEditDisableClass=true;
          this.adminEditDisableSubject=true;
          this.editBtn = false;
          this.uploadBtn = true;
          if(vue.isUserTypeChanged && (vue.userinfo.id === Number(vue.loginUserid)))
          {
            setTimeout(() => {
              vue.$alert('你编辑了自己的身份类型，需要重新登陆', '警告', {
                confirmButtonText: '确定',
                callback: action => {
                  console.log(action)
                  vue.isUserTypeChanged = false
                  vue.clearToken();
                  vue.$router.push('/login');
                }
              });
            }, 500);
          }
        } else {
          vue.$notify.error(res.data.msg)
        }
      }).catch(res => {
        console.log(res);
        vue.$notify.error("操作失败");
      })
    },canceleditInfo() {
      // eslint-disable-next-line no-constant-condition
      this.adminEditDisable2 = true;
      this.adminEditDisable = true;
      this.adminEditDisableClass=true;
      this.adminEditDisableSubject=true;
      this.editBtn = false;
      this.uploadBtn = true;
    },
    statechange(state) {
      // console.log("状态"+state)
      this.userinfo.state = state;
    },
    typechange(type){
      // console.log("状态"+this.stateoptions.label)
      this.userinfo.type = type;
      this.userinfo.department = '';
      this.userinfo.classname = '';
      this.stateoptions=[];
      // this.stateoptions.push();
      // this.stateoptions.values='';
      this.labels.state='';
      this.userinfo.state='';
      this.stateoptions.push();
      if(type==0)
      {
        this.isTeacher=false
        // this.userinfo.department = '';
        this.showBrace=false;
        this.stateoptions.push({
          value: 3,
          label: '已入学'
        }, {
          value: 4,
          label: '已毕业'
        }, {
          value: 2,
          label: '未入学'
        });
      }else if(type==1)
      {
        this.isTeacher=true
        this.showBrace=true;
        this.stateoptions.push({
          value: 1,
          label: '离职'
        }, {
          value: 0,
          label: '在职'
        });
        // this.userinfo.classname = '';
      }else if(type==2)
      {
        this.isTeacher=true
        this.showBrace=true;
        this.stateoptions.push({
          value: 1,
          label: '离职'
        }, {
          value: 0,
          label: '在职'
        });
        // this.userinfo.classname = '';
      }else if(type==3)
      {
        this.isTeacher=true
        this.showBrace=true;
        this.stateoptions.push({
          value: 1,
          label: '离职'
        }, {
          value: 0,
          label: '在职'
        });
        // this.userinfo.classname = '';
      }else if(type==4)
      {
        this.isTeacher=true
        this.showBrace=true;
        this.stateoptions.push({
          value: 1,
          label: '离职'
        }, {
          value: 0,
          label: '在职'
        });
        // this.userinfo.classname = '';
      }
      this.isUserTypeChanged = true
    },
    collegeChange(college) {
      // console.log(college)
      let vue = this;
      this.adminEditDisableSubject = false;
      this.userinfo.subject = '';
      this.$axios.post('/baseinfo/getBaseInfoWithName', {
        method: 0,
        name: college
      }).then(res => {
        if (res.data.code == 200) {
          vue.subject.length=0
          vue.subject.push()
          let result = res.data.content;
          for (let i = 0; i < result.length; i++) {
            vue.subject.push(result[i].subject)
          }
        }
      })
    },
    subjectChange(subject) {
      let vue = this;
      this.adminEditDisableClass = false;
      this.userinfo.classname = '';
      this.$axios.post('/baseinfo/getBaseInfoWithName', {
        method: 1,
        name: subject
      }).then(res => {
        if (res.data.code == 200) {
          vue.classname.length=0
          vue.classname.push()
          let result = res.data.content;
          for (let i = 0; i < result.length; i++) {
            vue.classname.push(result[i].classname)
          }
        }
      })
    },
    submitInfo() {
      let vue=this;
      if(vue.userinfo.name==='')
      {
        vue.$message.error("请填写姓名");
        return false
      }
      if(vue.userinfo.gender==='')
      {
        vue.$message.error("请选择用户性别");
        return false
      }
      if(vue.userinfo.type==='')
      {
        vue.$message.error("请选择身份类型");
        return false
      }
      if(vue.userinfo.state==='')
      {
        vue.$message.error("请选择用户状态");
        return false
      }
      if(vue.userinfo.type==0)
      {
        // console.log("学院:"+vue.userinfo.college)
        if(vue.userinfo.college==='')
        {
          vue.$message.error("请选择学院");
          return false
        }
      }
      this.$axios.post('/user/setUser', vue.userinfo).then(res => {
        if (res.data.code === 200) {
          vue.$notify.success(res.data.msg)
          this.$router.back();
        } else {
          vue.$notify.error(res.data.msg)
        }
      }).catch(res => {
        console.log(res);
        vue.$notify.error("操作失败");
      })
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
