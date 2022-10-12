package com.zqzess.dlms.service;

import com.zqzess.dlms.bean.Subject;
import com.zqzess.dlms.mapper.SubjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author zqzess
 * @Date 2021/11/5
 * @Project dlms
 * @Package com.zqzess.dlms.service
 * @Version 1.0
 * @Github https://github.com/zqzess
 * @Msg 亦余心之所善兮, 虽九死其尤未毁
 **/
@Service
public class SubjectService implements SubjectMapper {
    @Autowired
    SubjectMapper subjectMapper;
    @Override
    public List<Subject> getAllSubject() {
        return subjectMapper.getAllSubject();
    }

    @Override
    public void insertSubject(String subject, String college) {
        subjectMapper.insertSubject(subject,college);
    }

    @Override
    public void updateSubject(String table, String newName, String oldName) {
        subjectMapper.updateSubject(table,newName,oldName);
    }

    @Override
    public List<Subject> getSubject(String name) {
        return subjectMapper.getSubject(name);
    }
}
