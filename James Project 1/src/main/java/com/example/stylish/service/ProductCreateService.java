package com.example.stylish.service;

import com.example.stylish.model.Product;
import com.example.stylish.repository.ProductCreateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductCreateService {

    @Autowired
    private ProductCreateRepository productCreateRepository;

    public void createProduct(Product product, MultipartFile mainImage, MultipartFile[] images) throws IOException {
        // Save mainImage
        String mainImagePath = productCreateRepository.saveImage(mainImage);

        // Save other images
        List<String> imagePaths = new ArrayList<>();
        for (MultipartFile image : images) {
            String imagePath = productCreateRepository.saveImage(image);
            if (imagePath != null) {
                imagePaths.add(imagePath);
            }
        }

        // Create a product object
        Product productCreate = new Product(product.getId(), product.getCategory(), product.getTitle(),
                                      product.getDescription(), product.getPrice(), product.getTexture(),
                                      product.getWash(), product.getPlace(), product.getNote(),
                                      product.getStory(), product.getColors(), product.getSizes(), product.getVariants(), mainImagePath, imagePaths);

        // Store the product data in the database
        productCreateRepository.saveProduct(productCreate);

        // Store the color data in the database
        for (Product.Color color : productCreate.getColors()) {
            productCreateRepository.saveProductColor(productCreate.getId(), color.getCode(), color.getName());
        }

        // Store the size data in the database
        for (String size : productCreate.getSizes()) {
            productCreateRepository.saveProductSize(productCreate.getId(), size);
        }

        // Store the variant data in the database
        for (Product.Variant variant : productCreate.getVariants()) {
            productCreateRepository.saveProductVariant(productCreate.getId(), variant.getColorCode(), variant.getSize(), variant.getStock());
        }

        // Store the image data in the database
        for (String imagePath : productCreate.getImages()) {
            int imageId = productCreateRepository.saveImageAndReturnId(imagePath);
            productCreateRepository.saveProductImage(productCreate.getId(), imageId);
        }
    }
}
