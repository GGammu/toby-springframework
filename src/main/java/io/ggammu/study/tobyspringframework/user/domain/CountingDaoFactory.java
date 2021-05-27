package io.ggammu.study.tobyspringframework.user.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CountingDaoFactory {

    @Bean
    public UserDaoJdbc userDao() {
//        return new UserDao(connectionMaker());
        return new UserDaoJdbc();
    }

    @Bean
    public ConnectionMaker connectionMaker() {
        return new CountingConnectionMaker(realConnectionMaker());
    }

    @Bean
    public ConnectionMaker realConnectionMaker() {
        return new LocalDBConnectionMaker();
    }

}
