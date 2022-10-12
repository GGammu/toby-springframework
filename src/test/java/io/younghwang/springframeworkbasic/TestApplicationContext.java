package io.younghwang.springframeworkbasic;

import com.zaxxer.hikari.pool.ProxyFactory;
import io.younghwang.springframeworkbasic.core.dao.CoreDao;
import io.younghwang.springframeworkbasic.core.service.CoreService;
import io.younghwang.springframeworkbasic.core.service.CoreServiceImpl;
import io.younghwang.springframeworkbasic.user.dao.UserDao;
import io.younghwang.springframeworkbasic.user.dao.UserDaoJdbc;
import io.younghwang.springframeworkbasic.user.service.DummyMailSender;
import io.younghwang.springframeworkbasic.user.service.MockMailSender;
import io.younghwang.springframeworkbasic.user.service.TransactionAdvice;
import io.younghwang.springframeworkbasic.user.service.TxProxyFactoryBean;
import io.younghwang.springframeworkbasic.user.service.UserLevelUpgradePolicy;
import io.younghwang.springframeworkbasic.user.service.UserLevelUpgradePolicyImpl;
import io.younghwang.springframeworkbasic.user.service.UserService;
import io.younghwang.springframeworkbasic.user.service.UserServiceImpl;
import io.younghwang.springframeworkbasic.user.service.UserServiceTx;
import org.mockito.Mock;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.mail.MailSender;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class TestApplicationContext {
//    @Bean
//    public UserService userService() {
//        UserServiceTx userService = new UserServiceTx();
//        userService.setTransactionManager(transactionManager());
//        userService.setUserService(userServiceImpl());
//        return userService;
//    };

    @Bean
    public UserServiceImpl userServiceImpl() {
        UserServiceImpl userService = new UserServiceImpl();
        userService.setUserDao(userDao());
        userService.setMailSender(mailSender());
        return userService;
    }

    @Bean
    public UserLevelUpgradePolicy userLevelUpgradePolicy() {
        return new UserLevelUpgradePolicyImpl();
    }

    @Bean
    public UserDao userDao() {
        UserDao userDao = new UserDaoJdbc(dataSource());
        return userDao;
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

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean
    public MailSender mailSender() {
        MailSender mailSender = new DummyMailSender();
        return mailSender;
    }

    @Bean(name = "userService")
    public ProxyFactoryBean transactionProxyFactoryBean() {
        ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
        proxyFactoryBean.addAdvisor(transactionAdvisor());
        return proxyFactoryBean;
    }

    @Bean
    public UserService userService() throws Exception {
        return (UserService) transactionProxyFactoryBean().getObject();
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

    @Bean
    public TransactionAdvice transactionAdvice() {
        TransactionAdvice transactionAdvice = new TransactionAdvice();
        transactionAdvice.setTransactionManager(transactionManager());
        return transactionAdvice;
    }

    @Bean
    public NameMatchMethodPointcut transactionPointcut() {
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedName("upgrade*");
        return pointcut;
    }

    @Bean
    public DefaultPointcutAdvisor transactionAdvisor() {
        return new DefaultPointcutAdvisor(transactionPointcut(), transactionAdvice());
    }
}
