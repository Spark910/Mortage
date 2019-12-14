package com.bank.retailbanking.service;

import com.bank.retailbanking.dto.AccountSummaryResponsedto;
import com.bank.retailbanking.exception.GeneralException;

public interface AccountSummaryService {
	public AccountSummaryResponsedto getAccountSummary(Long customerId) throws GeneralException;
}
