package com.bank.retailbanking.service;

import com.bank.retailbanking.dto.ViewAccountResponseDto;

public interface AdminService {
	
	ViewAccountResponseDto viewAccountDetails(Long accountNumber);

}
