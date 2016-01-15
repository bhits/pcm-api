package gov.samhsa.bhits.pcm.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class TooManyProvidersSelectedException extends RuntimeException {
    public TooManyProvidersSelectedException(String message) {
        super(message);
    }

    public TooManyProvidersSelectedException(String message, Throwable cause) {
        super(message, cause);
    }
}
