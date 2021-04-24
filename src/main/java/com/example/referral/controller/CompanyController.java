package com.example.referral.controller;

import com.example.referral.model.Company;
import com.example.referral.model.User;
import com.example.referral.service.CompanyService;
import com.example.referral.service.UserService;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * All company related endpoints are given here
 */
@RestController
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private UserService userService;


    @GetMapping("/getAllCompanies")
    public List<String> getAllCompanies() {
        return companyService.getAllCompanyName();
    }

    /**
     * adding a new company
     * @param company the name of the company
     * @return the created company uri
     */
    @PostMapping("/putCompany")
    public ResponseEntity<Company> putCompany(@RequestBody @NotNull Company company, @NotNull OAuth2AuthenticationToken token) {
        final User user = userService.saveNewUser(token);

        //setting the user you have added the company
        company.setUserToList(user);

        final Company createdCompany = companyService.saveCompany(company);

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

    @PostMapping("/putCompanyForUser")
    public ResponseEntity<User> putCompanyForUser(
            @RequestBody @NotNull final String companyName, @NotNull final OAuth2AuthenticationToken token) {

        final User user = userService.saveNewUser(token);
        final Company company = companyService.getCompanyByName(companyName);

        if (company == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }

        final User updateUser = userService.putCompanyForUser(company, user);

        return ResponseEntity.status(HttpStatus.OK).body(updateUser);
    }

    @GetMapping("/getCompanyExistsForUser")
    public ResponseEntity<Boolean> getCompanyExistsForUser(@NotNull final OAuth2AuthenticationToken token) {
        final User user = userService.saveNewUser(token);
        boolean isExists = userService.getCompanyExistsForUser(user);
        return ResponseEntity.status(HttpStatus.OK).body(isExists);
    }

    @GetMapping("/isCompanyEmailValid")
    public ResponseEntity<Boolean> isCompanyEmailValid(@NotNull @RequestBody String companyName, @NotNull @RequestBody String email) {

        //call the api provided

        return ResponseEntity.status(HttpStatus.OK).body(false);
    }
}
