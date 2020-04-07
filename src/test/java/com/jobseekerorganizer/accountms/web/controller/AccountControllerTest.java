package com.jobseekerorganizer.accountms.web.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobseekerorganizer.accountms.web.model.UserAccountDTO;

@WebMvcTest(AccountController.class)
class AccountControllerTest {
	
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Test
	void testCreate() throws Exception {
		UserAccountDTO userDTO = UserAccountDTO.builder().build();
		String userAccDTOJson = objectMapper.writeValueAsString(userDTO);
				
		mockMvc.perform(post("/account")
				.contentType((MediaType.APPLICATION_JSON))
				.content(userAccDTOJson))
				.andExpect(status().isCreated());
	}

	@Test
	void testGetByEmail() throws Exception {
		UserAccountDTO userDTO = UserAccountDTO.builder().build();
		String userAccDTOJson = objectMapper.writeValueAsString(userDTO);
				
		mockMvc.perform(post("/account/byEmail")
				.contentType((MediaType.APPLICATION_JSON))
				.content(userAccDTOJson))
				.andExpect(status().isOk());
	}

	@Test
	void testUpdate()throws Exception {
		UserAccountDTO userDTO = UserAccountDTO.builder().build();
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
	}

}
