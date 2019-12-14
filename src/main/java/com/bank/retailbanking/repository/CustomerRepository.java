package com.bank.retailbanking.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bank.retailbanking.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

	Optional<Customer> findByCustomerId(Long customerId);

	Optional<Customer> findByCustomerEmailAndMobile(String email, Long mobileNumber);
}
