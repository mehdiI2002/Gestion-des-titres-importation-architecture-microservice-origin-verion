package com.example.email_sender_service.component;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EmailConfiguration {
    @Value("${GMAIL_APP_PASSWORD}")
    private String gmailAppPassword;
}
