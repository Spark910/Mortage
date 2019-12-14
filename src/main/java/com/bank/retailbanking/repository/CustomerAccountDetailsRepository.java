package com.bank.retailbanking.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bank.retailbanking.entity.Customer;
import com.bank.retailbanking.entity.CustomerAccountDetail;

@Repository
public interface CustomerAccountDetailsRepository extends JpaRepository<CustomerAccountDetail, Long> {

	Optional<CustomerAccountDetail> findByCustomerId(Customer customer);

	Optional<CustomerAccountDetail> findByAccountNumber(Long accountNumber);


	@Query("select u from CustomerAccountDetail u WHERE CAST(u.accountNumber AS string) LIKE %:accountNumber%")
	List<CustomerAccountDetail> findAllByAccountNumber(@Param("accountNumber") String accountNumber);

	Optional<CustomerAccountDetail> findByCustomerIdAndAccountType(Customer customer, String transactionPurpose);

	Optional<CustomerAccountDetail> findByAccountNumber(Customer customer);

	Optional<CustomerAccountDetail> findByCustomerIdAndAccountType(Optional<Customer> customer, String string);
	Optional<CustomerAccountDetail> findByCustomerIdAndPasswordAndAccountType(Customer customer, String password, String accountType);


}
