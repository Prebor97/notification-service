package com.ticket.app.notification_service.eventDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class UserDeletedEvent {
    private String eventType = "UserDeleted";
    private String user_id;
    private String email;
    private String name;
}
