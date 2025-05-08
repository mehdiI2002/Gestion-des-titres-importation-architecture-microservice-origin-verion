package com.example.email_sender_service.web;

import com.example.email_sender_service.dto.EmailRequest;

import com.example.email_sender_service.service.SendEmailsToAdmins;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/email/")
public class EmailController {
    private SendEmailsToAdmins sendEmailsToAdmins;
    public EmailController(SendEmailsToAdmins sendEmailsToAdmins) {
        this.sendEmailsToAdmins = sendEmailsToAdmins;
    }
    @PostMapping("/send-email")
    public void sendEmail(@RequestBody EmailRequest request) {
String user = request.getEmail();
        System.out.println("user"+ user);
String role = request.getRole();
sendEmailsToAdmins.sendMailsToAdmins(user,role);
    }
    @GetMapping("/refuserEmail")
    public void refuserEmail(@RequestParam String user) {
        System.out.println("Refus de l'inscription pour : " + user);
        sendEmailsToAdmins.sendRejectionEmail(user);
    }

}
