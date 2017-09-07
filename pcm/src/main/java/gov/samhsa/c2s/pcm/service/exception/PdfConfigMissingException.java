package gov.samhsa.c2s.pcm.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class PdfConfigMissingException extends RuntimeException {
    public PdfConfigMissingException() {
        super();
    }

    public PdfConfigMissingException(String message) {
        super(message);
    }

    public PdfConfigMissingException(String message, Throwable cause) {
        super(message, cause);
    }

    public PdfConfigMissingException(Throwable cause) {
        super(cause);
    }

    protected PdfConfigMissingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
