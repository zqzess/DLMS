<template>
  <keep-alive>
    <div class="content">
      <el-container style="height: 98%; border: 1px solid #eee;padding: 1%">
        <el-header>
          <el-page-header @back="goBack" content="查看表信息" style="margin: 20px;"/>
        </el-header>

        <div style="padding-left:3%;padding-right: 3%">
          <el-descriptions class="margin-top" title="基本信息" :column="3" :size="size" border>
            <template slot="extra">
              <el-button type="primary" size="small" @click="exportExcel">导出为EXCEL</el-button>
            </template>
            <!--            <el-descriptions-item>-->
            <!--              <template slot="label">-->
            <!--                <i class="el-icon-user"></i>-->
            <!--                编号-->
            <!--              </template>-->
            <!--              {{ tableinfo.id }}-->
            <!--            </el-descriptions-item>-->
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-user"></i>
                名称
              </template>
              {{ tableinfo.name }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-user"></i>
                创建人
              </template>
              {{ tableinfo.creator }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-location-outline"></i>
                创建时间
              </template>
              {{ tableinfo.createTime }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-tickets"></i>
                状态
              </template>
              <el-tag size="small">{{ tableinfo.state.label }}</el-tag>
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-office-building"></i>
                填写对象
              </template>
              {{ tableinfo.type.label }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-user"></i>
                已填写人数
              </template>
              {{ tableinfo.editCount }}
            </el-descriptions-item>
          </el-descriptions>
        </div>
        <div style="padding-left:3%;margin-top: 10px;font-size: 16px;font-weight: bold">表内容</div>
        <el-main style="padding-left: 3%;padding-right: 3%;margin-top: 10px">
          <el-table
              :data="tableData"
              border
              max-height="300"
              style="width: 100%">
            <el-table-column
                v-for="(item,index) in tablecolumns"
                :key="index"
                :prop="item.key"
                :label="item.title">
            </el-table-column>
          </el-table>
        </el-main>
      </el-container>
    </div>
  </keep-alive>
</template>

<script>
import { export2Excel } from '@/common/util'
export default {
  name: "tableView",
  data() {
    return {
      tableData: [],
      // tablecolumns:["姓名","年龄","学号","身份"],
      tablecolumns: [],
      tableinfo: {
        id: '',
        name: '',
        creator: '',
        createTime: '',
        state: {
          value: '',
          label: ''
        },
        type: {
          value: '',
          label: ''
        },
        editCount: ''
      },
      size: ''
    }
  },
  created() {
    let vue = this;
    // let tableViewstate = sessionStorage.getItem('tableViewstate');
    let data = JSON.parse(sessionStorage.getItem('tableViewstate'));
    if (data == null || data == 'undefined') {
      // this.$router.go(-1);//返回上一层
      this.$router.push('/error')
    }
    // let tableViewstate = data.tag;
    let tableid = data.id;
    vue.$axios.get('/table/getTable', {
      params: {
        method: 1,
        tableid: tableid
      }
    }).then(res => {
      let result = res.data.content;
      let structure = JSON.parse(res.data.content.tableStructure).structure;
      let jsonContent=res.data.function;
      if (res.data.code == 200) {
        vue.tableinfo.id = result.id;
        vue.tableinfo.name = result.name;
        vue.tableinfo.creator = result.creator;
        vue.tableinfo.createTime = result.createTime;
        if (result.state == 0) {
          vue.tableinfo.state.label = '待发布'
          vue.tableinfo.state.value = 0
        } else if (result.state == 1) {
          vue.tableinfo.state.label = '已发布'
          vue.tableinfo.state.value = 1
        } else if (result.state == 2) {
          vue.tableinfo.state.label = '已截止'
          vue.tableinfo.state.value = 2
        }

        if (result.type == 0) {
          vue.tableinfo.type.label = '学生'
          vue.tableinfo.type.value = 0
        } else if (result.type == 1) {
          vue.tableinfo.type.label = '教师'
          vue.tableinfo.type.value = 1
        }

        for (let i = 0; i < structure.length; i++) {
          //提交生成表头数据
          vue.tablecolumns.push({key: structure[i].value, title: structure[i].label})
        }
        vue.tableinfo.editCount=jsonContent.length;
        for(let i=0;i<jsonContent.length;i++)
        {//提交生成表内容
          // console.log("test:"+jsonContent[i].object6)
          // let data=JSON.parse(jsonContent[i].jsonContent)
          vue.tableData.push(JSON.parse(jsonContent[i].jsonContent));
        }
      }
    }).catch(res=>{
      console.log(res);
      this.$notify.error("数据加载失败，请稍后重试")
    })
  },
  methods: {
    goBack() {
      this.$router.back();
    },
    exportExcel(){
      export2Excel(this.tablecolumns,this.tableData)
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