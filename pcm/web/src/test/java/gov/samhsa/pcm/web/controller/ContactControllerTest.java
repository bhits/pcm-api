package gov.samhsa.pcm.web.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import gov.samhsa.pcm.service.contact.ContactService;
import gov.samhsa.pcm.service.dto.ContactDto;
import gov.samhsa.pcm.service.validator.FieldValidatorRequestMoreInformation;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.StringUtils;

@RunWith(MockitoJUnitRunner.class)
public class ContactControllerTest {

	@Mock
	ContactService contactService;
	
	@Mock
	FieldValidatorRequestMoreInformation fieldValidatorRequestMoreInformation;
	
	MockMvc mockMvc;

	@Before
	public void before() {	
		ContactController contactController = new ContactController( contactService, fieldValidatorRequestMoreInformation);
		mockMvc = MockMvcBuilders.standaloneSetup(contactController).build();
	}
	
	@Test
	public void testRequestMoreInformationLink() throws Exception {
		mockMvc.perform(get("/contact.html"))	
				.andExpect(status().isOk())
				.andExpect(model().attributeExists(StringUtils.uncapitalize(ContactDto.class.getSimpleName())))
				.andExpect(view().name("views/contact"));
	}
	
	
	@Test
	public void testSendContactEmailRedirect() throws Exception {
		ContactDto contactDto = mock(ContactDto.class);
		doNothing().when(contactService).sendEmail(contactDto);
		
		mockMvc.perform(post("/sendContactEmail.html")
							.param("fistName", "test")
							.param("lastName", "test")
							.param("email", "test@test.com")
							.param("emailConfirmation", "test@test.com")
							.param("messageCode", "1"))
							.andExpect(status().is3xxRedirection())
							.andExpect(view().name("redirect:/contactSuccess.html"));
	}

}
