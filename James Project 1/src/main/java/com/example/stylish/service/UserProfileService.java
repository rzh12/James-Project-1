package com.example.stylish.service;

import com.example.stylish.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserProfileService {

    private static final Logger log = LoggerFactory.getLogger(UserProfileService.class);

    @Autowired
    private JwtUtil jwtUtil;

    public ResponseEntity<Map<String, Object>> getUserProfile(String authorization) {
        // Remove "Bearer " prefix
        String token = authorization.substring(7);

        // Extract claims from token
        Claims claims = jwtUtil.extractClaims(token);
        log.info("Extracted claims from token: {}", claims);

        // Build user data from claims
        Map<String, Object> userData = new HashMap<>();
        userData.put("provider", claims.get("provider"));
        userData.put("name", claims.get("name"));
        userData.put("email", claims.get("email"));
        userData.put("picture", claims.get("picture"));

        // Construct final response
        Map<String, Object> response = new HashMap<>();
        response.put("data", userData);

        log.info("User profile data: {}", userData);

        return ResponseEntity.ok(response);
    }
}
