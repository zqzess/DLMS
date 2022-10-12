package com.zqzess.dlms.controller;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zqzess.dlms.bean.*;
import com.zqzess.dlms.service.TableContentService;
import com.zqzess.dlms.service.TableInfoService;
import com.zqzess.dlms.service.UserService;
import com.zqzess.dlms.utils.ResultFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author zqzess
 * @Date 2021/11/8
 * @Project dlms
 * @Package com.zqzess.dlms.controller
 * @Version 1.0
 * @Github https://github.com/zqzess
 * @Msg 亦余心之所善兮, 虽九死其尤未毁
 **/
@CrossOrigin
@RestController
@RequestMapping("/api/table")
public class TableController {
    @Autowired
    TableInfoService tableInfoService;

    @Autowired
    UserService userService;

    @Autowired
    TableContentService tableContentService;

    @GetMapping("/getTableList")
    @ResponseBody
    public String getTableList(@RequestParam(name = "method", required = true) int method,@RequestParam(name = "userid") long userid,@RequestParam(name = "type") int type,@RequestParam(name = "state") int state) throws JsonProcessingException {
        HashMap<String, Object> hs = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        List<TableInfo> res;
        User u;
        System.out.println("\ngetTableList");
        System.out.println("方法:" + method);
        String college;
        String subject;
        String classname;
        String department;
        if(method==0)
        {//查询
            System.out.println("id:" + userid+"\ttype:"+type+"\tstate:"+state);
            u=userService.getUserInfo(userid);
            if(u.getCollege()==null)
            {
                college="";
            }else {
                college=u.getCollege();
            }
            if(u.getSubject()==null)
            {
                subject="";
            }else {
                subject=u.getSubject();
            }
            if(u.getClassname()==null)
            {
                classname="";
            }else {
                classname=u.getClassname();
            }
            if(u.getDepartment()==null)
            {
                department="";
            }else {
                department=u.getDepartment();
            }
            if(u.getType()==0)
            {
                res=tableInfoService.getTbaleInfo(college,subject,"classname",classname,type,state);
                List<TableContent> res2=tableContentService.getTableContentWithUser(userid);
                hs= ResultFactory.resultBack(Result.Code.SUCCESS,res,res2);
            }else if(u.getType()==1)
            {
                res=tableInfoService.getTbaleInfo(college,subject,"department",department,type,state);
                List<TableContent> res2=tableContentService.getTableContentWithUser(userid);
                hs= ResultFactory.resultBack(Result.Code.SUCCESS,res,res2);
            }
//            res=tableInfoService.getTbaleInfo(college,subject,classname,department,type,state);
//            hs= ResultFactory.resultBack(Result.Code.SUCCESS,res);
        } else if(method==1)
        {
            System.out.println("查询所有表");
            res=tableInfoService.getAllTable();
            hs= ResultFactory.resultBack(Result.Code.SUCCESS,res);
        }else if(method==2)
        {//部门
            System.out.println("查询部门表");
            u=userService.getUserInfo(userid);
            if(u.getDepartment()==null)
            {
                department="";
            }else {
                department=u.getDepartment();
            }
            res=tableInfoService.getTableListWithTable("department",department);
            hs= ResultFactory.resultBack(Result.Code.SUCCESS,res);
        }else if(method==3)
        {//学院
            System.out.println("查询学院表");
            u=userService.getUserInfo(userid);
            if(u.getCollege()==null)
            {
                college="";
            }else {
                college=u.getCollege();
            }
            res=tableInfoService.getTableListWithTable("college",college);
            hs= ResultFactory.resultBack(Result.Code.SUCCESS,res);
        }else if(method==4)
        {//查询某个用户创建的表
            System.out.println("查询某个用户创建的表");
            res=tableInfoService.getTableListWithCreatorId(userid);
            hs= ResultFactory.resultBack(Result.Code.SUCCESS,res);
        }
        return objectMapper.writeValueAsString(hs);
    }

    @GetMapping("/getTable")
    @ResponseBody
    public String getTable(@RequestParam(name = "method", required = true) int method,@RequestParam(name = "tableid") long tableid) throws JsonProcessingException {
        HashMap<String, Object> hs = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println("\ngetTable");
        System.out.println("方法:" + method);
        if(method==0)
        {//依据id查询表信息
            System.out.println("tableid:" + tableid);
            TableInfo res=tableInfoService.getTable(tableid);
            hs= ResultFactory.resultBack(Result.Code.SUCCESS,res);
        }else if(method==1)
        {
            //依据id查询表信息和表内容
            System.out.println("tableid:" + tableid);
            List<TableContent> content=tableContentService.getTableContentWithId(tableid);
            TableInfo res=tableInfoService.getTable(tableid);
            hs= ResultFactory.resultBack(Result.Code.SUCCESS,res,content);
        }
        return objectMapper.writeValueAsString(hs);
    }

    @PostMapping("/setTableContent")
    @ResponseBody
    public String setTableContent(@RequestBody Map<String, Object> para) throws JsonProcessingException {
        HashMap<String, Object> hs = new HashMap<>();
        String method=(String) para.get("method");
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println("\nsetTableContent");
        System.out.println("方法:" + method);
        if(method.equals("0"))
        {//插入表内容
//            String content=(String)para.get("content");
            LinkedHashMap linkedHashMap = (LinkedHashMap) para.get("content");
            String content = new JSONObject().toJSONString(linkedHashMap);
            System.out.println("json:\n" + content);
            long tableid=(long)((int)para.get("tableid"));
            long userid=Long.parseLong((String)para.get("userid"));
            try{
                tableContentService.inserTableContent(tableid,userid,content);
                hs=ResultFactory.resultBack(Result.Code.SUCCESS,Result.Msg.UPDATESUCCESS);
            }catch (Exception e)
            {
                e.printStackTrace();
                hs=ResultFactory.resultBack(Result.Code.CONTROL_ERR,Result.Msg.UPDATEERR);
            }
        }
        return objectMapper.writeValueAsString(hs);
    }

    @PostMapping("/setTableInfo")
    @ResponseBody
    public String setTableInfo(@RequestBody Map<String, Object> para) throws JsonProcessingException {
        HashMap<String, Object> hs = new HashMap<>();
        int method=(int) para.get("method");
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println("\nsetTableInfo");
        System.out.println("方法:" + method);
        if(method==0)
        {//插入新的表
//            String structure=(String)para.get("structure");
            LinkedHashMap linkedHashMap = (LinkedHashMap) para.get("structure");
            String structure = new JSONObject().toJSONString(linkedHashMap);
            System.out.println("json:\n" + structure);
            int type=(int)para.get("type");
            int state=(int)para.get("state");
            String name=(String)para.get("name");
            String college=(String)para.get("college");
            String subject=(String)para.get("subject");
            String classname=(String)para.get("classname");
            String department=(String)para.get("department");
            long userid=Long.parseLong((String)para.get("userid"));
            User u= userService.getUserInfo(userid);
            System.out.println("table_name:\n" + name);
            System.out.println("creator_id:\n" + userid);
            System.out.println("creator:\n" + u.getName());
            try{
                tableInfoService.insertTableInfo(name,userid,u.getName(),state,college,department,subject,classname,type,structure);
                hs=ResultFactory.resultBack(Result.Code.SUCCESS,Result.Msg.UPDATESUCCESS);
            }catch (Exception e)
            {
                e.printStackTrace();
                hs=ResultFactory.resultBack(Result.Code.CONTROL_ERR,Result.Msg.UPDATEERR);
            }
        }
        return objectMapper.writeValueAsString(hs);
    }

    @GetMapping("/deleteTable")
    @ResponseBody
    public String deleteTable(@RequestParam(name = "method", required = true) int method,@RequestParam(name = "tableid") long tableid) throws JsonProcessingException {
        HashMap<String, Object> hs = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println("\ndeleteTable");
        System.out.println("方法:" + method);
        if(method==0)
        {//删除表
            System.out.println("tableid:" + tableid);
            try{
                tableInfoService.deleteTableInfo(tableid);
                tableContentService.deleteTableContent(tableid);
                hs= ResultFactory.resultBack(Result.Code.SUCCESS,Result.Msg.CONTROLSUCCESS);
            }catch (Exception e)
            {
                e.printStackTrace();
                hs= ResultFactory.resultBack(Result.Code.CONTROL_ERR,Result.Msg.CONTROLERR);
            }
        }
        return objectMapper.writeValueAsString(hs);
    }

    @GetMapping("/updateTableInfo")
    @ResponseBody
    public String updateTableInfo(@RequestParam(name = "method", required = true) int method,@RequestParam(name = "tableid") long tableid,@RequestParam(name = "state") int state) throws JsonProcessingException {
        HashMap<String, Object> hs = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println("\nupdateTableInfo");
        System.out.println("方法:" + method);
        if(method==0)
        {//删除表
            System.out.println("tableid:" + tableid);
            try{
                tableInfoService.updateTableInfoState(state,tableid);
                hs= ResultFactory.resultBack(Result.Code.SUCCESS,Result.Msg.CONTROLSUCCESS);
            }catch (Exception e)
            {
                e.printStackTrace();
                hs= ResultFactory.resultBack(Result.Code.CONTROL_ERR,Result.Msg.CONTROLERR);
            }
        }
        return objectMapper.writeValueAsString(hs);
    }
}
