package gov.samhsa.mhc.vss.service;

/**
 * The Class ValueSetNotFoundException.
 */
public class ValueSetNotFoundException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 665469313254307065L;
	
	/**
	 * Instantiates a new value set not found exception.
	 *
	 * @param msg the msg
	 */
	public ValueSetNotFoundException(String msg) {
        super(msg);
    }
	
	/**
	 * Instantiates a new value set not found exception.
	 */
	public ValueSetNotFoundException() {
        super();
    }
}
