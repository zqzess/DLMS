package com.zqzess.dlms.bean;
/**
 * @Author zqzess
 * @Date 2021/10/30
 * @Project dlms
 * @Package com.zqzess.dlms.bean
 * @Version 1.0
 * @Github https://github.com/zqzess
 * @Msg 亦余心之所善兮, 虽九死其尤未毁
 **/
public class Result {
    public static class Code{
        public static int SUCCESS=200;    //成功
        public static int CONTROL_ERR=304;  //操作失败或未修改
        public static int REQUEST_ERR=400;    //请求错误
        public static int NOT_AUTH=401;   //未验证
        public static int REQUEST_REJECT=403; //请求被拒绝
        public static int NOTFOUND=404;   //不存在该页面
        public static int TIMEOUT=408;    //超时
        public static int SERVICE_ERR=500;    //服务器错误
        public static int SERVICE_NOTPRE=501; //服务未实现
    }
    public static class Msg{
        public static String LOGIN="登录成功，即将进入管理页面!";
        public static String LOGINERR="登录失效,请重新登录!";
        public static String PWDERR="密码错误!";
        public static String NOACCOUNT="账号不存在，请联系管理员！";
        public static String INSERTSUCCESS="插入成功！";
        public static String INSERTERR="插入失败，请稍后重试！";
        public static String UPDATESUCCESS="更新成功！";
        public static String UPDATEERR="更新失败，请稍后重试！";
        public static String GETERR="数据获取失败，请稍后重试！";
        public static String CONTROLSUCCESS="操作成功！";
        public static String CONTROLERR="操作失败，请稍后重试！";

    }

}
