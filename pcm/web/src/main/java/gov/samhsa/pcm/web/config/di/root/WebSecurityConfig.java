package gov.samhsa.pcm.web.config.di.root;

import gov.samhsa.pcm.domain.account.UsersRepository;
import gov.samhsa.pcm.service.account.AccountUserDetailsService;
import gov.samhsa.pcm.web.AjaxTimeoutRedirectFilter;
import gov.samhsa.pcm.web.CorsFilter;

import java.util.HashMap;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.authentication.ExceptionMappingAuthenticationFailureHandler;
import org.springframework.security.web.header.HeaderWriterFilter;

@Configuration
@EnableWebSecurity
@EnableWebMvcSecurity
@EnableGlobalAuthentication
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Value("${maximum_failed_attempts}")
	private String maximumFailedAttemptsValue;

	@Value("${auto_unlock_interval}")
	private String autoUnlockIntervalValue;

	@Autowired
	UsersRepository usersRepository;

	@Autowired
	DataAccessConfig dataAccessConfig;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
			.headers()
			.cacheControl()
			.frameOptions()
			.xssProtection()
			.httpStrictTransportSecurity()
		.and()
			.authorizeRequests()
			.antMatchers("/**").permitAll()
			.anyRequest().authenticated()
		.and()
			.sessionManagement()
			.sessionFixation()
			.migrateSession();
			/*.maximumSessions(1)
			.expiredUrl("/index.html?expired=true")
			.maxSessionsPreventsLogin(false);*/
	
		http.addFilterBefore(corsFilter(), HeaderWriterFilter.class);
	}

	// Configure Authentication mechanism
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder)
			throws Exception {
		authenticationManagerBuilder.userDetailsService(accountUserDetailsService())
									.passwordEncoder(passwordEncoder());
	}


	@Bean
	public AccountUserDetailsService accountUserDetailsService() {
		short maxFailedAttempts = Short.parseShort(maximumFailedAttemptsValue);
		long autoUnlockInterval = Long.parseLong(autoUnlockIntervalValue);
		AccountUserDetailsService accountUserDetailsService = new AccountUserDetailsService(
				maxFailedAttempts, autoUnlockInterval, usersRepository);
		return accountUserDetailsService;

	}

	// Create a custom filter, Custom HTTP error code as 440.
	// This filter is configured in configure(HttpSecurity http) method
	@Bean
	public AjaxTimeoutRedirectFilter ajaxTimeoutRedirectFilter() {
		AjaxTimeoutRedirectFilter ajaxTimeoutRedirectFilter = new AjaxTimeoutRedirectFilter();
		ajaxTimeoutRedirectFilter.setCustomSessionExpiredErrorCode(440);
		return ajaxTimeoutRedirectFilter;
	}
	
	@Bean
	public CorsFilter corsFilter() {
		return new CorsFilter();
	}

	@Bean
	public UserDetailsManager jdbcUserDetailsManager(DataSource dataSource) {
		JdbcUserDetailsManager u = new JdbcUserDetailsManager();
		u.setDataSource(dataAccessConfig.dataSource());
		return u;
	}

	@Bean
	public ExceptionMappingAuthenticationFailureHandler loginMappingFailureHandler() {
		ExceptionMappingAuthenticationFailureHandler exceptionMapping = new ExceptionMappingAuthenticationFailureHandler();
		HashMap<String, String> failureUrlMap = new HashMap<String, String>();
		failureUrlMap
		.put("org.springframework.security.authentication.BadCredentialsException",
				"/index.html?login_error=bad_credentials");
		failureUrlMap.put(
				"org.springframework.security.authentication.LockedException",
				"/index.html?login_error=account_locked");
		failureUrlMap
		.put("org.springframework.security.authentication.DisabledException",
				"/index.html?login_error=account_disabled");
		failureUrlMap
		.put("org.springframework.security.authentication.AccountExpiredException",
				"/index.html?login_error=auth_service_error");
		failureUrlMap
		.put("org.springframework.security.authentication.CredentialsExpiredException",
				"/index.html?login_error=auth_service_error");
		failureUrlMap
		.put("org.springframework.security.authentication.AuthenticationServiceException",
				"/index.html?login_error=auth_service_error");
		exceptionMapping.setExceptionMappings(failureUrlMap);
		return exceptionMapping;

	}

	// Uses SHA-256 with multiple iterations and a random salt value.
	@Bean
	public StandardPasswordEncoder passwordEncoder() {
		return new StandardPasswordEncoder();
	}

}
