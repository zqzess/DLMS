package com.zqzess.dlms.mapper;

import com.zqzess.dlms.bean.TableContent;
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
public interface TableContentMapper {
    @Select("SELECT * FROM tablecontent WHERE tableid=#{id};")
    List<TableContent> getTableContentWithId(long id);

    @Select("SELECT * FROM tablecontent WHERE userid=#{id};")
    List<TableContent> getTableContentWithUser(long id);

    @Select("INSERT INTO tablecontent(tableid,userid,jsoncontent) VALUES(#{tableid},#{userid},#{jsoncontent});")
    void inserTableContent(long tableid,long userid,String jsoncontent);

    @Select("DELETE FROM tablecontent WHERE tableid=#{id}")
    void deleteTableContent(long id);

    @Select("DELETE FROM tablecontent WHERE userid=#{id}")
    void deleteTableContentWithUser(long id);
}
