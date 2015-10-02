package gov.samhsa.consent2share.service.contact;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import gov.samhsa.consent2share.domain.commondomainservices.EmailSender;
import gov.samhsa.consent2share.domain.commondomainservices.EmailType;
import gov.samhsa.consent2share.service.dto.ContactDto;

@Transactional
public class ContactServiceImpl implements ContactService {

	/** The email sender. */
	protected EmailSender emailSender;

	/** The logger. */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public ContactServiceImpl(EmailSender emailSender) {
		this.emailSender = emailSender;
	}

	@Override
	public void sendEmail(ContactDto contactDto) {
		try {

			String message = "";

			if(contactDto.getMessageCode().equals("1")){
				message = "I am interested to learn how to sign up for Consent 2 Share.";
			}else if(contactDto.getMessageCode().equals("2")){
				message = "I am interested in information to learn more about managing who can see my patient records";
			}

			emailSender.sendContactMessage(EmailType.REQUEST_MORE_INFORMATION, contactDto.getFirstName(), contactDto.getLastName(),contactDto.getEmail(), contactDto.getTelephoneNumber(), message);
			emailSender.sendContactMessage(EmailType.CONFIRM_REQUEST_MORE_INFORMATION, "", "", contactDto.getEmail(),  "",  "");
		} catch (MessagingException e) {
			logger.error(e.getMessage(), e);
		}
	}

}
