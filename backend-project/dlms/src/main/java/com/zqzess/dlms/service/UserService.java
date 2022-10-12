package com.zqzess.dlms.service;

import com.zqzess.dlms.bean.Function;
import com.zqzess.dlms.bean.User;
import com.zqzess.dlms.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserMapper{
    @Autowired
    UserMapper userMapper;
    public User getUserInfo(long id){
        return userMapper.getUserInfo(id);
    }

    @Override
    public List<Function> getTypeFunction(int type) {
        return userMapper.getTypeFunction(type);
    }

    @Override
    public Function getFunction(int id) {
        return userMapper.getFunction(id);
    }

    @Override
    public void updateUser(String name, String peopleid, String college, String department, String subject, String classname, String brace, int state, int type, long phone, String address, String password, int id) {
        userMapper.updateUser(name,peopleid,college,department,subject,classname,brace,state,type,phone,address,password,id);
    }

    @Override
    public List<User> getUserWithType(int type) {
        return userMapper.getUserWithType(type);
    }

    @Override
    public List<User> searchUser(String method, String id) {
        return userMapper.searchUser(method, id);
    }

    @Override
    public List<User> searchUserWitchString(String name) {
        return userMapper.searchUserWitchString(name);
    }

    @Override
    public void insertUser(String name, String gender, String peopleid, String college, String department, String subject, String classname, String brace, int state, int type, long phone, String address, String password) {
        userMapper.insertUser(name, gender,peopleid, college, department, subject, classname, brace, state, type, phone, address, password);
    }

    @Override
    public void deleteUser(long id) {
        userMapper.deleteUser(id);
    }


}
