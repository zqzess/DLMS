package com.zqzess.dlms.mapper;

import com.zqzess.dlms.bean.Subject;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.GetMapping;

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
public interface SubjectMapper {
    @Select("SELECT * FROM `subject`;")
    List<Subject> getAllSubject();

    @Select("INSERT INTO subject(subject,college) VALUES(#{subject},#{college});")
    void insertSubject(String subject,String college);

    @Select("UPDATE ${table} SET `subject`=#{newName} WHERE subject=#{oldName};")
    void updateSubject(String table,String newName,String oldName);

    @Select("SELECT * FROM `subject` WHERE college=#{name};;")
    List<Subject> getSubject(String name);
}
