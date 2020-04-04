package com.jobseekerorganizer;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.jobseekerorganizer.datamodels.UserAccount;
import com.jobseekerorganizer.repositories.UserAccountRepository;

@Configuration
@ComponentScan
@SpringBootApplication
@EnableJpaRepositories(excludeFilters = {
		@ComponentScan.Filter(type = FilterType.ANNOTATION, value = EnableScan.class) })
public class DemoApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
//		System.out.println("something");
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(DemoApplication.class);
	}

	private static Class<DemoApplication> applicationClass = DemoApplication.class;

}

@RestController
class GreetingController {
	@Autowired
    UserAccountRepository repository;
 
	@Autowired
	private AmazonDynamoDB amazonDynamoDB;

	@RequestMapping("/hello/{name}")
	String hello(@PathVariable String name) {
//		DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);
//
//		CreateTableRequest tableRequest = dynamoDBMapper.generateCreateTableRequest(UserAccount.class);
//		tableRequest.setProvisionedThroughput(new ProvisionedThroughput(1L, 1L));
//		amazonDynamoDB.createTable(tableRequest);
		
		UserAccount user = new UserAccount();
		user.setName("fernando");
		repository.save(user);
		
		System.out.println(repository.count());

		return "Hello, nego ney others some" + name + "!";
	}
}
