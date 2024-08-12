package com.example.stylish.repository;

import com.example.stylish.model.Product;
import com.example.stylish.model.Product.Color;
import com.example.stylish.model.Product.Variant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.stream.Collectors;

@Repository
public class ProductListRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Value("${base.url}")
    private String baseUrl;

    // Find products by category with pagination
    public List<Product> findProductsByCategory(String category, int offset, int limit) {
        String sql = category.equals("all") ?
                     "SELECT * FROM product LIMIT ? OFFSET ?" :
                     "SELECT * FROM product WHERE category = ? LIMIT ? OFFSET ?";

        List<Product> products = category.equals("all") ?
               jdbcTemplate.query(sql, new Object[]{limit, offset}, new BeanPropertyRowMapper<>(Product.class)) :
               jdbcTemplate.query(sql, new Object[]{category, limit, offset}, new BeanPropertyRowMapper<>(Product.class));

        // Fetch colors, sizes, and variants for each product and set them
        for (Product product : products) {
            Set<Color> colors = findColorsByProductId(product.getId());
            product.setColors(List.copyOf(colors));

            List<String> sizes = findSizesByProductId(product.getId());
            product.setSizes(sizes);

            List<Variant> variants = findVariantsByProductId(product.getId());
            product.setVariants(variants);

            List<String> images = findImagesByProductId(product.getId());
            product.setImages(images.stream().map(url -> baseUrl + url).collect(Collectors.toList()));

            // Ensure main_image is a complete URL
            product.setMainImage(baseUrl + product.getMainImage());
        }

        return products;
    }

    // Find colors associated with a product
    private Set<Color> findColorsByProductId(Integer productId) {
        String sql = "SELECT DISTINCT c.code, c.name FROM color c JOIN product_color pc ON c.code = pc.color_code WHERE pc.product_id = ?";
        return new HashSet<>(jdbcTemplate.query(sql, new Object[]{productId}, (rs, rowNum) ->
            new Color(rs.getString("code"), rs.getString("name"))
        ));
    }

    // Find sizes associated with a product
    private List<String> findSizesByProductId(Integer productId) {
        String sql = "SELECT s.size FROM size s JOIN product_size ps ON s.size = ps.size WHERE ps.product_id = ?";
        return jdbcTemplate.query(sql, new Object[]{productId}, (rs, rowNum) ->
            rs.getString("size")
        );
    }

    // Find variants associated with a product
    private List<Variant> findVariantsByProductId(Integer productId) {
        String sql = "SELECT v.color_code, v.size, v.stock FROM variant v WHERE v.product_id = ?";
        return jdbcTemplate.query(sql, new Object[]{productId}, (rs, rowNum) ->
            new Variant(rs.getString("color_code"), rs.getString("size"), rs.getInt("stock"))
        );
    }

    // Find images associated with a product
    private List<String> findImagesByProductId(Integer productId) {
        String sql = "SELECT i.url FROM image i JOIN product_image pi ON i.id = pi.image_id WHERE pi.product_id = ?";
        return jdbcTemplate.query(sql, new Object[]{productId}, (rs, rowNum) ->
            rs.getString("url")
        );
    }
}
