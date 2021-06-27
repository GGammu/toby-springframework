package io.ggammu.study.tobyspringframework.user.domain;

import io.ggammu.study.tobyspringframework.service.user.UserService;
import java.sql.SQLException;
import javax.sql.DataSource;
import lombok.Setter;
import org.springframework.transaction.PlatformTransactionManager;

@Setter
public class TestUserService implements UserService {
    private String id;
    private UserDao userDao;

    TestUserService(String id) {
        this.id = id;
    }

    public void upgradeLevels(User user) throws SQLException {
        if (user.getId().equals(this.id)) {
            throw new TestUserServiceException();
        }
    }

    @Override
    public void upgradeLevels() throws SQLException {

    }

    @Override
    public void add(User user) {

    }
}
