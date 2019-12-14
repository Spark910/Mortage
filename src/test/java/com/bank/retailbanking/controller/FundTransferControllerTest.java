package com.bank.retailbanking.controller;

import static org.junit.Assert.assertEquals;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bank.retailbanking.constants.ApplicationConstants;
import com.bank.retailbanking.controller.FundTransferController;
import com.bank.retailbanking.dto.FundTransferRequestDto;
import com.bank.retailbanking.dto.FundTransferResponseDto;
import com.bank.retailbanking.entity.CustomerTransactions;
import com.bank.retailbanking.exception.AmountInvalidException;
import com.bank.retailbanking.exception.CustomerNotFoundException;
import com.bank.retailbanking.exception.SameAccountNumberException;
import com.bank.retailbanking.service.FundTransferService;

@RunWith(SpringJUnit4ClassRunner.class)
public class FundTransferControllerTest {
	@InjectMocks
	FundTransferController fundTransferController;

	@Mock
	FundTransferService fundTransferService;

	FundTransferRequestDto fundTransferRequestDto = null;
	FundTransferResponseDto fundTransferResponseDto = null;
	CustomerTransactions customerTransactions = null;

	@Before
	public void before() {
		fundTransferRequestDto = new FundTransferRequestDto();
		fundTransferRequestDto.setCreditAccount(100002L);
		fundTransferRequestDto.setAmount(100.0);
		fundTransferRequestDto.setCustomerId(1001L);
		fundTransferRequestDto.setTransactionPurpose(ApplicationConstants.FUND_TRANSFER_PURPOSE_MESSAGE);

		fundTransferResponseDto = new FundTransferResponseDto();
		fundTransferResponseDto.setStatusCode(ApplicationConstants.FUND_TRANSFER_SUCCESS_CODE);
		fundTransferResponseDto.setStatusMessage(ApplicationConstants.FUND_TRANSFER_SUCCESS_MESSAGE);

		customerTransactions = new CustomerTransactions();
	}

	@Test
	public void testFundTransfer() throws CustomerNotFoundException, AmountInvalidException, SameAccountNumberException{
		Mockito.when(fundTransferService.fundTransfer(fundTransferRequestDto))
				.thenReturn(Optional.of(fundTransferResponseDto));
		ResponseEntity<Optional<FundTransferResponseDto>> response = fundTransferController
				.fundTransfer(fundTransferRequestDto);
		Assert.assertNotNull(response);
	}

	@Test
	public void testFundTransferFailure() throws CustomerNotFoundException, AmountInvalidException,
			SameAccountNumberException{
		Mockito.when(fundTransferService.fundTransfer(fundTransferRequestDto)).thenReturn(Optional.ofNullable(null));
		ResponseEntity<Optional<FundTransferResponseDto>> response = fundTransferController
				.fundTransfer(fundTransferRequestDto);
		assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
	}
}
