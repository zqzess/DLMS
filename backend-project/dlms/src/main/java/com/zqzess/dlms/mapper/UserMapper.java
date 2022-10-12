package com.zqzess.dlms.mapper;

import com.zqzess.dlms.bean.Function;
import com.zqzess.dlms.bean.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @Author zqzess
 * @Date 2021/10/27
 * @File UserMapper.java
 * @Package com.zqzess.dlms.mapper
 * @Version 1.0
 * @Github https://github.com/zqzess
 * @Msg 亦余心之所善兮, 虽九死其尤未毁
 **/

@Mapper
public interface UserMapper {
    @Select("select * from users WHERE users.id = #{id}")
    User getUserInfo(long id);

    @Select("SELECT * FROM `function` WHERE type = #{type};")
    List<Function> getTypeFunction(int type);

    @Select("SELECT * FROM `function` WHERE id = #{id};")
    Function getFunction(int id);

    @Select("UPDATE users SET `name`=#{name},peopleid=#{peopleid},college=#{college},department=#{department},`subject`=#{subject},classname=#{classname},`brace`=#{brace},`state`=#{state},type=#{type},phone=#{phone},address=#{address},`password`=#{password} WHERE id=#{id};")
    void updateUser(String name, String peopleid ,String college, String department, String subject, String classname, String brace, int state, int type, long phone, String address, String password, int id);

    @Select("SELECT * FROM users WHERE type<#{type};")
    List<User> getUserWithType(int type);

    @Select("SELECT * FROM users WHERE ${method} LIKE ${id}")
    List<User> searchUser(String method, String id);

    @Select("SELECT * FROM users WHERE `name` LIKE #{name} OR college LIKE #{name} OR `department` LIKE #{name} OR `subject` LIKE #{name} OR `classname` LIKE #{name};")
    List<User> searchUserWitchString(String name);

    @Select("INSERT INTO users(`name`,gender,peopleid,college,department,`subject`,classname,brace,state,type,phone,address,`password`) VALUES(#{name},#{gender},#{peopleid},#{college},#{department},#{subject},#{classname},#{brace},#{state},#{type},#{phone},#{address},#{password})")
    void insertUser(String name, String gender, String peopleid , String college, String department, String subject, String classname, String brace, int state, int type, long phone, String address, String password);

    @Select("DELETE FROM users WHERE id=#{id}")
    void deleteUser(long id);
}
