package gov.samhsa.mhc.pcm.service.patient;

import gov.samhsa.mhc.pcm.domain.account.Users;
import gov.samhsa.mhc.pcm.domain.account.UsersRepository;
import gov.samhsa.mhc.pcm.domain.commondomainservices.EmailSender;
import gov.samhsa.mhc.pcm.domain.patient.*;
import gov.samhsa.mhc.pcm.domain.provider.IndividualProvider;
import gov.samhsa.mhc.pcm.domain.provider.OrganizationalProvider;
import gov.samhsa.mhc.pcm.service.dto.AddConsentIndividualProviderDto;
import gov.samhsa.mhc.pcm.service.dto.AddConsentOrganizationalProviderDto;
import gov.samhsa.mhc.pcm.service.dto.PatientAdminDto;
import gov.samhsa.mhc.pcm.service.dto.PatientProfileDto;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.mail.AuthenticationFailedException;
import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PatientServiceImplTest {

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private PatientLegalRepresentativeAssociationRepository patientLegalRepresentativeAssociationRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private EmailSender emailSender;

    @InjectMocks
    private PatientServiceImpl sut = new PatientServiceImpl(patientRepository,
            patientLegalRepresentativeAssociationRepository, modelMapper,
            passwordEncoder, emailSender);

    @Test
    public void testCountAllPatients() {
        // Arrange
        final Long expectedCount = new Long(100);
        when(patientRepository.count()).thenReturn(expectedCount);

        // Act
        Long result = sut.countAllPatients();

        // Assert
        assertEquals(expectedCount, result);
    }

    @Test
    public void testFindPatient() {
        // Arrange
        final Patient patient = mock(Patient.class);
        when(patientRepository.findOne(anyLong())).thenReturn(patient);
        final PatientProfileDto expectedPatientProfileDto = mock(PatientProfileDto.class);
        when(modelMapper.map(patient, PatientProfileDto.class)).thenReturn(
                expectedPatientProfileDto);

        // Act
        PatientProfileDto result = sut.findPatient(anyLong());

        // Assert
        assertEquals(expectedPatientProfileDto, result);
    }

    @Test
    public void testFindIdByUsername() {
        // Arrange
        final Patient patient = mock(Patient.class);
        when(patientRepository.findByUsername(anyString())).thenReturn(patient);
        final Long expectedId = new Long(100);
        when(patient.getId()).thenReturn(expectedId);

        // Act
        Long result = sut.findIdByUsername(anyString());

        // Assert
        assertEquals(expectedId, result);
    }

    @Test
    public void testFindPatientEmailByUsername() {
        // Arrange
        final Patient patient = mock(Patient.class);
        when(patientRepository.findByUsername(anyString())).thenReturn(patient);
        final String expectedEmail = "test@test.com";
        when(patient.getEmail()).thenReturn(expectedEmail);

        // Act
        String result = sut.findPatientEmailByUsername(anyString());

        // Assert
        assertEquals(expectedEmail, result);
    }

    @Test
    public void testFindPatientProfileByUsername() {
        // Arrange
        final Patient patient = mock(Patient.class);
        when(patientRepository.findByUsername(anyString())).thenReturn(patient);
        final PatientProfileDto expectedPatientProfileDto = mock(PatientProfileDto.class);
        when(modelMapper.map(patient, PatientProfileDto.class)).thenReturn(
                expectedPatientProfileDto);

        // Act
        PatientProfileDto result = sut
                .findPatientProfileByUsername(anyString());

        // Assert
        assertEquals(expectedPatientProfileDto, result);
    }

    @Test
    public void testFindPatientEntries() {
        // Arrange
        final int pageNumber = 1;
        final int pageSize = 9;
        final Patient patient1 = mock(Patient.class);
        final Patient patient2 = mock(Patient.class);
        List<Patient> patientList = new ArrayList<Patient>();
        patientList.add(patient1);
        patientList.add(patient2);

        int numberOfPatients = patientList.size();

        Page<Patient> patientPage = mock(Page.class);
        when(patientPage.getContent()).thenReturn(patientList);

        ArgumentMatcher<PageRequest> pageRequestArgumentMatcher = new ArgumentMatcher<PageRequest>() {

            @Override
            public boolean matches(Object argument) {
                PageRequest pageRequest = (PageRequest) argument;
                if (pageRequest == null) {
                    return false;
                }

                if (pageRequest.getPageSize() != pageSize) {
                    return false;
                }

                if (pageRequest.getPageNumber() != pageNumber) {
                    return false;
                }

                return true;
            }
        };

        when(patientRepository.findAll(argThat(pageRequestArgumentMatcher)))
                .thenReturn(patientPage);

        final PatientProfileDto expectedPatientProfileDto1 = mock(PatientProfileDto.class);
        when(modelMapper.map(patient1, PatientProfileDto.class)).thenReturn(
                expectedPatientProfileDto1);

        final PatientProfileDto expectedPatientProfileDto2 = mock(PatientProfileDto.class);
        when(modelMapper.map(patient2, PatientProfileDto.class)).thenReturn(
                expectedPatientProfileDto2);

        // Act
        List<PatientProfileDto> result = sut.findPatientEntries(pageNumber,
                pageSize);

        // Assert
        assertEquals(numberOfPatients, result.size());
        for (PatientProfileDto patientProfileDto : result) {
            assertTrue(patientProfileDto == expectedPatientProfileDto1
                    || patientProfileDto == expectedPatientProfileDto2);
        }

        assertTrue(result.get(0) != result.get(1));
    }

    @Test
    public void testSavePatient() {
        // Arrange
        PatientProfileDto patientProfileDtoInput = mock(PatientProfileDto.class);
        Patient patient = mock(Patient.class);
        PatientProfileDto expectedPatientProfileDto = mock(PatientProfileDto.class);
        when(modelMapper.map(patient, PatientProfileDto.class)).thenReturn(
                expectedPatientProfileDto);
        when(modelMapper.map(patientProfileDtoInput, Patient.class)).thenReturn(
                patient);

        // Act
        PatientProfileDto result = sut.savePatient(patientProfileDtoInput);

        // Assert
        verify(patientRepository, times(1)).save(patient);
        assertEquals(expectedPatientProfileDto, result);
    }

    @Test
    public void testUpdatePatient_when_authentication_success()
            throws AuthenticationFailedException {
        // Arrange
        final String username = "TheUsername";
        Users user = mock(Users.class);
        when(user.getUsername()).thenReturn(username);
        when(user.getPassword()).thenReturn("hashedpassword");
        when(usersRepository.loadUserByUsername(anyString())).thenReturn(user);
        when(passwordEncoder.matches("rawpassword", "hashedpassword"))
                .thenReturn(true);
        PatientProfileDto patientProfileDtoInput = mock(PatientProfileDto.class);
        Patient patient = mock(Patient.class);
        when(patientProfileDtoInput.getUsername()).thenReturn(username);
        when(patientProfileDtoInput.getPassword()).thenReturn("rawpassword");
        when(patientProfileDtoInput.getFirstName()).thenReturn("albert");
        when(patientProfileDtoInput.getLastName()).thenReturn("Smith");
        when(patientProfileDtoInput.getEmail()).thenReturn(
                "albert.smith@consent2share.com");
        Patient initialpatient = mock(Patient.class);
        when(patientRepository.findByUsername(username)).thenReturn(
                initialpatient);
        when(initialpatient.getEnterpriseIdentifier()).thenReturn("FAM.123");
        when(modelMapper.map(patientProfileDtoInput, Patient.class)).thenReturn(patient);

        // Act
        sut.updatePatient(patientProfileDtoInput);

        // Assert
        verify(patientRepository, times(1)).save(patient);
    }

    @Ignore("Need to revisit this approach considering OAuth2 security")
    @Test
    public void testUpdatePatient_when_username_does_not_match_and_so_authentication_fails() {
        // Arrange
        Users user = mock(Users.class);
        Patient patient = mock(Patient.class);
        when(user.getUsername()).thenReturn("AnotherUsername");
        PatientProfileDto patientProfileDtoInput = mock(PatientProfileDto.class);
        when(patientProfileDtoInput.getUsername())
                .thenReturn("AnotherUsername");
        when(modelMapper.map(patientProfileDtoInput, Patient.class)).thenReturn(patient);

        // Act
        sut.updatePatient(patientProfileDtoInput);

        // Assert
        verify(patientRepository, times(0)).save(patient);
    }

    @Test
    public void testUpdatePatient_when_username_matches_but_password_is_wrong() {
        final String username = "TheUsername";
        Users user = mock(Users.class);
        when(user.getUsername()).thenReturn(username);
        when(user.getPassword()).thenReturn("hashedpassword");
        when(usersRepository.loadUserByUsername(anyString())).thenReturn(user);
        when(passwordEncoder.matches("rawpassword", "hashedpassword"))
                .thenReturn(false);

        // Arrange
        PatientProfileDto patientProfileDtoInput = mock(PatientProfileDto.class);
        Patient patient = mock(Patient.class);
        when(patientProfileDtoInput.getUsername()).thenReturn(username);
        when(patientProfileDtoInput.getPassword()).thenReturn("rawpassword");
        when(patientProfileDtoInput.getFirstName()).thenReturn("albert");
        when(patientProfileDtoInput.getLastName()).thenReturn("Smith");
        when(patientProfileDtoInput.getEmail()).thenReturn(
                "albert.smith@consent2share.com");

        // Act
        sut.updatePatient(patientProfileDtoInput);
        // Assert
        verify(patientRepository, times(0)).save(patient);
    }

    @Test
    public void testFindAddConsentIndividualProviderDtoByUsername() {
        // Arrange
        final String username = "TheUsername";
        Patient patient = mock(Patient.class);
        when(patientRepository.findByUsername(username)).thenReturn(patient);

        IndividualProvider provider1 = mock(IndividualProvider.class);
        IndividualProvider provider2 = mock(IndividualProvider.class);

        Set<IndividualProvider> providerSet = new HashSet();
        providerSet.add(provider1);
        providerSet.add(provider2);

        when(patient.getIndividualProviders()).thenReturn(providerSet);

        AddConsentIndividualProviderDto providerDto1 = mock(AddConsentIndividualProviderDto.class);
        AddConsentIndividualProviderDto providerDto2 = mock(AddConsentIndividualProviderDto.class);

        when(modelMapper.map(provider1, AddConsentIndividualProviderDto.class))
                .thenReturn(providerDto1);
        when(modelMapper.map(provider2, AddConsentIndividualProviderDto.class))
                .thenReturn(providerDto2);

        // Act
        List<AddConsentIndividualProviderDto> result = sut
                .findAddConsentIndividualProviderDtoByUsername(username);

        // Assert
        // Assert
        assertEquals(2, result.size());
        for (AddConsentIndividualProviderDto providerDto : result) {
            assertTrue(providerDto == providerDto1
                    || providerDto == providerDto2);
        }

        assertTrue(result.get(0) != result.get(1));
    }

    @Test
    public void testFindAddConsentOrganizationalProviderDtoByUsername() {
        // Arrange
        final String username = "TheUsername";
        Patient patient = mock(Patient.class);
        when(patientRepository.findByUsername(username)).thenReturn(patient);

        OrganizationalProvider provider1 = mock(OrganizationalProvider.class);
        OrganizationalProvider provider2 = mock(OrganizationalProvider.class);

        Set<OrganizationalProvider> providerSet = new HashSet();
        providerSet.add(provider1);
        providerSet.add(provider2);

        when(patient.getOrganizationalProviders()).thenReturn(providerSet);

        AddConsentOrganizationalProviderDto providerDto1 = mock(AddConsentOrganizationalProviderDto.class);
        AddConsentOrganizationalProviderDto providerDto2 = mock(AddConsentOrganizationalProviderDto.class);

        when(
                modelMapper.map(provider1,
                        AddConsentOrganizationalProviderDto.class)).thenReturn(
                providerDto1);
        when(
                modelMapper.map(provider2,
                        AddConsentOrganizationalProviderDto.class)).thenReturn(
                providerDto2);

        // Act
        List<AddConsentOrganizationalProviderDto> result = sut
                .findAddConsentOrganizationalProviderDtoByUsername(username);

        // Assert
        // Assert
        assertEquals(2, result.size());
        for (AddConsentOrganizationalProviderDto providerDto : result) {
            assertTrue(providerDto == providerDto1
                    || providerDto == providerDto2);
        }

        assertTrue(result.get(0) != result.get(1));
    }

    @Test
    public void testIsLegalRepForCurrentUser_Given_LegalRep_Not_For_Current_User() {
        // Arrange
        final Long patientOneId = new Long(1);
        final Long patientTwoId = new Long(2);

        final Long legalRepIdInput = new Long(100);

        final String username = "TheUsername";

        Patient patient1 = mock(Patient.class);
        when(patient1.getId()).thenReturn(patientOneId);
        when(patientRepository.findByUsername(username)).thenReturn(patient1);

        PatientLegalRepresentativeAssociation association = mock(PatientLegalRepresentativeAssociation.class);
        List<PatientLegalRepresentativeAssociation> associations = new ArrayList<PatientLegalRepresentativeAssociation>();
        associations.add(association);
        when(
                patientLegalRepresentativeAssociationRepository
                        .findByPatientLegalRepresentativeAssociationPkLegalRepresentativeId(legalRepIdInput))
                .thenReturn(associations);

        PatientLegalRepresentativeAssociationPk associationPk = mock(PatientLegalRepresentativeAssociationPk.class);
        when(association.getPatientLegalRepresentativeAssociationPk())
                .thenReturn(associationPk);
        Patient patient2 = mock(Patient.class);
        when(patient2.getId()).thenReturn(patientTwoId);
        when(associationPk.getPatient()).thenReturn(patient2);

        // Act
        boolean result = sut.isLegalRepForCurrentUser(username, legalRepIdInput);

        // Assert
        assertFalse(result);
    }

    @Test
    public void testIsLegalRepForCurrentUser_Given_LegalRep_For_Current_User() {
        // Arrange
        final Long patientOneId = new Long(1);
        final Long patientTwoId = new Long(1);

        final Long legalRepIdInput = new Long(100);

        final String username = "TheUsername";

        Patient patient1 = mock(Patient.class);
        when(patient1.getId()).thenReturn(patientOneId);
        when(patientRepository.findByUsername(username)).thenReturn(patient1);

        PatientLegalRepresentativeAssociation association = mock(PatientLegalRepresentativeAssociation.class);
        List<PatientLegalRepresentativeAssociation> associations = new ArrayList<PatientLegalRepresentativeAssociation>();
        associations.add(association);
        when(
                patientLegalRepresentativeAssociationRepository
                        .findByPatientLegalRepresentativeAssociationPkLegalRepresentativeId(legalRepIdInput))
                .thenReturn(associations);

        PatientLegalRepresentativeAssociationPk associationPk = mock(PatientLegalRepresentativeAssociationPk.class);
        when(association.getPatientLegalRepresentativeAssociationPk())
                .thenReturn(associationPk);
        Patient patient2 = mock(Patient.class);
        when(patient2.getId()).thenReturn(patientTwoId);
        when(associationPk.getPatient()).thenReturn(patient2);

        // Act
        boolean result = sut.isLegalRepForCurrentUser(username, legalRepIdInput);

        // Assert
        assertTrue(result);
    }

    @Test
    public void testFindAllPatientByFirstNameAndLastName() {
        List<Patient> patients = new ArrayList<Patient>();
        Patient patient = mock(Patient.class);
        Patient patient2 = mock(Patient.class);
        patients.add(patient);
        patients.add(patient2);
        when(
                patientRepository.findAllByFirstNameLikesAndLastNameLikes(
                        "%john%", "%smith%")).thenReturn(patients);
        PatientAdminDto patientAdminDto = mock(PatientAdminDto.class);
        PatientAdminDto patientAdminDto2 = mock(PatientAdminDto.class);
        when(modelMapper.map(patient, PatientAdminDto.class)).thenReturn(
                patientAdminDto);
        when(modelMapper.map(patient2, PatientAdminDto.class)).thenReturn(
                patientAdminDto2);
        assertEquals(
                Arrays.asList(patientAdminDto, patientAdminDto2),
                sut.findAllPatientByFirstNameAndLastName(new String[]{"john",
                        "smith"}));
    }

    @Test
    public void testFindPatientConnectionByUsername() {
        Patient patient = mock(Patient.class);
        when(patientRepository.findByUsername(anyString())).thenReturn(patient);
    }

    @Test
    public void testFindPatientConnectionById() {
        Patient patient = mock(Patient.class);
        when(patientRepository.findOne(anyLong())).thenReturn(patient);
    }
}
