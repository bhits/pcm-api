package gov.samhsa.bhits.pcm.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ConsentNotBelongingToPatientException extends RuntimeException{
    public ConsentNotBelongingToPatientException(String message) {
        super(message);
    }

    public ConsentNotBelongingToPatientException(String message, Throwable cause) {
        super(message, cause);
    }
}
