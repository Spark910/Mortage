package com.bank.retailbanking.service;

import java.util.Optional;

import com.bank.retailbanking.dto.FundTransferRequestDto;
import com.bank.retailbanking.dto.FundTransferResponseDto;
import com.bank.retailbanking.exception.AmountInvalidException;
import com.bank.retailbanking.exception.CustomerNotFoundException;
import com.bank.retailbanking.exception.SameAccountNumberException;

public interface FundTransferService {

	/**
	 * 
	 * @param fundTransferRequestDto
	 * @return
	 * @throws CustomerNotFoundException
	 * @throws AmountInvalidException
	 * @throws SameAccountNumberException
	 */

	Optional<FundTransferResponseDto> fundTransfer(FundTransferRequestDto fundTransferRequestDto)
			throws CustomerNotFoundException, AmountInvalidException, SameAccountNumberException;

}
