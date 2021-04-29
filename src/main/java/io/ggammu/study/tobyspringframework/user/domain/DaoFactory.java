package io.ggammu.study.tobyspringframework.user.domain;

// Factory Class
public class DaoFactory {
    public UserDao userDao() {
        UserDao userDao = new UserDao(connectionMaker());
        return userDao;
    }

    public AccountDao accountDao() {
        AccountDao accountDao = new AccountDao(connectionMaker());
        return accountDao;
    }

    public MessageDao messageDao() {
        MessageDao messageDao = new MessageDao(connectionMaker());
        return messageDao;
    }

    public ConnectionMaker connectionMaker() {
        return new DConnectionMaker();
    }
}
