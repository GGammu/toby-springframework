package io.younghwang.springframeworkbasic.user.dao;

import io.younghwang.springframeworkbasic.user.dao.exception.DuplicateUserIdException;
import io.younghwang.springframeworkbasic.user.domain.User;
import org.h2.api.ErrorCode;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDao {
    private JdbcTemplate jdbcTemplate;

    private RowMapper<User> userMapper = new RowMapper<User>() {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getString("id"));
            user.setName(rs.getString("name"));
            user.setPassword(rs.getString("password"));
            return user;
        }
    };

    public UserDao() {
    }

    public UserDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void add(final User user) throws DuplicateUserIdException{
        try {
            this.jdbcTemplate.update(
                    "insert into users(id, name, password) values (?, ?, ?)",
                    user.getId(),
                    user.getName(),
                    user.getPassword());
        } catch (DuplicateKeyException e) {
            throw new DuplicateUserIdException(e);
        }
    }

    public User get(String id) {
        return this.jdbcTemplate.queryForObject(
                "select * from users where id = ?",
                this.userMapper,
                id);
    }

    public void deleteAll() {
        this.jdbcTemplate.update("delete from users");
    }

    public int getCount() {
        return this.jdbcTemplate.queryForObject("select count(*) from users", Integer.class);
    }

    public List<User> getAll() {
        List<User> users = this.jdbcTemplate.query("select * from users order by id", this.userMapper);
        return users;
    }
}
