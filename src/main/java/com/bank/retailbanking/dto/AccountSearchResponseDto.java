package com.bank.retailbanking.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountSearchResponseDto {
	private List<AccountList> accountList;
	private String message;
	private Integer statusCode;
}
