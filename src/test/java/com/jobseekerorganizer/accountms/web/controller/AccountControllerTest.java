package com.jobseekerorganizer.accountms.web.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobseekerorganizer.DemoApplication;
import com.jobseekerorganizer.accountms.domain.UserAccount;
import com.jobseekerorganizer.accountms.services.UserAccountService;
import com.jobseekerorganizer.accountms.web.model.UserAccountDto;
import com.jobseekerorganizer.config.DynamoDBConfig;

@ExtendWith(SpringExtension.class)
//@SpringBootTest(classes = DemoApplication.class)
@WebAppConfiguration
@ActiveProfiles("local")
@TestPropertySource(properties = { 
  "amazon.dynamodb.endpoint=http://localhost:8000/", 
  "amazon.aws.accesskey=test1", 
  "amazon.aws.secretkey=test231" })
@WebMvcTest(AccountController.class)
class AccountControllerTest {
	
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ObjectMapper objectMapper;

    private DynamoDBMapper dynamoDBMapper;
 
    @Autowired
    private AmazonDynamoDB amazonDynamoDB;

    @BeforeEach
    public void setup() throws Exception {
        dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);
         
        CreateTableRequest tableRequest = dynamoDBMapper
          .generateCreateTableRequest(UserAccount.class);
        tableRequest.setProvisionedThroughput(
          new ProvisionedThroughput(1L, 1L));
        amazonDynamoDB.createTable(tableRequest);
    }
		

	@Test
	void testCreate() throws Exception {
		UserAccountDto userDTO = UserAccountDto.builder().build();
		String userAccDTOJson = objectMapper.writeValueAsString(userDTO);
				
		mockMvc.perform(post("/account")
				.contentType((MediaType.APPLICATION_JSON))
				.content(userAccDTOJson))
				.andExpect(status().isCreated());
	}

	@Test
	void testGetByEmail() throws Exception {
		UserAccountDto userDTO = UserAccountDto.builder().build();
		String userAccDTOJson = objectMapper.writeValueAsString(userDTO);
				
		mockMvc.perform(post("/account/byEmail")
				.contentType((MediaType.APPLICATION_JSON))
				.content(userAccDTOJson))
				.andExpect(status().isOk());
	}

	@Test
	void testUpdate()throws Exception {
		UserAccountDto userDTO = UserAccountDto.builder().build();
		String userAccDTOJson = objectMapper.writeValueAsString(userDTO);
				
		mockMvc.perform(put("/account")
				.contentType((MediaType.APPLICATION_JSON))
				.content(userAccDTOJson))
				.andExpect(status().isNoContent());
	}

	@Test
	void testDeleteAccount()throws Exception {
		String id = "someid";		
		mockMvc.perform(delete("/account/" + id)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());
		System.out.println("Tested");
	}

}
