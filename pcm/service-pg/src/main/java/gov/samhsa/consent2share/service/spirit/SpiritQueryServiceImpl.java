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
package gov.samhsa.consent2share.service.spirit;

import gov.samhsa.consent2share.hl7.dto.PixPatientDto;
import gov.samhsa.consent2share.service.account.pg.PatientExistingException;
import gov.samhsa.consent2share.service.dto.BasicPatientAccountDto;
import gov.samhsa.consent2share.service.dto.PatientProfileDto;
import gov.samhsa.spirit.wsclient.adapter.SpiritAdapter;
import gov.samhsa.spirit.wsclient.dto.PatientDto;
import gov.samhsa.spirit.wsclient.exception.SpiritAdapterException;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class SpiritQueryServiceImpl.
 */
public class SpiritQueryServiceImpl implements SpiritQueryService {

	/** The logger. */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/** The spirit adapter. */
	private SpiritAdapter spiritAdapter;

	/**
	 * Instantiates a new spirit query service impl.
	 *
	 * @param spiritAdapter
	 *            the spirit adapter
	 */
	public SpiritQueryServiceImpl(SpiritAdapter spiritAdapter) {
		super();
		this.spiritAdapter = spiritAdapter;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.consent2share.service.spirit.SpiritQueryService#addPatient
	 * (gov.samhsa.consent2share.hl7.dto.PixPatientDto)
	 */
	@Override
	public PatientDto addPatient(PixPatientDto pixPatientDto)
			throws SpiritClientNotAvailableException, PatientExistingException {
		try {
			return (spiritAdapter
					.createPatientByPDQ(transferPixPatientDtoToPatientDto(pixPatientDto)));
		} catch (SpiritAdapterException e) {
			logger.error("HIE Query Failure! " + e.getMessage());
			throw new SpiritClientNotAvailableException(
					"HIE service not available.");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.consent2share.service.spirit.SpiritQueryService#
	 * updateHIEPatientProfilebyLocalPID
	 * (gov.samhsa.consent2share.service.dto.PatientProfileDto)
	 */
	@Override
	public PatientProfileDto updatePatient(PatientProfileDto patientProfileDto)
			throws SpiritClientNotAvailableException {
		try {
			PatientDto patientDto = spiritAdapter
					.updatePatientByLocId(transferPatientProfileDtoToPatientDto(patientProfileDto));
			// setting latest ids
			patientProfileDto
					.setEnterpriseIdentifier(patientDto.getPatientId());
			// patientProfileDto.setMedicalRecordNumber(patientDto.getLocalPatientId());
		} catch (SpiritAdapterException e) {
			logger.error("HIE Query Failure! " + e.getMessage());
			throw new SpiritClientNotAvailableException(
					"HIE service not available.");
		}
		return patientProfileDto;
	}

	/**
	 * Transfer pix patient dto to patient dto.
	 *
	 * @param pixPatientDto
	 *            the pix patient dto
	 * @return the patient dto
	 */
	protected PatientDto transferPixPatientDtoToPatientDto(
			PixPatientDto pixPatientDto) {
		PatientDto patientDto = new PatientDto();
		patientDto.setBirthDate(pixPatientDto.getBirthTimeValue());
		patientDto.setCountry("USA");
		patientDto.setFirstName(pixPatientDto.getPatientFirstName());
		patientDto.setLastName(pixPatientDto.getPatientLastName());
		patientDto.setGenderCode(pixPatientDto.getAdministrativeGenderCode());
		patientDto.setEmailHome(pixPatientDto.getPatientEmailHome());
		patientDto.setSsnNumber(pixPatientDto.getSsn());
		patientDto.setLocalPatientId(pixPatientDto.getIdExtension());
		return patientDto;
	}

	/**
	 * Transfer basic patient account dto to patient dto.
	 *
	 * @param basicPatientAccountDto
	 *            the basic patient account dto
	 * @return the patient dto
	 */
	protected PatientDto transferBasicPatientAccountDtoToPatientDto(
			BasicPatientAccountDto basicPatientAccountDto) {

		PatientDto patientDto = new PatientDto();
		patientDto.setBirthDate(trasferDateType(basicPatientAccountDto
				.getBirthDate()));
		patientDto.setCountry("USA");
		patientDto.setFirstName(basicPatientAccountDto.getFirstName());
		patientDto.setLastName(basicPatientAccountDto.getLastName());
		patientDto.setGenderCode(basicPatientAccountDto
				.getAdministrativeGenderCode());
		patientDto.setEmailHome(basicPatientAccountDto.getEmail());
		patientDto.setSsnNumber(basicPatientAccountDto
				.getSocialSecurityNumber());

		return patientDto;
	}

	/**
	 * Transfer patient profile dto to patient dto.
	 *
	 * @param patientProfileDto
	 *            the patient profile dto
	 * @return the patient dto
	 */
	protected PatientDto transferPatientProfileDtoToPatientDto(
			PatientProfileDto patientProfileDto) {

		PatientDto patientDto = new PatientDto();
		patientDto.setPatientId(patientProfileDto.getEnterpriseIdentifier());
		patientDto
				.setLocalPatientId(patientProfileDto.getMedicalRecordNumber());
		patientDto.setBirthDate(trasferDateType(patientProfileDto
				.getBirthDate()));
		patientDto.setCountry("USA");
		patientDto.setFirstName(patientProfileDto.getFirstName());
		patientDto.setLastName(patientProfileDto.getLastName());
		patientDto.setGenderCode(patientProfileDto
				.getAdministrativeGenderCode());
		patientDto.setEmailHome(patientProfileDto.getEmail());
		patientDto.setSsnNumber(patientProfileDto.getSocialSecurityNumber());
		patientDto.setStreetAddress(patientProfileDto
				.getAddressStreetAddressLine());
		patientDto.setSsnNumber(patientProfileDto.getSocialSecurityNumber());
		patientDto.setCity(patientProfileDto.getAddressCity());
		patientDto.setState(patientProfileDto.getAddressStateCode());
		patientDto.setZip(patientProfileDto.getAddressPostalCode());
		patientDto.setMaritalStatus(patientProfileDto.getMaritalStatusCode()
				.getDisplayName());
		patientDto.setRace(patientProfileDto.getRaceCode().getDisplayName());
		patientDto.setReligion(patientProfileDto.getReligiousAffiliationCode()
				.getDisplayName());
		patientDto.setLanguage(patientProfileDto.getLanguageCode()
				.getDisplayName());
		patientDto.setHomePhone(patientProfileDto.getTelephoneTelephone());

		return patientDto;
	}

	/**
	 * Trasfer date type.
	 *
	 * @param date
	 *            the date
	 * @return the string
	 */
	protected String trasferDateType(Date date) {
		String pattern = "yyyyMMdd";
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return (format.format(date));

	}
}
