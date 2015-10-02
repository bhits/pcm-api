package gov.samhsa.consent2share.web;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = org.springframework.http.HttpStatus.NOT_FOUND)
public final class ResourceNotFoundException extends RuntimeException {

	/**
	 * Default Constructor
	 */
	public ResourceNotFoundException() {
		super();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}