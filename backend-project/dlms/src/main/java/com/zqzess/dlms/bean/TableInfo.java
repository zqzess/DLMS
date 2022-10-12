package com.zqzess.dlms.bean;

import com.alibaba.fastjson.JSON;
import lombok.Data;

/**
 * @Author zqzess
 * @Date 2021/11/8
 * @Project dlms
 * @Package com.zqzess.dlms.bean
 * @Version 1.0
 * @Github https://github.com/zqzess
 * @Msg 亦余心之所善兮, 虽九死其尤未毁
 **/
@Data
public class TableInfo {
    long id;
    String name;
    long creatorId;
    String creator;
    String createTime;
    int state;
    String college;
    String department;
    String subject;
    String classname;
    int type;
    String tableStructure;
}
