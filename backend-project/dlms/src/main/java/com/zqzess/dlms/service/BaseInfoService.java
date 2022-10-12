package com.zqzess.dlms.service;

import com.zqzess.dlms.mapper.BaseInfoMapper;
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
public class BaseInfoService implements BaseInfoMapper {
    @Autowired
    BaseInfoMapper baseInfoMapper;

    @Override
    public List<String> getAllCollege() {
        return baseInfoMapper.getAllCollege();
    }

    @Override
    public void insertCollege(String name) {
        baseInfoMapper.insertCollege(name);
    }

    @Override
    public void updateCollege(String table,String newName, String oldName) {
        baseInfoMapper.updateCollege(table,newName,oldName);
    }

    @Override
    public List<String> getAllDepartment() {
        return baseInfoMapper.getAllDepartment();
    }

    @Override
    public void insertDepartment(String name) {
        baseInfoMapper.insertDepartment(name);
    }

    @Override
    public void updateDepartment(String table,String newName, String oldName) {
        baseInfoMapper.updateDepartment(table,newName,oldName);
    }

    @Override
    public List<String> getAllBrace() {
        return baseInfoMapper.getAllBrace();
    }

    @Override
    public void insertBrace(String name) {
        baseInfoMapper.insertBrace(name);
    }

    @Override
    public void updateBrace(String table, String newName, String oldName) {
        baseInfoMapper.updateBrace(table,newName,oldName);
    }


}
