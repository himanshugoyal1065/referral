package com.example.referral.service;

import com.example.referral.model.Company;
import com.example.referral.repository.CompanyRepository;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

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
}
