package gov.samhsa.pcm.infrastructure.security;

import gov.samhsa.pcm.infrastructure.security.TokenGeneratorUuidImpl;

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
