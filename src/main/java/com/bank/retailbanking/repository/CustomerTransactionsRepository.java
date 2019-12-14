package com.bank.retailbanking.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bank.retailbanking.entity.CustomerAccountDetails;
import com.bank.retailbanking.entity.CustomerTransactions;

@Repository
public interface CustomerTransactionsRepository extends JpaRepository<CustomerTransactions, Long> {
List<CustomerTransactions> findByAccountNumber(CustomerAccountDetails accountNumber);

List<CustomerTransactions> findByAccountNumber(CustomerAccountDetails customerAccountDetails, Pageable pageable);
}
