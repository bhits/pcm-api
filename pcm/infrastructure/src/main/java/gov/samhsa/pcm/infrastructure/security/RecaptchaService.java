package gov.samhsa.pcm.infrastructure.security;

import net.tanesha.recaptcha.ReCaptchaFactory;
import net.tanesha.recaptcha.ReCaptchaImpl;
import net.tanesha.recaptcha.ReCaptchaResponse;

import org.springframework.beans.factory.InitializingBean;

/**
 * The Class RecaptchaService.
 */
public class RecaptchaService implements InitializingBean {

	/** The https server. */
	private String httpsServer;

	/** The http server. */
	private String httpServer;

	/** The verify url. */
	private String verifyURL;

	/** The private key. */
	private String privateKey;

	/** The public key. */
	private String publicKey;

	/** The re captcha impl. */
	private ReCaptchaImpl reCaptchaImpl;

	/**
	 * Instantiates a new recaptcha service.
	 *
	 * @param httpsServer
	 *            the https server
	 * @param httpServer
	 *            the http server
	 * @param verifyURL
	 *            the verify url
	 * @param privateKey
	 *            the private key
	 * @param publicKey
	 *            the public key
	 */
	public RecaptchaService(String httpsServer, String httpServer,
			String verifyURL, String privateKey, String publicKey) {
		super();
		this.httpsServer = httpsServer;
		this.httpServer = httpServer;
		this.verifyURL = verifyURL;
		this.privateKey = privateKey;
		this.publicKey = publicKey;
	}

	/**
	 * Instantiates a new recaptcha service.
	 */
	@SuppressWarnings("unused")
	private RecaptchaService() {
	}

	/**
	 * Creates the secure recaptcha html.
	 *
	 * @return the string
	 */
	public String createSecureRecaptchaHtml() {
		return reCaptchaImpl.createRecaptchaHtml(null, null);
	}

	/**
	 * Check answer.
	 *
	 * @param remoteAddr
	 *            the remote addr
	 * @param challenge
	 *            the challenge
	 * @param uresponse
	 *            the uresponse
	 * @return true, if successful
	 */
	public boolean checkAnswer(String remoteAddr, String challenge,
			String uresponse) {
		ReCaptchaResponse reCaptchaResponse = reCaptchaImpl.checkAnswer(
				remoteAddr, challenge, uresponse);
		return reCaptchaResponse.isValid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		reCaptchaImpl = (ReCaptchaImpl) ReCaptchaFactory.newSecureReCaptcha(
				publicKey, privateKey, false);
		reCaptchaImpl.setRecaptchaServer(httpsServer);
	}
}
