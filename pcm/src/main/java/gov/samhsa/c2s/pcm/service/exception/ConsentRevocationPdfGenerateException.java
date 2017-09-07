package gov.samhsa.c2s.pcm.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ConsentRevocationPdfGenerateException extends RuntimeException {
    public ConsentRevocationPdfGenerateException() {
        super();
    }

    public ConsentRevocationPdfGenerateException(String message) {
        super(message);
    }

    public ConsentRevocationPdfGenerateException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConsentRevocationPdfGenerateException(Throwable cause) {
        super(cause);
    }

    protected ConsentRevocationPdfGenerateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
