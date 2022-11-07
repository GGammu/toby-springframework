package io.younghwang.springframeworkbasic;

import io.younghwang.springframeworkbasic.core.dao.CoreDao;
import io.younghwang.springframeworkbasic.core.service.CoreService;
import io.younghwang.springframeworkbasic.core.service.CoreServiceImpl;
import io.younghwang.springframeworkbasic.user.dao.UserDao;
import io.younghwang.springframeworkbasic.user.dao.UserDaoJdbc;
import io.younghwang.springframeworkbasic.user.service.DummyMailSender;
import io.younghwang.springframeworkbasic.user.service.TxProxyFactoryBean;
import io.younghwang.springframeworkbasic.user.service.UserLevelUpgradePolicy;
import io.younghwang.springframeworkbasic.user.service.UserLevelUpgradePolicyImpl;
import io.younghwang.springframeworkbasic.user.service.UserService;
import io.younghwang.springframeworkbasic.user.service.UserServiceImpl;
import io.younghwang.springframeworkbasic.user.service.UserServiceTest;
import io.younghwang.springframeworkbasic.user.sqlservice.SimpleSqlService;
import io.younghwang.springframeworkbasic.user.sqlservice.SqlService;
import io.younghwang.springframeworkbasic.user.sqlservice.XmlSqlService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.mail.MailSender;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
public class TestApplicationContext {
    @Bean
    public DataSource dataSource() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(org.h2.Driver.class);
        dataSource.setUrl("jdbc:h2:tcp://localhost/~/spring-framework-basic");
        dataSource.setUsername("sa");
        dataSource.setPassword("");
        return dataSource;
    }

    @Bean
    public XmlSqlService sqlService() {
        XmlSqlService xmlSqlService = new XmlSqlService();
        xmlSqlService.setSqlMapFile("sqlmap.xml");
        return xmlSqlService;
    }

    @Bean
    public UserDao userDao() {
        UserDaoJdbc userDao = new UserDaoJdbc(dataSource());
        userDao.setSqlService(sqlService());
        return userDao;
    }

    @Bean
    public UserService userService() {
        UserServiceImpl userService = new UserServiceImpl();
        userService.setUserDao(userDao());
        userService.setMailSender(mailSender());
        return userService;
    }

    @Bean
    public UserServiceTest.TestUserService testUserService() {
        UserServiceTest.TestUserService testUserService = new UserServiceTest.TestUserService();
        testUserService.setUserDao(userDao());
        testUserService.setMailSender(mailSender());
        return testUserService;
    }

    @Bean
    public UserLevelUpgradePolicy userLevelUpgradePolicy() {
        return new UserLevelUpgradePolicyImpl();
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean
    public MailSender mailSender() {
        MailSender mailSender = new DummyMailSender();
        return mailSender;
    }

    @Bean
    public CoreDao coreDao() {
        return new CoreDao();
    }

    @Bean
    public CoreService coreService() throws Exception {
        TxProxyFactoryBean factoryBean = new TxProxyFactoryBean();
        factoryBean.setTarget(coreServiceTarget());
        factoryBean.setTransactionManager(transactionManager());
        factoryBean.setPattern("");
        factoryBean.setServiceInterface(CoreService.class);
        return (CoreService) factoryBean.getObject();
    }

    @Bean
    public CoreServiceImpl coreServiceTarget() {
        CoreServiceImpl coreService = new CoreServiceImpl();
        coreService.setDao(coreDao());
        return coreService;
    }

//    @Bean
//    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
//        return new DefaultAdvisorAutoProxyCreator();
//    }

//    @Bean
//    public TransactionAdvice transactionAdvice() {
//        TransactionAdvice transactionAdvice = new TransactionAdvice();
//        transactionAdvice.setTransactionManager(transactionManager());
//        Properties properties = new Properties();
//        properties.setProperty("get*", "readOnly");
//        properties.setProperty("*", "");
//        transactionAdvice.setTransactionAttributes(properties);
//        return transactionAdvice;
//    }

//    @Bean
//    public AspectJExpressionPointcut transactionPointcut() {
//        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
//        pointcut.setExpression("bean(*Service)");
//        return pointcut;
//    }

//    @Bean
//    public DefaultPointcutAdvisor defaultPointcutAdvisor() {
//        return new DefaultPointcutAdvisor(transactionPointcut(), transactionAdvice());
//    }
}
