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
package gov.samhsa.pcm.service.patient;

import gov.samhsa.pcm.domain.patient.Patient;
import gov.samhsa.pcm.domain.patient.PatientRepository;
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
import gov.samhsa.pcm.service.dto.PatientProfileDto;

import java.util.function.Consumer;
import java.util.function.Supplier;

import org.springframework.util.StringUtils;

/**
 * The Class PatientProfileDtoToPatientMapper.
 */
public class PatientProfileDtoToPatientMapper implements
		DtoToDomainEntityMapper<PatientProfileDto, Patient> {

	/** The patient repository. */
	private PatientRepository patientRepository;

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
	 * Instantiates a new patient profile dto to patient mapper.
	 *
	 * @param patientRepository
	 *            the patient repository
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
	public PatientProfileDtoToPatientMapper(
			PatientRepository patientRepository,
			StateCodeRepository stateCodeRepository,
			CountryCodeRepository countryCodeRepository,
			AdministrativeGenderCodeRepository administrativeGenderCodeRepository,
			LanguageCodeRepository languageCodeRepository,
			MaritalStatusCodeRepository maritalStatusCodeRepository,
			RaceCodeRepository raceCodeRepository,
			ReligiousAffiliationCodeRepository religiousAffiliationCodeRepository) {
		super();
		this.patientRepository = patientRepository;
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
	public Patient map(PatientProfileDto patientDto) {
		Patient patient = null;

		// Since username is not required, so need to check if it is null
		if (patientDto.getUsername() == null) {
			if (patientDto.getId() != null) {
				patient = patientRepository.findOne(patientDto.getId());
			} else {
				patient = new Patient();
			}
		} else {
			patient = patientRepository
					.findByUsername(patientDto.getUsername());
		}
		patient.setFirstName(patientDto.getFirstName());
		patient.setLastName(patientDto.getLastName());
		patient.setEmail(patientDto.getEmail());
		patient.setBirthDay(patientDto.getBirthDate());

		// Optional demographics fields
		set(patient::setSocialSecurityNumber,
				patientDto::getSocialSecurityNumber);

		if (StringUtils.hasText(patientDto.getTelephoneTelephone())) {
			Telephone telephone = new Telephone();
			telephone.setTelephone(patientDto.getTelephoneTelephone());
			// TODO: set telecom use code
			patient.setTelephone(telephone);
		} else {
			patient.setTelephone(null);
		}

		if (StringUtils.hasText(patientDto.getAddressStreetAddressLine())
				|| StringUtils.hasText(patientDto.getAddressCity())
				|| StringUtils.hasText(patientDto.getAddressStateCode())
				|| StringUtils.hasText(patientDto.getAddressPostalCode())) {

			Address address = new Address();
			address.setStreetAddressLine(patientDto
					.getAddressStreetAddressLine());
			address.setCity(patientDto.getAddressCity());
			address.setStateCode(stateCodeRepository.findByCode(patientDto
					.getAddressStateCode()));
			address.setPostalCode(patientDto.getAddressPostalCode());
			address.setCountryCode(countryCodeRepository.findByCode(patientDto
					.getAddressCountryCode()));
			patient.setAddress(address);
		} else {
			patient.setAddress(null);
		}

		if (StringUtils.hasText(patientDto.getAdministrativeGenderCode())) {
			AdministrativeGenderCode administrativeGenderCode = administrativeGenderCodeRepository
					.findByCode(patientDto.getAdministrativeGenderCode());
			patient.setAdministrativeGenderCode(administrativeGenderCode);
		} else {
			patient.setAdministrativeGenderCode(null);
		}

		if (patientDto.getLanguageCode() != null
				&& StringUtils.hasText(patientDto.getLanguageCode().getCode())) {
			patient.setLanguageCode(languageCodeRepository
					.findByCode(patientDto.getLanguageCode().getCode()));
		} else {
			patient.setLanguageCode(null);
		}

		if (patientDto.getMaritalStatusCode() != null
				&& StringUtils.hasText(patientDto.getMaritalStatusCode()
						.getCode())) {
			patient.setMaritalStatusCode(maritalStatusCodeRepository
					.findByCode(patientDto.getMaritalStatusCode().getCode()));
		} else {
			patient.setMaritalStatusCode(null);
		}

		if (patientDto.getRaceCode() != null
				&& StringUtils.hasText(patientDto.getRaceCode().getCode())) {
			patient.setRaceCode(raceCodeRepository.findByCode(patientDto
					.getRaceCode().getCode()));
		} else {
			patient.setRaceCode(null);
		}

		if (patientDto.getReligiousAffiliationCode() != null
				&& StringUtils.hasText(patientDto.getReligiousAffiliationCode()
						.getCode())) {
			patient.setReligiousAffiliationCode(religiousAffiliationCodeRepository
					.findByCode(patientDto.getReligiousAffiliationCode()
							.getCode()));
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

	private static void set(Consumer<String> consumer, Supplier<String> supplier) {
		if (StringUtils.hasText(supplier.get())) {
			consumer.accept(supplier.get());
		} else {
			consumer.accept(null);
		}
	}
}
