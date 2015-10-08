package gov.samhsa.consent2share.web.config.di.root;

import gov.samhsa.consent2share.domain.account.UsersRepository;
import gov.samhsa.consent2share.service.account.AccountUserDetailsService;
import gov.samhsa.consent2share.web.AjaxTimeoutRedirectFilter;
import gov.samhsa.consent2share.web.CustomAccessDeniedHandler;

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
		http.csrf()
		.and()
			.headers()
			.cacheControl()
			.frameOptions()
			.xssProtection()
			.httpStrictTransportSecurity()
		.and()
			.authorizeRequests()
			.antMatchers("/sysadmin/lookupService/**").permitAll()
			.antMatchers("/Administrator/**").permitAll()
			.antMatchers("/sysadmin/**").hasRole("SYSADMIN")
				.antMatchers("/instrumentation/**").permitAll()
			.antMatchers("/patients/**", "/clinicaldocuments/**","/consents/**").permitAll()
			.antMatchers("/**", "/sysadmin/lookupService/**","/index.html", "/registration.html").permitAll()
			.anyRequest().authenticated()
		.and()
			.formLogin()
			.loginPage("/")
			.usernameParameter("j_username")
			.passwordParameter("j_password")
			.loginProcessingUrl("/resources/j_spring_security_check")
			.permitAll()
			.defaultSuccessUrl("/defaultLoginPage.html")
			.failureHandler(loginMappingFailureHandler())
		.and()
			.logout()
			.logoutUrl("/resources/j_spring_security_logout")
			.logoutSuccessUrl("/")
			.invalidateHttpSession(true)
			.deleteCookies("JSESSIONID")
		.and()
			.exceptionHandling().accessDeniedPage("/views/dataAccessFailure.html")
		.and()
			.sessionManagement()
			.sessionFixation()
			.migrateSession()
			.maximumSessions(1)
			.expiredUrl("/index.html?expired=true")
			.maxSessionsPreventsLogin(false);

		http.addFilterAfter(ajaxTimeoutRedirectFilter(),
				ExceptionTranslationFilter.class);
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
