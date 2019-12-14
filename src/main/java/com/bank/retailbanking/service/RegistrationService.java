package com.bank.retailbanking.service;

import com.bank.retailbanking.dto.RegistrationRequestDto;
import com.bank.retailbanking.dto.RegistrationResponseDto;
import com.bank.retailbanking.exception.AgeException;
import com.bank.retailbanking.exception.GeneralException;
import com.bank.retailbanking.exception.InvalidRegistrationException;

/**
 *
 * @author Bindushree
 *
 */
public interface RegistrationService {

/**
*
* @param registrationRequestDto
* @return
* @throws AgeException
* @throws InvalidRegistrationException
* @throws GeneralException
*/

RegistrationResponseDto registerCustomer(RegistrationRequestDto registrationRequestDto)
throws AgeException, InvalidRegistrationException, GeneralException;

}

