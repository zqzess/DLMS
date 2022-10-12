package com.zqzess.dlms.mapper;

import com.zqzess.dlms.bean.TableInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author zqzess
 * @Date 2021/11/8
 * @Project dlms
 * @Package com.zqzess.dlms.mapper
 * @Version 1.0
 * @Github https://github.com/zqzess
 * @Msg 亦余心之所善兮, 虽九死其尤未毁
 **/
@Mapper
public interface TableInfoMapper {
//    @Select("SELECT DISTINCT * FROM tableinfo WHERE college=#{college} AND type=#{type} AND state=#{type}" +
//            "UNION SELECT * FROM tableinfo WHERE `subject`=#{subject} AND type=#{type} AND state=#{type}" +
//            "UNION SELECT * FROM tableinfo WHERE `classname`=#{classname} AND type=#{type} AND state=#{type}" +
//            "UNION SELECT * FROM tableinfo WHERE `department`=#{department} AND type=#{type} AND state=#{type};")

//    @Select("SELECT DISTINCT * FROM tableinfo WHERE college=#{college} AND `subject`=#{subject} AND `classname`=#{classname} AND `department`=#{department} AND type=#{type} AND state=#{state} ;")
    @Select("SELECT DISTINCT * FROM tableinfo WHERE (college=#{college} OR `subject`=#{subject} OR `${table}`=#{name}) AND type=#{type} AND state=#{state} UNION SELECT DISTINCT * FROM tableinfo WHERE type=#{type} AND state=#{state};")
    List<TableInfo> getTbaleInfo(String college,String subject,String table,String name,int type, int state);


    @Select("SELECT * FROM tableinfo;")
    List<TableInfo> getAllTable();

    @Select("SELECT * FROM tableinfo WHERE `creator_id`=#{id};")
    List<TableInfo> getTableListWithCreatorId(long id);

    @Select("SELECT * FROM tableinfo WHERE `${table}`=#{name};")
    List<TableInfo> getTableListWithTable(String table,String name);

    @Select("SELECT * FROM tableinfo WHERE id=#{id};")
    TableInfo getTable(long id);

    @Select("INSERT INTO tableinfo(name,creator_id,creator,state,college,department,`subject`,classname,type,table_structure) VALUES(#{name},#{creator_id},#{creator},#{state},#{college},#{department},#{subject},#{classname},#{type},#{table_structure});")
    void insertTableInfo(String name,long creator_id,String creator,int state,String college,String department,String subject,String classname,int type,String table_structure);

    @Select("DELETE FROM tableinfo WHERE id =#{id}")
    void deleteTableInfo(long id);

    @Select("DELETE FROM tableinfo WHERE creator_id =#{id}")
    void deleteTableInfoWithUser(long id);

    @Select("UPDATE tableinfo SET state=#{state} WHERE id=#{id}")
    void updateTableInfoState(int state,long id);

}
