package io.ggammu.study.tobyspringframework.user.domain;

import javax.xml.crypto.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class TestDaoFactory {

    @Bean
    public UserDaoJdbc userDao() {
        UserDaoJdbc userDao = new UserDaoJdbc();
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

    @Bean
    public UserLevelUpgradePolicy userLevelUpgradePolicy() {
        return new ImplUserLevelUpgradePolicy();
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean
    public JavaMailSenderImpl mailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("mail.server.com");
        return mailSender;
    }

    @Bean
    public UserService userService() {
        UserService userService = new UserService();
        userService.setUserDao(userDao());
        userService.setTransactionManager(transactionManager());
        userService.setMailSender(mailSender());
        return userService;
    }

}
