package gov.samhsa.pcm.service.contact;

import gov.samhsa.pcm.service.dto.ContactDto;

public interface ContactService {
	/**
	 * Send mail.
	 *
	 * @param contactDto 
	 * 			 holds all the information enter by the user
	 */
	public abstract void sendEmail(ContactDto contactDto) ;
}
