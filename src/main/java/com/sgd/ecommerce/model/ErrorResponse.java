/**
 * 
 */
package com.sgd.ecommerce.model;

/**
 *
 * @author Sayantan Ghosh Dastider
 *
 */
public class ErrorResponse {

	private String errorMessage;
	private String uriPath;
	
	public String getErrorMessage() {
		return errorMessage;
	}
	
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	public String getUriPath() {
		return uriPath;
	}
	
	public void setUriPath(String uriPath) {
		this.uriPath = uriPath;
	}
}
