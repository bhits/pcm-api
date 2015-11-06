package gov.samhsa.pcm.service.dto;

/**
 * The Class AdminCreatePatientResponseDto.
 */
public class AdminCreatePatientResponseDto {

	/** The patient status unknown. */
	public static String PATIENT_STATUS_UNKNOWN = "-1";

	/** The patient exist in exchange. */
	public static String PATIENT_EXIST_IN_EXCHANGE = "1";

	/** The patient is new in exchange. */
	public static String PATIENT_IS_NEW_IN_EXCHANGE = "0";

	/** The message. */
	private String message;

	/** The patient id. */
	private long patientId;

	/**
	 * Instantiates a new admin create patient response dto.
	 *
	 * @param message
	 *            the message
	 * @param patientId
	 *            the patient id
	 */
	public AdminCreatePatientResponseDto(String message, long patientId) {
		super();
		this.message = message;
		this.patientId = patientId;
	}

	/**
	 * Gets the message.
	 *
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sets the message.
	 *
	 * @param message
	 *            the new message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Gets the patient id.
	 *
	 * @return the patient id
	 */
	public long getPatientId() {
		return patientId;
	}

	/**
	 * Sets the patient id.
	 *
	 * @param patientId
	 *            the new patient id
	 */
	public void setPatientId(long patientId) {
		this.patientId = patientId;
	}

}
