package gov.samhsa.mhc.pcm.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class MissingPurposeOfUseException extends RuntimeException {
    public MissingPurposeOfUseException(String message) {
        super(message);
    }

    public MissingPurposeOfUseException(String message, Throwable cause) {
        super(message, cause);
    }
}
