package com.example.stylish.repository;

import com.example.stylish.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ProductSearchRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Value("${base.url}")
    private String baseUrl;

    // Find products by title with pagination
    public List<Product> findProductsByTitle(String keyword, int offset, int limit) {
        String sql = "SELECT * FROM product WHERE title LIKE ? LIMIT ? OFFSET ?";
        String searchKeyword = "%" + keyword + "%";
        List<Product> products = jdbcTemplate.query(sql, new Object[]{searchKeyword, limit, offset}, new BeanPropertyRowMapper<>(Product.class));

        for (Product product : products) {
            product.setColors(findColorsByProductId(product.getId()));
            product.setSizes(findSizesByProductId(product.getId()));
            product.setVariants(findVariantsByProductId(product.getId()));
            product.setImages(findImagesByProductId(product.getId()).stream().map(url -> baseUrl + url).collect(Collectors.toList()));
            product.setMainImage(baseUrl + product.getMainImage());
        }

        return products;
    }

    // Find product by ID
    public Product findProductById(Integer id) {
        String sql = "SELECT * FROM product WHERE id = ?";
        List<Product> products = jdbcTemplate.query(sql, new Object[]{id}, new BeanPropertyRowMapper<>(Product.class));

        if (products.isEmpty()) {
            return null;
        }

        Product product = products.get(0);
        product.setColors(findColorsByProductId(product.getId()));
        product.setSizes(findSizesByProductId(product.getId()));
        product.setVariants(findVariantsByProductId(product.getId()));
        product.setImages(findImagesByProductId(product.getId()).stream().map(url -> baseUrl + url).collect(Collectors.toList()));
        product.setMainImage(baseUrl + product.getMainImage());

        return product;
    }

    // Find colors associated with a product by ID
    private List<Product.Color> findColorsByProductId(Integer productId) {
        String sql = "SELECT DISTINCT c.code, c.name FROM color c JOIN product_color pc ON c.code = pc.color_code WHERE pc.product_id = ?";
        return jdbcTemplate.query(sql, new Object[]{productId}, (rs, rowNum) ->
                new Product.Color(rs.getString("code"), rs.getString("name"))
        );
    }

    // Find sizes associated with a product by ID
    private List<String> findSizesByProductId(Integer productId) {
        String sql = "SELECT s.size FROM size s JOIN product_size ps ON s.size = ps.size WHERE ps.product_id = ?";
        return jdbcTemplate.query(sql, new Object[]{productId}, (rs, rowNum) -> rs.getString("size"));
    }

    // Find variants associated with a product by ID
    private List<Product.Variant> findVariantsByProductId(Integer productId) {
        String sql = "SELECT v.color_code, v.size, v.stock FROM variant v WHERE v.product_id = ?";
        return jdbcTemplate.query(sql, new Object[]{productId}, (rs, rowNum) ->
                new Product.Variant(rs.getString("color_code"), rs.getString("size"), rs.getInt("stock"))
        );
    }

    // Find images associated with a product by ID
    private List<String> findImagesByProductId(Integer productId) {
        String sql = "SELECT i.url FROM image i JOIN product_image pi ON i.id = pi.image_id WHERE pi.product_id = ?";
        return jdbcTemplate.query(sql, new Object[]{productId}, (rs, rowNum) -> rs.getString("url"));
    }
}
