package io.ggammu.study.tobyspringframework.user.domain;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Deprecated
public class SimpleConnectionMaker {
    public Connection makeNewConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/toby_spring",
                "spring",
                "password");

        return connection;
    }
}
