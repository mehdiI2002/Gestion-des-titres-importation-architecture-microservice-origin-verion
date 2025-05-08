package com.example.security_service.service;

import com.example.security_service.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Service
public class JwtService {
    private static final String SECRET_KEY="8F3B7D2D4C5A0E6F8A3B2F6D8E5A2C3B4C1A7E5F6F2B8A4D3E1B7C4F5A2D8E3";


    public String extractUserNameFromJwt(String token) {
        return extractClaim(token,Claims::getSubject);
    }
    private Claims extractAllClaims(String token ){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    public <T>  T extractClaim(String token , Function<Claims,T> claimResolver){
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }


    public String generateToken( UserDetails userDetails){
        Map<String, Object> claims = new HashMap<>();
        if (userDetails instanceof User) {
            claims.put("authorities", userDetails.getAuthorities());
        }
        return generateToken(claims,userDetails);

    }
    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ){
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*60*24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    public boolean isTokenValid(String token ,UserDetails userDetails){
      final String userNameInToken = extractUserNameFromJwt(token);
       return userNameInToken.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractClaim(token,Claims::getExpiration).before(new Date());
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
