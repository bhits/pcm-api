package gov.samhsa.consent2share.service.admin.pg;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import gov.samhsa.consent2share.common.AuthenticatedUser;
import gov.samhsa.consent2share.common.UserContext;
import gov.samhsa.consent2share.domain.account.Users;
import gov.samhsa.consent2share.domain.account.UsersRepository;
import gov.samhsa.consent2share.domain.commondomainservices.EmailSender;
import gov.samhsa.consent2share.domain.patient.Patient;
import gov.samhsa.consent2share.domain.patient.PatientLegalRepresentativeAssociationRepository;
import gov.samhsa.consent2share.domain.patient.PatientRepository;
import gov.samhsa.consent2share.domain.staff.Staff;
import gov.samhsa.consent2share.domain.staff.StaffRepository;
import gov.samhsa.consent2share.infrastructure.DtoToDomainEntityMapper;
import gov.samhsa.consent2share.infrastructure.PixService;
import gov.samhsa.consent2share.infrastructure.security.AuthenticationFailedException;
import gov.samhsa.consent2share.service.account.MrnService;
import gov.samhsa.consent2share.service.admin.AdminProfileDtoToAdministratorMapper;
import gov.samhsa.consent2share.service.dto.AdminProfileDto;
import gov.samhsa.consent2share.service.dto.PatientProfileDto;
import gov.samhsa.consent2share.service.spirit.SpiritClientNotAvailableException;
import gov.samhsa.consent2share.service.spirit.SpiritQueryService;
import gov.samhsa.spirit.wsclient.dto.PatientDto;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

@RunWith(MockitoJUnitRunner.class)
public class AdminServiceImplTest {

	@Mock
	private PatientRepository patientRepository;

	@Mock
	private PatientLegalRepresentativeAssociationRepository patientLegalRepresentativeAssociationRepository;

	@Mock
	private ModelMapper modelMapper;

	@Mock
	private UserContext userContext;

	@Mock
	private AdminProfileDtoToAdministratorMapper adminProfileDtoToAdministratorMapper;

	@Mock
	private DtoToDomainEntityMapper<PatientProfileDto, Patient> patientProfileDtoToPatientMapper;

	@Mock
	private UsersRepository usersRepository;

	@Mock
	private PasswordEncoder passwordEncoder;

	@Mock
	private EmailSender emailSender;

	@Mock
	private SpiritQueryService spiritQueryService;

	@Mock
	private StaffRepository administratorRepository;

	@Mock
	private DtoToDomainEntityMapper<PatientDto, Patient> patientDtoToPatientMapper;

	@Mock
	private MrnService mrnService;

	@Mock
	private PixService pixService;

	@InjectMocks
	private AdminServiceImpl sut = new AdminServiceImpl(1,
			administratorRepository, patientRepository,
			patientProfileDtoToPatientMapper,
			adminProfileDtoToAdministratorMapper, modelMapper, userContext,
			passwordEncoder, emailSender, null, null, usersRepository, null,
			spiritQueryService, patientDtoToPatientMapper, mrnService,
			pixService);

	@Test
	public void testFindPatientProfileByUsername() {
		// Arrange
		final Staff admin = mock(Staff.class);
		when(administratorRepository.findByUsername(anyString())).thenReturn(
				admin);
		final AdminProfileDto expectedPAdminProfileDto = mock(AdminProfileDto.class);
		when(modelMapper.map(admin, AdminProfileDto.class)).thenReturn(
				expectedPAdminProfileDto);

		// Act
		AdminProfileDto result = sut.findAdminProfileByUsername(anyString());

		// Assert
		assertEquals(expectedPAdminProfileDto, result);
	}

	@Test
	public void testUpdateAdministrator_when_authentication_success()
			throws AuthenticationFailedException {
		final String username = "TheUsername";
		AuthenticatedUser authenticatedUser = mock(AuthenticatedUser.class);
		when(authenticatedUser.getUsername()).thenReturn(username);
		when(userContext.getCurrentUser()).thenReturn(authenticatedUser);

		Users user = mock(Users.class);
		when(user.getUsername()).thenReturn(username);
		when(user.getPassword()).thenReturn("hashedpassword");
		when(usersRepository.loadUserByUsername(anyString())).thenReturn(user);
		when(passwordEncoder.matches("rawpassword", "hashedpassword"))
				.thenReturn(true);

		// Arrange
		AdminProfileDto adminProfileDtoInput = mock(AdminProfileDto.class);
		Staff admin = mock(Staff.class);
		when(adminProfileDtoInput.getUsername()).thenReturn(username);
		when(adminProfileDtoInput.getPassword()).thenReturn("rawpassword");
		when(adminProfileDtoInput.getFirstName()).thenReturn("albert");
		when(adminProfileDtoInput.getLastName()).thenReturn("Smith");
		when(adminProfileDtoInput.getEmail()).thenReturn(
				"albert.smith@consent2share.com");
		when(adminProfileDtoToAdministratorMapper.map(adminProfileDtoInput))
				.thenReturn(admin);

		// Act
		sut.updateAdministrator(adminProfileDtoInput);

		// Assert
		verify(administratorRepository, times(1)).save(admin);
	}

	@Test
	public void testUpdateAdministrator_when_username_does_not_match_and_so_authentication_fails() {
		AuthenticatedUser authenticatedUser = mock(AuthenticatedUser.class);
		when(authenticatedUser.getUsername()).thenReturn("TheUsername");
		when(userContext.getCurrentUser()).thenReturn(authenticatedUser);
		Users user = mock(Users.class);
		when(user.getUsername()).thenReturn("AnotherUsername");

		// Arrange
		AdminProfileDto adminProfileDtoInput = mock(AdminProfileDto.class);
		when(adminProfileDtoInput.getUsername()).thenReturn("AnotherUsername");

		// Act
		try {
			sut.updateAdministrator(adminProfileDtoInput);
		} catch (AuthenticationFailedException e) {
			assertEquals(e.getMessage(),
					"Username does not match current active user.");
		}

		// Assert
		verify(administratorRepository, times(0)).save(any(Staff.class));
	}

	@Test
	public void testUpdateAdministrator_when_username_matches_but_password_is_wrong() {
		final String username = "TheUsername";
		AuthenticatedUser authenticatedUser = mock(AuthenticatedUser.class);
		when(authenticatedUser.getUsername()).thenReturn(username);
		when(userContext.getCurrentUser()).thenReturn(authenticatedUser);

		Users user = mock(Users.class);
		when(user.getUsername()).thenReturn(username);
		when(user.getPassword()).thenReturn("hashedpassword");
		when(usersRepository.loadUserByUsername(anyString())).thenReturn(user);
		when(passwordEncoder.matches("rawpassword", "hashedpassword"))
				.thenReturn(false);

		// Arrange
		AdminProfileDto adminProfileDtoInput = mock(AdminProfileDto.class);
		Staff admin = mock(Staff.class);
		when(adminProfileDtoInput.getUsername()).thenReturn(username);
		when(adminProfileDtoInput.getPassword()).thenReturn("rawpassword");
		when(adminProfileDtoInput.getFirstName()).thenReturn("albert");
		when(adminProfileDtoInput.getLastName()).thenReturn("Smith");
		when(adminProfileDtoInput.getEmail()).thenReturn(
				"albert.smith@consent2share.com");
		when(adminProfileDtoToAdministratorMapper.map(adminProfileDtoInput))
				.thenReturn(admin);

		// Act
		try {
			sut.updateAdministrator(adminProfileDtoInput);
		} catch (AuthenticationFailedException e) {
			assertEquals(e.getMessage(), "Password is incorrect.");
		}
		// Assert
		verify(administratorRepository, times(0)).save(admin);
	}

	@Test
	public void testUpdatePatient() throws SpiritClientNotAvailableException {
		Patient patient = mock(Patient.class);
		PatientProfileDto patientDto = mock(PatientProfileDto.class);
		when(patientProfileDtoToPatientMapper.map(patientDto)).thenReturn(
				patient);
		sut.updatePatient(patientDto);
		verify(patientRepository, times(1)).save(patient);
		verify(spiritQueryService, times(1)).updatePatient(patientDto);
	}
}
