package gov.samhsa.consent2share.service.contact;

import gov.samhsa.consent2share.service.dto.ContactDto;

public interface ContactService {
	/**
	 * Send mail.
	 *
	 * @param contactDto 
	 * 			 holds all the information enter by the user
	 */
	public abstract void sendEmail(ContactDto contactDto) ;
}
