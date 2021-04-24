package com.example.referral.service;

import com.example.referral.model.Company;
import com.example.referral.model.User;
import com.example.referral.repository.CompanyRepository;
import com.example.referral.utils.Constants;
import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private UserService userService;

    @NotNull
    public List<String> getAllCompanyName() {
        final List<Company> companies = companyRepository.findAll();
        List<String> companyNames = new ArrayList<>();
        companies.forEach(company -> companyNames.add(company.getName()));
        return companyNames;
    }

    @NotNull
    public Company saveCompany(@NotNull Company company) {
        return companyRepository.save(company);
    }

//    @NotNull
//    public Company saveCompanyFromUser(@NotNull final String companyName, @NotNull final OAuth2AuthenticationToken token) {
//        final User user = userService.getUserByToken(token);
//    }

    @Nullable
    public Company getCompanyByName(@NotNull String companyName) {
        boolean companyExists = companyRepository.existsById(companyName);
        if (companyExists) {
            return companyRepository.getOne(companyName);
        }
        return null;
    }

    @NotNull
    public List<User> getUsersByCompanyName(@NotNull String companyName) {
        final Company company = getCompanyByName(companyName);

        return company.getUsers();
    }

//    @NotNull
//    public User putCompanyForUser(@NotNull final Company company, @NotNull final User user) {
//
//    }
}
