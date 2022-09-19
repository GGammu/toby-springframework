package io.younghwang.springframeworkbasic.user.dao;

import io.younghwang.springframeworkbasic.user.domain.Level;
import io.younghwang.springframeworkbasic.user.domain.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDaoJdbc implements UserDao {
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    private RowMapper<User> userMapper = new RowMapper<User>() {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getString("id"));
            user.setName(rs.getString("name"));
            user.setPassword(rs.getString("password"));
            user.setLevel(Level.valueOf(rs.getInt("level")));
            user.setLogin(rs.getInt("login"));
            user.setRecommend(rs.getInt("recommend"));
            user.setEmail(rs.getString("email"));
            return user;
        }
    };

    public UserDaoJdbc() {
    }

    public UserDaoJdbc(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(final User user) {
        this.jdbcTemplate.update(
                "insert into users(id, name, password, level, login, recommend, email) values (?, ?, ?, ?, ?, ?, ?)",
                user.getId(),
                user.getName(),
                user.getPassword(),
                user.getLevel().intValue(),
                user.getLogin(),
                user.getRecommend(),
                user.getEmail()
        );
    }

    @Override
    public User get(String id) {
        return this.jdbcTemplate.queryForObject(
                "select * from users where id = ?",
                this.userMapper,
                id);
    }

    @Override
    public void deleteAll() {
        this.jdbcTemplate.update("delete from users");
    }

    @Override
    public int getCount() {
        return this.jdbcTemplate.queryForObject("select count(*) from users", Integer.class);
    }

    @Override
    public List<User> getAll() {
        List<User> users = this.jdbcTemplate.query("select * from users order by id", this.userMapper);
        return users;
    }

    @Override
    public void update(User user) {
        this.jdbcTemplate.update("update users set name = ?, password = ?, level = ?, login = ?, recommend = ?, email = ? where id = ?",
                user.getName(),
                user.getPassword(),
                user.getLevel().intValue(),
                user.getLogin(),
                user.getRecommend(),
                user.getEmail(),
                user.getId()
        );
    }
}
