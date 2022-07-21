package io.younghwang.springframewokbasic;

import io.younghwang.springframewokbasic.user.dao.UserDao;
import io.younghwang.springframewokbasic.user.dao.UserDaoFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;

public class SpringFrameworkBasicApplication {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        ApplicationContext context = new AnnotationConfigApplicationContext(UserDaoFactory.class);
        UserDao dao = context.getBean("userDao", UserDao.class);
    }
}
