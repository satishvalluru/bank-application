package com.java.exception;

public class FromAccountNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public FromAccountNotFoundException(String message) {
		super(message);

	}

	public FromAccountNotFoundException(String message, Throwable t) {
		super(message, t);

	}
}
