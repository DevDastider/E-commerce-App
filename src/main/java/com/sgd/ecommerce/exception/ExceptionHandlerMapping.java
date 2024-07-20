/**
 * 
 */
package com.sgd.ecommerce.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.sgd.ecommerce.model.ErrorResponse;

import jakarta.servlet.http.HttpServletRequest;

/**
 *
 * @author Sayantan Ghosh Dastider
 *
 */
@ControllerAdvice
public class ExceptionHandlerMapping {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlerMapping.class);

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleUserNotFound(final UserNotFoundException ex,
			final HttpServletRequest request) {
		LOGGER.error("Exception stack trace-> ");
		ex.printStackTrace();
		ErrorResponse response = new ErrorResponse();
		response.setErrorMessage(ex.getMessage());
		response.setUriPath(request.getRequestURI());
		return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getStatusCode()));
	}

	@ExceptionHandler(UsernameNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public @ResponseBody ErrorResponse handleUserNotFound(final UsernameNotFoundException ex,
			final HttpServletRequest request) {
		LOGGER.error("Exception stack trace-> ");
		ex.printStackTrace();
		ErrorResponse response = new ErrorResponse();
		response.setErrorMessage(ex.getMessage());
		response.setUriPath(request.getRequestURI());
		return response;
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public @ResponseBody ErrorResponse handleGeneralException(final Exception ex, final HttpServletRequest request) {
		LOGGER.error("Exception stack trace-> ");
		ex.printStackTrace();
		ErrorResponse response = new ErrorResponse();
		response.setErrorMessage(ex.getMessage());
		response.setUriPath(request.getRequestURI());

		return response;
	}

	@ExceptionHandler(ServiceRuntimeException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public @ResponseBody ErrorResponse handleRuntimeException(final ServiceRuntimeException ex,
			final HttpServletRequest request) {
		LOGGER.error("Exception stack trace-> ");
		ex.printStackTrace();
		ErrorResponse response = new ErrorResponse();
		response.setErrorMessage(ex.getMessage());
		response.setUriPath(request.getRequestURI());

		return response;
	}

	@ExceptionHandler(GeneralServiceException.class)
	public ResponseEntity<ErrorResponse> handleGeneralServiceException(final GeneralServiceException ex,
			final HttpServletRequest request) {
		LOGGER.error("Exception stack trace-> ");
		ex.printStackTrace();
		ErrorResponse response = new ErrorResponse();
		response.setErrorMessage(ex.getMessage());
		response.setUriPath(request.getRequestURI());

		return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getStatusCode()));
	}
}
