package com.bank.retailbanking.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AccountList {
	private Long accountNumber;
	private String accountType;
	private Double availableBalance;
	private LocalDate accountOpeningDate;

	private Long customerId;
}
