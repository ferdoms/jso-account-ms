package com.jobseekerorganizer;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;

import com.jobseekerorganizer.accountms.config.DynamoDBConfig;
import com.jobseekerorganizer.accountms.jwt.TokenAuthenticationService;
import com.jobseekerorganizer.accountms.repositories.UserAccountRepository;



@Configuration
@Import({DynamoDBConfig.class})
@ComponentScan
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class, RedisAutoConfiguration.class })
public class AccountMsApplication extends SpringBootServletInitializer {
	
	@Value("${ENC_KEY}")
    private String encKey;

    @Bean
    public TokenAuthenticationService tokenAuthService() {
        return new TokenAuthenticationService(encKey);
    }

	public static void main(String[] args) {
		SpringApplication.run(AccountMsApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(AccountMsApplication.class);
	}

}
