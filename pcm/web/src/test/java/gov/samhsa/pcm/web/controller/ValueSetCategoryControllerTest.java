package gov.samhsa.pcm.web.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import gov.samhsa.pcm.common.AuthenticatedUser;
import gov.samhsa.pcm.common.UserContext;
import gov.samhsa.pcm.service.dto.ValueSetCategoryDto;
import gov.samhsa.pcm.service.valueset.ValueSetCategoryNotFoundException;
import gov.samhsa.pcm.service.valueset.ValueSetCategoryService;

import java.util.List;

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
public class ValueSetCategoryControllerTest {
	
	@InjectMocks
	ValueSetCategoryController valueSetCategoryController;
	
	@Mock
	ValueSetCategoryService valueSetCategoryService;
	
	@Mock
	UserContext userContext;
	
		
	MockMvc mockMvc;
	
	@Before
	public void setUp(){
		mockMvc = MockMvcBuilders.standaloneSetup(this.valueSetCategoryController).build();
	}
	
	@Test
	public void testGetValueSetCategoryList() throws Exception{
		List<ValueSetCategoryDto> valueSetCategoryDtos=(List<ValueSetCategoryDto>) mock(List.class);
		when(valueSetCategoryService.findAll()).thenReturn(valueSetCategoryDtos);
		
		mockMvc.perform(get("/sysadmin/valueSetCategoryList"))
			.andExpect(status().isOk())
			.andExpect(model().attribute("valueSetCategoryDtos",valueSetCategoryDtos))
			.andExpect(view().name("views/sysadmin/valueSetCategoryList"));
	}
	
		
	@Test
	public void testGetValueSetCategoryAdd() throws Exception{
		
		ValueSetCategoryDto valueSetCategoryDto=new ValueSetCategoryDto();
		AuthenticatedUser currentUser=mock(AuthenticatedUser.class);
		when(userContext.getCurrentUser()).thenReturn(currentUser);
		
		mockMvc.perform(get("/sysadmin/valueSetCategoryAdd.html")
				.sessionAttr("valueSetCategoryDto", valueSetCategoryDto))
			.andExpect(status().isOk())
			.andExpect(view().name("views/sysadmin/valueSetCategoryAdd"));
	}
	
	
	@Test
	public void testCreatedValueSetForm() throws Exception{
		
		AuthenticatedUser currentUser=mock(AuthenticatedUser.class);
		when(userContext.getCurrentUser()).thenReturn(currentUser);
		
		final String username="username";
		when(currentUser.getUsername()).thenReturn(username);
		
		ValueSetCategoryDto valueSetCategoryDto=mock(ValueSetCategoryDto.class);
		when(valueSetCategoryService.create(any(ValueSetCategoryDto.class))).thenReturn(valueSetCategoryDto);
		
		when(valueSetCategoryDto.getCode()).thenReturn("code");
		when(valueSetCategoryDto.getName()).thenReturn("name");
		
		mockMvc.perform(post("/sysadmin/valueSetCategory/create")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
						.param("name","valueSetCatName")
						.param("code","valueSetCatCode"))
			.andExpect(status().isFound())
			.andExpect(view().name("redirect:../valueSetCategoryList"));
	}
	
		
	@Test
	public void testCreatedValueSetForm_DataIntegrityViolationException() throws Exception{
		
		AuthenticatedUser currentUser=mock(AuthenticatedUser.class);
		when(userContext.getCurrentUser()).thenReturn(currentUser);
		
		final String username="username";
		when(currentUser.getUsername()).thenReturn(username);
		
		ValueSetCategoryDto valueSetCategoryDto=mock(ValueSetCategoryDto.class);
		when(valueSetCategoryService.create(any(ValueSetCategoryDto.class))).thenThrow(new DataIntegrityViolationException("DataIntegrityViolationException"));
		
		when(valueSetCategoryDto.getCode()).thenReturn("code");
		when(valueSetCategoryDto.getName()).thenReturn("name");
		
		mockMvc.perform(post("/sysadmin/valueSetCategory/create")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
						.param("name","valueSetCatName")
						.param("code","valueSetCatCode"))
			.andExpect(status().isFound())
			.andExpect(view().name("redirect:../valueSetCategoryAdd.html"));
		
	}
	
	@Test
	public void testDeleteValueSetCategory() throws Exception{
		
		ValueSetCategoryDto deleted=new ValueSetCategoryDto();
		
		when(valueSetCategoryService.delete(anyLong())).thenReturn(deleted);
		
		mockMvc.perform(post("/sysadmin/valueSetCategory/delete/1"))
			.andExpect(view().name("redirect:../../valueSetCategoryList"));
	}
	
	@Test
	public void testDeleteValueSetForm_throwException() throws Exception{
		
		when(valueSetCategoryService.delete(anyLong())).thenThrow(new ValueSetCategoryNotFoundException());
		
		mockMvc.perform(post("/sysadmin/valueSetCategory/delete/1"))
			.andExpect(view().name("redirect:../../valueSetCategoryList"));
	}
	
	@Test
	public void testEditValueSetForm() throws Exception{
		
		ValueSetCategoryDto editValueSetCategoryDto=new ValueSetCategoryDto();
		
		when(valueSetCategoryService.findById(anyLong())).thenReturn(editValueSetCategoryDto);
		
		mockMvc.perform(get("/sysadmin/valueSetCategory/edit/1"))
			.andExpect(status().isOk())
			.andExpect(model().attribute("valueSetCategoryDto", editValueSetCategoryDto))
			.andExpect(view().name("views/sysadmin/valueSetCategoryEdit"));
	}
	
	@Test
	public void testEditValueSetCategoryForm2() throws Exception{
		
		ValueSetCategoryDto editValueSetCategoryDto=null;
		
		when(valueSetCategoryService.findById(anyLong())).thenReturn(editValueSetCategoryDto);
		
		mockMvc.perform(get("/sysadmin/valueSetCategory/edit/1"))
			.andExpect(model().attribute("valueSetDto", editValueSetCategoryDto))
			.andExpect(view().name("redirect:../../valueSetCategoryList"));
	}
	
	@Test
	public void testEditValueSetCategoryForm_Post() throws Exception{
		
		ValueSetCategoryDto editValueSetCategoryDto=new ValueSetCategoryDto();
		editValueSetCategoryDto.setId((long) 1);
		editValueSetCategoryDto.setCode("code");
		editValueSetCategoryDto.setName("name");
		
		AuthenticatedUser currentUser=mock(AuthenticatedUser.class);
		when(userContext.getCurrentUser()).thenReturn(currentUser);
		
		when(valueSetCategoryService.update(any(ValueSetCategoryDto.class))).thenReturn(editValueSetCategoryDto);
		
		
		mockMvc.perform(post("/sysadmin/valueSetCategory/edit/1")
				.param("code", "code")
				.param("name", "name"))
			.andExpect(view().name("redirect:../../valueSetCategoryList"));
	}
	
	@Test
	public void testEditValueSetCategoryForm_Post_throwException() throws Exception{
		
		ValueSetCategoryDto editValueSetCategoryDto=new ValueSetCategoryDto();
		editValueSetCategoryDto.setId((long) 1);
		editValueSetCategoryDto.setCode("code");
		editValueSetCategoryDto.setName("name");
		
		AuthenticatedUser currentUser=mock(AuthenticatedUser.class);
		when(userContext.getCurrentUser()).thenReturn(currentUser);
		
		when(valueSetCategoryService.update(any(ValueSetCategoryDto.class))).thenThrow(new ValueSetCategoryNotFoundException());
		
		
		mockMvc.perform(post("/sysadmin/valueSetCategory/edit/1")
				.param("code", "code")
				.param("name", "name"))
			.andExpect(view().name("redirect:../../valueSetCategoryList"));
	}
	
	


}
