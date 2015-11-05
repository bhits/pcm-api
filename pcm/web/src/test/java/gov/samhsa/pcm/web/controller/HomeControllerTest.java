package gov.samhsa.pcm.web.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import gov.samhsa.pcm.common.AuthenticatedUser;
import gov.samhsa.pcm.common.UserContext;
import gov.samhsa.pcm.domain.account.Users;
import gov.samhsa.pcm.domain.account.UsersRepository;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(MockitoJUnitRunner.class)
public class HomeControllerTest {
	
	@InjectMocks
	HomeController homeController = new HomeController();
	
	@Mock
	private UserContext userContext;
	
	@Mock
	private UsersRepository usersRepository;
	
	MockMvc mockMvc;
	
	@Before
	public void before() {
		mockMvc = MockMvcBuilders.standaloneSetup(this.homeController).build();
	}

	@Test
	public void testHome() throws Exception {
		mockMvc.perform(get("/"))
			.andExpect(view().name("redirect:/defaultLoginPage.html"));
	}

	@Test
	public void testIndex() throws Exception {
		AuthenticatedUser currentUser = mock(AuthenticatedUser.class);
		when(userContext.getCurrentUser()).thenReturn(currentUser);
		when(currentUser.getUsername()).thenReturn("username");
		
		Users users= mock(Users.class);
		when(usersRepository.loadUserByUsername("username")).thenReturn(users);
		Set<GrantedAuthority> auths=new HashSet<GrantedAuthority>();
		auths.add(new SimpleGrantedAuthority("ROLE_USER"));
		
		when(users.getAuthorities()).thenReturn(auths);
		
		mockMvc.perform(get("/index.html"))
			.andExpect(view().name("redirect:/patients/home.html"));
	}
	@Test
	public void testIndexSysAdmin() throws Exception {
		AuthenticatedUser currentUser = mock(AuthenticatedUser.class);
		when(userContext.getCurrentUser()).thenReturn(currentUser);
		when(currentUser.getUsername()).thenReturn("username");
		
		Users users= mock(Users.class);
		when(usersRepository.loadUserByUsername("username")).thenReturn(users);
		Set<GrantedAuthority> auths=new HashSet<GrantedAuthority>();
		auths.add(new SimpleGrantedAuthority("ROLE_SYSADMIN"));
		
		when(users.getAuthorities()).thenReturn(auths);
		
		mockMvc.perform(get("/index.html"))
			.andExpect(view().name("redirect:/sysadmin/valueSetList"));
	}
	@Test
	public void testIndexAdmin() throws Exception {
		AuthenticatedUser currentUser = mock(AuthenticatedUser.class);
		when(userContext.getCurrentUser()).thenReturn(currentUser);
		when(currentUser.getUsername()).thenReturn("username");
		
		Users users= mock(Users.class);
		when(usersRepository.loadUserByUsername("username")).thenReturn(users);
		Set<GrantedAuthority> auths=new HashSet<GrantedAuthority>();
		auths.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		
		when(users.getAuthorities()).thenReturn(auths);
		
		mockMvc.perform(get("/index.html"))
			.andExpect(view().name("redirect:/Administrator/adminHome.html"));
	}

	@Test
	public void testError() throws Exception {
		mockMvc.perform(get("/error.html"))
			.andExpect(view().name("WEB-INF/views/error.html"));
	}

}
