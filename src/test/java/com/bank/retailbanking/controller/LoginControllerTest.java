package com.bank.retailbanking.controller;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bank.retailbanking.dto.LoginRequestdto;
import com.bank.retailbanking.dto.LoginResponsedto;
import com.bank.retailbanking.exception.GeneralException;
import com.bank.retailbanking.service.LoginServiceImplementation;


@RunWith(SpringJUnit4ClassRunner.class)
public class LoginControllerTest {
	
	@InjectMocks
	LoginController loginController;
	
	@Mock
	LoginServiceImplementation loginServiceImplementation;
	
	LoginRequestdto loginRequestDto =new LoginRequestdto();
	LoginResponsedto loginResponsedto= new LoginResponsedto();
	@Before
	public void setUp() {
		loginRequestDto.setAccountType("savings");
		loginRequestDto.setCustomerId(1L);
		loginRequestDto.setPassword("c");
		loginResponsedto.setCustomerId(1L);
	}
	
	@Test
	public void testUserLoginPositive() throws GeneralException {
		Mockito.when(loginServiceImplementation.login(loginRequestDto)).thenReturn(Optional.of(loginResponsedto));
		ResponseEntity<Optional<LoginResponsedto>> loginResponsedto=loginController.login(loginRequestDto);
		Assert.assertNotNull(loginResponsedto);
	}
	
	@Test
	public void testUserLoginNegative() throws GeneralException {
		Mockito.when(loginServiceImplementation.login(loginRequestDto)).thenReturn(Optional.ofNullable(null));
		ResponseEntity<Optional<LoginResponsedto>> loginResponsedto=loginController.login(loginRequestDto);
		Assert.assertNotNull(loginResponsedto);
	}

}
