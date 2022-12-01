package io.younghwang.springframeworkbasic.user.sqlservice;

import io.younghwang.springframeworkbasic.user.exception.SqlUpdateFailureException;
import io.younghwang.springframeworkbasic.user.sqlservice.updatable.EmbeddedDbSqlRegistry;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.HSQL;

public class EmbeddedDbSqlRegistryTest extends AbstractUpdatableSqlRegistryTest {
    EmbeddedDatabase db;

    @Override
    protected UpdatableSqlRegistry createUpdatableSqlRegistry() {
        db = new EmbeddedDatabaseBuilder()
                .setType(HSQL)
                .addScript("classpath:/io/younghwang/springframeworkbasic/embeddeddb/schema.sql")
                .build();
        EmbeddedDbSqlRegistry embeddedDbSqlRegistry = new EmbeddedDbSqlRegistry();
        embeddedDbSqlRegistry.setDataSource(db);
        embeddedDbSqlRegistry.setJdbc(db);
        return embeddedDbSqlRegistry;
    }

    @Test
    void transactionalUpgrade() {
        // given
        checkFindResult("SQL1", "SQL2", "SQL3");
        // when
        Map<String, String> sqlmap = new HashMap<String, String>();
        sqlmap.put("KEY1", "Modified1");
        sqlmap.put("KEY999", "Modified999");
        // then
        try {
            sqlRegistry.updateSql(sqlmap);
        } catch (SqlUpdateFailureException e) {
            checkFindResult("SQL1", "SQL2", "SQL3");
        }
    }

    @AfterEach
    void tearDown() {
        db.shutdown();
    }
}
