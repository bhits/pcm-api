package gov.samhsa.mhc.pcm.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class UnprocessableConsentAddRequestException extends RuntimeException{
    public UnprocessableConsentAddRequestException(String message) {
        super(message);
    }

    public UnprocessableConsentAddRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
