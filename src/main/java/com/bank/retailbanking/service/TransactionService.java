package com.bank.retailbanking.service;

import java.text.ParseException;
import java.util.Optional;

import com.bank.retailbanking.dto.TransactionSummaryResponsedto;
import com.bank.retailbanking.exception.TransactionException;

/**
 * The {@code TransactionService} interface provides the methods for the
 * implimentation to the specific logic
 * 
 * @author maheswraraju
 */
public interface TransactionService {
	/*
	 * this method reads two parameters which are customerId and month from the controller
	 */
	Optional<TransactionSummaryResponsedto> fetchTransactionsByMonth(Long customerId, String month) throws ParseException, TransactionException;
}
