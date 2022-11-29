package io.younghwang.springframeworkbasic.embeddeddb;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.HSQL;

public class EmbeddedDbTest {
    EmbeddedDatabase db;
    JdbcTemplate template;

    @BeforeEach
    public void setUp() {
        db = new EmbeddedDatabaseBuilder()
                .setType(HSQL)
                .addScript("classpath:/io/younghwang/springframeworkbasic/embeddeddb/schema.sql")
                .addScript("classpath:/io/younghwang/springframeworkbasic/embeddeddb/data.sql")
                .build();

        template = new JdbcTemplate(db);
    }

    @AfterEach
    void tearDown() {
        db.shutdown();
    }

    @Test
    void initData() {
        // given
        assertThat(template.queryForObject("select count(*) from sqlmap", Integer.class)).isEqualTo(2);

        // when
        List<Map<String, Object>> list = template.queryForList("select * from sqlmap order by key_");
        assertThat((String) list.get(0).get("key_")).isEqualTo("KEY1");
        assertThat((String) list.get(0).get("sql_")).isEqualTo("SQL1");
        assertThat((String) list.get(1).get("key_")).isEqualTo("KEY2");
        assertThat((String) list.get(1).get("sql_")).isEqualTo("SQL2");
        // then
    }

    @Test
    void insert() {
        // given
        template.update("insert into sqlmap(key_, sql_) values (?, ?)", "KEY3", "SQL3");
        // when
        // then
        assertThat(template.queryForObject("select count(*) from sqlmap", Integer.class)).isEqualTo(3);
    }
}
