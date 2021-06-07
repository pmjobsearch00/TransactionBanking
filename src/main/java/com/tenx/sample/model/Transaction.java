package com.tenx.sample.model;

import java.util.Currency;

public class Transaction {

	private Long id;
	private Long sourceAccountId;
	private Long targetAccountId;
	private Double amount;
	private Currency currency;

	/**
	 * @param id
	 * @param sourceAccountId
	 * @param targetAccountId
	 * @param amount
	 * @param currency
	 */
	public Transaction(Long id, Long sourceAccountId, Long targetAccountId, Double amount, Currency currency) {
		this.id = id;
		this.sourceAccountId = sourceAccountId;
		this.targetAccountId = targetAccountId;
		this.amount = amount;
		this.currency = currency;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the sourceAccountId
	 */
	public Long getSourceAccountId() {
		return sourceAccountId;
	}

	/**
	 * @param sourceAccountId
	 *            the sourceAccountId to set
	 */
	public void setSourceAccountId(Long sourceAccountId) {
		this.sourceAccountId = sourceAccountId;
	}

	/**
	 * @return the targetAccountId
	 */
	public Long getTargetAccountId() {
		return targetAccountId;
	}

	/**
	 * @param targetAccountId
	 *            the targetAccountId to set
	 */
	public void setTargetAccountId(Long targetAccountId) {
		this.targetAccountId = targetAccountId;
	}

	/**
	 * @return the amount
	 */
	public Double getAmount() {
		return amount;
	}

	/**
	 * @param amount
	 *            the amount to set
	 */
	public void setAmount(Double amount) {
		this.amount = amount;
	}

	/**
	 * @return the currency
	 */
	public Currency getCurrency() {
		return currency;
	}

	/**
	 * @param currency
	 *            the currency to set
	 */
	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Transaction [id=" + id + ", sourceAccountId=" + sourceAccountId + ", targetAccountId=" + targetAccountId
				+ ", amount=" + amount + ", currency=" + currency + "]";
	}

}
