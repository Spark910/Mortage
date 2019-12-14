package com.bank.retailbanking.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ViewAccountResponseDto {
	
	private Integer statusCode;
	private String message;
	private Long accountNumber;
	private String accountType;
	private Long customerId;
	private String customerName;
	private Integer age;
	private String gender;


}
