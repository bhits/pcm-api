/*******************************************************************************
 * Open Behavioral Health Information Technology Architecture (OBHITA.org)
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the <organization> nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 ******************************************************************************/
package gov.samhsa.pcm.service.dto;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 * The Class ContactDto.
 */
public class ContactDto {

	/** The first name. */
	@NotNull
	@Size(max = 30)
	private String firstName;

	/** The last name. */
	@NotNull
	@Size(max = 30)
	private String lastName;


	/** The email. */
	private String email;
	
	/** The email confirmation. */
	private String emailConfirmation;


	/** The phone number. */	
	private String telephoneNumber;
	
	/** 
	 * The message code to lookup the message
	 * to be send in the email.
	 * */
	@NotNull
	@Size(max = 255)
	private String messageCode;
	
	/**
	 * The default contact DTO.
	 */
	public ContactDto() {	
	}
	
	/**
	 * Create DTO with the dafault message code.
	 *
	 * @params messageCode 
	 * 			 The default message code	
	 */
	public ContactDto(String messageCode) {
		this.messageCode = messageCode;
	}
	
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
	 * Gets the email.
	 *
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	
	
	/**
	 * Sets the email.
	 *
	 * @param email
	 *            the new email
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * Gets the confirm Email.
	 *
	 * @return the confirmEmail
	 */
	public String getEmailConfirmation() {
		return emailConfirmation;
	}
	
	
	/**
	 * Sets the second email.
	 *
	 * @param emailConfirmation
	 *            the second email
	 */
	public void setEmailConfirmation(String email) {
		this.emailConfirmation = email;
	}
	
	
	/**
	 * Gets the telephone number.
	 *
	 * @return the telephoneNumber
	 */
	public String getTelephoneNumber() {
		return telephoneNumber;
	}
	/**
	 * Sets the telephone number.
	 *
	 * @param telephonenumber
	 *            the new telephonenumber
	 */
	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}
	/**
	 * Gets the message code.
	 *
	 * @return the messageCode
	 */
	public String getMessageCode() {
		return messageCode;
	}
	/**
	 * Sets the message code.
	 *
	 * @param message
	 *            the new message
	 */
	public void setMessageCode(String code) {
		this.messageCode = code;
	}
	
	

}
