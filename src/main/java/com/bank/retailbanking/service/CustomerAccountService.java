package com.bank.retailbanking.service;

import java.util.Optional;

import com.bank.retailbanking.dto.CustomerRequestDto;
import com.bank.retailbanking.dto.CustomerResponseDto;
import com.bank.retailbanking.exception.GeneralException;

public interface CustomerAccountService {
	public Optional<CustomerResponseDto> createCustomerAccount(CustomerRequestDto customerRequestDto) throws GeneralException;
}
