package gov.samhsa.c2s.pcm.infrastructure.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class InvalidTableAttributeException extends RuntimeException {
    public InvalidTableAttributeException() {
    }

    public InvalidTableAttributeException(String message) {
        super(message);
    }

    public InvalidTableAttributeException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidTableAttributeException(Throwable cause) {
        super(cause);
    }

    public InvalidTableAttributeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
