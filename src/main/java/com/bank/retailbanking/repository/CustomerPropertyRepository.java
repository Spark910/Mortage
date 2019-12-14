package com.bank.retailbanking.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bank.retailbanking.entity.Customer;
import com.bank.retailbanking.entity.CustomerProperty;

@Repository
public interface CustomerPropertyRepository extends JpaRepository<CustomerProperty, Long> {
Optional<CustomerProperty> findByCustomerId(Customer customerId); 
}
