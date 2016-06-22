package gov.samhsa.mhc.pcm.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Jiahao.Li on 6/13/2016.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class PatientNotFoundException extends RuntimeException {
    public PatientNotFoundException() {
        super();
    }

    public PatientNotFoundException(String message) {
        super(message);
    }

    public PatientNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public PatientNotFoundException(Throwable cause) {
        super(cause);
    }

    protected PatientNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
