package gov.samhsa.acs.trypolicy.exception;

public class TryPolicyException extends Exception {

	private static final long serialVersionUID = -366645896954292980L;

	public TryPolicyException() {
		super();
	}

	public TryPolicyException(String message) {
		super(message);
	}

	public TryPolicyException(String message, Throwable cause) {
		super(message, cause);
	}

	public TryPolicyException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public TryPolicyException(Throwable cause) {
		super(cause);
	}
}
