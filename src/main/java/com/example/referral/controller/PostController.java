package com.example.referral.controller;

import com.example.referral.model.Company;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class PostController {

    @RequestMapping(value = "/putStuff/{id}", method = {RequestMethod.GET, RequestMethod.POST}, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String putStuff(@RequestBody Company company) {
        System.out.println(company);
        return "Himanshu";
    }
}
