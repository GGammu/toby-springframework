package io.younghwang.springframeworkbasic.user.dao;

import io.younghwang.springframeworkbasic.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class UserDaoTest {
    private UserDao dao;
    private User user1;
    private User user2;
    private User user3;
    @BeforeEach
    public void setUp() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(UserDaoFactory.class);
        this.dao = context.getBean("userDao", UserDao.class);
        this.user1 = new User("id1", "name1", "password1");
        this.user2 = new User("id2", "name2", "password2");
        this.user3 = new User("id3", "name3", "password3");
    }

    @Test
    public void addAndGet() throws SQLException {
        dao.deleteAll();
        assertThat(dao.getCount()).isEqualTo(0);

        dao.add(user1);
        dao.add(user2);
        assertThat(dao.getCount()).isEqualTo(2);

        User getUser1 = dao.get(user1.getId());
        assertThat(getUser1.getName()).isEqualTo(user1.getName());
        assertThat(getUser1.getPassword()).isEqualTo(user1.getPassword());

        User getUser2 = dao.get(user2.getId());
        assertThat(getUser2.getName()).isEqualTo(user2.getName());
        assertThat(getUser2.getPassword()).isEqualTo(user2.getPassword());
    }

    @Test
    public void count() throws SQLException {
        dao.deleteAll();
        assertThat(dao.getCount()).isEqualTo(0);

        dao.add(user1);
        assertThat(dao.getCount()).isEqualTo(1);

        dao.add(user2);
        assertThat(dao.getCount()).isEqualTo(2);

        dao.add(user3);
        assertThat(dao.getCount()).isEqualTo(3);

        dao.deleteAll();
    }

    @Test
    void getUserFailure() throws SQLException {
        // given

        // when
        dao.deleteAll();
        assertThat(dao.getCount()).isEqualTo(0);

        // then
        Throwable thrown = catchThrowable(() -> dao.get("unknown"));
        assertThat(thrown).isInstanceOf(EmptyResultDataAccessException.class);
    }
}