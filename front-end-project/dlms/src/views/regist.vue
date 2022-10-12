<template>
  <div class="page">
    <div class="background">
      <div class="pageview">
        <div class="title">欢迎使用朵蓝信息管理系统</div>
        <div class="registform">
          <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="auto" class="ruleForm">
            <el-form-item prop="id">
              <el-input type="username" v-model="ruleForm.id" autocomplete="off" placeholder="输入姓名"></el-input>
            </el-form-item>
            <el-form-item prop="pass">
              <el-input type="password" v-model="ruleForm.pass" autocomplete="off" placeholder="输入密码"></el-input>
            </el-form-item>
            <el-form-item prop="checkPass">
              <el-input type="password" v-model="ruleForm.checkPass" autocomplete="off" placeholder="确认密码"></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="submitForm('ruleForm')" style="width:100%;">注册</el-button>
              <!--              <el-button @click="resetForm('ruleForm')">重置</el-button>-->
            </el-form-item>
          </el-form>
          <!--          <router-link to="/regist">没有账号?创建一个</router-link>-->
          <p class="login" @click="gotoLogin">已有账号?去登陆</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "regist",
  data() {
    const checkId = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('账号不能为空'));
      }
      setTimeout(() => {
        var numReg = /^[0-9]*$/
        var numRe = new RegExp(numReg)
        if (!numRe.test(value)) {
          callback(new Error('账号必须为数字'));
        }
      }, 1000);
    };
    const checkPwd = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'));
      } else {
        if (this.ruleForm.checkPass !== '') {
          this.$refs.ruleForm.validateField('checkPass');
        }
        callback();
      }
    };
    const checkPwd2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'));
      } else if (value !== this.ruleForm.pass) {
        callback(new Error('两次输入密码不一致!'));
      } else {
        callback();
      }
    };
    return {
      // imgBgSrc:require('@/assets/bg_login.svg'),
      ruleForm: {
        id: '',
        pass: '',
        checkPass: ''
      },
      rules: {
        id: [
          { validator: checkId, trigger: 'blur' }
        ],
        pass: [
          { validator: checkPwd, trigger: 'blur' }
        ],
        checkPass: [
          { validator: checkPwd2, trigger: 'blur' }
        ]
      }
    };
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          alert('提交成功!');
        } else {
          console.log('提交错误!!');
          return false;
        }
      });
    },
    gotoLogin(){
      this.$router.push({
        path: "/login"
      });
    }
  }
}
</script>

<style scoped>
.page{
  background: url("~@/assets/css/global.css");
  position: fixed;
}
.background{
  background: url("~@/assets/bg_login.svg");
  width:100%;
  height: 100%;  /**宽高100%是为了图片铺满屏幕 */
  /*z-index:-1;*/
  position: fixed;
  background-size: 100% 100%;
  display: flex;
  align-items: center;
  text-align:center;
  horiz-align: center;
}
.pageview{
  /*z-index:1;*/
  width: auto;
  height: auto;
  padding: 2%;
  margin: 0 auto;
  background: whitesmoke;
  horiz-align: center;
  border-radius: 16px;
}
.title{
  font-size: 20px;
  font-weight: bold;
  margin: 10px;
}
.login{
  margin-top: 10px;
  font-size: 14px;
  line-height: 22px;
  color: #1ab2ff;
  cursor: pointer;
  text-align: left;
  text-indent: 8px;
  width: 160px;
}
</style>