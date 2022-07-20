package io.younghwang.springframewokbasic.user.dao;

public class AccountDao {
    ConnectionMaker connectionMaker;

    public AccountDao(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }
}
