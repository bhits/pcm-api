package gov.samhsa.consent2share.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.csrf.InvalidCsrfTokenException;

/**
 * The Class CustomAccessDeniedHandler.
 */
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(CustomAccessDeniedHandler.class);

	/** The access denied url. */
	private String accessDeniedUrl;

	/**
	 * Instantiates a new custom access denied handler.
	 */
	public CustomAccessDeniedHandler() {
	}

	/**
	 * Instantiates a new custom access denied handler.
	 *
	 * @param accessDeniedUrl
	 *            the access denied url
	 */
	public CustomAccessDeniedHandler(String accessDeniedUrl) {
		this.accessDeniedUrl = accessDeniedUrl;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.security.web.access.AccessDeniedHandler#handle(javax
	 * .servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse,
	 * org.springframework.security.access.AccessDeniedException)
	 */
	@Override
	public void handle(HttpServletRequest request,
			HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException,
			ServletException {

		LOGGER.info("Access denied url: " + accessDeniedUrl);

		if (accessDeniedException instanceof InvalidCsrfTokenException) {
			request.getSession().setAttribute("tokenErrorMessage",
					"Page has expired! Please try login again.");
			response.sendRedirect("../index.html");
		} else {

			request.getSession().setAttribute("message",
					"You do not have permission to access this page!");
			response.sendRedirect("../" + accessDeniedUrl);
		}
	}

	/**
	 * Gets the access denied url.
	 *
	 * @return the access denied url
	 */
	public String getAccessDeniedUrl() {
		return accessDeniedUrl;
	}

	/**
	 * Sets the access denied url.
	 *
	 * @param accessDeniedUrl
	 *            the new access denied url
	 */
	public void setAccessDeniedUrl(String accessDeniedUrl) {
		this.accessDeniedUrl = accessDeniedUrl;
	}
}