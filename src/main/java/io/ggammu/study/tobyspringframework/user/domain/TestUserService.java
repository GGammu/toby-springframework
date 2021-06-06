package io.ggammu.study.tobyspringframework.user.domain;

public class TestUserService extends UserService {
    private String id;

    TestUserService(String id) {
        this.id = id;
    }

    @Override
    protected void upgradeLevel(User user) {
        if (user.getId().equals(this.id)) {
            throw new TestUserServiceException();
        }
        super.upgradeLevel(user);
    }
}
