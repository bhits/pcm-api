package gov.samhsa.acs.dss.wsclient;

import gov.samhsa.acs.common.exception.DS4PException;

public class DSSWebServiceClientException extends DS4PException {

	private static final long serialVersionUID = -7648098728117922471L;

	public DSSWebServiceClientException() {
		super();
	}

	public DSSWebServiceClientException(String arg0) {
		super(arg0);
	}

	public DSSWebServiceClientException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public DSSWebServiceClientException(Throwable arg0) {
		super(arg0);
	}
}
