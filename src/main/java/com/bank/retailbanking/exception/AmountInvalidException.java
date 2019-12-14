package com.bank.retailbanking.exception;

public class AmountInvalidException extends Exception {
	private static final long serialVersionUID = 1L;

	public AmountInvalidException(String exception) {
		super(exception);
	}
}