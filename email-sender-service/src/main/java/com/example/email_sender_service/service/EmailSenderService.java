package com.example.email_sender_service.service;

import jakarta.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import jakarta.mail.MessagingException;

import java.util.List;

@Service
public class EmailSenderService {

    private JavaMailSender  mailSender;

    public EmailSenderService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
    public void sendAdminEmail(List<String> adminEmails, String userEmail, String role) {
        try {
            for (String adminEmail : adminEmails) {
                MimeMessage message = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, true);

                helper.setFrom("portnetbcpnoreply@gmail.com");

                helper.setTo(adminEmail);
                helper.setSubject("Nouvelle demande d'inscription : " + userEmail);
                String body = "<p>L'utilisateur <strong>" + userEmail + "</strong> a fait une demande d'accès à l'application en tant que <strong>" + role + "</strong>.</p>"
                        + "<p>Souhaitez-vous approuver cette demande ?</p>"
                        + "<a href='http:localhost/9002/validerEmail?user=" + userEmail + "'>✅ Accepter</a>"
                        + "<a href='http:localhost/9002/email/refuserEmail?user=" + userEmail + "'>❌ Refuser</a>";

                helper.setText(body, true);  // HTML content
                message.addHeader("X-Priority", "1");
                message.addHeader("X-MSMail-Priority", "High");
                message.addHeader("Importance", "High");
                mailSender.send(message);
            }
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de l'envoi de l'email aux admins.");
        }

    }
}