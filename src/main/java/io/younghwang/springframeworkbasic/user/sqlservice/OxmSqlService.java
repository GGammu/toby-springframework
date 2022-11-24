package io.younghwang.springframeworkbasic.user.sqlservice;

import io.younghwang.springframeworkbasic.user.dao.UserDao;
import io.younghwang.springframeworkbasic.user.exception.SqlNotFoundException;
import io.younghwang.springframeworkbasic.user.sqlservice.jaxb.SqlType;
import io.younghwang.springframeworkbasic.user.sqlservice.jaxb.Sqlmap;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.oxm.Unmarshaller;

import javax.annotation.PostConstruct;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;

public class OxmSqlService implements SqlService {
    private final OxmSqlReader oxmSqlReader = new OxmSqlReader();
    private final BaseSqlService baseSqlService = new BaseSqlService();
    private SqlRegistry sqlRegistry = new HashMapSqlRegistry();

    public void setUnmarshaller(Unmarshaller unmarshaller) {
        this.oxmSqlReader.setUnmarshaller(unmarshaller);
    }

    public void setSqlmapFile(String sqlmapFile) {
        this.oxmSqlReader.setSqlmapFile(sqlmapFile);
    }

    public void setSqlRegistry(SqlRegistry sqlRegistry) {
        this.sqlRegistry = sqlRegistry;
    }

    public void setSqlmap(Resource sqlmap) {
        this.oxmSqlReader.setSqlmap(sqlmap);
    }

    @PostConstruct
    public void loadSql() {
        this.baseSqlService.setSqlReader(this.oxmSqlReader);
        this.baseSqlService.setSqlRegistry(this.sqlRegistry);

        this.baseSqlService.loadSql();
    }

    @Override
    public String getSql(String key) throws SqlRetrievalFailureException {
        try {
            return this.sqlRegistry.findSql(key);
        } catch (SqlNotFoundException e) {
            throw new SqlNotFoundException("key");
        }
    }

    private class OxmSqlReader implements SqlReader {
        private Resource sqlmap = new ClassPathResource("sqlmap.xml", UserDao.class);
        private Unmarshaller unmarshaller;
        private String sqlmapFile;

        public void setSqlmap(Resource sqlmap) {
            this.sqlmap = sqlmap;
        }

        public void setUnmarshaller(Unmarshaller unmarshaller) {
            this.unmarshaller = unmarshaller;
        }

        public void setSqlmapFile(String sqlmapFile) {
            this.sqlmapFile = sqlmapFile;
        }

        @Override
        public void read(SqlRegistry registry) {
//            StreamSource xmlSource = new StreamSource(getClass().getResourceAsStream("sqlmap.xml"));
            try {
                StreamSource xmlSource = new StreamSource(this.sqlmap.getInputStream());
                Sqlmap sqlmap = (Sqlmap) this.unmarshaller.unmarshal(xmlSource);
                for (SqlType sql : sqlmap.getSql()) {
                    sqlRegistry.registry(sql.getKey(), sql.getValue());
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
