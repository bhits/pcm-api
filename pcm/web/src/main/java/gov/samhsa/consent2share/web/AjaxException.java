package gov.samhsa.consent2share.web;

import org.springframework.http.HttpStatus;

public final class AjaxException extends RuntimeException {

	/**
	 * The HTTP Status Code To Be Returned
	 */
	private HttpStatus httpStatus;

	/**
	 * The error message text, if any, to be returned
	 */
	private String errorMessage;

	/**
	 * Constructor passing in an HttpStatus
	 * 
	 * @param httpStatus
	 */
	public AjaxException(HttpStatus httpStatus) {
		super();
		this.httpStatus = httpStatus;
		this.setErrorMessage(null);
	}

	/**
	 * Constructor passing in an HttpStatus & a message
	 * 
	 * @param httpStatus
	 * @param errorMessage
	 */
	public AjaxException(HttpStatus httpStatus, String errorMessage) {
		super();
		this.httpStatus = httpStatus;
		this.setErrorMessage(errorMessage);
	}

	/**
	 * @return the httpStatus
	 */
	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	/**
	 * @param httpStatus
	 *            the httpStatus to set
	 */
	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * @param errorMessage
	 *            the errorMessage to set
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	/**
	 * Constant serial version ID
	 */
	private static final long serialVersionUID = 1L;

}