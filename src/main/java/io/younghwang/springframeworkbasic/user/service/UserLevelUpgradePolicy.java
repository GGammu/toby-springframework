package io.younghwang.springframeworkbasic.user.service;

import io.younghwang.springframeworkbasic.user.domain.User;

public interface UserLevelUpgradePolicy {
    boolean canUpgradeLevel(User user);
    void upgradeLevel(User user);
}
