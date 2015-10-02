package gov.samhsa.consent2share.service.audit;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyByte;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import gov.samhsa.consent2share.domain.audit.ModifiedEntityTypeEntity;
import gov.samhsa.consent2share.domain.audit.ModifiedEntityTypeEntityRepository;
import gov.samhsa.consent2share.domain.audit.RevisionInfoEntity;
import gov.samhsa.consent2share.domain.audit.RevisionInfoEntityRepository;
import gov.samhsa.consent2share.domain.patient.Patient;
import gov.samhsa.consent2share.domain.patient.PatientRepository;
import gov.samhsa.consent2share.domain.staff.StaffRepository;
import gov.samhsa.consent2share.service.dto.HistoryDto;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManagerFactory;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AuditServiceImplTest {

	@Mock
	EntityManagerFactory entityManagerFactory;

	@Mock
	PatientRepository patientRepository;

	@Mock
	RevisionInfoEntityRepository patientRevisionEntityRepository;

	@Mock
	ModifiedEntityTypeEntityRepository modifiedEntityTypeEntityRepository;

	@Mock
	StaffRepository staffRepository;

	@InjectMocks
	AuditServiceImpl pasut;

	@Before
	public void setUp() {
		RevisionInfoEntity patientRevisionEntity = mock(RevisionInfoEntity.class);
		when(patientRevisionEntityRepository.findOneById(anyLong()))
				.thenReturn(patientRevisionEntity);
		Patient patient = mock(Patient.class);
		when(patientRepository.findByUsername(anyString())).thenReturn(patient);
		when(patientRepository.findOne(anyLong())).thenReturn(patient);

	}

	@Test
	public void testGetReversed() {
		List<HistoryDto> historyList = new ArrayList<HistoryDto>();
		List<HistoryDto> historyReversedExpected = new ArrayList<HistoryDto>();
		HistoryDto hd1 = mock(HistoryDto.class);
		historyList.add(hd1);
		HistoryDto hd2 = mock(HistoryDto.class);
		historyList.add(hd2);
		HistoryDto hd3 = mock(HistoryDto.class);
		historyList.add(hd3);
		historyReversedExpected.add(hd3);
		historyReversedExpected.add(hd2);
		historyReversedExpected.add(hd1);
		List<HistoryDto> historyReversed = pasut.getReversed(historyList);
		Assert.assertEquals(historyReversedExpected, historyReversed);
	}

	@Test
	public void testFindHistoryDetail() {
		AuditService pasutSpy = spy(pasut);
		Number n = mock(Number.class);
		HistoryDto hd = mock(HistoryDto.class);
		RevisionInfoEntity patientRevisionEntity = mock(RevisionInfoEntity.class);
		@SuppressWarnings("unchecked")
		List<ModifiedEntityTypeEntity> modifiedEntityTypeEntitys = mock(List.class);
		Patient patient = mock(Patient.class);

		when(pasutSpy.makeHistoryDto()).thenReturn(hd);
		when(patientRevisionEntityRepository.findOneById(n)).thenReturn(
				patientRevisionEntity);
		when(
				modifiedEntityTypeEntityRepository
						.findAllByRevision(any(RevisionInfoEntity.class)))
				.thenReturn(modifiedEntityTypeEntitys);
		when(patientRevisionEntity.getUsername()).thenReturn("username");
		when(patientRepository.findByUsername(anyString())).thenReturn(patient);
		when(patient.getLastName()).thenReturn("LastName");
		when(patient.getFirstName()).thenReturn("FirstName");
		when(patientRevisionEntity.getTimestamp()).thenReturn((long) 1);

		pasutSpy.findHistoryDetail(n);

		verify(pasutSpy, times(1)).findRevType(modifiedEntityTypeEntitys);
		verify(pasutSpy, times(1)).findRevClassName(modifiedEntityTypeEntitys);
		verify(hd, times(1)).setRevisionid(anyLong());
		verify(hd, times(1)).setChangedBy(anyString());
		verify(hd, times(1)).setTimestamp(anyString());
		verify(hd, times(1)).setRecType(anyString());
		verify(hd, times(1)).setType(anyString());

	}

	@Test
	public void testFindHistoryDetails() {
		List<Number> revisions = new ArrayList<Number>();
		for (int i = 0; i < 3; i++) {
			revisions.add(i);
		}
		AuditService pasutSpy = spy(pasut);

		List<HistoryDto> historyDtosList = new ArrayList<HistoryDto>();
		List<HistoryDto> historyDtosListSpy = spy(historyDtosList);
		when(pasutSpy.makeHistoryDtos()).thenReturn(historyDtosListSpy);
		pasutSpy.findHistoryDetails(revisions);
		verify(historyDtosListSpy, times(3)).add(any(HistoryDto.class));

	}

	@Test
	public void testmakeHistoryDtos_return_correct_class() {
		Object object = pasut.makeHistoryDtos();
		String className = object.getClass().getName();
		assertEquals("java.util.ArrayList", className);
	}

	@Test
	public void testmakeHistoryDto_return_correct_class() {
		Object object = pasut.makeHistoryDto();
		String className = object.getClass().getName();
		assertEquals("gov.samhsa.consent2share.service.dto.HistoryDto",
				className);
	}

	@Test
	public void testFindRevType_when_type_is_create() {
		Byte btype = 0;
		String revType = pasut.findRevType(btype);
		assertEquals("Create new entry", revType);
	}

	@Test
	public void testFindRevType_when_type_is_modified() {
		Byte btype = 1;
		String revType = pasut.findRevType(btype);
		assertEquals("Changed entry", revType);
	}

	@Test
	public void testFindRevType_when_type_is_deleted() {
		Byte btype = 2;
		String revType = pasut.findRevType(btype);
		assertEquals("Delete entry", revType);
	}

	@Test
	public void testFindRevType_when_type_is_notVaild() {
		Byte btype = 3;
		String revType = pasut.findRevType(btype);
		assertEquals(null, revType);
	}

	@Test
	public void testFindRevClassName_when_modifiedEntity_size_is_one() {

		List<ModifiedEntityTypeEntity> modifiedEntityTypeEntitys = new ArrayList<ModifiedEntityTypeEntity>();
		modifiedEntityTypeEntitys.add(mock(ModifiedEntityTypeEntity.class));
		when(modifiedEntityTypeEntitys.get(0).getEntityClassName()).thenReturn(
				"gov.samhsa.consent2share.domain.patient.Patient");

		String revClassName = pasut.findRevClassName(modifiedEntityTypeEntitys);
		assertEquals("Patient", revClassName);
	}

	@Test
	public void testFindRevClassName_when_modifiedEntity_size_is_two() {

		List<ModifiedEntityTypeEntity> modifiedEntityTypeEntitys = new ArrayList<ModifiedEntityTypeEntity>();
		modifiedEntityTypeEntitys.add(mock(ModifiedEntityTypeEntity.class));
		modifiedEntityTypeEntitys.add(mock(ModifiedEntityTypeEntity.class));
		when(modifiedEntityTypeEntitys.get(0).getRevisionType()).thenReturn(
				(byte) 1);
		when(modifiedEntityTypeEntitys.get(1).getEntityClassName()).thenReturn(
				"gov.samhsa.consent2share.domain.patient.Patient");

		String revClassName = pasut.findRevClassName(modifiedEntityTypeEntitys);
		assertEquals("Patient", revClassName);
	}

	@Test
	public void testFindRevClassName_when_modifiedEntity_size_is_two_Second() {

		List<ModifiedEntityTypeEntity> modifiedEntityTypeEntitys = new ArrayList<ModifiedEntityTypeEntity>();
		modifiedEntityTypeEntitys.add(mock(ModifiedEntityTypeEntity.class));
		modifiedEntityTypeEntitys.add(mock(ModifiedEntityTypeEntity.class));
		when(modifiedEntityTypeEntitys.get(0).getRevisionType()).thenReturn(
				(byte) 0);
		when(modifiedEntityTypeEntitys.get(0).getEntityClassName()).thenReturn(
				"gov.samhsa.consent2share.domain.provider.IndividualProvider");

		String revClassName = pasut.findRevClassName(modifiedEntityTypeEntitys);
		assertEquals("Individual Provider", revClassName);
	}

	@Test
	public void testFindRevClassName_when_modifiedEntity_size_is_three() {

		List<ModifiedEntityTypeEntity> modifiedEntityTypeEntitys = new ArrayList<ModifiedEntityTypeEntity>();
		modifiedEntityTypeEntitys.add(mock(ModifiedEntityTypeEntity.class));
		modifiedEntityTypeEntitys.add(mock(ModifiedEntityTypeEntity.class));
		modifiedEntityTypeEntitys.add(mock(ModifiedEntityTypeEntity.class));
		String revClassName = pasut.findRevClassName(modifiedEntityTypeEntitys);
		assertEquals("Add provider", revClassName);
	}

	@Test
	public void testFindRevType_when_modifiedEntity_size_is_one() {

		List<ModifiedEntityTypeEntity> modifiedEntityTypeEntitys = new ArrayList<ModifiedEntityTypeEntity>();
		modifiedEntityTypeEntitys.add(mock(ModifiedEntityTypeEntity.class));
		when(modifiedEntityTypeEntitys.get(0).getRevisionType()).thenReturn(
				(byte) 2);
		AuditService pasutSpy = spy(pasut);
		pasutSpy.findRevType(modifiedEntityTypeEntitys);
		verify(pasutSpy, times(1)).findRevType((byte) 2);

	}

	@Test
	public void testFindRevType_when_modifiedEntity_size_is_two() {

		List<ModifiedEntityTypeEntity> modifiedEntityTypeEntitys = new ArrayList<ModifiedEntityTypeEntity>();
		modifiedEntityTypeEntitys.add(mock(ModifiedEntityTypeEntity.class));
		modifiedEntityTypeEntitys.add(mock(ModifiedEntityTypeEntity.class));
		when(modifiedEntityTypeEntitys.get(0).getRevisionType()).thenReturn(
				(byte) 1);
		when(modifiedEntityTypeEntitys.get(1).getRevisionType()).thenReturn(
				(byte) 2);
		AuditService pasutSpy = spy(pasut);
		pasutSpy.findRevType(modifiedEntityTypeEntitys);
		verify(pasutSpy, times(1)).findRevType((byte) 2);

	}

	@Test
	public void testFindRevType_when_modifiedEntity_size_is_two_Second() {

		List<ModifiedEntityTypeEntity> modifiedEntityTypeEntitys = new ArrayList<ModifiedEntityTypeEntity>();
		modifiedEntityTypeEntitys.add(mock(ModifiedEntityTypeEntity.class));
		modifiedEntityTypeEntitys.add(mock(ModifiedEntityTypeEntity.class));
		when(modifiedEntityTypeEntitys.get(0).getRevisionType()).thenReturn(
				(byte) 2);
		AuditService pasutSpy = spy(pasut);
		pasutSpy.findRevType(modifiedEntityTypeEntitys);
		verify(pasutSpy, times(1)).findRevType((byte) 2);

	}

	@Test
	public void testFindRevType_when_modifiedEntity_size_is_three() {

		List<ModifiedEntityTypeEntity> modifiedEntityTypeEntitys = new ArrayList<ModifiedEntityTypeEntity>();
		modifiedEntityTypeEntitys.add(mock(ModifiedEntityTypeEntity.class));
		modifiedEntityTypeEntitys.add(mock(ModifiedEntityTypeEntity.class));
		modifiedEntityTypeEntitys.add(mock(ModifiedEntityTypeEntity.class));
		when(modifiedEntityTypeEntitys.get(0).getRevisionType()).thenReturn(
				(byte) 0);
		AuditService pasutSpy = spy(pasut);
		pasutSpy.findRevType(modifiedEntityTypeEntitys);
		verify(pasutSpy, times(1)).findRevType((byte) 0);

	}

	@Test
	public void testFindLegalHistoryDetail() {
		AuditService pasutSpy = spy(pasut);
		Number n = mock(Number.class);
		HistoryDto hd = mock(HistoryDto.class);
		RevisionInfoEntity patientRevisionEntity = mock(RevisionInfoEntity.class);
		List<ModifiedEntityTypeEntity> modifiedEntityTypeEntitys = new ArrayList<ModifiedEntityTypeEntity>();
		ModifiedEntityTypeEntity mete = mock(ModifiedEntityTypeEntity.class);
		modifiedEntityTypeEntitys.add(mete);
		Patient patient = mock(Patient.class);

		when(pasutSpy.makeHistoryDto()).thenReturn(hd);
		when(patientRevisionEntityRepository.findOneById(n)).thenReturn(
				patientRevisionEntity);
		when(
				modifiedEntityTypeEntityRepository
						.findAllByRevision(any(RevisionInfoEntity.class)))
				.thenReturn(modifiedEntityTypeEntitys);
		when(patientRevisionEntity.getUsername()).thenReturn("username");
		when(patientRepository.findByUsername(anyString())).thenReturn(patient);
		when(patient.getLastName()).thenReturn("LastName");
		when(patient.getFirstName()).thenReturn("FirstName");
		when(patientRevisionEntity.getTimestamp()).thenReturn((long) 1);
		when(modifiedEntityTypeEntitys.get(0).getRevisionType()).thenReturn(
				(byte) 1);

		pasutSpy.findLegalHistoryDetail(n);

		verify(pasutSpy, times(1)).findRevType(anyByte());
		verify(hd, times(1)).setRevisionid(anyLong());
		verify(hd, times(1)).setChangedBy(anyString());
		verify(hd, times(1)).setTimestamp(anyString());
		verify(hd, times(1)).setRecType(anyString());
		verify(hd, times(1)).setType(anyString());

	}

	@Test
	public void testFindLegalHistoryDetails() {
		List<Number> revisions = new ArrayList<Number>();
		for (int i = 0; i < 3; i++) {
			revisions.add(i);
		}
		AuditService pasutSpy = spy(pasut);

		List<HistoryDto> historyDtosList = new ArrayList<HistoryDto>();
		List<HistoryDto> historyDtosListSpy = spy(historyDtosList);
		when(pasutSpy.makeHistoryDtos()).thenReturn(historyDtosListSpy);
		pasutSpy.findHistoryDetails(revisions);
		verify(historyDtosListSpy, times(3)).add(any(HistoryDto.class));

	}

}
