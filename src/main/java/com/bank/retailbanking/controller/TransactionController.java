package com.bank.retailbanking.controller;

import java.text.ParseException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bank.retailbanking.constants.ApplicationConstants;
import com.bank.retailbanking.dto.AccountSummaryResponsedto;
import com.bank.retailbanking.dto.FundTransferRequestDto;
import com.bank.retailbanking.dto.FundTransferResponseDto;
import com.bank.retailbanking.dto.TransactionSummaryResponsedto;
import com.bank.retailbanking.exception.AmountInvalidException;
import com.bank.retailbanking.exception.CustomerNotFoundException;
import com.bank.retailbanking.exception.GeneralException;
import com.bank.retailbanking.exception.MortgageException;
import com.bank.retailbanking.exception.SameAccountNumberException;
import com.bank.retailbanking.exception.TransactionException;
import com.bank.retailbanking.service.TransactionService;

import lombok.extern.slf4j.Slf4j;

/**
 * The {@code TransactionController} class responsible for diplaying all the
 * transactions which are fetched by the resprected layers from the db
 * 
 * @author maheswraraju
 * @since JDK1.8
 */

@CrossOrigin(allowedHeaders = { "*", "*/" }, origins = { "*", "*/" })
@RestController
@RequestMapping("/transactions")
@Slf4j
public class TransactionController {
	@Autowired
	TransactionService transactionService;

	/**
	 * 
	 * @author Muthu
	 * 
	 * @param fundTransferRequestDto
	 * @return
	 * @throws FundTransferFailureException
	 * @throws CustomerNotFoundException
	 * @throws AmountInvalidException
	 * @throws SameAccountNumberException
	 * @throws MortgageException 
	 */

	@PostMapping
	public ResponseEntity<Optional<FundTransferResponseDto>> fundTransfer(
			@RequestBody FundTransferRequestDto fundTransferRequestDto)
			throws CustomerNotFoundException, AmountInvalidException, SameAccountNumberException, MortgageException {
		Optional<FundTransferResponseDto> response = transactionService.fundTransfer(fundTransferRequestDto);
		if (response.isPresent()) {
			log.info("Transaction is successfull");
			response.get().setStatusCode(ApplicationConstants.FUND_TRANSFER_SUCCESS_CODE);
			response.get().setStatusMessage(ApplicationConstants.FUND_TRANSFER_SUCCESS_MESSAGE);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		log.info("Transaction failed");
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}

	/*
	 * this method is responsible for calling the service layer and in return it
	 * fetches AccountsSummaryObject. finally it displays the transactions data with
	 * the respective status codes
	 */

	@GetMapping("{customerId}/{month}")
	public ResponseEntity<Optional<TransactionSummaryResponsedto>> fetchTransactionsByMonth(
			@RequestParam("customerId") Long customerId, @RequestParam("month") String month)
			throws ParseException, TransactionException {
		log.info("fetch fetchTransactionsByMonth() is called");
		Optional<TransactionSummaryResponsedto> accountSummaryResponsedtoList = transactionService
				.fetchTransactionsByMonth(customerId, month);
		if (accountSummaryResponsedtoList.isPresent()) {
			accountSummaryResponsedtoList.get().setStatusCode(HttpStatus.OK.value());
			accountSummaryResponsedtoList.get().setStatusMessage("Success");
			return new ResponseEntity<>(accountSummaryResponsedtoList, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	/**
	 * @author Chethana
	 * @Description This method is used to get the customer account summary which
	 *              includes account details with latest 5 transactions(If
	 *              available)
	 * @param customerId Eg:{1001}
	 * @return { "message": "Success", "statusCode": 200, "accountNumber": 100001,
	 *         "accountBalance": 3000, "transactions": [ { "transactionType":
	 *         "debit", "transactionAmount": 100, "transactionDate": "2019-12-03",
	 *         "transactionComments": "a", "transactionStatus": "success" }}}
	 * @throws GeneralException
	 */

	@GetMapping("/{customerId}")
	public ResponseEntity<AccountSummaryResponsedto> getAccountSummary(@PathVariable Long customerId)
			throws GeneralException {
		log.info("Entering into getAccountSummary method of LoginController");
		AccountSummaryResponsedto accountSummaryResponsedto = transactionService.getAccountSummary(customerId);
		accountSummaryResponsedto.setMessage(ApplicationConstants.SUCCESS);
		accountSummaryResponsedto.setStatusCode(HttpStatus.OK.value());
		return new ResponseEntity<>(accountSummaryResponsedto, HttpStatus.OK);

	}

}
