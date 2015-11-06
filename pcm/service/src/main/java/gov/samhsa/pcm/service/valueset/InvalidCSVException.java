package gov.samhsa.pcm.service.valueset;

/**
 * The Class InvalidCSVException.
 */
public class InvalidCSVException extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new invalid csv exception.
	 *
	 * @param message the message
	 */
	public InvalidCSVException(String message) {
		super(message);
	}
	
	/**
	 * Instantiates a new invalid csv exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 */
	public InvalidCSVException(String message, Throwable cause) {
		super(message, cause);
	}
}
