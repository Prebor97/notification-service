package com.ticket.app.notification_service.service;

import com.ticket.app.notification_service.eventDto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConsumerService {

    private final EmailService emailService;
    private final EventProcessing processing;

    @KafkaListener(topics = "user-registered-topic", groupId = "notification-group-user")
    public void handleUserRegistrationEvent(String message) {
        processing.processUserServiceEvent(message, UserRegisteredEvent.class,"user-registered-mail-template.html","Activate Your Account!" );
    }

    @KafkaListener(topics = "user-deleted-topic", groupId = "notification-group-user")
    public void handleUserDeletedEvent(String message) {
        processing.processUserServiceEvent(message, UserDeletedEvent.class,"user-deleted-mail-template.html","Account Deletion Notification!!!" );
    }

    @KafkaListener(topics = "user-logged-in-topic", groupId = "notification-group-user")
    public void handleUserLoggedEvent(String message) {
        processing.processUserServiceEvent(message, UserLoggedEvent.class,"user-logged-in-mail-template.html","Login Alert!" );
    }

    @KafkaListener(topics = "admin-created-topic", groupId = "notification-group-user")
    public void handleAdminCreatedEvent(String message) {
        processing.processUserServiceEvent(message, AdminCreatedEvent.class,"admin-created-mail-template.html","Admin Account Created" );
    }

    @KafkaListener(topics = "user-role-updated-topic", groupId = "notification-group-user")
    public void handleUserRoleUpdatedEvent(String message) {
        processing.processUserServiceEvent(message, UserLoggedEvent.class,"role-updated-mail-template.html","Role update Alert!" );
    }

    @KafkaListener(topics = "password-reset-requested-topic", groupId = "notification-group-user")
    public void handlePasswordResetEvent(String message) {
        processing.processUserServiceEvent(message, PasswordResetRequestEvent.class,"password-reset-mail-template.html","Password Reset request" );
    }

}
