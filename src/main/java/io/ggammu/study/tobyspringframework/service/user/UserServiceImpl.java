package io.ggammu.study.tobyspringframework.service.user;

import io.ggammu.study.tobyspringframework.user.domain.Level;
import io.ggammu.study.tobyspringframework.user.domain.User;
import io.ggammu.study.tobyspringframework.user.domain.UserDao;
import java.net.InetAddress;
import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import lombok.Getter;
import lombok.Setter;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;


@Setter
@Getter
public class UserServiceImpl implements UserService {

    public static final int MIN_LOGCOUNT_FOR_SILER = 50;
    public static final int MIN_RECCOMEND_FOR_GOLD = 30;

    private MailSender mailSender;
    private UserDao userDao;

    @Override
    public void upgradeLevels() {
        // DAO 메소드 호출
        List<User> users = userDao.getAll();
        for (User user : users) {
            if (canUpgradeLevel(user)) {
                upgradeLevel(user);
            }
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
        sendUpgradeEmail(user);
    }

    private void sendUpgradeEmail(User user) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setFrom("ykcul02@gmail.com");
        mailMessage.setSubject("Upgrade 안내");
        mailMessage.setText("사용자 등급이 " + user.getLevel().name());

        mailSender.send(mailMessage);
    }

    @Override
    public void add(User user) {
        if (user.getLevel() == null) user.setLevel(Level.BASIC);
        userDao.add(user);
    }

}
