package com.example.stylish.repository;

import com.example.stylish.model.Campaign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.List;

@Repository
public class CampaignRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Value("${base.url}")
    private String baseUrl;

    public void saveCampaign(Campaign campaign) {
        String sql = "INSERT INTO campaign (product_id, picture, story) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, campaign.getProductId(), campaign.getPicture(), campaign.getStory());
    }

    public String saveImage(MultipartFile image) throws IOException {
        if (image.isEmpty()) {
            return null;
        }

        String uploadDir = "uploads/";
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String originalFilename = image.getOriginalFilename();
        String extension = "";
        int dotIndex = originalFilename.lastIndexOf('.');
        if (dotIndex > 0) {
            extension = originalFilename.substring(dotIndex);
        }
        String uniqueFilename = UUID.randomUUID().toString() + extension;
        String imagePath = uploadDir + uniqueFilename;
        Path filePath = uploadPath.resolve(uniqueFilename);

        Files.copy(image.getInputStream(), filePath);

        return imagePath;
    }

    // Ensure Picture is a complete URL
    public List<Campaign> getAllCampaigns() {
        String sql = "SELECT c.product_id, c.picture, c.story FROM campaign c";

        List<Campaign> campaigns = jdbcTemplate.query(sql, (rs, rowNum) -> {
        Campaign campaign = new Campaign();
        campaign.setProductId(rs.getInt("product_id"));
        campaign.setPicture(baseUrl + rs.getString("picture"));
        campaign.setStory(rs.getString("story"));
        return campaign;
    });
        return campaigns;
    }
}
