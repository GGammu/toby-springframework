package io.younghwang.springframeworkbasic.user.sqlservice;

import java.sql.SQLException;

public interface SqlRegistry {
    void registry(String key, String sql);

    String findSql(String key) throws SQLException;
}
