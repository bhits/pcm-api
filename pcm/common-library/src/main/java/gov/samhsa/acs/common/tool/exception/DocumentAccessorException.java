package gov.samhsa.acs.common.tool.exception;

import javax.xml.xpath.XPathExpressionException;

public class DocumentAccessorException extends XPathExpressionException {

	private static final long serialVersionUID = -1339078784033295863L;

	public DocumentAccessorException(String message) {
		super(message);
	}

	public DocumentAccessorException(Throwable cause) {
		super(cause);
	}
}
