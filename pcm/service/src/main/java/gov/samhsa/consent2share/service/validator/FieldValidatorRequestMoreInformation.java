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
package gov.samhsa.consent2share.service.validator;

import gov.samhsa.consent2share.service.dto.ContactDto;
import gov.samhsa.consent2share.service.dto.SignupLinkToPatientDto;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * The Class FieldValidatorRequestMoreInformation.
 */
public class FieldValidatorRequestMoreInformation implements Validator {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 * org.springframework.validation.Errors)
	 */
	@Override
	public void validate(Object target, Errors errors) {
		String emailRegexPattern = "^[\\w-]+(\\.[\\w-]+)*@([a-z0-9-]+(\\.[a-z0-9-]+)*?\\.[a-z]{2,6}|(\\d{1,3}\\.){3}\\d{1,3})(:\\d{4})?$";
		
		String targetDtoName = target.getClass().getName().substring(target.getClass().getName().lastIndexOf('.') + 1,target.getClass().getName().length());
		
		targetDtoName = "contactDto";

		try {
			if (target instanceof ContactDto) {

				// FirstName
				String firstName = (String) PropertyUtils.getProperty(target,"firstName");
				if (!StringUtils.hasText(firstName)) {
					errors.rejectValue("firstName", "NotEmpty." + targetDtoName + ".firstName");
				}
				
				if (StringUtils.hasText(firstName) && (firstName.length() < 2 || firstName.length() > 30)) {
					errors.rejectValue("firstName", "Size." + targetDtoName + ".firstName");
				}
				
				// LasstName
				String lastName = (String) PropertyUtils.getProperty(target,"lastName");
				if (!StringUtils.hasText(lastName)) {
					errors.rejectValue("lastName", "NotEmpty." + targetDtoName + ".lastName");
				}
				
				if (StringUtils.hasText(lastName) && (lastName.length() < 2 || lastName.length() > 30)) {
					errors.rejectValue("lastName", "Size." + targetDtoName + ".lastName");
				}
				
				// Email
				String email = (String) PropertyUtils.getProperty(target,"email");
				if (!StringUtils.hasText(email)) {
					errors.rejectValue("email", "NotEmpty." + targetDtoName + ".email");
				}
				
				if (StringUtils.hasText(email)&& !email.matches(emailRegexPattern)) {
					errors.rejectValue("email", "Pattern." + targetDtoName+ ".email");
				}
				
				// Email Confirmation
				String emailConfirmation = (String) PropertyUtils.getProperty(target,"emailConfirmation");
				if (!StringUtils.hasText(emailConfirmation)) {errors.rejectValue("emailConfirmation", "NotEmpty." + targetDtoName + ".emailConfirmation");
				}
				
				if (StringUtils.hasText(emailConfirmation)&& !email.matches(emailConfirmation)) {
					errors.rejectValue("emailConfirmation", "Pattern." + targetDtoName+ ".emailConfirmation");
				}
				
				if(StringUtils.hasText(email)&&  StringUtils.hasText(emailConfirmation)&& !email.equals(emailConfirmation)){
					errors.rejectValue("email", "emailConfirmation", targetDtoName+ ".email must be the same as " + targetDtoName + ".emailConfirmation" );
				}
				
				// Message Code
				String messageCode = (String) PropertyUtils.getProperty(target,"messageCode");
				if (!StringUtils.hasText(messageCode)) {
					errors.rejectValue("messageCode", "NotEmpty." + targetDtoName + ".messageCode");
				}
				
			}
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		} catch (InvocationTargetException e1) {
			e1.printStackTrace();
		} catch (NoSuchMethodException e1) {
			e1.printStackTrace();
		}
	}
}
