package io.younghwang.springframeworkbasic;

import io.younghwang.springframeworkbasic.user.sqlservice.SqlService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.Unmarshaller;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class OxmTestContext {
    /**
     * spring unmarshaller 를 이용한 unmarshalling
     *
     * @return
     */
    @Bean
    public Unmarshaller unmarshaller() {
        Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
        jaxb2Marshaller.setContextPath("io.younghwang.springframeworkbasic.user.sqlservice.jaxb");
        return jaxb2Marshaller;
    }
}
