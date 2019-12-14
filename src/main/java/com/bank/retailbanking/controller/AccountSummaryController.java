package com.bank.retailbanking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.retailbanking.constants.ApplicationConstants;
import com.bank.retailbanking.dto.AccountSummaryResponsedto;
import com.bank.retailbanking.exception.GeneralException;
import com.bank.retailbanking.service.AccountSummaryService;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author Chethana M
 * @Description This class is used to perform all the customer account summary
 *              details related operations
 *
 */
@CrossOrigin(allowedHeaders = { "*", "*/" }, origins = { "*", "*/" })
@RestController
@RequestMapping("/customers")
@Slf4j
public class AccountSummaryController {

	@Autowired
	AccountSummaryService accountSummaryService;

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
	public ResponseEntity<AccountSummaryResponsedto> getAccountSummary(@PathVariable Long customerId) throws GeneralException {
		log.info("Entering into getAccountSummary method of LoginController");
		AccountSummaryResponsedto accountSummaryResponsedto = accountSummaryService.getAccountSummary(customerId);
		accountSummaryResponsedto.setMessage(ApplicationConstants.SUCCESS);
		accountSummaryResponsedto.setStatusCode(HttpStatus.OK.value());
		return new ResponseEntity<>(accountSummaryResponsedto, HttpStatus.OK);

	}
}
