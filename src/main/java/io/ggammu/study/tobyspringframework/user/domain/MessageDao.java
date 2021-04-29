package io.ggammu.study.tobyspringframework.user.domain;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MessageDao {
    private final ConnectionMaker connectionMaker;
}
