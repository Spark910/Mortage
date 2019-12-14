package com.bank.retailbanking.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionException extends Exception {

	private static final long serialVersionUID = 1L;

	public TransactionException(String s) {
		super(s);
	}
}
