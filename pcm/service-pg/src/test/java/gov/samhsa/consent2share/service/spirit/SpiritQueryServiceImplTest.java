package gov.samhsa.consent2share.service.spirit;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.samhsa.consent2share.hl7.dto.PixPatientDto;
import gov.samhsa.spirit.wsclient.adapter.SpiritAdapter;
import gov.samhsa.spirit.wsclient.dto.PatientDto;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
@SuppressWarnings("unchecked")
public class SpiritQueryServiceImplTest {

	@Mock
	private SpiritAdapter spiritAdapter;

	@InjectMocks
	SpiritQueryServiceImpl sqt;

	@Test
	public void testTrasferDateType() {
		@SuppressWarnings("deprecation")
		Date date = new Date("Fri May 09 16:07:46 EDT 2014");
		assertEquals("20140509", sqt.trasferDateType(date));
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testTransferPixPatientDtoToPatientDto() {
		PixPatientDto pixPatientDto = mock(PixPatientDto.class);
		when(pixPatientDto.getBirthTimeValue()).thenReturn("20140509");
		when(pixPatientDto.getPatientFirstName()).thenReturn("Albert");
		when(pixPatientDto.getPatientLastName()).thenReturn("Smith");
		when(pixPatientDto.getAdministrativeGenderCode()).thenReturn("M");

		PatientDto patientDto = sqt
				.transferPixPatientDtoToPatientDto(pixPatientDto);

		assertEquals("Albert", patientDto.getFirstName());
		assertEquals("Smith", patientDto.getLastName());
		assertEquals("M", patientDto.getGenderCode());
		assertEquals("USA", patientDto.getCountry());
		assertEquals("20140509", patientDto.getBirthDate());

	}
	// TODO (SC): Re-enable the unit tests
	/*
	 * @Test public void testGetHIEPatientIdbyPDQ() throws
	 * SpiritAdapterException {
	 * 
	 * PatientDto patientDto = mock(PatientDto.class);
	 * 
	 * when(spiritAdapter.getPatientIdByPDQ(patientDto)).thenReturn(
	 * "FAM.139938253593554");
	 * 
	 * assertEquals("FAM.139938253593554",
	 * sqt.getHIEPatientIdbyPDQ(patientDto)); }
	 */

	/*
	 * @Test public void testGetHIEPatientIdbyPDQ_throw_Exception() throws
	 * SpiritAdapterException {
	 * 
	 * PatientDto patientDto = mock(PatientDto.class);
	 * 
	 * when(spiritAdapter.getPatientIdByPDQ(patientDto)).thenThrow(
	 * SpiritAdapterException.class);
	 * 
	 * assertEquals(null, sqt.getHIEPatientIdbyPDQ(patientDto)); }
	 */

	/*
	 * @SuppressWarnings("deprecation")
	 * 
	 * @Test public void testGetHIEPatientIdbySignupDto() throws
	 * SpiritAdapterException, SpiritClientNotAvailableException {
	 * 
	 * SpiritQueryServiceImpl sqtspy = spy(sqt);
	 * 
	 * SignupDto signupDto = mock(SignupDto.class);
	 * when(signupDto.getBirthDate()).thenReturn( new
	 * Date("Fri May 09 16:07:46 EDT 2014"));
	 * when(signupDto.getFirstName()).thenReturn("Albert");
	 * when(signupDto.getLastName()).thenReturn("Smith");
	 * when(signupDto.getGenderCode()).thenReturn("M");
	 * 
	 * when(spiritAdapter.getPatientIdByPDQ(any(PatientDto.class)))
	 * .thenReturn("FAM.139938253593554");
	 * 
	 * assertEquals("FAM.139938253593554",
	 * sqtspy.getHIEPatientIdbyPDQ(signupDto)); }
	 */

	/*
	 * @SuppressWarnings("deprecation")
	 * 
	 * @Test public void testGetHIEPatientIdbySignupDto_throw_exception() throws
	 * SpiritAdapterException, SpiritClientNotAvailableException {
	 * 
	 * SignupDto signupDto = mock(SignupDto.class);
	 * when(signupDto.getBirthDate()).thenReturn( new
	 * Date("Fri May 09 16:07:46 EDT 2014"));
	 * when(signupDto.getFirstName()).thenReturn("Albert");
	 * when(signupDto.getLastName()).thenReturn("Smith");
	 * when(signupDto.getGenderCode()).thenReturn("M");
	 * when(signupDto.getEmail()).thenReturn("M");
	 * 
	 * when(spiritAdapter.getPatientIdByPDQ(any(PatientDto.class))).thenThrow(
	 * SpiritAdapterException.class);
	 * 
	 * }
	 */

}
