package com.example.referral;

import com.example.referral.utils.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class ReferralApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReferralApplication.class, args);
	}

}
