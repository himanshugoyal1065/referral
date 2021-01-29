package com.example.referral.controller;

import com.example.referral.model.Company;
import com.example.referral.service.CompanyRepository;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
public class BootstrapController {

    @Autowired
    private CompanyRepository companyRepository;

    @GetMapping("/")
    public String testEndpointForSpirngSecurity(OAuth2AuthenticationToken token) {
        System.out.println("the token is " + String.valueOf(token));
        return "Hello! Welcome to website";
    }

    @GetMapping("/test")
    public String testEndpoint(OAuth2AuthenticationToken token) {
        System.out.println("the token is " + String.valueOf(token));
        return "test api working";
    }

    @GetMapping("/getAllCompanies")
    public List<String> getAllCompanies() {
        return Collections.emptyList();
    }

    /**
     * adding a new company
     * @param company the name of the company
     * @return the created company uri
     */
    @PostMapping("/putCompany")
    public ResponseEntity<Company> putCompany(@RequestBody @NotNull Company company) {
        Company createdCompany = companyRepository.save(company);
        if (createdCompany == null) {
            return ResponseEntity.notFound().build();
        } else {
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(createdCompany.getName())
                    .toUri();

            return ResponseEntity.created(uri).body(createdCompany);
        }
    }
}
