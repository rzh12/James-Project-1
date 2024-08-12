package com.example.stylish.service;

import com.example.stylish.dto.JwtResponse;
import com.example.stylish.dto.UserDTO;
import com.example.stylish.exception.ClientErrorException;
import com.example.stylish.exception.SignInFailedException;
import com.example.stylish.model.User;
import com.example.stylish.repository.UserRepository;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Version;
import com.restfb.exception.FacebookOAuthException;
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
public class SignInService {

    private static final Logger log = LoggerFactory.getLogger(SignInService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SecretKey secretKey;

    public ResponseEntity<Map<String, Object>> signIn(Map<String, String> credentials) {
        String provider = credentials.get("provider");
        Optional<User> userOptional = Optional.empty();

        if ("native".equals(provider)) {
            // Handle native sign in
            String email = credentials.get("email");
            String password = credentials.get("password");
            userOptional = userRepository.findByEmail(email);
            if (userOptional.isEmpty() || !passwordEncoder.matches(password, userOptional.get().getPasswordHash())) {
                log.info("Invalid email or password for email: {}", email);
                throw new SignInFailedException("Invalid email or password");
            }
        } else if ("facebook".equals(provider)) {
            // Handle Facebook sign in
            String accessToken = credentials.get("access_token");
            FacebookClient facebookClient = new DefaultFacebookClient(accessToken, Version.LATEST);
            com.restfb.types.User facebookUser;
            try {
                facebookUser = facebookClient.fetchObject("me", com.restfb.types.User.class,
                        com.restfb.Parameter.with("fields", "id,name,email,picture"));
            } catch (FacebookOAuthException e) {
                log.info("Invalid Facebook access token: {}", accessToken);
                throw new SignInFailedException("Invalid Facebook access token");
            }
            userOptional = userRepository.findByEmail(facebookUser.getEmail());
            if (userOptional.isEmpty()) {
                User user = new User();
                user.setProvider("facebook");
                user.setName(facebookUser.getName());
                user.setEmail(facebookUser.getEmail());
                user.setPicture(facebookUser.getPicture() != null ? facebookUser.getPicture().getUrl() : null);
                userRepository.save(user);
                userOptional = Optional.of(user);
            }
        } else {
            // Handle invalid provider
            throw new ClientErrorException("Invalid provider");
        }

        User user = userOptional.get();

        // Include all user information in the JWT
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());
        claims.put("provider", user.getProvider());
        claims.put("name", user.getName());
        claims.put("email", user.getEmail());
        claims.put("picture", user.getPicture());

        // Create access token
        String accessToken = Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600 * 1000))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
        long accessExpired = 3600;

        log.info("Access token generated for user: {}", user);
        log.info("JWT: {}", accessToken);

        // Build user data using DTO
        UserDTO userDTO = new UserDTO(
            user.getId(),
            user.getProvider(),
            user.getName(),
            user.getEmail(),
            user.getPicture()
        );

        // Build JWT response using DTO
        JwtResponse jwtResponse = new JwtResponse(accessToken, accessExpired, userDTO);

        // Construct final response
        Map<String, Object> response = new HashMap<>();
        response.put("data", jwtResponse);

        return ResponseEntity.ok(response);

    }
}
