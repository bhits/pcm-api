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
import gov.samhsa.pcm.service.dto.CodeSystemDto;
import gov.samhsa.pcm.service.valueset.CodeSystemNotFoundException;
import gov.samhsa.pcm.service.valueset.CodeSystemService;

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
public class CodeSystemControllerTest {
	
	@InjectMocks
	CodeSystemController codeSystemController;
	
	@Mock
	CodeSystemService codeSystemService;
	
	@Mock
	UserContext userContext;
	
		
	MockMvc mockMvc;
	
	@Before
	public void setUp(){
		mockMvc = MockMvcBuilders.standaloneSetup(this.codeSystemController).build();
	}
	

	@Test
	public void testGetCodeSystemList() throws Exception{
		List<CodeSystemDto> codeSystemDtos=(List<CodeSystemDto>) mock(List.class);
		when(codeSystemService.findAll()).thenReturn(codeSystemDtos);
		
		mockMvc.perform(get("/sysadmin/home"))
			.andExpect(status().isOk())
			.andExpect(model().attribute("codeSystemDtos",codeSystemDtos))
			.andExpect(view().name("views/sysadmin/home"));
	}
	
		
	@Test
	public void testGetCodeSystemAdd() throws Exception{
		
		CodeSystemDto codeSystemDto=new CodeSystemDto();
		AuthenticatedUser currentUser=mock(AuthenticatedUser.class);
		when(userContext.getCurrentUser()).thenReturn(currentUser);
		
		mockMvc.perform(get("/sysadmin/codeSystemAdd.html")
				.sessionAttr("codeSystemDto", codeSystemDto))
			.andExpect(status().isOk())
			.andExpect(view().name("views/sysadmin/codeSystemAdd"));
	}
	
	
	@Test
	public void testCreatedCodeSystemForm() throws Exception{
		
		AuthenticatedUser currentUser=mock(AuthenticatedUser.class);
		when(userContext.getCurrentUser()).thenReturn(currentUser);
		
		final String username="username";
		when(currentUser.getUsername()).thenReturn(username);
		
		CodeSystemDto codeSystemDto=mock(CodeSystemDto.class);
		when(codeSystemService.create(any(CodeSystemDto.class))).thenReturn(codeSystemDto);
		
		when(codeSystemDto.getCode()).thenReturn("code");
		when(codeSystemDto.getName()).thenReturn("name");
		
		mockMvc.perform(post("/sysadmin/codeSystem/create")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
						.param("name","name")
						.param("code","code")
						.param("codeSystemOId","codeSystemOId"))
			.andExpect(status().isFound())
			.andExpect(view().name("redirect:../home"));
	}
	
	
		
	@Test
	public void testCreatedValueSetForm_DataIntegrityViolationException() throws Exception{
		
		AuthenticatedUser currentUser=mock(AuthenticatedUser.class);
		when(userContext.getCurrentUser()).thenReturn(currentUser);
		
		final String username="username";
		when(currentUser.getUsername()).thenReturn(username);
		
		CodeSystemDto codeSystemDto=mock(CodeSystemDto.class);
		when(codeSystemService.create(any(CodeSystemDto.class))).thenThrow(new DataIntegrityViolationException("DataIntegrityViolationException"));
		
		when(codeSystemDto.getCode()).thenReturn("code");
		when(codeSystemDto.getName()).thenReturn("name");
		
		mockMvc.perform(post("/sysadmin/codeSystem/create")
						.param("name","name")
						.param("code","code")
						.param("codeSystemOId","codeSystemOId"))
			.andExpect(status().isFound())
			.andExpect(view().name("redirect:../codeSystemAdd.html"));
		
	}
	
	@Test
	public void testDeleteCodeSystem() throws Exception{
		
		CodeSystemDto deleted=new CodeSystemDto();
		
		when(codeSystemService.delete(anyLong())).thenReturn(deleted);
		
		mockMvc.perform(post("/sysadmin/codeSystem/delete/1"))
			.andExpect(view().name("redirect:../../home"));
	}
	
	@Test
	public void testDeleteCodeSystemForm_throwException() throws Exception{
		
		when(codeSystemService.delete(anyLong())).thenThrow(new CodeSystemNotFoundException());
		
		mockMvc.perform(post("/sysadmin/codeSystem/delete/1"))
			.andExpect(view().name("redirect:../../home"));
	}
	
	@Test
	public void testEditCodeSystemForm() throws Exception{
		
		CodeSystemDto editCodeSystemDto=new CodeSystemDto();
		
		when(codeSystemService.findById(anyLong())).thenReturn(editCodeSystemDto);
		
		mockMvc.perform(get("/sysadmin/codeSystem/edit/1"))
			.andExpect(status().isOk())
			.andExpect(model().attribute("codeSystemDto", editCodeSystemDto))
			.andExpect(view().name("views/sysadmin/codeSystemEdit"));
	}
	
	@Test
	public void testEditCodeSystemForm2() throws Exception{
		
		CodeSystemDto editCodeSystemDto=null;
		
		when(codeSystemService.findById(anyLong())).thenReturn(editCodeSystemDto);
		
		mockMvc.perform(get("/sysadmin/codeSystem/edit/1"))
			.andExpect(model().attribute("codeSystemDto", editCodeSystemDto))
			.andExpect(view().name("redirect:../../home"));
	}
	
	@Test
	public void testEditCodeSystemForm_Post() throws Exception{
		
		CodeSystemDto editCodeSystemDto=new CodeSystemDto();
		editCodeSystemDto.setId((long) 1);
		editCodeSystemDto.setCode("code");
		editCodeSystemDto.setName("name");
		
		AuthenticatedUser currentUser=mock(AuthenticatedUser.class);
		when(userContext.getCurrentUser()).thenReturn(currentUser);
		
		when(codeSystemService.update(any(CodeSystemDto.class))).thenReturn(editCodeSystemDto);
		
		
		mockMvc.perform(post("/sysadmin/codeSystem/edit/1")
				.param("code", "code")
				.param("name", "name"))
			.andExpect(view().name("redirect:../../home"));
	}
	
	@Test
	public void testEditCodeSystemForm_Post_throwException() throws Exception{
		
		CodeSystemDto editCodeSystemDto=new CodeSystemDto();
		editCodeSystemDto.setId((long) 1);
		editCodeSystemDto.setCode("code");
		editCodeSystemDto.setName("name");
		
		AuthenticatedUser currentUser=mock(AuthenticatedUser.class);
		when(userContext.getCurrentUser()).thenReturn(currentUser);
		
		when(codeSystemService.update(any(CodeSystemDto.class))).thenThrow(new CodeSystemNotFoundException());
		
		
		mockMvc.perform(post("/sysadmin/codeSystem/edit/1")
				.param("code", "code")
				.param("name", "name"))
			.andExpect(view().name("redirect:../../home"));
	}
	
	

}
