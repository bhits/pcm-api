package gov.samhsa.pcm.web.controller;

import gov.samhsa.pcm.web.AjaxException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public class AbstractNodeController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(AbstractNodeController.class);

	private static final String FLASH_ERROR_MESSAGE = "errorMessage";
	private static final String FLASH_FEEDBACK_MESSAGE = "feedbackMessage";

	private static final String VIEW_REDIRECT_PREFIX = "redirect:";

	/**
	 * Adds a new error message
	 * 
	 * @param model
	 *            A model which stores the the error message.
	 * @param code
	 *            A message code which is used to fetch the correct message from
	 *            the message source.
	 * @param params
	 *            The parameters attached to the actual error message.
	 */
	protected void addErrorMessage(RedirectAttributes model, String code,
			Object... params) {
		LOGGER.debug("adding error message with code: " + code
				+ " and params: " + params);
		model.addFlashAttribute(FLASH_ERROR_MESSAGE, code);
	}

	/**
	 * Adds a new feedback message.
	 * 
	 * @param model
	 *            A model which stores the feedback message.
	 * @param code
	 *            A message code which is used to fetch the actual message from
	 *            the message source.
	 * @param params
	 *            The parameters which are attached to the actual feedback
	 *            message.
	 */
	protected void addFeedbackMessage(RedirectAttributes model, String code,
			Object... params) {
		LOGGER.debug("Adding feedback message with code: " + code
				+ " and params: " + params);
		model.addFlashAttribute(FLASH_FEEDBACK_MESSAGE, code);
	}

	/**
	 * Creates a redirect view path for a specific controller action
	 * 
	 * @param path
	 *            The path processed by the controller method.
	 * @return A redirect view path to the given controller method.
	 */
	protected String createRedirectViewPath(String path) {
		StringBuilder builder = new StringBuilder();
		builder.append(VIEW_REDIRECT_PREFIX);
		builder.append(path);
		return builder.toString();
	}

	/**
	 * Automatically handles thrown AjaxExceptions
	 * 
	 * @param e
	 *            The AjaxException caught
	 * @return
	 */
	@ExceptionHandler(AjaxException.class)
	protected @ResponseBody ResponseEntity<String> handleAjaxException(
			AjaxException e) {

		return new ResponseEntity<String>(e.getErrorMessage(),
				e.getHttpStatus());
	}

	/**
	 * Automatically handles uncaught exceptions
	 * 
	 * Uncaught exceptions thrown by a method called by AJAX will return a
	 * ResponseEntity<String> with a Http Status code of 500. Other exceptions
	 * will be logged, and then re-thrown.
	 * 
	 * @param request
	 * @param response
	 * @param ex
	 *            The exception caught
	 * @return
	 * @throws Exception
	 */
	@ExceptionHandler(Exception.class)
	public @ResponseBody ResponseEntity<String> handleAllException(
			HttpServletRequest request, HttpServletResponse response,
			Exception ex) throws Exception {
		if (isAjax(request)) {
			LOGGER.error("Error occured in method called by AJAX:", ex);
			return new ResponseEntity<String>("An unknown error has occured.",
					HttpStatus.INTERNAL_SERVER_ERROR);
		} else {
			LOGGER.error("Error occured:", ex);
			throw ex;
		}
	}

	/**
	 * Checks if request was sent as an AJAX call
	 * 
	 * @param request
	 *            The request to check
	 * @return <code>true</code> if request sent as AJAX call;
	 *         <code>false</code> otherwise
	 */
	private boolean isAjax(HttpServletRequest request) {
		return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
	}

}
