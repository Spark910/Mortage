package com.bank.retailbanking.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.retailbanking.dto.AccountSummaryResponse;
import com.bank.retailbanking.dto.TransactionSummaryResponsedto;
import com.bank.retailbanking.entity.Customer;
import com.bank.retailbanking.entity.CustomerAccountDetails;
import com.bank.retailbanking.entity.CustomerTransactions;
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
	 * this method rtakes two parameters which are customerId and month and finds
	 * the specific month number And with the help of cutomer id and month
	 * transactions will be fetched
	 */
	@Override
	public Optional<TransactionSummaryResponsedto> fetchTransactionsByMonth(Long customerId, String month)
			throws ParseException, TransactionException {
		log.info("fetch fetchTransactionsByMonth() is called");
		Optional<Customer> customerDetails = customerRepository.findById(customerId);
		if (customerDetails.isPresent()) {
			Optional<CustomerAccountDetails> customerAccountDetails = customerAccountDetailsRepository
					.findByCustomerId(customerDetails.get());

			TransactionSummaryResponsedto accountSummaryResponsedto = new TransactionSummaryResponsedto();
			List<AccountSummaryResponse> accountSummaryResponseList = new ArrayList<>();
			if (customerAccountDetails.isPresent()) {
				List<CustomerTransactions> transactionList = customerTransactionsRepository
						.findByAccountNumber(customerAccountDetails.get());
				for (CustomerTransactions transactionHistory : transactionList) {

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

}