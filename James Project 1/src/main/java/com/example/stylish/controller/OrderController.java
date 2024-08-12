package com.example.stylish.controller;

import com.example.stylish.dto.ErrorResponse;
import com.example.stylish.exception.NoTokenException;
import com.example.stylish.exception.WrongTokenException;
import com.example.stylish.model.Order;
import com.example.stylish.service.OrderService;
import com.example.stylish.dto.TapPayResponse;
import com.example.stylish.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/1.0/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/checkout")
    public ResponseEntity<?> checkoutOrder(@RequestBody Order order, HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            try {
                Claims claims = jwtUtil.extractClaims(token);
                Integer userId = claims.get("id", Integer.class);
                order.setUserId(userId);
            } catch (JwtException e) {
                throw new WrongTokenException("Invalid token");
            }
        } else {
            throw new NoTokenException("User ID not found in request");
        }

        // Create an unpaid order record
        int orderId = orderService.saveInitialOrder(order);

        try {
            orderService.processOrder(order, orderId);
            TapPayResponse.DataContainer dataContainer = new TapPayResponse.DataContainer(String.valueOf(orderId));
            TapPayResponse Response = new TapPayResponse(dataContainer);
            return ResponseEntity.ok(Response);
        } catch (Exception e) {
            orderService.updateOrderStatus(orderId, -1);
            return ResponseEntity.status(500).body(new ErrorResponse("Error", e.getMessage()));
        }
    }
}
