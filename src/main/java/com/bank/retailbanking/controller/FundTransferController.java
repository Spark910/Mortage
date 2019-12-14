package com.bank.retailbanking.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.retailbanking.constants.ApplicationConstants;
import com.bank.retailbanking.dto.FundTransferRequestDto;
import com.bank.retailbanking.dto.FundTransferResponseDto;
import com.bank.retailbanking.exception.AmountInvalidException;
import com.bank.retailbanking.exception.CustomerNotFoundException;
import com.bank.retailbanking.exception.SameAccountNumberException;
import com.bank.retailbanking.service.FundTransferService;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author Muthu
 * @Description Controller enables the customer to transfer funds from his
 *              account to the account present in that particular branch
 *
 */

@RestController
@RequestMapping("/fundtransfer")
@CrossOrigin(allowedHeaders = { "*", "/" }, origins = { "*", "/" })
@Slf4j
public class FundTransferController {
	@Autowired
	FundTransferService fundTransferService;

	/**
	 * 
	 * @param fundTransferRequestDto
	 * @return
	 * @throws FundTransferFailureException
	 * @throws CustomerNotFoundException
	 * @throws AmountInvalidException
	 * @throws SameAccountNumberException
	 */

	@PostMapping
	public ResponseEntity<Optional<FundTransferResponseDto>> fundTransfer(
			@RequestBody FundTransferRequestDto fundTransferRequestDto)
			throws CustomerNotFoundException, AmountInvalidException, SameAccountNumberException {
		Optional<FundTransferResponseDto> response = fundTransferService.fundTransfer(fundTransferRequestDto);
		if (response.isPresent()) {
			log.info("Transaction is successfull");
			response.get().setStatusCode(ApplicationConstants.FUND_TRANSFER_SUCCESS_CODE);
			response.get().setStatusMessage(ApplicationConstants.FUND_TRANSFER_SUCCESS_MESSAGE);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		log.info("Transaction failed");
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}

}
