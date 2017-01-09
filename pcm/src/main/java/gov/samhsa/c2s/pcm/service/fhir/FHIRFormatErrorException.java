package gov.samhsa.c2s.pcm.service.fhir;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class FHIRFormatErrorException extends RuntimeException {
    public FHIRFormatErrorException() {
    }

    public FHIRFormatErrorException(String message) {
        super(message);
    }

    public FHIRFormatErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public FHIRFormatErrorException(Throwable cause) {
        super(cause);
    }

    public FHIRFormatErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}