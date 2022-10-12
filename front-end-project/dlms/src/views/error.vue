<template>
<div>{{msg}}</div>
</template>

<script>
export default {
  name: "error",
  data(){
    return{
      msg:'403 访问被拒'
    }
  },
  created() {
    let err=this.$route.params.err;
    if(err==null||err==''||err=='undefined')
    {
      this.msg="403 访问被拒";
    }else {
      console.log("错误信息:"+err)
      this.msg=err;
    }
  }
}
</script>

<style scoped>

</style>