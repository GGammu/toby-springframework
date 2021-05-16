package io.ggammu.study.tobyspringframework.user.domain;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteAllStatement implements StateStrategy {
    @Override
    public PreparedStatement makePrepareStatement(Connection connection) throws SQLException {
        System.out.println("makePrepareStatement " + connection);
        return connection.prepareStatement("delete from users");
    }
}
