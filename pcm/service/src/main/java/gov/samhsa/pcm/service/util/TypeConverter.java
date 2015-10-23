/*******************************************************************************
 * Open Behavioral Health Information Technology Architecture (OBHITA.org)
 *   
 *   Redistribution and use in source and binary forms, with or without
 *   modification, are permitted provided that the following conditions are met:
 *       * Redistributions of source code must retain the above copyright
 *         notice, this list of conditions and the following disclaimer.
 *       * Redistributions in binary form must reproduce the above copyright
 *         notice, this list of conditions and the following disclaimer in the
 *         documentation and/or other materials provided with the distribution.
 *       * Neither the name of the <organization> nor the
 *         names of its contributors may be used to endorse or promote products
 *         derived from this software without specific prior written permission.
 *   
 *   THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 *   ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 *   WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *   DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 *   DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 *   (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 *   LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 *   ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *   (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *   SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 ******************************************************************************/
package gov.samhsa.pcm.service.util;

import gov.samhsa.pcm.hl7.dto.PixPatientDto;
import gov.samhsa.pcm.service.dto.BasicPatientAccountDto;
import gov.samhsa.pcm.service.dto.PatientProfileDto;
import gov.samhsa.pcm.service.dto.SignupDto;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.apache.commons.lang.math.NumberUtils;
import org.springframework.util.StringUtils;

/**
 * The Class TypeConverter.
 */
public class TypeConverter {

	/**
	 * Signup dto to pix patient dto.
	 *
	 * @param signupDto
	 *            the signup dto
	 * @param mrn
	 *            the mrn
	 * @return the pix patient dto
	 */
	public static PixPatientDto signupDtoToPixPatientDto(SignupDto signupDto,
			String mrn) {
		PixPatientDto pixPatientDto = new PixPatientDto();
		set(pixPatientDto::setBirthTimeValue,
				() -> dateToDateValue(signupDto.getBirthDate()));
		set(pixPatientDto::setPatientFirstName, signupDto::getFirstName);
		set(pixPatientDto::setAdministrativeGenderCode,
				signupDto::getGenderCode);
		set(pixPatientDto::setPatientLastName, signupDto::getLastName);
		set(pixPatientDto::setSsn,
				() -> formatSsn(signupDto.getSocialSecurityNumber()));
		set(pixPatientDto::setIdExtension, () -> mrn);
		return pixPatientDto;
	}

	/**
	 * Basic patient account dto to pix patient dto.
	 *
	 * @param basicPatientAccountDto
	 *            the basic patient account dto
	 * @param mrn
	 *            the mrn
	 * @return the pix patient dto
	 */
	public static PixPatientDto basicPatientAccountDtoToPixPatientDto(
			BasicPatientAccountDto basicPatientAccountDto, String mrn) {
		PixPatientDto pixPatientDto = new PixPatientDto();
		set(pixPatientDto::setAdministrativeGenderCode,
				basicPatientAccountDto::getAdministrativeGenderCode);
		set(pixPatientDto::setBirthTimeValue,
				() -> dateToDateValue(basicPatientAccountDto.getBirthDate()));
		set(pixPatientDto::setPatientEmailHome,
				basicPatientAccountDto::getEmail);
		set(pixPatientDto::setPatientFirstName,
				basicPatientAccountDto::getFirstName);
		set(pixPatientDto::setPatientLastName,
				basicPatientAccountDto::getLastName);
		set(pixPatientDto::setSsn,
				() -> formatSsn(basicPatientAccountDto
						.getSocialSecurityNumber()));
		set(pixPatientDto::setIdExtension, () -> mrn);
		return pixPatientDto;
	}

	/**
	 * Patient profile dto to pix patient dto.
	 *
	 * @param patientProfileDto
	 *            the patient profile dto
	 * @param mrn
	 *            the mrn
	 * @return the pix patient dto
	 */
	public static PixPatientDto patientProfileDtoToPixPatientDto(
			PatientProfileDto patientProfileDto, String mrn) {
		PixPatientDto pixPatientDto = new PixPatientDto();
		set(pixPatientDto::setAddrCity, patientProfileDto::getAddressCity);
		set(pixPatientDto::setAddrPostalCode,
				patientProfileDto::getAddressPostalCode);
		set(pixPatientDto::setAddrState, patientProfileDto::getAddressStateCode);
		set(pixPatientDto::setAddrStreetAddressLine,
				patientProfileDto::getAddressStreetAddressLine);
		set(pixPatientDto::setAdministrativeGenderCode,
				patientProfileDto::getAdministrativeGenderCode);
		set(pixPatientDto::setBirthTimeValue,
				() -> dateToDateValue(patientProfileDto.getBirthDate()));
		set(pixPatientDto::setIdExtension, () -> mrn);
		set(pixPatientDto::setMaritalStatusCode, () -> patientProfileDto
				.getMaritalStatusCode().getCode());
		set(pixPatientDto::setPatientEmailHome, patientProfileDto::getEmail);
		set(pixPatientDto::setPatientFirstName, patientProfileDto::getFirstName);
		set(pixPatientDto::setPatientLastName, patientProfileDto::getLastName);
		set(pixPatientDto::setSsn,
				() -> formatSsn(patientProfileDto.getSocialSecurityNumber()));
		set(pixPatientDto::setTelecomValue,
				patientProfileDto::getTelephoneTelephone);
		return pixPatientDto;
	}

	/**
	 * Date to date value.
	 *
	 * @param date
	 *            the date
	 * @return the string
	 */
	public static String dateToDateValue(Date date) {
		return new SimpleDateFormat("yyyyMMdd").format(date);
	}

	/**
	 * Format ssn.
	 *
	 * @param ssn
	 *            the ssn
	 * @return the string
	 */
	public static String formatSsn(String ssn) {
		if (StringUtils.hasText(ssn) && !ssn.contains("-") && ssn.length() == 9
				&& NumberUtils.isNumber(ssn)) {
			StringBuilder builder = new StringBuilder();
			builder.append(ssn.substring(0, 3));
			builder.append("-");
			builder.append(ssn.substring(3, 5));
			builder.append("-");
			builder.append(ssn.substring(5));
			ssn = builder.toString();
		}
		return ssn;
	}

	/**
	 * Sets the.
	 *
	 * @param consumer
	 *            the consumer
	 * @param supplier
	 *            the supplier
	 */
	private static void set(Consumer<String> consumer, Supplier<String> supplier) {
		consumer.accept(StringUtils.hasText(supplier.get()) ? supplier.get()
				: " ");
	}
}
