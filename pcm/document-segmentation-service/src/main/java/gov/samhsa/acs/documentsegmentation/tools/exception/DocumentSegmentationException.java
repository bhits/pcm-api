package gov.samhsa.acs.documentsegmentation.tools.exception;

import gov.samhsa.acs.common.exception.DS4PException;

public class DocumentSegmentationException extends DS4PException {

	private static final long serialVersionUID = -1286240612088308611L;

	public DocumentSegmentationException() {
		super();		
	}

	public DocumentSegmentationException(String arg0, Throwable arg1) {
		super(arg0, arg1);		
	}

	public DocumentSegmentationException(String arg0) {
		super(arg0);		
	}

	public DocumentSegmentationException(Throwable arg0) {
		super(arg0);		
	}
}
