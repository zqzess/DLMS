package com.zqzess.dlms.mapper;

import com.zqzess.dlms.bean.ClassName;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author zqzess
 * @Date 2021/11/6
 * @Project dlms
 * @Package com.zqzess.dlms.mapper
 * @Version 1.0
 * @Github https://github.com/zqzess
 * @Msg 亦余心之所善兮, 虽九死其尤未毁
 **/
@Mapper
public interface ClassNameMapper {
    @Select("SELECT * FROM `class`;")
    List<ClassName> getAllClass();

    @Select("INSERT INTO `class`(classname,subject) VALUES(#{classname},#{subject});")
    void insertClass(String classname,String subject);

    @Select("UPDATE ${table} SET `classname`=#{newName} WHERE classname=#{oldName};")
    void updateClass(String table,String newName,String oldName);

    @Select("SELECT * FROM `class` WHERE subject=#{name};")
    List<ClassName> getClass(String name);

}
