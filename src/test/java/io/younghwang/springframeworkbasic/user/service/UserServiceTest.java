package io.younghwang.springframeworkbasic.user.service;

import io.younghwang.springframeworkbasic.TestApplicationContext;
import io.younghwang.springframeworkbasic.user.dao.MockUserDao;
import io.younghwang.springframeworkbasic.user.dao.UserDao;
import io.younghwang.springframeworkbasic.user.domain.Level;
import io.younghwang.springframeworkbasic.user.domain.User;
import io.younghwang.springframeworkbasic.user.exception.TestUserServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.lang.reflect.Proxy;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static io.younghwang.springframeworkbasic.user.service.UserServiceImpl.MIN_LOGCOUNT_FOR_SILVER;
import static io.younghwang.springframeworkbasic.user.service.UserServiceImpl.MIN_RECOMMEND_FOR_GOLD;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestApplicationContext.class)
@DirtiesContext
public class UserServiceTest {
    static class TestUserService extends UserServiceImpl {
        private String id;

        public TestUserService(String id) {
            this.id = id;
        }

        @Override
        protected void upgradeLevel(User user) {
            if (user.getId().equals(this.id))
                throw new TestUserServiceException();
            super.upgradeLevel(user);
        }
    }

    static class TestServiceException extends RuntimeException {

    }

    @Autowired
    UserDao userDao;

    @Autowired
    UserService userService;

    @Autowired
    UserServiceImpl userServiceImpl;

    @Autowired
    DataSource dataSource;

    @Autowired
    MailSender mailSender;

    List<User> users;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    private ApplicationContext context;

    @BeforeEach
    void setUp() {
        users = Arrays.asList(
                new User("id1", "name1", "password1", Level.BASIC, MIN_LOGCOUNT_FOR_SILVER - 1, 0, "id1@gmail.com"),
                new User("id2", "name2", "password2", Level.BASIC, MIN_LOGCOUNT_FOR_SILVER, 10, "id2@gmail.com"),
                new User("id3", "name3", "password3", Level.SILVER, 60, MIN_RECOMMEND_FOR_GOLD - 1, "id3@gmail.com"),
                new User("id4", "name4", "password4", Level.SILVER, 60, MIN_RECOMMEND_FOR_GOLD, "id4@gmail.com"),
                new User("id5", "name5", "password5", Level.GOLD, 100, Integer.MAX_VALUE, "id5@gmail.com")
        );
    }

    @Test
    void bean() {
        // given
        // when
        // then
        assertThat(this.userService).isNotNull();
    }

    @Test
    void upgradeLevels() throws SQLException {
        // given
        UserServiceImpl userServiceImpl = new UserServiceImpl();

        UserDao mockUserDao = mock(UserDao.class);
        when(mockUserDao.getAll()).thenReturn(this.users);
        userServiceImpl.setUserDao(mockUserDao);

        MailSender mockMailSender = mock(MailSender.class);
        userServiceImpl.setMailSender(mockMailSender);

        // when
        userServiceImpl.upgradeLevels();

        // then
        verify(mockUserDao, times(2)).update(any(User.class));
        verify(mockUserDao, times(2)).update(any(User.class));
        verify(mockUserDao).update(users.get(1));
        assertThat(users.get(1).getLevel()).isEqualTo(Level.SILVER);
        verify(mockUserDao).update(users.get(3));
        assertThat(users.get(3).getLevel()).isEqualTo(Level.GOLD);

        ArgumentCaptor<SimpleMailMessage> mailMessageArgumentCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(mockMailSender, times(2)).send(mailMessageArgumentCaptor.capture());
        List<SimpleMailMessage> mailMessages = mailMessageArgumentCaptor.getAllValues();
        assertThat(mailMessages.get(0).getTo()[0]).isEqualTo(users.get(1).getEmail());
        assertThat(mailMessages.get(1).getTo()[0]).isEqualTo(users.get(3).getEmail());
    }

    private void checkUserAndLevel(User user, String expectedId, Level expectedLevel) {
        assertThat(user.getId()).isEqualTo(expectedId);
        assertThat(user.getLevel()).isEqualTo(expectedLevel);
    }

    private void checkLevel(User user, boolean upgraded) {
        User userUpdated = userDao.get(user.getId());
        if (upgraded) {
            assertThat(userUpdated.getLevel()).isEqualTo(user.getLevel().nextLevel());
        } else {
            assertThat(userUpdated.getLevel()).isEqualTo(user.getLevel());
        }
    }

    @Test
    void add() {
        // given
        userDao.deleteAll();

        User userWithLevel = users.get(4);
        User userWithoutLevel = users.get(0);
        userWithoutLevel.setLevel(null);

        // when
        userService.add(userWithLevel);
        userService.add(userWithoutLevel);

        // then
        User userWithLevelRead = userDao.get(userWithLevel.getId());
        User userWithoutLevelRead = userDao.get(userWithoutLevel.getId());

        assertThat(userWithLevelRead.getLevel()).isEqualTo(userWithLevelRead.getLevel());
        assertThat(userWithoutLevelRead.getLevel()).isEqualTo(Level.BASIC);
    }

    @Test
    @DirtiesContext
    void upgradeAllOrNothing() throws Exception {
        // given
        TestUserService testUserService = new TestUserService(users.get(3).getId());
        testUserService.setMailSender(this.mailSender);
        testUserService.setUserDao(this.userDao);

        TxProxyFactoryBean factoryBean = context.getBean("&userService", TxProxyFactoryBean.class);
        factoryBean.setTarget(testUserService);
        UserService userServiceTx = (UserService) factoryBean.getObject();

        userDao.deleteAll();
        users.forEach(user -> userDao.add(user));

        // when
        try {
            userServiceTx.upgradeLevels();
            fail("TestUserServiceExceptionClass expected");
        } catch (TestUserServiceException e) {
        }

        // then
        checkLevel(users.get(1), false);
    }
}
