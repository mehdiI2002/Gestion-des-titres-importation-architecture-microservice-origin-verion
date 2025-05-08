package com.example.security_service.web;

import com.example.security_service.models.AuthenticationRequest;
import com.example.security_service.models.AuthenticationResponse;
import com.example.security_service.service.AuthenticationService;
import com.example.security_service.models.RegisterRequest;
import com.example.security_service.config.JwtAuthentificationFilter;

import com.example.security_service.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


@RestController
public class AuthentificationController {
    private AuthenticationService service ;
    private JwtAuthentificationFilter filter ;
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthentificationFilter.class);
private UserService userService;
    public AuthentificationController(AuthenticationService service, JwtAuthentificationFilter filter, UserService userService) {
        this.service = service;
        this.filter = filter;
        this.userService = userService;

    }
    @PostMapping("/auth/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
       return ResponseEntity.ok(service.register(request));
    }
    @PostMapping("/auth/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        logger.info("Authenticating user with email: {}", request.getEmail());
        logger.info("Password provided: {}", request.getPassword());
        return ResponseEntity.ok(service.authenticate(request));
    }
    @PostMapping("/auth/authenticatebytoken")
    public ResponseEntity<Map<String, Object>> authenticateByToken(HttpServletRequest request) {
        try {
            logger.info("Authenticating token for request: {}", request.getRequestURI());

            boolean authenticated = filter.authenticateByJwtToken(request);

            if (authenticated) {
                logger.info("Authentication successful");
                return ResponseEntity.ok(Map.of(
                        "status", "success",
                        "message", "Authentication successful",
                        "path", request.getRequestURI(),
                        "timestamp", System.currentTimeMillis()
                ));
            }

            logger.warn("Authentication failed");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
                    "status", "error",
                    "message", "Authentication failed: Invalid or expired token",
                    "path", request.getRequestURI(),
                    "timestamp", System.currentTimeMillis()
            ));
        } catch (Exception e) {
            logger.error("Exception during authentication", e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
                    "status", "error",
                    "message", "Authentication failed: " + e.getMessage(),
                    "path", request.getRequestURI(),
                    "timestamp", System.currentTimeMillis()
            ));
        }
    }
@GetMapping("/admins/listAdmin")
        public List<String> getAdmins(){
 List<String> admins = userService.getAllAdmins();
 return admins ;
    }


}