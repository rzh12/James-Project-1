package com.example.stylish.service;

import com.example.stylish.model.Product;
import com.example.stylish.repository.ProductSearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductSearchService {

    @Autowired
    private ProductSearchRepository productSearchRepository;

    // Search products by title with pagination
    public List<Product> searchProductsByTitle(String keyword, int paging, int size) {
        int offset = paging * size;
        return productSearchRepository.findProductsByTitle(keyword, offset, size);
    }

    // Get product details by ID
    public Product getProductById(Integer id) {
        return productSearchRepository.findProductById(id);
    }

    // Check if next paging is needed
    public boolean hasMoreProducts(String keyword, int paging, int size) {
        int offset = (paging + 1) * size;
        List<Product> nextProducts = productSearchRepository.findProductsByTitle(keyword, offset, 1);
        return !nextProducts.isEmpty();
    }
}
