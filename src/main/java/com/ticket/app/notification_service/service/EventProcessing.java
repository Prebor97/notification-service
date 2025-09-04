package com.ticket.app.notification_service.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ticket.app.notification_service.eventDto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EventProcessing {

    private final EmailService emailService;

    public <T> void processUserServiceEvent(
            String message,
            Class<T> eventClass,
            String templateFile,
            String subject) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            T event = mapper.readValue(message, eventClass);

            Map<String, String> placeholders = getStringStringMap(event);

            Path templatePath = Path.of("src/main/resources/templates/" + templateFile);
            emailService.sendMail(templatePath, placeholders.get("email"), subject, placeholders);

        } catch (IOException | MessagingException | jakarta.mail.MessagingException e) {
            System.out.println("Error processing event: " + e.getMessage());
        }
    }

    private static <T> Map<String, String> getStringStringMap(T event) {
        Map<String, String> placeholders = new HashMap<>();
        if (event instanceof UserRegisteredEvent registered) {
            placeholders.put("user_id", registered.getUser_id());
            placeholders.put("name", registered.getName());
            placeholders.put("email", registered.getEmail());
        } else if (event instanceof UserRoleUpdatedEvent updatedEvent) {
            placeholders.put("user_id", updatedEvent.getUser_id());
            placeholders.put("name", updatedEvent.getName());
            placeholders.put("email", updatedEvent.getEmail());
        } else if (event instanceof UserDeletedEvent deletedEvent) {
            placeholders.put("user_id", deletedEvent.getUser_id());
            placeholders.put("name", deletedEvent.getName());
            placeholders.put("email", deletedEvent.getEmail());
        } else if (event instanceof AdminCreatedEvent createdEvent) {
            placeholders.put("user_id", createdEvent.getUser_id());
            placeholders.put("name", createdEvent.getName());
            placeholders.put("email", createdEvent.getEmail());
        } else if (event instanceof UserLoggedEvent logged) {
            placeholders.put("user_id", logged.getUser_id());
            placeholders.put("name", logged.getName());
            placeholders.put("email", logged.getEmail());
            placeholders.put("login_date", logged.getLogin_date().toString());
        } else if (event instanceof PasswordResetRequestEvent resetRequestEvent) {
            placeholders.put("user_id", resetRequestEvent.getUser_id());
            placeholders.put("name", resetRequestEvent.getName());
            placeholders.put("email", resetRequestEvent.getEmail());
            placeholders.put("token", resetRequestEvent.getToken());
        }
        return placeholders;
    }
}
