package com.bank.retailbanking.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bank.retailbanking.entity.Customer;
import com.bank.retailbanking.entity.CustomerAccountDetail;

@Repository
public interface CustomerAccountDetailsRepository extends JpaRepository<CustomerAccountDetail, Long> {

	Optional<CustomerAccountDetail> findByCustomerId(Customer customer);

	Optional<CustomerAccountDetail> findByAccountNumber(Long accountNumber);

	Optional<CustomerAccountDetail> findByCustomerIdAndPasswordAndAccountType(Customer customer, String password, String accountType);

}
