package gov.samhsa.consent2share.service.patient;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import gov.samhsa.consent2share.common.AuthenticatedUser;
import gov.samhsa.consent2share.common.UserContext;
import gov.samhsa.consent2share.domain.patient.Patient;
import gov.samhsa.consent2share.domain.patient.PatientLegalRepresentativeAssociation;
import gov.samhsa.consent2share.domain.patient.PatientLegalRepresentativeAssociationPk;
import gov.samhsa.consent2share.domain.patient.PatientLegalRepresentativeAssociationRepository;
import gov.samhsa.consent2share.domain.patient.PatientRepository;
import gov.samhsa.consent2share.service.dto.LegalRepresentativeDto;
import gov.samhsa.consent2share.service.dto.PatientLegalRepresentativeAssociationDto;
import gov.samhsa.consent2share.service.dto.PatientProfileDto;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

@RunWith(MockitoJUnitRunner.class)
public class PatientLegalRepresentativeAssociationServiceImplTest {

	@Mock
	PatientRepository patientRepository;

	@Mock
	PatientLegalRepresentativeAssociationRepository patientLegalRepresentativeAssociationRepository;

	@Mock
	UserContext userContext;

	@Mock
	AuthenticatedUser authenticatedUser;

	@Mock
	ModelMapper modelMapper;

	@InjectMocks
	PatientLegalRepresentativeAssociationServiceImpl patientLegalRepresentativeAssociationService;

	@Before
	public void before() {
		Patient patient = mock(Patient.class);
		when(patientRepository.findByUsername(anyString())).thenReturn(patient);
		when(userContext.getCurrentUser()).thenReturn(authenticatedUser);
		when(authenticatedUser.getUsername()).thenReturn("albert.smith");

	}

	@Test
	public void testCountAllPatientLegalRepresentativeAssociations() {
		patientLegalRepresentativeAssociationService
				.countAllPatientLegalRepresentativeAssociations();
		verify(patientLegalRepresentativeAssociationRepository).count();
	}

	@Test
	public void testDeletePatientLegalRepresentativeAssociation() {
		patientLegalRepresentativeAssociationService
				.deletePatientLegalRepresentativeAssociation(mock(PatientLegalRepresentativeAssociation.class));
		verify(patientLegalRepresentativeAssociationRepository).delete(
				any(PatientLegalRepresentativeAssociation.class));
	}

	@Test
	public void testDeletePatientLegalRepresentativeById() {
		patientLegalRepresentativeAssociationService
				.deletePatientLegalRepresentativeById(anyLong());
		verify(patientRepository).findOne(anyLong());
		verify(patientLegalRepresentativeAssociationRepository)
				.findByPatientLegalRepresentativeAssociationPkLegalRepresentativeId(
						anyLong());
		verify(patientRepository).delete(any(Patient.class));
	}

	@Test
	public void testDeletePatientLegalRepresentativeAssociationById() {
		List<PatientLegalRepresentativeAssociation> associations = new ArrayList<PatientLegalRepresentativeAssociation>();
		PatientLegalRepresentativeAssociation association = mock(PatientLegalRepresentativeAssociation.class);
		PatientLegalRepresentativeAssociationPk associationPk = mock(PatientLegalRepresentativeAssociationPk.class);
		when(association.getPatientLegalRepresentativeAssociationPk())
				.thenReturn(associationPk);
		associations.add(association);

		Patient patient = mock(Patient.class);
		when(associationPk.getPatient()).thenReturn(patient);
		when(patient.getId()).thenReturn((long) 1);

		Patient patient2 = mock(Patient.class);

		when(
				patientLegalRepresentativeAssociationRepository
						.findByPatientLegalRepresentativeAssociationPkLegalRepresentativeId(anyLong()))
				.thenReturn(associations);
		when(patientRepository.findByUsername(anyString()))
				.thenReturn(patient2);
		when(patient2.getId()).thenReturn((long) 1);

		patientLegalRepresentativeAssociationService
				.deletePatientLegalRepresentativeById(anyLong());

		verify(patientLegalRepresentativeAssociationRepository).delete(
				association);
	}

	@Test
	public void testFindPatientLegalRepresentativeAssociation() {
		patientLegalRepresentativeAssociationService
				.findPatientLegalRepresentativeAssociation(anyLong());
		verify(patientLegalRepresentativeAssociationRepository).findOne(
				anyLong());
	}

	@Test
	public void testFindAllPatientLegalRepresentativeAssociations() {
		patientLegalRepresentativeAssociationService
				.findAllPatientLegalRepresentativeAssociations();
		verify(patientLegalRepresentativeAssociationRepository).findAll();
	}

	@Test
	public void testFindAllPatientLegalRepresentativeAssociationDtos() {
		Patient patient = mock(Patient.class);
		when(patientRepository.findByUsername(anyString())).thenReturn(patient);
		when(patient.getId()).thenReturn((long) 1);

		List<PatientLegalRepresentativeAssociation> allAssociations = new ArrayList<PatientLegalRepresentativeAssociation>();
		List<PatientLegalRepresentativeAssociationDto> associationDtos = new ArrayList<PatientLegalRepresentativeAssociationDto>();
		List<PatientLegalRepresentativeAssociationDto> associationDtosSpy = spy(associationDtos);

		PatientLegalRepresentativeAssociation association = mock(PatientLegalRepresentativeAssociation.class);
		allAssociations.add(association);
		PatientLegalRepresentativeAssociationPk associationPk = mock(PatientLegalRepresentativeAssociationPk.class);
		Patient patient2 = mock(Patient.class);
		PatientLegalRepresentativeAssociationDto associationDto = mock(PatientLegalRepresentativeAssociationDto.class);
		Patient legalRep = mock(Patient.class);

		when(patientLegalRepresentativeAssociationRepository.findAll())
				.thenReturn(allAssociations);
		when(association.getPatientLegalRepresentativeAssociationPk())
				.thenReturn(associationPk);
		when(associationPk.getPatient()).thenReturn(patient2);
		when(associationPk.getLegalRepresentative()).thenReturn(legalRep);
		when(patient2.getId()).thenReturn((long) 1);
		when(
				modelMapper.map(association,
						PatientLegalRepresentativeAssociationDto.class))
				.thenReturn(associationDto);

		associationDtos = patientLegalRepresentativeAssociationService
				.findAllPatientLegalRepresentativeAssociationDtos();

		assertEquals(associationDto, associationDtos.get(0));
	}

	@Test
	public void testFindAllPatientLegalRepresentativeDto() {
		patientLegalRepresentativeAssociationService
				.findAllPatientLegalRepresentativeDto();
		verify(patientRepository).findByUsername(anyString());
		verify(patientLegalRepresentativeAssociationRepository).findAll();
	}

	@Test
	public void testSavePatientLegalRepresentativeAssociation() {
		patientLegalRepresentativeAssociationService
				.savePatientLegalRepresentativeAssociation(mock(PatientLegalRepresentativeAssociation.class));
		verify(patientLegalRepresentativeAssociationRepository).save(
				any(PatientLegalRepresentativeAssociation.class));

	}

	@Test
	public void testSavePatientLegalRepresentativeAssociationDto() {
		patientLegalRepresentativeAssociationService
				.savePatientLegalRepresentativeAssociationDto(mock(PatientLegalRepresentativeAssociationDto.class));
		verify(patientLegalRepresentativeAssociationRepository).save(
				any(PatientLegalRepresentativeAssociation.class));
	}

	@Test
	public void testUpdatePatientLegalRepresentativeAssociation() {
		patientLegalRepresentativeAssociationService
				.updatePatientLegalRepresentativeAssociation(mock(PatientLegalRepresentativeAssociation.class));
		verify(patientLegalRepresentativeAssociationRepository).save(
				any(PatientLegalRepresentativeAssociation.class));
	}

	@Test
	public void testUpdatePatientLegalRepresentativeAssociationDto() {
		patientLegalRepresentativeAssociationService
				.updatePatientLegalRepresentativeAssociationDto(mock(PatientLegalRepresentativeAssociationDto.class));
		verify(patientLegalRepresentativeAssociationRepository)
				.findByPatientLegalRepresentativeAssociationPkLegalRepresentativeId(
						anyLong());
	}

	@Test
	public void testGetAllLegalRepresentativeDto() {
		patientLegalRepresentativeAssociationService
				.getAllLegalRepresentativeDto();
	}

	@Test
	public void testGetAssociationDtoFromLegalRepresentativeDto() {
		patientLegalRepresentativeAssociationService
				.getAssociationDtoFromLegalRepresentativeDto(mock(LegalRepresentativeDto.class));
	}

	@Test
	public void testGetPatientDtoFromLegalRepresentativeDto() {
		patientLegalRepresentativeAssociationService
				.getPatientDtoFromLegalRepresentativeDto(mock(LegalRepresentativeDto.class));
		verify(modelMapper).map(any(LegalRepresentativeDto.class),
				eq(PatientProfileDto.class));
	}

}
