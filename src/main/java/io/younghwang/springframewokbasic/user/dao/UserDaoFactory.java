package io.younghwang.springframewokbasic.user.dao;

public class UserDaoFactory {
    public UserDao userDao() {
        return new UserDao(getConnectionMaker());
    }

    public AccountDao accountDao() {
        return new AccountDao(getConnectionMaker());
    }

    public MessageDao messageDao() {
        return new MessageDao(getConnectionMaker());
    }

    private DConnectionMaker getConnectionMaker() {
        return new DConnectionMaker();
    }
}
