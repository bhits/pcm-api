package gov.samhsa.bhits.pcm.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ProviderAlreadyInUseException extends RuntimeException {
    public ProviderAlreadyInUseException(String message) {
        super(message);
    }
}
