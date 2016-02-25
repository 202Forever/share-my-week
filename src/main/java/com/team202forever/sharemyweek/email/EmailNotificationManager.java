package com.team202forever.sharemyweek.email;

import com.team202forever.sharemyweek.data.models.User;
import com.team202forever.sharemyweek.data.models.Week;
import com.team202forever.sharemyweek.data.models.WeekUser;
import com.team202forever.sharemyweek.data.processors.WeekProcessor;
import com.team202forever.sharemyweek.data.repository.UserRepository;
import com.team202forever.sharemyweek.data.repository.WeekRepository;
import com.team202forever.sharemyweek.exception.EmailNotificationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.hateoas.Link;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

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

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private SpringTemplateEngine templateEngine;

    @HandleBeforeCreate
    public void testEmailServer(Week week) throws MessagingException {
        mailSender.testConnection();
    }

    @HandleAfterCreate
    public void newWeekEmail(Week week) throws Exception {
    	try {
    		sendNewEmail(week);
    	} catch (Exception e) {
    		weekRepository.delete(week);
    		throw e;
    	}
    }
    
    private void sendNewEmail(Week week) throws EmailNotificationException, MessagingException, NoSuchMethodException, SecurityException {
        Collection<WeekUser> providedUsers = week.getUsers();
        Set<User> failedUsers = new LinkedHashSet<>();
        for (WeekUser weekUser : providedUsers) {
        	User user = weekUser.getUserInfo();
            MimeMessage mimeMessage = this.mailSender.createMimeMessage();
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "utf-8");
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
                message.setSubject("ShareMyWeek: Your week is shared!");
            } catch (MessagingException e) {
                logger.error("Unexpected error on setting email subject");
                throw e;
            }
            Context ctx = new Context();
            ctx.setVariable("publicLink", WeekProcessor.linkToWeek(week, "page").getHref());
            Link link = WeekProcessor.linkToWeek(week, user, "page");
            if (providedUsers.size() == 1) {
                week.getLinks().add(link);
            }
            ctx.setVariable("privateLink", link.getHref());
            String html = templateEngine.process("mail/new-week", ctx);
            try {
                message.setText(html, true);
            } catch (MessagingException e) {
                logger.error("Unexpected error on setting email body");
                throw e;
            }
            try {
                this.mailSender.send(mimeMessage);
                User stored = userRepository.findOne(user.getHashId());
                if (stored != null) {
                    stored.getWeeks().add(week);
                    user = stored;
                }
                userRepository.save(user);
            } catch (MailSendException e) {
                logger.error(e.getMessage(), e);
                failedUsers.add(user);
            }
        }
        if (failedUsers.size() == providedUsers.size()) {
            throw new EmailNotificationException("Failed to send email to provided email addresses");
        }
    }

}
