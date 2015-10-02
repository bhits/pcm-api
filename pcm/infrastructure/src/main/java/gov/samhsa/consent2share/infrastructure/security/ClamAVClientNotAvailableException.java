package gov.samhsa.consent2share.infrastructure.security;

public class ClamAVClientNotAvailableException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ClamAVClientNotAvailableException() {
		super();
	}

	public ClamAVClientNotAvailableException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ClamAVClientNotAvailableException(String message, Throwable cause) {
		super(message, cause);
	}

	public ClamAVClientNotAvailableException(String message) {
		super(message);
	}

	public ClamAVClientNotAvailableException(Throwable cause) {
		super(cause);
	}
}
