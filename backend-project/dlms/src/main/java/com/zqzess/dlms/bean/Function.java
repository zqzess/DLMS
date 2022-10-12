package com.zqzess.dlms.bean;

import lombok.Data;

/**
 * @Author zqzess
 * @Date 2021/11/2
 * @Project dlms
 * @Package com.zqzess.dlms.bean
 * @Version 1.0
 * @Github https://github.com/zqzess
 * @Msg 亦余心之所善兮, 虽九死其尤未毁
 **/
@Data
public class Function {
    int id;
    String name;
    int type;
    int state;
}
