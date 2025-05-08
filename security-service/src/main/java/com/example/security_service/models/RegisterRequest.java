package com.example.security_service.models;


import com.example.security_service.user.Role;

public class RegisterRequest {
    private String firstName ;
    private String email ;
    private String lastName;
    private String password ;
    private Role role ;

    public String getFirstName() {
        return firstName;
    }

    public String getEmail() {
        return email;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    public RegisterRequest() {
    }
    public RegisterRequest(String firstName, String email, String lastName, String password, Role role) {
        this.firstName = firstName;
        this.email = email;
        this.lastName = lastName;
        this.password = password;
       this.role = role;
    }
    public Role getRole() {
        return role;
    }

}
