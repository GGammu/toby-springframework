package io.younghwang.springframeworkbasic.user.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserTest {
    User user;

    @BeforeEach
    void setUp() {
        this.user = new User();
    }

    @Test
    void upgradeLevel() {
        // given
        Level[] levels= Level.values();

        // when
        // then
        for (Level level : levels) {
            if (level.nextLevel() == null) continue;
            user.setLevel(level);
            user.upgradeLevel();
            assertThat(user.getLevel()).isEqualTo(level.nextLevel());
        }
    }

    @Test
    void cannotUpgradeLevel() {
        // given
        Level[] levels= Level.values();

        // when
        // then
        assertThrows(IllegalStateException.class, () -> {
            for (Level level : levels) {
                if (level.nextLevel() != null) continue;
                user.setLevel(level);
                user.upgradeLevel();
            }
        });
    }
}