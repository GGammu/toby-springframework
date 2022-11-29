package io.younghwang.springframeworkbasic.user.sqlservice;

import io.younghwang.springframeworkbasic.user.exception.SqlNotFoundException;
import io.younghwang.springframeworkbasic.user.exception.SqlUpdateFailureException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public abstract class AbstractUpdatableSqlRegistryTest {
    UpdatableSqlRegistry sqlRegistry;

    @BeforeEach
    public void setUp() {
        sqlRegistry = createUpdatableSqlRegistry();
        sqlRegistry.registry("KEY1", "SQL1");
        sqlRegistry.registry("KEY2", "SQL2");
        sqlRegistry.registry("KEY3", "SQL3");
    }

    abstract protected UpdatableSqlRegistry createUpdatableSqlRegistry();

    @Test
    protected void find() {
        // given
        // when
        // then
        checkFindResult("SQL1", "SQL2", "SQL3");
    }

    protected void checkFindResult(String expected1, String expected2, String expected3) {
        assertThat(sqlRegistry.findSql("KEY1")).isEqualTo(expected1);
        assertThat(sqlRegistry.findSql("KEY2")).isEqualTo(expected2);
        assertThat(sqlRegistry.findSql("KEY3")).isEqualTo(expected3);
    }

    @Test
    protected void unknownKey() {
        // given
        // when
        // then
        assertThatThrownBy(() -> {
            sqlRegistry.findSql("SQL9999");
        }).isInstanceOf(SqlNotFoundException.class);
    }

    @Test
    protected void updateSingle() {
        // given
        sqlRegistry.updateSql("KEY2", "Modified2");
        checkFindResult("SQL1", "Modified2", "SQL3");
        // when
        // then
    }

    @Test
    protected void updateMulti() {
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
    protected void updateWithNotExistingKey() {
        // given
        // when
        // then
        assertThatThrownBy(() -> {
            sqlRegistry.updateSql("SQL999", "Modified2");
        }).isInstanceOf(SqlUpdateFailureException.class);
    }
}
