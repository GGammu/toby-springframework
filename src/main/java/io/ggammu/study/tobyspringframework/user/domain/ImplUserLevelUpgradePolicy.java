package io.ggammu.study.tobyspringframework.user.domain;

import io.younghwang.springframewokbasic.user.domain.User;

import static io.ggammu.study.tobyspringframework.service.user.UserServiceImpl.MIN_LOGCOUNT_FOR_SILER;
import static io.ggammu.study.tobyspringframework.service.user.UserServiceImpl.MIN_RECCOMEND_FOR_GOLD;

public class ImplUserLevelUpgradePolicy implements UserLevelUpgradePolicy {
    @Override
    public boolean canUpgradeLevel(User user) {
        Level currentLevel = user.getLevel();
        switch (currentLevel) {
            case BASIC:
                return user.getLogin() >= MIN_LOGCOUNT_FOR_SILER;
            case SILVER:
                return user.getRecommend() >= MIN_RECCOMEND_FOR_GOLD;
            case GOLD:
                return false;
            default:
                throw new IllegalArgumentException("Unknown Level: " + currentLevel);
        }
    }

    @Override
    public void upgradeLevel(UserDao userDao, User user) {
        user.upgradeLevel();
        userDao.update(user);
    }
}
