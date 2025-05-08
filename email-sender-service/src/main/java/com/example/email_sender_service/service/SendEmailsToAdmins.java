package com.example.email_sender_service.service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SendEmailsToAdmins {
    private EmailSenderService emailSenderService;
    private AdminEmailsService adminEmailsService;
   private RejectionEmail rejectionEmail;
    public SendEmailsToAdmins(EmailSenderService emailSenderService, AdminEmailsService adminEmailsService,RejectionEmail rejectionEmail) {
        this.emailSenderService = emailSenderService;
        this.adminEmailsService = adminEmailsService;
        this.rejectionEmail = rejectionEmail;
    }
    public void sendMailsToAdmins(String user, String role) {
        List<String> adminEmails = adminEmailsService.getAdminEmailsFromUserService();
        System.out.println("admins: " + adminEmails);
        emailSenderService.sendAdminEmail(adminEmails, user, role);
    }


    public void sendRejectionEmail(String user) {
        rejectionEmail.sendRejectionEmail(user);
    }
}
