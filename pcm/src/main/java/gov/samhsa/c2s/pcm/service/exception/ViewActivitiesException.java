package gov.samhsa.c2s.pcm.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Jiahao.Li on 6/12/2016.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ViewActivitiesException extends RuntimeException {
    public ViewActivitiesException() {
        super();
    }

    public ViewActivitiesException(String message) {
        super(message);
    }

    public ViewActivitiesException(Throwable cause) {
        super(cause);
    }

    public ViewActivitiesException(String message, Throwable cause) {
        super(message, cause);
    }

    protected ViewActivitiesException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
