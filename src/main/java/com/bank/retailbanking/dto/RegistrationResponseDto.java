package com.bank.retailbanking.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RegistrationResponseDto {

	private Long customerId;
	private String password;
	private String message;
	private Integer statusCode;

}
