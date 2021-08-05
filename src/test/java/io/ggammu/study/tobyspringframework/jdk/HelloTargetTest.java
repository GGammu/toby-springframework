package io.ggammu.study.tobyspringframework.jdk;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

class HelloTargetTest {

    @Test
    public void simpleProxy() {
        Hello hello = new HelloTarget();
        assertThat(hello.sayHello("Toby")).isEqualTo("Hello Toby");
        assertThat(hello.sayHi("Toby")).isEqualTo("Hi Toby");
        assertThat(hello.sayThankYou("Toby")).isEqualTo("Thank You Toby");
    }

}