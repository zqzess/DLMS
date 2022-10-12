package com.zqzess.dlms.service;

import com.zqzess.dlms.bean.TableInfo;
import com.zqzess.dlms.mapper.TableInfoMapper;
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
public class TableInfoService implements TableInfoMapper {
    @Autowired TableInfoMapper tableInfoMapper;
    @Override
    public List<TableInfo> getTbaleInfo(String college, String subject, String table, String name, int type, int state) {
        return tableInfoMapper.getTbaleInfo(college, subject, table, name, type, state);
    }

    @Override
    public List<TableInfo> getAllTable() {
        return tableInfoMapper.getAllTable();
    }

    @Override
    public List<TableInfo> getTableListWithCreatorId(long id) {
        return tableInfoMapper.getTableListWithCreatorId(id);
    }

    @Override
    public List<TableInfo> getTableListWithTable(String table, String name) {
        return tableInfoMapper.getTableListWithTable(table, name);
    }

    @Override
    public TableInfo getTable(long id) {
        return tableInfoMapper.getTable(id);
    }

    @Override
    public void insertTableInfo(String name, long creator_id, String creator, int state, String college, String department, String subject, String classname, int type, String table_structure) {
        tableInfoMapper.insertTableInfo(name, creator_id, creator, state, college, department, subject, classname, type, table_structure);
    }

    @Override
    public void deleteTableInfo(long id) {
        tableInfoMapper.deleteTableInfo(id);
    }

    @Override
    public void deleteTableInfoWithUser(long id) {
        tableInfoMapper.deleteTableInfoWithUser(id);
    }

    @Override
    public void updateTableInfoState(int state,long id) {
        tableInfoMapper.updateTableInfoState(state,id);
    }
}
