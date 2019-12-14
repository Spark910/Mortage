package com.bank.retailbanking.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TransactionSummaryResponsedto {
	Integer statusCode;
	String statusMessage;
	private List<AccountSummaryResponse> transactions;
}
