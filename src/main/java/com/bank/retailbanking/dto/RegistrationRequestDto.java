package com.bank.retailbanking.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class RegistrationRequestDto {

	private String firstName;
	private String lastName;
	private String gender;
	private String email;
	private LocalDate dateOfBirth;
	private Long mobileNumber;
	private Double amount; 

}
