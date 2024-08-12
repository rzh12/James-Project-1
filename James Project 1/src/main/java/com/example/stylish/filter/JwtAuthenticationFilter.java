package com.example.stylish.filter;

import com.example.stylish.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;


    /**
     * This method filters every request to validate JWT tokens.
     * It gets the Authorization header, extracts and validates the token,
     * and sets the authentication in the security context.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String path = request.getRequestURI();
        // Restrict filtering to authentication-required paths
        if (path.startsWith("/user/profile")) {

            // Get the Authorization header from the request
            String authorizationHeader = request.getHeader("Authorization");

            // No token: 401
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("{\"error\": \"No token provided\"}");
                return;
            }

            // Extract the JWT token from the Authorization header
            String token = authorizationHeader.substring(7);

            try {
                // Parse and validate the JWT token
                Claims claims = jwtUtil.extractClaims(token);
                String username = claims.getSubject();

                // If the token is valid and the user is not already authenticated
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    // Create an authentication token and set the authentication in the security context
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            username, null, null);
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.getWriter().write("{\"error\": \"Invalid token\"}");
                return;
            }
        }

        // Continue with the next filter in the chain
        chain.doFilter(request, response);
    }
}
