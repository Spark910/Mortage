package com.bank.retailbanking.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MortgageAccountSummaryResponsedto {
	
	private String message;
	private Integer statusCode;
	private List<MortgageAccountSummaryResponse> accountDetails;

}
