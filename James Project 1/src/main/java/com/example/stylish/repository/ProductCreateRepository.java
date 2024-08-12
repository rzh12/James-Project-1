package com.example.stylish.repository;

import com.example.stylish.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Repository
public class ProductCreateRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void saveProduct(Product product) {
        String sql = "INSERT INTO product (id, category, title, description, price, texture, wash, place, note, story, main_image) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, product.getId(), product.getCategory(), product.getTitle(), product.getDescription(),
                            product.getPrice(), product.getTexture(), product.getWash(), product.getPlace(),
                            product.getNote(), product.getStory(), product.getMainImage());
    }

    public String saveImage(MultipartFile image) throws IOException {
        if (image.isEmpty()) {
            return null;
        }


        // Determine the directory for saving images
        String uploadDir = "uploads/";
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Generate a unique filename with UUID
        String originalFilename = image.getOriginalFilename();
        String extension = "";
        int dotIndex = originalFilename.lastIndexOf('.');
        if (dotIndex > 0) {
            extension = originalFilename.substring(dotIndex);
        }
        String uniqueFilename = UUID.randomUUID().toString() + extension;
        String imagePath = uploadDir + uniqueFilename;
        Path filePath = uploadPath.resolve(uniqueFilename);

        // Store the image in the designated path
        Files.copy(image.getInputStream(), filePath);

        return imagePath;
    }

    // Save an image URL in the database and return the generated image ID
    public int saveImageAndReturnId(String imageUrl) {
        String sql = "INSERT INTO image (url) VALUES (?)";
        jdbcTemplate.update(sql, imageUrl);
        return jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
    }

    // Save the relationship between a product and an image in the DB
    public void saveProductImage(Integer productId, int imageId) {
        String sql = "INSERT INTO product_image (product_id, image_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, productId, imageId);
    }

    // Check if the color exists, and if not, insert it into the database
    public void saveColorIfNotExist(String colorCode, String colorName) {
        String sqlCheck = "SELECT COUNT(*) FROM color WHERE code = ?";
        Integer count = jdbcTemplate.queryForObject(sqlCheck, new Object[]{colorCode}, Integer.class);

        if (count == null || count == 0) {
            String sqlInsert = "INSERT INTO color (code, name) VALUES (?, ?)";
            jdbcTemplate.update(sqlInsert, colorCode, colorName);
        }
    }

    // Check if the size exists, and if not, insert it into the database
    public void saveSizeIfNotExist(String size) {
        String sqlCheck = "SELECT COUNT(*) FROM size WHERE size = ?";
        Integer count = jdbcTemplate.queryForObject(sqlCheck, new Object[]{size}, Integer.class);

        if (count == null || count == 0) {
            String sqlInsert = "INSERT INTO size (size) VALUES (?)";
            jdbcTemplate.update(sqlInsert, size);
        }
    }

    // Insert into product_color table
    public void saveProductColor(Integer productId, String colorCode, String colorName) {
        saveColorIfNotExist(colorCode, colorName);
        String sql = "INSERT INTO product_color (product_id, color_code) VALUES (?, ?)";
        jdbcTemplate.update(sql, productId, colorCode);
    }

    // Insert into product_size table
    public void saveProductSize(Integer productId, String size) {
        saveSizeIfNotExist(size);
        String sql = "INSERT INTO product_size (product_id, size) VALUES (?, ?)";
        jdbcTemplate.update(sql, productId, size);
    }

    // Insert into variant table
    public void saveProductVariant(Integer productId, String colorCode, String size, Integer stock) {
    String sql = "INSERT INTO variant (product_id, color_code, size, stock) VALUES (?, ?, ?, ?)";
    jdbcTemplate.update(sql, productId, colorCode, size, stock);
    }
}
