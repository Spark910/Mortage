package com.bank.retailbanking.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FundTransferResponseDto {
	private String statusMessage;
	private Integer statusCode;
}
