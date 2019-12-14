package com.bank.retailbanking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.retailbanking.constants.ApplicationConstants;
import com.bank.retailbanking.dto.AccountList;
import com.bank.retailbanking.dto.AccountSearchResponseDto;
import com.bank.retailbanking.exception.NoAccountListException;
import com.bank.retailbanking.service.AdminService;

import lombok.extern.slf4j.Slf4j;

@CrossOrigin(allowedHeaders = { "*", "*/" }, origins = { "*", "*/" })
@RestController
@RequestMapping("/admin/customers")
@Slf4j
public class AdminController {
	@Autowired
	AdminService adminService;

	@GetMapping("/{accountNumber}")
	public ResponseEntity<AccountSearchResponseDto> getAccountList(@PathVariable("accountNumber") String accountNumber)
			throws NoAccountListException {
		log.info("Searching list by partial account number");
		AccountSearchResponseDto accountSearchResponseDto = new AccountSearchResponseDto();
		List<AccountList> accountList = adminService.getAccountList(accountNumber);
		if (accountList.isEmpty()) {
			accountSearchResponseDto.setStatusCode(ApplicationConstants.LIST_FAILURE_CODE);
			accountSearchResponseDto.setMessage(ApplicationConstants.ACCOUNT_LIST_FAILURE_MESSAGE);
		}
		accountSearchResponseDto.setAccountList(accountList);
		accountSearchResponseDto.setStatusCode(ApplicationConstants.FUND_TRANSFER_SUCCESS_CODE);
		accountSearchResponseDto.setMessage(ApplicationConstants.ACCOUNT_LIST_SUCCESS_MESSAGE);
		return new ResponseEntity<>(accountSearchResponseDto, HttpStatus.OK);

	}

}
