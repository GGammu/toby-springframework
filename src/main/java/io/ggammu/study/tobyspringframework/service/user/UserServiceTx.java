package io.ggammu.study.tobyspringframework.service.user;

import io.ggammu.study.tobyspringframework.user.domain.User;
import java.sql.SQLException;
import lombok.Setter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Setter
public class UserServiceTx implements UserService {

    private PlatformTransactionManager transactionManager;

    UserService userService;

    @Override
    public void upgradeLevels() throws SQLException {
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            userService.upgradeLevels();
            this.transactionManager.commit(status);
        }
        catch (RuntimeException e) {
            this.transactionManager.rollback(status);
            throw e;
        }
    }

    @Override
    public void add(User user) {
        userService.add(user);
    }

}
