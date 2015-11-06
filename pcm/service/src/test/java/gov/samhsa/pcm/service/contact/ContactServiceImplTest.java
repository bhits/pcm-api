package gov.samhsa.pcm.service.contact;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import gov.samhsa.pcm.domain.commondomainservices.EmailSender;
import gov.samhsa.pcm.domain.commondomainservices.EmailType;
import gov.samhsa.pcm.service.contact.ContactServiceImpl;
import gov.samhsa.pcm.service.dto.ContactDto;




import javax.mail.MessagingException;

import org.junit.Before;
import org.junit.Test;

public class ContactServiceImplTest {

	private ContactServiceImpl contactService;
	private EmailSender emailSender;
	
	@Before
	public void setUp() {
		emailSender = mock(EmailSender.class);
        contactService = new ContactServiceImpl(emailSender);

	}

	@Test
	public void testSendEmail() throws MessagingException {
		ContactDto contactDto = mock(ContactDto.class);
		String msg = "I am interested to learn how to sign up for Consent 2 Share.";
		
		when(contactDto.getFirstName()).thenReturn("firstname");
		when(contactDto.getLastName()).thenReturn("laststname");
		when(contactDto.getEmail()).thenReturn("firstname@gmail.com");
		when(contactDto.getEmailConfirmation()).thenReturn("firstname@gmail.com");
		when(contactDto.getTelephoneNumber()).thenReturn("1111111111");
		when(contactDto.getMessageCode()).thenReturn("1");
		
		contactService.sendEmail(contactDto);
		
		verify(emailSender, times(1)).sendContactMessage(EmailType.REQUEST_MORE_INFORMATION,contactDto.getFirstName(),contactDto.getLastName(), contactDto.getEmail(),contactDto.getTelephoneNumber(),msg);
		verify(emailSender, times(1)).sendContactMessage(EmailType.CONFIRM_REQUEST_MORE_INFORMATION,"", "", contactDto.getEmail(), "", "");
	}	
}
