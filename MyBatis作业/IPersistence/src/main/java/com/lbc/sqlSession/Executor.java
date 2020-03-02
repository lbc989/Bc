package com.lbc.sqlSession;

import com.lbc.pojo.Configuration;
import com.lbc.pojo.MappedStatement;

import java.sql.ResultSet;
import java.util.List;

public interface Executor {
    public <E> List<E> query(Configuration configuration, MappedStatement mappedStatement,Object... params) throws Exception;
    public void add(Configuration configuration, MappedStatement mappedStatement, Object... params) throws Exception;
    public void update(Configuration configuration, MappedStatement mappedStatement,Object... params) throws Exception;
    public void delete(Configuration configuration, MappedStatement mappedStatement,int params) throws Exception;
}
