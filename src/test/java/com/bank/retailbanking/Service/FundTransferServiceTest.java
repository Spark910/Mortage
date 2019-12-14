
package com.bank.retailbanking.Service;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.bank.retailbanking.constants.ApplicationConstants;
import com.bank.retailbanking.dto.FundTransferRequestDto;
import com.bank.retailbanking.dto.FundTransferResponseDto;
import com.bank.retailbanking.entity.Customer;
import com.bank.retailbanking.entity.CustomerAccountDetail;
import com.bank.retailbanking.entity.CustomerTransaction;
import com.bank.retailbanking.exception.AmountInvalidException;
import com.bank.retailbanking.exception.CustomerNotFoundException;
import com.bank.retailbanking.exception.MortgageException;
import com.bank.retailbanking.exception.SameAccountNumberException;
import com.bank.retailbanking.repository.CustomerAccountDetailsRepository;
import com.bank.retailbanking.repository.CustomerRepository;
import com.bank.retailbanking.repository.CustomerTransactionsRepository;
import com.bank.retailbanking.service.TransactionServiceImpl;

@RunWith(MockitoJUnitRunner.Silent.class)
public class FundTransferServiceTest {

	@InjectMocks
	TransactionServiceImpl fundTransferServiceImpl;

	@Mock
	CustomerTransactionsRepository customerTransactionsRepository;

	@Mock
	CustomerAccountDetailsRepository customerAccountDetailsRepository;

	@Mock
	CustomerRepository customerRepository;

	Customer customer = null;
	Customer customer1 = null;
	Customer customer2 = null;
	CustomerAccountDetail customerAccountDetails = null;
	CustomerAccountDetail customerAccountDetails1 = null;
	CustomerAccountDetail customerAccountDetails2 = null;
	CustomerAccountDetail customerAccountDetails3 = null;
	CustomerAccountDetail customerAccountDetails4 = null;
	CustomerTransaction customerTransactions = null;
	CustomerTransaction customerCreditTransactions = null;
	FundTransferRequestDto fundTransferRequestDto = null;
	FundTransferRequestDto fundTransferRequestDto1 = null;
	FundTransferRequestDto fundTransferRequestDto2 = null;
	FundTransferRequestDto fundTransferRequestDto3 = null;

	@Before
	public void before() {
		customer = new Customer();
		customer1 = new Customer();
		customer2 = new Customer();
		customerAccountDetails = new CustomerAccountDetail();
		customerAccountDetails1 = new CustomerAccountDetail();
		customerAccountDetails2 = new CustomerAccountDetail();
		customerAccountDetails3 = new CustomerAccountDetail();
		customerAccountDetails4 = new CustomerAccountDetail();
		customerTransactions = new CustomerTransaction();
		customerCreditTransactions = new CustomerTransaction();

		customer.setCustomerId(1001L);
		customer1.setCustomerId(1002L);
		customer2.setCustomerId(1004L);

		fundTransferRequestDto = new FundTransferRequestDto();
		fundTransferRequestDto.setCreditAccount(100003L);
		fundTransferRequestDto.setAmount(5000.0);
		fundTransferRequestDto.setCustomerId(1001L);
		fundTransferRequestDto.setTransactionPurpose(ApplicationConstants.FUND_TRANSFER_PURPOSE_MESSAGE);

		fundTransferRequestDto1 = new FundTransferRequestDto();
		fundTransferRequestDto1.setCreditAccount(100003L);
		fundTransferRequestDto1.setAmount(10000.0);
		fundTransferRequestDto1.setCustomerId(1001L);
		fundTransferRequestDto1.setTransactionPurpose(ApplicationConstants.FUND_TRANSFER_PURPOSE_MESSAGE);

		fundTransferRequestDto2 = new FundTransferRequestDto();
		fundTransferRequestDto2.setCreditAccount(100003L);
		fundTransferRequestDto2.setAmount(1000.0);
		fundTransferRequestDto2.setCustomerId(1001L);
		fundTransferRequestDto2.setTransactionPurpose(ApplicationConstants.FUND_TRANSFER_PURPOSE_MESSAGE);

		fundTransferRequestDto3 = new FundTransferRequestDto();
		fundTransferRequestDto3.setCreditAccount(100003L);
		fundTransferRequestDto3.setAmount(5000.0);
		fundTransferRequestDto3.setCustomerId(1001L);
		fundTransferRequestDto3.setTransactionPurpose(ApplicationConstants.FUND_TRANSFER_PURPOSE_MESSAGE);

		customerAccountDetails.setAccountNumber(100002L);
		customerAccountDetails.setAccountOpeningDate(LocalDate.now());
		customerAccountDetails.setAccountType(ApplicationConstants.FUND_TRANSFER_ACCOUNT_TYPE);
		customerAccountDetails.setAvailableBalance(4000.00);
		customerAccountDetails.setCustomerId(customer);

		customerAccountDetails.setAccountNumber(100002L);
		customerAccountDetails.setAccountOpeningDate(LocalDate.now());
		customerAccountDetails.setAccountType(ApplicationConstants.FUND_TRANSFER_ACCOUNT_TYPE);
		customerAccountDetails.setAvailableBalance(980.00);
		customerAccountDetails.setCustomerId(customer2);

		customerAccountDetails4.setAccountNumber(100002L);
		customerAccountDetails4.setAccountOpeningDate(LocalDate.now());
		customerAccountDetails4.setAccountType(ApplicationConstants.FUND_TRANSFER_ACCOUNT_TYPE);
		customerAccountDetails4.setAvailableBalance(14000.00);
		customerAccountDetails4.setCustomerId(customer2);

		customerAccountDetails1.setAccountNumber(100003L);
		customerAccountDetails1.setAccountOpeningDate(LocalDate.now());
		customerAccountDetails1.setAccountType(ApplicationConstants.FUND_TRANSFER_ACCOUNT_TYPE);
		customerAccountDetails1.setAvailableBalance(1001.00);
		customerAccountDetails1.setCustomerId(customer1);

		customerAccountDetails2.setAccountNumber(100003L);
		customerAccountDetails2.setAccountOpeningDate(LocalDate.now());
		customerAccountDetails2.setAccountType(ApplicationConstants.FUND_TRANSFER_ACCOUNT_TYPE);
		customerAccountDetails2.setAvailableBalance(1000.00);
		customerAccountDetails2.setCustomerId(customer1);

		customerAccountDetails3.setAccountNumber(100003L);
		customerAccountDetails3.setAccountOpeningDate(LocalDate.now());
		customerAccountDetails3.setAccountType(ApplicationConstants.FUND_TRANSFER_ACCOUNT_TYPE);
		customerAccountDetails3.setAvailableBalance(4000.00);
		customerAccountDetails3.setCustomerId(customer1);

		customerTransactions.setAccountNumber(customerAccountDetails);
		customerTransactions.setTransactionAmount(fundTransferRequestDto.getAmount());
		customerTransactions.setTransactionComments(ApplicationConstants.FUND_TRANSFER_PURPOSE_MESSAGE);
		customerTransactions.setTransactionDate(LocalDate.now());
		customerTransactions.setTransactionStatus(ApplicationConstants.FUND_TRANSFER_SUCCESS_MESSAGE);
		customerTransactions.setTransactionType(ApplicationConstants.TRANSACTION_DEBIT_MESSAGE);
		customerCreditTransactions.setAccountNumber(customerAccountDetails);
		customerTransactions.setTransactionAmount(fundTransferRequestDto.getAmount());
		customerTransactions.setTransactionComments(ApplicationConstants.FUND_TRANSFER_PURPOSE_MESSAGE);
		customerTransactions.setTransactionDate(LocalDate.now());
		customerTransactions.setTransactionStatus(ApplicationConstants.FUND_TRANSFER_SUCCESS_MESSAGE);
		customerTransactions.setTransactionType(ApplicationConstants.TRANSACTION_CREDIT_MESSAGE);
	}

	@Test
	public void testFundTransferSuccess()
			throws CustomerNotFoundException, AmountInvalidException, SameAccountNumberException, MortgageException {
		Mockito.when(customerRepository.findByCustomerId(fundTransferRequestDto1.getCustomerId()))
				.thenReturn(Optional.of(customer));
		Mockito.when(customerAccountDetailsRepository.findByCustomerId(customer))
				.thenReturn(Optional.of(customerAccountDetails4));
		Mockito.when(customerAccountDetailsRepository.findByAccountNumber(fundTransferRequestDto1.getCreditAccount()))
				.thenReturn(Optional.of(customerAccountDetails));
		Optional<FundTransferResponseDto> expected = fundTransferServiceImpl.fundTransfer(fundTransferRequestDto1);
		assertEquals(expected.isPresent(), true);
	}

	@Test(expected = SameAccountNumberException.class)
	public void testFundTransferSameAccountNegative()
			throws CustomerNotFoundException, AmountInvalidException, SameAccountNumberException, MortgageException {
		Mockito.when(customerRepository.findByCustomerId(fundTransferRequestDto.getCustomerId()))
				.thenReturn(Optional.of(customer));
		Mockito.when(customerAccountDetailsRepository.findByCustomerId(customer))
				.thenReturn(Optional.of(customerAccountDetails));
		Mockito.when(customerAccountDetailsRepository.findByAccountNumber(fundTransferRequestDto.getCreditAccount()))
				.thenReturn(Optional.of(customerAccountDetails));
		String message = ApplicationConstants.ACCOUNTNUMBER_INVALID_MESSAGE;
		Optional<FundTransferResponseDto> expected = fundTransferServiceImpl.fundTransfer(fundTransferRequestDto);
		assertEquals(expected, message);
	}

	@Test(expected = AmountInvalidException.class)
	public void testFundInsufficientAmountException()
			throws CustomerNotFoundException, AmountInvalidException, SameAccountNumberException, MortgageException {
		Mockito.when(customerRepository.findByCustomerId(fundTransferRequestDto3.getCustomerId()))
				.thenReturn(Optional.of(customer));
		Mockito.when(customerAccountDetailsRepository.findByCustomerId(customer))
				.thenReturn(Optional.of(customerAccountDetails2));
		Mockito.when(customerAccountDetailsRepository.findByAccountNumber(fundTransferRequestDto3.getCreditAccount()))
				.thenReturn(Optional.of(customerAccountDetails3));
		String message = ApplicationConstants.AMOUNT_LESSBALANCE_MESSAGE;
		Optional<FundTransferResponseDto> expected = fundTransferServiceImpl.fundTransfer(fundTransferRequestDto3);
		assertEquals(expected, message);
	}

	@Test(expected = AmountInvalidException.class)
	public void testFundInsufficientAmount()
			throws CustomerNotFoundException, AmountInvalidException, SameAccountNumberException ,MortgageException{
		Mockito.when(customerRepository.findByCustomerId(fundTransferRequestDto.getCustomerId()))
				.thenReturn(Optional.of(customer));
		Mockito.when(customerAccountDetailsRepository.findByCustomerId(customer))
				.thenReturn(Optional.of(customerAccountDetails3));
		Mockito.when(customerAccountDetailsRepository.findByAccountNumber(fundTransferRequestDto.getCreditAccount()))
				.thenReturn(Optional.of(customerAccountDetails));
		String message = ApplicationConstants.AMOUNT_LESSBALANCE_MESSAGE;
		Optional<FundTransferResponseDto> expected = fundTransferServiceImpl.fundTransfer(fundTransferRequestDto);
		assertEquals(expected, message);
	}

	@Test(expected = AmountInvalidException.class)
	public void testFundAmountLessThanMinimumBalance()
			throws CustomerNotFoundException, AmountInvalidException, SameAccountNumberException ,MortgageException{
		Mockito.when(customerRepository.findByCustomerId(fundTransferRequestDto2.getCustomerId()))
				.thenReturn(Optional.of(customer));
		Mockito.when(customerAccountDetailsRepository.findByCustomerId(customer))
				.thenReturn(Optional.of(customerAccountDetails1));
		Mockito.when(customerAccountDetailsRepository.findByAccountNumber(fundTransferRequestDto2.getCreditAccount()))
				.thenReturn(Optional.of(customerAccountDetails));
		String message = ApplicationConstants.AMOUNT_LESSBALANCE_MESSAGE;
		Optional<FundTransferResponseDto> expected = fundTransferServiceImpl.fundTransfer(fundTransferRequestDto2);
		assertEquals(expected, message);
	}

	@Test(expected = CustomerNotFoundException.class)
	public void testFundAccountNumberInvalidNegative()
			throws CustomerNotFoundException, AmountInvalidException, SameAccountNumberException,MortgageException {
		Mockito.when(customerRepository.findByCustomerId(fundTransferRequestDto.getCustomerId()))
				.thenReturn(Optional.of(customer));
		Mockito.when(customerAccountDetailsRepository.findByCustomerId(customer1))
				.thenReturn(Optional.of(customerAccountDetails));
		Mockito.when(customerAccountDetailsRepository.findByAccountNumber(6L))
				.thenReturn(Optional.of(customerAccountDetails1));
		String message = ApplicationConstants.CUSTOMER_NOT_FOUND_MESSAGE;
		Optional<FundTransferResponseDto> expected = fundTransferServiceImpl.fundTransfer(fundTransferRequestDto1);
		assertEquals(expected, message);
	}

	@Test(expected = CustomerNotFoundException.class)
	public void testFundCustomerIdInvalidNegative()
			throws CustomerNotFoundException, AmountInvalidException, SameAccountNumberException,MortgageException {
		Mockito.when(customerRepository.findByCustomerId(1003L)).thenReturn(Optional.of(customer));
		String message = ApplicationConstants.CUSTOMER_NOT_FOUND_MESSAGE;
		Optional<FundTransferResponseDto> expected = fundTransferServiceImpl.fundTransfer(fundTransferRequestDto1);
		assertEquals(expected, message);
	}

	@Test(expected = CustomerNotFoundException.class)
	public void testFundAccountNumberNegative()
			throws CustomerNotFoundException, AmountInvalidException, SameAccountNumberException,MortgageException {
		Mockito.when(customerRepository.findByCustomerId(6L)).thenReturn(Optional.of(customer));
		Mockito.when(customerAccountDetailsRepository.findByCustomerId(customer))
				.thenReturn(Optional.of(customerAccountDetails));
		Mockito.when(customerAccountDetailsRepository.findByAccountNumber(1001L))
				.thenReturn(Optional.of(customerAccountDetails1));
		String message = ApplicationConstants.CUSTOMER_NOT_FOUND_MESSAGE;
		Optional<FundTransferResponseDto> expected = fundTransferServiceImpl.fundTransfer(fundTransferRequestDto1);
		assertEquals(expected, message);
	}

	@Test(expected = CustomerNotFoundException.class)
	public void testFundAccountNumberInvalid()
			throws CustomerNotFoundException, AmountInvalidException, SameAccountNumberException,MortgageException {
		Mockito.when(customerRepository.findByCustomerId(1001L)).thenReturn(Optional.of(customer));
		Mockito.when(customerAccountDetailsRepository.findByCustomerId(customer))
				.thenReturn(Optional.of(customerAccountDetails));
		Mockito.when(customerAccountDetailsRepository.findByAccountNumber(6L))
				.thenReturn(Optional.of(customerAccountDetails1));
		String message = ApplicationConstants.CUSTOMER_NOT_FOUND_MESSAGE;
		;
		Optional<FundTransferResponseDto> expected = fundTransferServiceImpl.fundTransfer(fundTransferRequestDto1);
		assertEquals(expected, message);
	}
}
