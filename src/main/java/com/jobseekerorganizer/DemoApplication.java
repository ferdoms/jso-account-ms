package com.jobseekerorganizer;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.jobseekerorganizer.accountms.repositories.UserAccountRepository;
import com.jobseekerorganizer.config.DynamoDBConfig;


@EnableDynamoDBRepositories(mappingContextRef = "dynamoDBMappingContext", basePackageClasses = UserAccountRepository.class)
@Configuration
@Import({DynamoDBConfig.class})
@ComponentScan
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@EnableJpaRepositories(excludeFilters = {
		@ComponentScan.Filter(type = FilterType.ANNOTATION, value = EnableScan.class) })
public class DemoApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(DemoApplication.class);
	}

}
