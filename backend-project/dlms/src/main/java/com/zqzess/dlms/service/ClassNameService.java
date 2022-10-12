package com.zqzess.dlms.service;

import com.zqzess.dlms.bean.ClassName;
import com.zqzess.dlms.mapper.ClassNameMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author zqzess
 * @Date 2021/11/6
 * @Project dlms
 * @Package com.zqzess.dlms.service
 * @Version 1.0
 * @Github https://github.com/zqzess
 * @Msg 亦余心之所善兮, 虽九死其尤未毁
 **/
@Service
public class ClassNameService implements ClassNameMapper {
    @Autowired
    ClassNameMapper classNameMapper;

    @Override
    public List<ClassName> getAllClass() {
        return classNameMapper.getAllClass();
    }

    @Override
    public void insertClass(String classname, String subject) {
        classNameMapper.insertClass(classname,subject);
    }

    @Override
    public void updateClass(String table, String newName, String oldName) {
        classNameMapper.updateClass(table,newName,oldName);
    }

    @Override
    public List<ClassName> getClass(String name) {
        return classNameMapper.getClass(name);
    }
}
