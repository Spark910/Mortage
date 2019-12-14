package com.bank.retailbanking.exception;

public class SameAccountNumberException extends Exception {
	private static final long serialVersionUID = 1L;

	public SameAccountNumberException(String exception) {
		super(exception);
	}
}