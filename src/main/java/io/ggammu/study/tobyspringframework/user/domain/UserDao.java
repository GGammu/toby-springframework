package io.ggammu.study.tobyspringframework.user.domain;

import com.mysql.cj.x.protobuf.MysqlxPrepare;
import sun.java2d.pipe.SpanShapeRenderer;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;

public class UserDao {
    private ConnectionMaker connectionMaker;

    public UserDao() {
        this.connectionMaker = new DConnectionMaker();
    }

    // user 등록을 위한 Template Method
    public void add(User user) throws ClassNotFoundException, SQLException {
        Connection connection = connectionMaker.makeConnection();

        PreparedStatement ps = connection.prepareStatement(
                "insert into users(id, name, password) values(?, ?, ?)"
        );
        ps.setString(1, user.getId());
        ps.setString(2, user.getName());
        ps.setString(3, user.getPassword());

        ps.executeUpdate();
        ps.close();
        connection.close();
    }

    // user 조회를 위한 Template Method
    public User get(String id) throws ClassNotFoundException, SQLException {
        Connection connection = connectionMaker.makeConnection();

        PreparedStatement ps = connection.prepareStatement(
                "select * from users where id = ?"
        );
        ps.setString(1, id);

        ResultSet rs = ps.executeQuery();
        rs.next();
        User user = new User();
        user.setId(rs.getString("id"));
        user.setName(rs.getString("name"));
        user.setPassword(rs.getString("password"));
        rs.close();
        ps.close();
        connection.close();
        return user;
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        UserDao dao = new UserDao();

        User user = new User();
        user.setId("ykcul02");
        user.setName("황영");
        user.setPassword("password");

        dao.add(user);
        System.out.println(user.getId() + " 등록 성공");

        User user2 = dao.get(user.getId());
        System.out.println(user2.getName());
        System.out.println(user2.getPassword());
        System.out.println(user2.getId());
    }
}
