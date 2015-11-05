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
package gov.samhsa.pcm.service.patient.pg;

import gov.samhsa.pcm.domain.patient.Patient;
import gov.samhsa.pcm.domain.reference.AdministrativeGenderCode;
import gov.samhsa.pcm.domain.reference.AdministrativeGenderCodeRepository;
import gov.samhsa.pcm.domain.reference.CountryCodeRepository;
import gov.samhsa.pcm.domain.reference.LanguageCodeRepository;
import gov.samhsa.pcm.domain.reference.MaritalStatusCodeRepository;
import gov.samhsa.pcm.domain.reference.RaceCodeRepository;
import gov.samhsa.pcm.domain.reference.ReligiousAffiliationCodeRepository;
import gov.samhsa.pcm.domain.reference.StateCodeRepository;
import gov.samhsa.pcm.domain.valueobject.Address;
import gov.samhsa.pcm.domain.valueobject.Telephone;
import gov.samhsa.pcm.infrastructure.DtoToDomainEntityMapper;
import gov.samhsa.spirit.wsclient.dto.PatientDto;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * The Class PatientDtoToPatientMapper.
 */
public class PatientDtoToPatientMapper implements
		DtoToDomainEntityMapper<PatientDto, Patient> {
	/** The logger. */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/** The state code repository. */
	private StateCodeRepository stateCodeRepository;

	/** The country code repository. */
	private CountryCodeRepository countryCodeRepository;

	/** The administrative gender code repository. */
	private AdministrativeGenderCodeRepository administrativeGenderCodeRepository;

	/** The language code repository. */
	private LanguageCodeRepository languageCodeRepository;

	/** The marital status code repository. */
	private MaritalStatusCodeRepository maritalStatusCodeRepository;

	/** The race code repository. */
	private RaceCodeRepository raceCodeRepository;

	/** The religious affiliation code repository. */
	private ReligiousAffiliationCodeRepository religiousAffiliationCodeRepository;

	/**
	 * Instantiates a new patient dto to patient mapper.
	 *
	 * @param stateCodeRepository
	 *            the state code repository
	 * @param countryCodeRepository
	 *            the country code repository
	 * @param administrativeGenderCodeRepository
	 *            the administrative gender code repository
	 * @param languageCodeRepository
	 *            the language code repository
	 * @param maritalStatusCodeRepository
	 *            the marital status code repository
	 * @param raceCodeRepository
	 *            the race code repository
	 * @param religiousAffiliationCodeRepository
	 *            the religious affiliation code repository
	 */
	public PatientDtoToPatientMapper(
			StateCodeRepository stateCodeRepository,
			CountryCodeRepository countryCodeRepository,
			AdministrativeGenderCodeRepository administrativeGenderCodeRepository,
			LanguageCodeRepository languageCodeRepository,
			MaritalStatusCodeRepository maritalStatusCodeRepository,
			RaceCodeRepository raceCodeRepository,
			ReligiousAffiliationCodeRepository religiousAffiliationCodeRepository) {
		super();
		this.stateCodeRepository = stateCodeRepository;
		this.countryCodeRepository = countryCodeRepository;
		this.administrativeGenderCodeRepository = administrativeGenderCodeRepository;
		this.languageCodeRepository = languageCodeRepository;
		this.maritalStatusCodeRepository = maritalStatusCodeRepository;
		this.raceCodeRepository = raceCodeRepository;
		this.religiousAffiliationCodeRepository = religiousAffiliationCodeRepository;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.consent2share.infrastructure.DtoToDomainEntityMapper#map(java
	 * .lang.Object)
	 */
	@Override
	public Patient map(PatientDto patientDto) {
		Patient patient = new Patient();

		patient.setFirstName(patientDto.getFirstName());
		patient.setLastName(patientDto.getLastName());
		patient.setEmail(patientDto.getEmailHome());
		try {
			patient.setBirthDay(new SimpleDateFormat("yyyyMMdd")
					.parse(patientDto.getBirthDate()));
		} catch (ParseException e) {
			logger.warn("Can't parse birthday from exchange:"
					+ patientDto.getBirthDate());
		}

		// Optional demographics fields
		if (StringUtils.hasText(patientDto.getSsnNumber())) {
			patient.setSocialSecurityNumber(patientDto.getSsnNumber());
		} else {
			patient.setSocialSecurityNumber(null);
		}

		if (StringUtils.hasText(patientDto.getPatientId())) {
			patient.setEnterpriseIdentifier(patientDto.getPatientId());
		} else {
			patient.setEnterpriseIdentifier(null);
		}

		// updating local identifier with medical record number
		if (StringUtils.hasText(patientDto.getLocalPatientId())) {
			patient.setMedicalRecordNumber(patientDto.getLocalPatientId());
		} else {
			patient.setMedicalRecordNumber(null);
		}

		if (StringUtils.hasText(patientDto.getHomePhone())) {
			Telephone telephone = new Telephone();
			telephone.setTelephone(patientDto.getHomePhone());
			// TODO: set telecom use code
			patient.setTelephone(telephone);
		} else {
			patient.setTelephone(null);
		}

		if (StringUtils.hasText(patientDto.getStreetAddress())
				|| StringUtils.hasText(patientDto.getCity())
				|| StringUtils.hasText(patientDto.getState())
				|| StringUtils.hasText(patientDto.getZip())) {

			Address address = new Address();
			address.setStreetAddressLine(patientDto.getStreetAddress());
			address.setCity(patientDto.getCity());
			address.setStateCode(stateCodeRepository.findByCode(patientDto
					.getState()));
			address.setPostalCode(patientDto.getZip());
			address.setCountryCode(countryCodeRepository.findByCode(patientDto
					.getCountry()));
			patient.setAddress(address);
		} else {
			patient.setAddress(null);
		}

		if (StringUtils.hasText(patientDto.getGenderCode())) {
			AdministrativeGenderCode administrativeGenderCode = administrativeGenderCodeRepository
					.findByCode(patientDto.getGenderCode());
			patient.setAdministrativeGenderCode(administrativeGenderCode);
		} else {
			patient.setAdministrativeGenderCode(null);
		}

		if (patientDto.getLanguage() != null
				&& StringUtils.hasText(patientDto.getLanguage())) {
			patient.setLanguageCode(languageCodeRepository
					.findByCode(patientDto.getLanguage()));
		} else {
			patient.setLanguageCode(null);
		}

		if (patientDto.getMaritalStatus() != null
				&& StringUtils.hasText(patientDto.getMaritalStatus())) {
			patient.setMaritalStatusCode(maritalStatusCodeRepository
					.findByCode(patientDto.getMaritalStatus()));
		} else {
			patient.setMaritalStatusCode(null);
		}

		if (patientDto.getRace() != null
				&& StringUtils.hasText(patientDto.getRace())) {
			patient.setRaceCode(raceCodeRepository.findByCode(patientDto
					.getRace()));
		} else {
			patient.setRaceCode(null);
		}

		if (patientDto.getReligion() != null
				&& StringUtils.hasText(patientDto.getReligion())) {
			patient.setReligiousAffiliationCode(religiousAffiliationCodeRepository
					.findByCode(patientDto.getReligion()));
		} else {
			patient.setReligiousAffiliationCode(null);
		}

		// if (patientDto.getIndividualProviders() != null
		// && patientDto.getIndividualProviders().size() > 0) {
		// patient.setIndividualProviders(patientDto.getIndividualProviders());
		// } else {
		// patient.setIndividualProviders(null);
		// }

		return patient;
	}
}
