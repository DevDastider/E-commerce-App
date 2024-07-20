/**
 * 
 */
package com.sgd.ecommerce.exception;

import org.springframework.security.authentication.BadCredentialsException;

/**
 *
 * @author Sayantan Ghosh Dastider
 *
 */
public class UserNotFoundException extends ServiceException {

	public UserNotFoundException(int statusCode, String message, Exception ex) {
		super(statusCode, message, ex);
	}

	/**
	 * @param string
	 * @param ex
	 */
	public UserNotFoundException(String msg, Exception ex) {
		super(msg, ex);
		super.setStatusCode(404);
	}
}
