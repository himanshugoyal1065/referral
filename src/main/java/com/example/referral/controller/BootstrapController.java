package com.example.referral.controller;

import com.example.referral.exceptions.ResourceNotFoundException;
import com.example.referral.model.Referral;
import com.example.referral.model.User;
import com.example.referral.repository.ReferralRepository;
import com.example.referral.security.CurrentUser;
import com.example.referral.security.UserPrincipal;
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

import java.util.List;

@RestController
public class BootstrapController {

    @Autowired
    private ReferralRepository referralRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String testEndpointForSpirngSecurity(OAuth2AuthenticationToken token) {
        System.out.println("the token is " + token);
        return "Hello! Welcome to website";
    }

    @GetMapping("/api/test")
    public String testEndpointCheck(OAuth2AuthenticationToken token) {
        System.out.println("the token is " + String.valueOf(token));
        return "test api working";
    }

    @GetMapping("/test")
    public String testEndpoint(OAuth2AuthenticationToken token) {
        System.out.println("the token is " + String.valueOf(token));
        return "test api working";
    }



    @GetMapping("/resumeExists")
    public ResponseEntity<Boolean> getResumeExists(@NotNull OAuth2AuthenticationToken token) {

        userService.saveNewUser(token);

        boolean resumeExists =
                userService.doesResumeExistsForUserByEmail(token.getPrincipal().getAttribute(Constants.EMAIL_SMALL));

        if (!resumeExists) {
            return ResponseEntity.status(HttpStatus.OK).body(false);
        }

        return ResponseEntity.status(HttpStatus.OK).body(true);
    }



    @PostMapping("/doReferral")
    public ResponseEntity referralRequest(@RequestBody @NotNull List<Referral> referrals, @NotNull OAuth2AuthenticationToken token) {

        final User user = userService.saveNewUser(token);

        for (Referral referral : referrals) {
            //validate the referral if required ?
            //save the referral
            referral.setRequester(user);
            referralRepository.save(referral);
        }

        //transaction


        return ResponseEntity.notFound().build();
    }

    @GetMapping("/user/me")
    public User getUser(@CurrentUser final UserPrincipal userPrincipal) {
//        final User user = userService.saveNewUser(token); //duplicate users are handled as the email is the primary key.
//        return new ResponseEntity<User>(user, HttpStatus.OK);

        return userService.getUserByEmail(userPrincipal.getEmail());
//                .orElseThrow(() -> new ResourceNotFoundException("User", "Email", userPrincipal.getEmail()));
    }

    @PostMapping("/putResume")
    public ResponseEntity<String> putResumeForUser(
            @RequestParam(required = true) @NotNull final MultipartFile resume,
            @NotNull final OAuth2AuthenticationToken token) {

        userService.saveNewUser(token);

        final String email = token.getPrincipal().getAttribute(Constants.EMAIL_SMALL);
        try {
            final ResponseMessage responseMessage = userService.uploadResume(email, resume);
            if (responseMessage.isSuccess())
                return ResponseEntity.status(HttpStatus.OK).body("true");
            else
                return ResponseEntity.status(HttpStatus.OK).body("The resume with name " + resume.getOriginalFilename() + " already exists");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("false");
        }
    }



}
