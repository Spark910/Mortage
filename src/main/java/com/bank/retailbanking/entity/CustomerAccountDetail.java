package com.bank.retailbanking.entity;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@SequenceGenerator(name = "accountNumberSequencer", initialValue = 100001, allocationSize = 1)

public class CustomerAccountDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "accountNumberSequencer")
	private Long accountNumber;
	private String accountType;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "customerId", nullable = false)
	private Customer customerId;

	private Double availableBalance;
	private LocalDate accountOpeningDate;
	private String password;

}