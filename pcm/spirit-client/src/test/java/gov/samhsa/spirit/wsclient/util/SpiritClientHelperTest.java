package gov.samhsa.spirit.wsclient.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.samhsa.spirit.wsclient.dto.PatientDto;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import com.spirit.ehr.ws.client.generated.EhrPatientClientDto;

@RunWith(MockitoJUnitRunner.class)
public class SpiritClientHelperTest {

	@InjectMocks
	SpiritClientHelper helper;

	@Test
	public void testConvertFromPatientDto() {

		EhrPatientClientDto eClientDto = new EhrPatientClientDto();
		PatientDto patientDto = mock(PatientDto.class);

		when(patientDto.getLastName()).thenReturn("lastname");
		when(patientDto.getFirstName()).thenReturn("firstname");
		when(patientDto.getCountry()).thenReturn("usa");
		when(patientDto.getGenderCode()).thenReturn("M");
		when(patientDto.getBirthDate()).thenReturn("19970101");
		when(patientDto.getEmailHome()).thenReturn("myemail@mail.com");

		when(patientDto.getStreetAddress()).thenReturn("streetaddress");
		when(patientDto.getCity()).thenReturn("columbia");
		when(patientDto.getState()).thenReturn("MD");
		when(patientDto.getZip()).thenReturn("21046");
		when(patientDto.getHomePhone()).thenReturn("4431111111");
		when(patientDto.getSsnNumber()).thenReturn("777-777-7777");

		when(patientDto.getMaritalStatus()).thenReturn("married");
		when(patientDto.getReligion()).thenReturn("religion");
		when(patientDto.getRace()).thenReturn("race");
		when(patientDto.getLanguage()).thenReturn("english");

		helper.convertFromPatientDto(patientDto, false, eClientDto);

		assertEquals("lastname", eClientDto.getFamilyName());
		assertEquals("firstname", eClientDto.getGivenName());
		assertEquals("usa", eClientDto.getCountry());
		assertEquals("M", eClientDto.getSex());
		assertEquals("19970101", eClientDto.getBirthdate());
		assertEquals("myemail@mail.com", eClientDto.getEmailHome());
		assertEquals("streetaddress", eClientDto.getStreetAddress());
		assertEquals("columbia", eClientDto.getCity());
		assertEquals("MD", eClientDto.getState());
		assertEquals("21046", eClientDto.getZip());
		assertEquals("4431111111", eClientDto.getHomePhone());
		assertEquals("777-777-7777", eClientDto.getPid().get(0).getPatientID());
		assertEquals("married", eClientDto.getMaritalStatus());
		assertEquals("religion", eClientDto.getReligion());
		assertEquals("race", eClientDto.getRace());
		assertEquals("english", eClientDto.getLanguage());
	}

	@Test
	public void testConvertFromPatientDto_basic() {

		EhrPatientClientDto eClientDto = new EhrPatientClientDto();
		PatientDto patientDto = mock(PatientDto.class);

		when(patientDto.getLastName()).thenReturn("lastname");
		when(patientDto.getFirstName()).thenReturn("firstname");
		when(patientDto.getCountry()).thenReturn("usa");
		when(patientDto.getGenderCode()).thenReturn("M");
		when(patientDto.getBirthDate()).thenReturn("19970101");
		when(patientDto.getEmailHome()).thenReturn("myemail@mail.com");

		when(patientDto.getStreetAddress()).thenReturn("streetaddress");
		when(patientDto.getCity()).thenReturn("columbia");
		when(patientDto.getState()).thenReturn("MD");
		when(patientDto.getZip()).thenReturn("21046");
		when(patientDto.getHomePhone()).thenReturn("4431111111");
		when(patientDto.getSsnNumber()).thenReturn("777-777-7777");

		when(patientDto.getMaritalStatus()).thenReturn("married");
		when(patientDto.getReligion()).thenReturn("religion");
		when(patientDto.getRace()).thenReturn("race");
		when(patientDto.getLanguage()).thenReturn("english");

		helper.convertFromPatientDto(patientDto, true, eClientDto);

		assertEquals("lastname", eClientDto.getFamilyName());
		assertEquals("firstname", eClientDto.getGivenName());
		assertEquals("usa", eClientDto.getCountry());
		assertEquals("M", eClientDto.getSex());
		assertEquals("19970101", eClientDto.getBirthdate());
		assertEquals(null, eClientDto.getStreetAddress());
		assertEquals(null, eClientDto.getCity());
		assertEquals(null, eClientDto.getState());
		assertEquals(null, eClientDto.getZip());
		assertEquals(null, eClientDto.getHomePhone());
		assertEquals("777-777-7777", eClientDto.getPid().get(0).getPatientID());
		assertEquals(null, eClientDto.getMaritalStatus());
		assertEquals(null, eClientDto.getReligion());
		assertEquals(null, eClientDto.getRace());
		assertEquals(null, eClientDto.getLanguage());
	}

	@Test
	public void testcreateC2SLocalIdentifier() {
		String c2sEnvType = "c2sEnvType";
		String locId = helper.createC2SLocalIdentifier(c2sEnvType);
		assertNotNull(locId);
		assertTrue(locId.startsWith(new String(c2sEnvType).toUpperCase()));
	}

}
