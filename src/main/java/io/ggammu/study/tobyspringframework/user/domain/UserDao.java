package io.ggammu.study.tobyspringframework.user.domain;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.SQLException;

public class UserDao {
    private DataSource dataSource;
    private JdbcContext jdbcContext;
    private JdbcTemplate jdbcTemplate;

    public void setJdbcContext(JdbcContext jdbcContext) {
        this.jdbcContext = jdbcContext;
    }

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.dataSource = dataSource;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    // user 등록을 위한 Template Method
    public void add(final User user) throws ClassNotFoundException, SQLException {
        this.jdbcTemplate.update("insert into users(id, name, password) values (?, ?, ?)",
                user.getId(),
                user.getName(),
                user.getPassword());
    }

    // user 조회를 위한 Template Method
    public User get(String id) throws ClassNotFoundException, SQLException {
        return this.jdbcTemplate.queryForObject(
                "select * from users where id = ?",
                (rs, rowNum) -> {
                    User user = new User();
                    user.setId(rs.getString("id"));
                    user.setName(rs.getString("name"));
                    user.setPassword(rs.getString("password"));
                    return user;
                },
                id
        );
    }

    public void deleteAll() throws SQLException {
        this.jdbcTemplate.update((con) -> con.prepareStatement("delete from users"));
    }

    public int getCount() throws SQLException {
        return this.jdbcTemplate.query(
                con -> con.prepareStatement("select count(*) form users"),
                rs -> { rs.next(); return rs.getInt(1); }
        );
    }
}
