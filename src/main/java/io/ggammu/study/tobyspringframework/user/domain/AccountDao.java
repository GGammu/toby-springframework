package io.ggammu.study.tobyspringframework.user.domain;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AccountDao {
    private final ConnectionMaker connectionMaker;
}
