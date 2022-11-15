package io.younghwang.springframeworkbasic.user.sqlservice.jaxb;

import io.younghwang.springframeworkbasic.OxmTestContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {OxmTestContext.class})
public class JaxbTest {
    @Autowired
    org.springframework.oxm.Unmarshaller unmarshaller;

    @Test
    void readSqlmap() throws JAXBException, IOException {
        // given
        String contentPath = Sqlmap.class.getPackage().getName();
        JAXBContext jaxbContext = JAXBContext.newInstance(contentPath);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        // when
        Sqlmap sqlmap = (Sqlmap) unmarshaller.unmarshal(getClass().getResourceAsStream("sqlmap.xml"));
        List<SqlType> sql = sqlmap.getSql();

        // then
        assertThat(sql.size()).isEqualTo(3);
        assertThat(sql.get(0).getKey()).isEqualTo("add");
        assertThat(sql.get(0).getValue()).isEqualTo("insert");
        assertThat(sql.get(1).getKey()).isEqualTo("get");
        assertThat(sql.get(1).getValue()).isEqualTo("select");
        assertThat(sql.get(2).getKey()).isEqualTo("delete");
        assertThat(sql.get(2).getValue()).isEqualTo("delete");
    }

    @Test
    void unmarshallSqlMap() throws JAXBException, IOException {
        // given
        StreamSource xmlSource = new StreamSource(getClass().getResourceAsStream("sqlmap.xml"));
        Sqlmap sqlmap = (Sqlmap) this.unmarshaller.unmarshal(xmlSource);
        // when
        List<SqlType> sql = sqlmap.getSql();
        // then
        assertThat(sql.size()).isEqualTo(3);
        assertThat(sql.get(0).getKey()).isEqualTo("add");
        assertThat(sql.get(0).getValue()).isEqualTo("insert");
        assertThat(sql.get(1).getKey()).isEqualTo("get");
        assertThat(sql.get(1).getValue()).isEqualTo("select");
        assertThat(sql.get(2).getKey()).isEqualTo("delete");
        assertThat(sql.get(2).getValue()).isEqualTo("delete");
    }
}
