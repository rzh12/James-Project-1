package com.example.stylish.controller;

import com.example.stylish.model.Product;
import com.example.stylish.service.ProductCreateService;
import com.example.stylish.dto.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

@RestController
@RequestMapping("/admin")
public class ProductCreateController {

    @Autowired
    private ProductCreateService productCreateService;

    @PostMapping("/product")
    public ResponseEntity<ApiResponse> createProduct(
            @RequestParam("productData") String productDataString,
            @RequestParam("main_image") MultipartFile mainImage,
            @RequestParam("images") MultipartFile[] images) throws IOException {


        // Use ObjectMapper to parse JSON strings
        ObjectMapper objectMapper = new ObjectMapper();
        Product product = objectMapper.readValue(productDataString, Product.class);

        // Pass the data to the service layer for processing
        productCreateService.createProduct(product, mainImage, images);

        // Assume the data is stored successfully
        ApiResponse response = new ApiResponse(true, "Product added successfully!");
        return ResponseEntity.ok(response);
    }
}
