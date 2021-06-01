package io.ggammu.study.tobyspringframework.user.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { TestDaoFactory.class })
class UserServiceTest {
    @Autowired
    UserDao userDao;

    @Autowired
    UserService userService;

    List<User> users;

    @BeforeEach
    public void setUp() {
        users = Arrays.asList(
                new User("1", "user1", "password1", Level.BASIC, 49, 0),
                new User("2", "user2", "password2", Level.BASIC, 50, 0),
                new User("3", "user3", "password3", Level.SILVER, 60, 29),
                new User("4", "user4", "password4", Level.SILVER, 60, 30),
                new User("5", "user5", "password5", Level.GOLD, 100, 100)
        );
    }

    @Test
    public void 빈_확인() {
        assertThat(userDao).isNotNull();
    }

    @Test
    public void 레벨_업그레이드() {
        // given
        userDao.deleteAll();
        for (User user : users) {
            userDao.add(user);
        }

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
        User userUpdate = userDao.get(user.getId());
        assertThat(userUpdate.getLevel()).isEqualTo(expectedLevel);
    }
}