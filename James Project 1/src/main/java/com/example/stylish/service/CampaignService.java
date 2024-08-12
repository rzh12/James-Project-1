package com.example.stylish.service;

import com.example.stylish.model.Campaign;
import com.example.stylish.repository.CampaignRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class CampaignService {

    private static final String CACHE_KEY = "campaign_data";

    @Autowired
    private CampaignRepository campaignRepository;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void createCampaign(Integer productId, String story, MultipartFile picture) throws IOException {
        // Save picture
        String picturePath = campaignRepository.saveImage(picture);

        // Create a campaign object
        Campaign campaign = new Campaign(productId, picturePath, story);

        // Store the campaign data in the database
        campaignRepository.saveCampaign(campaign);

        // Clear the cache after updating the database
        redisTemplate.delete(CACHE_KEY);
    }

    public List<Campaign> getAllCampaigns() {
        try {
            // Fetch the list of all Campaign IDs
            List<Integer> campaignIds = campaignRepository.getAllCampaigns()
                    .stream()
                    .map(Campaign::getProductId)
                    .collect(Collectors.toList());

            // Check and retrieve all Campaign data from Redis
            List<Campaign> campaigns = campaignIds.stream()
                    .map(id -> (Campaign) redisTemplate.opsForHash().get(CACHE_KEY, id.toString()))
                    .collect(Collectors.toList());

            // If any Campaign is not in the cache, fetch it from the database and store it in the cache
            if (campaigns.contains(null)) {
                campaigns = campaignRepository.getAllCampaigns();
                campaigns.forEach(campaign -> redisTemplate.opsForHash().put(CACHE_KEY, campaign.getProductId().toString(), campaign));
                redisTemplate.expire(CACHE_KEY, 10, TimeUnit.MINUTES);
            }

            return campaigns;

        } catch (Exception e) {
            // Handle exceptions from Redis or database operations
            e.printStackTrace();
            // Fallback to fetching data from the database if Redis operation fails
            return campaignRepository.getAllCampaigns();
        }
    }
}
