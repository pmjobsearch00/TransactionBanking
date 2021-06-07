/**
 * PainDTO for credit transfer and direct debit external API data-object.
 *
 * @author Partha Mondal
 */
package com.tenx.sample.dto;

public class PainDTO {

	private Long id;
	private Long sourceAccountId;
	private Long targetAccountId;
	private Double amount;
	private String currency;

	/**
	 * @param id
	 * @param sourceAccountId
	 * @param targetAccountId
	 * @param amount
	 * @param currency
	 */
	public PainDTO(Long id, Long sourceAccountId, Long targetAccountId, Double amount, String currency) {
		this.id = id;
		this.sourceAccountId = sourceAccountId;
		this.targetAccountId = targetAccountId;
		this.amount = amount;
		this.currency = currency;
	}

	/**
	 * @param none
	 */
	public PainDTO() {
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
	public String getCurrency() {
		return currency;
	}

	/**
	 * @param currency
	 *            the currency to set
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PainDTO [id=" + id + ", sourceAccountId=" + sourceAccountId + ", targetAccountId=" + targetAccountId
				+ ", amount=" + amount + ", currency=" + currency + "]";
	}

}
