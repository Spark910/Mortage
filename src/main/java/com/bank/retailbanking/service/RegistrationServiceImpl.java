package com.bank.retailbanking.service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.retailbanking.constants.ApplicationConstants;
import com.bank.retailbanking.dto.RegistrationRequestDto;
import com.bank.retailbanking.dto.RegistrationResponseDto;
import com.bank.retailbanking.entity.Customer;
import com.bank.retailbanking.entity.CustomerAccountDetail;
import com.bank.retailbanking.exception.AgeException;
import com.bank.retailbanking.exception.GeneralException;
import com.bank.retailbanking.exception.InvalidRegistrationException;
import com.bank.retailbanking.repository.CustomerAccountDetailRepository;
import com.bank.retailbanking.repository.CustomerRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Bindushree
 *
 * @Description This class is used for to do the registerCustomer operations
 */

@Service
@Slf4j
public class RegistrationServiceImpl implements RegistrationService {

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	CustomerAccountDetailRepository customerAccountDetailsRepository;

	Random random = new Random();

	/**
	 * @Description This method is used for storing the account and customer
	 *              details.
	 * @param registrationRequestDto
	 * @return RegistrationResponseDto
	 * @exception AgeException,InvalidRegistrationException,GeneralException
	 */
	@Override
	public RegistrationResponseDto registerCustomer(RegistrationRequestDto registrationRequestDto)
			throws AgeException, InvalidRegistrationException, GeneralException {

		log.info("Checking whether any of the accounts have same email id");
		Optional<Customer> customerExists = customerRepository.findByCustomerEmailAndMobile(
				registrationRequestDto.getCustomerEmail(), registrationRequestDto.getMobile());
		log.info("No account with same mailId is found");
		if (!(customerExists.isPresent())) {

			Long number = registrationRequestDto.getMobile();
			int length = String.valueOf(number).length();

			log.info("Checking whether the customer age is above 18 years");
			if (LocalDate.now().getYear()
					- registrationRequestDto.getDateOfBirth().getYear() >= ApplicationConstants.AGE_LIMIT) {

				log.info("Checking whether mobile number is valid");
				if (length == 10) {

					log.info("Checking whether the entered amount is greater than minimum balance");
					if (registrationRequestDto.getAmount() >= ApplicationConstants.OPENING_BALANCE) {

						Customer customer = new Customer();
						
						BeanUtils.copyProperties(registrationRequestDto, customer);
						Customer customerData = customerRepository.save(customer);

						CustomerAccountDetail customerAccountDetails = new CustomerAccountDetail();
						customerAccountDetails.setAccountOpeningDate(LocalDate.now());
						customerAccountDetails.setAccountType(ApplicationConstants.SAVING_ACCOUNT);
						customerAccountDetails.setAvailableBalance(registrationRequestDto.getAmount());
						customerAccountDetails.setCustomerId(customerData);
						customerAccountDetails.setPassword(customerData.getFirstName().substring(0)+random.nextInt(9999));
						customerAccountDetailsRepository.save(customerAccountDetails);

						RegistrationResponseDto registrationResponseDto = new RegistrationResponseDto();
						BeanUtils.copyProperties(customer, registrationResponseDto);
						return registrationResponseDto;

					}
					log.error(ApplicationConstants.INVALID_OPENING_BALANCE);
					throw new GeneralException(ApplicationConstants.INVALID_OPENING_BALANCE);

				}
				log.error(ApplicationConstants.INVALID_MOBILE);
				throw new GeneralException(ApplicationConstants.INVALID_MOBILE);

			}
			log.error(ApplicationConstants.NOT_ELIGIBLE_FOR_ACCOUNT);
			throw new AgeException(ApplicationConstants.NOT_ELIGIBLE_FOR_ACCOUNT);

		}
		log.error(ApplicationConstants.INVALID_REGISTRATION_FOR_ACCOUNT);
		throw new InvalidRegistrationException(ApplicationConstants.INVALID_REGISTRATION_FOR_ACCOUNT);

	}

}