package com.example.stylish.controller;

import com.example.stylish.model.Product;
import com.example.stylish.service.ProductListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/1.0/products")
public class ProductListController {

    @Autowired
    private ProductListService productListService;

    @GetMapping("/{category}")
    public ResponseEntity<?> getProducts(
            @PathVariable String category,
            @RequestParam(required = false, defaultValue = "0") int paging,
            @RequestParam(required = false, defaultValue = "6") int size) {

        // Validate paging and size parameters
        if (paging < 0 || size <= 0) {
            throw new IllegalArgumentException("Invalid paging or size parameter");
        }

        // Retrieve products from the service
        List<Product> products = productListService.getProducts(category, paging, size);

        // Check if there are more products beyond the current page
        boolean hasMoreProducts = productListService.hasMoreProducts(category, paging, size);

        // Create response map
        Map<String, Object> response = new HashMap<>();
        response.put("data", products);
        if (hasMoreProducts) {
            response.put("next_paging", paging + 1);
        }
        return ResponseEntity.ok(response);
    }

    // Handle IllegalArgumentException and return a 400 Bad Request response.
    @ExceptionHandler(IllegalArgumentException.class)
    public final ResponseEntity<Map<String, String>> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        Map<String, String> errorDetails = new HashMap<>();
        errorDetails.put("error", ex.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
}
