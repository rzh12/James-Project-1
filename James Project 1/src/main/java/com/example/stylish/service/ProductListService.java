package com.example.stylish.service;

import com.example.stylish.model.Product;
import com.example.stylish.repository.ProductListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductListService {

    @Autowired
    private ProductListRepository productListRepository;

    // Retrieve a list of products by category with pagination
    public List<Product> getProducts(String category, int paging, int size) {
        // Calculate the offset based on the paging and size parameters
        int offset = paging * size;
        return productListRepository.findProductsByCategory(category, offset, size);
    }

    // Check if there are more products beyond the current page
    public boolean hasMoreProducts(String category, int paging, int size) {
        int offset = (paging + 1) * size;
        List<Product> nextProducts = productListRepository.findProductsByCategory(category, offset, 1);
        return !nextProducts.isEmpty();
    }
}
