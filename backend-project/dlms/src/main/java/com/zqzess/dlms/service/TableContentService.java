package com.zqzess.dlms.service;

import com.zqzess.dlms.bean.TableContent;
import com.zqzess.dlms.mapper.TableContentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author zqzess
 * @Date 2021/11/8
 * @Project dlms
 * @Package com.zqzess.dlms.service
 * @Version 1.0
 * @Github https://github.com/zqzess
 * @Msg 亦余心之所善兮, 虽九死其尤未毁
 **/
@Service
public class TableContentService implements TableContentMapper {
    @Autowired
    TableContentMapper tableContentMapper;
    @Override
    public List<TableContent> getTableContentWithId(long id) {
        return tableContentMapper.getTableContentWithId(id);
    }

    @Override
    public List<TableContent> getTableContentWithUser(long id) {
        return tableContentMapper.getTableContentWithUser(id);
    }

    @Override
    public void inserTableContent(long tableid, long userid, String jsoncontent) {
        tableContentMapper.inserTableContent(tableid, userid, jsoncontent);
    }

    @Override
    public void deleteTableContent(long id) {
        tableContentMapper.deleteTableContent(id);
    }

    @Override
    public void deleteTableContentWithUser(long id) {
        tableContentMapper.deleteTableContentWithUser(id);
    }
}
