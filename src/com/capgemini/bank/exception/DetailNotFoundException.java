package com.capgemini.bank.exception;

public class DetailNotFoundException extends Exception{

	public DetailNotFoundException() {
		super();
	}

	public DetailNotFoundException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public DetailNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public DetailNotFoundException(String message) {
		super(message);
	}

	public DetailNotFoundException(Throwable cause) {
		super(cause);
	}

}
