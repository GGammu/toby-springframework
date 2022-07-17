package io.younghwang.springframewokbasic;

import io.younghwang.springframewokbasic.user.dao.UserDao;
import io.younghwang.springframewokbasic.user.domain.User;

import java.sql.SQLException;

public class SpringFrameworkBasicApplication {
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
        System.out.println(user2.getId() + " 조회 성공");
    }
}
