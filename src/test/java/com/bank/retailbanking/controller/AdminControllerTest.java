package com.bank.retailbanking.controller;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bank.retailbanking.constants.ApplicationConstants;
import com.bank.retailbanking.dto.AccountList;
import com.bank.retailbanking.dto.AccountSearchResponseDto;
import com.bank.retailbanking.dto.ViewAccountResponseDto;
import com.bank.retailbanking.entity.Customer;
import com.bank.retailbanking.exception.NoAccountListException;
import com.bank.retailbanking.service.AdminService;

@RunWith(SpringJUnit4ClassRunner.class)
public class AdminControllerTest {

	@InjectMocks
	AdminController adminController;

	@Mock
	AdminService adminService;

	ViewAccountResponseDto viewAccountResponseDto = null;
	List<AccountList> customerAccountList = null;
	List<AccountList> customerAccountList1 = null;
	AccountList accountList = null;
	Customer customer = null;
	AccountSearchResponseDto accountSearchResponseDto = null;

	@Before
	public void before() {
		viewAccountResponseDto = new ViewAccountResponseDto();
		viewAccountResponseDto.setAccountNumber(1002L);
		viewAccountResponseDto.setAccountType("savings");
		viewAccountResponseDto.setAge(12);

		customer = new Customer();
		accountSearchResponseDto = new AccountSearchResponseDto();
		accountList = new AccountList();
		customerAccountList = new ArrayList<>();
		accountList.setAccountNumber(1L);
		customerAccountList.add(accountList);
		accountSearchResponseDto.setAccountList(customerAccountList);
		accountSearchResponseDto.setStatusCode(ApplicationConstants.SUCESS_STATUS_CODE);

		customerAccountList1 = new ArrayList<>();
	}

	@Test
	public void testViewAccountDetails() {
		Mockito.when(adminService.viewAccountDetails(1002L)).thenReturn(viewAccountResponseDto);
		ResponseEntity<ViewAccountResponseDto> response = adminController.viewAccountDetails(1002L);
		Assert.assertNotNull(response);
	}

	@Test
	public void testGetAccountListPositive() throws NoAccountListException {
		Mockito.when(adminService.getAccountList("1L")).thenReturn(customerAccountList);
		Integer expected = adminController.getAccountList("1L").getStatusCodeValue();
		assertEquals(ApplicationConstants.SUCESS_STATUS_CODE, expected);
	}

	@Test
	public void testGetAccountListNegative() throws NoAccountListException {
		Mockito.when(adminService.getAccountList("1L")).thenReturn(customerAccountList1);
		Integer expected = adminController.getAccountList("2L").getStatusCodeValue();
		assertEquals(ApplicationConstants.LIST_FAILURE_CODE, expected);
	}

}
