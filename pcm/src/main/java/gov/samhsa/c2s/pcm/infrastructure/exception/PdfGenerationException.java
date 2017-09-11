package gov.samhsa.c2s.pcm.infrastructure.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class PdfGenerationException extends RuntimeException {

	public PdfGenerationException() {
	}

	public PdfGenerationException(String message) {
		super(message);
	}

	public PdfGenerationException(String message, Throwable cause) {
		super(message, cause);
	}

	public PdfGenerationException(Throwable cause) {
		super(cause);
	}

	public PdfGenerationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
