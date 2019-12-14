package com.bank.retailbanking.exception;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
public class ErrorResponse {
	String message;
	Integer statusCode;

	public ErrorResponse() {

	}

	public ErrorResponse(String message, Integer statusCode) {
		super();
		this.message = message;
		this.statusCode = statusCode;
	}

	public ErrorResponse(String message) {
		super();
		this.message = message;
	}
}
