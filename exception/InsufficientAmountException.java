package com.java.exception;

public class InsufficientAmountException extends Exception {

	private static final long serialVersionUID = 1L;

	public InsufficientAmountException(String message) {
		super(message);

	}

	public InsufficientAmountException(String message, Throwable t) {
		super(message, t);

	}
}
