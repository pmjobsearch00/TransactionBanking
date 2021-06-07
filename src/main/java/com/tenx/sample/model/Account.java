/*
 * Copyright to the original author.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.tenx.sample.model;

import java.util.Currency;
import java.util.Date;

public class Account {

	private Long id;
	private Double balance;
	private Currency currency;
	private Date createdAt;

	/**
	 * @param id
	 * @param balance
	 * @param currency
	 * @param createAt
	 */
	public Account(Long id, Double balance, Currency currency, Date createdAt) {
		this.id = id;
		this.balance = balance;
		this.currency = currency;
		this.createdAt = createdAt;
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
	 * @return the balance
	 */
	public Double getBalance() {
		return balance;
	}

	/**
	 * @param balance
	 *            the balance to set
	 */
	public void setBalance(Double balance) {
		this.balance = balance;
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

	/**
	 * @return the createAt
	 */
	public Date getCreatedAt() {
		return createdAt;
	}

	/**
	 * @param createAt
	 *            the createAt to set
	 */
	public void setCreateAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Account [id=" + id + ", balance=" + balance + ", currency=" + currency + ", createAt=" + createdAt + "]";
	}

}
