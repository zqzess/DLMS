package com.zqzess.dlms.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zqzess.dlms.bean.Function;
import com.zqzess.dlms.bean.Result;
import com.zqzess.dlms.bean.TableInfo;
import com.zqzess.dlms.bean.User;
import com.zqzess.dlms.service.FunctionService;
import com.zqzess.dlms.service.TableContentService;
import com.zqzess.dlms.service.TableInfoService;
import com.zqzess.dlms.service.UserService;
import com.zqzess.dlms.utils.ResultFactory;
import com.zqzess.dlms.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author zqzess
 * @Date 2021/10/27
 * @File UserController.java
 * @Package com.zqzess.dlms.controller
 * @Version 1.0
 * @Github https://github.com/zqzess
 * @Msg 亦余心之所善兮, 虽九死其尤未毁
 **/

@CrossOrigin
@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    FunctionService functionService;

    @Autowired
    TableContentService tableContentService;

    @Autowired
    TableInfoService tableInfoService;

    @PostMapping("/login")
    @ResponseBody
    public String login(@RequestHeader Map<String, Object> he, @RequestBody Map<String, Object> para) throws JsonProcessingException {
        System.out.println("请求头" + he);
        String userid = (String) para.get("userid");
        System.out.println("用户id:" + userid);
        long id = Long.parseLong(userid);
        String password = (String) para.get("password");
        User u = userService.getUserInfo(id);
        HashMap<String, Object> rs = new HashMap<>();
        if (u == null) {
            rs = ResultFactory.resultBack(Result.Msg.NOACCOUNT);
        } else {
            if (!u.getPassword().equals(password)) {
                System.out.println("错误密码:" + password);
                System.out.println("正确密码:" + u.getPassword());
                rs = ResultFactory.resultBack(Result.Msg.PWDERR);
            } else {
                String token = TokenUtil.sign(u);
                rs = ResultFactory.resultBack(Result.Code.SUCCESS, Result.Msg.LOGIN, token, u.getType());
            }
        }
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(rs);
    }

    @GetMapping("/test")
    @ResponseBody
    public String test() {
        return "123";
    }

    @PostMapping("/getUser")
    @ResponseBody
    public String getUser(@RequestBody Map<String, Object> para) throws JsonProcessingException {
        HashMap<String, Object> hs = new HashMap<>();
        Object object=para.get("userid");
        System.out.println("\ngetUser");
        long userid;
        if(object instanceof String)
        {
            userid = Long.parseLong((String) para.get("userid"));
            System.out.println("查询id:" + userid);
            User u = userService.getUserInfo(userid);
            hs = ResultFactory.resultBack(Result.Code.SUCCESS, u);
        }else if(object instanceof Long)
        {
            userid=(long)para.get("userid");
            System.out.println("查询id:" + userid);
            User u = userService.getUserInfo(userid);
            hs = ResultFactory.resultBack(Result.Code.SUCCESS, u);
        }else if(object instanceof Integer){
            userid = (long)((int)para.get("userid"));
            System.out.println("查询id:" + userid);
            User u = userService.getUserInfo(userid);
            hs = ResultFactory.resultBack(Result.Code.SUCCESS, u);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(hs);
    }

    @GetMapping("/getUserWithAccess")
    @ResponseBody
    public String getUserWithAccess(@RequestParam(name = "userid",required = true) String id ,@RequestParam(name = "typeid", required = true) int typeid) throws JsonProcessingException {
        HashMap<String, Object> hs = new HashMap<>();
        System.out.println("\ngetUserWithAccess");
        long userid = Long.parseLong(id);
        System.out.println("查询id:" + userid);
        User u = userService.getUserInfo(userid);
        List<Function> function;
        if(typeid==5)
        {
            function=functionService.getAdminFunction();
        }else
        {
            function=userService.getTypeFunction(typeid);
        }
        hs = ResultFactory.resultBack(Result.Code.SUCCESS, u,function);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(hs);
    }

    //    @PostMapping("/getController")
//    @ResponseBody
//    public String getController(@RequestBody Map<String,Object> para) throws JsonProcessingException{
//        HashMap<String,Object> hs=new HashMap<>();
//        int type=(int)para.get("type");
//        Function function=userService.getTypeFunction(type);
//        hs=ResultFactory.resultBack(Result.Code.SUCCESS,function);
//        ObjectMapper objectMapper=new ObjectMapper();
//        return objectMapper.writeValueAsString(hs);
//    }
    @GetMapping("/getController")
    @ResponseBody
    public String getUserById(@RequestParam(name = "method",required = true) int method ,@RequestParam(name = "typeid", required = true) int typeid) throws JsonProcessingException {
        HashMap<String, Object> hs = new HashMap<>();
        System.out.println("\ngetUserWithAccess");
        System.out.println("方法:"+method);
        System.out.println("查询值:"+typeid);
        if (method == 0) {
            //依据身份查询其所有权限
            List<Function> function=userService.getTypeFunction(typeid);
            hs=ResultFactory.resultBack(Result.Code.SUCCESS,function);
        }else if(method==1)
        {
            //依据权限id查询权限
            Function function=userService.getFunction(typeid);
            hs=ResultFactory.resultBack(Result.Code.SUCCESS,function);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(hs);
    }

    @PostMapping("/setUser")
    @ResponseBody
    public String setUser(@RequestBody Map<String, Object> para) throws JsonProcessingException {
        HashMap<String, Object> hs = new HashMap<>();
        String method=(String) para.get("method");
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println("\nsetUser");
        System.out.println("方法:" + method);
        if(method.equals("0"))
        {//插入新的用户
            String name=(String)para.get("name");
            String gender=(String)para.get("gender");
            String peopleid=(String)para.get("peopleid");
            String college=(String)para.get("college");
            String department=(String)para.get("department");
            String subject=(String)para.get("subject");
            String classname=(String)para.get("classname");
            String brace=(String)para.get("brace");
            int state=(int)para.get("state");
//            int type=Integer.parseInt((String)para.get("type"));
            int type=(int)para.get("type");
            long phone=0;
            Object object=para.get("phone");
            if(object instanceof String)
            {
                String tmp=(String)para.get("phone");
                if(tmp.isEmpty())
                {
                    phone=0;
                }else {
                    phone=Long.parseLong((String)para.get("phone"));
                }
            }else if(object instanceof Long)
            {
                phone=(long)para.get("phone");
            }
            String address=(String)para.get("address");
            String password=(String)para.get("password");
            try {
                userService.insertUser(name,gender,peopleid,college,department,subject,classname,brace,state,type,phone,address,password);
                hs=ResultFactory.resultBack(Result.Code.SUCCESS,Result.Msg.INSERTSUCCESS);
            }catch (Exception e)
            {
                e.printStackTrace();
                hs=ResultFactory.resultBack(Result.Code.CONTROL_ERR,Result.Msg.INSERTERR);
            }
        }else if(method.equals("1"))
        {
            String name=(String)para.get("name");
            int id=(int)para.get("id");
            String peopleid=(String)para.get("peopleid");
            String college=(String)para.get("college");
            String department=(String)para.get("department");
            String subject=(String)para.get("subject");
            String classname=(String)para.get("classname");
            String brace=(String)para.get("brace");
            int state=(int)para.get("state");
            int type=(int)para.get("type");
            long phone=0;
            Object object=para.get("phone");
            if(object instanceof String)
            {
                String tmp=(String)para.get("phone");
                if(tmp.isEmpty())
                {
                    phone=0;
                }else {
                    phone=Long.parseLong((String)para.get("phone"));
                }
            }else if(object instanceof Long)
            {
                phone=(long)para.get("phone");
            }
//            try{
//                phone=Long.parseLong((String)para.get("phone"));
//            }catch (Exception e){
//                e.printStackTrace();
//                phone=(long)para.get("phone");
//            }
            String address=(String)para.get("address");
            String password=(String)para.get("password");
            try{
                for(int i=0;i<3;i++)
                {
                    userService.updateUser(name,peopleid,college,department,subject,classname,brace,state,type,phone,address,password,id);
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

    @GetMapping("/getUserList")
    @ResponseBody
    public String getUserList(@RequestParam(name = "type",required = true) int type) throws JsonProcessingException {
        HashMap<String, Object> hs = new HashMap<>();
        System.out.println("\ngetUserList");
        System.out.println("查询type:" + type);
        List<User> u=userService.getUserWithType(type);
        hs = ResultFactory.resultBack(Result.Code.SUCCESS, u);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(hs);
    }
    @GetMapping("/searchUser")
    @ResponseBody
    public String searchUser(@RequestParam(name = "method",required = true) int method ,@RequestParam(name = "id",required = false) int id,@RequestParam(name = "nameString",required = false) String nameString) throws JsonProcessingException {
        HashMap<String, Object> hs = new HashMap<>();
        System.out.println("\nsearchUser");
        System.out.println("method:" + method);
        List<User> u;
        if(method==0)
        {//依据身份查询
            String searchid="'%"+id+"%'";
            u=userService.searchUser("type",searchid);
            hs = ResultFactory.resultBack(Result.Code.SUCCESS, u);
        }else if(method==1)
        {//依据id查询
            String searchid="'%"+id+"%'";
            u=userService.searchUser("id",searchid);
            hs = ResultFactory.resultBack(Result.Code.SUCCESS, u);
        }else if(method==2)
        {//依据某个字符串模糊查询
            String name="%"+nameString+"%";
//            name=name.replace("%\"","%");
            System.out.println("查询字符:"+name);
            u=userService.searchUserWitchString(name);
            hs = ResultFactory.resultBack(Result.Code.SUCCESS, u);
        }
//        hs = ResultFactory.resultBack(Result.Code.SUCCESS, u);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(hs);
    }

    @GetMapping("/deleteUser")
    @ResponseBody
    public String deleteUser(@RequestParam(name = "method", required = true) int method,@RequestParam(name = "userid") long userid) throws JsonProcessingException {
        HashMap<String, Object> hs = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println("\ndeleteUser");
        System.out.println("方法:" + method);
        if(method==0)
        {//删除表
            System.out.println("userid:" + userid);
            try{
                List<TableInfo> list= tableInfoService.getTableListWithCreatorId(userid);
                userService.deleteUser(userid);
                tableContentService.deleteTableContentWithUser(userid);
                for(int i=0;i<list.size();i++)
                {
                    tableContentService.deleteTableContent(list.get(i).getId());
                }
                tableInfoService.deleteTableInfoWithUser(userid);
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
