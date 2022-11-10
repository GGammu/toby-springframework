package io.younghwang.springframeworkbasic.user.sqlservice;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class HashMapSqlRegistry implements SqlRegistry {
    private Map<String, String> sqlMap = new HashMap<>();

    @Override
    public void registry(String key, String sql) {
        sqlMap.put(key, sql);
    }

    @Override
    public String findSql(String key) throws SQLException {
        String sql = sqlMap.get(key);
        if (sql == null) {
            throw new SqlRetrievalFailureException(key + "를 이용해서 찾을 수 없습니다.");
        }
        return sql;
    }
}
