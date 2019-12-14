package com.bank.retailbanking.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.retailbanking.constants.ApplicationConstants;
import com.bank.retailbanking.dto.LoginRequestdto;
import com.bank.retailbanking.dto.LoginResponsedto;
import com.bank.retailbanking.entity.Customer;
import com.bank.retailbanking.entity.CustomerAccountDetail;
import com.bank.retailbanking.exception.GeneralException;
import com.bank.retailbanking.repository.CustomerAccountDetailsRepository;
import com.bank.retailbanking.repository.CustomerRepository;

/**
 * 
 * @author Chethana M
 * @Description This class is used to provide Login Functionality implementation
 *
 */

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author Chethana M
 * @Description This class is used to provide Login Functionality implementation
 *
 */
@Service
@Slf4j
public class LoginServiceImplementation implements LoginService {

	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	CustomerAccountDetailsRepository customerAccountDetailsRepository;

	/**
	 * @Description This method is used for user to login with valid credentials
	 * @param loginRequestdto
	 * @return LoginResponsedto
	 * @exception LOGIN_ERROR
	 */

	public Optional<LoginResponsedto> login(LoginRequestdto loginRequestdto) throws GeneralException {
		log.info("Entering into LoginServiceImplementation-------login()");
		Optional<Customer> customerResponse=customerRepository.findByCustomerId(loginRequestdto.getCustomerId());
		if(!customerResponse.isPresent()) {
			throw new  GeneralException("Invalid Credentials");
		}
		Optional<CustomerAccountDetail> loginResponse = customerAccountDetailsRepository.findByCustomerIdAndPasswordAndAccountType(customerResponse.get(),loginRequestdto.getPassword(),loginRequestdto.getAccountType());
		if (!loginResponse.isPresent()) {
			log.error(ApplicationConstants.LOGIN_ERROR);
			throw new GeneralException(ApplicationConstants.LOGIN_ERROR);
		}
		LoginResponsedto response = new LoginResponsedto();
		response.setCustomerId(customerResponse.get().getCustomerId());
		return Optional.of(response);
	}

}
