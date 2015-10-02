package gov.samhsa.acs.trypolicy.wsclient;

public class TryPolicyWebServiceClientException extends RuntimeException {

	private static final long serialVersionUID = -486936610924590414L;

	public TryPolicyWebServiceClientException() {
		super();
	}

	public TryPolicyWebServiceClientException(String message) {
		super(message);
	}

	public TryPolicyWebServiceClientException(String message, Throwable cause) {
		super(message, cause);
	}

	public TryPolicyWebServiceClientException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public TryPolicyWebServiceClientException(Throwable cause) {
		super(cause);
	}
}
