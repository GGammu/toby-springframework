package io.younghwang.springframeworkbasic.user.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;

@Configuration
public class TestUserDaoFactory {
    @Bean
    public UserDao userDao() {
        UserDao userDao = new UserDao();
        userDao.setDataSource(dataSource());
        userDao.setJdbcContext(jdbcContext());
        return userDao;
    }

    @Bean
    public JdbcContext jdbcContext() {
        JdbcContext jdbcContext = new JdbcContext();
        jdbcContext.setDataSource(dataSource());
        return jdbcContext;
    }

    @Bean
    public DataSource dataSource() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(org.h2.Driver.class);
        dataSource.setUrl("jdbc:h2:tcp://localhost/~/spring-framework-basic");
        dataSource.setUsername("sa");
        dataSource.setPassword("");
        return dataSource;
    }
}
