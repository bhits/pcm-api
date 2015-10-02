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
package gov.samhsa.consent2share.domain.consent;

import gov.samhsa.consent2share.domain.patient.Patient;
import gov.samhsa.consent2share.domain.valueset.MedicalSection;
import gov.samhsa.consent2share.domain.valueset.ValueSetCategory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * The Interface ConsentRepository.
 */
@Repository
public interface ConsentRepository extends JpaSpecificationExecutor<Consent>,
		JpaRepository<Consent, Long> {

	/**
	 * Find by patient.
	 *
	 * @param patient
	 *            the patient
	 * @return the list
	 */
	List<Consent> findByPatient(Patient patient);

	/**
	 * Find by signed pdf consent document id.
	 *
	 * @param documentId
	 *            the document id
	 * @return the consent
	 */
	Consent findBySignedPdfConsentDocumentId(String documentId);

	/**
	 * Find by signed pdf consent document signed status.
	 *
	 * @param signStatus
	 *            the sign status
	 * @return the list
	 */
	List<Consent> findAllBySignedPdfConsentDocumentSignedStatus(
			String signStatus);

	/**
	 * Find by signed pdf consent revoke document signed status.
	 *
	 * @param signStatus
	 *            the sign status
	 * @return the list
	 */
	List<Consent> findAllBySignedPdfConsentRevokeDocumentSignedStatus(
			String signStatus);

	/**
	 * Find by signed pdf consent revoke document signed status not.
	 *
	 * @param signStatus
	 *            the sign status
	 * @return the list
	 */
	List<Consent> findBySignedPdfConsentRevokeDocumentSignedStatusNot(
			String signStatus);

	/**
	 * Find by signed pdf consent document signed status not.
	 *
	 * @param signStatus
	 *            the sign status
	 * @return the list
	 */
	List<Consent> findBySignedPdfConsentDocumentSignedStatusNot(
			String signStatus);

	/**
	 * Find all by patient username.
	 *
	 * @param username
	 *            the username
	 * @return the list
	 */
	List<Consent> findAllByPatientUsername(String username);

	/**
	 * Find by DoNotShareClinicalDocumentSectionTypeCodes by medicalSection.
	 *
	 * @param medicalSection
	 *            the medicalSection
	 * @return the list
	 */
	List<Consent> findAllByDoNotShareClinicalDocumentSectionTypeCodesMedicalSection(
			MedicalSection medicalSection);

	/**
	 * Find by ConsentDoNotShareSensitivityPolicyCode by ValueSetCategory.
	 *
	 * @param valueSetCategory
	 *            the value set category
	 * @return the list
	 */
	List<Consent> findAllByDoNotShareSensitivityPolicyCodesValueSetCategory(
			ValueSetCategory valueSetCategory);

	/**
	 * Find all by consent reference id.
	 *
	 * @param consentReferenceId
	 *            the consent reference id
	 * @return the list
	 */
	List<Consent> findAllByConsentReferenceId(String consentReferenceId);

	/**
	 * Find by patient.
	 *
	 * @param patientId
	 *            the patient id
	 * @return the list
	 */
	List<Consent> findByPatient(long patientId);

}
