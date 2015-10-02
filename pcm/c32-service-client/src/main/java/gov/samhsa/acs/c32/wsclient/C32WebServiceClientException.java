package gov.samhsa.acs.c32.wsclient;

public class C32WebServiceClientException extends RuntimeException {

	private static final long serialVersionUID = -5156295332159049651L;

	public C32WebServiceClientException() {
		super();
	}

	public C32WebServiceClientException(String message) {
		super(message);
	}

	public C32WebServiceClientException(String message, Throwable cause) {
		super(message, cause);
	}

	public C32WebServiceClientException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public C32WebServiceClientException(Throwable cause) {
		super(cause);
	}
}
