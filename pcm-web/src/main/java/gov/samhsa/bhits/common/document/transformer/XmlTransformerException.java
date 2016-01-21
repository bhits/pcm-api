package gov.samhsa.bhits.common.document.transformer;

public class XmlTransformerException extends RuntimeException {
    public XmlTransformerException(String message) {
        super(message);
    }

    public XmlTransformerException(String message, Throwable cause) {
        super(message, cause);
    }
}
