package org.mehdi.gatewayservice.filters;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory {

    private final WebClient.Builder webClientBuilder;

    public AuthenticationFilter(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }
    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                throw new RuntimeException("Missing authorization information");
            }
            String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
            return webClientBuilder.build()
                    .post()
                    .uri("lb://SECURITY-SERVICE/auth/authenticatebytoken") // URI du service de sécurité
                    .header(HttpHeaders.AUTHORIZATION, authHeader) // Passer le token dans les en-têtes
                    .retrieve()
                    .bodyToMono(Void.class) // Ne récupère aucune donnée en réponse
                    .then(chain.filter(exchange)); // Continuer avec la chaîne de filtres
        };
    }
}