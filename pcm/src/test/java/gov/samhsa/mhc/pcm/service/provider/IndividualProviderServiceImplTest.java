package gov.samhsa.mhc.pcm.service.provider;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import gov.samhsa.mhc.pcm.domain.patient.Patient;
import gov.samhsa.mhc.pcm.domain.patient.PatientRepository;
import gov.samhsa.mhc.pcm.domain.provider.IndividualProvider;
import gov.samhsa.mhc.pcm.domain.provider.IndividualProviderRepository;
import gov.samhsa.mhc.pcm.service.dto.IndividualProviderDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

@RunWith(MockitoJUnitRunner.class)
public class IndividualProviderServiceImplTest {

    @Mock
    IndividualProviderRepository individualProviderRepository;

    @Mock
    ModelMapper modelMapper;

    @Mock
    PatientRepository patientRepository;

    @InjectMocks
    IndividualProviderServiceImpl individualProviderService;

    @Before
    public void before() {
        IndividualProviderDto individualProviderDto = mock(IndividualProviderDto.class);
        Patient patient = mock(Patient.class);
        when(individualProviderDto.getUsername()).thenReturn("albert.smith");
        when(patientRepository.findByUsername(anyString())).thenReturn(patient);
    }

    @Test
    public void testCountAllIndividualProviders() {
        individualProviderService.countAllIndividualProviders();
        verify(individualProviderRepository).count();
    }

    @Test
    public void testUpdateIndividualProvider() {
        individualProviderService
                .updateIndividualProvider(mock(IndividualProvider.class));
        verify(individualProviderRepository)
                .save(any(IndividualProvider.class));
    }

    @Test
    public void testUpdateIndividualProviderDto() {
        IndividualProviderDto individualProviderDto = mock(IndividualProviderDto.class);
        individualProviderService
                .updateIndividualProvider(individualProviderDto);
        verify(patientRepository).save(any(Patient.class));
    }

    @Test
    public void testFindIndividualProviderByNpi() {
        individualProviderService.findIndividualProviderByNpi(anyString());
        verify(individualProviderRepository).findByNpi(anyString());
    }

    @Test
    public void testDeleteIndividualProvider() {
        individualProviderService
                .deleteIndividualProvider(mock(IndividualProvider.class));
        verify(individualProviderRepository).delete(
                any(IndividualProvider.class));
    }

    @Test
    public void testDeleteIndividualProviderDto() {
        IndividualProviderDto individualProviderDto = mock(IndividualProviderDto.class);
        individualProviderService
                .deleteIndividualProviderDto(mock(IndividualProviderDto.class));
        verify(patientRepository).save(any(Patient.class));
    }

    @Test
    public void testDeleteIndividualProviderByPatientId() {
        IndividualProviderDto individualProviderDto = mock(IndividualProviderDto.class);
        when(individualProviderDto.getPatientId()).thenReturn("11");
        Patient patient = mock(Patient.class);
        when(patientRepository.findOne(anyLong())).thenReturn(patient);
        individualProviderService
                .deleteIndividualProviderDto(individualProviderDto);
        verify(patientRepository).save(any(Patient.class));
    }

    @Test
    public void testFindIndividualProvider() {
        individualProviderService.findIndividualProvider(anyLong());
        verify(individualProviderRepository).findOne(anyLong());
    }

    @Test
    public void testFindAllIndividualProviders() {
        individualProviderService.findAllIndividualProviders();
        verify(individualProviderRepository).findAll();
    }

    @Test
    public void testSaveIndividualProvider() {
        individualProviderService
                .saveIndividualProvider(mock(IndividualProvider.class));
        verify(individualProviderRepository)
                .save(any(IndividualProvider.class));
    }

    @Test
    public void testFindAllIndividualProvidersDto() {
        individualProviderService.findAllIndividualProvidersDto();
        verify(individualProviderRepository).findAll();
    }

    @Test
    public void testFindIndividualProviderDto() {
        individualProviderService.findIndividualProviderDto(anyLong());
        verify(individualProviderRepository).findOne(anyLong());
        verify(modelMapper).map(any(IndividualProvider.class),
                eq(IndividualProviderDto.class));
    }

}
