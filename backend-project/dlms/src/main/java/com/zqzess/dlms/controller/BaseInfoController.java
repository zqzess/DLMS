package com.zqzess.dlms.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zqzess.dlms.bean.ClassName;
import com.zqzess.dlms.bean.Function;
import com.zqzess.dlms.bean.Result;
import com.zqzess.dlms.bean.Subject;
import com.zqzess.dlms.mapper.ClassNameMapper;
import com.zqzess.dlms.mapper.SubjectMapper;
import com.zqzess.dlms.service.BaseInfoService;
import com.zqzess.dlms.service.ClassNameService;
import com.zqzess.dlms.service.FunctionService;
import com.zqzess.dlms.utils.ResultFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author zqzess
 * @Date 2021/11/5
 * @Project dlms
 * @Package com.zqzess.dlms.controller
 * @Version 1.0
 * @Github https://github.com/zqzess
 * @Msg 亦余心之所善兮, 虽九死其尤未毁
 **/
@CrossOrigin
@RestController
@RequestMapping("/api/baseinfo")
public class BaseInfoController {
    @Autowired
    BaseInfoService baseInfoService;

    @Autowired
    SubjectMapper subjectMapper;

    @Autowired
    ClassNameService classNameService;

    @Autowired
    FunctionService functionService;

    @GetMapping("/getBaseInfo")
    @ResponseBody
    public String getCollege(@RequestParam(name = "method", required = true) int method) throws JsonProcessingException {
        HashMap<String, Object> hs = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        List<String> brace;
        List<String> college;
        List<String> department;
        List<Subject> subjects;
        List<ClassName> classNames;
        List<Function> functions;
        System.out.println("\ngetBaseInfo");
        System.out.println("方法:" + method);
        if(method==0)
        {//学院
            college=baseInfoService.getAllCollege();
            hs= ResultFactory.resultBack(Result.Code.SUCCESS,college);
        }else if(method==1)
        {//部门
            department=baseInfoService.getAllDepartment();
            hs= ResultFactory.resultBack(Result.Code.SUCCESS,department);
        }else if(method==2)
        {//专业
            subjects=subjectMapper.getAllSubject();
            hs= ResultFactory.resultBack(Result.Code.SUCCESS,subjects);
        }else if(method==3)
        {//班级
            classNames=classNameService.getAllClass();
            hs=ResultFactory.resultBack(Result.Code.SUCCESS,classNames);
        }else if(method==4)
        {//职称
            brace=baseInfoService.getAllBrace();
            hs=ResultFactory.resultBack(Result.Code.SUCCESS,brace);
        }else if(method==5)
        {//权限
            functions=functionService.getAllFunction();
            hs=ResultFactory.resultBack(Result.Code.SUCCESS,functions);
        }else if(method==6)
        {
            college=baseInfoService.getAllCollege();
            department=baseInfoService.getAllDepartment();
            subjects=subjectMapper.getAllSubject();
            classNames=classNameService.getAllClass();
            brace=baseInfoService.getAllBrace();
            hs=ResultFactory.resultBack(Result.Code.SUCCESS,college,department,subjects,classNames,brace);
        }else if(method==7)
        {

        }
        return objectMapper.writeValueAsString(hs);
    }

    @PostMapping("/getBaseInfoWithName")
    @ResponseBody
    public String getBaseInfoWithName(@RequestBody Map<String, Object> para) throws JsonProcessingException {
        HashMap<String, Object> hs = new HashMap<>();
        int method=(int) para.get("method");
        String name=(String)para.get("name");
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println("\ngetBaseInfoWithName");
        System.out.println("method:" + method);
        System.out.println("name:" + name);
        if(method==0)
        {
            try{
                List<Subject> subjects=subjectMapper.getSubject(name);
                hs=ResultFactory.resultBack(Result.Code.SUCCESS,subjects);
            }catch (Exception e)
            {
                e.printStackTrace();
                hs=ResultFactory.resultBack(Result.Code.REQUEST_ERR,Result.Msg.GETERR);

            }
        }else if(method==1){
            try{
                List<ClassName> classNames=classNameService.getClass(name);
                hs=ResultFactory.resultBack(Result.Code.SUCCESS,classNames);
            }catch (Exception e)
            {
                e.printStackTrace();
                hs=ResultFactory.resultBack(Result.Code.REQUEST_ERR,Result.Msg.GETERR);

            }
        }
        return objectMapper.writeValueAsString(hs);
    }

    @PostMapping("/setCollege")
    @ResponseBody
    public String setCollege(@RequestBody Map<String, Object> para) throws JsonProcessingException {
        HashMap<String, Object> hs = new HashMap<>();
        String method=(String) para.get("method");
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println("\nsetCollege");
        System.out.println("方法:" + method);
        if(method.equals("0"))
        {//插入新的学院
            String name = (String) para.get("name");
            System.out.println("name:" + name);
            try {
                baseInfoService.insertCollege(name);
                hs=ResultFactory.resultBack(Result.Code.SUCCESS,Result.Msg.INSERTSUCCESS);
            }catch (Exception e)
            {
                e.printStackTrace();
                hs=ResultFactory.resultBack(Result.Code.CONTROL_ERR,Result.Msg.INSERTERR);
            }
        }else if(method.equals("1"))
        {
            String newName=(String) para.get("newName");
            String oldName=(String) para.get("oldName");
            System.out.println("新:" + newName+"  原:  "+oldName);
//            String[] arr={"college","department","subject","users"};
            String[] arr={"college","subject","users","tableinfo"};
            try{
                for(int i=0;i<4;i++)
                {
                    baseInfoService.updateCollege(arr[i],newName,oldName);
                }
                hs=ResultFactory.resultBack(Result.Code.SUCCESS,Result.Msg.UPDATESUCCESS);
            }catch (Exception e)
            {
                e.printStackTrace();
                hs=ResultFactory.resultBack(Result.Code.CONTROL_ERR,Result.Msg.UPDATEERR);
            }
        }
        return objectMapper.writeValueAsString(hs);
    }
    @PostMapping("/setDepartment")
    @ResponseBody
    public String setDepartment(@RequestBody Map<String, Object> para) throws JsonProcessingException {
        HashMap<String, Object> hs = new HashMap<>();
        String method=(String) para.get("method");
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println("\nsetDepartment");
        System.out.println("方法:" + method);
        if(method.equals("0"))
        {//插入新的部门
            String name = (String) para.get("name");
            System.out.println("name:" + name);
            try {
                baseInfoService.insertDepartment(name);
                hs=ResultFactory.resultBack(Result.Code.SUCCESS,Result.Msg.INSERTSUCCESS);
            }catch (Exception e)
            {
                e.printStackTrace();
                hs=ResultFactory.resultBack(Result.Code.CONTROL_ERR,Result.Msg.INSERTERR);
            }
        }else if(method.equals("1"))
        {
            String newName=(String) para.get("newName");
            String oldName=(String) para.get("oldName");
            System.out.println("新:" + newName+"  原:  "+oldName);
            String[] arr={"department","users","tableinfo"};
            try{
                for(int i=0;i<3;i++)
                {
                    baseInfoService.updateDepartment(arr[i],newName,oldName);
                }
                hs=ResultFactory.resultBack(Result.Code.SUCCESS,Result.Msg.UPDATESUCCESS);
            }catch (Exception e)
            {
                e.printStackTrace();
                hs=ResultFactory.resultBack(Result.Code.CONTROL_ERR,Result.Msg.UPDATEERR);
            }
        }
        return objectMapper.writeValueAsString(hs);
    }

    @PostMapping("/setMajor")
    @ResponseBody
    public String setMajor(@RequestBody Map<String, Object> para) throws JsonProcessingException {
        HashMap<String, Object> hs = new HashMap<>();
        String method=(String) para.get("method");
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println("\nsetMajor");
        System.out.println("方法:" + method);
        if(method.equals("0"))
        {//插入新的专业
            String major = (String) para.get("major");
            String college =(String)para.get("college");
            System.out.println("major:" + major+"college:"+college);
            try {
                subjectMapper.insertSubject(major,college);
                hs=ResultFactory.resultBack(Result.Code.SUCCESS,Result.Msg.INSERTSUCCESS);
            }catch (Exception e)
            {
                e.printStackTrace();
                hs=ResultFactory.resultBack(Result.Code.CONTROL_ERR,Result.Msg.INSERTERR);
            }
        }else if(method.equals("1"))
        {
            String newName=(String) para.get("newName");
            String oldName=(String) para.get("oldName");
            System.out.println("新:" + newName+"  原:  "+oldName);
            String[] arr={"subject","users","class","tableinfo"};
            try{
                for(int i=0;i<4;i++)
                {
                    subjectMapper.updateSubject(arr[i],newName,oldName);
                }
                hs=ResultFactory.resultBack(Result.Code.SUCCESS,Result.Msg.UPDATESUCCESS);
            }catch (Exception e)
            {
                e.printStackTrace();
                hs=ResultFactory.resultBack(Result.Code.CONTROL_ERR,Result.Msg.UPDATEERR);
            }
        }
        return objectMapper.writeValueAsString(hs);
    }

    @PostMapping("/setClass")
    @ResponseBody
    public String setClassName(@RequestBody Map<String, Object> para) throws JsonProcessingException {
        HashMap<String, Object> hs = new HashMap<>();
        String method=(String) para.get("method");
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println("\nsetClass");
        System.out.println("方法:" + method);
        if(method.equals("0"))
        {//插入新的班级
            String classname = (String) para.get("classname");
            String major =(String)para.get("subject");
            System.out.println("classname:" + classname+"subject:"+major);
            try {
                classNameService.insertClass(classname,major);
                hs=ResultFactory.resultBack(Result.Code.SUCCESS,Result.Msg.INSERTSUCCESS);
            }catch (Exception e)
            {
                e.printStackTrace();
                hs=ResultFactory.resultBack(Result.Code.CONTROL_ERR,Result.Msg.INSERTERR);
            }
        }else if(method.equals("1"))
        {
            String newName=(String) para.get("newName");
            String oldName=(String) para.get("oldName");
            System.out.println("新:" + newName+"  原:  "+oldName);
            String[] arr={"users","class","tableinfo"};
            try{
                for(int i=0;i<3;i++)
                {
                    classNameService.updateClass(arr[i],newName,oldName);
                }
                hs=ResultFactory.resultBack(Result.Code.SUCCESS,Result.Msg.UPDATESUCCESS);
            }catch (Exception e)
            {
                e.printStackTrace();
                hs=ResultFactory.resultBack(Result.Code.CONTROL_ERR,Result.Msg.UPDATEERR);
            }
        }
        return objectMapper.writeValueAsString(hs);
    }

    @PostMapping("/setBrace")
    @ResponseBody
    public String setBrace(@RequestBody Map<String, Object> para) throws JsonProcessingException {
        HashMap<String, Object> hs = new HashMap<>();
        String method=(String) para.get("method");
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println("\nsetBrace");
        System.out.println("方法:" + method);
        if(method.equals("0"))
        {//插入新的职称
            String name = (String) para.get("name");
            System.out.println("name:" + name);
            try {
                baseInfoService.insertBrace(name);
                hs=ResultFactory.resultBack(Result.Code.SUCCESS,Result.Msg.INSERTSUCCESS);
            }catch (Exception e)
            {
                e.printStackTrace();
                hs=ResultFactory.resultBack(Result.Code.CONTROL_ERR,Result.Msg.INSERTERR);
            }
        }else if(method.equals("1"))
        {
            String newName=(String) para.get("newName");
            String oldName=(String) para.get("oldName");
            System.out.println("新:" + newName+"  原:  "+oldName);
//            String[] arr={"college","department","subject","users"};
            String[] arr={"brace","users"};
            try{
                for(int i=0;i<2;i++)
                {
                    baseInfoService.updateBrace(arr[i],newName,oldName);
                }
                hs=ResultFactory.resultBack(Result.Code.SUCCESS,Result.Msg.UPDATESUCCESS);
            }catch (Exception e)
            {
                e.printStackTrace();
                hs=ResultFactory.resultBack(Result.Code.CONTROL_ERR,Result.Msg.UPDATEERR);
            }
        }
        return objectMapper.writeValueAsString(hs);
    }

    @PostMapping("/setFunction")
    @ResponseBody
    public String setFunction(@RequestBody Map<String, Object> para) throws JsonProcessingException {
        HashMap<String, Object> hs = new HashMap<>();
        int id=(int) para.get("id");
        int state=(int)para.get("state");
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println("\nsetFunction");
        System.out.println("id:" + id);
        try{
            functionService.updateFunction(state,id);
            hs=ResultFactory.resultBack(Result.Code.SUCCESS,Result.Msg.UPDATESUCCESS);
        }catch (Exception e)
        {
            e.printStackTrace();
            hs=ResultFactory.resultBack(Result.Code.CONTROL_ERR,Result.Msg.UPDATEERR);

        }

        return objectMapper.writeValueAsString(hs);
    }
}
