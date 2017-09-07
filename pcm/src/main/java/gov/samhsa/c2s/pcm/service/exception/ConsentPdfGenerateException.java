package gov.samhsa.c2s.pcm.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ConsentPdfGenerateException extends RuntimeException {
    public ConsentPdfGenerateException() {
        super();
    }

    public ConsentPdfGenerateException(String message) {
        super(message);
    }

    public ConsentPdfGenerateException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConsentPdfGenerateException(Throwable cause) {
        super(cause);
    }

    protected ConsentPdfGenerateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
