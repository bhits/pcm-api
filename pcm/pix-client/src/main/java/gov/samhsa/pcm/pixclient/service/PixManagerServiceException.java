package gov.samhsa.pcm.pixclient.service;

import gov.samhsa.acs.common.exception.DS4PException;

public class PixManagerServiceException extends DS4PException {

	private static final long serialVersionUID = -3391575515209625846L;

	public PixManagerServiceException() {
		super();
	}

	public PixManagerServiceException(String arg0) {
		super(arg0);
	}

	public PixManagerServiceException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public PixManagerServiceException(Throwable arg0) {
		super(arg0);
	}
}
