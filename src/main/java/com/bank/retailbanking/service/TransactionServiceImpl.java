package com.bank.retailbanking.service;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.bank.retailbanking.constants.ApplicationConstants;
import com.bank.retailbanking.dto.AccountSummaryResponse;
import com.bank.retailbanking.dto.AccountSummaryResponsedto;
import com.bank.retailbanking.dto.FundTransferRequestDto;
import com.bank.retailbanking.dto.FundTransferResponseDto;
import com.bank.retailbanking.dto.TransactionSummaryResponsedto;
import com.bank.retailbanking.entity.Customer;
import com.bank.retailbanking.entity.CustomerAccountDetail;
import com.bank.retailbanking.entity.CustomerTransaction;
import com.bank.retailbanking.exception.AmountInvalidException;
import com.bank.retailbanking.exception.CustomerNotFoundException;
import com.bank.retailbanking.exception.GeneralException;
import com.bank.retailbanking.exception.MortgageException;
import com.bank.retailbanking.exception.SameAccountNumberException;
import com.bank.retailbanking.exception.TransactionException;
import com.bank.retailbanking.repository.CustomerAccountDetailsRepository;
import com.bank.retailbanking.repository.CustomerRepository;
import com.bank.retailbanking.repository.CustomerTransactionsRepository;
import com.bank.retailbanking.util.Month;

import lombok.extern.slf4j.Slf4j;

/**
 * The {@code TransactionServiceIMPl} class provides implimentation to the
 * specific methods
 * 
 * @author maheswraraju
 */

@Service
@Slf4j
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	CustomerAccountDetailsRepository customerAccountDetailsRepository;

	@Autowired
	CustomerTransactionsRepository customerTransactionsRepository;

	/*
	 * Method is used to transfer funds b/w two different savings account
	 */
	@Override
	@Transactional
	public Optional<FundTransferResponseDto> fundTransfer(FundTransferRequestDto fundTransferRequestDto)
			throws CustomerNotFoundException, AmountInvalidException, SameAccountNumberException, MortgageException {
		FundTransferResponseDto fundTransferResponseDto = new FundTransferResponseDto();
		Optional<Customer> customerDetails = customerRepository
				.findByCustomerId(fundTransferRequestDto.getCustomerId());
		if (customerDetails.isPresent()) {
			Optional<CustomerAccountDetail> customerAccountDetails = customerAccountDetailsRepository
					.findByCustomerIdAndAccountType(customerDetails.get(),
							fundTransferRequestDto.getTransactionPurpose());
			Optional<CustomerAccountDetail> creditAccountDetails = customerAccountDetailsRepository
					.findByAccountNumber(fundTransferRequestDto.getCreditAccount());
			/*
			 * Mortgage transaction
			 */
			if (customerAccountDetails.isPresent()) {
				Double amount = customerAccountDetails.get().getAvailableBalance();
				Double creditedAmount = creditAccountDetails.get().getAvailableBalance();
				Double transferAmount = fundTransferRequestDto.getAmount();
				Double balance = amount - transferAmount;
				if (transferAmount < amount && balance > ApplicationConstants.OPENING_BALANCE) {
					CustomerTransaction customerTransactions = new CustomerTransaction();
					customerTransactions.setAccountNumber(customerAccountDetails.get());
					customerTransactions.setTransactionAmount(fundTransferRequestDto.getAmount());
					customerTransactions.setTransactionComments(fundTransferRequestDto.getTransactionPurpose());
					customerTransactions.setTransactionDate(LocalDate.now());
					customerTransactions.setTransactionStatus(ApplicationConstants.TRANSACTION_SUCCESS_MESSAGE);
					customerTransactions.setTransactionType(ApplicationConstants.TRANSACTION_DEBIT_MESSAGE);
					BeanUtils.copyProperties(fundTransferRequestDto, customerTransactions);
					customerTransactionsRepository.save(customerTransactions);
					Double balanceRemaining = amount - transferAmount;
					customerAccountDetails.get().setAvailableBalance(balanceRemaining);
					customerAccountDetailsRepository.save(customerAccountDetails.get());
					customerTransactionsRepository.save(customerTransactions);
					CustomerTransaction customerCreditTransactions = new CustomerTransaction();
					customerCreditTransactions.setAccountNumber(creditAccountDetails.get());
					customerCreditTransactions.setTransactionAmount(fundTransferRequestDto.getAmount());
					customerCreditTransactions.setTransactionComments(customerTransactions.getTransactionComments());
					customerCreditTransactions.setTransactionDate(customerTransactions.getTransactionDate());
					customerCreditTransactions.setTransactionStatus(ApplicationConstants.TRANSACTION_SUCCESS_MESSAGE);
					customerCreditTransactions.setTransactionType(ApplicationConstants.TRANSACTION_CREDIT_MESSAGE);
					Double finalBalance = creditedAmount + transferAmount;
					creditAccountDetails.get().setAvailableBalance(finalBalance);
					customerAccountDetailsRepository.save(creditAccountDetails.get());
					customerTransactionsRepository.save(customerCreditTransactions);
					return Optional.of(fundTransferResponseDto);
				}
				log.error(ApplicationConstants.AMOUNT_LESSBALANCE_MESSAGE);
				throw new AmountInvalidException(ApplicationConstants.AMOUNT_LESSBALANCE_MESSAGE);
			} else {
				Optional<CustomerAccountDetail> customerAccount = customerAccountDetailsRepository.findByCustomerId(customerDetails.get());
				if(!customerAccount.isPresent()) {
					throw new SameAccountNumberException("Invalid customer Account number");
				}
				
				if ((customerAccount.get().getAccountNumber() != creditAccountDetails.get().getAccountNumber())) {
					if (customerAccount.isPresent()) {
						Double amount = customerAccountDetails.get().getAvailableBalance();
						Double creditedAmount = creditAccountDetails.get().getAvailableBalance();
						Double transferAmount = fundTransferRequestDto.getAmount();
						Double balance = amount - transferAmount;
						if (transferAmount < amount && balance > ApplicationConstants.OPENING_BALANCE) {
							/*
							 * @Description Gets stored as debit action savings
							 */
							CustomerTransaction customerTransactions = new CustomerTransaction();
							customerTransactions.setAccountNumber(customerAccountDetails.get());
							customerTransactions.setTransactionAmount(fundTransferRequestDto.getAmount());
							customerTransactions.setTransactionComments(fundTransferRequestDto.getTransactionPurpose());
							customerTransactions.setTransactionDate(LocalDate.now());
							customerTransactions.setTransactionStatus(ApplicationConstants.TRANSACTION_SUCCESS_MESSAGE);
							customerTransactions.setTransactionType(ApplicationConstants.TRANSACTION_DEBIT_MESSAGE);
							BeanUtils.copyProperties(fundTransferRequestDto, customerTransactions);
							customerTransactionsRepository.save(customerTransactions);
							Double balanceRemaining = amount - transferAmount;
							customerAccountDetails.get().setAvailableBalance(balanceRemaining);
							customerAccountDetailsRepository.save(customerAccountDetails.get());
							customerTransactionsRepository.save(customerTransactions);
							/*
							 * @Description Get stored as credit action savings
							 */
							CustomerTransaction customerCreditTransactions = new CustomerTransaction();
							customerCreditTransactions.setAccountNumber(creditAccountDetails.get());
							customerCreditTransactions.setTransactionAmount(fundTransferRequestDto.getAmount());
							customerCreditTransactions
									.setTransactionComments(customerTransactions.getTransactionComments());
							customerCreditTransactions.setTransactionDate(customerTransactions.getTransactionDate());
							customerCreditTransactions
									.setTransactionStatus(ApplicationConstants.TRANSACTION_SUCCESS_MESSAGE);
							customerCreditTransactions
									.setTransactionType(ApplicationConstants.TRANSACTION_CREDIT_MESSAGE);
							Double finalBalance = creditedAmount + transferAmount;
							creditAccountDetails.get().setAvailableBalance(finalBalance);
							customerAccountDetailsRepository.save(creditAccountDetails.get());
							customerTransactionsRepository.save(customerCreditTransactions);
							return Optional.of(fundTransferResponseDto);
						}
						log.error(ApplicationConstants.AMOUNT_LESSBALANCE_MESSAGE);
						throw new AmountInvalidException(ApplicationConstants.AMOUNT_LESSBALANCE_MESSAGE);
					}
					log.error(ApplicationConstants.CUSTOMER_NOT_FOUND_MESSAGE);
					throw new CustomerNotFoundException(ApplicationConstants.CUSTOMER_NOT_FOUND_MESSAGE);
				}
				log.error(ApplicationConstants.ACCOUNTNUMBER_INVALID_MESSAGE);
				throw new SameAccountNumberException(ApplicationConstants.ACCOUNTNUMBER_INVALID_MESSAGE);
			}
		}
		return Optional.of(fundTransferResponseDto);
	}

	/*
	 * this method takes two parameters which are customerId and month and finds the
	 * specific month number And with the help of customer id and month transactions
	 * will be fetched
	 */
	@Override
	public Optional<TransactionSummaryResponsedto> fetchTransactionsByMonth(Long customerId, String month)
			throws ParseException, TransactionException {
		log.info("fetch fetchTransactionsByMonth() is called");
		Optional<Customer> customerDetails = customerRepository.findById(customerId);
		if (customerDetails.isPresent()) {
			Optional<CustomerAccountDetail> customerAccountDetails = customerAccountDetailsRepository
					.findByCustomerId(customerDetails.get());

			TransactionSummaryResponsedto accountSummaryResponsedto = new TransactionSummaryResponsedto();
			List<AccountSummaryResponse> accountSummaryResponseList = new ArrayList<>();
			if (customerAccountDetails.isPresent()) {
				List<CustomerTransaction> transactionList = customerTransactionsRepository
						.findByAccountNumber(customerAccountDetails.get());
				for (CustomerTransaction transactionHistory : transactionList) {

					Integer transactionMonth = transactionHistory.getTransactionDate().getMonthValue();

					int actualMonthNumber = Month.monthStringToInt(month);

					if (actualMonthNumber == transactionMonth) {
						AccountSummaryResponse accountSummaryResponse = new AccountSummaryResponse();
						accountSummaryResponse.setTransactionAmount(transactionHistory.getTransactionAmount());
						accountSummaryResponse.setTransactionComments(transactionHistory.getTransactionComments());
						accountSummaryResponse.setTransactionDate(transactionHistory.getTransactionDate());
						accountSummaryResponse.setTransactionStatus(transactionHistory.getTransactionStatus());
						accountSummaryResponse.setTransactionType(transactionHistory.getTransactionType());
						accountSummaryResponseList.add(accountSummaryResponse);
					}
				}
				accountSummaryResponsedto.setTransactions(accountSummaryResponseList);
			}
			return Optional.of(accountSummaryResponsedto);
		}
		throw new TransactionException("Invalid Customer ID");
	}

	/*
	 * Method enables to get account summary details
	 */
	public AccountSummaryResponsedto getAccountSummary(Long customerId) throws GeneralException {
		log.info("Entering into AccountSummaryServiceImplementation--------getAccountSummary() Method");
		Optional<Customer> customerDetails = customerRepository.findById(customerId);
		AccountSummaryResponsedto accountSummaryResponsedto = new AccountSummaryResponsedto();
		if (!customerDetails.isPresent()) {
			throw new GeneralException("Invalid customer");
		}
		Optional<CustomerAccountDetail> customerAccountDetails = customerAccountDetailsRepository
				.findByCustomerId(customerDetails.get());
		if (!customerAccountDetails.isPresent()) {
			throw new GeneralException("No valid Account is available for the customer");
		}

		accountSummaryResponsedto.setAccountBalance(customerAccountDetails.get().getAvailableBalance());
		accountSummaryResponsedto.setAccountNumber(customerAccountDetails.get().getAccountNumber());

		List<AccountSummaryResponse> accountSummaryResponseList = new ArrayList<>();

		Pageable pageable = PageRequest.of(0, ApplicationConstants.TRANSACTION_HISTORY_COUNT, Direction.DESC,
				"transactionId");
		List<CustomerTransaction> customerTransactionsList = customerTransactionsRepository
				.findByAccountNumber(customerAccountDetails.get(), pageable);
		customerTransactionsList.forEach(customerTransaction -> {
			AccountSummaryResponse accountSummaryResponse = new AccountSummaryResponse();
			accountSummaryResponse.setTransactionAmount(customerTransaction.getTransactionAmount());
			accountSummaryResponse.setTransactionComments(customerTransaction.getTransactionComments());
			accountSummaryResponse.setTransactionDate(customerTransaction.getTransactionDate());
			accountSummaryResponse.setTransactionStatus(customerTransaction.getTransactionStatus());
			accountSummaryResponse.setTransactionType(customerTransaction.getTransactionType());
			accountSummaryResponseList.add(accountSummaryResponse);
		});
		accountSummaryResponsedto.setTransactions(accountSummaryResponseList);

		return accountSummaryResponsedto;
	}

}