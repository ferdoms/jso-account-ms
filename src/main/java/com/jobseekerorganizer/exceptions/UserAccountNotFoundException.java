package com.jobseekerorganizer.exceptions;

public class UserAccountNotFoundException extends IllegalStateException {
	public UserAccountNotFoundException(String message) {
		super(message);
	}
}
