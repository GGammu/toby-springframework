package io.ggammu.study.tobyspringframework.user.domain;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface StateStrategy {
    PreparedStatement makePrepareStatement(Connection connection) throws SQLException;
}
