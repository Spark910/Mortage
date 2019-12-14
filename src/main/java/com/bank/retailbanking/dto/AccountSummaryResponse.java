package com.bank.retailbanking.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AccountSummaryResponse {

	private String transactionType;
	private double transactionAmount;
	private LocalDate transactionDate;
	private String transactionComments;
	private String transactionStatus;
}
