package com.bank.retailbanking.service;

import java.util.Optional;

import com.bank.retailbanking.dto.LoginRequestdto;
import com.bank.retailbanking.dto.LoginResponsedto;
import com.bank.retailbanking.exception.GeneralException;


public interface LoginService {
	public Optional<LoginResponsedto> login(LoginRequestdto loginRequestdto) throws GeneralException;
}
