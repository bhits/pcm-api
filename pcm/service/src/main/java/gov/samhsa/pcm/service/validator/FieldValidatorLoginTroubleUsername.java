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
package gov.samhsa.pcm.service.validator;

import gov.samhsa.pcm.service.dto.LoginTroubleDto;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * The Class FieldValidatorLoginTroubleUsername.
 */
public class FieldValidatorLoginTroubleUsername implements Validator {

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
		String targetDtoName = target
				.getClass()
				.getName()
				.substring(target.getClass().getName().lastIndexOf('.') + 1,
						target.getClass().getName().length());
		targetDtoName = Character.toLowerCase(targetDtoName.charAt(0))
				+ targetDtoName.substring(1);

		try {

			if (target instanceof LoginTroubleDto) {

				// First name
				String firstname = (String) PropertyUtils.getProperty(target,
						"firstname");
				if (!StringUtils.hasText(firstname)) {
					errors.rejectValue("firstname", "NotEmpty." + targetDtoName
							+ ".firstname");
				}

				if (StringUtils.hasText(firstname)
						&& (firstname.length() < 2 || firstname.length() > 30)) {
					errors.rejectValue("firstname", "Size." + targetDtoName
							+ ".firstname");
				}

				// Last name
				String lastname = (String) PropertyUtils.getProperty(target,
						"lastname");
				if (!StringUtils.hasText(lastname)) {
					errors.rejectValue("lastname", "NotEmpty." + targetDtoName
							+ ".lastname");
				}

				if (StringUtils.hasText(lastname)
						&& (lastname.length() < 2 || lastname.length() > 30)) {
					errors.rejectValue("lastname", "Size." + targetDtoName
							+ ".lastname");
				}

				// Email
				String email = (String) PropertyUtils.getProperty(target,
						"email");
				if (!StringUtils.hasText(email)) {
					errors.rejectValue("email", "NotEmpty." + targetDtoName
							+ ".email");
				}
				if (StringUtils.hasText(email)
						&& !email
								.matches("^[\\w-]+(\\.[\\w-]+)*@([a-z0-9-]+(\\.[a-z0-9-]+)*?\\.[a-z]{2,6}|(\\d{1,3}\\.){3}\\d{1,3})(:\\d{4})?$")) {
					errors.rejectValue("email", "Pattern." + targetDtoName
							+ ".email");
				}

				// Date of Birth
				String dateOfBirth = (String) PropertyUtils.getProperty(target,
						"dateofbirth");
				if (!StringUtils.hasText(dateOfBirth)) {
					errors.rejectValue("dateofbirth", "NotEmpty."
							+ targetDtoName + ".dateofbirth");
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
