package com.ticket.app.notification_service.service;

import com.ticket.app.eventdto.UserEvents;
import com.ticket.app.notification_service.util.EmailSubject;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EventProcessing {

    private static final Logger logger = LoggerFactory.getLogger(EventProcessing.class);
    private final EmailService emailService;

    public  void processUserServiceEvent(
            UserEvents events) {
        try {
            String templateFile = getTemplate(events);
            Map<String, String> placeholders = getStringStringMap(events);
            Path templatePath = Path.of("src/main/resources/templates/" + templateFile);
            logger.info("email message sending ......................................................................");
            emailService.sendMail(templatePath, placeholders.get("email"), events.getSubject(), placeholders);
            logger.info("message sent");
        } catch (IOException | MessagingException | jakarta.mail.MessagingException e) {
            logger.info("Error processing event: {} ",  e.getMessage());
        }
    }

    private static Map<String, String> getStringStringMap(UserEvents event) {
        Map<String, String> placeholders = new HashMap<>();
        if (event.getSubject().equals(EmailSubject.USER_REGISTERED_SUBJECT)
                ||(event.getSubject().equals(EmailSubject.USER_ROLE_UPDATED_SUBJECT)
                ||(event.getSubject().equals(EmailSubject.USER_DELETED_SUBJECT)))) {
            placeholders.put("user_id", event.getUserId());
            placeholders.put("name", event.getName());
            placeholders.put("email", event.getEmail());
        } else if (event.getSubject().equals(EmailSubject.ADMIN_CREATED_SUBJECT)) {
            placeholders.put("user_id", event.getUserId());
            placeholders.put("name", event.getName());
            placeholders.put("email", event.getEmail());
            placeholders.put("password", event.getPassword());
        } else if (event.getSubject().equals(EmailSubject.USER_LOGGED_IN_SUBJECT)) {
            placeholders.put("user_id", event.getUserId());
            placeholders.put("name", event.getEmail());
            placeholders.put("email", event.getEmail());
            placeholders.put("login_date", event.getLoginDate());
        } else if (event.getSubject().equals(EmailSubject.PASSWORD_RESET_SUBJECT)) {
            placeholders.put("user_id", event.getUserId());
            placeholders.put("name", event.getName());
            placeholders.put("email", event.getEmail());
            placeholders.put("token", event.getToken());
        }
        return placeholders;
    }
    private static String getTemplate(UserEvents events){
        String templateFile = "";
        if (events.getSubject().equals(EmailSubject.PASSWORD_RESET_SUBJECT)){
            templateFile = "password-reset-mail-template.html";
        } else if (events.getSubject().equals(EmailSubject.USER_REGISTERED_SUBJECT)) {
            templateFile  = "user-registered-mail-template.html";
        } else if (events.getSubject().equals(EmailSubject.USER_DELETED_SUBJECT)) {
            templateFile = "user-deleted-mail-template.html";
        } else if (events.getSubject().equals(EmailSubject.ADMIN_CREATED_SUBJECT)) {
            templateFile = "admin-created-mail-template.html";
        } else if (events.getSubject().equals(EmailSubject.USER_LOGGED_IN_SUBJECT)) {
            templateFile = "user-logged-in-mail-template.html";
        } else if (events.getSubject().equals(EmailSubject.USER_ROLE_UPDATED_SUBJECT)) {
            templateFile = "role-updated-mail-template.html";
        }
        return templateFile;
    }
}
