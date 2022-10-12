package com.zqzess.dlms.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author zqzess
 * @Date 2021/11/5
 * @Project dlms
 * @Package com.zqzess.dlms.mapper
 * @Version 1.0
 * @Github https://github.com/zqzess
 * @Msg 亦余心之所善兮, 虽九死其尤未毁
 **/
@Mapper
public interface BaseInfoMapper {
    @Select("SELECT * FROM college;")
    List<String> getAllCollege();

//    @Results({ @Result(property = "userName", column = "user_name", javaType = User.class) })
    @Select("INSERT INTO college(college) VALUES(#{name});")
    void insertCollege(String name);

//    @Select("UPDATE college SET `name`=#{newName} WHERE name=#{oldName};" +
//            "UPDATE department SET `college`=#{newName} WHERE college=#{oldName};" +
//            "UPDATE `subject` SET `college`=#{newName} WHERE college=#{oldName};" +
//            "UPDATE users SET `college`=#{newName} WHERE college=#{oldName};")
    @Select("UPDATE ${table} SET `college`=#{newName} WHERE college=#{oldName};")
    void updateCollege(String table,String newName,String oldName);

    @Select("SELECT * FROM department;")
    List<String> getAllDepartment();

    @Select("INSERT INTO department(department) VALUES(#{name});")
    void insertDepartment(String name);

    @Select("UPDATE ${table} SET `department`=#{newName} WHERE department=#{oldName};")
    void updateDepartment(String table,String newName,String oldName);

    @Select("SELECT * FROM brace;")
    List<String> getAllBrace();

    @Select("INSERT INTO brace(brace) VALUES(#{name});")
    void insertBrace(String name);

    @Select("UPDATE ${table} SET `brace`=#{newName} WHERE brace=#{oldName};")
    void updateBrace(String table,String newName,String oldName);
}
