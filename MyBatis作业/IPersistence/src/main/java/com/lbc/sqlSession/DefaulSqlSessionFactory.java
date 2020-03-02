package com.lbc.sqlSession;

import com.lbc.pojo.Configuration;

public class DefaulSqlSessionFactory implements SqlSessionFactory {
    Configuration configuration;

    public DefaulSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public SqlSession openSession() {
        return new DefaultSqlSession(configuration) {
        };
    }
}
