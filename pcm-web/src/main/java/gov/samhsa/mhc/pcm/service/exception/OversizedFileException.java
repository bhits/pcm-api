package gov.samhsa.mhc.pcm.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class OversizedFileException extends RuntimeException {
    public OversizedFileException(String message) {
        super(message);
    }
}
