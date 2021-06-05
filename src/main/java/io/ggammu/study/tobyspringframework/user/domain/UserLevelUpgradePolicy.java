package io.ggammu.study.tobyspringframework.user.domain;

public interface UserLevelUpgradePolicy {
    boolean canUpgradeLevel(User user);
    void upgradeLevel(UserDao userDao, User user);
}
