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
package gov.samhsa.consent2share.service.patient;

import gov.samhsa.consent2share.common.AuthenticatedUser;
import gov.samhsa.consent2share.common.UserContext;
import gov.samhsa.consent2share.domain.patient.Patient;
import gov.samhsa.consent2share.domain.patient.PatientLegalRepresentativeAssociation;
import gov.samhsa.consent2share.domain.patient.PatientLegalRepresentativeAssociationPk;
import gov.samhsa.consent2share.domain.patient.PatientLegalRepresentativeAssociationRepository;
import gov.samhsa.consent2share.domain.patient.PatientRepository;
import gov.samhsa.consent2share.domain.reference.LegalRepresentativeTypeCodeRepository;
import gov.samhsa.consent2share.service.dto.LegalRepresentativeDto;
import gov.samhsa.consent2share.service.dto.LookupDto;
import gov.samhsa.consent2share.service.dto.PatientLegalRepresentativeAssociationDto;
import gov.samhsa.consent2share.service.dto.PatientProfileDto;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * The Class PatientLegalRepresentativeAssociationServiceImpl.
 */
@Transactional
public class PatientLegalRepresentativeAssociationServiceImpl implements
		PatientLegalRepresentativeAssociationService {

	/** The patient legal representative association repository. */
	PatientLegalRepresentativeAssociationRepository patientLegalRepresentativeAssociationRepository;

	/** The patient repository. */
	PatientRepository patientRepository;

	/** The legal representative type code repository. */
	LegalRepresentativeTypeCodeRepository legalRepresentativeTypeCodeRepository;

	/** The model mapper. */
	ModelMapper modelMapper;

	/** The user context. */
	UserContext userContext;

	/**
	 * Instantiates a new patient legal representative association service impl.
	 *
	 * @param patientLegalRepresentativeAssociationRepository
	 *            the patient legal representative association repository
	 * @param patientRepository
	 *            the patient repository
	 * @param legalRepresentativeTypeCodeRepository
	 *            the legal representative type code repository
	 * @param modelMapper
	 *            the model mapper
	 * @param userContext
	 *            the user context
	 */
	public PatientLegalRepresentativeAssociationServiceImpl(
			PatientLegalRepresentativeAssociationRepository patientLegalRepresentativeAssociationRepository,
			PatientRepository patientRepository,
			LegalRepresentativeTypeCodeRepository legalRepresentativeTypeCodeRepository,
			ModelMapper modelMapper, UserContext userContext) {
		super();
		this.patientLegalRepresentativeAssociationRepository = patientLegalRepresentativeAssociationRepository;
		this.patientRepository = patientRepository;
		this.legalRepresentativeTypeCodeRepository = legalRepresentativeTypeCodeRepository;
		this.modelMapper = modelMapper;
		this.userContext = userContext;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.consent2share.service.patient.
	 * PatientLegalRepresentativeAssociationService
	 * #countAllPatientLegalRepresentativeAssociations()
	 */
	@Override
	public long countAllPatientLegalRepresentativeAssociations() {
		return patientLegalRepresentativeAssociationRepository.count();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.consent2share.service.patient.
	 * PatientLegalRepresentativeAssociationService
	 * #deletePatientLegalRepresentativeAssociation
	 * (gov.samhsa.consent2share.domain
	 * .patient.PatientLegalRepresentativeAssociation)
	 */
	@Override
	public void deletePatientLegalRepresentativeAssociation(
			PatientLegalRepresentativeAssociation patientLegalRepresentativeAssociation) {
		patientLegalRepresentativeAssociationRepository
				.delete(patientLegalRepresentativeAssociation);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.consent2share.service.patient.
	 * PatientLegalRepresentativeAssociationService
	 * #deletePatientLegalRepresentativeById(java.lang.Long)
	 */
	@Override
	public void deletePatientLegalRepresentativeById(Long legalRepId) {
		deletePatientLegalRepresentativeAssociationById(legalRepId);
		Patient legalRep = patientRepository.findOne(legalRepId);
		patientRepository.delete(legalRep);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.consent2share.service.patient.
	 * PatientLegalRepresentativeAssociationService
	 * #findPatientLegalRepresentativeAssociation(java.lang.Long)
	 */
	@Override
	public PatientLegalRepresentativeAssociation findPatientLegalRepresentativeAssociation(
			Long id) {
		return patientLegalRepresentativeAssociationRepository.findOne(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.consent2share.service.patient.
	 * PatientLegalRepresentativeAssociationService
	 * #findAllPatientLegalRepresentativeAssociations()
	 */
	@Override
	public List<PatientLegalRepresentativeAssociation> findAllPatientLegalRepresentativeAssociations() {
		return patientLegalRepresentativeAssociationRepository.findAll();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.consent2share.service.patient.
	 * PatientLegalRepresentativeAssociationService
	 * #findAllPatientLegalRepresentativeAssociationDtos()
	 */
	@Override
	public List<PatientLegalRepresentativeAssociationDto> findAllPatientLegalRepresentativeAssociationDtos() {
		AuthenticatedUser currentUser = userContext.getCurrentUser();
		String username = currentUser.getUsername();
		Patient patient = patientRepository.findByUsername(username);
		Long patientId = patient.getId();
		List<PatientLegalRepresentativeAssociationDto> associationDtos = new ArrayList<PatientLegalRepresentativeAssociationDto>();

		List<PatientLegalRepresentativeAssociation> allAssociations = patientLegalRepresentativeAssociationRepository
				.findAll();
		for (PatientLegalRepresentativeAssociation association : allAssociations) {
			if (association.getPatientLegalRepresentativeAssociationPk()
					.getPatient().getId() == patientId) {
				associationDtos
						.add(getPatientLegalRepresentativeAssociationDto(association));
			}
		}

		return associationDtos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.consent2share.service.patient.
	 * PatientLegalRepresentativeAssociationService
	 * #findAllPatientLegalRepresentativeDto()
	 */
	@Override
	public List<PatientProfileDto> findAllPatientLegalRepresentativeDto() {
		List<PatientProfileDto> legalRepDtos = new ArrayList<PatientProfileDto>();
		List<PatientLegalRepresentativeAssociationDto> associationDtos = findAllPatientLegalRepresentativeAssociationDtos();
		for (PatientLegalRepresentativeAssociationDto associationDto : associationDtos) {
			Patient patient = patientRepository.findOne(associationDto
					.getLegalRepresentativeId());
			PatientProfileDto legalRepDto = modelMapper.map(patient,
					PatientProfileDto.class);
			legalRepDtos.add(legalRepDto);
		}
		return legalRepDtos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.consent2share.service.patient.
	 * PatientLegalRepresentativeAssociationService
	 * #findPatientLegalRepresentativeAssociationEntries(int, int)
	 */
	@Override
	public List<PatientLegalRepresentativeAssociation> findPatientLegalRepresentativeAssociationEntries(
			int firstResult, int maxResults) {
		return patientLegalRepresentativeAssociationRepository.findAll(
				new org.springframework.data.domain.PageRequest(firstResult
						/ maxResults, maxResults)).getContent();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.consent2share.service.patient.
	 * PatientLegalRepresentativeAssociationService
	 * #savePatientLegalRepresentativeAssociation
	 * (gov.samhsa.consent2share.domain
	 * .patient.PatientLegalRepresentativeAssociation)
	 */
	@Override
	public void savePatientLegalRepresentativeAssociation(
			PatientLegalRepresentativeAssociation patientLegalRepresentativeAssociation) {
		patientLegalRepresentativeAssociationRepository
				.save(patientLegalRepresentativeAssociation);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.consent2share.service.patient.
	 * PatientLegalRepresentativeAssociationService
	 * #savePatientLegalRepresentativeAssociationDto
	 * (gov.samhsa.consent2share.service
	 * .dto.PatientLegalRepresentativeAssociationDto)
	 */
	@Override
	public void savePatientLegalRepresentativeAssociationDto(
			PatientLegalRepresentativeAssociationDto patientLegalRepresentativeAssociationDto) {
		PatientLegalRepresentativeAssociation association = getPatientLegalRepresentativeAssociationFromDto(patientLegalRepresentativeAssociationDto);
		patientLegalRepresentativeAssociationRepository.save(association);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.consent2share.service.patient.
	 * PatientLegalRepresentativeAssociationService
	 * #updatePatientLegalRepresentativeAssociation
	 * (gov.samhsa.consent2share.domain
	 * .patient.PatientLegalRepresentativeAssociation)
	 */
	@Override
	public PatientLegalRepresentativeAssociation updatePatientLegalRepresentativeAssociation(
			PatientLegalRepresentativeAssociation patientLegalRepresentativeAssociation) {
		return patientLegalRepresentativeAssociationRepository
				.save(patientLegalRepresentativeAssociation);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.consent2share.service.patient.
	 * PatientLegalRepresentativeAssociationService
	 * #updatePatientLegalRepresentativeAssociationDto
	 * (gov.samhsa.consent2share.service
	 * .dto.PatientLegalRepresentativeAssociationDto)
	 */
	@Override
	public void updatePatientLegalRepresentativeAssociationDto(
			PatientLegalRepresentativeAssociationDto patientLegalRepresentativeAssociationDto) {
		List<PatientLegalRepresentativeAssociation> associations = patientLegalRepresentativeAssociationRepository
				.findByPatientLegalRepresentativeAssociationPkLegalRepresentativeId(patientLegalRepresentativeAssociationDto
						.getLegalRepresentativeId());
		for (PatientLegalRepresentativeAssociation association : associations) {
			if (patientRepository.findByUsername(
					userContext.getCurrentUser().getUsername()).getId() == association
					.getPatientLegalRepresentativeAssociationPk().getPatient()
					.getId()) {

				if (patientLegalRepresentativeAssociationDto
						.getLegalRepresentativeTypeCode() != null) {
					association
							.setLegalRepresentativeTypeCode(legalRepresentativeTypeCodeRepository
									.findByCode(patientLegalRepresentativeAssociationDto
											.getLegalRepresentativeTypeCode()
											.getCode()));
				} else {
					association.setLegalRepresentativeTypeCode(null);
				}

				if (patientLegalRepresentativeAssociationDto
						.getRelationshipStartDate() != null) {
					association
							.setRelationshipStartDate(patientLegalRepresentativeAssociationDto
									.getRelationshipStartDate());
				} else {
					association.setRelationshipStartDate(null);
				}

				if (patientLegalRepresentativeAssociationDto
						.getRelationshipEndDate() != null) {
					association
							.setRelationshipEndDate(patientLegalRepresentativeAssociationDto
									.getRelationshipEndDate());
				} else {
					association.setRelationshipEndDate(null);
				}
				patientLegalRepresentativeAssociationRepository
						.save(association);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.consent2share.service.patient.
	 * PatientLegalRepresentativeAssociationService
	 * #getAllLegalRepresentativeDto()
	 */
	@Override
	public List<LegalRepresentativeDto> getAllLegalRepresentativeDto() {
		// combine patientProfilrDto and legalRepAssociationDto into
		// legalRepresentativeDto
		List<PatientLegalRepresentativeAssociationDto> associationDtos = findAllPatientLegalRepresentativeAssociationDtos();
		List<PatientProfileDto> legalRepDtos = findAllPatientLegalRepresentativeDto();

		Iterator<PatientProfileDto> legalRepIterator = legalRepDtos.iterator();
		List<LegalRepresentativeDto> legalRepresentativeDtos = new ArrayList<LegalRepresentativeDto>();
		for (PatientLegalRepresentativeAssociationDto associationDto : associationDtos) {
			// maybe need manual mapping
			LegalRepresentativeDto legalRepresentativeDto = modelMapper.map(
					legalRepIterator.next(), LegalRepresentativeDto.class);
			if (associationDto.getLegalRepresentativeTypeCode() != null) {
				legalRepresentativeDto
						.setLegalRepresentativeTypeCode(associationDto
								.getLegalRepresentativeTypeCode().getCode());
			} else {
				legalRepresentativeDto.setLegalRepresentativeTypeCode(null);
			}
			if (associationDto.getRelationshipStartDate() != null) {
				legalRepresentativeDto.setRelationshipStartDate(associationDto
						.getRelationshipStartDate());
			} else {
				legalRepresentativeDto.setRelationshipStartDate(null);
			}
			if (associationDto.getRelationshipEndDate() != null) {
				legalRepresentativeDto.setRelationshipEndDate(associationDto
						.getRelationshipEndDate());
			} else {
				legalRepresentativeDto.setRelationshipEndDate(null);
			}
			legalRepresentativeDtos.add(legalRepresentativeDto);
		}

		return legalRepresentativeDtos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.consent2share.service.patient.
	 * PatientLegalRepresentativeAssociationService
	 * #getAssociationDtoFromLegalRepresentativeDto
	 * (gov.samhsa.consent2share.service.dto.LegalRepresentativeDto)
	 */
	@Override
	public PatientLegalRepresentativeAssociationDto getAssociationDtoFromLegalRepresentativeDto(
			LegalRepresentativeDto legalRepresentativeDto) {
		PatientLegalRepresentativeAssociationDto associationDto = new PatientLegalRepresentativeAssociationDto();
		if (StringUtils.hasText(legalRepresentativeDto
				.getLegalRepresentativeTypeCode())) {
			associationDto.setLegalRepresentativeTypeCode(modelMapper.map(
					legalRepresentativeTypeCodeRepository
							.findByCode(legalRepresentativeDto
									.getLegalRepresentativeTypeCode()),
					LookupDto.class));
		} else {
			associationDto.setLegalRepresentativeTypeCode(null);
		}
		if (legalRepresentativeDto.getRelationshipStartDate() != null) {
			associationDto.setRelationshipStartDate(legalRepresentativeDto
					.getRelationshipStartDate());
		} else {
			associationDto.setRelationshipStartDate(null);
		}
		if (legalRepresentativeDto.getRelationshipEndDate() != null) {
			associationDto.setRelationshipEndDate(legalRepresentativeDto
					.getRelationshipEndDate());
		} else {
			associationDto.setRelationshipEndDate(null);
		}

		return associationDto;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.consent2share.service.patient.
	 * PatientLegalRepresentativeAssociationService
	 * #getPatientDtoFromLegalRepresentativeDto
	 * (gov.samhsa.consent2share.service.dto.LegalRepresentativeDto)
	 */
	@Override
	public PatientProfileDto getPatientDtoFromLegalRepresentativeDto(
			LegalRepresentativeDto legalRepresentativeDto) {
		return modelMapper.map(legalRepresentativeDto, PatientProfileDto.class);

	}

	/**
	 * Delete patient legal representative association by id.
	 *
	 * @param legalRepId
	 *            the legal rep id
	 */
	private void deletePatientLegalRepresentativeAssociationById(Long legalRepId) {
		List<PatientLegalRepresentativeAssociation> patientLegalRepresentativeAssociations = patientLegalRepresentativeAssociationRepository
				.findByPatientLegalRepresentativeAssociationPkLegalRepresentativeId(legalRepId);
		for (PatientLegalRepresentativeAssociation association : patientLegalRepresentativeAssociations) {
			// if the current user is the patient in the
			// legalRepresentativeAssciation
			if (patientRepository.findByUsername(
					userContext.getCurrentUser().getUsername()).getId() == association
					.getPatientLegalRepresentativeAssociationPk().getPatient()
					.getId()) {
				patientLegalRepresentativeAssociationRepository
						.delete(association);
			}
		}
	}

	/**
	 * Gets the patient legal representative association dto.
	 *
	 * @param association
	 *            the association
	 * @return the patient legal representative association dto
	 */
	private PatientLegalRepresentativeAssociationDto getPatientLegalRepresentativeAssociationDto(
			PatientLegalRepresentativeAssociation association) {
		PatientLegalRepresentativeAssociationDto associationDto = modelMapper
				.map(association,
						PatientLegalRepresentativeAssociationDto.class);
		associationDto.setPatientId(association
				.getPatientLegalRepresentativeAssociationPk().getPatient()
				.getId());
		associationDto.setLegalRepresentativeId(association
				.getPatientLegalRepresentativeAssociationPk()
				.getLegalRepresentative().getId());
		return associationDto;
	}

	/**
	 * Gets the patient legal representative association from dto.
	 *
	 * @param associationDto
	 *            the association dto
	 * @return the patient legal representative association from dto
	 */
	private PatientLegalRepresentativeAssociation getPatientLegalRepresentativeAssociationFromDto(
			PatientLegalRepresentativeAssociationDto associationDto) {
		PatientLegalRepresentativeAssociation association = new PatientLegalRepresentativeAssociation();
		PatientLegalRepresentativeAssociationPk associationPk = new PatientLegalRepresentativeAssociationPk();

		associationPk.setPatient(patientRepository.findOne(associationDto
				.getPatientId()));
		associationPk.setLegalRepresentative(patientRepository
				.findOne(associationDto.getLegalRepresentativeId()));
		association.setPatientLegalRepresentativeAssociationPk(associationPk);

		if (associationDto.getLegalRepresentativeTypeCode() != null) {
			association
					.setLegalRepresentativeTypeCode(legalRepresentativeTypeCodeRepository
							.findByCode(associationDto
									.getLegalRepresentativeTypeCode().getCode()));
		} else {
			association.setLegalRepresentativeTypeCode(null);
		}

		if (associationDto.getRelationshipStartDate() != null) {
			association.setRelationshipStartDate(associationDto
					.getRelationshipStartDate());
		} else {
			association.setRelationshipStartDate(null);
		}

		if (associationDto.getRelationshipEndDate() != null) {
			association.setRelationshipEndDate(associationDto
					.getRelationshipEndDate());
		} else {
			association.setRelationshipEndDate(null);
		}
		return association;
	}
}
