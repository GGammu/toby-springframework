package io.ggammu.study.tobyspringframework.user.domain;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Setter
@Getter
public class UserService {
    public static final int MIN_LOGCOUNT_FOR_SILER = 50;
    public static final int MIN_RECCOMEND_FOR_GOLD = 30;

    UserDao userDao;
    //    UserLevelUpgradePolicy userLevelUpgradePolicy;
    private DataSource dataSource;

    public void upgradeLevels() throws SQLException {
        // DB Connection 생성
        TransactionSynchronizationManager.initSynchronization();
        Connection c = DataSourceUtils.getConnection(dataSource);

        // Transaction 시작
        c.setAutoCommit(false);
        try {
            // DAO 메소드 호출
            List<User> users = userDao.getAll();
            for (User user : users) {
                if (canUpgradeLevel(user)) {
                    upgradeLevel(user);
                }
            }
            // Transaction Commit
            c.commit();
        }
        catch (Exception e) {
            // Transaction Rollback
            c.rollback();
            throw e;
        }
        finally {
            // DB Connection 종료
            DataSourceUtils.releaseConnection(c, dataSource);
            TransactionSynchronizationManager.unbindResource(this.dataSource);
            TransactionSynchronizationManager.clearSynchronization();
        }
    }

    public boolean canUpgradeLevel(User user) {
        Level currentLevel = user.getLevel();
        switch (currentLevel) {
            case BASIC:
                return user.getLogin() >= MIN_LOGCOUNT_FOR_SILER;
            case SILVER:
                return user.getRecommend() >= MIN_RECCOMEND_FOR_GOLD;
            case GOLD:
                return false;
            default:
                throw new IllegalArgumentException("Unknown Level: " + currentLevel);
        }
    }

    protected void upgradeLevel(User user) {
        user.upgradeLevel();
        userDao.update(user);
    }

    public void add(User user) {
        if (user.getLevel() == null) user.setLevel(Level.BASIC);
        userDao.add(user);
    }
}
