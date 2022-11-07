package io.younghwang.springframeworkbasic.user.sqlservice;

import java.sql.SQLException;

public interface SqlRegistry {
    void registry(String key, String sql);

    void findSql(String key) throws SQLException;
}
