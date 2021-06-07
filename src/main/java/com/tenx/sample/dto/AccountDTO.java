/**
 * AccountDTO for account external API data-object.
 *
 * @author Partha Mondal
 */

package com.tenx.sample.dto;

import java.util.Date;

public class AccountDTO {
	
	private Long id;
	private Double balance;
	private String currency;
	private Date createdAt;
	
	/**
	 * @param id
	 * @param balance
	 * @param currency
	 * @param createAt
	 */
	public AccountDTO(Long id, Double balance, String currency, Date createdAt) {
		this.id = id;
		this.balance = balance;
		this.currency = currency;
		this.createdAt = createdAt;
	}
	
	/**
	 * @param none
	 */
	public AccountDTO() {
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the balance
	 */
	public Double getBalance() {
		return balance;
	}

	/**
	 * @param balance the balance to set
	 */
	public void setBalance(Double balance) {
		this.balance = balance;
	}

	/**
	 * @return the currency
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * @param currency the currency to set
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	/**
	 * @return the createdAt
	 */
	public Date getCreatedAt() {
		return createdAt;
	}

	/**
	 * @param createAt the createAt to set
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AccountDTO [id=" + id + ", balance=" + balance + ", currency=" + currency + ", createAt=" + createdAt
				+ "]";
	}
	

}
