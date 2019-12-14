package com.bank.retailbanking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.retailbanking.constants.ApplicationConstants;
import com.bank.retailbanking.dto.ViewAccountResponseDto;
import com.bank.retailbanking.service.AdminService;

import lombok.extern.slf4j.Slf4j;

@CrossOrigin(allowedHeaders = { "*", "*/" }, origins = { "*", "*/" })
@RestController
@RequestMapping("/admin")
@Slf4j
public class AdminController {
	
	@Autowired
	AdminService adminService;
	
	@GetMapping("/customer/{accountNumber}")
	public ResponseEntity<ViewAccountResponseDto> viewAccountDetails(@PathVariable Long accountNumber){
		log.info("Entering into viewAccountDetails method of controller" );
		ViewAccountResponseDto viewAccountResponseDto=adminService.viewAccountDetails(accountNumber);
		viewAccountResponseDto.setStatusCode(ApplicationConstants.SUCESS_STATUS_CODE);
		viewAccountResponseDto.setMessage(ApplicationConstants.SUCCESS);
		return new ResponseEntity<>(viewAccountResponseDto, HttpStatus.OK);
		
	}

}
