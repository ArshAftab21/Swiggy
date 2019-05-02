package com.swiggy.authentication.exception;

@SuppressWarnings("serial")
public class UserNotFoundException extends Exception {
	public UserNotFoundException(String message) {
		super(message);
	}

}
