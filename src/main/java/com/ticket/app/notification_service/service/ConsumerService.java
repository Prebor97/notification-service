package com.ticket.app.notification_service.service;

import com.ticket.app.eventdto.UserEvents;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConsumerService {

    private static final Logger logger = LoggerFactory.getLogger(ConsumerService.class);

    private final EventProcessing processing;

    @KafkaListener(topics = "user-topics", groupId = "notification-group-user")
    public void handleUserRegistrationEvent(UserEvents event) {
        logger.info("Received user event {}: ", event);
        processing.processUserServiceEvent(event);
        logger.info("User event processed");
    }

}
