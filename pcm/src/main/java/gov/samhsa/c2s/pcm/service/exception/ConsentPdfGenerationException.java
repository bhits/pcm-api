package gov.samhsa.c2s.pcm.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ConsentPdfGenerationException extends RuntimeException {
    public ConsentPdfGenerationException() {
        super();
    }

    public ConsentPdfGenerationException(String message) {
        super(message);
    }

    public ConsentPdfGenerationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConsentPdfGenerationException(Throwable cause) {
        super(cause);
    }

    protected ConsentPdfGenerationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
