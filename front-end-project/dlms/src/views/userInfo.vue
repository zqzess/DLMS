<template>
  <div style="width: 90%;margin: 0 auto">
    <div style="margin-left: 100px">
      <el-button type="primary" icon="el-icon-edit" @click="editInfo" :disabled="editBtn" v-if="!editBtn">编辑</el-button>
      <el-button type="primary" icon="el-icon-close" @click="canceleditInfo" v-else>取消</el-button>
      <el-button type="primary" icon="el-icon-upload" @click="uploadInfo" :disabled="uploadBtn">保存</el-button>
    </div>
    <el-form :model="userinfo" status-icon ref="userForm" label-width="100px" class="userForm">
      <el-form-item label="学号或工号" prop="id">
        <el-input v-model="userinfo.id" :disabled="true" label="学号或工号"></el-input>
      </el-form-item>
      <el-form-item label="姓名" prop="name">
        <el-input v-model="userinfo.name" :disabled="adminEditDisable" label="姓名"></el-input>
      </el-form-item>
      <el-form-item label="性别" prop="gender">
        <el-input v-model="userinfo.gender" :disabled="true" label="性别"></el-input>
      </el-form-item>
      <el-form-item label="身份证号" prop="poepleid">
        <el-input v-model="userinfo.peopleid" :disabled="userEditDisable" label="身份证号"></el-input>
      </el-form-item>
      <el-form-item label="学院" prop="college">
        <el-input v-model="userinfo.college" :disabled="adminEditDisable" label="学院"></el-input>
      </el-form-item>
      <el-form-item label="部门" prop="department">
        <el-input v-model="userinfo.department" :disabled="adminEditDisable" label="部门"></el-input>
      </el-form-item>
      <el-form-item label="专业" prop="subject">
        <el-input v-model="userinfo.subject" :disabled="adminEditDisable" label="专业"></el-input>
      </el-form-item>
      <el-form-item label="班级" prop="classname" v-show="showClassname">
        <el-input v-model="userinfo.classname" :disabled="adminEditDisable" label="班级"></el-input>
      </el-form-item>
      <el-form-item label="职称" prop="brace" v-show="showBrace">
        <el-input v-model="userinfo.brace" :disabled="adminEditDisable" label="职称"></el-input>
      </el-form-item>
      <el-form-item label="状态" prop="state">
<!--        <el-input v-model="labels.state" :disabled="adminEditDisable" label="状态"></el-input>-->
        <el-select v-model="stateoptions.label" :placeholder="value.label" :disabled="adminEditDisable" @change="statechange(stateoptions.label)">
          <el-option
              v-for="item in stateoptions"
              :key="item.value"
              :label="item.label"
              :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="身份类型" prop="type">
        <el-input v-model="labels.type" :disabled="adminEditDisable2" label="身份类型"></el-input>
      </el-form-item>
      <el-form-item label="手机号" prop="phone">
        <el-input v-model.number="userinfo.phone" :disabled="userEditDisable" label="手机号" maxlength="11"
                  onkeyup="value=value.replace(/[^\d]/g,0)" show-word-limit></el-input>
      </el-form-item>
      <el-form-item label="地址" prop="address">
        <el-input v-model="userinfo.address" :disabled="userEditDisable" label="地址"></el-input>
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <el-input v-model="userinfo.password" :disabled="userEditDisable" label="密码"></el-input>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  name: "userInfo",
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
      stateoptions: [{
        value: 3,
        label: '已入学'
      }, {
        value: 4,
        label: '已毕业'
      }, {
        value: 2,
        label: '未入学'
      }, {
        value: 1,
        label: '离职'
      }, {
        value: 0,
        label: '在职'
      }],
      value: {
        label:''
      },
      showClassname: true,
      showBrace: true,
      userEditDisable: true,
      adminEditDisable: true,
      adminEditDisable2:true,
      editBtn: true,
      uploadBtn: true,
    }
  },
  async created() {
    // let user=this.$route.params.user;
    // let fstate=this.$route.params.fstate;//已入学
    let vue=this;
    let user = JSON.parse(sessionStorage.getItem('user'))
    let fstate = JSON.parse(sessionStorage.getItem('fstate'));//所属权限状态
    let userid = {userid: localStorage.getItem("userid")}

    console.log("用户信息1:" + user)
    console.log("状态1:" + fstate)
    if (fstate === 1) {
      this.$message("您没有权限，请联系管理员!")
      setTimeout(() => {
        this.$router.back();//返回上一层
      }, 1000)
      // this.$router.go(-1);//返回上一层
    }
    if (user == null || user === 'undefined') {
      // this.$router.go(-1);//返回上一层
      await vue.$router.push('/error')
    } else {
      this.$axios.post('/user/getUser', userid).then(res => {
        if (res.data.code === 200) {
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
          vue.userinfo.password=result.password;
          vue.userinfo.phone=result.phone;
          vue.userinfo.address=result.address;
          if (result.state === 0) {
            this.labels.state = "在职";
            this.value.label="在职";
          } else if (result.state === 1) {
            this.labels.state = "离职";
            this.value.label="离职";
          } else if (result.state === 2) {
            this.labels.state = "未入学";
            this.value.label="未入学";
          } else if (result.state === 3) {
            this.labels.state = "已入学";
            this.value.label="已入学";
          } else if (result.state === 4) {
            this.labels.state = "毕业";
            this.value.label="毕业";
          }
          vue.userinfo.type = result.type;
        }
      }).catch(function (error) {
        if (error.response) {
          vue.$router.push({name: 'error', params: {err: "未知错误"}});
        } else {
          console.log('Error', error.message);
          vue.$router.push({name: 'error', params: {err: "503 服务器错误"}});
        }
      });
      if (user.type === 0) {
        this.labels.type = "学生";
        this.showBrace = false;
        vue.$axios.get('/user/getController',{params:{
          method:'0',
            typeid:'0'
          }}).then(res=>{
            if(res.data.code===200)
            {
              let result=res.data.content
              for(let i=0;i<result.length;i++)
              {
                if(result[i].id===1011&&user.state===3&&result[i].state===0)
                {
                  vue.editBtn=false;
                }else if(result[i].id===1024&&user.state===4&&result[i].state===0)
                {
                  vue.editBtn=false;
                }else if(result[i].id===1026&&user.state===2&&result[i].state===0)
                {
                  vue.editBtn=false;
                }
              }
            }
        })
      } else if (user.type === 1) {
        this.labels.type = "教师";
        this.showClassname = false;
        vue.$axios.get('/user/getController',{params:{
            method:'0',
            typeid:'1'
          }}).then(res=>{
          if(res.data.code===200)
          {
            let result=res.data.content
            for(let i=0;i<result.length;i++)
            {
              if(result[i].id===122&&result[i].state===0)
              {
                vue.editBtn=false;
              }
            }
          }
        })
      } else if (user.type === 2) {
        this.labels.type = "部门管理员";
        this.showBrace = false;
        this.showClassname = false;
      } else if (user.type === 3) {
        this.labels.type = "学院管理员";
        this.showBrace = false;
        this.showClassname = false;
      } else if (user.type === 4) {
        this.labels.type = "学校管理员";
        this.showBrace = false;
        this.showClassname = false;
      }
      // this.userinfo.phone = user.phone;
      // this.userinfo.address = user.address;
      // this.userinfo.password = user.password;
    }
  },
  methods: {
    editInfo() {
      // eslint-disable-next-line no-constant-condition
      if (this.userinfo.type === 0 || 1) {
        this.userEditDisable = false;
      } else {
        this.userEditDisable = false;
        this.adminEditDisable = false;
      }
      this.editBtn = true;
      this.uploadBtn = false;
    },
    uploadInfo() {
      let vue=this;
      // eslint-disable-next-line no-constant-condition
      if (this.userinfo.type === 0 || 1) {
        this.userEditDisable = true;
      } else {
        this.userEditDisable = true;
        this.adminEditDisable = true;
      }
      this.editBtn = false;
      this.uploadBtn = true;
      this.$axios.post('/user/setUser',vue.userinfo).then(res=>{
        if(res.data.code===200)
        {
          vue.$notify.success(res.data.msg)
        }else {
          vue.$notify.error(res.data.msg)
        }
      }).catch(res=>{
        console.log(res);
        vue.$notify.error("操作失败");
      })
      // console.log()
    },canceleditInfo() {
      // eslint-disable-next-line no-constant-condition
      if (this.userinfo.type === 0 || 1) {
        this.userEditDisable = true;
      } else {
        this.userEditDisable = true;
        this.adminEditDisable = true;
      }
      this.editBtn = false;
      this.uploadBtn = true;
    }
    ,statechange(state)
    {
      this.userinfo.state=state;
    }
  }
}
</script>

<style scoped>

</style>
