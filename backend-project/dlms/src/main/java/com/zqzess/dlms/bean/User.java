package com.zqzess.dlms.bean;

import lombok.Data;
/**
 *
 * @Author zqzess
 * @Date 2021/10/27
 * @File User.java
 * @Package com.zqzess.dlms.bean
 * @Version 1.0
 * @Github https://github.com/zqzess
 * @Msg 亦余心之所善兮,虽九死其尤未毁
 *
 **/

@Data
public class User {
    long id;    //工号
    String name;    //姓名        必须
    String gender;   //性别       必须
    String peopleid;    //身份证号
    String college;  //学院
    String department;  //部门
    String subject;    //专业
    String classname;   //班级
    String brace;   //职称
    int state;   //在职状态或入学情况     必须 0在职，1离职，2未入学，3已入学，4毕业
    int type;    //身份类型          必须 0学生，1教师，2部门管理员，3学院管理员，4学校管理员
    long phone;   //手机号
    String address; //地址
    String password;    //密码        必须

}
