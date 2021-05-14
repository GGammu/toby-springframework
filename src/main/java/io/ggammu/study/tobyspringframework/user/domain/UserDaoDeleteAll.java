package io.ggammu.study.tobyspringframework.user.domain;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDaoDeleteAll extends UserDao {

    @Override
    protected PreparedStatement makeStatement(Connection connection) throws SQLException {
        PreparedStatement ps;
        ps = connection.prepareStatement("delete from users");
        return ps;
    }

}
