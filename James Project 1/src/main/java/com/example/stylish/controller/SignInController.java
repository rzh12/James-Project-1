package com.example.stylish.controller;

import com.example.stylish.service.SignInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/1.0/user")
public class SignInController {

    @Autowired
    private SignInService signInService;

    @PostMapping("/signin")
    public ResponseEntity<Map<String, Object>> signIn(@RequestBody Map<String, String> credentials) {
        return signInService.signIn(credentials);
    }
}
