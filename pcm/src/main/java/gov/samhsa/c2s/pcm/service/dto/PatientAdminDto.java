package gov.samhsa.c2s.pcm.service.dto;

/**
 * The Class PatientAdminDto.
 */
public class PatientAdminDto {

	/** The first name. */
	private String firstName;

	/** The last name. */
	private String lastName;

	/** The id. */
	private Long id;

	/** The date of birth. */
	private String birthDay;

	/** The last four digits of ssn. */
	private String socialSecurityNumber;

	/**
	 * Gets the first name.
	 *
	 * @return the first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets the first name.
	 *
	 * @param firstName
	 *            the new first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Gets the last name.
	 *
	 * @return the last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the last name.
	 *
	 * @param lastName
	 *            the new last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id
	 *            the new id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the birth day.
	 *
	 * @return the birth day
	 */
	public String getBirthDay() {
		return birthDay;
	}

	/**
	 * Sets the birth day.
	 *
	 * @param birthDay
	 *            the new birth day
	 */
	public void setBirthDay(String birthDay) {
		this.birthDay = birthDay;
	}

	/**
	 * Gets the social security number.
	 *
	 * @return the social security number
	 */
	public String getSocialSecurityNumber() {
		return socialSecurityNumber;
	}

	/**
	 * Sets the social security number.
	 *
	 * @param socialSecurityNumber
	 *            the new social security number
	 */
	public void setSocialSecurityNumber(String socialSecurityNumber) {
		this.socialSecurityNumber = socialSecurityNumber;
	}

}
