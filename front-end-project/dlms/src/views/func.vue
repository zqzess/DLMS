<template>
  <keep-alive>
    <div style="display: flex;flex-direction: column;">
      <!--      <el-tooltip :content="'Switch value: ' + student.read" placement="top">-->
      <!--        <el-switch-->
      <!--            v-model="student.read"-->
      <!--            active-value="0"-->
      <!--            inactive-value="1">-->
      <!--        </el-switch>-->
      <!--      </el-tooltip>-->
      <div style="align-items: center;display: inline-flex;" v-show="isShow.admin1.people">
        <div style="margin-right: 10px">学生查看个人信息</div>
        <el-switch
            @change="setState(student.read,funcid.id1)"
            v-model="student.read"
            :active-value="0"
            :inactive-value="1">
        </el-switch>
      </div>
      <div style="align-items: center;display: inline-flex;margin-top: 10px" v-show="isShow.admin1.people">
        <div style="margin-right: 10px">学生修改个人信息</div>
        <el-switch
            @change="setState(student.write,funcid.id2)"
            v-model="student.write"
            :active-value="0"
            :inactive-value="1">
        </el-switch>
      </div>
      <div style="align-items: center;display: inline-flex;margin-top: 10px" v-show="isShow.admin1.people">
        <div style="margin-right: 10px">已毕业学生查看个人信息</div>
        <el-switch
            @change="setState(student2.read,funcid.id3)"
            v-model="student2.read"
            :active-value="0"
            :inactive-value="1">
        </el-switch>
      </div>
      <div style="align-items: center;display: inline-flex;margin-top: 10px" v-show="isShow.admin1.people">
        <div style="margin-right: 10px">已毕业学生修改个人信息</div>
        <el-switch
            @change="setState(student2.write,funcid.id4)"
            v-model="student2.write"
            :active-value="0"
            :inactive-value="1">
        </el-switch>
      </div>
      <div style="align-items: center;display: inline-flex;margin-top: 10px" v-show="isShow.admin1.people">
        <div style="margin-right: 10px">未入学学生查看个人信息</div>
        <el-switch
            @change="setState(student3.read,funcid.id5)"
            v-model="student3.read"
            :active-value="0"
            :inactive-value="1">
        </el-switch>
      </div>
      <div style="align-items: center;display: inline-flex;margin-top: 10px" v-show="isShow.admin1.people">
        <div style="margin-right: 10px">未入学学生修改个人信息</div>
        <el-switch
            @change="setState(student3.write,funcid.id6)"
            v-model="student3.write"
            :active-value="0"
            :inactive-value="1">
        </el-switch>
      </div>
      <div style="align-items: center;display: inline-flex;margin-top: 10px" v-show="isShow.admin1.people">
        <div style="margin-right: 10px">教师查看个人信息</div>
        <el-switch
            @change="setState(teacher.read,funcid.id7)"
            v-model="teacher.read"
            :active-value="0"
            :inactive-value="1">
        </el-switch>
      </div>
      <div style="align-items: center;display: inline-flex;margin-top: 10px" v-show="isShow.admin1.people">
        <div style="margin-right: 10px">教师修改个人信息</div>
        <el-switch
            @change="setState(teacher.write,funcid.id8)"
            v-model="teacher.write"
            :active-value="0"
            :inactive-value="1">
        </el-switch>
      </div>
      <div style="align-items: center;display: inline-flex;margin-top: 10px" v-show="isShow.admin1.people">
        <div style="margin-right: 10px">教师创建修改表</div>
        <el-switch
            @change="setState(teacher.table,funcid.id9)"
            v-model="teacher.table"
            :active-value="0"
            :inactive-value="1">
        </el-switch>
      </div>
      <div style="align-items: center;display: inline-flex;margin-top: 10px" v-show="isShow.admin2.people">
        <div style="margin-right: 10px">部门管理员管理人员</div>
        <el-switch
            @change="setState(admin1.people,funcid.id10)"
            v-model="admin1.people"
            :active-value="0"
            :inactive-value="1">
        </el-switch>
      </div>
      <div style="align-items: center;display: inline-flex;margin-top: 10px" v-show="isShow.admin2.class">
        <div style="margin-right: 10px">部门管理员管理班级</div>
        <el-switch
            @change="setState(admin1.class,funcid.id11)"
            v-model="admin1.class"
            :active-value="0"
            :inactive-value="1">
        </el-switch>
      </div>
      <div style="align-items: center;display: inline-flex;margin-top: 10px" v-show="isShow.admin3">
        <div style="margin-right: 10px">学院管理员管理部门</div>
        <el-switch
            @change="setState(admin2.func,funcid.id12)"
            v-model="admin2.func"
            :active-value="0"
            :inactive-value="1">
        </el-switch>
      </div>
      <div style="align-items: center;display: inline-flex;margin-top: 10px" v-show="isShow.admin3">
        <div style="margin-right: 10px">学院管理员管理专业</div>
        <el-switch
            @change="setState(admin2.major,funcid.id13)"
            v-model="admin2.major"
            :active-value="0"
            :inactive-value="1">
        </el-switch>
      </div>
    </div>
  </keep-alive>
</template>

<script>

export default {
  name: "func",
  data() {
    return {
      student: {
        read: 0,
        write: 0
      },
      student2: {
        read: 0,
        write: 0
      },
      student3: {
        read: 0,
        write: 0
      },
      teacher: {
        read: 0,
        write: 0,
        table: 0
      },
      admin1: {
        people: 0,
        class: 0,
      },
      admin2: {
        func: 0,
        major: 0
      },
      isShow: {
        admin1: {
          people:true,
        },
        admin2: {
          people:true,
          class:true
        },
        admin3: false
      },
      funcid:{
        id1:1010,
        id2:1011,
        id3:1023,
        id4:1024,
        id5:1025,
        id6:1026,
        id7:110,
        id8:111,
        id9:122,
        id10:210,
        id11:211,
        id12:310,
        id13:311,
      }
    }
  },
  created() {
    let vue=this;
    let funcstate = sessionStorage.getItem('funcstate');
    if (funcstate == null || funcstate == 'undefined') {
      // this.$router.go(-1);//返回上一层
      this.$router.push('/error')
    }
    let type = localStorage.getItem("type")
    if (type == 4) {
      this.isShow.admin3= true;
    }
    if(type==2)
    {
      this.isShow.admin2.class=false;
      this.isShow.admin2.people=false;
    }
    this.$axios.get('/baseinfo/getBaseInfo',{
      params: {
        method: '5',
      }
    }).then(res=>{
      if(res.data.code==200)
      {
        let result=res.data.content;
        for(let i=0;i<result.length;i++)
        {
          if(result[i].id==1010)
          {//学生查看个人信息
            vue.student.read=result[i].state;
          }else if(result[i].id==1011)
          {//学生修改个人信息
            vue.student.write=result[i].state;
          }else if(result[i].id==1023)
          {//已毕业学生查看个人信息
            vue.student2.read=result[i].state;
          }else if(result[i].id==1024)
          {//已毕业学生修改个人信息
            vue.student2.write=result[i].state;
          }else if(result[i].id==1025)
          {//未入学学生查看个人信息
            vue.student3.read=result[i].state;
          }else if(result[i].id==1026)
          {//未入学学生修改个人信息
            vue.student3.write=result[i].state;
          }else if(result[i].id==110)
          {//教师查看个人信息
            vue.teacher.read=result[i].state;
          }else if(result[i].id==111)
          {//教师修改个人信息
            vue.teacher.write=result[i].state;
          }else if(result[i].id==122)
          {//教师创建修改表
            vue.teacher.table=result[i].state;
          }else if(result[i].id==210)
          {//部门管理员人员
            vue.admin1.people=result[i].state;
            if(type==2)
            {
              if(result[i].state==1)
              {
                vue.isShow.admin1.people=false;
              }
            }
          }else if(result[i].id==211)
          {//部门管理员班级
            vue.admin1.class=result[i].state;
          }else if(result[i].id==310)
          {//学院管理员管理部门
            vue.admin2.func=result[i].state;
            if(type==3)
            {
              if(result[i].state==1)
              {
                vue.isShow.admin2.people=false;
                vue.isShow.admin2.class=false;
              }
            }
          }else if(result[i].id==311)
          {//学院管理员管理专业
            vue.admin2.major=result[i].state;
          }
        }
      }
    }).catch(function (err) {
      console.log(err);
    });
  },
  methods: {
    setState(state,id){
      let vue=this;
      console.log("状态"+state+"id:"+id)
      let data={
        id: id,
        state:state
      }
      this.$axios.post('/baseinfo/setFunction',data).then(res=>{
        if(res.data.code==200)
        {
          vue.$notify.success(res.data.msg);
        }else {
          vue.$notify.error(res.data.msg);
        }
      }).catch(res=>{
        console.log(res)
        vue.$notify.error("出错误");
      })
    }
  }
}
</script>

<style scoped>

</style>