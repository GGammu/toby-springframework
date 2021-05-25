package io.ggammu.study.tobyspringframework.user.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;

@Configuration
public class TestDaoFactory {
    @Bean
    public UserDao userDao() {
        UserDao userDao = new UserDao();
//        userDao.setDataSource(dataSource());
//        userDao.setJdbcContext(jdbcContext());
        userDao.setDataSource(dataSource());
        userDao.setUserRowMapper(userRowMapper());
        return userDao;
    }

    @Bean
    public DataSource dataSource() {
        SimpleDriverDataSource simpleDriverDataSource = new SimpleDriverDataSource();
        simpleDriverDataSource.setDriverClass(com.mysql.jdbc.Driver.class);
        simpleDriverDataSource.setUrl("jdbc:mysql://localhost/toby_spring");
        simpleDriverDataSource.setUsername("spring");
        simpleDriverDataSource.setPassword("password");
        return simpleDriverDataSource;
    }

    @Bean
    public RowMapper<User> userRowMapper() {
        return new UserRowMapper();
    }

    @Bean
    public AccountDao accountDao() {
        AccountDao accountDao = new AccountDao(connectionMaker());
        return accountDao;
    }

    @Bean
    public MessageDao messageDao() {
        MessageDao messageDao = new MessageDao(connectionMaker());
        return messageDao;
    }

    @Bean
    public ConnectionMaker connectionMaker() {
        return new LocalDBConnectionMaker();
    }
}
