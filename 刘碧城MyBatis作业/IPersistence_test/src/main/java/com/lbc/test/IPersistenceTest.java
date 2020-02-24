package com.lbc.test;

import com.lbc.dao.IUserDao;
import com.lbc.io.Resources;
import com.lbc.pojo.User;
import com.lbc.sqlSession.SqlSession;
import com.lbc.sqlSession.SqlSessionFactory;
import com.lbc.sqlSession.SqlSessionFactoryBuilder;
import org.dom4j.DocumentException;
import org.junit.Test;

import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.util.List;

public class IPersistenceTest {
//    @Test
//    public void test() throws Exception {
//        InputStream resourceAsStream = Resources.getResourceAsStream("SqlMapConfig.xml");
//        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
//        SqlSession sqlSession = sqlSessionFactory.openSession();
//        User user = new User();
//        user.setId(1);
//        user.setUsername("lisi");
////        User user2 = sqlSession.selectOne("user.selectOne", user);
////        System.out.println(user2);
//
////        List<User> users = sqlSession.selectList("user.selectList");
////        for (User user1 : users) {
////            System.out.println(user1);
////        }
//        IUserDao userDao = sqlSession.getMapper(IUserDao.class);
//        User user1 = userDao.findByCondition(user);
//        System.out.println(user1);
////        List<User> users = userDao.findAll();
////        for (User user1 : users) {
////            System.out.println(user1);
////        }
//    }

    @Test
    public void addTest() throws Exception {
        InputStream resourceAsStream = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        User user = new User();
        user.setId(5);
        user.setUsername("张三");
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);
        userDao.addUser(user);
    }
    @Test
    public void updateTest() throws Exception {
        InputStream resourceAsStream = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        User user = new User();
        user.setId(5);
        user.setUsername("张wu");
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);
        userDao.updateUser(user);
    }

    @Test
    public void deleteTest() throws Exception {
        InputStream resourceAsStream = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);
        userDao.deleteUser(5);
    }
}
