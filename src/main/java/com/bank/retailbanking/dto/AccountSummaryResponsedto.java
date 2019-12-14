package com.bank.retailbanking.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AccountSummaryResponsedto {
	private String message;
	private Integer statusCode;
	private Long accountNumber;
	private Double accountBalance;
	private List<AccountSummaryResponse> transactions;
}
