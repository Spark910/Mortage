package com.bank.retailbanking.controller;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bank.retailbanking.dto.ViewAccountResponseDto;
import com.bank.retailbanking.service.AdminService;


@RunWith(SpringJUnit4ClassRunner.class)
public class AdminControllerTest {
	
	@InjectMocks
	AdminController adminController;

	@Mock
	AdminService adminService;
	
	ViewAccountResponseDto viewAccountResponseDto=null;
	
	@Before
	public void before() {
		viewAccountResponseDto=new ViewAccountResponseDto();
		viewAccountResponseDto.setAccountNumber(1002L);
		viewAccountResponseDto.setAccountType("savings");
		viewAccountResponseDto.setAge(12);
	}
	
	@Test
	public void testViewAccountDetails() {
		Mockito.when(adminService.viewAccountDetails(1002L)).thenReturn(viewAccountResponseDto);
		ResponseEntity<ViewAccountResponseDto> response=adminController.viewAccountDetails(1002L);
		Assert.assertNotNull(response);
	}

}
