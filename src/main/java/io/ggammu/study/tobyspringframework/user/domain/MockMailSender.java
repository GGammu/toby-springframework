package io.ggammu.study.tobyspringframework.user.domain;

import java.util.ArrayList;
import java.util.List;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class MockMailSender implements MailSender {
    private List<String> request = new ArrayList<String>();

    public List<String> getRequest() {
        return request;
    }

    @Override
    public void send(SimpleMailMessage simpleMessage) throws MailException {
        request.add(simpleMessage.getTo()[0]);
    }

    @Override
    public void send(SimpleMailMessage... simpleMessages) throws MailException {
    }
}
