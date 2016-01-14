package gov.samhsa.bhits.pcm.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class CannotDeleteProviderException extends RuntimeException {

    public CannotDeleteProviderException(String message) {
        super(message);
    }
}
