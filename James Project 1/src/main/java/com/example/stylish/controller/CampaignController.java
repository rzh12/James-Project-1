package com.example.stylish.controller;

import com.example.stylish.dto.ApiResponse;
import com.example.stylish.service.CampaignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/admin")
public class CampaignController {

    @Autowired
    private CampaignService campaignService;

    @PostMapping("/campaign")
    public ResponseEntity<ApiResponse> createCampaign(
            @RequestParam("productId") Integer productId,
            @RequestParam("story") String story,
            @RequestParam("picture") MultipartFile picture) throws IOException {


        // Pass the data to the service layer for processing
        campaignService.createCampaign(productId, story, picture);

        // Assume the data is stored successfully
        ApiResponse response = new ApiResponse(true, "Campaign created successfully!");
        return ResponseEntity.ok(response);
    }
}

