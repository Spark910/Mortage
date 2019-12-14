package com.bank.retailbanking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler {
	@ExceptionHandler(value = CustomerNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleException(CustomerNotFoundException exception) {
		ErrorResponse errorResponse = new ErrorResponse(exception.getMessage());
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = AmountInvalidException.class)
	public ResponseEntity<ErrorResponse> handleException(AmountInvalidException exception) {
		ErrorResponse errorResponse = new ErrorResponse(exception.getMessage());
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = SameAccountNumberException.class)
	public ResponseEntity<ErrorResponse> handleException(SameAccountNumberException exception) {
		ErrorResponse errorResponse = new ErrorResponse(exception.getMessage());
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = AgeException.class)
	public ResponseEntity<ErrorResponse> handleException(AgeException exception) {
		ErrorResponse errorResponse = new ErrorResponse(exception.getMessage());
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_ACCEPTABLE);
	}

	@ExceptionHandler(value = InvalidRegistrationException.class)
	public ResponseEntity<ErrorResponse> handleException(InvalidRegistrationException exception) {
		ErrorResponse errorResponse = new ErrorResponse(exception.getMessage());
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_ACCEPTABLE);
	}
	
	@ExceptionHandler(value = GeneralException.class)
	public ResponseEntity<ErrorResponse> userException(Exception exception){
		ErrorResponse errorResponse = new ErrorResponse(exception.getMessage());
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_ACCEPTABLE);	
	}
	
	@ExceptionHandler(value = NoAccountListException.class)
	public ResponseEntity<ErrorResponse> handleException(NoAccountListException exception) {
		ErrorResponse errorResponse = new ErrorResponse(exception.getMessage());
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(value = MortgageException.class)
	public ResponseEntity<ErrorResponse> handleException(MortgageException exception) {
		ErrorResponse errorResponse = new ErrorResponse(exception.getMessage());
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}


}
