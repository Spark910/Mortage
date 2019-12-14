package com.bank.retailbanking.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class SingleTon {
	private static BCryptPasswordEncoder bCryptPasswordEncoder = null;

	private SingleTon() {
	}

	public static BCryptPasswordEncoder bCryptPasswordEncoder() {
		if (bCryptPasswordEncoder == null) {
			bCryptPasswordEncoder = new BCryptPasswordEncoder();
		}
		return bCryptPasswordEncoder;
	}

}
