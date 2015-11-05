package gov.samhsa.pcm.web.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import gov.samhsa.pcm.domain.provider.IndividualProvider;
import gov.samhsa.pcm.domain.provider.OrganizationalProvider;
import gov.samhsa.pcm.service.dto.IndividualProviderDto;
import gov.samhsa.pcm.service.dto.OrganizationalProviderDto;
import gov.samhsa.pcm.service.dto.ProviderDto;
import gov.samhsa.pcm.service.patient.PatientService;
import gov.samhsa.pcm.service.provider.HashMapResultToProviderDtoConverter;
import gov.samhsa.pcm.service.provider.IndividualProviderService;
import gov.samhsa.pcm.service.provider.OrganizationalProviderService;
import gov.samhsa.pcm.service.provider.ProviderSearchLookupService;
import gov.samhsa.pcm.service.validator.pg.FieldValidator;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.owasp.esapi.errors.AccessControlException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(MockitoJUnitRunner.class)
public class ProviderRestControllerTest {

	@InjectMocks
	ProviderRestController providerRestController = new ProviderRestController();

	@Mock
	PatientService patientService;

	@Mock
	IndividualProviderService individualProviderService;

	@Mock
	OrganizationalProviderService organizationalProviderService;

	@Mock
	ProviderSearchLookupService providerSearchLookupService;

	@Mock
	FieldValidator fieldValidator;

	@Mock
	HashMap<String, String> Result;

	@Mock
	HashMapResultToProviderDtoConverter hashMapResultToProviderDtoConverter;

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
		mockMvc.perform(get("/patients/providers")).andExpect(status().isOk());
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

		mockMvc.perform(delete("/patients/providers/1234567890")).andExpect(status().isOk());
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

		mockMvc.perform(delete("/patients/providers/1234567890")).andExpect(status().is5xxServerError());
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

		mockMvc.perform(delete("/patients/providers/1234567890")).andExpect(status().isOk());
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

		mockMvc.perform(delete("/patients/providers/1234567890")).andExpect(status().is5xxServerError());
	}

	@Test
	public void testAddProvider_individual() throws Exception {

		when(providerSearchLookupService.providerSearchByNpi(anyString())).thenReturn(
				"{'npi':'1234567890','entityType':'Individual','providerFirstName':'albert','providerMiddleName':'A','providerLastName':'smith'}");

		IndividualProvider individualProvider = mock(IndividualProvider.class);
		when(individualProviderService.addNewIndividualProvider(any(IndividualProviderDto.class)))
				.thenReturn(individualProvider);

		mockMvc.perform(post("/patients/providers/1234567890")).andExpect(status().isOk());

	}

	@Test
	public void testAddProvider_organization() throws Exception {

		when(providerSearchLookupService.providerSearchByNpi(anyString())).thenReturn(
				"{'npi':'1234567890','entityType':'Organization','providerOrganizationName':'albert','authorizedOfficialLastName':'A','authorizedOfficialFirstName':'smith'}");

		OrganizationalProvider organizationalProvider = mock(OrganizationalProvider.class);
		when(organizationalProviderService.addNewOrganizationalProvider(any(OrganizationalProviderDto.class)))
				.thenReturn(organizationalProvider);

		mockMvc.perform(post("/patients/providers/1234567890")).andExpect(status().isOk());

	}

	@Test
	public void testAddProvider_individual_existing() throws Exception {

		when(providerSearchLookupService.providerSearchByNpi(anyString())).thenReturn(
				"{'npi':'1234567890','entityType':'Individual','providerFirstName':'albert','providerMiddleName':'A','providerLastName':'smith'}");

		when(individualProviderService.addNewIndividualProvider(any(IndividualProviderDto.class))).thenReturn(null);

		mockMvc.perform(post("/patients/providers/1234567890")).andExpect(status().is4xxClientError());

	}

	@Test
	public void testAddProvider_organization_existing() throws Exception {

		when(providerSearchLookupService.providerSearchByNpi(anyString())).thenReturn(
				"{'npi':'1234567890','entityType':'Organization','providerOrganizationName':'albert','authorizedOfficialLastName':'A','authorizedOfficialFirstName':'smith'}");

		when(organizationalProviderService.addNewOrganizationalProvider(any(OrganizationalProviderDto.class)))
				.thenReturn(null);

		mockMvc.perform(post("/patients/providers/1234567890")).andExpect(status().is4xxClientError());

	}

}
