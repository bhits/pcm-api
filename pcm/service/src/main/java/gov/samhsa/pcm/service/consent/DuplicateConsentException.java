package gov.samhsa.pcm.service.consent;

public class DuplicateConsentException extends Exception {

	private static final long serialVersionUID = -7280855339511262377L;
	
	/**
	 * Instantiates a new ConsentGenException exception.
	 *
	 * @param msg the msg
	 * @param t the t
	 */
	public DuplicateConsentException(String msg, Throwable t) {
        super(msg, t);
    }
	
	/**
	 * Instantiates a new ConsentGenException.
	 *
	 * @param msg the msg
	 */
	public DuplicateConsentException(String msg) {
        super(msg);
    }		

}
