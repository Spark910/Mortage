package com.bank.retailbanking.controller;

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
import org.springframework.http.ResponseEntity;

import com.bank.retailbanking.constants.ApplicationConstants;
import com.bank.retailbanking.controller.LoginController;
import com.bank.retailbanking.dto.LoginRequestdto;
import com.bank.retailbanking.dto.LoginResponsedto;
import com.bank.retailbanking.exception.GeneralException;
import com.bank.retailbanking.service.LoginServiceImplementation;

@RunWith(MockitoJUnitRunner.Silent.class)
public class LoginControllerTest {

	@Mock
	LoginServiceImplementation loginServiceImplementation;

	@InjectMocks
	LoginController loginController;

	LoginRequestdto loginRequestdto;
	LoginResponsedto loginResponsedto;

	public LoginResponsedto getLoginResponsedto() {
		loginResponsedto = new LoginResponsedto();
		loginResponsedto.setMessage(ApplicationConstants.SUCCESS);
		loginResponsedto.setStatusCode(HttpStatus.OK.value());
		return loginResponsedto;
	}

	public LoginRequestdto getLoginRequestdto() {
		loginRequestdto = new LoginRequestdto();
		loginRequestdto.setCustomerId(1001L);
		loginRequestdto.setPassword("c");
		return loginRequestdto;
	}

	@Before
	public void setUp() {

		loginRequestdto = getLoginRequestdto();
		loginResponsedto = getLoginResponsedto();
	}

	@Test
	public void testLogin() throws GeneralException {
		Mockito.when(loginServiceImplementation.login(Mockito.any())).thenReturn(Optional.of(loginResponsedto));
		ResponseEntity<Optional<LoginResponsedto>> loginResponsedto = loginController.login(loginRequestdto);
		Assert.assertNotNull(loginResponsedto);

	}

	@Test(expected = GeneralException.class)
	public void testLoginNegative() throws Exception {
		Mockito.when(loginServiceImplementation.login(Mockito.any())).thenReturn(Optional.ofNullable(null));
		loginController.login(loginRequestdto);
	}
}
