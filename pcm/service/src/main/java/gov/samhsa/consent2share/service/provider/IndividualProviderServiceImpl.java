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
package gov.samhsa.consent2share.service.provider;

import gov.samhsa.consent2share.domain.patient.Patient;
import gov.samhsa.consent2share.domain.patient.PatientRepository;
import gov.samhsa.consent2share.domain.provider.IndividualProvider;
import gov.samhsa.consent2share.domain.provider.IndividualProviderRepository;
import gov.samhsa.consent2share.service.dto.IndividualProviderDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

/**
 * The Class IndividualProviderServiceImpl.
 */
@Transactional
public class IndividualProviderServiceImpl implements IndividualProviderService {

	/** The logger. */
	final Logger logger = LoggerFactory.getLogger(this.getClass());

	/** The individual provider repository. */
	IndividualProviderRepository individualProviderRepository;

	/** The model mapper. */
	ModelMapper modelMapper;

	/** The patient repository. */
	PatientRepository patientRepository;

	/**
	 * Instantiates a new individual provider service impl.
	 *
	 * @param individualProviderRepository
	 *            the individual provider repository
	 * @param modelMapper
	 *            the model mapper
	 * @param patientRepository
	 *            the patient repository
	 */
	public IndividualProviderServiceImpl(
			IndividualProviderRepository individualProviderRepository,
			ModelMapper modelMapper, PatientRepository patientRepository) {
		super();
		this.individualProviderRepository = individualProviderRepository;
		this.modelMapper = modelMapper;
		this.patientRepository = patientRepository;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.consent2share.service.provider.IndividualProviderService#
	 * countAllIndividualProviders()
	 */
	@Override
	public long countAllIndividualProviders() {
		return individualProviderRepository.count();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.consent2share.service.provider.IndividualProviderService#
	 * updateIndividualProvider
	 * (gov.samhsa.consent2share.service.dto.IndividualProviderDto)
	 */
	@Override
	public void updateIndividualProvider(
			IndividualProviderDto individualProviderDto) {
		Patient patient = patientRepository
				.findByUsername(individualProviderDto.getUsername());
		Set<IndividualProvider> individualProviders = patient
				.getIndividualProviders();
		IndividualProvider individualProvider = null;
		for (IndividualProvider o : individualProviders) {
			if (o.getNpi().equals(individualProviderDto.getNpi())) {
				individualProvider = o;
				break;
			}

		}
		if (individualProvider == null)
			individualProvider = new IndividualProvider();
		individualProvider.setFirstName(individualProviderDto.getFirstName());
		individualProvider.setMiddleName(individualProviderDto.getMiddleName());
		individualProvider.setLastName(individualProviderDto.getLastName());
		individualProvider.setNamePrefix(individualProviderDto.getNamePrefix());
		individualProvider.setNameSuffix(individualProviderDto.getNameSuffix());
		individualProvider.setCredential(individualProviderDto.getCredential());
		individualProvider.setNpi(individualProviderDto.getNpi());
		individualProvider.setEntityType(individualProviderDto.getEntityType());
		individualProvider.setFirstLineMailingAddress(individualProviderDto
				.getFirstLineMailingAddress());
		individualProvider.setSecondLineMailingAddress(individualProviderDto
				.getSecondLineMailingAddress());
		individualProvider.setMailingAddressCityName(individualProviderDto
				.getMailingAddressCityName());
		individualProvider.setMailingAddressStateName(individualProviderDto
				.getMailingAddressStateName());
		individualProvider.setMailingAddressPostalCode(individualProviderDto
				.getMailingAddressPostalCode());
		individualProvider.setMailingAddressCountryCode(individualProviderDto
				.getMailingAddressCountryCode());
		individualProvider
				.setMailingAddressTelephoneNumber(individualProviderDto
						.getMailingAddressTelephoneNumber());
		individualProvider.setMailingAddressFaxNumber(individualProviderDto
				.getMailingAddressFaxNumber());
		individualProvider
				.setFirstLinePracticeLocationAddress(individualProviderDto
						.getFirstLinePracticeLocationAddress());
		individualProvider
				.setSecondLinePracticeLocationAddress(individualProviderDto
						.getSecondLinePracticeLocationAddress());
		individualProvider
				.setPracticeLocationAddressCityName(individualProviderDto
						.getPracticeLocationAddressCityName());
		individualProvider
				.setPracticeLocationAddressStateName(individualProviderDto
						.getPracticeLocationAddressStateName());
		individualProvider
				.setPracticeLocationAddressPostalCode(individualProviderDto
						.getPracticeLocationAddressPostalCode());
		individualProvider
				.setPracticeLocationAddressCountryCode(individualProviderDto
						.getPracticeLocationAddressCountryCode());
		individualProvider
				.setPracticeLocationAddressTelephoneNumber(individualProviderDto
						.getPracticeLocationAddressTelephoneNumber());
		individualProvider
				.setPracticeLocationAddressFaxNumber(individualProviderDto
						.getPracticeLocationAddressFaxNumber());
		individualProvider.setEnumerationDate(individualProviderDto
				.getEnumerationDate());
		individualProvider.setLastUpdateDate(individualProviderDto
				.getLastUpdateDate());
		individualProvider.setProviderTaxonomyCode(individualProviderDto
				.getProviderTaxonomyCode());
		individualProvider.setProviderTaxonomyDescription(individualProviderDto
				.getProviderTaxonomyDescription());
		individualProviders.add(individualProvider);
		patient.setIndividualProviders(individualProviders);

		patientRepository.save(patient);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.consent2share.service.provider.IndividualProviderService#
	 * deleteIndividualProvider
	 * (gov.samhsa.consent2share.domain.provider.IndividualProvider)
	 */
	@Override
	public void deleteIndividualProvider(IndividualProvider individualProvider) {
		individualProviderRepository.delete(individualProvider);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.consent2share.service.provider.IndividualProviderService#
	 * deleteIndividualProviderDto
	 * (gov.samhsa.consent2share.service.dto.IndividualProviderDto)
	 */
	@Override
	public void deleteIndividualProviderDto(
			IndividualProviderDto individualProviderDto) {

		Patient patient = patientRepository
				.findByUsername(individualProviderDto.getUsername());
		Set<IndividualProvider> individualProviders = patient
				.getIndividualProviders();
		for (IndividualProvider o : individualProviders) {
			if (o.getNpi().equals(individualProviderDto.getNpi())) {
				individualProviders.remove(o);
				break;
			}
		}
		patient.setIndividualProviders(individualProviders);
		patientRepository.save(patient);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.consent2share.service.provider.IndividualProviderService#
	 * deleteIndividualProviderByPatientId
	 * (gov.samhsa.consent2share.service.dto.IndividualProviderDto)
	 */
	@Override
	public void deleteIndividualProviderDtoByPatientId(
			IndividualProviderDto individualProviderDto) {

		Patient patient = patientRepository.findOne(Long
				.parseLong(individualProviderDto.getPatientId()));
		Set<IndividualProvider> individualProviders = patient
				.getIndividualProviders();
		for (IndividualProvider o : individualProviders) {
			if (o.getNpi().equals(individualProviderDto.getNpi())) {
				individualProviders.remove(o);
				break;
			}
		}
		patient.setIndividualProviders(individualProviders);
		patientRepository.save(patient);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.consent2share.service.provider.IndividualProviderService#
	 * findIndividualProviderByNpi(java.lang.String)
	 */
	@Override
	public IndividualProvider findIndividualProviderByNpi(String npi) {
		return individualProviderRepository.findByNpi(npi);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.consent2share.service.provider.IndividualProviderService#
	 * findIndividualProvider(java.lang.Long)
	 */
	@Override
	public IndividualProvider findIndividualProvider(Long id) {
		return individualProviderRepository.findOne(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.consent2share.service.provider.IndividualProviderService#
	 * findAllIndividualProviders()
	 */
	@Override
	public List<IndividualProvider> findAllIndividualProviders() {
		return individualProviderRepository.findAll();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.consent2share.service.provider.IndividualProviderService#
	 * findIndividualProviderEntries(int, int)
	 */
	@Override
	public List<IndividualProvider> findIndividualProviderEntries(
			int firstResult, int maxResults) {
		return individualProviderRepository.findAll(
				new org.springframework.data.domain.PageRequest(firstResult
						/ maxResults, maxResults)).getContent();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.consent2share.service.provider.IndividualProviderService#
	 * saveIndividualProvider
	 * (gov.samhsa.consent2share.domain.provider.IndividualProvider)
	 */
	@Override
	public void saveIndividualProvider(IndividualProvider individualProvider) {
		individualProviderRepository.save(individualProvider);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.consent2share.service.provider.IndividualProviderService#
	 * updateIndividualProvider
	 * (gov.samhsa.consent2share.domain.provider.IndividualProvider)
	 */
	@Override
	public IndividualProvider updateIndividualProvider(
			IndividualProvider individualProvider) {
		return individualProviderRepository.save(individualProvider);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.consent2share.service.provider.IndividualProviderService#
	 * addNewIndividualProvider
	 * (gov.samhsa.consent2share.service.dto.IndividualProviderDto)
	 */
	@Override
	public IndividualProvider addNewIndividualProvider(
			IndividualProviderDto individualProviderDto) {

		Patient patient;
		String inNPI = individualProviderDto.getNpi();

		if (individualProviderDto.getUsername() == null
				&& individualProviderDto.getPatientId() != null) {
			patient = patientRepository.findOne(Long.valueOf(
					individualProviderDto.getPatientId()).longValue());
		} else {
			patient = patientRepository.findByUsername(individualProviderDto
					.getUsername());
		}

		Set<IndividualProvider> individualProviders = patient
				.getIndividualProviders();

		IndividualProvider in_individualProvider = null;
		for (IndividualProvider o : individualProviders) {
			if (o.getNpi().equals(inNPI)) {
				in_individualProvider = o;
				break;
			}
		}

		if (in_individualProvider != null) {
			return null;
		} else {
			IndividualProvider individualProvider = new IndividualProvider();

			individualProvider.setFirstName(individualProviderDto
					.getFirstName());
			individualProvider.setMiddleName(individualProviderDto
					.getMiddleName());
			individualProvider.setLastName(individualProviderDto.getLastName());
			individualProvider.setNamePrefix(individualProviderDto
					.getNamePrefix());
			individualProvider.setNameSuffix(individualProviderDto
					.getNameSuffix());
			individualProvider.setCredential(individualProviderDto
					.getCredential());
			individualProvider.setNpi(individualProviderDto.getNpi());
			individualProvider.setEntityType(individualProviderDto
					.getEntityType());
			individualProvider.setFirstLineMailingAddress(individualProviderDto
					.getFirstLineMailingAddress());
			individualProvider
					.setSecondLineMailingAddress(individualProviderDto
							.getSecondLineMailingAddress());
			individualProvider.setMailingAddressCityName(individualProviderDto
					.getMailingAddressCityName());
			individualProvider.setMailingAddressStateName(individualProviderDto
					.getMailingAddressStateName());
			individualProvider
					.setMailingAddressPostalCode(individualProviderDto
							.getMailingAddressPostalCode());
			individualProvider
					.setMailingAddressCountryCode(individualProviderDto
							.getMailingAddressCountryCode());
			individualProvider
					.setMailingAddressTelephoneNumber(individualProviderDto
							.getMailingAddressTelephoneNumber());
			individualProvider.setMailingAddressFaxNumber(individualProviderDto
					.getMailingAddressFaxNumber());
			individualProvider
					.setFirstLinePracticeLocationAddress(individualProviderDto
							.getFirstLinePracticeLocationAddress());
			individualProvider
					.setSecondLinePracticeLocationAddress(individualProviderDto
							.getSecondLinePracticeLocationAddress());
			individualProvider
					.setPracticeLocationAddressCityName(individualProviderDto
							.getPracticeLocationAddressCityName());
			individualProvider
					.setPracticeLocationAddressStateName(individualProviderDto
							.getPracticeLocationAddressStateName());
			individualProvider
					.setPracticeLocationAddressPostalCode(individualProviderDto
							.getPracticeLocationAddressPostalCode());
			individualProvider
					.setPracticeLocationAddressCountryCode(individualProviderDto
							.getPracticeLocationAddressCountryCode());
			individualProvider
					.setPracticeLocationAddressTelephoneNumber(individualProviderDto
							.getPracticeLocationAddressTelephoneNumber());
			individualProvider
					.setPracticeLocationAddressFaxNumber(individualProviderDto
							.getPracticeLocationAddressFaxNumber());
			individualProvider.setEnumerationDate(individualProviderDto
					.getEnumerationDate());
			individualProvider.setLastUpdateDate(individualProviderDto
					.getLastUpdateDate());
			individualProvider.setProviderTaxonomyCode(individualProviderDto
					.getProviderTaxonomyCode());
			individualProvider
					.setProviderTaxonomyDescription(individualProviderDto
							.getProviderTaxonomyDescription());
			individualProviders.add(individualProvider);
			patient.setIndividualProviders(individualProviders);

			patient = patientRepository.save(patient);

			Set<IndividualProvider> individualProvidersReturned = patient
					.getIndividualProviders();

			IndividualProvider in_individualProviderReturned = null;
			for (IndividualProvider o : individualProvidersReturned) {
				if (o.getNpi().equals(inNPI)) {
					in_individualProviderReturned = o;
					break;
				}
			}

			if (in_individualProviderReturned == null) {
				return null;
			} else {
				return in_individualProviderReturned;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.consent2share.service.provider.IndividualProviderService#
	 * findAllIndividualProvidersDto()
	 */
	@Override
	public List<IndividualProviderDto> findAllIndividualProvidersDto() {
		List<IndividualProviderDto> providers = new ArrayList<IndividualProviderDto>();

		for (IndividualProvider entity : individualProviderRepository.findAll()) {
			providers.add(modelMapper.map(entity, IndividualProviderDto.class));
		}
		return providers;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.consent2share.service.provider.IndividualProviderService#
	 * findIndividualProviderDto(java.lang.Long)
	 */
	@Override
	public IndividualProviderDto findIndividualProviderDto(Long id) {
		IndividualProvider provider = individualProviderRepository.findOne(id);
		IndividualProviderDto providerDto = modelMapper.map(provider,
				IndividualProviderDto.class);

		return providerDto;
	}
}
