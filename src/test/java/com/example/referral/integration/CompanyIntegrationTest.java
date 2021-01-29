package com.example.referral.integration;

import com.example.referral.model.Company;
import com.example.referral.service.CompanyRepository;
import org.h2.tools.Server;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.SQLException;

import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@DataJpaTest
public class CompanyIntegrationTest {

    @Autowired
    private CompanyRepository companyRepository;

    @BeforeAll
    public void initTest() throws SQLException {
        Server.createWebServer("-web", "-webAllowOthers", "-webPort", "8082")
                .start();
    }

    @Test
    public void testCompany() {
        Company company = companyRepository.save(new Company("PayPal"));
        Company companyFound = companyRepository.getOne(company.getName());
        assertEquals("the objects must be same", company, companyFound);
    }

    @Test
    public void tesDuplicateCompany() {
        String companyName = "PayPal";
        Company company = companyRepository.save(new Company(companyName));
        Company companyFound = companyRepository.findById(companyName).get();
        assertEquals("the company must be same", companyFound, company);
        Company duplicateCompany = companyRepository.save(new Company(companyName));

//        assertEquals("The count must be 2", 2, companyRepository.count());

        Company duplicateCompanyFound = companyRepository.findById(companyName).get();
        assertEquals(duplicateCompany, duplicateCompanyFound);
        assertEquals(company, duplicateCompany);
    }


}
