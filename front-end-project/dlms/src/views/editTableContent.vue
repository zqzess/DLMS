<template>
  <keep-alive>
    <div class="content">
      <el-container style="height: 98%; border: 1px solid #eee;padding: 1%">
        <el-header>
          <el-page-header @back="goBack" :content="tablename" style="margin: 20px;"/>
        </el-header>
        <el-main style="width: 90%;margin: 0 auto">
          <!--          <el-table-->
          <!--              :data="tableData"-->
          <!--              border-->
          <!--              max-height="300"-->
          <!--              style="width: 100%">-->
          <!--            <el-table-column-->
          <!--                v-for="(item,index) in tablecolumns"-->
          <!--                :key="index"-->
          <!--                :prop="item.key"-->
          <!--                :label="item.title">-->
          <!--            </el-table-column>-->
          <!--          </el-table>-->
          <el-form :model="tableData" status-icon ref="tableData" label-width="100px" class="tableData">
            <el-form-item v-for="(item,index) in tablecolumns" :key="index" :label="item.title">
              <el-input v-model="tableData[item.key]" autocomplete="off" v-show="inputType[index].type0"></el-input>
              <el-select v-model="tableData[item.key]" :placeholder="tableData[item.key]"
                         v-show="inputType[index].type1" @change="collegeChange(tableData[item.key])">
                <el-option
                    v-for="item in college"
                    :key="item"
                    :value="item">
                </el-option>
              </el-select>
              <el-select v-model="tableData[item.key]" :placeholder="tableData[item.key]"
                         v-show="inputType[index].type2" @change="subjectChange(tableData[item.key])">
                <el-option
                    v-for="item in subject"
                    :key="item"
                    :value="item">
                </el-option>
              </el-select>
              <el-select v-model="tableData[item.key]" :placeholder="tableData[item.key]"
                         v-show="inputType[index].type3" @change="selectChange">
                <el-option
                    v-for="item in classname"
                    :key="item"
                    :value="item">
                </el-option>
              </el-select>
              <el-select v-model="tableData[item.key]" :placeholder="tableData[item.key]"
                         v-show="inputType[index].type4" @change="selectChange">
                <el-option
                    v-for="item in department"
                    :key="item"
                    :value="item">
                </el-option>
              </el-select>
              <el-date-picker
                  v-model="tableData[item.key]"
                  placeholder="????????????"
                  value-format="yyyyMMdd"
                  v-show="inputType[index].type5">
              </el-date-picker>
              <el-input v-model="tableData[item.key]" onkeyup="value=value.replace(/[^\d]/g,0)" v-show="inputType[index].type6"></el-input>
              <el-select v-model="tableData[item.key]" :placeholder="tableData[item.key]"
                         v-show="inputType[index].type7" @change="selectChange">
                <el-option
                    v-for="item in contentTmp[item.key]"
                    :key="item"
                    :value="item">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="submitForm()">??????</el-button>
            </el-form-item>
          </el-form>
        </el-main>
      </el-container>
    </div>
  </keep-alive>
</template>

<script>
export default {
  name: "editTableContent",
  data() {
    return {
      tableData: {},
      tablecolumns: [],
      tablename: '',
      tableid: '',
      // inputType:[{
      //   type0:false,
      //   type1:false,
      //   type2:false,
      //   type3:false,
      //   type4:false,
      //   type5:false
      // }],
      inputType: [],
      college: [],
      department: [],
      subject: [],
      classname: [],
      contentTmp:[],
      userinfo: {},
      subjectIndex:'',
      classnameIndex:'',
      userInfoMap:{}
    }
  },
  async created() {
    let vue = this;
    let data = JSON.parse(sessionStorage.getItem('editTableContentstate'));
    if (data == null || data == 'undefined') {
      // this.$router.go(-1);//???????????????
      this.$router.push('/error')
    }
    vue.tableid = data.id;
    let userid = {userid: localStorage.getItem("userid")}
    //??????????????????
    this.$axios.post('/user/getUser', userid).then(res => {
      if (res.data.code == 200) {
        vue.userinfo = res.data.content
      }
    }).catch(res => {
      console.log(res)
      vue.$message.error("??????????????????")
    })
    // ??????
    let college = await vue.getBaseInfo(0, 0)
    vue.college=college
    // ??????
    let department=await  vue.getBaseInfo(0,1)
    vue.department=department
    //??????????????????
    await vue.$axios.get('/table/getTable', {
      params: {
        method: 1,
        tableid: vue.tableid
      }
    }).then( res => {
      // let result = res.data.content;
      let structure = JSON.parse(res.data.content.tableStructure).structure;
      if(structure==null||structure===''||structure==='undefind')
      {
        vue.$notify.error("??????????????????")
        return false
      }
      if (res.data.code == 200) {
        vue.tablename = res.data.content.name;
        for (let i = 0; i < structure.length; i++) {
          //??????????????????
          let objectTmp = {
            type0: false,
            type1: false,
            type2: false,
            type3: false,
            type4: false,
            type5: false,
            type6: false,
            type7: false
          }
          if (structure[i].type == null || structure[i].type == 'undefined') {
            let str = structure[i].label
            let reg = /.+??????.+/;
            let reg2 = /.+?????????.+/;
            let matchResult = str.match(reg);
            let matchResult2 = str.match(reg2);

            if (structure[i].label === '??????') {
              objectTmp.type0 = true
              vue.tableData[structure[i].value] = vue.userinfo.name
            } else if (structure[i].label === '??????') {
              vue.contentTmp[structure[i].value] = ['???', '???']
              vue.tableData[structure[i].value] = vue.userinfo.gender
              objectTmp.type7 = true
            } else if (matchResult != null || matchResult2 != null) {
              vue.contentTmp[structure[i].value] = ['????????????', '??????', '??????', '?????????', '???????????????']
              objectTmp.type7 = true
            } else {
              objectTmp.type0 = true
            }
          } else if (structure[i].type == 0) {
            let str = structure[i].label
            let reg = /.+??????.+/;
            let reg2 = /.+?????????.+/;
            let reg3 = /.+?????????.+/;
            let matchResult = str.match(reg);
            let matchResult2 = str.match(reg2);
            if (structure[i].label === '??????') {
              objectTmp.type0 = true
              vue.tableData[structure[i].value] = vue.userinfo.name
            } else if (structure[i].label === '??????') {
              vue.contentTmp[structure[i].value] = ['???', '???']
              vue.tableData[structure[i].value] = vue.userinfo.gender
              objectTmp.type7 = true
            } else if (matchResult != null || matchResult2 != null) {
              vue.contentTmp[structure[i].value] = ['????????????', '??????', '??????', '?????????', '???????????????']
              objectTmp.type7 = true
            } else if (str.match(reg3)!=null)
            {//????????????
              objectTmp.type0 = true
              vue.tableData[structure[i].value] = vue.userinfo.peopleid
              vue.userInfoMap.peopleid=vue.userinfo.peopleid
            }
            else {
              objectTmp.type0 = true
            }
          } else if (structure[i].type == 1) {
            //??????
            objectTmp.type1 = true
            vue.tableData[structure[i].value] = vue.userinfo.college
            vue.userInfoMap.college=vue.userinfo.college
          } else if (structure[i].type == 2) {
            //??????
            objectTmp.type2 = true
            vue.tableData[structure[i].value] = vue.userinfo.subject
            vue.subjectIndex = structure[i].value
            vue.userInfoMap.subject=vue.userinfo.subject
          } else if (structure[i].type == 3) {
            //??????
            objectTmp.type3 = true
            vue.tableData[structure[i].value] = vue.userinfo.classname
            vue.classnameIndex = structure[i].value
            vue.userInfoMap.classname=vue.userinfo.classname
          } else if (structure[i].type == 4) {
            //??????
            objectTmp.type4 = true
            vue.tableData[structure[i].value] = vue.userinfo.department
            vue.userInfoMap.department=vue.userinfo.department
          } else if (structure[i].type == 5) {
            //??????
            objectTmp.type5 = true
          } else if (structure[i].type == 6) {
            //??????
            objectTmp.type6 = true
          }
          vue.inputType.push(objectTmp)
          vue.tablecolumns.push({key: structure[i].value, title: structure[i].label})
        }

        // if(res.data.function!=null)
        // {
        //   let jsonContent=JSON.parse(res.data.function.jsonContent);
        //   vue.tableData.push(jsonContent);
        // }

      }
    }).catch(res=>{
      console.log(res)
      vue.$notify.error("??????????????????")
      // this.$router.go(-1);
    })
    //??????
    if(vue.userInfoMap.college==null||vue.userInfoMap.college===''||vue.userInfoMap.college==='undefined')
    {
      let subject=await vue.getBaseInfo(0,2)
      for(let i in subject)
      {
        vue.subject.push(subject[i].subject)
      }
    }else
    {
      //??????????????????????????????
      let subject=await vue.getBaseInfo(1,0,vue.userinfo.college)
      for(let i in subject)
      {
        vue.subject.push(subject[i].subject)
      }
    }

    //??????
    if(vue.userInfoMap.subject==null||vue.userInfoMap.subject===''||vue.userInfoMap.subject==='undefined')
    {
      let classname=await vue.getBaseInfo(0,3)
      for(let i in classname)
      {
        vue.classname.push(classname[i].classname)
      }
    }else
    {//??????????????????????????????
      let classname=await vue.getBaseInfo(1,1,vue.userinfo.subject)
      for(let i in classname)
      {
        vue.classname.push(classname[i].classname)
      }
    }

  },
  methods: {
    goBack() {
      this.$router.back();
    },
    submitForm() {
      console.log(this.tableData);
      let vue = this;
      let userid = localStorage.getItem("userid");
      let data = {
        method: '0',
        tableid: vue.tableid,
        userid: userid,
        content: vue.tableData
      }
      this.$axios.post('/table/setTableContent', data).then(res => {
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
        this.$notify.error("????????????");
      })
    },
    async collegeChange(college) {
      let vue = this;
      vue.tableData[vue.subjectIndex]='';
      vue.tableData[vue.classnameIndex]='';
      let subject = await vue.getBaseInfo(1, 0, college)
      vue.subject.length = 0;
      vue.subject.push()
      vue.classname.length = 0;
      vue.classname.push()
      if(subject.length!=0)
      {
        for (let i in subject) {
          vue.subject.push(subject[i].subject)
        }
      }
      this.$forceUpdate()
    },
    async subjectChange(subject) {
      let vue = this;
      vue.tableData[vue.classnameIndex]='';
      let classname = await vue.getBaseInfo(1, 1, subject)
      vue.classname.length = 0;
      vue.classname.push()
      if(classname.length!=0)
      {
        for (let i in classname) {
          vue.classname.push(classname[i].classname)
        }
      }
      this.$forceUpdate()
    },
    selectChange(){
      this.$forceUpdate()
    },
    async getBaseInfo(type, method,name) {
      let vue = this;
      let object;
      if (type == 0) {
        await vue.$axios.get('/baseinfo/getBaseInfo', {
          params: {
            method: method
          }
        }).then(res => {
          if (res.data.code == 200) {
            object = res.data.content
          }
        })
      }else if(type==1)
      {
        await vue.$axios.post('/baseinfo/getBaseInfoWithName', {
          method: method,
          name: name
        }).then(res => {
          if (res.data.code == 200) {
            if(res.data.content!=='')
            {
              object = res.data.content
            }else
            {
              object = ''
            }
          }
        })
      }
      return object;
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