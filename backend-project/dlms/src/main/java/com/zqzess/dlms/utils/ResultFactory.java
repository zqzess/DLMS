package com.zqzess.dlms.utils;

import java.util.HashMap;
/**
 *
 * @Author zqzess
 * @Date 2021/10/30
 * @File ResultFactory.java
 * @Package com.zqzess.dlms.utils
 * @Version 1.0
 * @Github https://github.com/zqzess
 * @Msg 亦余心之所善兮,虽九死其尤未毁
 *
 **/
public class ResultFactory {
    public static HashMap<String,Object> resultBack(String message)
    {
        HashMap<String,Object> rs=new HashMap<>();
        rs.put("msg",message);
        return rs;
    }
    public static HashMap<String,Object> resultBack(int code,String message)
    {
        HashMap<String,Object> rs=new HashMap<>();
        rs.put("code",code);
        rs.put("msg",message);
        return rs;
    }
    public static HashMap<String,Object> resultBack(int code,String message,String token,int type)
    {
        HashMap<String,Object> rs=new HashMap<>();
        rs.put("code",code);
        rs.put("msg",message);
        rs.put("token",token);
        rs.put("type",type);
        return rs;
    }
    public static HashMap<String,Object> resultBack(int code,Object object)
    {
        HashMap<String,Object> rs=new HashMap<>();
        rs.put("code",code);
        rs.put("content",object);
        return rs;
    }
    public static HashMap<String,Object> resultBack(int code,String message,Object object)
    {
        HashMap<String,Object> rs=new HashMap<>();
        rs.put("code",code);
        rs.put("msg",message);
        rs.put("content",object);
        return rs;
    }
    public static HashMap<String,Object> resultBack(int code,Object object,Object object2)
    {
        HashMap<String,Object> rs=new HashMap<>();
        rs.put("code",code);
        rs.put("content",object);
        rs.put("function",object2);
        return rs;
    }
    public static HashMap<String,Object> resultBack(int code,Object object,Object object2,Object object3,Object object4,Object object5)
    {
        HashMap<String,Object> rs=new HashMap<>();
        rs.put("code",code);
        rs.put("colleges",object);
        rs.put("departments",object2);
        rs.put("subjetcs",object3);
        rs.put("classnames",object4);
        rs.put("braces",object5);
        return rs;
    }
}
