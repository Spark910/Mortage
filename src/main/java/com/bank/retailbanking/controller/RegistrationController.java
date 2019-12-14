package com.bank.retailbanking.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.retailbanking.constants.ApplicationConstants;
import com.bank.retailbanking.dto.CustomerRequestDto;
import com.bank.retailbanking.dto.CustomerResponseDto;
import com.bank.retailbanking.dto.RegistrationRequestDto;
import com.bank.retailbanking.dto.RegistrationResponseDto;
import com.bank.retailbanking.exception.AgeException;
import com.bank.retailbanking.exception.GeneralException;
import com.bank.retailbanking.exception.InvalidRegistrationException;
import com.bank.retailbanking.service.CustomerAccountService;
import com.bank.retailbanking.service.RegistrationService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Bindushree
 * 
 * @Description This class is used for to do the registerCustomer operations
 */

@CrossOrigin(allowedHeaders = { "*", "*/" }, origins = { "*", "*/" })
@RestController
@RequestMapping("/customers")
@Slf4j
public class RegistrationController {

	@Autowired
	RegistrationService registrationService;
	@Autowired
	CustomerAccountService customerAccountService;

	/**
	 * 
	 * @Description This method is used to register a particular customer.
	 * 
	 * @author Bindushree
	 * @param registrationRequestDto
	 * @return
	 * @throws PhoneNumberInvalidException
	 * @throws AgeException
	 * @throws NameException
	 * @throws InvalidRegistrationException
	 */

	@PostMapping("/register")
	public ResponseEntity<RegistrationResponseDto> registerCustomer(
			@RequestBody RegistrationRequestDto registrationRequestDto)
			throws GeneralException, AgeException, InvalidRegistrationException {
		log.info("Entering into registerCustomer method of registrationService ");
		RegistrationResponseDto registrationResponseDto = registrationService.registerCustomer(registrationRequestDto);
		registrationResponseDto.setMessage(ApplicationConstants.SUCCESS);
		registrationResponseDto.setStatusCode(ApplicationConstants.SUCESS_STATUS_CODE);
		return new ResponseEntity<>(registrationResponseDto, HttpStatus.OK);
	}

	@PostMapping("/mortgage")
	public ResponseEntity<Optional<CustomerResponseDto>> createMortgageCustomer(
			@RequestBody CustomerRequestDto customerRequestDto)
			throws GeneralException, AgeException, InvalidRegistrationException {
		log.info("Entering into createMortgageCustomer method of account creation ");
		Optional<CustomerResponseDto> customerResponseDto = customerAccountService
				.createCustomerAccount(customerRequestDto);
		if (customerResponseDto.isPresent()) {
			customerResponseDto.get().setStatusCode(ApplicationConstants.SUCESS_STATUS_CODE);
			customerResponseDto.get().setMessage(ApplicationConstants.MORTGAGE_SUCCESS_STATUS_MESSAGE);
			return new ResponseEntity<>(customerResponseDto, HttpStatus.OK);
		}
		CustomerResponseDto customerResponse = new CustomerResponseDto();
		customerResponse.setStatusCode(ApplicationConstants.MORTGAGE_FAILURE_STATUS_CODE);
		customerResponse.setMessage(ApplicationConstants.MORTGAGE_FAILURE_STATUS_MESSAGE);
		return new ResponseEntity<>(Optional.of(customerResponse), HttpStatus.NOT_FOUND);
	}

}
