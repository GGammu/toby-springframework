package io.ggammu.study.tobyspringframework.user.domain;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionMaker {
    public Connection makeConnection() throws ClassNotFoundException, SQLException;
}
