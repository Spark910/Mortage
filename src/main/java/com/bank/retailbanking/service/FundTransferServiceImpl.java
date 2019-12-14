package com.bank.retailbanking.service;

import java.time.LocalDate;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.retailbanking.constants.ApplicationConstants;
import com.bank.retailbanking.dto.FundTransferRequestDto;
import com.bank.retailbanking.dto.FundTransferResponseDto;
import com.bank.retailbanking.entity.Customer;
import com.bank.retailbanking.entity.CustomerAccountDetails;
import com.bank.retailbanking.entity.CustomerTransactions;
import com.bank.retailbanking.exception.AmountInvalidException;
import com.bank.retailbanking.exception.CustomerNotFoundException;
import com.bank.retailbanking.exception.SameAccountNumberException;
import com.bank.retailbanking.repository.CustomerAccountDetailsRepository;
import com.bank.retailbanking.repository.CustomerRepository;
import com.bank.retailbanking.repository.CustomerTransactionsRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author Muthu This service enables for transactions after verifying the
 *         accounts and sufficient balance
 *
 */

@Service
@Transactional
@Slf4j
public class FundTransferServiceImpl implements FundTransferService {
	@Autowired
	CustomerTransactionsRepository customerTransactionsRepository;

	@Autowired
	CustomerAccountDetailsRepository customerAccountDetailsRepository;

	@Autowired
	CustomerRepository customerRepository;

	@Override
	public Optional<FundTransferResponseDto> fundTransfer(FundTransferRequestDto fundTransferRequestDto)
			throws CustomerNotFoundException, AmountInvalidException, SameAccountNumberException {
		Optional<Customer> customerDetails = customerRepository
				.findByCustomerId(fundTransferRequestDto.getCustomerId());

		if (customerDetails.isPresent()) {

			Optional<CustomerAccountDetails> customerAccountDetails = customerAccountDetailsRepository
					.findByCustomerId(customerDetails.get());
			Optional<CustomerAccountDetails> creditAccountDetails = customerAccountDetailsRepository
					.findByAccountNumber(fundTransferRequestDto.getCreditAccount());
			FundTransferResponseDto fundTransferResponseDto = new FundTransferResponseDto();
			if (customerAccountDetails.isPresent() && creditAccountDetails.isPresent()) {
				if (customerAccountDetails.get().getAccountNumber() != creditAccountDetails.get().getAccountNumber()) {
					Double amount = customerAccountDetails.get().getAvailableBalance();
					Double creditedAmount = creditAccountDetails.get().getAvailableBalance();
					Double transferAmount = fundTransferRequestDto.getAmount();
					Double balance = amount - transferAmount;
					if (transferAmount < amount && balance > ApplicationConstants.OPENING_BALANCE) {
						/*
						 * @Description Gets stored as debit action
						 */
						CustomerTransactions customerTransactions = new CustomerTransactions();
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
						 * @Description Get stored as credit action
						 */
						CustomerTransactions customerCreditTransactions = new CustomerTransactions();
						customerCreditTransactions.setAccountNumber(creditAccountDetails.get());
						customerCreditTransactions.setTransactionAmount(fundTransferRequestDto.getAmount());
						customerCreditTransactions
								.setTransactionComments(customerTransactions.getTransactionComments());
						customerCreditTransactions.setTransactionDate(customerTransactions.getTransactionDate());
						customerCreditTransactions
								.setTransactionStatus(ApplicationConstants.TRANSACTION_SUCCESS_MESSAGE);
						customerCreditTransactions.setTransactionType(ApplicationConstants.TRANSACTION_CREDIT_MESSAGE);
						Double finalBalance = creditedAmount + transferAmount;
						creditAccountDetails.get().setAvailableBalance(finalBalance);
						customerAccountDetailsRepository.save(creditAccountDetails.get());
						customerTransactionsRepository.save(customerCreditTransactions);
						return Optional.of(fundTransferResponseDto);
					}
					/*
					 * @Description Gets executed when the transaction amount is more than the
					 * balance amount or less than minimum balance
					 */
					CustomerTransactions customerTransactions = new CustomerTransactions();
					customerTransactions.setAccountNumber(customerAccountDetails.get());
					customerTransactions.setTransactionAmount(fundTransferRequestDto.getAmount());
					customerTransactions.setTransactionComments(fundTransferRequestDto.getTransactionPurpose());
					customerTransactions.setTransactionDate(LocalDate.now());
					customerTransactions.setTransactionStatus(ApplicationConstants.TRANSACTION_FAILURE_MESSAGE);
					customerTransactions.setTransactionType(ApplicationConstants.TRANSACTION_DEBIT_MESSAGE);
					customerTransactionsRepository.save(customerTransactions);
					log.error(ApplicationConstants.AMOUNT_LESSBALANCE_MESSAGE);
					throw new AmountInvalidException(ApplicationConstants.AMOUNT_LESSBALANCE_MESSAGE);
				}
				log.error(ApplicationConstants.ACCOUNTNUMBER_INVALID_MESSAGE);
				throw new SameAccountNumberException(ApplicationConstants.ACCOUNTNUMBER_INVALID_MESSAGE);
			}
			log.error(ApplicationConstants.CUSTOMER_NOT_FOUND_MESSAGE);
			throw new CustomerNotFoundException(ApplicationConstants.CUSTOMER_NOT_FOUND_MESSAGE);
		}
		log.error(ApplicationConstants.CUSTOMER_NOT_FOUND_MESSAGE);
		throw new CustomerNotFoundException(ApplicationConstants.CUSTOMER_NOT_FOUND_MESSAGE);
	}

}
