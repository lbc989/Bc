package com.lbc.sqlSession;

import com.lbc.pojo.Configuration;
import com.lbc.pojo.MappedStatement;

import java.lang.reflect.*;
import java.util.List;

public class DefaultSqlSession implements SqlSession {
    private Configuration configuration;

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public <E> List<E> selectList(String statementid, Object... params) throws Exception {
        simpleExecutor simpleExecutor = new simpleExecutor();
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementid);
        List<Object> list = simpleExecutor.query(configuration, mappedStatement, params);
        return (List<E>) list;
    }

    @Override
    public <T> T selectOne(String statementid, Object... params) throws Exception {
        List<Object> objects = selectList(statementid,params);
        if(objects.size()==1)
        {
            return (T) objects.get(0);
        }
        else{
            throw new RuntimeException("查询为空或过多");
        }
    }

    @Override
    public <T> T addUser(String statementid, Object... params) throws Exception {
        simpleExecutor simpleExecutor = new simpleExecutor();
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementid);
        simpleExecutor.add(configuration,mappedStatement,params);
        return null;
    }


    @Override
    public <T> T updateUser(String statementid, Object... params) throws Exception {
        simpleExecutor simpleExecutor = new simpleExecutor();
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementid);
        simpleExecutor.update(configuration,mappedStatement,params);
        return null;
    }

    @Override
    public <T> T deleteUser(String statementid, int params) throws Exception {
        simpleExecutor simpleExecutor = new simpleExecutor();
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementid);
        simpleExecutor.delete(configuration,mappedStatement,params);
        return null;
    }


    @Override
    public <T> T getMapper(Class<?> mapperClass) {
        Object ProxyInstance = Proxy.newProxyInstance(DefaultSqlSession.class.getClassLoader(), new Class[]{mapperClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                String name = method.getName();
                String className = method.getDeclaringClass().getName();
                String statementid = className+"."+name;


                Type genericReturnType = method.getGenericReturnType();
                if(genericReturnType instanceof ParameterizedType){
                    List<Object> objects = selectList(statementid, args);
                    return objects;
                }
                else if(statementid.contains("addUser"))
                {
                   return addUser(statementid,args);
                }
                else if(statementid.contains("updateUser"))
                {
                    return updateUser(statementid,args);
                }
                else if(statementid.contains("deleteUser"))
                {
                    return deleteUser(statementid, (Integer) args[0]);
                }
                return selectOne(statementid,args);
            }
        });
        return (T) ProxyInstance;
    }
}
