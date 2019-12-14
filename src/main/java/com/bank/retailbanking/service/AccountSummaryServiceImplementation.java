package com.bank.retailbanking.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.bank.retailbanking.constants.ApplicationConstants;
import com.bank.retailbanking.dto.AccountSummaryResponse;
import com.bank.retailbanking.dto.AccountSummaryResponsedto;
import com.bank.retailbanking.entity.Customer;
import com.bank.retailbanking.entity.CustomerAccountDetails;
import com.bank.retailbanking.entity.CustomerTransactions;
import com.bank.retailbanking.exception.GeneralException;
import com.bank.retailbanking.repository.CustomerAccountDetailsRepository;
import com.bank.retailbanking.repository.CustomerRepository;
import com.bank.retailbanking.repository.CustomerTransactionsRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author Chethana M
 * @since December 2019
 * @Description This class is used to provide the account summary details of the
 *              particular customer bank account.
 *
 */
@Service
@Slf4j
public class AccountSummaryServiceImplementation implements AccountSummaryService {
	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	CustomerAccountDetailsRepository customerAccountDetailsRepository;

	@Autowired
	CustomerTransactionsRepository customerTransactionsRepository;

	/**
	 * @Description This method is used to get the customer account summary which
	 *              includes account details with latest 5 transactions
	 * @author Chethana
	 * @param customerId
	 * @return accountSummaryResponsedto
	 * @throws GeneralException
	 * 
	 */
	public AccountSummaryResponsedto getAccountSummary(Long customerId) throws GeneralException {
		log.info("Entering into AccountSummaryServiceImplementation--------getAccountSummary() Method");
		Optional<Customer> customerDetails = customerRepository.findById(customerId);
		AccountSummaryResponsedto accountSummaryResponsedto = new AccountSummaryResponsedto();
		if (!customerDetails.isPresent()) {
			throw new GeneralException("Invalid customer");
		} 
		Optional<CustomerAccountDetails> customerAccountDetails = customerAccountDetailsRepository
				.findByCustomerId(customerDetails.get());
		if (!customerAccountDetails.isPresent()) {
			throw new GeneralException("No valid Account is available for the customer");
		}

		accountSummaryResponsedto.setAccountBalance(customerAccountDetails.get().getAvailableBalance());
		accountSummaryResponsedto.setAccountNumber(customerAccountDetails.get().getAccountNumber());

		List<AccountSummaryResponse> accountSummaryResponseList = new ArrayList<>();

		Pageable pageable = PageRequest.of(0, ApplicationConstants.TRANSACTION_HISTORY_COUNT, Direction.DESC,
				"transactionId");
		List<CustomerTransactions> customerTransactionsList = customerTransactionsRepository
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
