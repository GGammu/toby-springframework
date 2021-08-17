package io.ggammu.study.tobyspringframework.jdk;

import java.lang.reflect.Proxy;
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

    @Test
    public void dynamicProxy() {
        Hello proxiedHello = (Hello) Proxy.newProxyInstance(
                getClass().getClassLoader(),
                new Class[] { Hello.class },
                new UppercaseHandler(new HelloTarget()));

        assertThat(proxiedHello.sayHello("Toby")).isEqualTo("HELLO TOBY");
    }

}