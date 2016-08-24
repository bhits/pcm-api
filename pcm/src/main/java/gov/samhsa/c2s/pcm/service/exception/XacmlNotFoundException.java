package gov.samhsa.c2s.pcm.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class XacmlNotFoundException extends RuntimeException {
    public XacmlNotFoundException() {
        super();
    }

    public XacmlNotFoundException(String message) {
        super(message);
    }

    public XacmlNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public XacmlNotFoundException(Throwable cause) {
        super(cause);
    }

    protected XacmlNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
