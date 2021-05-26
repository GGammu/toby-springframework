package io.ggammu.study.tobyspringframework.user.domain;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

public class UserDao {
    private JdbcContext jdbcContext;
    private JdbcTemplate jdbcTemplate;
    private RowMapper<User> userRowMapper;

    public void setJdbcContext(JdbcContext jdbcContext) {
        this.jdbcContext = jdbcContext;
    }

    public void setUserRowMapper(RowMapper<User> userRowMapper) {
        this.userRowMapper = userRowMapper;
    }

    // user 등록을 위한 Template Method
    public void add(final User user) {
        this.jdbcTemplate.update(
                "insert into users(id, name, password) values (?, ?, ?)",
                user.getId(),
                user.getName(),
                user.getPassword()
        );
    }

    // user 조회를 위한 Template Method
    public User get(String id) {
        return this.jdbcTemplate.queryForObject(
                "select * from users where id = ?",
                this.userRowMapper,
                id
        );
    }

    public void deleteAll() {
        this.jdbcTemplate.update((con) -> con.prepareStatement("delete from users"));
    }

    public int getCount() {
        return this.jdbcTemplate.query(
                con -> con.prepareStatement("select count(*) from users"),
                rs -> {
                    rs.next();
                    return rs.getInt(1);
                }
        );
    }

    public List<User> getAll() {
        return this.jdbcTemplate.query(
                "select * from users order by id",
                this.userRowMapper
        );
    }

}
