package com.bank.retailbanking.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginRequestdto {
	private Long customerId;
	private String password;
}
