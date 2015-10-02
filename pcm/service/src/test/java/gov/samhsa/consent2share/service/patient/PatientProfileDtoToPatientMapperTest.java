package gov.samhsa.consent2share.service.patient;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.samhsa.consent2share.domain.patient.Patient;
import gov.samhsa.consent2share.domain.patient.PatientRepository;
import gov.samhsa.consent2share.domain.provider.IndividualProvider;
import gov.samhsa.consent2share.domain.reference.AdministrativeGenderCodeRepository;
import gov.samhsa.consent2share.domain.reference.CountryCodeRepository;
import gov.samhsa.consent2share.domain.reference.LanguageCodeRepository;
import gov.samhsa.consent2share.domain.reference.MaritalStatusCodeRepository;
import gov.samhsa.consent2share.domain.reference.RaceCodeRepository;
import gov.samhsa.consent2share.domain.reference.ReligiousAffiliationCodeRepository;
import gov.samhsa.consent2share.domain.reference.StateCodeRepository;
import gov.samhsa.consent2share.service.dto.LookupDto;
import gov.samhsa.consent2share.service.dto.PatientProfileDto;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class PatientProfileDtoToPatientMapperTest {

	private PatientProfileDtoToPatientMapper sut;

	private PatientRepository patientRepository;

	private StateCodeRepository stateCodeRepository;

	private CountryCodeRepository countryCodeRepository;

	private AdministrativeGenderCodeRepository administrativeGenderCodeRepository;

	private LanguageCodeRepository languageCodeRepository;

	private MaritalStatusCodeRepository maritalStatusCodeRepository;

	private RaceCodeRepository raceCodeRepository;

	private ReligiousAffiliationCodeRepository religiousAffiliationCodeRepository;

	@Before
	public void setUp() {
		// Mock dependencies and create sut
		// Just to save a few lines of code for each individual test
		// But independency, clarity of the unit tests are much more important
		// than code reuse
		patientRepository = mock(PatientRepository.class);
		stateCodeRepository = mock(StateCodeRepository.class);
		countryCodeRepository = mock(CountryCodeRepository.class);
		administrativeGenderCodeRepository = mock(AdministrativeGenderCodeRepository.class);
		languageCodeRepository = mock(LanguageCodeRepository.class);
		maritalStatusCodeRepository = mock(MaritalStatusCodeRepository.class);
		raceCodeRepository = mock(RaceCodeRepository.class);
		religiousAffiliationCodeRepository = mock(ReligiousAffiliationCodeRepository.class);

		sut = new PatientProfileDtoToPatientMapper(patientRepository,
				stateCodeRepository, countryCodeRepository,
				administrativeGenderCodeRepository, languageCodeRepository,
				maritalStatusCodeRepository, raceCodeRepository,
				religiousAffiliationCodeRepository);

	}

	@Test
	public void testMap_Given_Null_Username_And_Null_Patient_Id() {
		// Arrange
		PatientProfileDto patientDto = mock(PatientProfileDto.class);
		when(patientDto.getUsername()).thenReturn(null);
		when(patientDto.getId()).thenReturn(null);

		final String firstName = "Firstname";
		when(patientDto.getFirstName()).thenReturn(firstName);

		// Act
		Patient patient = sut.map(patientDto);

		// Assert
		assertEquals(firstName, patient.getFirstName());
	}

	@Test
	public void testMap_Given_Username() {
		// Arrange
		final String username = "username";
		PatientProfileDto patientDto = mock(PatientProfileDto.class);
		when(patientDto.getUsername()).thenReturn(username);

		Patient patient = mock(Patient.class);
		when(patientRepository.findByUsername(username)).thenReturn(patient);

		final String firstName = "Firstname";
		when(patientDto.getFirstName()).thenReturn(firstName);

		// Act
		Patient result = sut.map(patientDto);

		// Assert
		assertEquals(patient, result);
	}

	@Ignore("MRN is not passed via UI anymore")
	@Test
	public void testMap_Given_MedicalRecordNumber() {
		// Arrange
		final String username = "username";
		PatientProfileDto patientDto = mock(PatientProfileDto.class);
		when(patientDto.getUsername()).thenReturn(username);

		Patient patient = new Patient();
		when(patientRepository.findByUsername(username)).thenReturn(patient);

		final String mrn = "mrn";
		when(patientDto.getMedicalRecordNumber()).thenReturn(mrn);

		// Act
		Patient result = sut.map(patientDto);

		// Assert
		assertEquals(patient, result);
		assertEquals(mrn, result.getMedicalRecordNumber());
	}

	@Ignore("EID is not passed via UI anymore")
	@Test
	public void testMap_Given_EnterpriseIdentifier() {
		// Arrange
		final String username = "username";
		PatientProfileDto patientDto = mock(PatientProfileDto.class);
		when(patientDto.getUsername()).thenReturn(username);

		Patient patient = new Patient();
		when(patientRepository.findByUsername(username)).thenReturn(patient);

		final String eid = "eid";
		when(patientDto.getEnterpriseIdentifier()).thenReturn(eid);

		// Act
		Patient result = sut.map(patientDto);

		// Assert
		assertEquals(patient, result);
		assertEquals(eid, result.getEnterpriseIdentifier());
	}

	@Test
	public void test_General_Case() {
		final String username = "username";
		PatientProfileDto patientDto = mock(PatientProfileDto.class);
		when(patientDto.getUsername()).thenReturn(username);

		Patient patient = mock(Patient.class);
		when(patientRepository.findByUsername(username)).thenReturn(patient);
		final String firstName = "Firstname";
		when(patientDto.getFirstName()).thenReturn(firstName);
		when(patientDto.getSocialSecurityNumber()).thenReturn("111-11-1111");

		when(patientDto.getTelephoneTelephone()).thenReturn("111-111-1111");
		when(patientDto.getAddressStreetAddressLine())
				.thenReturn("addressLine");
		when(patientDto.getAddressCity()).thenReturn("NewYork");
		when(patientDto.getAddressStateCode()).thenReturn("NY");
		when(patientDto.getAddressPostalCode()).thenReturn("11111");
		when(patientDto.getAdministrativeGenderCode()).thenReturn("M");
		LookupDto languageCode = mock(LookupDto.class);
		when(languageCode.getCode()).thenReturn("US");
		when(patientDto.getLanguageCode()).thenReturn(languageCode);
		LookupDto maritalStatusCode = mock(LookupDto.class);
		when(maritalStatusCode.getCode()).thenReturn("single");
		when(patientDto.getMaritalStatusCode()).thenReturn(maritalStatusCode);
		LookupDto raceCode = mock(LookupDto.class);
		when(raceCode.getCode()).thenReturn("Asian");
		when(patientDto.getRaceCode()).thenReturn(raceCode);
		LookupDto affiliationCode = mock(LookupDto.class);
		when(affiliationCode.getCode()).thenReturn("affi");
		when(patientDto.getReligiousAffiliationCode()).thenReturn(
				affiliationCode);
		Set<IndividualProvider> providers = new HashSet<IndividualProvider>();
		for (int i = 0; i < 3; i++) {
			IndividualProvider individualProvider = mock(IndividualProvider.class);
			providers.add(individualProvider);
		}
		when(patientDto.getIndividualProviders()).thenReturn(providers);

		Patient result = sut.map(patientDto);
		assertEquals(patient, result);
	}

}
