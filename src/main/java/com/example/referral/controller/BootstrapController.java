package com.example.referral.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
public class BootstrapController {

    @GetMapping("/")
    public String testEndpointForSpirngSecurity() {
        return "Hello! Welcome to website";
    }

    @GetMapping("/test")
    public String testEndpoint() {
      return "test api working";
    }

    @GetMapping("/getAllCompanies")
    public List<String> getAllCompanies() {
        return Collections.emptyList();
    }
}
