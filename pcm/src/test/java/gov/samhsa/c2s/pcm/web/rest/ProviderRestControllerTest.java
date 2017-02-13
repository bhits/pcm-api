package gov.samhsa.c2s.pcm.web.rest;

import gov.samhsa.c2s.pcm.service.dto.MultiProviderRequestDto;
import gov.samhsa.c2s.pcm.service.dto.ProviderDto;
import gov.samhsa.c2s.pcm.service.exception.ProviderAlreadyInUseException;
import gov.samhsa.c2s.pcm.service.patient.PatientService;
import gov.samhsa.c2s.pcm.service.provider.IndividualProviderService;
import gov.samhsa.c2s.pcm.service.provider.OrganizationalProviderService;
import gov.samhsa.c2s.pcm.service.provider.ProviderSearchLookupService;
import gov.samhsa.c2s.pcm.web.ProviderRestController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.security.AccessControlException;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class ProviderRestControllerTest {

    @Mock
    PatientService patientService;

    @Mock
    IndividualProviderService individualProviderService;

    @Mock
    OrganizationalProviderService organizationalProviderService;

    @Mock
    ProviderSearchLookupService providerSearchLookupService;

    @Mock
    private OAuth2Authentication principal;

    @InjectMocks
    ProviderRestController providerRestController;

    MockMvc mockMvc;

    @Before
    public void before() throws AccessControlException {
        mockMvc = MockMvcBuilders.standaloneSetup(this.providerRestController).build();
    }

    @Test
    public void testListProviders() throws Exception {
        Set<ProviderDto> providerDtos = new HashSet<ProviderDto>();
        ProviderDto providerDto = new ProviderDto();
        providerDto.setNpi("1234567890");
        providerDtos.add(providerDto);
        when(patientService.findProvidersByUsername(anyString())).thenReturn(providerDtos);
        mockMvc.perform(get("/patients/providers").principal(principal)).andExpect(status().isOk());
    }

    @Test
    public void testDeleteIndProvider() throws Exception {
        Set<ProviderDto> providerDtos = new HashSet<ProviderDto>();
        ProviderDto providerDto = new ProviderDto();
        providerDto.setNpi("1234567890");
        providerDto.setDeletable(true);
        providerDto.setEntityType("Individual");
        providerDtos.add(providerDto);

        when(patientService.findProvidersByUsername(anyString())).thenReturn(providerDtos);

        mockMvc.perform(delete("/patients/providers/1234567890").principal(principal)).andExpect(status().isOk());
    }

    @Test
    public void testDeleteIndProvider_nonDeletable() throws Exception {
        Set<ProviderDto> providerDtos = new HashSet<ProviderDto>();
        ProviderDto providerDto = new ProviderDto();
        providerDto.setNpi("1234567890");
        providerDto.setDeletable(false);
        providerDto.setEntityType("Individual");
        providerDtos.add(providerDto);

        when(patientService.findProvidersByUsername(anyString())).thenReturn(providerDtos);

        mockMvc.perform(delete("/patients/providers/1234567890").principal(principal)).andExpect(status().isConflict());
    }

    @Test
    public void testDeleteOrgProvider() throws Exception {
        Set<ProviderDto> providerDtos = new HashSet<ProviderDto>();
        ProviderDto providerDto = new ProviderDto();
        providerDto.setNpi("1234567890");
        providerDto.setDeletable(true);
        providerDto.setEntityType("Organization");
        providerDtos.add(providerDto);

        when(patientService.findProvidersByUsername(anyString())).thenReturn(providerDtos);

        mockMvc.perform(delete("/patients/providers/1234567890").principal(principal)).andExpect(status().isOk());
    }

    @Test
    public void testDeleteOrgProvider_nonDeletable() throws Exception {
        Set<ProviderDto> providerDtos = new HashSet<ProviderDto>();
        ProviderDto providerDto = new ProviderDto();
        providerDto.setNpi("1234567890");
        providerDto.setDeletable(false);
        providerDto.setEntityType("Organization");
        providerDtos.add(providerDto);

        when(patientService.findProvidersByUsername(anyString())).thenReturn(providerDtos);

        mockMvc.perform(delete("/patients/providers/1234567890").principal(principal)).andExpect(status().isConflict());
    }

    @Test
    public void testAddProvider() throws Exception {
        String npi = "1234567890";
        String url = "/patients/providers/" + npi;
        String principalName = "test";
        when(principal.getName()).thenReturn(principalName);
        Mockito.doThrow(ProviderAlreadyInUseException.class).when(providerSearchLookupService).addProvider(principalName,npi);
        mockMvc.perform(post(url).principal(principal)).andExpect(status().is4xxClientError());
    }

    @Test
    public void testAddMultipleProviders() throws Exception {
        Set<String> npiList = new HashSet<String>();
        npiList.add("1234567890");
        npiList.add("1234567891");
        MultiProviderRequestDto multiProviderRequestDto  = new MultiProviderRequestDto();
        multiProviderRequestDto.setNpiList(npiList);

        String url = "/patients/providers/";
        String principalName = "test";

        when(principal.getName()).thenReturn(principalName);
        Mockito.doThrow(ProviderAlreadyInUseException.class).when(providerSearchLookupService).addMultipleProviders(principal,multiProviderRequestDto);
        mockMvc.perform(post(url).principal(principal)).andExpect(status().is4xxClientError());
    }
}