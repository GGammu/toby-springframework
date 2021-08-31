package io.ggammu.study.tobyspringframework.jdk.proxy;

import com.zaxxer.hikari.pool.ProxyFactory;
import io.ggammu.study.tobyspringframework.jdk.Hello;
import io.ggammu.study.tobyspringframework.jdk.HelloTarget;
import io.ggammu.study.tobyspringframework.jdk.UppercaseHandler;
import java.lang.reflect.Proxy;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactoryBean;

public class DynamicProxyTest {
    @Test
    public void simpleProxy() {
        Hello proxiedHello = (Hello) Proxy.newProxyInstance(
                getClass().getClassLoader(),
                new Class[] { Hello.class },
                new UppercaseHandler(new HelloTarget())
        );
    }

    @Test
    public void proxyFactoryBean() {
        ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
        proxyFactoryBean.setTarget(new HelloTarget());
        proxyFactoryBean.addAdvice(new UppercaseAdvice());

        Hello proxiedHello = (Hello) proxyFactoryBean.getObject();
    }
}
