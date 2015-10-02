package gov.samhsa.acs.contexthandler.exception;

public class NoPolicyFoundException extends Exception {

	private static final long serialVersionUID = -8567264734499490602L;

	public NoPolicyFoundException() {
		super();
	}

	public NoPolicyFoundException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public NoPolicyFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoPolicyFoundException(String message) {
		super(message);
	}

	public NoPolicyFoundException(Throwable cause) {
		super(cause);
	}
}
