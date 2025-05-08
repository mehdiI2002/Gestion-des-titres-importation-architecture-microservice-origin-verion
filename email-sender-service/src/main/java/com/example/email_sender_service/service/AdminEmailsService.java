package com.example.email_sender_service.service;


import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
@Service
public class AdminEmailsService {
    private  RestTemplate restTemplate;
    public AdminEmailsService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    public List<String> getAdminEmailsFromUserService() {
        String url = "http://localhost:9001/admins/listAdmin";  // URL de l'API REST
        List<String> adminEmails = restTemplate.getForObject(url, List.class);
        System.out.println( "admins recuperer avec succes "+adminEmails);// Récupère la liste des emails
        return adminEmails;
    }
}
