package gov.samhsa.consent2share.web.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import gov.samhsa.consent2share.common.AuthenticatedUser;
import gov.samhsa.consent2share.common.UserContext;
import gov.samhsa.consent2share.service.dto.CodeSystemVersionDto;
import gov.samhsa.consent2share.service.valueset.CodeSystemNotFoundException;
import gov.samhsa.consent2share.service.valueset.CodeSystemVersionNotFoundException;
import gov.samhsa.consent2share.service.valueset.CodeSystemVersionService;
import gov.samhsa.consent2share.web.controller.CodeSystemVersionController;

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
public class CodeSystemVersionControllerTest {
	
	@InjectMocks
	CodeSystemVersionController codeSystemVersionController;
	
	@Mock
	CodeSystemVersionService codeSystemVersionService;
	
	@Mock
	UserContext userContext;
	
		
	MockMvc mockMvc;
	
	@Before
	public void setUp(){
		mockMvc = MockMvcBuilders.standaloneSetup(this.codeSystemVersionController).build();
	}
	

	@Test
	public void testGetCodeSystemVersionList() throws Exception{
		List<CodeSystemVersionDto> codeSystemVersionDtos=(List<CodeSystemVersionDto>) mock(List.class);
		when(codeSystemVersionService.findAll()).thenReturn(codeSystemVersionDtos);
		
		mockMvc.perform(get("/sysadmin/codeSystemVersionList"))
			.andExpect(status().isOk())
			.andExpect(model().attribute("codeSystemVersionDtos",codeSystemVersionDtos))
			.andExpect(view().name("views/sysadmin/codeSystemVersionList"));
	}
	
		
	@Test
	public void testGetCodeSystemVersionAdd() throws Exception{
		
		CodeSystemVersionDto codeSystemVersionDto=new CodeSystemVersionDto();
		AuthenticatedUser currentUser=mock(AuthenticatedUser.class);
		when(userContext.getCurrentUser()).thenReturn(currentUser);
		
		mockMvc.perform(get("/sysadmin/codeSystemVersionAdd.html")
				.sessionAttr("codeSystemVersionDto", codeSystemVersionDto))
			.andExpect(status().isOk())
			.andExpect(view().name("views/sysadmin/codeSystemVersionAdd"));
	}
	
	@Test
	public void testGetCodeSystemVersionAdd_ThrowException() throws Exception{
		
		CodeSystemVersionDto codeSystemVersionDto=new CodeSystemVersionDto();
		AuthenticatedUser currentUser=mock(AuthenticatedUser.class);
		when(userContext.getCurrentUser()).thenReturn(currentUser);
		
		when(codeSystemVersionService.create()).thenThrow(new CodeSystemNotFoundException());
		
		mockMvc.perform(get("/sysadmin/codeSystemVersionAdd.html")
				.sessionAttr("codeSystemVersionDto", codeSystemVersionDto))
			.andExpect(status().isOk())
			.andExpect(view().name("views/sysadmin/codeSystemVersionList"));
	}
	
	
	@Test
	public void testCreatedCodeSystemVersionForm() throws Exception{
		
		AuthenticatedUser currentUser=mock(AuthenticatedUser.class);
		when(userContext.getCurrentUser()).thenReturn(currentUser);
		
		final String username="username";
		when(currentUser.getUsername()).thenReturn(username);
		
		CodeSystemVersionDto codeSystemVersionDto=mock(CodeSystemVersionDto.class);
		when(codeSystemVersionService.create(any(CodeSystemVersionDto.class))).thenReturn(codeSystemVersionDto);
		
		when(codeSystemVersionDto.getCode()).thenReturn("code");
		when(codeSystemVersionDto.getName()).thenReturn("name");
		
		mockMvc.perform(post("/sysadmin/codeSystemVersion/create")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
						.param("name","name")
						.param("code","code"))
			.andExpect(status().isFound())
			.andExpect(view().name("redirect:../../codeSystemVersionList"));
	}
	
	
		
	@Test
	public void testCreatedCodeSystemVersionListForm_DataIntegrityViolationException() throws Exception{
		
		AuthenticatedUser currentUser=mock(AuthenticatedUser.class);
		when(userContext.getCurrentUser()).thenReturn(currentUser);
		
		final String username="username";
		when(currentUser.getUsername()).thenReturn(username);
		
		CodeSystemVersionDto codeSystemVersionDto=mock(CodeSystemVersionDto.class);
		when(codeSystemVersionService.create(any(CodeSystemVersionDto.class))).thenThrow(new DataIntegrityViolationException("DataIntegrityViolationException"));
		
		when(codeSystemVersionDto.getCode()).thenReturn("code");
		when(codeSystemVersionDto.getName()).thenReturn("name");
		
		mockMvc.perform(post("/sysadmin/codeSystemVersion/create")
						.param("name","name")
						.param("code","code"))
			.andExpect(status().isFound())
			.andExpect(view().name("redirect:../codeSystemVersionAdd.html"));
		
	}
	
	@Test
	public void testCreatedCodeSystemVersionListForm_throw_CodeSystemNotFoundException () throws Exception{
		
		AuthenticatedUser currentUser=mock(AuthenticatedUser.class);
		when(userContext.getCurrentUser()).thenReturn(currentUser);
		
		final String username="username";
		when(currentUser.getUsername()).thenReturn(username);
		
		CodeSystemVersionDto codeSystemVersionDto=mock(CodeSystemVersionDto.class);
		when(codeSystemVersionService.create(any(CodeSystemVersionDto.class))).thenThrow(new CodeSystemNotFoundException ());
		
		when(codeSystemVersionDto.getCode()).thenReturn("code");
		when(codeSystemVersionDto.getName()).thenReturn("name");
		
		mockMvc.perform(post("/sysadmin/codeSystemVersion/create")
						.param("name","name")
						.param("code","code"))
			.andExpect(status().isFound())
			.andExpect(view().name("redirect:../codeSystemVersionAdd.html"));
		
	}
	
	@Test
	public void testDeleteCodeSystemVersion() throws Exception{
		
		CodeSystemVersionDto deleted=new CodeSystemVersionDto();
		
		when(codeSystemVersionService.delete(anyLong())).thenReturn(deleted);
		
		mockMvc.perform(post("/sysadmin/codeSystemVersion/delete/1"))
			.andExpect(view().name("redirect:../../codeSystemVersionList"));
	}
	
	@Test
	public void testDeleteCodeSystemVersionForm_throwException() throws Exception{
		
		when(codeSystemVersionService.delete(anyLong())).thenThrow(new CodeSystemVersionNotFoundException());
		
		mockMvc.perform(post("/sysadmin/codeSystemVersion/delete/1"))
			.andExpect(view().name("redirect:../../codeSystemVersionList"));
	}
	
	@Test
	public void testEditCodeSystemVersionForm() throws Exception{
		
		CodeSystemVersionDto editCodeSystemVersionDto=new CodeSystemVersionDto();
		
		when(codeSystemVersionService.findById(anyLong())).thenReturn(editCodeSystemVersionDto);
		
		mockMvc.perform(get("/sysadmin/codeSystemVersion/edit/1"))
			.andExpect(status().isOk())
			.andExpect(model().attribute("codeSystemVersionDto", editCodeSystemVersionDto))
			.andExpect(view().name("views/sysadmin/codeSystemVersionEdit"));
	}
	
	@Test
	public void testEditCodeSystemVersionForm2() throws Exception{
		
		CodeSystemVersionDto editCodeSystemVersionDto=null;
		
		when(codeSystemVersionService.findById(anyLong())).thenReturn(editCodeSystemVersionDto);
		
		mockMvc.perform(get("/sysadmin/codeSystemVersion/edit/1"))
			.andExpect(model().attribute("codeSystemVersionDto", editCodeSystemVersionDto))
			.andExpect(view().name("redirect:../../codeSystemVersionList"));
	}
	
	@Test
	public void testEditCodeSystemVersionForm_Post() throws Exception{
		
		CodeSystemVersionDto editCodeSystemVersionDto=new CodeSystemVersionDto();
		editCodeSystemVersionDto.setId((long) 1);
		editCodeSystemVersionDto.setCode("code");
		editCodeSystemVersionDto.setName("name");
		
		AuthenticatedUser currentUser=mock(AuthenticatedUser.class);
		when(userContext.getCurrentUser()).thenReturn(currentUser);
		
		when(codeSystemVersionService.update(any(CodeSystemVersionDto.class))).thenReturn(editCodeSystemVersionDto);
		
		
		mockMvc.perform(post("/sysadmin/codeSystemVersion/edit/1")
				.param("code", "code")
				.param("name", "name"))
			.andExpect(view().name("redirect:../../codeSystemVersionList"));
	}
	
	@Test
	public void testEditCodeSystemVersionForm_Post_throwException() throws Exception{
		
		CodeSystemVersionDto editCodeSystemVersionDto=new CodeSystemVersionDto();
		editCodeSystemVersionDto.setId((long) 1);
		editCodeSystemVersionDto.setCode("code");
		editCodeSystemVersionDto.setName("name");
		
		AuthenticatedUser currentUser=mock(AuthenticatedUser.class);
		when(userContext.getCurrentUser()).thenReturn(currentUser);
		
		when(codeSystemVersionService.update(any(CodeSystemVersionDto.class))).thenThrow(new CodeSystemVersionNotFoundException());
		
		
		mockMvc.perform(post("/sysadmin/codeSystemVersion/edit/1")
				.param("code", "code")
				.param("name", "name"))
			.andExpect(view().name("redirect:../../codeSystemVersionList"));
	}
	
	

}
