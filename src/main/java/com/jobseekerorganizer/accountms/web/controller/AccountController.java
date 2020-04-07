package com.jobseekerorganizer.accountms.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.jobseekerorganizer.accountms.services.UserAccountService;
import com.jobseekerorganizer.accountms.web.model.UserAccount;
import com.jobseekerorganizer.accountms.web.model.UserAccountDTO;

@RestController
@RequestMapping("/account")
public class AccountController {
	@Autowired
	private AmazonDynamoDB amazonDynamoDB;

	@Autowired
	private UserAccountService service;

	@PostMapping(produces = "application/json", consumes = "application/json")
	public ResponseEntity create(@RequestBody UserAccountDTO newUserAcc) {
		UserAccountDTO savedDTO = service.create(newUserAcc);

		HttpHeaders headers = new HttpHeaders();
		// TODO add hostname to url
		headers.add("Location", "/account" + savedDTO.toString());

		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

	@PostMapping(path = "/byEmail", produces = "application/json", consumes = "application/json")
	public ResponseEntity<UserAccountDTO> getByEmail(@RequestBody UserAccountDTO user) {

		return new ResponseEntity<>(service.getByEmail(user.getEmail()), HttpStatus.OK);
	}

	@PutMapping(produces = "application/json", consumes = "application/json")
	public ResponseEntity update(@RequestBody UserAccountDTO user) {
		service.update(user);
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}

	// Testing purposes
	@GetMapping(produces = "application/json")
	public ResponseEntity<Iterable<UserAccount>> get() {
		return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
	}

	@DeleteMapping({"/{userId"})
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteAccount(@PathVariable String userId) {
		service.delete(userId);
	}
}
