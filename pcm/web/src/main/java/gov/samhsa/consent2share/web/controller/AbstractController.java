/*******************************************************************************
 * Open Behavioral Health Information Technology Architecture (OBHITA.org)
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the <organization> nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 ******************************************************************************/
package gov.samhsa.consent2share.web.controller;

import gov.samhsa.consent2share.web.AjaxException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * The Class AbstractController.
 */
public abstract class AbstractController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * Inits the binder.
	 *
	 * @param binder
	 *            the binder
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(String.class,
				new StringTrimmerEditor(false));
	}

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
	 * @return
	 * @throws Exception
	 */
	@ExceptionHandler(Exception.class)
	public @ResponseBody ResponseEntity<String> handleAllException(
			HttpServletRequest request, HttpServletResponse response,
			Exception ex) throws Exception {
		if (isAjax(request)) {
			logger.error("Error occured in method called by AJAX:", ex);
			return new ResponseEntity<String>("An unknown error has occured.",
					HttpStatus.INTERNAL_SERVER_ERROR);
		} else {
			logger.error("Error occured:", ex);
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
