package com.example.stylish.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Component
public class JwtUtil {

    private final SecretKey secretKey;

    // Constructor to initialize the secret key
    public JwtUtil(@Value("${jwt.secret.key}") String secretKeyString) {
        byte[] decodedKey = Base64.getDecoder().decode(secretKeyString);
        this.secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "HmacSHA256");
    }

    // Method to extract claims from a JWT token
    public Claims extractClaims(String token) {
        try {
            // Parse the JWT token and extract the claims
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return claimsJws.getBody();
        } catch (JwtException e) {
            // Handle invalid or expired token
            throw new JwtException("Token is expired or invalid", e);
        }
    }
}
