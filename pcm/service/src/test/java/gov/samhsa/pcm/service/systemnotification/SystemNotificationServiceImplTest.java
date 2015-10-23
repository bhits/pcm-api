package gov.samhsa.pcm.service.systemnotification;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import gov.samhsa.pcm.domain.patient.PatientRepository;
import gov.samhsa.pcm.domain.systemnotification.SystemNotification;
import gov.samhsa.pcm.domain.systemnotification.SystemNotificationRepository;
import gov.samhsa.pcm.service.dto.SystemNotificationDto;

import java.util.ArrayList;
import java.util.List;

import gov.samhsa.pcm.service.systemnotification.SystemNotificationService;
import gov.samhsa.pcm.service.systemnotification.SystemNotificationServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

/**
 * The Class ConsentServiceImplTest.
 */
@RunWith(MockitoJUnitRunner.class)
public class SystemNotificationServiceImplTest {

	/** The systemNotification Repository. */
	@Mock
	SystemNotificationRepository systemNotificationRepository;

	/** The patient repository. */
	@Mock
	PatientRepository patientRepository;

	@Mock
	ModelMapper modelMapper;

	/** The snst. */
	@InjectMocks
	SystemNotificationServiceImpl snst;

	@Test
	public void testFindAllSystemNotificationDtosByPatient() {
		SystemNotificationService spy = spy(snst);
		List<SystemNotification> systemNotifications = new ArrayList<SystemNotification>();
		SystemNotification systemNotification = new SystemNotification();
		systemNotifications.add(systemNotification);
		when(systemNotificationRepository.findAllByPatientId(anyLong()))
				.thenReturn(systemNotifications);
		ArrayList<SystemNotificationDto> systemNotificationDtos = new ArrayList<SystemNotificationDto>();
		when(spy.makeSystemNotificationDtos()).thenReturn(
				systemNotificationDtos);
		SystemNotificationDto systemNotificationDto = new SystemNotificationDto();
		when(modelMapper.map(systemNotification, SystemNotificationDto.class))
				.thenReturn(systemNotificationDto);
		systemNotificationDtos.add(systemNotificationDto);
		Assert.assertEquals(
				spy.findAllSystemNotificationDtosByPatient(anyLong()),
				systemNotificationDtos);

	}

	@Test
	public void testGetReversed() {
		List<SystemNotificationDto> systemNotificationDtos = new ArrayList<SystemNotificationDto>();
		List<SystemNotificationDto> systemNotificationDtosExpected = new ArrayList<SystemNotificationDto>();
		SystemNotificationDto systemNotificationDto1 = mock(SystemNotificationDto.class);
		systemNotificationDtos.add(systemNotificationDto1);
		SystemNotificationDto systemNotificationDto2 = mock(SystemNotificationDto.class);
		systemNotificationDtos.add(systemNotificationDto2);
		SystemNotificationDto systemNotificationDto3 = mock(SystemNotificationDto.class);
		systemNotificationDtos.add(systemNotificationDto3);
		systemNotificationDtosExpected.add(systemNotificationDto3);
		systemNotificationDtosExpected.add(systemNotificationDto2);
		systemNotificationDtosExpected.add(systemNotificationDto1);
		List<SystemNotificationDto> SystemNotificationDtoReversed = snst
				.getReversed(systemNotificationDtos);
		Assert.assertEquals(systemNotificationDtosExpected,
				SystemNotificationDtoReversed);
	}

	@Test
	public void testmakeSystemNotificationDtos() {
		Object object = snst.makeSystemNotificationDtos();
		String className = object.getClass().getName();
		assertEquals("java.util.ArrayList", className);
	}

}
