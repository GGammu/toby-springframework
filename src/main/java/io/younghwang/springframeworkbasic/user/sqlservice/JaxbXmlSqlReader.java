package io.younghwang.springframeworkbasic.user.sqlservice;

import io.younghwang.springframeworkbasic.user.dao.UserDao;
import io.younghwang.springframeworkbasic.user.sqlservice.jaxb.Sqlmap;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class JaxbXmlSqlReader implements SqlReader {
    private String sqlMapFile;

    public void setSqlMapFile(String sqlMapFile) {
        this.sqlMapFile = sqlMapFile;
    }

    @Override
    public void read(SqlRegistry sqlRegistry) {
        String contextPath = Sqlmap.class.getPackage().getName();
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(contextPath);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            Sqlmap sqlmap = (Sqlmap) unmarshaller.unmarshal(UserDao.class.getResourceAsStream(sqlMapFile));
            sqlmap.getSql().forEach(sql -> sqlRegistry.registry(sql.getKey(), sql.getValue()));
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }

    }
}
