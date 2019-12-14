package com.bank.retailbanking.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FundTransferRequestDto {
	private Long customerId;
	private Long creditAccount;
	private Double amount;
	private String transactionPurpose;
}
