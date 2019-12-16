package com.bank.retailbanking.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MortgageAccountSummaryResponse {
	
	private Long accountNumber;
	private Double accountBalance;
	private String accountType;
	//private LocalDate lastTransactionDate;

}
