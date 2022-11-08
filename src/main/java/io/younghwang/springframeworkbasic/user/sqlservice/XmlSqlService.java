package io.younghwang.springframeworkbasic.user.sqlservice;

import io.younghwang.springframeworkbasic.user.dao.UserDao;
import io.younghwang.springframeworkbasic.user.sqlservice.jaxb.Sqlmap;

import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class XmlSqlService implements SqlService, SqlReader, SqlRegistry {
    private Map<String, String> sqlMap = new HashMap<>();
    private String sqlMapFile;
    private SqlReader sqlReader;
    private SqlRegistry sqlRegistry;

    public void setSqlMapFile(String sqlMapFile) {
        this.sqlMapFile = sqlMapFile;
    }

    public void setSqlReader(SqlReader sqlReader) {
        this.sqlReader = sqlReader;
    }

    public void setSqlRegistry(SqlRegistry sqlRegistry) {
        this.sqlRegistry = sqlRegistry;
    }

    public XmlSqlService() {
    }

    @PostConstruct
    public void loadSql() {
        this.sqlReader.read(this.sqlRegistry);
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

    @Override
    public void registry(String key, String sql) {
        sqlMap.put(key, sql);
    }

    @Override
    public String findSql(String key) throws SQLException {
        String sql = sqlMap.get(key);
        if (sql == null)
            throw new SqlRetrievalFailureException(key + "를 이용해서 SQL을 찾을 수 없습니다.");
        return sql;
    }

    @Override
    public String getSql(String key) throws SqlRetrievalFailureException {
        try {
            return this.sqlRegistry.findSql(key);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
