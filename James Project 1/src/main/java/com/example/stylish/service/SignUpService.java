package com.example.stylish.service;

import com.example.stylish.dto.JwtResponse;
import com.example.stylish.dto.UserDTO;
import com.example.stylish.exception.EmailAlreadyExistsException;
import com.example.stylish.exception.ClientErrorException;
import com.example.stylish.model.User;
import com.example.stylish.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class SignUpService {

    private static final Logger log = LoggerFactory.getLogger(SignUpService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SecretKey secretKey;

    public ResponseEntity<Map<String, Object>> signUp(User user) {

        // Handle client errors
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            log.info("Email is missing in the request");
            throw new ClientErrorException("Email is required");
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            log.info("Password is missing in the request");
            throw new ClientErrorException("Password is required");
        }

        // Check if email already exists
        if (userRepository.existsByEmail(user.getEmail())) {
            log.info("Email already exists: {}", user.getEmail());
            throw new EmailAlreadyExistsException("The email " + user.getEmail() + " is already registered.");
        }

        user.setProvider("native");  // Set provider to "native"
        user.setPasswordHash(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        // Reload user data to get the ID after saving
        Optional<User> savedUserOptional = userRepository.findByEmail(user.getEmail());
        User savedUser = savedUserOptional.orElseThrow(() -> new RuntimeException("Failed to retrieve user after saving."));

        // Include all user information in the JWT
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", savedUser.getId());
        claims.put("provider", savedUser.getProvider());
        claims.put("name", savedUser.getName());
        claims.put("email", savedUser.getEmail());
        claims.put("picture", savedUser.getPicture());

        // Create access token
        String accessToken = Jwts.builder()
                .setClaims(claims)
                .setSubject(savedUser.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600 * 1000)) // 1 hour expiration
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
        long accessExpired = 3600;

        // Build user data using DTO
        UserDTO userDTO = new UserDTO(
            savedUser.getId(),
            savedUser.getProvider(),
            savedUser.getName(),
            savedUser.getEmail(),
            savedUser.getPicture()
        );

        // Build JWT response using DTO
        JwtResponse jwtResponse = new JwtResponse(accessToken, accessExpired, userDTO);

        // Construct final response
        Map<String, Object> response = new HashMap<>();
        response.put("data", jwtResponse);

        return ResponseEntity.ok(response);
    }
}
