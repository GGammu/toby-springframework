package io.ggammu.study.tobyspringframework.service.user;

import io.ggammu.study.tobyspringframework.user.domain.TestUserServiceException;
import io.ggammu.study.tobyspringframework.user.domain.User;

static class TestUserServiceImpl extends UserServiceImpl {
    private String id = "madnite1";

    protected void upgradeLevel(User user) {
        if (user.getId().equals(this.id)) throw new TestUserServiceException();
        super.upgradeLevel(user);
    }

}