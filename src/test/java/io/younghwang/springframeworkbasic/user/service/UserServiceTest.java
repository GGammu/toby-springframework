package io.younghwang.springframeworkbasic.user.service;

import io.younghwang.springframeworkbasic.TestApplicationContext;
import io.younghwang.springframeworkbasic.user.dao.UserDao;
import io.younghwang.springframeworkbasic.user.domain.Level;
import io.younghwang.springframeworkbasic.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static io.younghwang.springframeworkbasic.user.service.UserService.MIN_LOGCOUNT_FOR_SILVER;
import static io.younghwang.springframeworkbasic.user.service.UserService.MIN_RECOMMEND_FOR_GOLD;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestApplicationContext.class)
public class UserServiceTest {
    @Autowired
    UserDao userDao;

    @Autowired
    UserService userService;

    List<User> users;

    @BeforeEach
    void setUp() {
        users = Arrays.asList(
                new User("id1", "name1", "password1", Level.BASIC, MIN_LOGCOUNT_FOR_SILVER - 1, 0),
                new User("id2", "name2", "password2", Level.BASIC, MIN_LOGCOUNT_FOR_SILVER, 10),
                new User("id3", "name3", "password3", Level.SILVER, 60, MIN_RECOMMEND_FOR_GOLD - 1),
                new User("id4", "name4", "password4", Level.SILVER, 60, MIN_RECOMMEND_FOR_GOLD),
                new User("id5", "name5", "password5", Level.GOLD, 100, Integer.MAX_VALUE)
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
    void upgradeLevels() {
        // given
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
}
