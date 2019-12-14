package com.bank.retailbanking.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class CustomerProperty {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long customerPropertyId;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "customerId", nullable = false)
	private Customer customerId;

	private String propertyType;
	private String propertyLocation;
	private Double propertyValue;
}
