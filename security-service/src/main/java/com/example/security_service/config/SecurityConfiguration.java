package com.example.security_service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    private final  JwtAuthentificationFilter jwtAuthFilter;
    private  final   AuthenticationProvider authenticationProvider;
@Autowired
    public SecurityConfiguration(JwtAuthentificationFilter jwtAuthFilter, AuthenticationProvider authenticationProvider) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.authenticationProvider = authenticationProvider;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
        http
                .csrf(csrf -> csrf.disable()) // Désactivation de la protection CSRF
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/register", "/auth/authenticate","/admins/listAdmin").permitAll()
                        .requestMatchers("/auth/democontroller").hasAuthority("USER")
                        .requestMatchers("/auth/user/deleteUser").hasAuthority("ADMIN")
                        .requestMatchers("/auth/user/users").hasAuthority("ADMIN")
                        .requestMatchers("/auth/user/createUser").hasAuthority("ADMIN")
                        .requestMatchers("/auth/user/updateUser").hasAuthority("ADMIN")
                        .anyRequest().authenticated() //// Authentifie toutes les autres requête

                )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) /// La session est stateless (pas de session persistante)
        )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
}
}
