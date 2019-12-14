package com.bank.retailbanking.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.retailbanking.constants.ApplicationConstants;
import com.bank.retailbanking.dto.LoginRequestdto;
import com.bank.retailbanking.dto.LoginResponsedto;
import com.bank.retailbanking.exception.GeneralException;
import com.bank.retailbanking.service.LoginService;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author Chethana M
 * @Description This class is used to perform all the customer related
 *              authentication operations
 *
 */
@CrossOrigin(allowedHeaders = { "*", "*/" }, origins = { "*", "*/" })
@RestController
@RequestMapping("/customers")
@Slf4j
public class LoginController {
	@Autowired
	LoginService loginService;

	/**
	 * @author Chethana
	 * @Description This Method is used to login the customer into the retail
	 *              banking application.
	 * @param loginRequestdto Eg:{ "customerMail": "c@gmail.com", "pass": "c" }
	 * @return LoginResponsedto Eg:{ "message": "Success", "statusCode": 200,
	 *         "customerId": 1001 } on success
	 * @throws GeneralException on Failure
	 * 
	 */
	@PostMapping
	public ResponseEntity<Optional<LoginResponsedto>> login(@RequestBody LoginRequestdto loginRequestdto)
			throws GeneralException {
		log.info("Entering into login method of LoginController");
		Optional<LoginResponsedto> loginResponsedto = loginService.login(loginRequestdto);
		if (!loginResponsedto.isPresent()) {
			throw new GeneralException(ApplicationConstants.LOGIN_ERROR);
		}
		loginResponsedto.get().setMessage(ApplicationConstants.SUCCESS);
		loginResponsedto.get().setStatusCode(HttpStatus.OK.value());
		return new ResponseEntity<>(loginResponsedto, HttpStatus.OK);
	}
}
