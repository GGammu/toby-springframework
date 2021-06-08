package io.ggammu.study.tobyspringframework.user.domain;

import static io.ggammu.study.tobyspringframework.user.domain.UserService.MIN_LOGCOUNT_FOR_SILER;
import static io.ggammu.study.tobyspringframework.user.domain.UserService.MIN_RECCOMEND_FOR_GOLD;
import static org.assertj.core.api.Assertions.fail;
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
                new User("1", "user1", "password1", Level.BASIC, MIN_LOGCOUNT_FOR_SILER - 1, 0),
                new User("2", "user2", "password2", Level.BASIC, MIN_LOGCOUNT_FOR_SILER, 0),
                new User("3", "user3", "password3", Level.SILVER, 60, MIN_RECCOMEND_FOR_GOLD - 1),
                new User("4", "user4", "password4", Level.SILVER, 60, MIN_RECCOMEND_FOR_GOLD),
                new User("5", "user5", "password5", Level.GOLD, 100, Integer.MAX_VALUE)
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
        try {
            userService.upgradeLevels();
        }
        catch (Exception e) {

        }

        // then
        checkLevelUpdate(users.get(0), false);
        checkLevelUpdate(users.get(1), true);
        checkLevelUpdate(users.get(2), false);
        checkLevelUpdate(users.get(3), true);
        checkLevelUpdate(users.get(4), false);
    }

    private void checkLevelUpdate(User user, boolean upgraded) {
        User updateUser = userDao.get(user.getId());
        if (upgraded) {
            assertThat(updateUser.getLevel()).isEqualTo(user.getLevel().nextLevel());
        } else {
            assertThat(updateUser.getLevel()).isEqualTo(user.getLevel());
        }
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
        UserService testUserService = new TestUserService(users.get(3).getId());
        testUserService.setUserDao(userDao);
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

        checkLevelUpdate(users.get(1), false);
    }
}