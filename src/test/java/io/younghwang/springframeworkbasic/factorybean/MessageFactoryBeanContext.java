package io.younghwang.springframeworkbasic.factorybean;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageFactoryBeanContext {
    @Bean(name = "message")
    public MessageFactoryBean messageFactoryBean() {
        MessageFactoryBean messageFactoryBean = new MessageFactoryBean();
        messageFactoryBean.setText("Factory Bean");
        return messageFactoryBean;
    }

    @Bean
    public Message message() throws Exception {
        return messageFactoryBean().getObject();
    }
}
