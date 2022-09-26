package io.younghwang.springframeworkbasic.user.service;

import io.younghwang.springframeworkbasic.user.domain.User;

public interface UserService {
    public void add(User user);

    public void upgradeLevels();
}
