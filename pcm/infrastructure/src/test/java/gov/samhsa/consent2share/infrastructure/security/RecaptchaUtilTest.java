package gov.samhsa.consent2share.infrastructure.security;

import static org.junit.Assert.*;
import net.tanesha.recaptcha.ReCaptchaImpl;
import net.tanesha.recaptcha.ReCaptchaResponse;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.Mockito.*;

/**
 * The Class RecaptchaUtilTest.
 */
public class RecaptchaUtilTest {
	
	/** The https server. */
	private final String HTTPS_SERVER="httpsServer";
	
	/** The http server. */
	private final String HTTP_SERVER="httpServer";
	
	/** The vefification url. */
	private final String VEFIFICATION_URL="verifyURL";
	
	/** The private key. */
	private final String PRIVATE_KEY="privateKey";
	
	/** The public key. */
	private final String PUBLIC_KEY="publicKey";
	
	/** The remote addr. */
	private final String REMOTE_ADDR="remoteAddr";
	
	/** The right captcha. */
	private final String RIGHT_CAPTCHA="Right Captcha";
	
	/** The wrong captcha. */
	private final String WRONG_CAPTCHA="Wrong CAPTCHA";
	
	/** The re captcha impl. */
	private ReCaptchaImpl reCaptchaImpl;
	
	/** The recaptcha util. */
	private RecaptchaService recaptchaUtil;
	
	/** The sut. */
	private RecaptchaService sut;
	
	/**
	 * Sets the up.
	 */
	@Before
	public void setUp() {
		reCaptchaImpl=mock(ReCaptchaImpl.class);
		recaptchaUtil=new RecaptchaService(HTTPS_SERVER, HTTP_SERVER, VEFIFICATION_URL, PRIVATE_KEY, PUBLIC_KEY);
		ReflectionTestUtils.setField(recaptchaUtil, "reCaptchaImpl", reCaptchaImpl);
		sut=spy(recaptchaUtil);
		ReCaptchaResponse trueReCaptchaResponse=mock(ReCaptchaResponse.class);
		ReCaptchaResponse falseReCaptchaResponse=mock(ReCaptchaResponse.class);
		doReturn(true).when(trueReCaptchaResponse).isValid();
		doReturn(false).when(falseReCaptchaResponse).isValid();
		doReturn(trueReCaptchaResponse).when(reCaptchaImpl).checkAnswer(REMOTE_ADDR, RIGHT_CAPTCHA, RIGHT_CAPTCHA);
		doReturn(falseReCaptchaResponse).when(reCaptchaImpl).checkAnswer(REMOTE_ADDR, RIGHT_CAPTCHA, WRONG_CAPTCHA);
	}
	
	/**
	 * Test check answer when succed.
	 */
	@Test
	public void testCheckAnswerWhenSucced() {
		assertTrue(sut.checkAnswer(REMOTE_ADDR, RIGHT_CAPTCHA, RIGHT_CAPTCHA));
	}
	
	/**
	 * Test check answer when fail.
	 */
	@Test
	public void testCheckAnswerWhenFail() {
		assertFalse(sut.checkAnswer(REMOTE_ADDR, RIGHT_CAPTCHA, WRONG_CAPTCHA));
	}
	
	/**
	 * Test create secure recaptcha html.
	 */
	@Test
	public void testCreateSecureRecaptchaHtml() {
		sut.createSecureRecaptchaHtml();
		verify(reCaptchaImpl).createRecaptchaHtml(null, null);
	}

}
