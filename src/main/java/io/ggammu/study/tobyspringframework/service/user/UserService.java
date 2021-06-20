package io.ggammu.study.tobyspringframework.service.user;

import io.ggammu.study.tobyspringframework.user.domain.User;
import java.sql.SQLException;

public interface UserService {

    void upgradeLevels() throws SQLException;

    void add(User user);

}
