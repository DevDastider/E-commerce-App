/**
 * 
 */
package com.sgd.ecommerce.exception;

/**
 *
 * @author Sayantan Ghosh Dastider
 *
 */
public class ServiceRuntimeException extends RuntimeException {

	/**
	 * @param string
	 * @param e
	 */
	public ServiceRuntimeException(String msg, Exception e) {
		super(msg, e);
	}

}
