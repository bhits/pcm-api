package gov.samhsa.mhc.common.util;

public class UniqueValueGeneratorException extends RuntimeException {

	private static final long serialVersionUID = -4376077150096446144L;

	public UniqueValueGeneratorException() {
		super();
	}

	public UniqueValueGeneratorException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public UniqueValueGeneratorException(String message, Throwable cause) {
		super(message, cause);
	}

	public UniqueValueGeneratorException(String message) {
		super(message);
	}

	public UniqueValueGeneratorException(Throwable cause) {
		super(cause);
	}
}
