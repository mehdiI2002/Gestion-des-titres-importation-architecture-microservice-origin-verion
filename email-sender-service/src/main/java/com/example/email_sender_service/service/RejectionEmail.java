package com.example.email_sender_service.service;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import jakarta.mail.MessagingException;

@Service
public class RejectionEmail {
    private JavaMailSender mailSender;
    public void sendRejectionEmail(String userEmail) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("portnetbcpnoreply@gmail.com");
            helper.setTo(userEmail);
            helper.setSubject("Demande d'inscription refusée");
            String body = "<p>Bonjour,</p>"
                    + "<p>Nous vous informons que votre demande d'inscription a été refusée.</p>"
                    + "<p>Pour plus d'informations, veuillez contacter l'administrateur.</p>";

            helper.setText(body, true);  // Contenu HTML
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de l'envoi de l'email de refus.");
        }
    }
}
