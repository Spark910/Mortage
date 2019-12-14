package com.bank.retailbanking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bank.retailbanking.entity.CustomerProperty;

@Repository
public interface CustomerPropertyRepository extends JpaRepository<CustomerProperty, Long> {

}
