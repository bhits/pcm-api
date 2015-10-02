package gov.samhsa.consent2share.service.report.exception;

public class SqlScriptFileException extends RuntimeException {

	private static final long serialVersionUID = -7643751788564017608L;

	public SqlScriptFileException() {
		super();
	}

	public SqlScriptFileException(String message) {
		super(message);
	}

	public SqlScriptFileException(String message, Throwable cause) {
		super(message, cause);
	}

	public SqlScriptFileException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public SqlScriptFileException(Throwable cause) {
		super(cause);
	}
}
