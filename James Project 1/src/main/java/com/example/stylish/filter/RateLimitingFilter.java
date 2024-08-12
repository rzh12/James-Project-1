package com.example.stylish.filter;

import com.example.stylish.service.RateLimiterService;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Log4j2
@Component
@Order(1) // Ensure this filter is executed early in the filter chain
public class RateLimitingFilter implements Filter {

    @Autowired
    private RateLimiterService rateLimiterService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String ip = httpRequest.getRemoteAddr(); // Get the IP address
        String requestUri = httpRequest.getRequestURI(); // Get the URI

        log.info("Rate limiting IP: {} for URI: {}", ip, requestUri);

        // Combine IP and URI to determine if this request should be rate-limited
        String ipUriCombo = ip + requestUri;

        // Apply rate limiting based on the combination of IP address and URI
        if (!rateLimiterService.isAllowed(ipUriCombo)) {
            log.warn("IP {} exceeded rate limit for URI: {}", ip, requestUri);

            // Set the response status to 429 Too Many Requests
            httpResponse.setStatus(429);
            httpResponse.setContentType("application/json");
            httpResponse.setCharacterEncoding("UTF-8");

            // Construct the JSON response
            String jsonResponse = "{\"error\": \"Too many requests\", \"message\": \"Please try again later.\"}";

            // Write the JSON response to the output
            httpResponse.getWriter().write(jsonResponse);
            return;
        }

        // Proceed with the request if not rate-limited
        chain.doFilter(request, response);
    }
}
