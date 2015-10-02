package gov.samhsa.consent2share.infrastructure.security;

import gov.samhsa.consent2share.infrastructure.security.TokenGeneratorUuidImpl;

import org.junit.Assert;
import org.junit.Test;

public class TokenGeneratorUuidImplTest {

	@Test
	public void testGenerateToken() {
		TokenGeneratorUuidImpl sut = new TokenGeneratorUuidImpl();
		String token = sut.generateToken();
		
		Assert.assertTrue(token.matches("\\d+"));
	}
}
