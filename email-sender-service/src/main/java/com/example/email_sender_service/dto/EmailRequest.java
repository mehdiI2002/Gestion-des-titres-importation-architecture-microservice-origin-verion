package com.example.email_sender_service.dto;

public class EmailRequest {
    private String  email;
    private String role ;

    public EmailRequest(String email, String role) {
        this.email = email;
        this.role = role;
    }

    public EmailRequest() {
    }
    public String getRole() {
        return role;
    }

    public String getEmail() {
        return email;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
