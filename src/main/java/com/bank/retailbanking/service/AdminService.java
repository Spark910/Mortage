package com.bank.retailbanking.service;


import java.util.List;

import com.bank.retailbanking.dto.AccountList;
import com.bank.retailbanking.dto.ViewAccountResponseDto;
import com.bank.retailbanking.exception.NoAccountListException;



public interface AdminService {
	
	ViewAccountResponseDto viewAccountDetails(Long accountNumber);
	List<AccountList> getAccountList(String accountNumber) throws NoAccountListException;

}
