package io.younghwang.springframeworkbasic.user.dao;

import io.younghwang.springframeworkbasic.user.dao.exception.DuplicateUserIdException;
import io.younghwang.springframeworkbasic.user.domain.User;

import java.util.List;

public interface UserDao {
    void add(User user);

    User get(String id);

    void deleteAll();

    int getCount();

    List<User> getAll();

    void update(User user);
}
