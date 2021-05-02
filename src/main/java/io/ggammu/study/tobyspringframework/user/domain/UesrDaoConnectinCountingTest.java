package io.ggammu.study.tobyspringframework.user.domain;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class UesrDaoConnectinCountingTest {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(CountingDaoFactory.class);
        UserDao userDao = context.getBean("userDao", UserDao.class);
        CountingConnectionMaker countingConnectionMaker = context.getBean("connectinoMaker", CountingConnectionMaker.class);
        System.out.println(countingConnectionMaker.getCounter());
    }
}
