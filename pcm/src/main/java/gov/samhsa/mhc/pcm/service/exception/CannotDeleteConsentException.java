package gov.samhsa.mhc.pcm.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class CannotDeleteConsentException extends RuntimeException{
    public CannotDeleteConsentException(String message) {
        super(message);
    }

    public CannotDeleteConsentException(String message, Throwable cause) {
        super(message, cause);
    }
}
