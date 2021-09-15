package io.ggammu.study.tobyspringframework.user.domain;

import io.ggammu.study.tobyspringframework.service.user.UserService;
import java.sql.SQLException;
import lombok.Setter;
import org.springframework.mail.MailSender;

@Setter
public class TestUserService implements UserService {
    private String id;
    private UserDao userDao;
    private MailSender mailSender;

    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    TestUserService(String id) {
        this.id = id;
    }

    public void upgradeLevels(User user) throws SQLException {
        if (user.getId().equals(this.id)) {
            throw new TestUserServiceException();
        }
    }

    @Override
    public void upgradeLevels() throws SQLException {

    }

    @Override
    public void add(User user) {

    }
}
