/**
 * 
 */
package com.sgd.ecommerce.exception;

/**
 *
 * @author Sayantan Ghosh Dastider
 *
 */
public class GeneralServiceException extends ServiceException {

	/**
	 * @param i
	 * @param string
	 * @param e
	 */
	public GeneralServiceException(int statusCode, String msg, Exception ex) {
		super(msg, ex);
		this.setStatusCode(statusCode);
	}

}
