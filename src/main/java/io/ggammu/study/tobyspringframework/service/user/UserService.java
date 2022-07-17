package io.ggammu.study.tobyspringframework.service.user;

import io.younghwang.springframewokbasic.user.domain.User;
import java.sql.SQLException;
import java.util.List;

public interface UserService {

    void upgradeLevels() throws SQLException;

    void add(User user);

    User get(String id);

    List<User> getAll();

    void deleteAll();

    void update(User user);

}
