package gov.samhsa.spirit.wsclient.exception;

public class SpiritAdapterException extends Exception {

	private static final long serialVersionUID = 8464994942221238008L;
	
	public SpiritAdapterException() {
		super();		
	}

	public SpiritAdapterException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);		
	}

	public SpiritAdapterException(String message, Throwable cause) {
		super(message, cause);		
	}

	public SpiritAdapterException(String message) {
		super(message);		
	}

	public SpiritAdapterException(Throwable cause) {
		super(cause);		
	}	

}
