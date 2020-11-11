package com.java.exception;

public class ToAccountNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public ToAccountNotFoundException(String message) {
		super(message);

	}

	public ToAccountNotFoundException(String message, Throwable t) {
		super(message, t);

	}
}
