package com.bank.retailbanking.controller;

import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import com.bank.retailbanking.controller.RegistrationController;
import com.bank.retailbanking.dto.RegistrationRequestDto;
import com.bank.retailbanking.dto.RegistrationResponseDto;
import com.bank.retailbanking.exception.AgeException;
import com.bank.retailbanking.exception.GeneralException;
import com.bank.retailbanking.exception.InvalidRegistrationException;
import com.bank.retailbanking.service.RegistrationService;

@RunWith(MockitoJUnitRunner.class)
public class RegistrationControllerTest {

	@InjectMocks
	RegistrationController registrationController;

	@Mock
	RegistrationService registrationService;

	RegistrationRequestDto registrationRequestDto = null;
	RegistrationResponseDto registrationResponseDto = null;

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
	}

	@Test
	public void testRegisterCustomer() throws GeneralException, AgeException, InvalidRegistrationException {
		Mockito.when(registrationService.registerCustomer(registrationRequestDto)).thenReturn(registrationResponseDto);
		ResponseEntity<RegistrationResponseDto> response = registrationController
				.registerCustomer(registrationRequestDto);
		Assert.assertNotNull(response);
	}

}
