package gov.samhsa.c2s.pcm.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class CannotDeserializeProviderResultException extends RuntimeException {
    public CannotDeserializeProviderResultException(String message) {
        super(message);
    }

    public CannotDeserializeProviderResultException(String message, Throwable cause) {
        super(message, cause);
    }
}
