package com.example.stylish.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
@Log4j2
@Service
public class TapPayService {

    @Value("${tappay.partner_key}")
    private String partnerKey;

    @Value("${tappay.merchant_id}")
    private String merchantId;

    private final RestTemplate restTemplate = new RestTemplate();

    private static final Logger logger = Logger.getLogger(TapPayService.class.getName());

    public Map<String, Object> verifyPrime(String prime, double amount, String details, Map<String, Object> cardholder) {
        String url = "https://sandbox.tappaysdk.com/tpc/payment/pay-by-prime";

        // Convert amount to integer cents
        int amountInCents = (int) (amount * 100);

        // Create request body
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("prime", prime);
        requestBody.put("partner_key", partnerKey);
        requestBody.put("merchant_id", merchantId);
        requestBody.put("amount", amountInCents);
        requestBody.put("details", details);
        requestBody.put("cardholder", cardholder);

        log.info("Request Body: " + requestBody);

        // Create headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-api-key", partnerKey);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            throw new RuntimeException("TapPay verification failed");
        }
    }
}
