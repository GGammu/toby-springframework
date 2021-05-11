package io.ggammu.study.tobyspringframework.junit;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class JunitTest {
    static JunitTest testObject;

    @Test
    public void test1() {
        assertThat(this).isNotSameAs(testObject);
        testObject = this;
    }

    @Test
    public void test2() {
        assertThat(this).isNotSameAs(testObject);
        testObject = this;
    }

    @Test
    public void test3() {
        assertThat(this).isNotSameAs(testObject);
        testObject = this;
    }
}
