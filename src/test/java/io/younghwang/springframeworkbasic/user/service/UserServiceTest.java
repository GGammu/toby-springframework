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
                new User("id1", "name1", "password1", Level.BASIC, 49, 0),
                new User("id2", "name2", "password2", Level.BASIC, 50, 10),
                new User("id3", "name3", "password3", Level.SILVER, 60, 29),
                new User("id4", "name4", "password4", Level.SILVER, 60, 30),
                new User("id5", "name5", "password5", Level.GOLD, 100, 10)
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
        checkLevel(users.get(0), Level.BASIC);
        checkLevel(users.get(1), Level.SILVER);
        checkLevel(users.get(2), Level.SILVER);
        checkLevel(users.get(3), Level.GOLD);
        checkLevel(users.get(4), Level.GOLD);
    }

    private void checkLevel(User user, Level expectedLevel) {
        User userUpdated = userDao.get(user.getId());
        assertThat(userUpdated.getLevel()).isEqualTo(expectedLevel);
    }
}
