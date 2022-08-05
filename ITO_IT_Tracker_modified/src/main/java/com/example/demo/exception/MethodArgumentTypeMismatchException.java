package com.example.demo.exception;

public class MethodArgumentTypeMismatchException extends RuntimeException {
	
	String message ;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	
	public MethodArgumentTypeMismatchException(String message) {
		super(message);
	}
}
