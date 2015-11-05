package gov.samhsa.pcm.web;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import gov.samhsa.pcm.domain.account.TokenGenerator;
import gov.samhsa.pcm.domain.commondomainservices.SignatureService;
import gov.samhsa.pcm.infrastructure.EchoSignSignatureService;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.Test;
import org.modelmapper.ModelMapper;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

public class ApplicationContextIntegrationTest {

	@Test
	public void bootstrapAppFromXml() {
		// Here cannot use WEB-INF/spring/root-context.xml
		GenericXmlApplicationContext context = new GenericXmlApplicationContext();
		context.getEnvironment().setActiveProfiles("test");
		context.load("classpath:META-INF/spring/applicationContext*.xml");
		context.refresh();

		assertThat(context, is(notNullValue()));

		// Test StandardPasswordEncoder bean is loaded
		StandardPasswordEncoder passwordEncoder = context
				.getBean(StandardPasswordEncoder.class);
		assertNotNull(passwordEncoder);

		// ModelMapper
		ModelMapper modelMapper = context.getBean(ModelMapper.class);
		assertNotNull(modelMapper);

		// DataSource
		BasicDataSource dataSource = (BasicDataSource) (context
				.getBean("dataSource"));
		assertNotNull(dataSource);
		// assertEquals("jdbc:mysql://localhost:3306/test",
		// dataSource.getUrl());

		// TokenGenerator
		TokenGenerator tokenGenerator = (context.getBean(TokenGenerator.class));
		assertNotNull(tokenGenerator);

		// SignatureService
		SignatureService signatureService = (context
				.getBean(SignatureService.class));
		assertNotNull(signatureService);

		// EchoSignSignatureService
		EchoSignSignatureService echoSignSignatureService = (context
				.getBean(EchoSignSignatureService.class));
		assertNotNull(echoSignSignatureService);
	}
}
