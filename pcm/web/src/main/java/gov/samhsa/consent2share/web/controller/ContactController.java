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

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import gov.samhsa.consent2share.service.contact.ContactService;
import gov.samhsa.consent2share.service.dto.ContactDto;
import gov.samhsa.consent2share.service.validator.FieldValidatorCreateNewAccountOnPatient;
import gov.samhsa.consent2share.service.validator.FieldValidatorRequestMoreInformation;

import org.springframework.ui.Model;

/**
 * The Class ContactController.
 */
@Controller
public class ContactController extends AbstractController {

	/** The contact service. */	
	//@Autowired
	private ContactService contactService;
	
	/** The field validator for Request More Information. */	
	//@Autowired
	private FieldValidatorRequestMoreInformation fieldValidatorRequestMoreInformation;
	
	@Autowired
	public ContactController(ContactService contactService, FieldValidatorRequestMoreInformation fieldValidatorRequestMoreInformation) {
		this.contactService = contactService;
		this.fieldValidatorRequestMoreInformation = fieldValidatorRequestMoreInformation;
	}
	/**
	 * Contact page.
	 *
	 * @param model
	 *            the model
	 * @return the views/contact
	 * 			   the contact page	
	 */
	@RequestMapping(value = "contact.html", method = RequestMethod.GET)
	public String contactpage(Model model) {
		ContactDto contactDto = new ContactDto("1");
		model.addAttribute("contactDto", contactDto);
		return "views/contact";
	}
	
	/**
	 * Sends contact information in email.
	 *
	 * @param model
	 *            the model
	 *  @param contactDto
	 *            the holds all the information for the email.
	 *                       
	 * @return the views/contact
	 * 			   the contact page	
	 */
	@RequestMapping(value = "sendContactEmail.html", method = RequestMethod.POST)
	public String sendEmail(@Valid ContactDto contactDto,BindingResult result, Model model){		
		
		fieldValidatorRequestMoreInformation.validate(contactDto, result);

		if (result.hasErrors()) {
			return "views/contact";
		}
		
		contactService.sendEmail(contactDto);			
		return "redirect:/contactSuccess.html";
	}
	
	/**
	 * Route to the success page after sending email.
	 *
	 * @param model
	 *            The model
	 * @return the views/contactSuccess
	 * 			   The page which confirms that the email has been sent.	
	 */
	@RequestMapping(value = "contactSuccess.html")
	public String contactSuccessPage(Model model) {		
		return "views/contactSuccess";
	}

}
