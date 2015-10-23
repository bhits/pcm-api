package gov.samhsa.pcm.web.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import gov.samhsa.pcm.common.AuthenticatedUser;
import gov.samhsa.pcm.common.UserContext;
import gov.samhsa.pcm.infrastructure.PixService;
import gov.samhsa.pcm.service.dto.LookupDto;
import gov.samhsa.pcm.service.dto.PatientProfileDto;
import gov.samhsa.pcm.service.dto.SystemNotificationDto;
import gov.samhsa.pcm.service.notification.NotificationService;
import gov.samhsa.pcm.service.patient.PatientService;
import gov.samhsa.pcm.service.reference.AdministrativeGenderCodeService;
import gov.samhsa.pcm.service.reference.LanguageCodeService;
import gov.samhsa.pcm.service.reference.MaritalStatusCodeService;
import gov.samhsa.pcm.service.reference.RaceCodeService;
import gov.samhsa.pcm.service.reference.ReligiousAffiliationCodeService;
import gov.samhsa.pcm.service.reference.pg.StateCodeServicePg;
import gov.samhsa.pcm.service.systemnotification.SystemNotificationService;
import gov.samhsa.pcm.service.validator.pg.FieldValidator;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

@RunWith(MockitoJUnitRunner.class)
public class PatientControllerTest {

	@InjectMocks
	private PatientController sut = new PatientController();

	@Mock
	PatientService patientService;
	
	@Mock
	SystemNotificationService systemNotificationService;

	@Mock
	AdministrativeGenderCodeService administrativeGenderCodeService;

	@Mock
	LanguageCodeService languageCodeService;

	@Mock
	MaritalStatusCodeService maritalStatusCodeService;

	@Mock
	RaceCodeService raceCodeService;

	@Mock
	ReligiousAffiliationCodeService religiousAffiliationCodeService;

	@Mock
	StateCodeServicePg stateCodeService;
	
	@Mock
	NotificationService notificationService;
	
	@Mock
	UserContext userContext;
	
	@Mock
	PixService pixService;

	@Mock
	private FieldValidator fieldValidator;

	private MockMvc mockMvc;
	
	final static String VALID_FIRST_NAME = "Tom";
	final static String VALID_LAST_NAME = "Lee";
	final static String VALID_BIRTHDAY = "1/1/1950";
	final static String VALID_EMAIL = "test@test.com";
	final static String VALID_GENDER_CODE = "M";
	final static String VALID_MRN = "PUI100000000001";
	final static String VALID_EID = "1c5c59f0-5788-11e3-84b3-00155d3a2124";
	
	final static String GENDER_FIELD="administrativeGenderCode";

	@Before
	public void setUp() {
		mockMvc = standaloneSetup(this.sut).build();
	}

	@Test
	public void testProfile_When_HttpGet() throws Exception {
		final String username = "username";

		final AuthenticatedUser currentUser = mock(AuthenticatedUser.class);
		when(currentUser.getUsername()).thenReturn(username);

		when(userContext.getCurrentUser()).thenReturn(currentUser);

		final PatientProfileDto patientProfile = new PatientProfileDto();
		when(patientService.findPatientProfileByUsername(username)).thenReturn(
				patientProfile);

		List<LookupDto> administrativeGenderCodes = (List<LookupDto>) mock(List.class);
		when(
				administrativeGenderCodeService
						.findAllAdministrativeGenderCodes()).thenReturn(
				administrativeGenderCodes);

		List<LookupDto> maritalStatusCodes = (List<LookupDto>) mock(List.class);
		when(maritalStatusCodeService.findAllMaritalStatusCodes())
				.thenReturn(maritalStatusCodes);

		List<LookupDto> religiousAffiliationCodes = (List<LookupDto>) mock(List.class);
		when(
				religiousAffiliationCodeService
						.findAllReligiousAffiliationCodes()).thenReturn(
				religiousAffiliationCodes);

		List<LookupDto> raceCodes = (List<LookupDto>) mock(List.class);
		when(raceCodeService.findAllRaceCodes()).thenReturn(raceCodes);

		List<LookupDto> languageCodes = (List<LookupDto>) mock(List.class);
		when(languageCodeService.findAllLanguageCodes()).thenReturn(
				languageCodes);

		List<LookupDto> stateCodes = (List<LookupDto>) mock(List.class);
		when(stateCodeService.findAllStateCodes()).thenReturn(stateCodes);

		mockMvc.perform(get("/patients/profile.html"))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("patientProfileDto"))
				.andExpect(
						model().attribute("patientProfileDto",
								equalTo(patientProfile)))
				.andExpect(
						model().attribute("currentUser", equalTo(currentUser)))
				.andExpect(
						model().attribute("administrativeGenderCodes",
								equalTo(administrativeGenderCodes)))
				.andExpect(model().attributeExists("maritalStatusCodes"))
				.andExpect(
						model().attribute("maritalStatusCodes",
								equalTo(maritalStatusCodes)))
				.andExpect(
						model().attribute("religiousAffiliationCodes",
								equalTo(religiousAffiliationCodes)))
				.andExpect(model().attribute("raceCodes", equalTo(raceCodes)))
				.andExpect(
						model().attribute("languageCodes",
								equalTo(languageCodes)))
				.andExpect(model().attribute("stateCodes", equalTo(stateCodes)))
				.andExpect(model().hasNoErrors())
				.andExpect(view().name("views/patients/profile"))
				.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void testProfile_When_HttpPost_With_Empty_Param_First_Name()
			throws Exception {

		fieldValidator = new FieldValidator();
		ReflectionTestUtils.setField(sut, "fieldValidator", fieldValidator);

		List<LookupDto> administrativeGenderCodes = (List<LookupDto>) mock(List.class);
		when(
				administrativeGenderCodeService
						.findAllAdministrativeGenderCodes()).thenReturn(
				administrativeGenderCodes);

		List<LookupDto> maritalStatusCodes = (List<LookupDto>) mock(List.class);
		when(maritalStatusCodeService.findAllMaritalStatusCodes())
				.thenReturn(maritalStatusCodes);

		List<LookupDto> religiousAffiliationCodes = (List<LookupDto>) mock(List.class);
		when(
				religiousAffiliationCodeService
						.findAllReligiousAffiliationCodes()).thenReturn(
				religiousAffiliationCodes);

		List<LookupDto> raceCodes = (List<LookupDto>) mock(List.class);
		when(raceCodeService.findAllRaceCodes()).thenReturn(raceCodes);

		List<LookupDto> languageCodes = (List<LookupDto>) mock(List.class);
		when(languageCodeService.findAllLanguageCodes()).thenReturn(
				languageCodes);

		List<LookupDto> stateCodes = (List<LookupDto>) mock(List.class);
		when(stateCodeService.findAllStateCodes()).thenReturn(stateCodes);

		mockMvc.perform(
				post("/patients/profile.html")
						.param("firstName", " ")
						.param("lastName", VALID_LAST_NAME )
						.param("birthDate", VALID_BIRTHDAY)
						.param("email", "ex@test.com")
						.param(GENDER_FIELD ,
								VALID_GENDER_CODE ))
				.andExpect(status().isOk())
				.andExpect(
						model().attribute("administrativeGenderCodes",
								equalTo(administrativeGenderCodes)))
				.andExpect(model().attributeExists("maritalStatusCodes"))
				.andExpect(
						model().attribute("maritalStatusCodes",
								equalTo(maritalStatusCodes)))
				.andExpect(
						model().attribute("religiousAffiliationCodes",
								equalTo(religiousAffiliationCodes)))
				.andExpect(model().attribute("raceCodes", equalTo(raceCodes)))
				.andExpect(
						model().attribute("languageCodes",
								equalTo(languageCodes)))
				.andExpect(model().attribute("stateCodes", equalTo(stateCodes)))
				.andExpect(model().hasErrors())
				.andExpect(view().name("views/patients/profile"))
				.andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	public void testHome_when_authority_is_ROLE_USER() throws Exception {
		final String username = "username";
		final AuthenticatedUser currentUser = mock(AuthenticatedUser.class);
		final PatientProfileDto patientProfileDto=mock(PatientProfileDto.class);
		List<SystemNotificationDto> systemNotificationDtos=mock(List.class);
		
		when(currentUser.getUsername()).thenReturn(username);
		when(userContext.getCurrentUser()).thenReturn(currentUser);
		when(patientService.findPatientProfileByUsername(anyString())).thenReturn(patientProfileDto);
		when(systemNotificationService.findAllSystemNotificationDtosByPatient(anyLong())).thenReturn(systemNotificationDtos);
		
		
		mockMvc.perform(get("/patients/home.html"))
			.andExpect(view().name("views/patients/home"))
			.andExpect(model().attribute("systemNotificationDtos", equalTo(systemNotificationDtos)))
			.andExpect(model().attribute("currentUser", equalTo(currentUser)));
	}


	@Test
	public void testProfile_When_HttpPost_With_Invalide_Length_Param_First_Name()
			throws Exception {

		fieldValidator = new FieldValidator();
		ReflectionTestUtils.setField(sut, "fieldValidator", fieldValidator);

		List<LookupDto> administrativeGenderCodes = (List<LookupDto>) mock(List.class);
		when(
				administrativeGenderCodeService
						.findAllAdministrativeGenderCodes()).thenReturn(
				administrativeGenderCodes);

		List<LookupDto> maritalStatusCodes = (List<LookupDto>) mock(List.class);
		when(maritalStatusCodeService.findAllMaritalStatusCodes())
				.thenReturn(maritalStatusCodes);

		List<LookupDto> religiousAffiliationCodes = (List<LookupDto>) mock(List.class);
		when(
				religiousAffiliationCodeService
						.findAllReligiousAffiliationCodes()).thenReturn(
				religiousAffiliationCodes);

		List<LookupDto> raceCodes = (List<LookupDto>) mock(List.class);
		when(raceCodeService.findAllRaceCodes()).thenReturn(raceCodes);

		List<LookupDto> languageCodes = (List<LookupDto>) mock(List.class);
		when(languageCodeService.findAllLanguageCodes()).thenReturn(
				languageCodes);

		List<LookupDto> stateCodes = (List<LookupDto>) mock(List.class);
		when(stateCodeService.findAllStateCodes()).thenReturn(stateCodes);

		mockMvc.perform(
				post("/patients/profile.html")
						.param("firstName", "1")
						.param("lastName", VALID_LAST_NAME )
						.param("birthDate", VALID_BIRTHDAY)
						.param("email", "ex@test.com")
						.param(GENDER_FIELD ,
								VALID_GENDER_CODE ))
				.andExpect(status().isOk())
				.andExpect(
						model().attribute("administrativeGenderCodes",
								equalTo(administrativeGenderCodes)))
				.andExpect(model().attributeExists("maritalStatusCodes"))
				.andExpect(
						model().attribute("maritalStatusCodes",
								equalTo(maritalStatusCodes)))
				.andExpect(
						model().attribute("religiousAffiliationCodes",
								equalTo(religiousAffiliationCodes)))
				.andExpect(model().attribute("raceCodes", equalTo(raceCodes)))
				.andExpect(
						model().attribute("languageCodes",
								equalTo(languageCodes)))
				.andExpect(model().attribute("stateCodes", equalTo(stateCodes)))
				.andExpect(model().hasErrors())
				.andExpect(view().name("views/patients/profile"))
				.andDo(MockMvcResultHandlers.print());

		mockMvc.perform(
				post("/patients/profile.html")
						.param("firstName", "1234567890123456789012345678901")
						.param("lastName", VALID_LAST_NAME )
						.param("birthDate", VALID_BIRTHDAY)
						.param("email", "ex@test.com")
						.param(GENDER_FIELD ,
								VALID_GENDER_CODE ))
				.andExpect(status().isOk())
				.andExpect(
						model().attribute("administrativeGenderCodes",
								equalTo(administrativeGenderCodes)))
				.andExpect(model().attributeExists("maritalStatusCodes"))
				.andExpect(
						model().attribute("maritalStatusCodes",
								equalTo(maritalStatusCodes)))
				.andExpect(
						model().attribute("religiousAffiliationCodes",
								equalTo(religiousAffiliationCodes)))
				.andExpect(model().attribute("raceCodes", equalTo(raceCodes)))
				.andExpect(
						model().attribute("languageCodes",
								equalTo(languageCodes)))
				.andExpect(model().attribute("stateCodes", equalTo(stateCodes)))
				.andExpect(model().hasErrors())
				.andExpect(view().name("views/patients/profile"))
				.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void testProfile_When_HttpPost_With_Empty_Param_Last_Name()
			throws Exception {

		fieldValidator = new FieldValidator();
		ReflectionTestUtils.setField(sut, "fieldValidator", fieldValidator);

		List<LookupDto> administrativeGenderCodes = (List<LookupDto>) mock(List.class);
		when(
				administrativeGenderCodeService
						.findAllAdministrativeGenderCodes()).thenReturn(
				administrativeGenderCodes);

		List<LookupDto> maritalStatusCodes = (List<LookupDto>) mock(List.class);
		when(maritalStatusCodeService.findAllMaritalStatusCodes())
				.thenReturn(maritalStatusCodes);

		List<LookupDto> religiousAffiliationCodes = (List<LookupDto>) mock(List.class);
		when(
				religiousAffiliationCodeService
						.findAllReligiousAffiliationCodes()).thenReturn(
				religiousAffiliationCodes);

		List<LookupDto> raceCodes = (List<LookupDto>) mock(List.class);
		when(raceCodeService.findAllRaceCodes()).thenReturn(raceCodes);

		List<LookupDto> languageCodes = (List<LookupDto>) mock(List.class);
		when(languageCodeService.findAllLanguageCodes()).thenReturn(
				languageCodes);

		List<LookupDto> stateCodes = (List<LookupDto>) mock(List.class);
		when(stateCodeService.findAllStateCodes()).thenReturn(stateCodes);

		mockMvc.perform(
				post("/patients/profile.html")
						.param("lastName", " ")
						.param("firstName",VALID_FIRST_NAME)
						.param("birthDate", VALID_BIRTHDAY)
						.param("email", "ex@test.com")
						.param(GENDER_FIELD ,
								VALID_GENDER_CODE ))
				.andExpect(status().isOk())
				.andExpect(
						model().attribute("administrativeGenderCodes",
								equalTo(administrativeGenderCodes)))
				.andExpect(model().attributeExists("maritalStatusCodes"))
				.andExpect(
						model().attribute("maritalStatusCodes",
								equalTo(maritalStatusCodes)))
				.andExpect(
						model().attribute("religiousAffiliationCodes",
								equalTo(religiousAffiliationCodes)))
				.andExpect(model().attribute("raceCodes", equalTo(raceCodes)))
				.andExpect(
						model().attribute("languageCodes",
								equalTo(languageCodes)))
				.andExpect(model().attribute("stateCodes", equalTo(stateCodes)))
				.andExpect(model().hasErrors())
				.andExpect(view().name("views/patients/profile"))
				.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void testProfile_When_HttpPost_With_Invalide_Length_Param_Last_Name()
			throws Exception {

		fieldValidator = new FieldValidator();
		ReflectionTestUtils.setField(sut, "fieldValidator", fieldValidator);

		List<LookupDto> administrativeGenderCodes = (List<LookupDto>) mock(List.class);
		when(
				administrativeGenderCodeService
						.findAllAdministrativeGenderCodes()).thenReturn(
				administrativeGenderCodes);

		List<LookupDto> maritalStatusCodes = (List<LookupDto>) mock(List.class);
		when(maritalStatusCodeService.findAllMaritalStatusCodes())
				.thenReturn(maritalStatusCodes);

		List<LookupDto> religiousAffiliationCodes = (List<LookupDto>) mock(List.class);
		when(
				religiousAffiliationCodeService
						.findAllReligiousAffiliationCodes()).thenReturn(
				religiousAffiliationCodes);

		List<LookupDto> raceCodes = (List<LookupDto>) mock(List.class);
		when(raceCodeService.findAllRaceCodes()).thenReturn(raceCodes);

		List<LookupDto> languageCodes = (List<LookupDto>) mock(List.class);
		when(languageCodeService.findAllLanguageCodes()).thenReturn(
				languageCodes);

		List<LookupDto> stateCodes = (List<LookupDto>) mock(List.class);
		when(stateCodeService.findAllStateCodes()).thenReturn(stateCodes);

		mockMvc.perform(
				post("/patients/profile.html")
						.param("lastName", "1")
						.param("firstName",VALID_FIRST_NAME)
						.param("birthDate", VALID_BIRTHDAY)
						.param("email", "ex@test.com")
						.param(GENDER_FIELD ,
								VALID_GENDER_CODE ))
				.andExpect(status().isOk())
				.andExpect(
						model().attribute("administrativeGenderCodes",
								equalTo(administrativeGenderCodes)))
				.andExpect(model().attributeExists("maritalStatusCodes"))
				.andExpect(
						model().attribute("maritalStatusCodes",
								equalTo(maritalStatusCodes)))
				.andExpect(
						model().attribute("religiousAffiliationCodes",
								equalTo(religiousAffiliationCodes)))
				.andExpect(model().attribute("raceCodes", equalTo(raceCodes)))
				.andExpect(
						model().attribute("languageCodes",
								equalTo(languageCodes)))
				.andExpect(model().attribute("stateCodes", equalTo(stateCodes)))
				.andExpect(model().hasErrors())
				.andExpect(view().name("views/patients/profile"))
				.andDo(MockMvcResultHandlers.print());

		mockMvc.perform(
				post("/patients/profile.html")
						.param("lastName", "1234567890123456789012345678901")
						.param("firstName",VALID_FIRST_NAME)
						.param("birthDate", VALID_BIRTHDAY)
						.param("email", "ex@test.com")
						.param(GENDER_FIELD ,
								VALID_GENDER_CODE ))
				.andExpect(status().isOk())
				.andExpect(
						model().attribute("administrativeGenderCodes",
								equalTo(administrativeGenderCodes)))
				.andExpect(model().attributeExists("maritalStatusCodes"))
				.andExpect(
						model().attribute("maritalStatusCodes",
								equalTo(maritalStatusCodes)))
				.andExpect(
						model().attribute("religiousAffiliationCodes",
								equalTo(religiousAffiliationCodes)))
				.andExpect(model().attribute("raceCodes", equalTo(raceCodes)))
				.andExpect(
						model().attribute("languageCodes",
								equalTo(languageCodes)))
				.andExpect(model().attribute("stateCodes", equalTo(stateCodes)))
				.andExpect(model().hasErrors())
				.andExpect(view().name("views/patients/profile"))
				.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void testProfile_When_HttpPost_With_Null_Param_Birth_Date()
			throws Exception {

		fieldValidator = new FieldValidator();
		ReflectionTestUtils.setField(sut, "fieldValidator", fieldValidator);

		List<LookupDto> administrativeGenderCodes = (List<LookupDto>) mock(List.class);
		when(
				administrativeGenderCodeService
						.findAllAdministrativeGenderCodes()).thenReturn(
				administrativeGenderCodes);

		List<LookupDto> maritalStatusCodes = (List<LookupDto>) mock(List.class);
		when(maritalStatusCodeService.findAllMaritalStatusCodes())
				.thenReturn(maritalStatusCodes);

		List<LookupDto> religiousAffiliationCodes = (List<LookupDto>) mock(List.class);
		when(
				religiousAffiliationCodeService
						.findAllReligiousAffiliationCodes()).thenReturn(
				religiousAffiliationCodes);

		List<LookupDto> raceCodes = (List<LookupDto>) mock(List.class);
		when(raceCodeService.findAllRaceCodes()).thenReturn(raceCodes);

		List<LookupDto> languageCodes = (List<LookupDto>) mock(List.class);
		when(languageCodeService.findAllLanguageCodes()).thenReturn(
				languageCodes);

		List<LookupDto> stateCodes = (List<LookupDto>) mock(List.class);
		when(stateCodeService.findAllStateCodes()).thenReturn(stateCodes);

		mockMvc.perform(
				post("/patients/profile.html")
						.param("firstName",VALID_FIRST_NAME)
						.param("lastName", VALID_LAST_NAME )
						.param("email", "ex@test.com")
						.param(GENDER_FIELD ,
								VALID_GENDER_CODE ))
				.andExpect(status().isOk())
				.andExpect(
						model().attribute("administrativeGenderCodes",
								equalTo(administrativeGenderCodes)))
				.andExpect(model().attributeExists("maritalStatusCodes"))
				.andExpect(
						model().attribute("maritalStatusCodes",
								equalTo(maritalStatusCodes)))
				.andExpect(
						model().attribute("religiousAffiliationCodes",
								equalTo(religiousAffiliationCodes)))
				.andExpect(model().attribute("raceCodes", equalTo(raceCodes)))
				.andExpect(
						model().attribute("languageCodes",
								equalTo(languageCodes)))
				.andExpect(model().attribute("stateCodes", equalTo(stateCodes)))
				.andExpect(model().hasErrors())
				.andExpect(view().name("views/patients/profile"))
				.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void testProfile_When_HttpPost_With_Invalid_Param_Birth_Date()
			throws Exception {

		fieldValidator = new FieldValidator();
		ReflectionTestUtils.setField(sut, "fieldValidator", fieldValidator);

		List<LookupDto> administrativeGenderCodes = (List<LookupDto>) mock(List.class);
		when(
				administrativeGenderCodeService
						.findAllAdministrativeGenderCodes()).thenReturn(
				administrativeGenderCodes);

		List<LookupDto> maritalStatusCodes = (List<LookupDto>) mock(List.class);
		when(maritalStatusCodeService.findAllMaritalStatusCodes())
				.thenReturn(maritalStatusCodes);

		List<LookupDto> religiousAffiliationCodes = (List<LookupDto>) mock(List.class);
		when(
				religiousAffiliationCodeService
						.findAllReligiousAffiliationCodes()).thenReturn(
				religiousAffiliationCodes);

		List<LookupDto> raceCodes = (List<LookupDto>) mock(List.class);
		when(raceCodeService.findAllRaceCodes()).thenReturn(raceCodes);

		List<LookupDto> languageCodes = (List<LookupDto>) mock(List.class);
		when(languageCodeService.findAllLanguageCodes()).thenReturn(
				languageCodes);

		List<LookupDto> stateCodes = (List<LookupDto>) mock(List.class);
		when(stateCodeService.findAllStateCodes()).thenReturn(stateCodes);

		mockMvc.perform(
				post("/patients/profile.html")
						.param("birthDate", "12/31/1899")
						.param("firstName", " ")
						.param("lastName", VALID_LAST_NAME )
						.param("email", "ex@test.com")
						.param(GENDER_FIELD ,
								VALID_GENDER_CODE ))
				.andExpect(
						model().attribute("administrativeGenderCodes",
								equalTo(administrativeGenderCodes)))
				.andExpect(model().attributeExists("maritalStatusCodes"))
				.andExpect(
						model().attribute("maritalStatusCodes",
								equalTo(maritalStatusCodes)))
				.andExpect(
						model().attribute("religiousAffiliationCodes",
								equalTo(religiousAffiliationCodes)))
				.andExpect(model().attribute("raceCodes", equalTo(raceCodes)))
				.andExpect(
						model().attribute("languageCodes",
								equalTo(languageCodes)))
				.andExpect(model().attribute("stateCodes", equalTo(stateCodes)))
				.andExpect(model().hasErrors())
				.andExpect(view().name("views/patients/profile"))
				.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void testProfile_When_HttpPost_With_Empty_Param_Email()
			throws Exception {

		fieldValidator = new FieldValidator();
		ReflectionTestUtils.setField(sut, "fieldValidator", fieldValidator);

		List<LookupDto> administrativeGenderCodes = (List<LookupDto>) mock(List.class);
		when(
				administrativeGenderCodeService
						.findAllAdministrativeGenderCodes()).thenReturn(
				administrativeGenderCodes);

		List<LookupDto> maritalStatusCodes = (List<LookupDto>) mock(List.class);
		when(maritalStatusCodeService.findAllMaritalStatusCodes())
				.thenReturn(maritalStatusCodes);

		List<LookupDto> religiousAffiliationCodes = (List<LookupDto>) mock(List.class);
		when(
				religiousAffiliationCodeService
						.findAllReligiousAffiliationCodes()).thenReturn(
				religiousAffiliationCodes);

		List<LookupDto> raceCodes = (List<LookupDto>) mock(List.class);
		when(raceCodeService.findAllRaceCodes()).thenReturn(raceCodes);

		List<LookupDto> languageCodes = (List<LookupDto>) mock(List.class);
		when(languageCodeService.findAllLanguageCodes()).thenReturn(
				languageCodes);

		List<LookupDto> stateCodes = (List<LookupDto>) mock(List.class);
		when(stateCodeService.findAllStateCodes()).thenReturn(stateCodes);

		mockMvc.perform(
				post("/patients/profile.html")
						.param("firstName",VALID_FIRST_NAME)
						.param("lastName", VALID_LAST_NAME )
						.param("birthDate", VALID_BIRTHDAY)
						.param("email", " ")
						.param(GENDER_FIELD ,
								VALID_GENDER_CODE ))
				.andExpect(
						model().attribute("administrativeGenderCodes",
								equalTo(administrativeGenderCodes)))
				.andExpect(model().attributeExists("maritalStatusCodes"))
				.andExpect(
						model().attribute("maritalStatusCodes",
								equalTo(maritalStatusCodes)))
				.andExpect(
						model().attribute("religiousAffiliationCodes",
								equalTo(religiousAffiliationCodes)))
				.andExpect(model().attribute("raceCodes", equalTo(raceCodes)))
				.andExpect(
						model().attribute("languageCodes",
								equalTo(languageCodes)))
				.andExpect(model().attribute("stateCodes", equalTo(stateCodes)))
				.andExpect(model().hasErrors())
				.andExpect(view().name("views/patients/profile"))
				.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void testProfile_When_HttpPost_With_Invalid_Param_Email()
			throws Exception {

		fieldValidator = new FieldValidator();
		ReflectionTestUtils.setField(sut, "fieldValidator", fieldValidator);

		List<LookupDto> administrativeGenderCodes = (List<LookupDto>) mock(List.class);
		when(
				administrativeGenderCodeService
						.findAllAdministrativeGenderCodes()).thenReturn(
				administrativeGenderCodes);

		List<LookupDto> maritalStatusCodes = (List<LookupDto>) mock(List.class);
		when(maritalStatusCodeService.findAllMaritalStatusCodes())
				.thenReturn(maritalStatusCodes);

		List<LookupDto> religiousAffiliationCodes = (List<LookupDto>) mock(List.class);
		when(
				religiousAffiliationCodeService
						.findAllReligiousAffiliationCodes()).thenReturn(
				religiousAffiliationCodes);

		List<LookupDto> raceCodes = (List<LookupDto>) mock(List.class);
		when(raceCodeService.findAllRaceCodes()).thenReturn(raceCodes);

		List<LookupDto> languageCodes = (List<LookupDto>) mock(List.class);
		when(languageCodeService.findAllLanguageCodes()).thenReturn(
				languageCodes);

		List<LookupDto> stateCodes = (List<LookupDto>) mock(List.class);
		when(stateCodeService.findAllStateCodes()).thenReturn(stateCodes);

		mockMvc.perform(
				post("/patients/profile.html")
						.param("firstName",VALID_FIRST_NAME)
						.param("lastName", VALID_LAST_NAME )
						.param("birthDate", VALID_BIRTHDAY)
						.param("email", "not-email-address")
						.param(GENDER_FIELD ,
								VALID_GENDER_CODE ))
				.andExpect(
						model().attribute("administrativeGenderCodes",
								equalTo(administrativeGenderCodes)))
				.andExpect(model().attributeExists("maritalStatusCodes"))
				.andExpect(
						model().attribute("maritalStatusCodes",
								equalTo(maritalStatusCodes)))
				.andExpect(
						model().attribute("religiousAffiliationCodes",
								equalTo(religiousAffiliationCodes)))
				.andExpect(model().attribute("raceCodes", equalTo(raceCodes)))
				.andExpect(
						model().attribute("languageCodes",
								equalTo(languageCodes)))
				.andExpect(model().attribute("stateCodes", equalTo(stateCodes)))
				.andExpect(model().hasErrors())
				.andExpect(view().name("views/patients/profile"))
				.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void testProfile_When_HttpPost_With_Valid_Params() throws Exception {

		final String username = "username";

		final AuthenticatedUser currentUser = mock(AuthenticatedUser.class);
		when(currentUser.getUsername()).thenReturn(username);

		when(userContext.getCurrentUser()).thenReturn(currentUser);

		fieldValidator = new FieldValidator();
		ReflectionTestUtils.setField(sut, "fieldValidator", fieldValidator);

		List<LookupDto> administrativeGenderCodes = (List<LookupDto>) mock(List.class);
		when(
				administrativeGenderCodeService
						.findAllAdministrativeGenderCodes()).thenReturn(
				administrativeGenderCodes);

		List<LookupDto> maritalStatusCodes = (List<LookupDto>) mock(List.class);
		when(maritalStatusCodeService.findAllMaritalStatusCodes())
				.thenReturn(maritalStatusCodes);

		List<LookupDto> religiousAffiliationCodes = (List<LookupDto>) mock(List.class);
		when(
				religiousAffiliationCodeService
						.findAllReligiousAffiliationCodes()).thenReturn(
				religiousAffiliationCodes);

		List<LookupDto> raceCodes = (List<LookupDto>) mock(List.class);
		when(raceCodeService.findAllRaceCodes()).thenReturn(raceCodes);

		List<LookupDto> languageCodes = (List<LookupDto>) mock(List.class);
		when(languageCodeService.findAllLanguageCodes()).thenReturn(
				languageCodes);

		List<LookupDto> stateCodes = (List<LookupDto>) mock(List.class);
		when(stateCodeService.findAllStateCodes()).thenReturn(stateCodes);
		
		when(pixService.getEid(VALID_MRN)).thenReturn(VALID_EID);

		mockMvc.perform(
				post("/patients/profile.html")
						.param("firstName",VALID_FIRST_NAME)
						.param("lastName", VALID_LAST_NAME )
						.param("birthDate", VALID_BIRTHDAY)
						.param("email", VALID_EMAIL)
						.param(GENDER_FIELD ,
								VALID_GENDER_CODE )
						.param("username", username)
						.param("password","password")
						.param("medicalRecordNumber",VALID_MRN))
				.andExpect(
						model().attribute("currentUser", equalTo(currentUser)))
				.andExpect(
						model().attribute("updatedMessage", "Updated your profile successfully!"))
				.andExpect(
						model().attribute("administrativeGenderCodes",
								equalTo(administrativeGenderCodes)))
				.andExpect(model().attributeExists("maritalStatusCodes"))
				.andExpect(
						model().attribute("maritalStatusCodes",
								equalTo(maritalStatusCodes)))
				.andExpect(
						model().attribute("religiousAffiliationCodes",
								equalTo(religiousAffiliationCodes)))
				.andExpect(model().attribute("raceCodes", equalTo(raceCodes)))
				.andExpect(
						model().attribute("languageCodes",
								equalTo(languageCodes)))
				.andExpect(model().attribute("stateCodes", equalTo(stateCodes)))
				.andExpect(model().hasNoErrors())
				.andExpect(view().name("views/patients/profile"))
				.andDo(MockMvcResultHandlers.print());
		
		ArgumentMatcher<PatientProfileDto> patientProfileArgumentMatcher = new ArgumentMatcher<PatientProfileDto>(){

			@Override
			public boolean matches(Object argument) {
				PatientProfileDto patientProfileDto = (PatientProfileDto)argument;
				if (patientProfileDto == null){
					return false;
				}
				
				if (patientProfileDto.getAddressCountryCode() == null || !patientProfileDto.getAddressCountryCode().equals("US")){
					return false;
				}
				
				if (patientProfileDto.getUsername() == null || !patientProfileDto.getUsername().equals(username)){
					return false;
				}
				
				if (patientProfileDto.getFirstName() == null || !patientProfileDto.getFirstName().equals(VALID_FIRST_NAME)){
					return false;
				}
				
				if (patientProfileDto.getLastName() == null || !patientProfileDto.getLastName().equals(VALID_LAST_NAME)){
					return false;
				}
				
				if (patientProfileDto.getEmail() == null || !patientProfileDto.getEmail().equals(VALID_EMAIL)){
					return false;
				}
				
				if (patientProfileDto.getAdministrativeGenderCode() == null || !patientProfileDto.getAdministrativeGenderCode().equals(VALID_GENDER_CODE)){
					return false;
				}
											
								
				return true;
			}
			
		};
		
		verify(patientService, times(1)).updatePatient(org.mockito.Matchers.argThat(patientProfileArgumentMatcher));
	}
}
