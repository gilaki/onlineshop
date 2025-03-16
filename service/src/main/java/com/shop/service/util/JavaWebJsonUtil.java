package com.shop.service.util;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JavaWebJsonUtil {
    private SecretKey secretKey;
    private Long jwtExpireTimeMs;

    @Value("${jwt.secret}")
    String propertiesSecret;
    @Value("${jwt.expirationMs}")
    Long propertiesExpirationMs;

    @PostConstruct
    private void init(){
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(propertiesSecret));
        this.jwtExpireTimeMs = propertiesExpirationMs;
    }
    public String generateToken(String username){
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpireTimeMs);
        return Jwts.builder()
                .subject(username)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(secretKey)
                .compact();
    }
    public boolean validateToken(String token){
        try{
            Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token);
            return true;
        }catch (JwtException e){
            return false;
        }
    }
    public String extractUsernameFromToken(String token){
        try {
            return Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject();
        }catch (JwtException e){
            throw new JwtException("Invalid JWT token");
        }
    }


}