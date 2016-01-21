package gov.samhsa.bhits.common.document.converter;

public class DocumentXmlConverterException extends RuntimeException {

	private static final long serialVersionUID = -7533194938812238191L;

	public DocumentXmlConverterException() {
		super();		
	}

	public DocumentXmlConverterException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);		
	}

	public DocumentXmlConverterException(String message, Throwable cause) {
		super(message, cause);		
	}

	public DocumentXmlConverterException(String message) {
		super(message);		
	}

	public DocumentXmlConverterException(Throwable cause) {
		super(cause);		
	}
}
