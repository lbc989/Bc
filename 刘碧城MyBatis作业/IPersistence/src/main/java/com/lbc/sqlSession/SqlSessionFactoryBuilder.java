package com.lbc.sqlSession;

import com.lbc.config.XMLConfigBuilder;
import com.lbc.pojo.Configuration;
import org.dom4j.DocumentException;

import java.beans.PropertyVetoException;
import java.io.InputStream;

public class SqlSessionFactoryBuilder {
    public  SqlSessionFactory build(InputStream in) throws DocumentException, PropertyVetoException {
        XMLConfigBuilder xmlConfigBuilder = new XMLConfigBuilder();
        Configuration configuration = xmlConfigBuilder.parseConfig(in);
        DefaulSqlSessionFactory defaulSqlSessionFactory = new DefaulSqlSessionFactory(configuration);
        return defaulSqlSessionFactory;
    }
}
