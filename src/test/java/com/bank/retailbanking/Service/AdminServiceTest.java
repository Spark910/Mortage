package com.bank.retailbanking.Service;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bank.retailbanking.dto.ViewAccountResponseDto;
import com.bank.retailbanking.entity.Customer;
import com.bank.retailbanking.entity.CustomerAccountDetail;
import com.bank.retailbanking.repository.CustomerAccountDetailsRepository;
import com.bank.retailbanking.repository.CustomerRepository;
import com.bank.retailbanking.service.AdminServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
public class AdminServiceTest {

	@InjectMocks
	AdminServiceImpl adminServiceImpl;

	@Mock
	CustomerAccountDetailsRepository customerAccountDetailsRepository;

	@Mock
	CustomerRepository customerRepository;

	CustomerAccountDetail customerAccountDetail = null;
	ViewAccountResponseDto viewAccountResponseDto = null;
	Customer customer=null;
	ViewAccountResponseDto responseDto1=null;

	@Before
	public void before() {
		customer=new Customer();
		customer.setCustomerId(1001L);
		customer.setDateOfBirth(LocalDate.now());
		customer.setGender("Female");
		customer.setFirstName("Bindu");
		customer.setLastName("Shree");
		customer.setCustomerEmail("b@gmail.com");
		customer.setMobile(978687889L);
		
		customerAccountDetail = new CustomerAccountDetail();
		customerAccountDetail.setAccountNumber(1L);
		customerAccountDetail.setAccountType("Savings");
		customerAccountDetail.setCustomerId(customer);
		customerAccountDetail.setAccountOpeningDate(LocalDate.now());
		customerAccountDetail.setAvailableBalance(100.0);
		customerAccountDetail.setPassword("sdms");
		
		viewAccountResponseDto = new ViewAccountResponseDto();
		viewAccountResponseDto.setAccountNumber(1L);
		viewAccountResponseDto.setAccountType("Savings");
		viewAccountResponseDto.setAge(3);
		viewAccountResponseDto.setCustomerId(1001L);
		viewAccountResponseDto.setCustomerName("bindu");
		viewAccountResponseDto.setGender("Female");
		
		responseDto1=new ViewAccountResponseDto();
		
		
		
	}

	@Test
	public void testViewAccountDetailsForPositive() {
		Mockito.when(customerAccountDetailsRepository.findByAccountNumber(1L)).thenReturn(Optional.of(customerAccountDetail));
		Mockito.when(customerRepository.findByCustomerId(1L)).thenReturn(Optional.of(customer));
		ViewAccountResponseDto responseDto=adminServiceImpl.viewAccountDetails(1L);
		Assert.assertNotNull(responseDto);
	}
	
	@Test
	public void testViewAccountDetailsForNegativeAccountDetail() {
		Optional<CustomerAccountDetail> customerAccount = Optional.ofNullable(null);
		Mockito.when(customerAccountDetailsRepository.findByAccountNumber(1L)).thenReturn(customerAccount);
		ViewAccountResponseDto responseDto=adminServiceImpl.viewAccountDetails(1L);
		Assert.assertNotNull(responseDto);	}
}
