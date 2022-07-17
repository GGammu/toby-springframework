package io.ggammu.study.tobyspringframework.user.domain;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import io.younghwang.springframewokbasic.user.domain.User;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.util.List;

public class UserDaoJdbc implements UserDao {
    private JdbcContext jdbcContext;
    private JdbcTemplate jdbcTemplate;
    private RowMapper<User> userRowMapper;
    private String sqlAdd;

    public void setSqlAdd(String sqlAdd) {
        this.sqlAdd = sqlAdd;
    }

    public void setDataSource (DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void setUserRowMapper(RowMapper<User> userRowMapper) {
        this.userRowMapper = userRowMapper;
    }

    // user 등록을 위한 Template Method
    @Override
    public void add(final User user) throws DataAccessException {
        try {
            InitialContext ctx = new InitialContext();
        } catch (NamingException e) {
            e.printStackTrace();
        }

        try {
            this.jdbcTemplate.update(
//                    "insert into users(id, name, password, level, login, recommend) values (?, ?, ?, ?, ?, ?)",
                    this.sqlAdd,
                    user.getId(),
                    user.getName(),
                    user.getPassword()
//                    user.getLevel().initValue(),
//                    user.getLogin(),
//                    user.getRecommend()
            );
        } catch (DataAccessException e) {
            throw e;
        }
    }

    // user 조회를 위한 Template Method
    @Override
    public User get(String id) {
        return this.jdbcTemplate.queryForObject(
                "select * from users where id = ?",
                this.userRowMapper,
                id
        );
    }

    @Override
    public void deleteAll() {
        this.jdbcTemplate.update((con) -> con.prepareStatement("delete from users"));
    }

    @Override
    public int getCount() {
        return this.jdbcTemplate.query(
                con -> con.prepareStatement("select count(*) from users"),
                rs -> {
                    rs.next();
                    return rs.getInt(1);
                }
        );
    }

    @Override
    public void update(User user1) {
        this.jdbcTemplate.update(
                "update users set name = ?, password = ?, level = ?, login = ?, recommend = ? where id = ?",
                user1.getName(),
                user1.getPassword(),
//                user1.getLevel().initValue(),
//                user1.getLogin(),
//                user1.getRecommend(),
                user1.getId()
        );
    }

    @Override
    public List<User> getAll() {
        return this.jdbcTemplate.query(
                "select * from users order by id",
                this.userRowMapper
        );
    }
}
