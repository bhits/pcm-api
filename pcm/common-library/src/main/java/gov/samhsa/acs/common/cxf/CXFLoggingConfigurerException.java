package gov.samhsa.acs.common.cxf;

import gov.samhsa.acs.common.exception.DS4PException;

public class CXFLoggingConfigurerException extends DS4PException {

	private static final long serialVersionUID = 5117818728470682827L;

	public CXFLoggingConfigurerException() {
		super();
	}

	public CXFLoggingConfigurerException(String arg0) {
		super(arg0);
	}

	public CXFLoggingConfigurerException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public CXFLoggingConfigurerException(Throwable arg0) {
		super(arg0);
	}

}
