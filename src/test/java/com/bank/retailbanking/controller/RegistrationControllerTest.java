package com.bank.retailbanking.controller;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import com.bank.retailbanking.dto.CustomerRequestDto;
import com.bank.retailbanking.dto.CustomerResponseDto;
import com.bank.retailbanking.dto.RegistrationRequestDto;
import com.bank.retailbanking.dto.RegistrationResponseDto;
import com.bank.retailbanking.exception.AgeException;
import com.bank.retailbanking.exception.GeneralException;
import com.bank.retailbanking.exception.InvalidRegistrationException;
import com.bank.retailbanking.service.CustomerAccountService;
import com.bank.retailbanking.service.RegistrationService;

@RunWith(MockitoJUnitRunner.class)
public class RegistrationControllerTest {

	@InjectMocks
	RegistrationController registrationController;

	@Mock
	RegistrationService registrationService;
	
	@Mock
	CustomerAccountService customerAccountService; 

	RegistrationRequestDto registrationRequestDto = null;
	RegistrationResponseDto registrationResponseDto = null;
	CustomerRequestDto customerRequestDto=null;
	CustomerResponseDto customerResponseDto=null;

	@Before
	public void before() {
		registrationRequestDto = new RegistrationRequestDto();
		registrationRequestDto.setCustomerEmail("bindu@gmail.com");
		registrationRequestDto.setDateOfBirth(LocalDate.of(1997, 10, 2));
		registrationRequestDto.setFirstName("Bind");
		registrationRequestDto.setLastName("shree");
		registrationRequestDto.setGender("female");
		registrationRequestDto.setMobile(2653871738L);

		registrationResponseDto = new RegistrationResponseDto();
		registrationResponseDto.setCustomerId(1878L);
		registrationResponseDto.setMessage("success");
		registrationResponseDto.setPassword("BS4668");
		registrationResponseDto.setStatusCode(200);
		
		
		customerRequestDto=new CustomerRequestDto();
		customerRequestDto.setAccountType("mortgage");
		customerRequestDto.setCustomerId(1001L);
		
		customerResponseDto=new CustomerResponseDto();
		customerResponseDto.setMessage("success");
		customerResponseDto.setStatusCode(201);
	}

	@Test
	public void testRegisterCustomer() throws GeneralException, AgeException, InvalidRegistrationException {
		Mockito.when(registrationService.registerCustomer(registrationRequestDto)).thenReturn(registrationResponseDto);
		ResponseEntity<RegistrationResponseDto> response = registrationController
				.registerCustomer(registrationRequestDto);
		Assert.assertNotNull(response);
	}
	@Test
	public void createMortgageCustomer() throws GeneralException{
		Mockito.when(customerAccountService.createCustomerAccount(customerRequestDto)).thenReturn(Optional.of(customerResponseDto));
		ResponseEntity<Optional<CustomerResponseDto>> response = registrationController.createMortgageCustomer(customerRequestDto);
		Assert.assertNotNull(response);
	}
}
