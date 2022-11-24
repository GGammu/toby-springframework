package io.younghwang.springframeworkbasic.user.sqlservice;

import io.younghwang.springframeworkbasic.user.exception.SqlNotFoundException;
import io.younghwang.springframeworkbasic.user.exception.SqlUpdateException;
import io.younghwang.springframeworkbasic.user.exception.SqlUpdateFailureException;

import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapSqlRegistry implements UpdatableSqlRegistry {
    private Map<String, String> sqlMap = new ConcurrentHashMap<>();

    @Override
    public void registry(String key, String sql) {
        sqlMap.put(key, sql);
    }

    @Override
    public String findSql(String key) {
        String sql = sqlMap.get(key);
        if (sql == null) throw new SqlNotFoundException();
        return sql;
    }

    @Override
    public void updateSql(String key, String sql) throws SqlUpdateFailureException {
        if (sqlMap.get(key) == null)
            throw new SqlUpdateFailureException(key + "에 해당하는 SQL을 찾을 수 없습니다.");
        sqlMap.put(key, sql);
    }

    @Override
    public void updateSql(Map<String, String> sqlmap) throws SqlUpdateException {
        for (Map.Entry<String, String> entry : sqlMap.entrySet()) {
            updateSql(entry.getKey(), entry.getValue());
        }
    }
}
