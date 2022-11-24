package io.younghwang.springframeworkbasic.user.sqlservice;

import io.younghwang.springframeworkbasic.TestSqlRegistryContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestSqlRegistryContext.class})
public class ConcurrentHashMapSqlRegistryTest {
    UpdatableSqlRegistry sqlRegistry;

    @BeforeEach
    public void setUp() {
        sqlRegistry = new ConcurrentHashMapSqlRegistry();
        sqlRegistry.registry("KEY1", "SQL1");
        sqlRegistry.registry("KEY2", "SQL2");
        sqlRegistry.registry("KEY3", "SQL3");
    }

    @Test
    void find() throws SQLException {
        // given
        checkFindResult("SQL1", "SQL2", "SQL3");
        // when
        // then
    }

    private void checkFindResult(String expected1, String expected2, String expected3) throws SQLException {
        assertThat(sqlRegistry.findSql("KEY1")).isEqualTo(expected1);
        assertThat(sqlRegistry.findSql("KEY2")).isEqualTo(expected2);
        assertThat(sqlRegistry.findSql("KEY3")).isEqualTo(expected3);
    }

    @Test
    void unknownKey() {
        // given
        assertThatThrownBy(() -> {
            sqlRegistry.findSql("SQL9999");
        }).isInstanceOf(SQLException.class);
        // when
        // then
    }

    @Test
    void updateSingle() throws SQLException {
        // given
        sqlRegistry.updateSql("KEY2", "Modified2");
        checkFindResult("SQL1", "Modified2", "SQL3");
        // when
        // then
    }

    @Test
    void updateMulti() throws SQLException {
        // given
        Map<String, String> sqlmap = new HashMap<>();
        sqlmap.put("KEY1", "Modified1");
        sqlmap.put("KEY3", "Modified3");

        // when
        sqlRegistry.updateSql(sqlmap);

        // then
        checkFindResult("Modified1", "SQL2", "Modified3");
    }

    @Test
    void updateWithNotExistingKey() {
        // given
        sqlRegistry.updateSql("SQL999", "Modified2");
        // when
        // then
    }
}
