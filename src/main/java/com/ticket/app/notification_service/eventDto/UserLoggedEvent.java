package com.ticket.app.notification_service.eventDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class UserLoggedEvent {
    private String eventType = "UserLoggedIn";
    private String user_id;
    private String email;
    private String name;
    private LocalDateTime login_date;
}
