package com.example.security_service.dto;

import com.example.security_service.user.Role;

public record UserDTO (
    String firstName ,
     String lastName ,
    String email ,
    Role role
    ) {}
