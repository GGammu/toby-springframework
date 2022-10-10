package io.younghwang.springframeworkbasic.factorybean;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = MessageFactoryBeanContext.class)
class MessageFactoryBeanTest {

    @Autowired
    ApplicationContext context;

    @Test
    void getMessageFromFactoryBean() {
        // given
        Object message = context.getBean("message");
        // when
        // then
        assertThat(message.getClass()).isEqualTo(Message.class);
        assertThat(((Message)message).getText()).isEqualTo("Factory Bean");
    }

    @Test
    void getFactoryBean() {
        // given
        Object factory = context.getBean("&message");
        // when
        // then
        assertThat(factory.getClass()).isEqualTo(MessageFactoryBean.class);
    }
}