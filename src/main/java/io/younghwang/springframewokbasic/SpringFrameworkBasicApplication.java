package io.younghwang.springframewokbasic;

import io.younghwang.springframewokbasic.user.dao.UserDao;
import io.younghwang.springframewokbasic.user.dao.UserDaoFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;

public class SpringFrameworkBasicApplication {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        UserDaoFactory factory = new UserDaoFactory();
        UserDao userDao1 = factory.userDao();
        UserDao userDao2 = factory.userDao();
        System.out.println(userDao1);
        System.out.println(userDao2);

        ApplicationContext context = new AnnotationConfigApplicationContext(UserDaoFactory.class);
        UserDao userDao3 = context.getBean("userDao", UserDao.class);
        UserDao userDao4 = context.getBean("userDao", UserDao.class);
        System.out.println(userDao3);
        System.out.println(userDao4);
    }
}
