package com.bank.retailbanking.Service;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bank.retailbanking.dto.AccountList;
import com.bank.retailbanking.dto.ViewAccountResponseDto;
import com.bank.retailbanking.entity.Customer;
import com.bank.retailbanking.entity.CustomerAccountDetail;
import com.bank.retailbanking.exception.NoAccountListException;
import com.bank.retailbanking.repository.CustomerAccountDetailRepository;
import com.bank.retailbanking.repository.CustomerRepository;
import com.bank.retailbanking.service.AdminServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
public class AdminServiceTest {

	@InjectMocks
	AdminServiceImpl adminServiceImpl;

	@Mock
	CustomerAccountDetailRepository customerAccountDetailsRepository;

	@Mock
	CustomerRepository customerRepository;

	CustomerAccountDetail customerAccountDetail = null;
	ViewAccountResponseDto viewAccountResponseDto = null;
	Customer customer = null;
	ViewAccountResponseDto responseDto1 = null;

	List<CustomerAccountDetail> customerAccountList = null;

	@Before
	public void before() {
		customer = new Customer();
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

		customerAccountList = new ArrayList<>();
		customerAccountList.add(customerAccountDetail);

		viewAccountResponseDto = new ViewAccountResponseDto();
		viewAccountResponseDto.setAccountNumber(1L);
		viewAccountResponseDto.setAccountType("Savings");
		viewAccountResponseDto.setAge(3);
		viewAccountResponseDto.setCustomerId(1001L);
		viewAccountResponseDto.setCustomerName("bindu");
		viewAccountResponseDto.setGender("Female");

		responseDto1 = new ViewAccountResponseDto();

	}

	@Test
	public void testViewAccountDetailsForPositive() {
		Mockito.when(customerAccountDetailsRepository.findByAccountNumber(1L))
				.thenReturn(Optional.of(customerAccountDetail));
		Mockito.when(customerRepository.findByCustomerId(1L)).thenReturn(Optional.of(customer));
		ViewAccountResponseDto responseDto = adminServiceImpl.viewAccountDetails(1L);
		Assert.assertNotNull(responseDto);
	}

	@Test
	public void testViewAccountDetailsForNegativeAccountDetail() {
		Optional<CustomerAccountDetail> customerAccount = Optional.ofNullable(null);
		Mockito.when(customerAccountDetailsRepository.findByAccountNumber(1L)).thenReturn(customerAccount);
		ViewAccountResponseDto responseDto = adminServiceImpl.viewAccountDetails(1L);
		Assert.assertNotNull(responseDto);
	}

	@Test
	public void testGetAccountListPositive() throws NoAccountListException {
		Mockito.when(customerAccountDetailsRepository.findAllByAccountNumber("1L")).thenReturn(customerAccountList);
		List<AccountList> expected = adminServiceImpl.getAccountList("1L");
		assertEquals(customerAccountList.size(), expected.size());
	}

	@Test(expected = NoAccountListException.class)
	public void testGetAccountListNegative() throws NoAccountListException {
		Mockito.when(customerAccountDetailsRepository.findAllByAccountNumber("1L")).thenReturn(customerAccountList);
		List<AccountList> expected = adminServiceImpl.getAccountList("2L");
		Assert.assertNull(expected);
	}
}
