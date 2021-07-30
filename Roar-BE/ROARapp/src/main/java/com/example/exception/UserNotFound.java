package com.example.exception;

public class UserNotFound extends RuntimeException {

	  public UserNotFound(Long id) {
	    super("Could not find employee " + id);
	  }
	}