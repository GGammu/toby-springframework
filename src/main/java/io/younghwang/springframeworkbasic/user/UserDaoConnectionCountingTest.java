package io.younghwang.springframeworkbasic.user;

import io.younghwang.springframeworkbasic.user.dao.CountingConnectionMaker;
import io.younghwang.springframeworkbasic.user.dao.CountingDaoFactory;
import io.younghwang.springframeworkbasic.user.dao.UserDao;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class UserDaoConnectionCountingTest {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(CountingDaoFactory.class);
        UserDao userDao = context.getBean("userDao", UserDao.class);

        CountingConnectionMaker ccm = context.getBean("connectionMaker", CountingConnectionMaker.class);
        System.out.println("Connection counter : " + ccm.getCount());
    }
}
