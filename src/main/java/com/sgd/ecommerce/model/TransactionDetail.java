/**
 * 
 */
package com.sgd.ecommerce.model;

/**
 *
 * @author Sayantan Ghosh Dastider
 *
 */
public class TransactionDetail {

	private String orderId;
	private Integer amount;
	private String currency;
	private String key;
	
	/**
	 * @param orderId
	 * @param amount
	 * @param currency
	 */
	public TransactionDetail(String orderId, Integer amount, String currency, String key) {
		this.orderId = orderId;
		this.amount = amount;
		this.currency = currency;
		this.key = key;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
}
