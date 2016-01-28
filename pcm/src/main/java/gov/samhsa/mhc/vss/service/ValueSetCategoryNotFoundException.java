package gov.samhsa.mhc.vss.service;

/**
 * The Class ValueSetCategoryNotFoundException.
 */
public class ValueSetCategoryNotFoundException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 5122534788895239791L;

	/**
	 * Instantiates a new value set category not found exception.
	 */
	public ValueSetCategoryNotFoundException() {
		super();
	}
	
	/**
	 * Instantiates a new value set category not found exception.
	 *
	 * @param message the message
	 */
	public ValueSetCategoryNotFoundException(String message) {
		super(message);
	}
}
