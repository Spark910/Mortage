package com.bank.retailbanking.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.retailbanking.constants.ApplicationConstants;
import com.bank.retailbanking.dto.AccountList;
import com.bank.retailbanking.entity.CustomerAccountDetail;
import com.bank.retailbanking.exception.NoAccountListException;
import com.bank.retailbanking.repository.CustomerAccountDetailsRepository;
import com.bank.retailbanking.repository.CustomerRepository;

@Service
public class AdminServiceImpl implements AdminService {
	@Autowired
	CustomerAccountDetailsRepository customerAccountDetailRepository;
	
	@Autowired
	CustomerRepository customerRepository;

	@Override
	public List<AccountList> getAccountList(String accountNumber) throws NoAccountListException {
		List<CustomerAccountDetail> customerAccountDetail = customerAccountDetailRepository
				.findAllByAccountNumber(accountNumber);
		if(customerAccountDetail.isEmpty()) {
			throw new NoAccountListException(ApplicationConstants.ACCOUNT_LIST_FAILURE_MESSAGE);
		}
		customerAccountDetail = customerAccountDetail.stream().filter(
				accountType -> (accountType.getAccountType().equalsIgnoreCase(ApplicationConstants.SAVING_ACCOUNT)))
				.collect(Collectors.toList());
		List<AccountList> accounts = new ArrayList<>();
		customerAccountDetail.forEach(customerAccountDetails -> {
			AccountList accountList = new AccountList();
			BeanUtils.copyProperties(customerAccountDetails, accountList);
			accountList.setCustomerId(customerAccountDetails.getCustomerId().getCustomerId());
			accounts.add(accountList);

		});
		return accounts;
	}
}
