package io.younghwang.springframewokbasic.user.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserDaoFactory {
    @Bean
    public UserDao userDao() {
        return new UserDao(getConnectionMaker());
    }

    @Bean
    public AccountDao accountDao() {
        return new AccountDao(getConnectionMaker());
    }

    @Bean
    public MessageDao messageDao() {
        return new MessageDao(getConnectionMaker());
    }

    @Bean
    public DConnectionMaker getConnectionMaker() {
        return new DConnectionMaker();
    }
}
