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
import com.bank.retailbanking.controller.TransactionController;
import com.bank.retailbanking.dto.FundTransferRequestDto;
import com.bank.retailbanking.dto.FundTransferResponseDto;
import com.bank.retailbanking.entity.CustomerTransaction;
import com.bank.retailbanking.exception.AmountInvalidException;
import com.bank.retailbanking.exception.CustomerNotFoundException;
import com.bank.retailbanking.exception.MortgageException;
import com.bank.retailbanking.exception.SameAccountNumberException;
import com.bank.retailbanking.service.TransactionService;

@RunWith(SpringJUnit4ClassRunner.class)
public class FundTransferControllerTest {
	@InjectMocks
	TransactionController fundTransferController;

	@Mock
	TransactionService fundTransferService;

	FundTransferRequestDto fundTransferRequestDto = null;
	FundTransferResponseDto fundTransferResponseDto = null;
	CustomerTransaction customerTransactions = null;

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

		customerTransactions = new CustomerTransaction();
	}

	@Test
	public void testFundTransfer() throws CustomerNotFoundException, AmountInvalidException, SameAccountNumberException, MortgageException{
		Mockito.when(fundTransferService.fundTransfer(fundTransferRequestDto))
				.thenReturn(Optional.of(fundTransferResponseDto));
		ResponseEntity<Optional<FundTransferResponseDto>> response = fundTransferController
				.fundTransfer(fundTransferRequestDto);
		Assert.assertNotNull(response);
	}

	@Test
	public void testFundTransferFailure() throws CustomerNotFoundException, AmountInvalidException,
			SameAccountNumberException, MortgageException{
		Mockito.when(fundTransferService.fundTransfer(fundTransferRequestDto)).thenReturn(Optional.ofNullable(null));
		ResponseEntity<Optional<FundTransferResponseDto>> response = fundTransferController
				.fundTransfer(fundTransferRequestDto);
		assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
	}
}
