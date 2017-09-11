package gov.samhsa.c2s.pcm.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ConsentRevocationPdfGenerationException extends RuntimeException {
    public ConsentRevocationPdfGenerationException() {
        super();
    }

    public ConsentRevocationPdfGenerationException(String message) {
        super(message);
    }

    public ConsentRevocationPdfGenerationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConsentRevocationPdfGenerationException(Throwable cause) {
        super(cause);
    }

    protected ConsentRevocationPdfGenerationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
