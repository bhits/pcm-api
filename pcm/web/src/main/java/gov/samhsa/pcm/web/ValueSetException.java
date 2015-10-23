package gov.samhsa.pcm.web;

public class ValueSetException extends Exception {
	
	private static final long serialVersionUID = 3917484621889686382L;
	/**
	 * Instantiates a new patient not found exception.
	 *
	 * @param msg the msg
	 * @param t the t
	 */
	public ValueSetException(String msg, Throwable t) {
        super(msg, t);
    }
	
	/**
	 * Instantiates a new patient not found exception.
	 *
	 * @param msg the msg
	 */
	public ValueSetException(String msg) {
        super(msg);
    }	
	
}
