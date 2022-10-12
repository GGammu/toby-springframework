package io.younghwang.springframeworkbasic.jdk.proxy;

import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;

import java.lang.reflect.Proxy;

import static org.assertj.core.api.Assertions.assertThat;

public class DynamicProxyTest {
    @Test
    void simpleProxy() {
        // given
        Hello hello = new HelloTarget();
        Hello proxiedHello = new HelloUpperCase(hello);
        Hello dynamicProxiedHello = (Hello) Proxy.newProxyInstance(
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

    @Test
    public void proxyFactoryBean() {
        ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
        proxyFactoryBean.setTarget(new HelloTarget());

        NameMatchMethodPointcut nameMatchMethodPointcut = new NameMatchMethodPointcut();
        nameMatchMethodPointcut.setMappedName("sayH*");

//        proxyFactoryBean.addAdvice(new UppercaseAdvice());
        proxyFactoryBean.addAdvisor(new DefaultPointcutAdvisor(nameMatchMethodPointcut, new UppercaseAdvice()));

        Hello proxiedHello = (Hello) proxyFactoryBean.getObject();

        assertThat(proxiedHello.sayHello("Hwang")).isEqualTo("HELLO HWANG");
        assertThat(proxiedHello.sayHi("Hwang")).isEqualTo("HI HWANG");
        assertThat(proxiedHello.sayThankYou("Hwang")).isEqualTo("Thank you Hwang");
    }
}
