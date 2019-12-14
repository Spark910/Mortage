package com.bank.retailbanking.Service;

import java.util.Optional;

import org.junit.Assert;
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
import com.bank.retailbanking.exception.GeneralException;
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

	LoginResponsedto loginResponsedto;
	LoginRequestdto loginRequestdto;
	Customer customer;

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

	public Customer getCustomer() {
		customer = new Customer();
		customer.setCustomerId(1001L);
		customer.setCustomerId(10001L);
		return customer;
	}

	@Before
	public void setup() {
		loginResponsedto = getLoginResponse();
		loginRequestdto = getLoginRequest();
	}

	@Test
	public void testLogin() throws GeneralException {
		log.info("Entering into loginTest of LoginServiceImplementationTest");
		loginResponsedto = getLoginResponse();
		loginRequestdto = getLoginRequest();
		customer = getCustomer();
		Mockito.when(loginRepository.findByCustomerIdAndPassword(Mockito.any(), Mockito.anyString()))
				.thenReturn(Optional.of(customer));
		Optional<LoginResponsedto> response = loginServiceImplementation.login(loginRequestdto);
		Assert.assertNotNull(response);
	}

	@Test(expected = GeneralException.class)
	public void testLoginNegative() throws Exception {
		log.info("Entering into testLoginNegative of LoginServiceImplementationTest");
		Mockito.when(loginRepository.findByCustomerIdAndPassword(Mockito.any(), Mockito.anyString()))
				.thenReturn(Optional.ofNullable(null));
		loginServiceImplementation.login(loginRequestdto);
	}

}
