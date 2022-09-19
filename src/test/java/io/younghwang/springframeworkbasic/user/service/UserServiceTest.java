package io.younghwang.springframeworkbasic.user.service;

import io.younghwang.springframeworkbasic.TestApplicationContext;
import io.younghwang.springframeworkbasic.user.dao.UserDao;
import io.younghwang.springframeworkbasic.user.domain.Level;
import io.younghwang.springframeworkbasic.user.domain.User;
import io.younghwang.springframeworkbasic.user.exception.TestUserServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static io.younghwang.springframeworkbasic.user.service.UserService.MIN_LOGCOUNT_FOR_SILVER;
import static io.younghwang.springframeworkbasic.user.service.UserService.MIN_RECOMMEND_FOR_GOLD;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestApplicationContext.class)
public class UserServiceTest {
    static class TestUserService extends UserService {
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
    DataSource dataSource;

    List<User> users;

    @Autowired
    private PlatformTransactionManager transactionManager;

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
        userService.setPlatformTransactionManager(this.transactionManager);

        userDao.deleteAll();
        users.forEach(user -> userDao.add(user));

        // when
        userService.upgradeLevels();

        // then
        checkLevel(users.get(0), false);
        checkLevel(users.get(1), true);
        checkLevel(users.get(2), false);
        checkLevel(users.get(3), true);
        checkLevel(users.get(4), false);
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
    void upgradeAllOrNothing() throws Exception{
        // given
        TestUserService testUserService = new TestUserService(users.get(3).getId());
        testUserService.setUserDao(this.userDao);
        testUserService.setDataSource(this.dataSource);
        testUserService.setPlatformTransactionManager(this.transactionManager);

        userDao.deleteAll();
        users.forEach(user -> userDao.add(user));

        // when
        try {
            testUserService.upgradeLevels();
            fail("TestUserServiceExceptionClass expected");
        } catch (TestUserServiceException e) {
        }

        // then
        checkLevel(users.get(1), false);
    }
}
