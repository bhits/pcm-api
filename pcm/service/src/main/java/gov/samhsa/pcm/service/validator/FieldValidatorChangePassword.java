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

import gov.samhsa.pcm.service.dto.PasswordChangeDto;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * The Class FieldValidatorChangePassword.
 */
public class FieldValidatorChangePassword implements Validator {

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

			if (target instanceof PasswordChangeDto) {

				// Username
				String username = (String) PropertyUtils.getProperty(target,
						"username");
				if (!StringUtils.hasText(username)) {
					errors.rejectValue("username", "NotEmpty." + targetDtoName
							+ ".username");
				}

				if (StringUtils.hasText(username)
						&& (username.length() < 2 || username.length() > 30)) {
					errors.rejectValue("username", "Size." + targetDtoName
							+ ".username");
				}

				// Old Password
				String oldPassword = (String) PropertyUtils.getProperty(target,
						"oldPassword");
				if (!StringUtils.hasText(oldPassword)) {
					errors.rejectValue("oldPassword", "NotEmpty."
							+ targetDtoName + ".oldPassword");
				}

				// New Password
				String newPassword = (String) PropertyUtils.getProperty(target,
						"newPassword");
				if (!StringUtils.hasText(newPassword)) {
					errors.rejectValue("newPassword", "NotEmpty."
							+ targetDtoName + ".newPassword");
				}

				if (StringUtils.hasText(newPassword)
						&& (newPassword.length() < 8 || newPassword.length() > 30)) {
					errors.rejectValue("newPassword", "Size." + targetDtoName
							+ ".newPassword");
				}

				if (StringUtils.hasText(newPassword)
						&& (!newPassword.matches(".*\\d.*"))) {
					errors.rejectValue("newPassword", "Number." + targetDtoName
							+ ".newPassword");
				}

				if (StringUtils.hasText(newPassword)
						&& (!newPassword.matches(".*[a-z].*"))) {
					errors.rejectValue("newPassword", "Lowercase."
							+ targetDtoName + ".newPassword");
				}

				if (StringUtils.hasText(newPassword)
						&& (!newPassword.matches(".*[A-Z].*"))) {
					errors.rejectValue("newPassword", "Uppercase."
							+ targetDtoName + ".newPassword");
				}

				if (StringUtils.hasText(newPassword)
						&& (!newPassword
								.matches(".*[,~,!,@,#,$,%,^,&,*,(,),-,_,=,+,[,{,],},|,;,:,<,>,/,?].*$"))) {
					errors.rejectValue("newPassword", "Specialchar."
							+ targetDtoName + ".newPassword");
				}

				// Repeat New Password
				String repeatNewPassword = (String) PropertyUtils.getProperty(
						target, "repeatNewPassword");
				if (!StringUtils.hasText(repeatNewPassword)) {
					errors.rejectValue("repeatNewPassword", "NotEmpty."
							+ targetDtoName + ".repeatNewPassword");
				}

				if (!errors.hasErrors()
						&& StringUtils.hasText(repeatNewPassword)
						&& StringUtils.hasText(newPassword)) {
					if (!repeatNewPassword.equals(newPassword)) {
						errors.rejectValue("newPassword", "Match."
								+ targetDtoName + ".newPassword");
					}
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
