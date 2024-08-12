package com.example.stylish.repository;

import com.example.stylish.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.Optional;

@Repository
public class UserRepository {

    private static final Logger log = LoggerFactory.getLogger(UserRepository.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Check if a user exists by their email
    public boolean existsByEmail(String email) {
        String sql = "SELECT COUNT(*) FROM user WHERE email = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{email}, Integer.class);
        boolean exists = count != null && count > 0;
        log.info("Checking if email exists: {}, result: {}", email, exists);
        return exists;
    }

    // Save a new user to the database
    public User save(User user) {
        String sql = "INSERT INTO user (provider, name, email, password_hash, picture) VALUES (?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, user.getProvider());
            ps.setString(2, user.getName());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPasswordHash());
            ps.setString(5, user.getPicture());
            return ps;
        }, keyHolder);

        user.setId(keyHolder.getKey().intValue());
        log.info("Saved user: {}", user);
        return user;
    }

    /**
     * Finds a user by their email address.
     *
     * This method returns an Optional to handle cases where a user may not exist in the database.
     * Using Optional is essential for supporting multiple login providers, like native and Facebook logins.
     * When a user logs in with Facebook for the first time, their email might not be in the database,
     * and Optional helps handle this scenario.
     *
     * @param email the email address of the user
     * @return an Optional containing the user if found, or Optional.empty() if not found
     */
    public Optional<User> findByEmail(String email) {
        String sql = "SELECT * FROM user WHERE email = ?";
        return jdbcTemplate.query(sql, new Object[]{email}, rs -> {
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setProvider(rs.getString("provider"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPasswordHash(rs.getString("password_hash"));
                user.setPicture(rs.getString("picture") != null ? rs.getString("picture") : "");
                log.info("Found user by email: {}", user);
                return Optional.of(user);
            } else {
                log.info("No user found with email: {}", email);
                return Optional.empty();
            }
        });
    }
}

