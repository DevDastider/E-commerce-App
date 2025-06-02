/**
 * 
 */
package com.sgd.ecommerce.exception;

/**
 *
 * @author Sayantan Ghosh Dastider
 *
 */
public abstract class ServiceException extends Exception {

	private int statusCode;

	/**
	 * @param msg
	 * @param ex
	 */
	public ServiceException(String msg, Exception ex) {
		super(msg, ex);
	}

	/**
	 * 
	 * @param statusCode
	 * @param msg
	 * @param ex
	 */
	public ServiceException(int statusCode, String msg, Exception ex) {
		super(msg, ex);
		this.statusCode = statusCode;
	}
	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	
}
