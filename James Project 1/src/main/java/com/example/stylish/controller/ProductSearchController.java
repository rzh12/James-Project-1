package com.example.stylish.controller;

import com.example.stylish.model.Product;
import com.example.stylish.service.ProductSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/1.0/products")
public class ProductSearchController {

    @Autowired
    private ProductSearchService productSearchService;

    // Endpoint for Product Search API
    @GetMapping("/search")
    public ResponseEntity<?> searchProducts(
            @RequestParam String keyword,
            @RequestParam(required = false, defaultValue = "0") String paging) {

        try {
            int page = Integer.parseInt(paging); // Convert paging parameter to integer
            List<Product> products = productSearchService.searchProductsByTitle(keyword, page, 6);
            boolean hasMoreProducts = productSearchService.hasMoreProducts(keyword, page, 6);

            Map<String, Object> response = new HashMap<>();
            response.put("data", products);
            if (hasMoreProducts) {
                response.put("next_paging", page + 1); // Add next paging if there are more products
            }
            return ResponseEntity.ok(response); // Return the response with product data
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }

    // Endpoint for Product Details API
    @GetMapping("/details")
    public ResponseEntity<?> getProductDetails(@RequestParam Integer id) {
        try {
            Product product = productSearchService.getProductById(id); // Get product by ID
            if (product == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Product not found")); // Handle product not found
            }
            return ResponseEntity.ok(Map.of("data", product)); // Return the response with product details
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage())); // Handle invalid ID parameter
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "An unexpected error occurred"));
        }
    }

    // Handle IllegalArgumentException and return a 400 Bad Request response.
    @ExceptionHandler(IllegalArgumentException.class)
    public final ResponseEntity<Map<String, String>> handleIllegalArgumentException(IllegalArgumentException ex) {
        Map<String, String> errorDetails = new HashMap<>();
        errorDetails.put("error", ex.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
}
