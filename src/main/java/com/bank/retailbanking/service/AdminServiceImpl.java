package com.bank.retailbanking.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.retailbanking.dto.ViewAccountResponseDto;
import com.bank.retailbanking.entity.Customer;
import com.bank.retailbanking.entity.CustomerAccountDetail;
import com.bank.retailbanking.repository.CustomerAccountDetailsRepository;
import com.bank.retailbanking.repository.CustomerRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	CustomerAccountDetailsRepository customerAccountDetailsRepository;

	@Autowired
	CustomerRepository customerRepository;

	@Override
	public ViewAccountResponseDto viewAccountDetails(Long accountNumber) {
		log.info("Entering into viewAccountDetails method of service");
		Optional<CustomerAccountDetail> customerAccountDetail = customerAccountDetailsRepository
				.findByAccountNumber(accountNumber);
		ViewAccountResponseDto viewAccountResponseDto = new ViewAccountResponseDto();
		if (customerAccountDetail.isPresent()) {
			Customer customer = customerAccountDetail.get().getCustomerId();
			Optional<Customer> customerDetails = customerRepository.findByCustomerId(customer.getCustomerId());
			if (customerDetails.isPresent()) {
				viewAccountResponseDto.setAccountNumber(accountNumber);
				viewAccountResponseDto.setAccountType(customerAccountDetail.get().getAccountType());
				viewAccountResponseDto.setCustomerId(customerDetails.get().getCustomerId());
				viewAccountResponseDto.setCustomerName(
						customerDetails.get().getFirstName() + " " + customerDetails.get().getLastName());
				viewAccountResponseDto
						.setAge(LocalDate.now().getYear() - customerDetails.get().getDateOfBirth().getYear());
				viewAccountResponseDto.setGender(customerDetails.get().getGender());
			}
		}
		return viewAccountResponseDto;
	}

}
