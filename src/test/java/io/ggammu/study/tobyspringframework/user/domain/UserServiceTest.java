package io.ggammu.study.tobyspringframework.user.domain;

import io.ggammu.study.tobyspringframework.service.user.UserService;
import io.ggammu.study.tobyspringframework.service.user.UserServiceImpl;
import static io.ggammu.study.tobyspringframework.service.user.UserServiceImpl.MIN_LOGCOUNT_FOR_SILER;
import static io.ggammu.study.tobyspringframework.service.user.UserServiceImpl.MIN_RECCOMEND_FOR_GOLD;
import io.ggammu.study.tobyspringframework.service.user.UserServiceTx;
import static org.assertj.core.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.transaction.PlatformTransactionManager;

@ExtendWith(SpringExtension.class)
@DirtiesContext
@ContextConfiguration(classes = { TestDaoFactory.class })
class UserServiceTest {

    @Autowired
    UserDao userDao;

    @Autowired
    UserService userService;

    @Autowired
    UserServiceImpl userServiceImpl;

    @Autowired
    PlatformTransactionManager transactionManager;

    @Autowired
    MailSender mailSender;

    List<User> users;

    @BeforeEach
    public void setUp() {
        users = Arrays.asList(
                new User("1", "user1", "password1", Level.BASIC, MIN_LOGCOUNT_FOR_SILER - 1, 0),
                new User("2", "user2", "password2", Level.BASIC, MIN_LOGCOUNT_FOR_SILER, 0),
                new User("3", "user3", "password3", Level.SILVER, 60, MIN_RECCOMEND_FOR_GOLD - 1),
                new User("4", "user4", "password4", Level.SILVER, 60, MIN_RECCOMEND_FOR_GOLD),
                new User("5", "user5", "password5", Level.GOLD, 100, Integer.MAX_VALUE)
        );
    }

    @Test
    public void beanCheck() {
        assertThat(userDao).isNotNull();
    }

    @Test
    public void upgradeLevels() {
        // given
        UserServiceImpl userServiceImpl = new UserServiceImpl();

//        MockUserDao mockUserDao = new MockUserDao(this.users);
        UserDao mockUserDao = mock(UserDao.class);
        when(mockUserDao.getAll()).thenReturn(this.users);
        userServiceImpl.setUserDao(mockUserDao);

//        MockMailSender mockMailSender = new MockMailSender();
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


    }

    private void checkLevelUpdate(User updated, String expectedId, Level expectedLevel) {
        assertThat(updated.getId()).isEqualTo(expectedId);
        assertThat(updated.getLevel()).isEqualTo(expectedLevel);
    }

    private void checkLevel(User user, Level expectedLevel) {
        User userUpdate = userDao.get(user.getId());
        assertThat(userUpdate.getLevel()).isEqualTo(expectedLevel);
    }

    @Test
    void 사용자_추가_레벨_확인() {
        // given
        userDao.deleteAll();

        User userWithLevel = users.get(4);
        User userWithoutLevel = users.get(0);
        userWithoutLevel.setLevel(null);

        // when
        userService.add(userWithLevel);
        userService.add(userWithoutLevel);

        // then
        User userWithLevelRead = userDao.get("5");
        User userWithoutLevelRead = userDao.get("1");

        assertThat(userWithLevelRead.getLevel()).isEqualTo(Level.GOLD);
        assertThat(userWithoutLevelRead.getLevel()).isEqualTo(Level.BASIC);
    }

    @Test
    void 사용자_레벨_업그레이드_예외_취소() {
        TestUserService testUserService = new TestUserService(users.get(3).getId());
        testUserService.setUserDao(userDao);

        UserServiceTx userServiceTx = new UserServiceTx();
        userServiceTx.setTransactionManager(transactionManager);
        userServiceTx.setUserService(testUserService);

        userDao.deleteAll();
        for (User user : users) {
            userDao.add(user);
        }

        try {
            testUserService.upgradeLevels();
            fail("TestUserServiceException expected");
        }
        catch (TestUserServiceException e) {
        }
        catch (Exception e) {
        }

//        checkLevelUpdate(users.get(1), false);
    }
}