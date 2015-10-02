package gov.samhsa.consent2share.web.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import gov.samhsa.consent2share.common.AuthenticatedUser;
import gov.samhsa.consent2share.common.UserContext;
import gov.samhsa.consent2share.service.dto.CodeSystemDto;
import gov.samhsa.consent2share.service.dto.ConceptCodeDto;
import gov.samhsa.consent2share.service.dto.ConceptCodeVSCSDto;
import gov.samhsa.consent2share.service.dto.ValueSetDto;
import gov.samhsa.consent2share.service.valueset.CodeSystemNotFoundException;
import gov.samhsa.consent2share.service.valueset.CodeSystemService;
import gov.samhsa.consent2share.service.valueset.CodeSystemVersionNotFoundException;
import gov.samhsa.consent2share.service.valueset.ConceptCodeNotFoundException;
import gov.samhsa.consent2share.service.valueset.ConceptCodeService;
import gov.samhsa.consent2share.service.valueset.ValueSetNotFoundException;
import gov.samhsa.consent2share.service.valueset.ValueSetService;
import gov.samhsa.consent2share.web.controller.ConceptCodeController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(MockitoJUnitRunner.class)
public class ConceptCodeControllerTest {
	
	@InjectMocks
	ConceptCodeController conceptCodeController;
	
	@Mock
	ConceptCodeService conceptCodeService;
	
	@Mock
	CodeSystemService codeSystemService;
	
	@Mock
	ValueSetService valueSetService;
	
	@Mock
	UserContext userContext;
		
	MockMvc mockMvc;
	
	@Before
	public void setUp(){
		mockMvc = MockMvcBuilders.standaloneSetup(this.conceptCodeController).build();
	}
	
	@Test
	public void testGetConceptCodeList() throws Exception{
		ConceptCodeVSCSDto conceptCodeVSCSDto = mock(ConceptCodeVSCSDto.class);
		
		List<CodeSystemDto> codeSystems = (List<CodeSystemDto>) mock(List.class);
		List<ValueSetDto> valueSets = (List<ValueSetDto>) mock(List.class);
		
		when(conceptCodeService.create()).thenReturn(conceptCodeVSCSDto);
		when(codeSystemService.findAll()).thenReturn(codeSystems);
		when(valueSetService.findAllWithoutDeletable()).thenReturn(valueSets);
		
		mockMvc.perform(get("/sysadmin/conceptCodeList"))
			.andExpect(status().isOk())
			.andExpect(model().attribute("conceptCodeVSCSDto", conceptCodeVSCSDto))
			.andExpect(model().attribute("codeSystems", codeSystems))
			.andExpect(model().attribute("valueSets", valueSets))
			.andExpect(view().name("views/sysadmin/conceptCodeList"));
	}
	
	
	@Test
	public void testAjaxGetAllConceptCodes() throws Exception{
		List<ConceptCodeDto> conceptCodes = conceptCodeService.findAll();
		
		when(conceptCodeService.findAll()).thenReturn(conceptCodes);
		
		mockMvc.perform(get("/sysadmin/conceptCode/ajaxGetAllConceptCodes"))
		.andExpect(status().isOk())
		.andExpect(content().contentType("application/json;charset=UTF-8"));
	}
	
	
	
	@Test
	public void testAjaxSearchConceptCode_By_Name() throws Exception{
		List<ConceptCodeDto> conceptCodes= new ArrayList<ConceptCodeDto>();
		Map<String, Object> pagedconceptCodes = new HashMap<String, Object>();
		ConceptCodeDto ccdto=new ConceptCodeDto();
		ccdto.setName("disorder");		
		conceptCodes.add(ccdto);
		when(conceptCodeService.findAllByName(anyString(), anyString(), anyString(), anyString(), anyInt())).thenReturn(pagedconceptCodes);

		mockMvc.perform(get("/sysadmin/conceptCode/ajaxSearchConceptCode/pageNumber/0/searchCategory/name/searchTerm/D/codeSystem/LOINC/codeSystemVersion/2014/valueSetName/Alcohol-Ethanol-Toxicology-LOINC"))
		.andExpect(status().isOk())
		.andExpect(content().contentType("application/json;charset=UTF-8"));
	}
	
	@Test
	public void testAjaxSearchConceptCode_By_Code() throws Exception{
		ConceptCodeDto ccdto=new ConceptCodeDto();
		ccdto.setCode("disorder");	
		when(conceptCodeService.findAllByName(anyString(), anyString(), anyString(), anyString(), anyInt())).thenReturn(mock(Map.class));
		mockMvc.perform(get("/sysadmin/conceptCode/ajaxSearchConceptCode/pageNumber/0/searchCategory/code/searchTerm/3/codeSystem/LOINC/codeSystemVersion/2014/valueSetName/Alcohol-Ethanol-Toxicology-LOINC"))
		.andExpect(status().isOk())
		.andExpect(content().contentType("application/json;charset=UTF-8"));
	}
	
	@Test
	public void testGetConceptCodeAdd() throws Exception{
		
		ConceptCodeVSCSDto conceptCodeVSCDto = mock(ConceptCodeVSCSDto.class);
		when(conceptCodeService.create()).thenReturn(conceptCodeVSCDto);
		ConceptCodeDto conceptCodeDto = new ConceptCodeDto();
		conceptCodeDto.setCode("code");
		conceptCodeDto.setName("name");
				
		mockMvc.perform(get("/sysadmin/conceptCodeAdd.html")
				.sessionAttr("conceptCodeDto", conceptCodeDto))
			.andExpect(status().isOk())
			.andExpect(view().name("views/sysadmin/conceptCodeAdd"));
	}
	
	@Test
	public void testGetConceptCodeAdd_throwValueSetNotFoundException() throws Exception{
		
		when(conceptCodeService.create()).thenThrow(new ValueSetNotFoundException());
		ConceptCodeDto conceptCodeDto = new ConceptCodeDto();
		conceptCodeDto.setCode("code");
		conceptCodeDto.setName("name");
				
		mockMvc.perform(get("/sysadmin/conceptCodeAdd.html")
				.sessionAttr("conceptCodeDto", conceptCodeDto))
			.andExpect(status().isOk())
			.andExpect(view().name("views/sysadmin/conceptCodeList"));
	}
	
	@Test
	public void testGetConceptCodeAdd_throwCodeSystemNotFoundException() throws Exception{
		
		when(conceptCodeService.create()).thenThrow(new CodeSystemNotFoundException());
		ConceptCodeDto conceptCodeDto = new ConceptCodeDto();
		conceptCodeDto.setCode("code");
		conceptCodeDto.setName("name");
				
		mockMvc.perform(get("/sysadmin/conceptCodeAdd.html")
				.sessionAttr("conceptCodeDto", conceptCodeDto))
			.andExpect(status().isOk())
			.andExpect(view().name("views/sysadmin/conceptCodeList"));
	}
	
	@Test
	public void testGetConceptCodeAdd_throwException() throws Exception{
		
		when(conceptCodeService.create()).thenThrow(new CodeSystemVersionNotFoundException());
		ConceptCodeDto conceptCodeDto = new ConceptCodeDto();
		conceptCodeDto.setCode("code");
		conceptCodeDto.setName("name");
				
		mockMvc.perform(get("/sysadmin/conceptCodeAdd.html")
				.sessionAttr("conceptCodeDto", conceptCodeDto))
			.andExpect(status().isOk())
			.andExpect(view().name("views/sysadmin/conceptCodeList"));
	}
	
	@Test
	public void testCreatedConceptCodeForm() throws Exception{
		
		ConceptCodeDto conceptCodeDto=new ConceptCodeDto();
		
		AuthenticatedUser currentUser=mock(AuthenticatedUser.class);
		when(userContext.getCurrentUser()).thenReturn(currentUser);
		
		final String username="username";
		when(currentUser.getUsername()).thenReturn(username);
		
		when(conceptCodeService.create(any(ConceptCodeDto.class))).thenReturn(conceptCodeDto);
		
		mockMvc.perform(post("/sysadmin/conceptCode/create")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
						.param("name","name")
						.param("code","code"))
			.andExpect(status().isFound())
			.andExpect(view().name("redirect:../conceptCodeList?panelState=addnew"));
	}
	
	@Test
	public void testCreatedConceptCodeForm_ThrowCodeSystemNotFoundException() throws Exception{
		
		AuthenticatedUser currentUser=mock(AuthenticatedUser.class);
		when(userContext.getCurrentUser()).thenReturn(currentUser);
		
		final String username="username";
		when(currentUser.getUsername()).thenReturn(username);
		
		when(conceptCodeService.create(any(ConceptCodeDto.class))).thenThrow(new CodeSystemNotFoundException());
		
		mockMvc.perform(post("/sysadmin/conceptCode/create")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
						.param("name","ConceptCodeCatName")
						.param("code","ConceptCodeCatCode"))
			.andExpect(status().isFound())
			.andExpect(view().name("redirect:../conceptCodeAdd.html?panelState=addnew"));
	}
	
	@Test
	public void testCreatedConceptCodeForm_ThrowValueSetNotFoundException() throws Exception{
		
		AuthenticatedUser currentUser=mock(AuthenticatedUser.class);
		when(userContext.getCurrentUser()).thenReturn(currentUser);
		
		final String username="username";
		when(currentUser.getUsername()).thenReturn(username);
		
		when(conceptCodeService.create(any(ConceptCodeDto.class))).thenThrow(new ValueSetNotFoundException());
		
		mockMvc.perform(post("/sysadmin/conceptCode/create")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
						.param("name","ConceptCodeCatName")
						.param("code","ConceptCodeCatCode"))
			.andExpect(status().isFound())
			.andExpect(view().name("redirect:../conceptCodeAdd.html?panelState=addnew"));
	}
	
	@Test
	public void testCreatedConceptCodeForm_DataIntegrityViolationException() throws Exception{
		
		AuthenticatedUser currentUser=mock(AuthenticatedUser.class);
		when(userContext.getCurrentUser()).thenReturn(currentUser);
		
		final String username="username";
		when(currentUser.getUsername()).thenReturn(username);
		
		when(conceptCodeService.create(any(ConceptCodeDto.class))).thenThrow(new DataIntegrityViolationException("DataIntegrityViolationException"));
		
		mockMvc.perform(post("/sysadmin/conceptCode/create")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
						.param("name","ConceptCodeCatName")
						.param("code","ConceptCodeCatCode"))
			.andExpect(status().isFound())
			.andExpect(view().name("redirect:../conceptCodeAdd.html?panelState=addnew"));
		
	}
	
	@Test
	public void testDeleteConceptCode() throws Exception{
		
		ConceptCodeDto deleted=new ConceptCodeDto();
		deleted.setCode("TESTCODE");
		deleted.setName("Test Name");
		
		when(conceptCodeService.delete(anyLong())).thenReturn(deleted);
		
		mockMvc.perform(delete("/sysadmin/conceptCode/delete/1"))
			.andExpect(content().string("ConceptCode with Code: TESTCODE and Name: Test Name is deleted Successfully."));
	}
	
	@Test
	public void testDeleteConceptCode_throwException() throws Exception{
		
		when(conceptCodeService.delete(anyLong())).thenThrow(new ConceptCodeNotFoundException());
		
		mockMvc.perform(delete("/sysadmin/conceptCode/delete/1"))
			.andExpect(status().isInternalServerError())
			.andExpect(content().string("Deleted conceptCode was not found."));
	}
	
	@Test
	public void testDeleteConceptCode_withPost() throws Exception{
		
		ConceptCodeDto deleted=new ConceptCodeDto();
		
		when(conceptCodeService.delete(anyLong())).thenReturn(deleted);
		
		mockMvc.perform(post("/sysadmin/conceptCode/delete/1"))
			.andExpect(status().isMethodNotAllowed());
	}
	
	@Test
	public void testEditConceptCode() throws Exception{
		
		ConceptCodeDto editConceptCodeDto=new ConceptCodeDto();
		
		when(conceptCodeService.findById(anyLong())).thenReturn(editConceptCodeDto);
		
		mockMvc.perform(get("/sysadmin/conceptCode/edit/1"))
			.andExpect(status().isOk())
			.andExpect(model().attribute("conceptCodeDto", editConceptCodeDto))
			.andExpect(view().name("views/sysadmin/conceptCodeEdit"));
	}
	
	@Test
	public void testEditConceptCode2() throws Exception{
		
		ConceptCodeDto editConceptCodeDto=null;
		
		when(conceptCodeService.findById(anyLong())).thenReturn(editConceptCodeDto);
		
		mockMvc.perform(get("/sysadmin/conceptCode/edit/1"))
			.andExpect(model().attribute("conceptCodeDto", editConceptCodeDto))
			.andExpect(view().name("redirect:../../conceptCodeList"));
	}
	
	@Test
	public void testEditConceptCodeForm_Post() throws Exception{
		
		ConceptCodeDto editConceptCodeDto=new ConceptCodeDto();
		editConceptCodeDto.setId((long) 1);
		editConceptCodeDto.setCode("code");
		editConceptCodeDto.setName("name");
		
		AuthenticatedUser currentUser=mock(AuthenticatedUser.class);
		when(userContext.getCurrentUser()).thenReturn(currentUser);
		
		when(conceptCodeService.update(any(ConceptCodeDto.class))).thenReturn(editConceptCodeDto);
		
		
		mockMvc.perform(post("/sysadmin/conceptCode/edit/1")
				.param("code", "code")
				.param("name", "name"))
			.andExpect(view().name("redirect:../../conceptCodeList"));
	}
	
	@Test
	public void testEditConceptCodeForm_Post_throwException() throws Exception{
		
		ConceptCodeDto editConceptCodeDto=new ConceptCodeDto();
		editConceptCodeDto.setId((long) 1);
		editConceptCodeDto.setCode("code");
		editConceptCodeDto.setName("name");
		
		AuthenticatedUser currentUser=mock(AuthenticatedUser.class);
		when(userContext.getCurrentUser()).thenReturn(currentUser);
		
		when(conceptCodeService.update(any(ConceptCodeDto.class))).thenThrow(new ConceptCodeNotFoundException());
		
		
		mockMvc.perform(post("/sysadmin/conceptCode/edit/1")
				.param("code", "code")
				.param("name", "name"))
			.andExpect(view().name("redirect:../../conceptCodeList"));
	}
	
	@Test
	public void testValueSetBatchUpload() throws Exception{
//		List<ConceptCodeDto> conceptCodeDtos=(List<ConceptCodeDto>) mock(List.class);
//		List<CodeSystemDto> codeSystems = (List<CodeSystemDto>) mock(List.class);
//		List<ValueSetDto> valueSets = (List<ValueSetDto>) mock(List.class);
//		
//		when(conceptCodeService.findAll()).thenReturn(conceptCodeDtos);
//		when(codeSystemService.findAll()).thenReturn(codeSystems);
//		when(valueSetService.findAll()).thenReturn(valueSets);
//		
//		mockMvc.perform(post("/sysadmin/conceptCode/batchUpload"))
//			.andExpect(status().isOk())
//			.andExpect(model().attribute("conceptCodeDtos",conceptCodeDtos))
//			.andExpect(view().name("views/sysadmin/conceptCodeList"));
//		
//		
		
//		MockMultipartFile file = new MockMultipartFile("file", "orig", null, "bar".getBytes());
//		mockMvc.perform(fileUpload("/sysadmin/conceptCode/batchUpload").file(file)
//				.param("name", "mocked_name")
//				.param("description", "mocked_description")
//				.param("documentType", "mocked_type"))
//			.andExpect(view().name("redirect:../conceptCodeList"));
		
		
//		 mockMvc.perform(post("/form"))
//		   .andExpect(status.isOk())
//		   .andExpect(redirectedUrl("/person/1"))
//		   .andExpect(model().size(1))
//		   .andExpect(model().attributeExists("person"))
//		   .andExpect(flash().attributeCount(1))
//		   .andExpect(flash().attribute("message", "success!"));

	}


}
