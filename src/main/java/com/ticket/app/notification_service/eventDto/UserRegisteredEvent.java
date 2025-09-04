package com.ticket.app.notification_service.eventDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class UserRegisteredEvent {
    private String eventType = "UserRegistered";
    private String user_id;
    private String email;
    private String name;
}
