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

import gov.samhsa.consent2share.domain.patient.PatientLegalRepresentativeAssociation;
import gov.samhsa.consent2share.service.dto.LegalRepresentativeDto;
import gov.samhsa.consent2share.service.dto.PatientLegalRepresentativeAssociationDto;
import gov.samhsa.consent2share.service.dto.PatientProfileDto;

import java.util.List;

import org.springframework.security.access.annotation.Secured;

/**
 * The Interface PatientLegalRepresentativeAssociationService.
 */
@Secured({"ROLE_USER","ROLE_ADMIN"})
public interface PatientLegalRepresentativeAssociationService {

	/**
	 * Count all patient legal representative associations.
	 *
	 * @return the long
	 */
	public abstract long countAllPatientLegalRepresentativeAssociations();


	/**
	 * Delete patient legal representative association.
	 *
	 * @param patientLegalRepresentativeAssociation the patient legal representative association
	 */
	public abstract void deletePatientLegalRepresentativeAssociation(PatientLegalRepresentativeAssociation patientLegalRepresentativeAssociation);

	
	/**
	 * Delete patient legal representative by id.
	 *
	 * @param legalRepId the legal rep id
	 */
	public abstract void deletePatientLegalRepresentativeById(Long legalRepId);
	
	
	/**
	 * Find patient legal representative association.
	 *
	 * @param id the id
	 * @return the patient legal representative association
	 */
	public abstract PatientLegalRepresentativeAssociation findPatientLegalRepresentativeAssociation(Long id);


	/**
	 * Find all patient legal representative associations.
	 *
	 * @return the list
	 */
	public abstract List<PatientLegalRepresentativeAssociation> findAllPatientLegalRepresentativeAssociations();
	
	
	/**
	 * Find all patient legal representative association dtos.
	 *
	 * @return the list
	 */
	public abstract List<PatientLegalRepresentativeAssociationDto> findAllPatientLegalRepresentativeAssociationDtos();
	
	
	/**
	 * Find all patient legal representative dto.
	 *
	 * @return the list
	 */
	public abstract List<PatientProfileDto> findAllPatientLegalRepresentativeDto();
	

	/**
	 * Find patient legal representative association entries.
	 *
	 * @param firstResult the first result
	 * @param maxResults the max results
	 * @return the list
	 */
	public abstract List<PatientLegalRepresentativeAssociation> findPatientLegalRepresentativeAssociationEntries(int firstResult, int maxResults);


	/**
	 * Save patient legal representative association.
	 *
	 * @param patientLegalRepresentativeAssociation the patient legal representative association
	 */
	public abstract void savePatientLegalRepresentativeAssociation(PatientLegalRepresentativeAssociation patientLegalRepresentativeAssociation);

	
	/**
	 * Save patient legal representative association dto.
	 *
	 * @param patientLegalRepresentativeAssociationDto the patient legal representative association dto
	 */
	public abstract void savePatientLegalRepresentativeAssociationDto(PatientLegalRepresentativeAssociationDto patientLegalRepresentativeAssociationDto);
	
	
	/**
	 * Update patient legal representative association.
	 *
	 * @param patientLegalRepresentativeAssociation the patient legal representative association
	 * @return the patient legal representative association
	 */
	public abstract PatientLegalRepresentativeAssociation updatePatientLegalRepresentativeAssociation(PatientLegalRepresentativeAssociation patientLegalRepresentativeAssociation);

	
	/**
	 * Update patient legal representative association dto.
	 *
	 * @param patientLegalRepresentativeAssociationDto the patient legal representative association dto
	 */
	public abstract void updatePatientLegalRepresentativeAssociationDto(PatientLegalRepresentativeAssociationDto patientLegalRepresentativeAssociationDto);
	
	
	/**
	 * Gets the all legal representative dto.
	 *
	 * @return the all legal representative dto
	 */
	public abstract List<LegalRepresentativeDto> getAllLegalRepresentativeDto();
	
	
	/**
	 * Gets the association dto from legal representative dto.
	 *
	 * @param legalRepresentativeDto the legal representative dto
	 * @return the association dto from legal representative dto
	 */
	public abstract PatientLegalRepresentativeAssociationDto getAssociationDtoFromLegalRepresentativeDto (LegalRepresentativeDto legalRepresentativeDto);
	
	
	/**
	 * Gets the patient dto from legal representative dto.
	 *
	 * @param legalRepresentativeDto the legal representative dto
	 * @return the patient dto from legal representative dto
	 */
	public abstract PatientProfileDto getPatientDtoFromLegalRepresentativeDto (LegalRepresentativeDto legalRepresentativeDto);
}
