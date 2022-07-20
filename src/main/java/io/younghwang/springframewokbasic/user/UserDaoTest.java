package io.younghwang.springframewokbasic.user;

import io.younghwang.springframewokbasic.user.dao.ConnectionMaker;
import io.younghwang.springframewokbasic.user.dao.DConnectionMaker;
import io.younghwang.springframewokbasic.user.dao.UserDao;
import io.younghwang.springframewokbasic.user.domain.User;

import java.sql.SQLException;

public class UserDaoTest {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        ConnectionMaker connectionMaker = new DConnectionMaker();
        UserDao dao = new UserDao(connectionMaker);

        User user = new User();
        user.setId("ykcul02");
        user.setName("황영");
        user.setPassword("password");

        dao.add(user);

        System.out.println(user.getId() + " 등록 성공");

        User user2 = dao.get(user.getId());
        System.out.println(user2.getName());
        System.out.println(user2.getPassword());
        System.out.println(user2.getId() + " 조회 성공");
    }
}
