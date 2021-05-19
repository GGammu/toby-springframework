package io.ggammu.study.tobyspringframework.user.domain;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcContext {
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void workWithStatementStrategy(StateStrategy stateStrategy) throws SQLException {
        Connection c = null;
        PreparedStatement ps = null;

        try {
            c = dataSource.getConnection();
            ps = stateStrategy.makePrepareStatement(c);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            if (ps != null) { try { ps.close(); } catch (SQLException e) { } }
            if (c!= null) { try { c.close(); } catch (SQLException e) { } }
        }
    }
    public void executeSql(String query) throws SQLException {
        this.workWithStatementStrategy(new StateStrategy() {
            @Override
            public PreparedStatement makePrepareStatement(Connection connection) throws SQLException {
                return connection.prepareStatement(query);
            }
        });
    }

    public void executeSql(String query, String... args) throws SQLException {
        this.workWithStatementStrategy(new StateStrategy() {
            @Override
            public PreparedStatement makePrepareStatement(Connection connection) throws SQLException {
                int idx = 1;
                PreparedStatement ps = connection.prepareStatement(query);

                for (String value : args) {
                    ps.setString(idx++, value);
                }
                return ps;
            }
        });
    }
}
