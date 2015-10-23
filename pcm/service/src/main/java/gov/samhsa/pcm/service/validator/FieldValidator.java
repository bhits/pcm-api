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

import gov.samhsa.pcm.service.dto.AdminProfileDto;
import gov.samhsa.pcm.service.dto.BasicPatientAccountDto;
import gov.samhsa.pcm.service.dto.LegalRepresentativeDto;
import gov.samhsa.pcm.service.dto.PatientProfileDto;
import gov.samhsa.pcm.service.dto.SignupDto;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * The Class FieldValidator.
 */
public class FieldValidator implements Validator {

	/** The logger. */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

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
		String targetDtoName = getTargetDtoName(target);

		try {
			if (target instanceof SignupDto
					|| target instanceof PatientProfileDto
					|| target instanceof LegalRepresentativeDto
					|| target instanceof AdminProfileDto
					|| target instanceof BasicPatientAccountDto) {

				// First name
				String firstName = (String) PropertyUtils.getProperty(target,
						"firstName");
				if (!StringUtils.hasText(firstName)) {
					errors.rejectValue("firstName", "NotEmpty." + targetDtoName
							+ ".firstName");
				}

				if (StringUtils.hasText(firstName)
						&& (firstName.length() < 2 || firstName.length() > 30)) {
					errors.rejectValue("firstName", "Size." + targetDtoName
							+ ".firstName");
				}

				// Last name
				String lastName = (String) PropertyUtils.getProperty(target,
						"lastName");
				if (!StringUtils.hasText(lastName)) {
					errors.rejectValue("lastName", "NotEmpty." + targetDtoName
							+ ".lastName");
				}
				if (StringUtils.hasText(lastName)
						&& (lastName.length() < 2 || lastName.length() > 30)) {
					errors.rejectValue("lastName", "Size." + targetDtoName
							+ ".lastName");
				}

				// Gender
				if (target instanceof PatientProfileDto) {
					String gender = (String) PropertyUtils.getProperty(target,
							"administrativeGenderCode");
					if (gender == null) {
						errors.rejectValue("administrativeGenderCode",
								"NotNull." + targetDtoName + ".gender");
					}
					if (!(gender.equals("M") || gender.equals("F"))) {
						errors.rejectValue("administrativeGenderCode",
								"Must Be M or F." + targetDtoName + ".gender");
					}
				}

				// Date of Birth
				if (target instanceof SignupDto
						|| target instanceof PatientProfileDto
						|| target instanceof LegalRepresentativeDto
						|| target instanceof BasicPatientAccountDto) {
					Date birthDate = (Date) PropertyUtils.getProperty(target,
							"birthDate");
					if (birthDate == null) {
						errors.rejectValue("birthDate", "NotNull."
								+ targetDtoName + ".birthDate");
					}
					SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
					Date minDate = null;
					try {
						minDate = sdf.parse("01/01/1900");
					} catch (ParseException e) {
						logger.error(e.getMessage(), e);
					}
					if (birthDate != null && birthDate.compareTo(minDate) < 0) {
						errors.rejectValue("birthDate", "Pattern."
								+ targetDtoName + ".birthDate");
					}
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

				if (target instanceof PatientProfileDto) {
					// medicalRecordNumber
					String medicalRecordNumber = (String) PropertyUtils
							.getProperty(target, "medicalRecordNumber");
					if (StringUtils.hasText(medicalRecordNumber)
							&& (medicalRecordNumber.length() < 0 || medicalRecordNumber
									.length() > 30)) {
						errors.rejectValue("medicalRecordNumber", "Size."
								+ targetDtoName + ".medicalRecordNumber");
					}
				}

				if (target instanceof LegalRepresentativeDto) {
					Date startDate = (Date) PropertyUtils.getProperty(target,
							"relationshipStartDate");
					Date endDate = (Date) PropertyUtils.getProperty(target,
							"relationshipEndDate");
					if (startDate != null && endDate != null
							&& startDate.after(endDate)) {
						errors.rejectValue("relationshipStartDate", "Pattern."
								+ targetDtoName + ".relationshipStartDate");
					}
				}
			}

			if (target instanceof SignupDto) {

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

				// Password
				String password = (String) PropertyUtils.getProperty(target,
						"password");
				if (!StringUtils.hasText(password)) {
					errors.rejectValue("password", "NotEmpty." + targetDtoName
							+ ".password");
				}

				if (StringUtils.hasText(password)
						&& (password.length() < 8 || password.length() > 30)) {
					errors.rejectValue("password", "Size." + targetDtoName
							+ ".password");
				}

				if (StringUtils.hasText(password)
						&& (!password.matches(".*\\d.*"))) {
					errors.rejectValue("password", "Number." + targetDtoName
							+ ".password");
				}

				if (StringUtils.hasText(password)
						&& (!password.matches(".*[a-z].*"))) {
					errors.rejectValue("password", "Lowercase." + targetDtoName
							+ ".password");
				}

				if (StringUtils.hasText(password)
						&& (!password.matches(".*[A-Z].*"))) {
					errors.rejectValue("password", "Uppercase." + targetDtoName
							+ ".password");
				}

				if (StringUtils.hasText(password)
						&& (!password
								.matches(".*[,~,!,@,#,$,%,^,&,*,(,),-,_,=,+,[,{,],},|,;,:,<,>,/,?].*$"))) {
					errors.rejectValue("password", "Specialchar."
							+ targetDtoName + ".password");
				}

				if (StringUtils.hasText(password)
						&& (password.equals(username))) {
					errors.rejectValue("password", "Username." + targetDtoName
							+ ".password");
				}

			}
		} catch (IllegalAccessException e1) {
			logger.error(e1.getMessage(), e1);
		} catch (InvocationTargetException e1) {
			logger.error(e1.getMessage(), e1);
		} catch (NoSuchMethodException e1) {
			logger.error(e1.getMessage(), e1);
		}
	}

	/**
	 * Gets the target dto name.
	 *
	 * @param target
	 *            the target
	 * @return the target dto name
	 */
	protected String getTargetDtoName(Object target) {
		String targetDtoName = target
				.getClass()
				.getName()
				.substring(target.getClass().getName().lastIndexOf('.') + 1,
						target.getClass().getName().length());
		targetDtoName = Character.toLowerCase(targetDtoName.charAt(0))
				+ targetDtoName.substring(1);
		return targetDtoName;
	}
}
