package io.younghwang.springframeworkbasic;


import io.younghwang.springframeworkbasic.user.sqlservice.ConcurrentHashMapSqlRegistry;
import io.younghwang.springframeworkbasic.user.sqlservice.OxmSqlService;
import io.younghwang.springframeworkbasic.user.sqlservice.SqlRegistry;
import io.younghwang.springframeworkbasic.user.sqlservice.SqlService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class TestSqlRegistryContext {
    @Bean
    public SqlRegistry sqlRegistry() {
        return new ConcurrentHashMapSqlRegistry();
    }

    @Bean
    public Jaxb2Marshaller unmarshaller() {
        Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
        jaxb2Marshaller.setContextPath("io.younghwang.springframeworkbasic.user.sqlservice.jaxb");
        return jaxb2Marshaller;
    }

    @Bean
    public SqlService sqlService() {
        OxmSqlService oxmSqlService = new OxmSqlService();
        oxmSqlService.setUnmarshaller(unmarshaller());
        oxmSqlService.setSqlRegistry(sqlRegistry());
        return oxmSqlService;
    }
}
