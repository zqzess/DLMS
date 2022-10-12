package com.zqzess.dlms.mapper;

import com.zqzess.dlms.bean.Function;
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
public interface FunctionMapper {
    @Select("SELECT * FROM `function`;")
    List<Function> getAllFunction();

    @Select("SELECT * FROM `function` WHERE type = 2 OR type = 3;")
    List<Function> getAdminFunction();

    @Select("UPDATE `function` SET state=#{state} WHERE id=#{id};")
    void updateFunction(int state,int id);
}
