package gov.samhsa.mhc.vss.service;

public class ConceptCodeNotFoundException extends Exception {

	private static final long serialVersionUID = 3234232442718766659L;

	public ConceptCodeNotFoundException() {
		super();
	}

	public ConceptCodeNotFoundException(String message) {
		super(message);
	}
}
