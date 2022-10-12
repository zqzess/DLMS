<template>
  <div class="page">
    <div class="background">
<!--      <img :src="imgBgSrc" width="100%" height="100%" alt="center" />-->
<!--      <router-view></router-view>-->
      <div class="pageview">
        <div class="title">欢迎使用朵蓝信息管理系统</div>
        <div class="loginform">
          <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="auto" class="ruleForm">
            <el-form-item label="账户" prop="id">
              <el-input type="username" v-model="ruleForm.id" autocomplete="off" placeholder="输入学号或工号" clearable></el-input>
            </el-form-item>
            <el-form-item label="密码" prop="pass">
              <el-input type="password" v-model="ruleForm.pass" autocomplete="off" placeholder="输入密码" clearable></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="submitForm('ruleForm')" style="width:100%;">登陆</el-button>
<!--              <el-button @click="resetForm('ruleForm')">重置</el-button>-->
            </el-form-item>
          </el-form>
<!--          <router-link to="/regist">没有账号?创建一个</router-link>-->
<!--          <p class="regist" @click="gotoRegist">没有账号?去创建</p>-->
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapMutations } from 'vuex';
export default {
  name: "login",
  // data(){
  //   return{
  //     imgBgSrc:require('@/assets/bg_login.svg')
  //   }
  // }
  data() {
    const checkId = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('账号不能为空'));
      }
      setTimeout(() => {
        let numReg = /^[0-9]*$/
        let numRe = new RegExp(numReg)
        if (!numRe.test(value)) {
          callback(new Error('账号必须为数字'));
        }
      }, 1000);
    };
    const checkPwd = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'));
      }
    };
    return {
      // imgBgSrc:require('@/assets/bg_login.svg'),
      ruleForm: {
        id: '',
        pass: ''
      },
      rules: {
        id: [
          { validator: checkId, trigger: 'blur' }
        ],
        pass: [
          { validator: checkPwd, trigger: 'blur' }
        ]
      }
    };
  },
  methods: {
    ...mapMutations(['setToken']),
    submitForm() {
      let v=this;
      let data={
        userid: v.ruleForm.id,
        password: v.ruleForm.pass
      }
      // let data = JSON.stringify(fd);
      console.log(data)
      if(v.ruleForm.id.length<=0|v.ruleForm.pass.length<=0)
      {
        this.$message("请填写账号密码");
      }else {
        this.$axios.post('/user/login',data).then(res =>{
          if(res.data.code === 200){
            this.$message.success(res.data.msg);
            console.log(res.data)
            let userToken = res.data.token;
            let type=res.data.type;
            console.log(userToken)
            console.log("身份类型:"+type)
            this.setToken({token: userToken ,userid: v.ruleForm.id,type: type});//保存验证信息token
            // const loading = this.$loading({
            //   lock: true,
            //   text: '登录成功即将进入管理页面',
            //   spinner: 'el-icon-loading',
            //   background: 'rgba(0, 0, 0, 0.7)'
            // });
            let redirectpath=''
            if(type==4 || type==3 || type==2)
            {
              redirectpath='/admin'
            }else if(type==0)
            {
              redirectpath='/student'
            }else if(type==1)
            {
              redirectpath='/teacher'
            }
            setTimeout(() => {
              this.$router.push({path: redirectpath}); // 强制切换当前路由 path
              // loading.close();
            }, 3000);

          }else{
            this.$message.error(res.data.msg);
          }
        }).catch(res=>{
          console.log(res)
          v.$notify.error("操作失败，请检查网络或稍后重试")
        })
      }
    },
    // gotoRegist(){
    //   this.$router.push({
    //     path: "/regist"
    //   });
    // }
  }

}
</script>

<style scoped>
.page{
  /*background: url("~@/assets/css/global.css");*/
  /*background: url("../assets/css/global.css");*/
  position: fixed;
}
.background{
  background: url("../assets/bg_login.svg");
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
.regist{
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
