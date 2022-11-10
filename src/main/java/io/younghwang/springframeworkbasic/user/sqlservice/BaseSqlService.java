package io.younghwang.springframeworkbasic.user.sqlservice;

import javax.annotation.PostConstruct;
import java.sql.SQLException;
import java.sql.SQLNonTransientException;

public class BaseSqlService implements SqlService {
    protected SqlReader sqlReader;
    protected SqlRegistry sqlRegistry;

    public void setSqlReader(SqlReader sqlReader) {
        this.sqlReader = sqlReader;
    }

    public void setSqlRegistry(SqlRegistry sqlRegistry) {
        this.sqlRegistry = sqlRegistry;
    }

    @PostConstruct
    public void loadSql() {
        this.sqlReader.read(this.sqlRegistry);
    }

    @Override
    public String getSql(String key) throws SqlRetrievalFailureException {
        try {
            return this.sqlRegistry.findSql(key);
        } catch (SQLException e) {
            throw new SqlRetrievalFailureException("");
        }
    }
}
