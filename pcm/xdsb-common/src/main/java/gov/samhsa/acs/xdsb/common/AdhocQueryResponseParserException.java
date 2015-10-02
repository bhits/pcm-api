package gov.samhsa.acs.xdsb.common;

import gov.samhsa.acs.common.exception.DS4PException;

public class AdhocQueryResponseParserException extends DS4PException {

	private static final long serialVersionUID = -1940430773232382016L;

	public AdhocQueryResponseParserException() {
		super();
	}

	public AdhocQueryResponseParserException(String arg0) {
		super(arg0);
	}

	public AdhocQueryResponseParserException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public AdhocQueryResponseParserException(Throwable arg0) {
		super(arg0);
	}
}
