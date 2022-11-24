package io.younghwang.springframeworkbasic.user.sqlservice;

import io.younghwang.springframeworkbasic.user.exception.SqlNotFoundException;

import java.sql.SQLException;

public interface SqlRegistry {
    void registry(String key, String sql);

    String findSql(String key) throws SqlNotFoundException;
}
