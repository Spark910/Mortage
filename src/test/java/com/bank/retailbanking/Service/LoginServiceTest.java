package com.bank.retailbanking.Service;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;

import com.bank.retailbanking.constants.ApplicationConstants;
import com.bank.retailbanking.dto.LoginRequestdto;
import com.bank.retailbanking.dto.LoginResponsedto;
import com.bank.retailbanking.entity.Customer;
import com.bank.retailbanking.entity.CustomerAccountDetail;
import com.bank.retailbanking.exception.GeneralException;
import com.bank.retailbanking.repository.CustomerAccountDetailsRepository;
import com.bank.retailbanking.repository.CustomerRepository;
import com.bank.retailbanking.service.LoginServiceImplementation;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author Chethana M
 * @Description This class is used for to do test operation for login service
 *
 */
@RunWith(MockitoJUnitRunner.Silent.class)
@Slf4j
public class LoginServiceTest {
	@InjectMocks
	LoginServiceImplementation loginServiceImplementation;

	@Mock
	CustomerRepository loginRepository;
	
	@Mock
	CustomerAccountDetailsRepository customerAccountDetailsRepository;

	LoginResponsedto loginResponsedto;
	LoginRequestdto loginRequestdto;
	

	
	CustomerAccountDetail customerAccountDetail= new CustomerAccountDetail();
	Customer customer= new Customer();
	

	public LoginResponsedto getLoginResponse() {
		loginResponsedto = new LoginResponsedto();
		loginResponsedto.setMessage(ApplicationConstants.SUCCESS);
		loginResponsedto.setStatusCode(HttpStatus.OK.value());
		return loginResponsedto;
	}

	public LoginRequestdto getLoginRequest() {
		loginRequestdto = new LoginRequestdto();
		loginRequestdto.setCustomerId(1001L);
		loginRequestdto.setPassword("c");
		return loginRequestdto;
	}

	
	@Before
	public void setup() {
		loginResponsedto = getLoginResponse();
		loginRequestdto = getLoginRequest();
	}

	@Test(expected = GeneralException.class)
	public void testLoginNegative() throws Exception {
		log.info("Entering into testLoginNegative of LoginServiceImplementationTest");
		customer.setCustomerId(1L);
		Mockito.when(loginRepository.findByCustomerId(1L)).thenReturn(Optional.of(customer));
		loginServiceImplementation.login(loginRequestdto);
	}
	
}
