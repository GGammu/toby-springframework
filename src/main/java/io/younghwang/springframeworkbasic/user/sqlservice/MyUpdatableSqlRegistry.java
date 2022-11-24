package io.younghwang.springframeworkbasic.user.sqlservice;

import io.younghwang.springframeworkbasic.user.exception.SqlNotFoundException;
import io.younghwang.springframeworkbasic.user.exception.SqlUpdateException;

import java.sql.SQLException;
import java.util.Map;

public class MyUpdatableSqlRegistry implements UpdatableSqlRegistry {
    @Override
    public void registry(String key, String sql) {

    }

    @Override
    public String findSql(String key) throws SqlNotFoundException {
        return null;
    }

    @Override
    public void updateSql(String key, String sql) throws SqlUpdateException {

    }

    @Override
    public void updateSql(Map<String, String> sqlmap) throws SqlUpdateException {

    }
}
