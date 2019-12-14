package com.bank.retailbanking.service;

import java.text.ParseException;
import java.util.Optional;

import com.bank.retailbanking.dto.AccountSummaryResponsedto;
import com.bank.retailbanking.dto.FundTransferRequestDto;
import com.bank.retailbanking.dto.FundTransferResponseDto;
import com.bank.retailbanking.dto.MortgageAccountSummaryResponsedto;
import com.bank.retailbanking.dto.TransactionSummaryResponsedto;
import com.bank.retailbanking.exception.AmountInvalidException;
import com.bank.retailbanking.exception.CustomerNotFoundException;
import com.bank.retailbanking.exception.GeneralException;
import com.bank.retailbanking.exception.SameAccountNumberException;
import com.bank.retailbanking.exception.TransactionException;

/**
 * The {@code TransactionService} interface provides the methods for the
 * implimentation to the specific logic
 * 
 * @author maheswraraju
 */
public interface TransactionService {
	
	Optional<TransactionSummaryResponsedto> fetchTransactionsByMonth(Long customerId, String month) throws ParseException, TransactionException;
	Optional<FundTransferResponseDto> fundTransfer(FundTransferRequestDto fundTransferRequestDto)
			throws CustomerNotFoundException, AmountInvalidException, SameAccountNumberException;
	 MortgageAccountSummaryResponsedto getAccountSummary(Long customerId) throws GeneralException;
	AccountSummaryResponsedto getAccountSummarys(Long customerId) throws GeneralException;
	
}
