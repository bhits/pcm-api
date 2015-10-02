package gov.samhsa.acs.common.tool.exception;

import javax.xml.bind.JAXBException;

public class SimpleMarshallerException extends JAXBException {

	private static final long serialVersionUID = -6728449516693987525L;

	public SimpleMarshallerException(String message, String errorCode,
			Throwable exception) {
		super(message, errorCode, exception);		
	}

	public SimpleMarshallerException(String message, String errorCode) {
		super(message, errorCode);		
	}

	public SimpleMarshallerException(String message, Throwable exception) {
		super(message, exception);		
	}

	public SimpleMarshallerException(String message) {
		super(message);		
	}

	public SimpleMarshallerException(Throwable exception) {
		super(exception);		
	}
}
