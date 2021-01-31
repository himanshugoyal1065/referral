package com.example.referral.controller;

import com.example.referral.model.Company;
import com.example.referral.model.Referral;
import com.example.referral.model.User;
import com.example.referral.repository.CompanyRepository;
import com.example.referral.repository.ReferralRepository;
import com.example.referral.repository.UserRepository;
import com.example.referral.service.UserService;
import com.example.referral.utils.Constants;
import com.example.referral.utils.ResponseMessage;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
public class BootstrapController {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private ReferralRepository referralRepository;

    @Autowired
    private UserService userService;

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

    @PostMapping("/doReferral")
    public ResponseEntity referralRequest(@RequestBody @NotNull List<Referral> referrals, @NotNull OAuth2AuthenticationToken token) {

        final String userEmail = token.getPrincipal().getAttribute(Constants.EMAIL_SMALL);


        if (userEmail != null && !userEmail.isEmpty() && !userService.userExists(userEmail)) {
            return new ResponseEntity("The user must be authenticated", HttpStatus.FORBIDDEN);
        }

        final User user = userService.getUserByEmail(userEmail);

        for (Referral referral : referrals) {
            //validate the referral if required ?
            //save the referral
            referral.setRequester(user);
            referralRepository.save(referral);
        }

        //transaction


        return ResponseEntity.notFound().build();
    }

    @GetMapping("/me")
    public ResponseEntity<User> getUser(@NotNull final OAuth2AuthenticationToken token) {
        final Map<String, Object> userAttributes = token.getPrincipal().getAttributes();
        final User user = new User();
        user.setEmail((String) userAttributes.get(Constants.EMAIL_SMALL));
        user.setName((String) userAttributes.get(Constants.NAME_SMALL));
        userService.saveUser(user);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @PostMapping("/putResume")
    public ResponseEntity<Boolean> putResumeForUser(
            @RequestParam(required = true) @NotNull final MultipartFile resume,
            @NotNull final OAuth2AuthenticationToken token) {
        final String email = token.getPrincipal().getAttribute(Constants.EMAIL_SMALL);
        try {
            userService.uploadResume(email, resume);
            return ResponseEntity.status(HttpStatus.OK).body(true);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(false);
        }
    }


}
