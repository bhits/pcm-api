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
import gov.samhsa.consent2share.service.dto.ValueSetCategoryDto;
import gov.samhsa.consent2share.service.dto.ValueSetDto;
import gov.samhsa.consent2share.service.dto.ValueSetVSCDto;
import gov.samhsa.consent2share.service.valueset.ValueSetCategoryNotFoundException;
import gov.samhsa.consent2share.service.valueset.ValueSetCategoryService;
import gov.samhsa.consent2share.service.valueset.ValueSetNotFoundException;
import gov.samhsa.consent2share.service.valueset.ValueSetService;
import gov.samhsa.consent2share.web.controller.ValueSetController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Ignore;
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
public class ValueSetControllerTest {
	
	@InjectMocks
	ValueSetController valueSetController;
	
	@Mock
	ValueSetService valueSetService;
	
	@Mock
	ValueSetCategoryService valueSetCategoryService;
	
	@Mock
	UserContext userContext;
	
		
	MockMvc mockMvc;
	
	@Before
	public void setUp(){
		mockMvc = MockMvcBuilders.standaloneSetup(this.valueSetController).build();
	}
	
	@Ignore
	@Test
	public void testGetValueSetList() throws Exception{
		List<ValueSetDto> valueSetDtos=(List<ValueSetDto>) mock(List.class);
		when(valueSetService.findAll()).thenReturn(valueSetDtos);
		List<ValueSetCategoryDto> valueSetCategoryDtos=(List<ValueSetCategoryDto>) mock(List.class);
		when(valueSetCategoryService.findAll()).thenReturn(valueSetCategoryDtos);
		
		mockMvc.perform(get("/sysadmin/valueSetList"))
			.andExpect(status().isOk())
			.andExpect(model().attribute("valueSetDtos",valueSetDtos))
			.andExpect(model().attribute("valueSetCategoryDtos",valueSetCategoryDtos))
			.andExpect(view().name("views/sysadmin/valueSetList"));
	}
	
	@Ignore
	@Test
	public void testAjaxSearchValueSet_By_Name() throws Exception{
		List<ValueSetDto> valueSets= new ArrayList<ValueSetDto>();
		Map<String, Object> valueSetPageMap = new HashMap<String, Object>();
		ValueSetDto vsdto=new ValueSetDto();
		vsdto.setName("disorder");		
		valueSets.add(vsdto);
		valueSetPageMap.put("valueSets", valueSets);
		when(valueSetService.findAllByName(anyString(), anyString(), anyInt())).thenReturn(valueSetPageMap);
		mockMvc.perform(get("/sysadmin/valueSet/ajaxSearchValueSet?searchCategory=name&&searchTerm=dis"))
		.andExpect(status().isOk())
		.andExpect(content().contentType("application/json;charset=UTF-8"));
	}
	
	@Ignore
	@Test
	public void testAjaxSearchValueSet_By_Code() throws Exception{
		List<ValueSetDto> valueSets= new ArrayList<ValueSetDto>();
		Map<String, Object> valueSetPageMap = new HashMap<String, Object>();
		ValueSetDto vsdto=new ValueSetDto();
		vsdto.setCode("disorder");		
		valueSets.add(vsdto);
		valueSetPageMap.put("valueSets", valueSets);
		when(valueSetService.findAllByName(anyString(), anyString(), anyInt())).thenReturn(valueSetPageMap);
		mockMvc.perform(get("/sysadmin/valueSet/ajaxSearchValueSet?searchCategory=code&&searchTerm=dis"))
		.andExpect(status().isOk())
		.andExpect(content().contentType("application/json;charset=UTF-8"));
	}
	
	@Test
	public void testGetValueSetAdd() throws Exception{
		
		ValueSetVSCDto valueSetVSCDto = mock(ValueSetVSCDto.class);
		when(valueSetService.create()).thenReturn(valueSetVSCDto);
		ValueSetDto valueSetDto = new ValueSetDto();
		valueSetDto.setCode("code");
		valueSetDto.setName("name");
		valueSetDto.setValueSetCategoryId((long)1);
				
		mockMvc.perform(get("/sysadmin/valueSetAdd.html")
				.sessionAttr("valueSetDto", valueSetDto))
			.andExpect(status().isOk())
			.andExpect(view().name("views/sysadmin/valueSetAdd"));
	}
	
	@Test
	public void testGetValueSetAdd_throwException() throws Exception{
		
		when(valueSetService.create()).thenThrow(new ValueSetCategoryNotFoundException());
		ValueSetDto valueSetDto = new ValueSetDto();
		valueSetDto.setCode("code");
		valueSetDto.setName("name");
		valueSetDto.setValueSetCategoryId((long)1);
				
		mockMvc.perform(get("/sysadmin/valueSetAdd.html")
				.sessionAttr("valueSetDto", valueSetDto))
			.andExpect(status().isOk())
			.andExpect(view().name("views/sysadmin/valueSetList"));
	}
	
	@Test
	public void testCreatedValueSetForm() throws Exception{
		
		ValueSetDto valueSetDtoModel=new ValueSetDto();
		
		AuthenticatedUser currentUser=mock(AuthenticatedUser.class);
		when(userContext.getCurrentUser()).thenReturn(currentUser);
		
		final String username="username";
		when(currentUser.getUsername()).thenReturn(username);
		
		ValueSetDto valueSetDto=mock(ValueSetDto.class);
		when(valueSetService.create(any(ValueSetDto.class))).thenReturn(valueSetDto);
		
		when(valueSetDto.getCode()).thenReturn("code");
		when(valueSetDto.getName()).thenReturn("name");
		
		mockMvc.perform(post("/sysadmin/valueSet/create")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.sessionAttr("valueSetDto", valueSetDtoModel)
						.param("name","valueSetCatName")
						.param("code","valueSetCatCode")
						.param("valueSetCategoryId","1")
						.param("description","value set"))
			.andExpect(status().isFound())
			.andExpect(view().name("redirect:../valueSetList?panelState=addnew"));
	}
	
	@Test
	public void testCreatedValueSetForm_ValueSetCategoryNotFoundException() throws Exception{
		
		ValueSetDto valueSetDtoModel=new ValueSetDto();
		
		AuthenticatedUser currentUser=mock(AuthenticatedUser.class);
		when(userContext.getCurrentUser()).thenReturn(currentUser);
		
		final String username="username";
		when(currentUser.getUsername()).thenReturn(username);
		
		ValueSetDto valueSetDto=mock(ValueSetDto.class);
		when(valueSetService.create(any(ValueSetDto.class))).thenThrow(new ValueSetCategoryNotFoundException());
		
		when(valueSetDto.getCode()).thenReturn("code");
		when(valueSetDto.getName()).thenReturn("name");
		
		mockMvc.perform(post("/sysadmin/valueSet/create")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.sessionAttr("valueSetDto", valueSetDtoModel)
						.param("name","valueSetCatName")
						.param("code","valueSetCatCode")
						.param("description","value set"))
			.andExpect(status().isFound())
			.andExpect(view().name("redirect:../valueSetAdd.html?panelState=addnew"));
	}
	
	@Test
	public void testCreatedValueSetForm_DataIntegrityViolationException() throws Exception{
		
		ValueSetDto valueSetDtoModel=new ValueSetDto();
		
		AuthenticatedUser currentUser=mock(AuthenticatedUser.class);
		when(userContext.getCurrentUser()).thenReturn(currentUser);
		
		final String username="username";
		when(currentUser.getUsername()).thenReturn(username);
		
		ValueSetDto valueSetDto=mock(ValueSetDto.class);
		when(valueSetService.create(any(ValueSetDto.class))).thenThrow(new DataIntegrityViolationException("DataIntegrityViolationException"));
		
		when(valueSetDto.getCode()).thenReturn("code");
		when(valueSetDto.getName()).thenReturn("name");
		
		mockMvc.perform(post("/sysadmin/valueSet/create")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.sessionAttr("valueSetDto", valueSetDtoModel)
						.param("name","valueSetCatName")
						.param("code","valueSetCatCode")
						.param("description","value set"))
			.andExpect(status().isFound())
			.andExpect(view().name("redirect:../valueSetAdd.html?panelState=addnew"));
	}
	
	@Test
	public void testDeleteValueSet() throws Exception{
		
		ValueSetDto deleted=new ValueSetDto();
		deleted.setCode("TESTCODE");
		deleted.setName("Test Name");
		
		when(valueSetService.delete(anyLong())).thenReturn(deleted);
		
		mockMvc.perform(delete("/sysadmin/valueSet/delete/1"))
		.andExpect(content().string("ValueSet with Code: TESTCODE and Name: Test Name is deleted Successfully."));
	}
	
	@Test
	public void testDeleteValueSet_throwException() throws Exception{
		
		when(valueSetService.delete(anyLong())).thenThrow(new ValueSetNotFoundException());
		
		mockMvc.perform(delete("/sysadmin/valueSet/delete/1"))
			.andExpect(status().isInternalServerError());
			//.andExpect(content().string("Deleted valueSet was not found."));
	}
	
	@Test
	public void testDeleteValueSet_withPost() throws Exception{
		
		ValueSetDto deleted=new ValueSetDto();
		
		when(valueSetService.delete(anyLong())).thenReturn(deleted);
		
		mockMvc.perform(post("/sysadmin/valueSet/delete/1"))
			.andExpect(status().isMethodNotAllowed());
	}
	
	@Test
	public void testEditValueSet() throws Exception{
		
		ValueSetDto editValueSetDto=new ValueSetDto();
		
		when(valueSetService.findById(anyLong())).thenReturn(editValueSetDto);
		
		mockMvc.perform(get("/sysadmin/valueSet/edit/1"))
			.andExpect(status().isOk())
			.andExpect(model().attribute("valueSetDto", editValueSetDto))
			.andExpect(view().name("views/sysadmin/valueSetEdit"));
	}
	
	@Test
	public void testEditValueSet2() throws Exception{
		
		ValueSetDto editValueSetDto=null;
		
		when(valueSetService.findById(anyLong())).thenReturn(editValueSetDto);
		
		mockMvc.perform(get("/sysadmin/valueSet/edit/1"))
			.andExpect(model().attribute("valueSetDto", editValueSetDto))
			.andExpect(view().name("redirect:../../valueSetList"));
	}
	
	@Test
	public void testEditValueSetForm_Post() throws Exception{
		
		ValueSetDto editValueSetDto=new ValueSetDto();
		editValueSetDto.setId((long) 1);
		editValueSetDto.setCode("code");
		editValueSetDto.setName("name");
		
		AuthenticatedUser currentUser=mock(AuthenticatedUser.class);
		when(userContext.getCurrentUser()).thenReturn(currentUser);
		
		when(valueSetService.update(any(ValueSetDto.class))).thenReturn(editValueSetDto);
		
		
		mockMvc.perform(post("/sysadmin/valueSet/edit/1")
				.param("code", "code")
				.param("name", "name"))
			.andExpect(view().name("redirect:../../valueSetList"));
	}
	
	@Test
	public void testEditValueSetForm_Post_throwException() throws Exception{
		
		ValueSetDto editValueSetDto=new ValueSetDto();
		editValueSetDto.setId((long) 1);
		editValueSetDto.setCode("code");
		editValueSetDto.setName("name");
		
		AuthenticatedUser currentUser=mock(AuthenticatedUser.class);
		when(userContext.getCurrentUser()).thenReturn(currentUser);
		
		when(valueSetService.update(any(ValueSetDto.class))).thenThrow(new ValueSetNotFoundException());
		
		
		mockMvc.perform(post("/sysadmin/valueSet/edit/1")
				.param("code", "code")
				.param("name", "name"))
			.andExpect(view().name("redirect:../../valueSetList"));
	}
	
	


}
