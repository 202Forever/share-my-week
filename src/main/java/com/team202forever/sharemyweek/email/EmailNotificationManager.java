package com.team202forever.sharemyweek.email;

import com.team202forever.sharemyweek.data.models.User;
import com.team202forever.sharemyweek.data.models.Week;
import com.team202forever.sharemyweek.data.repository.WeekRepository;
import com.team202forever.sharemyweek.exception.EmailNotificationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

@Service
@RepositoryEventHandler
public class EmailNotificationManager {

    private final Logger logger = LoggerFactory.getLogger(EmailNotificationManager.class);

    @Autowired
    private JavaMailSenderImpl mailSender;

    @Autowired
    private WeekRepository weekRepository;

    @HandleBeforeCreate
    public void testEmailServer(Week week) throws MessagingException {
        mailSender.testConnection();
    }

    @HandleAfterCreate
    public void emailWeekLink(Week week) throws EmailNotificationException, MessagingException {
        Collection<User> providedUsers = week.getUsers();
        Set<User> failedUsers = new LinkedHashSet<>();
        for (User user : providedUsers) {
            MimeMessage mimeMessage = this.mailSender.createMimeMessage();
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
            try {
                message.setFrom("team202forever@sharemyweek.com");
            } catch (MessagingException e) {
                logger.error("Unexpected error on setting message sender email");
                throw e;
            }
            try {
                message.setTo(user.getEmail());
            } catch (MessagingException e) {
                logger.error("Failed to set the message recipient {}", user.getEmail());
                throw e;
            }
            try {
                message.setSubject("This is the message subject");
            } catch (MessagingException e) {
                logger.error("Unexpected error on setting email subject");
                throw e;
            }
            try {
                message.setText("This is the message body");
            } catch (MessagingException e) {
                logger.error("Unexpected error on setting email body");
                throw e;
            }
            try {
                this.mailSender.send(mimeMessage);
            } catch (MailSendException e) {
                logger.error(e.getMessage(), e);
                failedUsers.add(user);
            }
        }
        if (providedUsers.size() == failedUsers.size()) {
            weekRepository.delete(week);
            throw new EmailNotificationException("Failed to send email to provided email addresses");
        }
    }

}
