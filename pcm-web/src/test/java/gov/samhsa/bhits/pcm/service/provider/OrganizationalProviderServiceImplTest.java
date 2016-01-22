package gov.samhsa.bhits.pcm.service.provider;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import gov.samhsa.bhits.pcm.domain.patient.Patient;
import gov.samhsa.bhits.pcm.domain.patient.PatientRepository;
import gov.samhsa.bhits.pcm.domain.provider.OrganizationalProvider;
import gov.samhsa.bhits.pcm.domain.provider.OrganizationalProviderRepository;
import gov.samhsa.bhits.pcm.service.dto.OrganizationalProviderDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

@RunWith(MockitoJUnitRunner.class)
public class OrganizationalProviderServiceImplTest {

    @Mock
    PatientRepository patientRepository;

    @Mock
    ModelMapper modelMapper;

    @Mock
    OrganizationalProviderRepository organizationalProviderRepository;

    @InjectMocks
    OrganizationalProviderServiceImpl organizationalProviderService;

    @Before
    public void before() {
        OrganizationalProviderDto organizationalProviderDto = mock(OrganizationalProviderDto.class);
        Patient patient = mock(Patient.class);
        when(organizationalProviderDto.getUsername())
                .thenReturn("albert.smith");
        when(patientRepository.findByUsername(anyString())).thenReturn(patient);
    }

    @Test
    public void testCountAllOrganizationalProviders() {
        organizationalProviderService.countAllOrganizationalProviders();
        verify(organizationalProviderRepository).count();
    }

    @Test
    public void testDeleteOrganizationalProvider() {
        organizationalProviderService
                .deleteOrganizationalProvider(mock(OrganizationalProvider.class));
        verify(organizationalProviderRepository).delete(
                any(OrganizationalProvider.class));
    }

    @Test
    public void testDeleteOrganizationalProviderDto() {
        OrganizationalProviderDto organizationalProviderDto = mock(OrganizationalProviderDto.class);
        organizationalProviderService
                .deleteOrganizationalProviderDto(mock(OrganizationalProviderDto.class));
        verify(patientRepository).save(any(Patient.class));
    }

    @Test
    public void testDeleteOrganizationalProviderByPatientId() {
        OrganizationalProviderDto organizationalProviderDto = mock(OrganizationalProviderDto.class);
        when(organizationalProviderDto.getPatientId()).thenReturn("11");
        Patient patient = mock(Patient.class);
        when(patientRepository.findOne(anyLong())).thenReturn(patient);
        organizationalProviderService
                .deleteOrganizationalProviderDto(mock(OrganizationalProviderDto.class));
        verify(patientRepository).save(any(Patient.class));
    }

    @Test
    public void testFindOrganizationalProvider() {
        organizationalProviderService.findOrganizationalProvider(anyLong());
        verify(organizationalProviderRepository).findOne(anyLong());
    }

    @Test
    public void testFindOrganizationalProviderByNpi() {
        organizationalProviderService
                .findOrganizationalProviderByNpi(anyString());
        verify(organizationalProviderRepository).findByNpi(anyString());
    }

    @Test
    public void testFindOrganizationalProviderDto() {
        organizationalProviderService.findOrganizationalProviderDto(anyLong());
        verify(organizationalProviderRepository).findOne(anyLong());
        verify(modelMapper).map(any(OrganizationalProvider.class),
                eq(OrganizationalProviderDto.class));
    }

    @Test
    public void testFindAllOrganizationalProviders() {
        organizationalProviderService.findAllOrganizationalProviders();
        verify(organizationalProviderRepository).findAll();
    }

    @Test
    public void testSaveOrganizationalProvider() {
        organizationalProviderService
                .saveOrganizationalProvider(mock(OrganizationalProvider.class));
        verify(organizationalProviderRepository).save(
                any(OrganizationalProvider.class));
    }

    @Test
    public void testUpdateOrganizationalProviderOrganizationalProvider() {
        organizationalProviderService
                .updateOrganizationalProvider(mock(OrganizationalProvider.class));
        verify(organizationalProviderRepository).save(
                any(OrganizationalProvider.class));
    }

    @Test
    public void testUpdateOrganizationalProviderOrganizationalProviderDto() {
        organizationalProviderService
                .updateOrganizationalProvider(mock(OrganizationalProviderDto.class));
        verify(patientRepository).save(any(Patient.class));
    }

}
