package gov.samhsa.c2s.pcm.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ConflictingConsentException extends RuntimeException {
    public ConflictingConsentException(String message) {
        super(message);
    }
}
