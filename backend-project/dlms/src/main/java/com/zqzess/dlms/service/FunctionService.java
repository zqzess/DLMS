package com.zqzess.dlms.service;

import com.zqzess.dlms.bean.Function;
import com.zqzess.dlms.mapper.FunctionMapper;
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
public class FunctionService implements FunctionMapper {
    @Autowired
    FunctionMapper functionMapper;


    @Override
    public List<Function> getAllFunction() {
        return functionMapper.getAllFunction();
    }

    @Override
    public List<Function> getAdminFunction() {
        return functionMapper.getAdminFunction();
    }

    @Override
    public void updateFunction(int state, int id) {
        functionMapper.updateFunction(state,id);
    }
}
