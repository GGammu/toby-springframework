package io.younghwang.springframeworkbasic.user.sqlservice;

import io.younghwang.springframeworkbasic.user.sqlservice.jaxb.SqlType;
import io.younghwang.springframeworkbasic.user.sqlservice.jaxb.Sqlmap;
import org.springframework.oxm.Unmarshaller;

import javax.annotation.PostConstruct;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.sql.SQLException;

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
        } catch (SQLException e) {
            throw new SqlRetrievalFailureException("");
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    private class OxmSqlReader implements SqlReader {
        private Unmarshaller unmarshaller;
        private String sqlmapFile;

        public void setUnmarshaller(Unmarshaller unmarshaller) {
            this.unmarshaller = unmarshaller;
        }

        public void setSqlmapFile(String sqlmapFile) {
            this.sqlmapFile = sqlmapFile;
        }

        @Override
        public void read(SqlRegistry registry) {
            StreamSource xmlSource = new StreamSource(getClass().getResourceAsStream("sqlmap.xml"));
            try {
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
