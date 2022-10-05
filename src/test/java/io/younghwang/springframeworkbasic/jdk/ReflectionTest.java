package io.younghwang.springframeworkbasic.jdk;

import io.younghwang.springframeworkbasic.jdk.proxy.Hello;
import io.younghwang.springframeworkbasic.jdk.proxy.HelloTarget;
import io.younghwang.springframeworkbasic.jdk.proxy.HelloUpperCase;
import io.younghwang.springframeworkbasic.jdk.proxy.UppercaseHandler;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import static org.assertj.core.api.Assertions.assertThat;

public class ReflectionTest {
    @Test
    void invokeMethod() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // given
        String name = "Spring";
        Method lengthMethod = String.class.getMethod("length");
        Method charAtMethod = String.class.getMethod("charAt", int.class);
        // when
        // then
        assertThat(name.length()).isEqualTo(6);
        assertThat((int)lengthMethod.invoke(name)).isEqualTo(6);

        assertThat(name.charAt(0)).isEqualTo('S');
        assertThat((char)charAtMethod.invoke(name, 0)).isEqualTo('S');
    }

    @Test
    void simpleProxy() {
        // given
        Hello hello = new HelloTarget();
        Hello proxiedHello = new HelloUpperCase(hello);
        Hello dynamicProxiedHello = (Hello)Proxy.newProxyInstance(
                getClass().getClassLoader(),
                new Class[] {Hello.class},
                new UppercaseHandler(new HelloTarget()));
        // when
        // then
        assertThat(hello.sayHello("Hwang")).isEqualTo("Hello Hwang");
        assertThat(hello.sayHi("Hwang")).isEqualTo("Hi Hwang");
        assertThat(hello.sayThankYou("Hwang")).isEqualTo("Thank you Hwang");

        assertThat(proxiedHello.sayHello("Hwang")).isEqualTo("HELLO HWANG");
        assertThat(proxiedHello.sayHi("Hwang")).isEqualTo("HI HWANG");
        assertThat(proxiedHello.sayThankYou("Hwang")).isEqualTo("THANK YOU HWANG");

        assertThat(dynamicProxiedHello.sayHello("Hwang")).isEqualTo("HELLO HWANG");
        assertThat(dynamicProxiedHello.sayHi("Hwang")).isEqualTo("HI HWANG");
        assertThat(dynamicProxiedHello.sayThankYou("Hwang")).isEqualTo("THANK YOU HWANG");
    }
}
