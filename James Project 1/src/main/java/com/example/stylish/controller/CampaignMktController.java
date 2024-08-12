package com.example.stylish.controller;

import com.example.stylish.model.Campaign;
import com.example.stylish.service.CampaignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/1.0/marketing")
public class CampaignMktController {

    @Autowired
    private CampaignService campaignService;

    @GetMapping("/campaigns")
    public ResponseEntity<?> getAllCampaigns() {
        List<Campaign> campaigns = campaignService.getAllCampaigns();
        Map<String, Object> response = new HashMap<>();
        response.put("data", campaigns);
        return ResponseEntity.ok(response);
    }
}
