package com.lbc.sqlSession;

import java.util.List;

public interface SqlSession {
    public <E> List<E> selectList(String statementid,Object... params) throws Exception;
    public  <T> T selectOne(String statementid,Object... params) throws Exception;
    public  <T> T addUser(String statementid, Object... params) throws Exception;
    public  <T> T updateUser(String statementid,Object... params) throws Exception;
    public  <T> T deleteUser(String statementid,int params) throws Exception;
    public <T> T getMapper(Class<?> mapperClass);
}
