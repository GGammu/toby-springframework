package io.ggammu.study.tobyspringframework.user.domain;

import io.younghwang.springframewokbasic.user.domain.User;

public interface UserLevelUpgradePolicy {
    boolean canUpgradeLevel(User user);
    void upgradeLevel(UserDao userDao, User user);
}
