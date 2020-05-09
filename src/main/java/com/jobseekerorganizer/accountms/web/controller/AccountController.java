package com.jobseekerorganizer.accountms.web.controller;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.ExceptionHandler;

import com.jobseekerorganizer.accountms.domain.AuthenticationTokenImpl;
import com.jobseekerorganizer.accountms.domain.UserAccount;
import com.jobseekerorganizer.accountms.services.UserAccountService;
import com.jobseekerorganizer.accountms.web.model.PasswordDto;
import com.jobseekerorganizer.accountms.web.model.UserAccountDto;
import com.jobseekerorganizer.accountms.web.model.UserAccountProfileDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/account")
public class AccountController {

	@Autowired
	private UserAccountService service;

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity create(@Valid @RequestBody @Validated UserAccountDto newUserAcc) {
		UserAccountDto savedDTO = service.create(newUserAcc);

		HttpHeaders headers = new HttpHeaders();
		// TODO add hostname to url
		headers.add("Location", "/account" + savedDTO.toString());

		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserAccountProfileDto> getByToken(AuthenticationTokenImpl auth) {
		return new ResponseEntity<>(service.getProfileById(auth.getPrincipal().toString()), HttpStatus.OK);
	}

	@PutMapping(path = "/{userId}", produces = "application/json", consumes = "application/json")
	public ResponseEntity update(@PathVariable String userId,
			@Valid @RequestBody @Validated UserAccountProfileDto user) {
		service.updateProfile(userId, user);
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}

	@PutMapping(path = "/passwordReset", consumes = "application/json")
	public ResponseEntity resetPassword(AuthenticationTokenImpl auth, @Valid @RequestBody @Validated PasswordDto pass) {
		service.updatePassword(auth.getPrincipal().toString(), pass);
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}


	@DeleteMapping("/{userId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteAccount(@PathVariable String userId) {
		service.delete(userId);
	}
}
