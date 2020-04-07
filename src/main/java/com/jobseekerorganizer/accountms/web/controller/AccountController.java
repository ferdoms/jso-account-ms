package com.jobseekerorganizer.accountms.web.controller;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.jobseekerorganizer.accountms.domain.UserAccount;
import com.jobseekerorganizer.accountms.services.UserAccountService;
import com.jobseekerorganizer.accountms.web.model.UserAccountDTO;

@RestController 
@RequestMapping("/account")
public class AccountController {

	@Autowired
	private UserAccountService service;

	@PostMapping(produces = "application/json", consumes = "application/json")
	public ResponseEntity create(@Valid @RequestBody @Validated UserAccountDTO newUserAcc) {
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

	@PutMapping(path="/{userId}", produces = "application/json", consumes = "application/json")
	public ResponseEntity update(@PathVariable String userId, @Valid @RequestBody @Validated UserAccountDTO user) {
		service.update(userId, user);
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}

	// Testing purposes
	@GetMapping(produces = "application/json")
	public ResponseEntity<Iterable<UserAccount>> get() {
		return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
	}

	@DeleteMapping("/{userId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteAccount(@PathVariable String userId) {
		service.delete(userId);
	}
}
