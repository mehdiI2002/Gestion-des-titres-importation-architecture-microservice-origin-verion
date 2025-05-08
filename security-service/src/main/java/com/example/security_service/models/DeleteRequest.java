package com.example.security_service.models;

public class DeleteRequest {
    private String email ;

    public DeleteRequest(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
    public DeleteRequest(){

    }
}

