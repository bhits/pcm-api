package gov.samhsa.bhits.pcm.infrastructure.security;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class InfectedFileException extends RuntimeException{
    public InfectedFileException(String message) {
        super(message);
    }
}
