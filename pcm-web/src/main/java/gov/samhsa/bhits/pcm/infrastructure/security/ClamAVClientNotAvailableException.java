package gov.samhsa.bhits.pcm.infrastructure.security;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ClamAVClientNotAvailableException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ClamAVClientNotAvailableException() {
        super();
    }

    public ClamAVClientNotAvailableException(String message, Throwable cause,
                                             boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ClamAVClientNotAvailableException(String message, Throwable cause) {
        super(message, cause);
    }

    public ClamAVClientNotAvailableException(String message) {
        super(message);
    }

    public ClamAVClientNotAvailableException(Throwable cause) {
        super(cause);
    }
}
