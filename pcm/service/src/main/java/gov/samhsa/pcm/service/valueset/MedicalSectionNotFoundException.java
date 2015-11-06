package gov.samhsa.pcm.service.valueset;

public class MedicalSectionNotFoundException extends Exception {

	private static final long serialVersionUID = -8504410815505151211L;

	/**
	 * Instantiates a new medical Section not found exception.
	 */
	public MedicalSectionNotFoundException() {
		super();
	}
	
	/**
	 * Instantiates a new medical Section not found exception.
	 *
	 * @param message the message
	 */
	public MedicalSectionNotFoundException(String message) {
		super(message);
	}
}

