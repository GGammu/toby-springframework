package io.ggammu.study.tobyspringframework.user.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class User {
    private String id;
    private String name;
    private String password;
    private Level level;
    private int login;
    private int recommand;

    public User(String id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public User(String id, String name, String password, Level level, int login, int recommand) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.level = level;
        this.login = login;
        this.recommand = recommand;
    }
}
