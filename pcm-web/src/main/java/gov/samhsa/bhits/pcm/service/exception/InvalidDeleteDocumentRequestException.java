package gov.samhsa.bhits.pcm.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidDeleteDocumentRequestException extends RuntimeException {
    public InvalidDeleteDocumentRequestException(String message) {
        super(message);
    }
}
