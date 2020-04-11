package com.jobseekerorganizer.accountms.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MvcExceptionHandler   {

//	
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<List> validationErrorHandler(ConstraintViolationException e){
		List<String> errors = new ArrayList<>(e.getConstraintViolations().size());
		
		e.getConstraintViolations().forEach(error -> {
			errors.add(error.toString());
		});
		
		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(BindException.class)
	public ResponseEntity<List> validationErrorHandler(BindException e){
		return new ResponseEntity<List>(e.getAllErrors(), HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
	public ResponseEntity<Throwable> MediaTyperErrorHandler(HttpMediaTypeNotAcceptableException e){
		return new ResponseEntity<>(e.fillInStackTrace(), HttpStatus.BAD_REQUEST);
	} 
	
}
