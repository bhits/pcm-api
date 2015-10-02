package gov.samhsa.acs.contexthandler.exception;

public class PolicyProviderException extends Exception {

	private static final long serialVersionUID = -6047379793823059499L;

	public PolicyProviderException() {
		super();
	}

	public PolicyProviderException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public PolicyProviderException(String message, Throwable cause) {
		super(message, cause);
	}

	public PolicyProviderException(String message) {
		super(message);
	}

	public PolicyProviderException(Throwable cause) {
		super(cause);
	}
}
